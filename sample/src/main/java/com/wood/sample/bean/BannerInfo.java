package com.wood.sample.bean;

import com.wood.library.bean.Banner;

import lombok.Data;

@Data
public class BannerInfo implements Banner {
    private String imageUrl;
    private String title;
    private int id;

    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String getTitle() {
        return title;
    }
}
