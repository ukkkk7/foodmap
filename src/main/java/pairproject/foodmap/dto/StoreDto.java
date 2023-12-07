package pairproject.foodmap.dto;

import lombok.Data;

@Data
public class StoreDto {

   private long store_id;
   private String store_name;
   private String address;
   private String phone_number;
   private String runtime;
   private double longtitude;
   private double latitude;
   private int like_count;
   private int hate_count;
   private long category_id;

}
