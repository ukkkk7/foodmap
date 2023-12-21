package pairproject.foodmap.dto;

import lombok.Data;

@Data
public class StoreDetailVO {


    private long storeId;
    private String storeName;
    private String address;
    private String phoneNum;
    private int likeCnt;
    private int hateCnt;
    private String categoryName;
    private long likeId;
    private long userId;
    private long bookmarkId;


}
