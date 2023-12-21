package pairproject.foodmap.dto;

import lombok.Data;

@Data
public class StoreDto {

   private long storeId;
   private String storeName;
   private String address;
   private String phoneNum;
   private double longitude;
   private double latitude;
   private int likeCnt;
   private int hateCnt;
   private String categoryName;

}
