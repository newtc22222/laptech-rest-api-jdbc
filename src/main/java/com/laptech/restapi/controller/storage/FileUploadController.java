package com.laptech.restapi.controller.storage;

import com.laptech.restapi.dto.response.BaseResponse;
import com.laptech.restapi.dto.response.DataResponse;
import com.laptech.restapi.service.upload.IStorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @since 2022-12-01
 */
@Api(tags = "Upload images", value = "File upload controller")
@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class FileUploadController {
    @Autowired
    private IStorageService storageService;

    @ApiOperation(value = "Upload and save an image to server", response = DataResponse.class)
    @PostMapping("/uploads")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<DataResponse> uploadImageFile(@RequestParam("file") MultipartFile file) {
        String generateFileName = storageService.storeFile(file);
        return DataResponse.success("Upload image", generateFileName);
    }

    @ApiOperation(value = "Upload and save multiple images to server", response = DataResponse.class)
    @PostMapping("/uploads-multiple")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<DataResponse> uploadMultipleImageFiles(@RequestParam("files") MultipartFile[] files) {
        List<String> generateFileNameList = new ArrayList<>();
        Arrays.stream(files).forEach(file -> {
            String generateFileName = storageService.storeFile(file);
            generateFileNameList.add(generateFileName);
        });
        return DataResponse.success("Upload multiple image", generateFileNameList);
    }

    @ApiOperation(value = "Show image with file name (and file type)", response = ResponseEntity.class)
    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String filename) {
        try {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(storageService.readFileContent(filename));
        } catch (Exception err) {
            return ResponseEntity.noContent().build();
        }
    }

    @ApiOperation(value = "Show path of file in system", response = String.class)
    @GetMapping("/files/{filename:.+}/path")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<String> getPathOfFile(@PathVariable String filename) {
        return ResponseEntity
                .ok()
                .body(storageService.getPath(filename));
    }
}
