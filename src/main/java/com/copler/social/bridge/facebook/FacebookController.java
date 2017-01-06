package com.copler.social.bridge.facebook;

import com.copler.social.bridge.facebook.dto.UserDto;
import com.copler.social.bridge.facebook.services.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.ImageType;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.User;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Andrew on 12/22/2016.
 */
@RestController
@RequestMapping("/facebook")
public class FacebookController {

    private Facebook facebook;
    private ConnectionRepository connectionRepository;

    @Autowired
    private UserDataService userDataService;

    @Inject
    public FacebookController(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping(value = "/search/users", method = RequestMethod.GET)
    public PagedList<User> searchUsers(@RequestParam("query") String query) {
        // return facebook.userOperations().search(query);
        return search(query);
    }

    private PagedList<User> search(String query) {
        MultiValueMap<String, String> queryMap = new LinkedMultiValueMap<String, String>();
        queryMap.add("q", query);
        queryMap.add("type", "user");
        return facebook.fetchConnections("search", null, User.class, queryMap,
                "id, name, picture");
    }

    @RequestMapping(value = "/search/users", method = RequestMethod.POST)
    public List<UserDto> searchUsers(@RequestBody List<UserDto> userDtos) {
        List<UserDto> users = new ArrayList<>();
        for (UserDto userDto : userDtos) {
            UserDto userDtoCached = userDataService.getUser(userDto);
            if (!StringUtils.isEmpty(userDtoCached.getFacebookId())) {
                users.add(userDtoCached);
                continue;
            }
            PagedList<User> facebookUsers = search(userDto.getName());
            for (User facebookUser : facebookUsers) {
                if (facebookUser.getName().equals(userDto.getName())) {
                    userDto.setFacebookId(facebookUser.getId());
                    userDto.setPicture(getPictureURL(facebookUser));
                    users.add(userDto);
                    userDataService.putUser(userDto);
                    break;
                }
            }
        }
        return users;
    }

    private String getPictureURL(final User facebookUser) {
        String url = "";
        Map<String, Object> extraData = facebookUser.getExtraData();
        if (!extraData.isEmpty()) {
            Map picture = (Map) extraData.get("picture");
            Map data = (Map) picture.get("data");
            url = (String) data.get("url");
        }
        return url;
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User user(@PathVariable("id") String objectId) {
        Connection<Facebook> connection = connectionRepository.findPrimaryConnection(Facebook.class);
        // User user = connection.getApi().userOperations().getUserProfile();
        return facebook.fetchObject(objectId, User.class, PROFILE_FIELDS);
    }

    @RequestMapping(value = "/user/{id}/picture", method = RequestMethod.GET)
    public byte[] userPicture(@PathVariable("id") String userId, @RequestParam("type") String type) {
        return facebook.userOperations().getUserProfileImage(userId, selectUsePictureType(type));
    }

    private static ImageType selectUsePictureType(String imageTypeStr) {
        if (imageTypeStr == null) return ImageType.NORMAL;
        String imageTypeStrUpperCase = imageTypeStr.toUpperCase();
        for (ImageType imageType : ImageType.values()) {
            if (imageType.name().equals(imageTypeStrUpperCase))
                return imageType;
        }
        return ImageType.NORMAL;
    }

    private static final String[] PROFILE_FIELDS = {
            "id", "about", "age_range", "birthday", "context", "cover", "currency", "devices", "education", "email",
            "favorite_athletes", "favorite_teams", "first_name", "gender", "hometown", "inspirational_people", "installed", "install_type",
            "is_verified", "languages", "last_name", "link", "locale", "location", "meeting_for", "middle_name", "name", "name_format",
            "political", "quotes", "payment_pricepoints", "relationship_status", "religion", "security_settings", "significant_other",
            "sports", "test_group", "timezone", "third_party_id", "updated_time", "verified", "viewer_can_send_gift",
            "website", "work"
    };

}
