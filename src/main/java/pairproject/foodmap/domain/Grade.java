package pairproject.foodmap.domain;

import lombok.Getter;

@Getter
public enum Grade {
    LEV1("맛린이"),
    LEV2("맛초보"),
    LEV3("맛중수"),
    LEV4("맛고수"),
    LEV5("맛잘알");

    private final String name;
    
    Grade(String name) {
        this.name = name;
    }
}
