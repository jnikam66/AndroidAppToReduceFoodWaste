package com.jyoti.NoFoodWastage;

import android.location.Location;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Post implements Serializable {
    String postId;
    String title;
    String description;
    String foodCategory;
    Integer numOfServes;
    LatLong location;
    String imageUrl;
    String postedBy;



    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }
    public Post(String postId, String foodCategory, Integer numOfServes, LatLong location,String imageUrl, String title, String description, String postedBy) {
        this.postId=postId;
        this.foodCategory=foodCategory;
        this.numOfServes=numOfServes;
        this.location=location;
        this.imageUrl=imageUrl;
        this.title = title;
        this.description = description;
        this.postedBy = postedBy;
    }


    public String getPostId() {
        return postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory = foodCategory;
    }

    public Integer getNumOfServes() {
        return numOfServes;
    }

    public void setNumOfServes(Integer numOfServes) {
        this.numOfServes = numOfServes;
    }

   public LatLong getLocation() {
        return location;
    }

    public void setLocation(LatLong location) {
        this.location = location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }



}


