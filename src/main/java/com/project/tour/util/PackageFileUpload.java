package com.project.tour.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class PackageFileUpload {

    public static void saveFile1(String uploadDir1, String fileName1,
                                MultipartFile multipartFile1) throws IOException {

        Path uploadPath1 = Paths.get(uploadDir1);

        if (!Files.exists(uploadPath1)) {
            Files.createDirectories(uploadPath1);
        }

        try (InputStream inputStream = multipartFile1.getInputStream()) {
            Path filePath1 = uploadPath1.resolve(fileName1);

            Files.copy(inputStream, filePath1, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName1, ioe);
        }
    }

    public static void saveFile2(String uploadDir2, String fileName2,
                                MultipartFile multipartFile2) throws IOException {
        Path uploadPath2 = Paths.get(uploadDir2);

        if (!Files.exists(uploadPath2)) {
            Files.createDirectories(uploadPath2);
        }

        try (InputStream inputStream = multipartFile2.getInputStream()) {
            Path filePath2 = uploadPath2.resolve(fileName2);

            Files.copy(inputStream, filePath2, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName2, ioe);
        }
    }
}
