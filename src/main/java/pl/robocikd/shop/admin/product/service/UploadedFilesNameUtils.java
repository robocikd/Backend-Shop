package pl.robocikd.shop.admin.product.service;

import com.github.slugify.Slugify;
import org.apache.commons.io.FilenameUtils;

class UploadedFilesNameUtils {
    private UploadedFilesNameUtils() {
    }

    public static String slugifyFileName(String fileName) {
        final String baseName = FilenameUtils.getBaseName(fileName);
        final Slugify slg = Slugify.builder().customReplacement("_", " ").build();
        final String slugifyBaseName = slg.slugify(baseName);
        return slugifyBaseName + "." + FilenameUtils.getExtension(fileName);
    }
}
