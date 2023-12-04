package com.digitalcomanetwork.kosterme.fileuploadservice.ui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileUploadController {
    @Autowired
    private Environment env;

    @GetMapping("/status/check")
    public String getStatus() {
        return "Working on port " + env.getProperty("local.server.port");
    }
}
