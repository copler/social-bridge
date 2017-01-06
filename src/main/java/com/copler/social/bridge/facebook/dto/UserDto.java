package com.copler.social.bridge.facebook.dto;

/**
 * Created by Andrew on 1/5/2017.
 */
public class UserDto {

    private String id;
    private String name;
    private String picture;
    private String facebookId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        if (!id.equals(userDto.id)) return false;
        if (!name.equals(userDto.name)) return false;
        if (picture != null ? !picture.equals(userDto.picture) : userDto.picture != null) return false;
        return facebookId != null ? facebookId.equals(userDto.facebookId) : userDto.facebookId == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (picture != null ? picture.hashCode() : 0);
        result = 31 * result + (facebookId != null ? facebookId.hashCode() : 0);
        return result;
    }
}
