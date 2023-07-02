package com.laptech.restapi.service.upload;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * @since 2022-12-01
 */
public interface IStorageService {
    String storeFile(MultipartFile file, HttpServletRequest request);

    Stream<Path> loadAll();

    byte[] readFileContent(String fileName);

    String getPath(String fileName);

    void deleteFile();

    void deleteAllFiles();
}
