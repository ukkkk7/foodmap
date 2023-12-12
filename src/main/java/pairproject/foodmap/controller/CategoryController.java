package pairproject.foodmap.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pairproject.foodmap.domain.Category;
import pairproject.foodmap.service.CategoryService;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/category")
    public ResponseEntity<String> categoryCreate(@RequestBody Category category) {
        categoryService.createCategory(category);
        return new ResponseEntity<>("추가되었습니다.", HttpStatus.OK);
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<String> categoryDelete(@PathVariable long categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return new ResponseEntity<>("삭제되었습니다", HttpStatus.OK);
    }

    @PatchMapping("/categories/{categoryId}")
    public ResponseEntity<String> categoryUpdate(@PathVariable long categoryId, @RequestBody Category category) {
        categoryService.updateCategory(categoryId, category);
        return new ResponseEntity<>("수정되었습니다", HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> categoryList() {
        List<String> categoryNames = categoryService.getCategoryNames();
        return new ResponseEntity<>(categoryNames, HttpStatus.OK);
    }
}