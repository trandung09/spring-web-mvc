package com.tvd.petcare.utils;

import org.apache.commons.io.FilenameUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class FileUtils {

    private static final String UPLOADS_FOLDER = "uploads/images";

    private static final List<String> IMAGE_FILE_EXTENSIONS = List.of("png", "jpg", "jpeg", "gif", "webp");

    public static void deleteFile(String filename) throws IOException {

        Path uploadDir = Paths.get(UPLOADS_FOLDER);
        Path filePath = uploadDir.resolve(filename);

        if (Files.exists(filePath)) {
            Files.delete(filePath);
        } else {
            System.out.println("File not found: " + filename);
        }
    }

    public static boolean isNonImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return true;
        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String extension = FilenameUtils.getExtension(fileName);
        return extension.isBlank() || !IMAGE_FILE_EXTENSIONS.contains(extension.toLowerCase());
    }

    public static String onUpLoadFolder(MultipartFile file) throws IOException {
        if (isNonImageFile(file)) {
            throw new IOException("Invalid image format ");
        }

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String extension = FilenameUtils.getExtension(fileName);

        String uniqueFileName = UUID.randomUUID().toString() + System.nanoTime() + "." + extension;

        Path uploadDir = Paths.get(UPLOADS_FOLDER);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        Path filePath = Paths.get(uploadDir.toString(), uniqueFileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }
}
