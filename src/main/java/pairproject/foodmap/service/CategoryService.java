package pairproject.foodmap.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pairproject.foodmap.domain.Category;
import pairproject.foodmap.repository.CategoryMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryMapper categoryMapper;
    public void createCategory(Category category) {
        duplicateNameCheck(category.getName());
        categoryMapper.save(category);
    }

    public void deleteCategoryById(long categoryId) {
        categoryMapper.deleteById(categoryId);
    }

    public void updateCategory(long categoryId, Category category) {
        duplicateNameCheck(category.getName());
        categoryMapper.edit(categoryId, category);
    }

    public void duplicateNameCheck(String name) {
        boolean isDuplicateCheck = categoryMapper.findNameAll().stream()
                .filter(findName -> findName.equals(name))
                .findAny().isEmpty(); //중복이 아니면 true
        if (!isDuplicateCheck) { //중복이면 예외 발생
            throw new RuntimeException("이미 중복되는 이름이 있습니다.");
        }
    }

    public List<String> getCategoryNames() {
        return categoryMapper.findNameAll();
    }
}
