package com.copler.social.bridge.facebook;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * Created by Andrew on 12/22/2016.
 */
@RestController
@RequestMapping("/resources/facebook")
public class FacebookController {

    private Facebook facebook;
    private ConnectionRepository connectionRepository;

    @Inject
    public FacebookController(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public User home(Model model) {
        Connection<Facebook> connection = connectionRepository.findPrimaryConnection(Facebook.class);
        return connection.getApi().userOperations().getUserProfile();
    }

}
