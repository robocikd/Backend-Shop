package pl.robocikd.shop.admin.product.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import pl.robocikd.shop.admin.common.utils.SlugifyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AdminProductImageService {

    @Value("${app.uploadDir}")
    private String uploadDir;

    public String uploadImage(String fileName, InputStream inputStream) {
        String slugifyFileName = SlugifyUtils.slugifyFileName(fileName);
        slugifyFileName = ExistingFileRenameUtil.renameIfExists(Path.of(uploadDir), slugifyFileName);
        Path filePath = Paths.get(uploadDir).resolve(slugifyFileName);
        try (OutputStream outputStream = Files.newOutputStream(filePath)) {
            inputStream.transferTo(outputStream);
        } catch (IOException e) {
            throw new RuntimeException("Nie można zapisać pliku", e);
        }
        return slugifyFileName;
    }

    public Resource serveFiles(String fileName) {
        FileSystemResourceLoader fileSystemResourceLoader = new FileSystemResourceLoader();
        return fileSystemResourceLoader.getResource(uploadDir + fileName);
    }
}
