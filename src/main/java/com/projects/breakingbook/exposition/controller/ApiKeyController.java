package com.projects.breakingbook.exposition.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/key")
@CrossOrigin(origins = "${breaking-book.app.client}")
public class ApiKeyController {

    @Value("${breaking-book.app.apiKey}")
    private String apikey;

    @GetMapping("")
    public String getApi() {
        return this.apikey;
    }
}
