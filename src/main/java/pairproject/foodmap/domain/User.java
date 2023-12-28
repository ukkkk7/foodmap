package pairproject.foodmap.domain;

import lombok.Getter;

@Getter
public class User {
    private long userId;
    private String mail;
    private String password;
    private String name;
    private String birth;
    private String role;
    private Grade grade;

    public User() {
        this.grade = Grade.LEV1;
        this.role = "USER";
    }
    public void updateGrade(Grade grade) {
        this.grade = grade;
    }
}
