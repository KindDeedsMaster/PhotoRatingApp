package com.photography.lithuanian_press_photography.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.photography.lithuanian_press_photography.dto.request.photo.PhotoRequest;
import com.photography.lithuanian_press_photography.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

//    @GetMapping("/server")
//    public String listUploadedFiles(Model model) throws IOException {
//
//        model.addAttribute("files", photoService.loadAll().map(
//                        path -> MvcUriComponentsBuilder.fromMethodName(PhotoController.class,
//                                "serveFile", path.getFileName().toString()).build().toUri().toString())
//                .collect(Collectors.toList()));
//        return "uploadForm";
//    }

    @GetMapping("/category/{categoryId}/images")
    public ResponseEntity<List<String>> listUploadedFiles2(@PathVariable UUID categoryId) {
        try {
            Stream<Path> imagesPaths = photoService.loadAll();
            List<String> imageUrls = imagesPaths
                    .map(path -> MvcUriComponentsBuilder.fromMethodName(PhotoController.class, "serveFile", path.getFileName().toString())
                            .build()
                            .toString())
                    .toList();
            return new ResponseEntity<>(imageUrls, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = photoService.loadAsResource(filename);

        if (file == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/category/{categoryId}/upload")
    public String handleFilesUpload(@PathVariable UUID categoryId,
                                    @RequestParam("files") MultipartFile[] files) {
        photoService.storeAll(categoryId, files);
        return "You successfully uploaded photos!";
    }

    @GetMapping("/meta")
    @ResponseBody
    public ResponseEntity<?> meta() throws IOException {
        File img = new File("C:\\Users\\anton\\OneDrive\\Desktop\\IMG_0152.jpg");
        photoService.readImageMeta(img);
        return ResponseEntity.ok().body("qq");
    }

//    @PostMapping("/")
//    public String uploadPhotosToCategory(@RequestParam ("files") request,
//                                         RedirectAttributes redirectAttributes) {
//
//
//        photoService.createPhotoParticipation(request);
//        redirectAttributes.addFlashAttribute("message",
//                "You successfully uploaded photos!");
//
//        return "redirect:/";
//    }
}