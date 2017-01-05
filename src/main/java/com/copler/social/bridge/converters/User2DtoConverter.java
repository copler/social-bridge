package com.copler.social.bridge.converters;

import com.copler.social.bridge.dto.UserDto;
import com.restfb.types.User;

import java.util.List;
import java.util.stream.Collectors;

public class User2DtoConverter {

    public static List<UserDto> convert(final List<User> users) {
        return users
                .stream()
                .map(u -> new UserDto(u.getId(), u.getName(), u.getPicture().getUrl()))
                .collect(Collectors.toList());
    }

    private User2DtoConverter() {}
}
