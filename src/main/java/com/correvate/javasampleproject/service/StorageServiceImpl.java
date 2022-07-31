package com.correvate.javasampleproject.service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.correvate.javasampleproject.config.StorageProperties;
import com.correvate.javasampleproject.exception.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageServiceImpl implements StorageService {
    private final Path rootLocation;

    @Autowired
    public StorageServiceImpl(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }


    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.rootLocation.resolve(Objects.requireNonNull(file.getOriginalFilename())));
        } catch (Exception e) {
            throw new StorageException("Could not store the file. Error: " + e.getMessage());
        }
    }


    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void zipFiles(MultipartFile[] srcFiles, OutputStream os) throws IOException {
        deleteAll();
        init();
        ZipOutputStream zipOut = new ZipOutputStream(new BufferedOutputStream(os));
        for (MultipartFile srcFile : srcFiles) {
            Files.copy(srcFile.getInputStream(), this.rootLocation.resolve(Objects.requireNonNull(srcFile.getOriginalFilename())));
            File fileToZip = this.rootLocation.resolve(Objects.requireNonNull(srcFile.getOriginalFilename())).toFile();
            FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();
        }
        zipOut.close();
    }

}
