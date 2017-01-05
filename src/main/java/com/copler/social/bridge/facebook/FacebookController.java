package com.copler.social.bridge.facebook;

import com.copler.social.bridge.converters.User2DtoConverter;
import com.copler.social.bridge.dto.UserDto;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by Andrew on 12/22/2016.
 */
@RestController
@RequestMapping("/facebook")
public class FacebookController {

    @Value("${application.token}")
    private String token;

    private FacebookClient client;

    @RequestMapping(value = "/search/users", method = RequestMethod.GET)
    public List<UserDto> searchUsers(@RequestParam("query") String query) {
        return search(query);
    }

    private List<UserDto> search(String query) {
        Connection<User> publicSearch =
                client.fetchConnection("search", User.class,
                        Parameter.with("q", query),
                        Parameter.with("type", "user"),
                        Parameter.with("fields", "id,name,picture"));
        List<User> users = publicSearch.getData();
        return User2DtoConverter.convert(users);
    }

    @PostConstruct
    private void init() {
        client = new DefaultFacebookClient(token);
    }
}
