package pl.robocikd.shop.admin.product.service;

import org.apache.commons.io.FilenameUtils;

import java.nio.file.Files;
import java.nio.file.Path;

class ExistingFileRenameUtil {

    private ExistingFileRenameUtil() {
    }

    public static String renameIfExists(Path uploadDir, String slugifyFileName) {
        if (Files.exists(uploadDir.resolve(slugifyFileName))) {
            return renameAndCheck(uploadDir, slugifyFileName);
        }
        return slugifyFileName;
    }

    private static String renameAndCheck(Path uploadDir, String slugifyFileName) {
        String newName = renameFileName(slugifyFileName);
        if (Files.exists(uploadDir.resolve(newName))) {
            newName = renameAndCheck(uploadDir, newName);
        }
        return newName;
    }

    private static String renameFileName(String slugifyFileName) {
        String baseName = FilenameUtils.getBaseName(slugifyFileName);
        String[] split = baseName.split("-(?=[0-9]+$)");
        int counter = split.length > 1 ? Integer.parseInt(split[1]) + 1 : 1;
        return split[0] + "-" + counter + "." + FilenameUtils.getExtension(slugifyFileName);
    }
}
