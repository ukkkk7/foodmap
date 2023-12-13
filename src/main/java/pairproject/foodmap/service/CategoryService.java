package pairproject.foodmap.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pairproject.foodmap.domain.Category;
import pairproject.foodmap.repository.CategoryMapper;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryMapper categoryMapper;
    public void createCategory(Category category) {
        duplicateNameCheck(category.getCategoryId());
        categoryMapper.save(category);
    }

    public void deleteCategoryById(String categoryId) {
        categoryMapper.deleteById(categoryId);
    }

    public void updateCategory(String categoryId, String newCategoryId) {
        String decoded = decodeName(newCategoryId);
        duplicateNameCheck(decoded);
        categoryMapper.edit(categoryId, decoded);
    }

    public void duplicateNameCheck(String name) {
        boolean isDuplicateCheck = getCategoryNames().stream()
                .filter(findName -> findName.equals(name))
                .findAny().isEmpty(); //중복이 아니면 true
        if (!isDuplicateCheck) { //중복이면 예외 발생
            throw new RuntimeException("이미 중복되는 이름이 있습니다.");
        }
    }

    public String decodeName(String name) {
        String json = URLDecoder.decode(name, StandardCharsets.UTF_8);
        return getTestByJson(json);
    }

    private static String getTestByJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(json);
            JsonNode categoryIdNode = rootNode.get("categoryId");

            if (categoryIdNode != null) {
                return categoryIdNode.asText();
            } else {
                throw new RuntimeException("categoryId 키를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            return "오류가 발생했습니다.";
        }
    }

    public List<String> getCategoryNames() {
        if (categoryMapper.findAll().isEmpty()) {
            return new ArrayList<>();
        }
        return categoryMapper.findAll().stream()
                .map(Category::getCategoryId)
                .toList();
    }
}
