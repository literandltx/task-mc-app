package com.literandltx.taskmcapp.controller;

import com.literandltx.taskmcapp.dto.labels.CreateLabelRequestDto;
import com.literandltx.taskmcapp.dto.labels.LabelResponseDto;
import com.literandltx.taskmcapp.dto.labels.UpdateLabelRequestDto;
import com.literandltx.taskmcapp.model.User;
import com.literandltx.taskmcapp.service.LabelService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/labels")
@RestController
public class LabelController {
    private final LabelService labelService;

    @PostMapping
    public LabelResponseDto createNewLabel(
            @RequestBody @Valid CreateLabelRequestDto requestDto,
            @RequestParam Long projectId,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        return labelService.create(requestDto, projectId, user);
    }

    @GetMapping
    public List<LabelResponseDto> retrieveLabels(
            @RequestParam Long projectId,
            Authentication authentication,
            Pageable pageable
    ) {
        User user = (User) authentication.getPrincipal();

        return labelService.findAll(projectId, user, pageable);
    }

    @GetMapping("/{id}")
    public LabelResponseDto retrieveLabelsById(
            Long id,
            @RequestParam Long projectId,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        return labelService.findById(id, projectId, user);
    }

    @PutMapping
    public LabelResponseDto updateLabel(
            @RequestBody @Valid UpdateLabelRequestDto requestDto,
            @RequestParam Long id,
            @RequestParam Long projectId,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        return labelService.updateById(requestDto, id, projectId, user);
    }

    @DeleteMapping
    public void deleteLabel(
            @RequestParam Long id,
            @RequestParam Long projectId,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        labelService.deleteById(id, projectId, user);
    }

    @PatchMapping
    public void assignLabelToTask() {
        throw new UnsupportedOperationException("Not now");
    }
}
