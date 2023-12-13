package pairproject.foodmap.domain;

import lombok.Getter;

@Getter
public class Grade {
    private long gradeId;
    private String name;
    private int rangeMin;
    private int rangeMax;
}
