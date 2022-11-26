package pl.robocikd.shop.admin.category.controller;

import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.robocikd.shop.admin.category.controller.dto.AdminCategoryDto;
import pl.robocikd.shop.admin.category.controller.service.AdminCategoryService;
import pl.robocikd.shop.admin.category.model.AdminCategory;

import java.util.List;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    public static final Long EMPTY_ID = null;
    private final AdminCategoryService adminProductService;

    @GetMapping
    public List<AdminCategory> getCategories() {
        return adminProductService.getCategories();
    }

    @GetMapping("/{id}")
    public AdminCategory getCategory(@PathVariable Long id) {
        return adminProductService.getCategory(id);
    }

    @PostMapping
    public AdminCategory createCategory(@RequestBody AdminCategoryDto adminCategoryDto) {
        return adminProductService.createCategory(mapToAdminCategory(EMPTY_ID, adminCategoryDto));
    }

    private AdminCategory mapToAdminCategory(Long id, AdminCategoryDto adminCategoryDto) {
        return AdminCategory.builder()
                .id(id)
                .name(adminCategoryDto.getName())
                .description(adminCategoryDto.getDescription())
                .slug(slugifyCategoryName(adminCategoryDto.getSlug()))
                .build();
    }

    private String slugifyCategoryName(String slug) {
        Slugify slugify = Slugify.builder().customReplacement("_", "-").build();
        return slugify.slugify(slug);
    }

    @PutMapping("/{id}")
    public AdminCategory updateCategory(@PathVariable Long id, @RequestBody AdminCategoryDto adminCategoryDto) {
        return adminProductService.updateCategory(mapToAdminCategory(id, adminCategoryDto));
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        adminProductService.delete(id);
    }
}
