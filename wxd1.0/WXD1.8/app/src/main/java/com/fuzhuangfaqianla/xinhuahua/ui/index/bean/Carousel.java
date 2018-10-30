package com.fuzhuangfaqianla.xinhuahua.ui.index.bean;

/**
 * Created by xiongchang on 2017/9/21.
 */
public class Carousel {
    private Integer carouselPicId;
    private String carouselPicUrl;
    private String carouselPicNote;
    private String redirectUrl;

    public Integer getCarouselPicId() {
        return carouselPicId;
    }

    public void setCarouselPicId(Integer carouselPicId) {
        this.carouselPicId = carouselPicId;
    }

    public String getCarouselPicUrl() {
        return carouselPicUrl;
    }

    public void setCarouselPicUrl(String carouselPicUrl) {
        this.carouselPicUrl = carouselPicUrl;
    }

    public String getCarouselPicNote() {
        return carouselPicNote;
    }

    public void setCarouselPicNote(String carouselPicNote) {
        this.carouselPicNote = carouselPicNote;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @Override
    public String toString() {
        return "Carousel{" +
                "carouselPicId=" + carouselPicId +
                ", carouselPicUrl='" + carouselPicUrl + '\'' +
                ", carouselPicNote='" + carouselPicNote + '\'' +
                ", redirectUrl='" + redirectUrl + '\'' +
                '}';
    }
}