package pl.robocikd.shop.admin.common.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SlugifyUtilsTest {

    @ParameterizedTest
    @CsvSource({
            "test test.png, test-test.png",
            "Hello World.jpg, hello-world.jpg",
            "ąćęłńśźż.png, acelnszz.png",
            "Produkt-1.png, produkt-1.png",
            "---.png, .png",
            "a-.png, a.png",
            "Produkt - 1.png, produkt-1.png",
            "Produkt   1.png, produkt-1.png",
            "Produkt__1-.png, produkt-1.png",
            "Produkt-_-_1--.png, produkt-1.png",
            "Produkt__1-- -.png, produkt-1.png",
            "Produkt dwa__1.png, produkt-dwa-1.png",
            "Produkt dwa__1.png, produkt-dwa-1.png"

    })
    void shouldSlugifyFileName(String in, String out) {
        String slugifyIn = SlugifyUtils.slugifyFileName(in);
        assertEquals(out, slugifyIn);
    }
}