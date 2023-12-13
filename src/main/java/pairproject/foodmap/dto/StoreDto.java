package pairproject.foodmap.dto;

import lombok.Data;

@Data
public class StoreDto {

   private long storeId;
   private String storeName;
   private String address;
   private String phoneNumber;
   private String runtime;
   private double longitude;
   private double latitude;
   private int like_count;
   private int hate_count;
   private String categoryId;

}
