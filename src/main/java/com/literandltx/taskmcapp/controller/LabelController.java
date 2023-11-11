package com.literandltx.taskmcapp.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/labels")
@RestController
public class LabelController {
    @PostMapping
    public void createNewLabel() {

    }

    @GetMapping
    public void retrieveLabels() {

    }

    @PutMapping("/{id}")
    public void updateLabel(@PathVariable Long id) {

    }

    @DeleteMapping("/{id}")
    public void deleteLabel(@PathVariable Long id) {

    }
}
