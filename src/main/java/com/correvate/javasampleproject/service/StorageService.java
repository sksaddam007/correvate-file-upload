package com.correvate.javasampleproject.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;


public interface StorageService {
    void init();
    void save(MultipartFile file);
    void deleteAll();
    void zipFiles(MultipartFile[] srcFiles, OutputStream outputStream) throws IOException;
}
