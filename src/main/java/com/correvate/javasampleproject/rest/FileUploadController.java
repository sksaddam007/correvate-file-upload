package com.correvate.javasampleproject.rest;


import com.correvate.javasampleproject.exception.StorageFileNotFoundException;
import com.correvate.javasampleproject.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/files")
public class FileUploadController {
    private final StorageService storageService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @RequestMapping(value = "/zipfiles", method = RequestMethod.POST, produces="application/zip", consumes = "multipart/form-data")
    public ResponseEntity<StreamingResponseBody> convertToZip(@RequestParam(value = "files", required = false) MultipartFile[] files) throws IOException {
        logger.info("downloading all files");
        try {
            StreamingResponseBody stream = output -> storageService.zipFiles(files, output);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=merged.zip")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(stream);
        } catch (Exception e) {
            logger.error(e.toString());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
