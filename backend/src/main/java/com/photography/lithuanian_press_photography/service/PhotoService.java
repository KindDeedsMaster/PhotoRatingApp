package com.photography.lithuanian_press_photography.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

import com.photography.lithuanian_press_photography.config.StorageProperties;
import com.photography.lithuanian_press_photography.dto.request.photo.PhotoRequest;
import com.photography.lithuanian_press_photography.exeption.ImageProcessingException;
import com.photography.lithuanian_press_photography.exeption.ImageValidationException;
import com.photography.lithuanian_press_photography.exeption.StorageException;
import com.photography.lithuanian_press_photography.exeption.StorageFileNotFoundException;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.common.RationalNumber;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.GpsTagConstants;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;

@Service
public class PhotoService {

    private final Path rootLocation;

    @Autowired
    public PhotoService(StorageProperties properties) {
        if (properties.getLocation().trim().length() == 0) {
            throw new StorageException("File upload location can not be Empty.");
        }
        this.rootLocation = Paths.get(properties.getLocation());
    }

    public void store(UUID categoryId, MultipartFile file) {
        UUID photoId = UUID.randomUUID();
        String folderName = categoryId.toString();
        String photoName = photoId + ".jpg";
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }

            Path destinationFile = this.rootLocation
                    .resolve(folderName)
                    .resolve(photoName)
                    .normalize()
                    .toAbsolutePath();
            System.out.println(destinationFile);
            if (!destinationFile.getParent().getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    public Stream<Path> loadAll(String categoryId) {
        try {
            return Files.walk(this.rootLocation.resolve(categoryId), 1)
                    .filter(path -> !path.equals(this.rootLocation.resolve(categoryId)))
                    .map(this.rootLocation.resolve(categoryId)::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    public Path load(String categoryId, String filename) {
        return rootLocation.resolve(categoryId).resolve(filename);
    }

    public Resource loadAsResource(String categoryId, String filename) {
        try {
            Path file = load(categoryId, filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    public void init(UUID categoryId) {
        String categoryLocation = categoryId.toString();
        try {
            Files.createDirectories(rootLocation.resolve(categoryLocation));
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    public void storeAll(UUID categoryId, MultipartFile[] files) {
        validateImages(files);
        for (MultipartFile file : files) {
            store(categoryId, file);
        }
    }

    private void validateImages(MultipartFile[] images) {
        for (MultipartFile image : images) {
            if (!image.getContentType().equals("image/jpeg")) {
                throw new ImageValidationException(image.getOriginalFilename() + "wrong type");
            }
            try (InputStream is = new ByteArrayInputStream(image.getBytes())) {
                BufferedImage img = ImageIO.read(is);
                if (img.getHeight() > img.getWidth()) { // image is a portrait
                    if (img.getHeight() < 2500 || img.getHeight() > 4000) {
                        throw new ImageValidationException(image.getOriginalFilename() + " Image height must be between 2500 and 4000");
                    }
                } else {
                    if (img.getWidth() < 2500 || img.getWidth() > 4000) {
                        throw new ImageValidationException(image.getOriginalFilename() + " Image width must be between 2500 and 4000");
                    }
                }
            } catch (IOException exception) {
                throw new ImageProcessingException("Could not get image " +
                        image.getOriginalFilename() + " input stream");
            }
        }
    }

    public void readImageMeta(final File imgFile) throws IOException {
        /** get all metadata stored in EXIF format (ie. from JPEG or TIFF). **/
        final ImageMetadata metadata = Imaging.getMetadata(imgFile);
        System.out.println(metadata);
        System.out.println("--------------------------------------------------------------------------------");

        /** Get specific meta data information by drilling down the meta **/
        if (metadata instanceof JpegImageMetadata jpegMetadata) {
            printTagValue(jpegMetadata, GpsTagConstants.GPS_TAG_GPS_LATITUDE_REF);
            printTagValue(jpegMetadata, GpsTagConstants.GPS_TAG_GPS_LATITUDE);
            printTagValue(jpegMetadata, GpsTagConstants.GPS_TAG_GPS_LONGITUDE_REF);
            printTagValue(jpegMetadata, GpsTagConstants.GPS_TAG_GPS_LONGITUDE);

            // simple interface to GPS data
            final TiffImageMetadata exifMetadata = jpegMetadata.getExif();
            if (null != exifMetadata) {
                final TiffImageMetadata.GpsInfo gpsInfo = exifMetadata.getGpsInfo();
                if (null != gpsInfo) {
                    final String gpsDescription = gpsInfo.toString();
                    final double longitude = gpsInfo.getLongitudeAsDegreesEast();
                    final double latitude = gpsInfo.getLatitudeAsDegreesNorth();

                    System.out.println("    " + "GPS Description: " + gpsDescription);
                    System.out.println("    " + "GPS Longitude (Degrees East): " + longitude);
                    System.out.println("    " + "GPS Latitude (Degrees North): " + latitude);
                }
            }

            // more specific example of how to manually access GPS values
            final TiffField gpsLatitudeRefField = jpegMetadata.findExifValueWithExactMatch(GpsTagConstants.GPS_TAG_GPS_LATITUDE_REF);
            final TiffField gpsLatitudeField = jpegMetadata.findExifValueWithExactMatch(GpsTagConstants.GPS_TAG_GPS_LATITUDE);
            final TiffField gpsLongitudeRefField = jpegMetadata.findExifValueWithExactMatch(GpsTagConstants.GPS_TAG_GPS_LONGITUDE_REF);
            final TiffField gpsLongitudeField = jpegMetadata.findExifValueWithExactMatch(GpsTagConstants.GPS_TAG_GPS_LONGITUDE);
            if (gpsLatitudeRefField != null && gpsLatitudeField != null && gpsLongitudeRefField != null && gpsLongitudeField != null) {
                // all of these values are strings.
                final String gpsLatitudeRef = (String) gpsLatitudeRefField.getValue();
                final RationalNumber[] gpsLatitude = (RationalNumber[]) (gpsLatitudeField.getValue());
                final String gpsLongitudeRef = (String) gpsLongitudeRefField.getValue();
                final RationalNumber[] gpsLongitude = (RationalNumber[]) gpsLongitudeField.getValue();

                final RationalNumber gpsLatitudeDegrees = gpsLatitude[0];
                final RationalNumber gpsLatitudeMinutes = gpsLatitude[1];
                final RationalNumber gpsLatitudeSeconds = gpsLatitude[2];

                final RationalNumber gpsLongitudeDegrees = gpsLongitude[0];
                final RationalNumber gpsLongitudeMinutes = gpsLongitude[1];
                final RationalNumber gpsLongitudeSeconds = gpsLongitude[2];

                // This will format the gps info like so:
                //
                // gpsLatitude: 8 degrees, 40 minutes, 42.2 seconds S
                // gpsLongitude: 115 degrees, 26 minutes, 21.8 seconds E

                System.out.println("    " + "GPS Latitude: " + gpsLatitudeDegrees.toDisplayString() + " degrees, " + gpsLatitudeMinutes.toDisplayString() + " minutes, " + gpsLatitudeSeconds.toDisplayString() + " seconds " + gpsLatitudeRef);
                System.out.println("    " + "GPS Longitude: " + gpsLongitudeDegrees.toDisplayString() + " degrees, " + gpsLongitudeMinutes.toDisplayString() + " minutes, " + gpsLongitudeSeconds.toDisplayString() + " seconds " + gpsLongitudeRef);
            }
        }
    }

    private static void printTagValue(final JpegImageMetadata jpegMetadata, TagInfo tagInfo) {
        final TiffField field = jpegMetadata.findExifValueWithExactMatch(tagInfo);
        if (field == null) {
            System.out.println(tagInfo.name + ": " + "Not Found.");
        } else {
            System.out.println(tagInfo.name + ": " + field.getValueDescription());
        }
    }


}