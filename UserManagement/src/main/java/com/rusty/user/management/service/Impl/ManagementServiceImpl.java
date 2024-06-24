package com.rusty.user.management.service.Impl;

import com.rusty.user.management.domain.User;
import com.rusty.user.management.service.ManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ManagementServiceImpl implements ManagementService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String USER_BASE_URL = "http://localhost:8181/users";

    @Override
    public User createUser(User user) {
        ResponseEntity<User> response = restTemplate.postForEntity(USER_BASE_URL, user, User.class);
        return response.getBody();
    }

    @Override
    public List<User> getUsers() {
        ResponseEntity<List<User>> response = restTemplate.exchange(USER_BASE_URL,HttpMethod.GET,null,new ParameterizedTypeReference<List<User>>() {});
        return response.getBody();
    }

    @Override
    public User getUserById(Long id) {
        return restTemplate.getForObject(USER_BASE_URL + "/id/" + id, User.class);
    }

    @Override
    public User updateUser(Long id, User user) {
        restTemplate.put(USER_BASE_URL + "/" + id, user);
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        restTemplate.delete(USER_BASE_URL + "/" + id);
    }

    @Override
    public User getUserByEmail(String email) {
        String url = USER_BASE_URL + "/email/{email}";
        URI uri = UriComponentsBuilder.fromUriString(url).buildAndExpand(email).toUri();
        return restTemplate.getForObject(USER_BASE_URL + "/email/" + email, User.class);
    }

//    @Override
//    public User partialUpdateUser(Long id, User partialUser) {
//        String patchUrl = USER_BASE_URL + "/" + id;
//
//        Map<String, Object> updates = getStringObjectMap(partialUser);
//
//        // Create an HttpEntity with the updates map
//        HttpEntity<Map<String, Object>> request = new HttpEntity<>(updates);
//
//        // Set headers for the PATCH request if needed (not necessary in this example)
//
//        // Send PATCH request
//        ResponseEntity<User> response = restTemplate.exchange(patchUrl, HttpMethod.PATCH, request, User.class);
//
//        // Return the updated User object received from the server
//        return response.getBody();
//    }
//
//    private static Map<String, Object> getStringObjectMap(User partialUser) {
//        Map<String, Object> updates = new HashMap<>();
//        if (!StringUtils.isEmpty(partialUser.getName())) {
//            updates.put("name", partialUser.getName());
//        }
//        if (!StringUtils.isEmpty(partialUser.getEmail())) {
//            updates.put("email", partialUser.getEmail());
//        }
//        if (!StringUtils.isEmpty(partialUser.getAddress())) {
//            updates.put("address", partialUser.getAddress());
//        }
//        if (!StringUtils.isEmpty(partialUser.getPhone())) {
//            updates.put("phone", partialUser.getPhone());
//        }
//        return updates;
//    }
    @Override
    public User partialUpdateUser(Long id, User partialUser) {
        String patchUrl = USER_BASE_URL + "/" + id;

        Map<String, Object> updates = getStringObjectMap(partialUser);

        // Create an HttpEntity with the updates map
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(updates);

        try {
            // Send PATCH request
            ResponseEntity<User> response = restTemplate.exchange(patchUrl, HttpMethod.PATCH, request, User.class);
            return response.getBody();
        } catch (Exception e) {
            // Log the error details and rethrow if necessary
            System.err.println("Error during PATCH request: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

        private static Map<String, Object> getStringObjectMap(User partialUser) {
            Map<String, Object> updates = new HashMap<>();
            if (partialUser.getName() != null) {
                updates.put("name", partialUser.getName());
            }
            if (partialUser.getEmail() != null) {
                updates.put("email", partialUser.getEmail());
            }
            if (partialUser.getAddress() != null) {
                updates.put("address", partialUser.getAddress());
            }
            if (partialUser.getPhone() != null) {
                updates.put("phone", partialUser.getPhone());
            }
            return updates;
        }
}
