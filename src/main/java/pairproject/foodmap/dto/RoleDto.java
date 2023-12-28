package pairproject.foodmap.dto;

import lombok.Data;

@Data
public class RoleDto {

   private long roleId;
   private long userId;
   private String user;
   private String admin;

}
