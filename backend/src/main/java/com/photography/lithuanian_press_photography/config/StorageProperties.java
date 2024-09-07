package com.photography.lithuanian_press_photography.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "C:\\Users\\anton\\OneDrive\\Desktop\\PhotoRatingApp\\files";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
