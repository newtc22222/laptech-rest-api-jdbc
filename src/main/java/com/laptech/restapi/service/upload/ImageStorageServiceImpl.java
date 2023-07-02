package com.laptech.restapi.service.upload;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author Nhat Phi
 * @since 2022-12-01
 */
@Service
public class ImageStorageServiceImpl implements IStorageService {
    private static final String API_ABSOLUTE_PATH = "/api/v1/files/";
    private final Path storageFolder = Paths.get("uploads");

    public ImageStorageServiceImpl() {
        try {
            Files.createDirectories(storageFolder);
        } catch (IOException err) {
            throw new RuntimeException(err);
        }
    }

    private boolean isImageFile(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[]{"png", "jpg", "jpeg", "bmp"})
                .contains(Objects.requireNonNull(fileExtension).trim().toLowerCase());
    }

    @Override
    public String storeFile(MultipartFile file, HttpServletRequest request) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file!");
            }
            if (!isImageFile(file)) {
                throw new RuntimeException("You can only upload image file!");
            }
            float fileSizeInMegabytes = file.getSize() / 1_000_000f;
            if (fileSizeInMegabytes > 5.0) {
                throw new RuntimeException("File must be <= 5MBs");
            }

            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String generatorFileName = UUID.randomUUID().toString().replace("-", "");
            generatorFileName = generatorFileName + "." + fileExtension;
            Path destinationFilePath = this.storageFolder
                    .resolve(Paths.get(generatorFileName))
                    .normalize().toAbsolutePath();
            if (!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())) {
                throw new RuntimeException("Can not store file outside current directory!");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
            final String BASE_PATH = "http://" + request.getServerName() + ":" + request.getServerPort() + API_ABSOLUTE_PATH;
            return BASE_PATH + destinationFilePath.getFileName().toString();
        } catch (IOException err) {
            throw new RuntimeException(err);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public byte[] readFileContent(String fileName) {
        try {
            Path file = storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return StreamUtils.copyToByteArray(resource.getInputStream());
            } else {
                throw new RuntimeException("Cannot read this file!");
            }
        } catch (IOException err) {
            throw new RuntimeException(err);
        }
    }

    @Override
    public String getPath(String fileName) {
        try {
            Path file = storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return file.toAbsolutePath().toString();
            } else {
                throw new RuntimeException("Cannot read this file!");
            }
        } catch (IOException err) {
            throw new RuntimeException(err);
        }
    }

    @Override
    public void deleteFile() {

    }

    @Override
    public void deleteAllFiles() {

    }
}
