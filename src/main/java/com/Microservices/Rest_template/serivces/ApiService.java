package com.Microservices.Rest_template.serivces;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.Microservices.Rest_template.entity.User;

@Service
public class ApiService {

    private final RestTemplate restTemplate;

    private String sessionId;

    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void getSessionId() {
        ResponseEntity<String> res = restTemplate.exchange(
                "http://94.198.50.185:7081/api/users",
                HttpMethod.GET,
                null,
                String.class);
        List<String> cookies = res.getHeaders().get("Set-Cookie");
        this.sessionId = cookies.get(0).split(";")[0].split("=")[1];
    }

    public String getPartCode(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "JSESSIONID=" + sessionId);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://94.198.50.185:7081/api/users",
                HttpMethod.POST,
                entity,
                String.class);
        return response.getBody();
    }

    public String deleteUser(Long userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "JSESSIONID=" + sessionId);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://94.198.50.185:7081/api/users/" + userId,
                HttpMethod.DELETE,
                entity,
                String.class);
        return response.getBody();
    }

}
