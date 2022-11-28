package pl.robocikd.shop.admin.common.utils;

import com.github.slugify.Slugify;
import org.apache.commons.io.FilenameUtils;

public class SlugifyUtils {
    private SlugifyUtils() {
    }

    public static String slugifyFileName(String fileName) {
        final String baseName = FilenameUtils.getBaseName(fileName);
        final Slugify slg = Slugify.builder().customReplacement("_", " ").build();
        final String slugifyBaseName = slg.slugify(baseName);
        return slugifyBaseName + "." + FilenameUtils.getExtension(fileName);
    }

    public static String slugifySlug(String slug) {
        Slugify slugify = Slugify.builder().customReplacement("_", "-").build();
        return slugify.slugify(slug);
    }
}
