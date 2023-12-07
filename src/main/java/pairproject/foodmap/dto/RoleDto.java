package pairproject.foodmap.dto;

import lombok.Data;

@Data
public class RoleDto {

   private long role_id;
   private long user_id;
   private String user;
   private String admin;

}
