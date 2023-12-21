package pairproject.foodmap.dto;

import lombok.Data;

@Data
public class UserDto {

   private long userId;
    //등급 외래키
   private long gradeId;
   private String mail;
   private String name;
   private String birth;
   private String role;


}
