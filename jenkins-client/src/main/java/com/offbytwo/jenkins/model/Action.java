package com.offbytwo.jenkins.model;

import java.io.IOException;

/**
 * Created by msdg on 2019/1/8.
 * Look, there is a moon.
 */
public class Action extends BaseModel{

    private String name;
    private String url;
    private String color;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
