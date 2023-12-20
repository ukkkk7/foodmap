package pairproject.foodmap.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pairproject.foodmap.domain.Category;
import pairproject.foodmap.exception.DuplicateUserException;
import pairproject.foodmap.exception.exHandler.JsonParsingException;
import pairproject.foodmap.repository.CategoryMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @InjectMocks
    private CategoryService categoryService;
    @Mock
    private CategoryMapper categoryMapper;

    private Category createCategory() {
        return new Category("sample");
    }

    @Test
    @DisplayName("카테고리 생성 테스트")
    public void categoryAddTest() {
        //given
        Category category = createCategory();

        //when
        categoryService.createCategory(category);

        //then
        verify(categoryMapper, times(1)).save(category);
    }


    @Test
    @DisplayName("카테고리 삭제 테스트")
    public void categoryDeleteTest() {
        //given
        String categoryId = "test";

        //whenrc
        categoryService.deleteCategoryById(categoryId);

        //then
        verify(categoryMapper, times(1)).deleteById(categoryId);
    }

    @Test
    @DisplayName("중복이름 없는 경우_카테고리 중복이름 확인 테스트")
    public void duplicateFalse_NameCheckTest() {
        //given
        List<Category> categories = List.of(createCategory());
        String name = "sample2";
        when(categoryMapper.findAll()).thenReturn(categories);

        //when & then
        assertDoesNotThrow(() -> categoryService.duplicateNameCheck(name));
    }

    @Test
    @DisplayName("중복이름 있는 경우_카테고리 중복이름 확인 테스트")
    public void duplicateTrue_NameCheckTest() {
        //given
        List<Category> categories = List.of(createCategory());
        String name = "sample";
        when(categoryMapper.findAll()).thenReturn(categories);

        //when & then
        assertThrows(DuplicateUserException.class, () ->
                categoryService.duplicateNameCheck(name));

    }

    @Test
    @DisplayName("json 형식에서 카테고리 값 추출 테스트")
    public void getCategoryByJsonFormat() {
        //given
        String categoryId = "sample";
        String jsonData = "{\"categoryId\" : \"" + categoryId + "\"}";

        //when
        String returnJson = categoryService.getNameByJson(jsonData);

        //then
        assertEquals(returnJson, categoryId);
    }

    @Test
    @DisplayName("json 형식에서 카테고리 값 없는 경우 테스트")
    public void getCategoryByJsonFormat_notFound() {
        //given
        String categoryId = "sample";
        String jsonData = "{\"testId\" : \"" + categoryId + "\"}";

        //when & then
        assertThrows(JsonParsingException.class, () ->
                categoryService.getNameByJson(jsonData));
    }

    @Test
    @DisplayName("카테고리 목록 조회 테스트")
    public void categoryListTest() {
        //given
        List<Category> categories = new ArrayList<>();
        IntStream.range(0, 4)
                .forEach(i -> categories.add(createCategory()));
        when(categoryMapper.findAll()).thenReturn(categories);

        //when
        List<String> categoryNames = categoryService.getCategoryNames();

        //then
        assertEquals(categoryNames.size(), categories.size());
    }
}
