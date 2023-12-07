package pairproject.foodmap.dto;

import lombok.Data;

@Data
public class UserDto {

   private long user_id;
    //등급 외래키
   private long grade_id;
   private String mail;
   private String name;
   private String birth;
   private String role;


}
