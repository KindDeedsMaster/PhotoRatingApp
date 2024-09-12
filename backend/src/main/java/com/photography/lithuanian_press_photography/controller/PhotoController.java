package com.photography.lithuanian_press_photography.controller;

import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;

import com.photography.lithuanian_press_photography.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", photoService.loadAll().map(
                        path -> MvcUriComponentsBuilder.fromMethodName(PhotoController.class,
                                "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));
        return "uploadForm";
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

    @PostMapping("/")
    public String handleFilesUpload(@RequestParam("files") MultipartFile[] files,
                                   RedirectAttributes redirectAttributes) {


        photoService.storeAll(files);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded photos!");

        return "redirect:/";
    }

    @GetMapping("/meta")
    @ResponseBody
    public ResponseEntity<?> meta() throws IOException {
        File img = new File("C:\\Users\\anton\\OneDrive\\Desktop\\IMG_0152.jpg");
        photoService.readImageMeta(img);
        return ResponseEntity.ok().body("qq");
    }
}