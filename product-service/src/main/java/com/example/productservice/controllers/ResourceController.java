package com.example.productservice.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping(path = "/pictures")
@Slf4j
public class ResourceController {

    @GetMapping(path = "/{fileName}")
    @ResponseBody
    public ResponseEntity<Resource> getPictureByFileName(@PathVariable("fileName") String fileName) {
        log.info("getPictureByFileName " + fileName);
        String appFolder = System.getProperty("user.dir");
        log.info("App folder " + appFolder);
        Resource fileSystemResource = new FileSystemResource(appFolder + File.separator + "uploads" + File.separator + fileName);
        if(fileSystemResource.exists()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(fileSystemResource);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
