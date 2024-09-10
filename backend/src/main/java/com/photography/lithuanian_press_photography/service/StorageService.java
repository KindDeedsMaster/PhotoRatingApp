package com.photography.lithuanian_press_photography.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Stream;
import java.nio.file.Path;

public interface StorageService {
    void init();
    void store(MultipartFile files);
    Stream<Path> loadAll();
    Path load (String filename);
    Resource loadAsResource(String filename);
    void deleteAll();
    void storeAll(MultipartFile[] files);
}
