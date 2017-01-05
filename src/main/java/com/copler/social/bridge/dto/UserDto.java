package com.copler.social.bridge.dto;

public class UserDto {

    private final String id;
    private final String name;
    private final String picture;

    public UserDto(String id, String name, String picture) {
        this.id = id;
        this.name = name;
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }
}
