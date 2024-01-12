package com.barclays.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class CSRFService {

    public CsrfResponse requestToGetCsrfToken() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .cookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ALL))
                .build();

        String url = "http://localhost:8080/csrf";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic " + "QW5kcmV3OnBhc3MxMg==")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();

        CsrfResponse csrfResponse = objectMapper.readValue(response.body(), CsrfResponse.class);
        System.out.println(csrfResponse.getToken());
        return csrfResponse;


    }
}
