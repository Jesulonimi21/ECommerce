package com.example.jesulonimi.ecommerce.Model;

public class Products {
    String pName;
    String description;
    String image;
    String price,category,date,time,pid;

    public Products() {
    }

    public Products(String pName, String description, String image, String price, String category, String date, String time, String pid) {
        this.pName = pName;
        this.description = description;
        this.image = image;
        this.price = price;
        this.category = category;
        this.date = date;
        this.time = time;
        this.pid = pid;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
