package com.literandltx.taskmcapp.controller;

import com.literandltx.taskmcapp.dto.labels.CreateLabelRequestDto;
import com.literandltx.taskmcapp.dto.labels.LabelResponseDto;
import com.literandltx.taskmcapp.dto.labels.UpdateLabelRequestDto;
import com.literandltx.taskmcapp.model.User;
import com.literandltx.taskmcapp.service.app.LabelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Label manager")
@RequiredArgsConstructor
@RequestMapping("/labels")
@RestController
public class LabelController {
    private final LabelService labelService;

    @Operation(summary = "Create new label for project")
    @PostMapping
    public LabelResponseDto createNewLabel(
            @RequestBody @Valid final CreateLabelRequestDto requestDto,
            @RequestParam final Long projectId,
            final Authentication authentication
    ) {
        final User user = (User) authentication.getPrincipal();

        return labelService.create(requestDto, projectId, user);
    }

    @Operation(summary = "Retrieve all label in project")
    @GetMapping
    public List<LabelResponseDto> retrieveLabels(
            @RequestParam final Long projectId,
            final Authentication authentication,
            final Pageable pageable
    ) {
        final User user = (User) authentication.getPrincipal();

        return labelService.findAll(projectId, user, pageable);
    }

    @Operation(summary = "Retrieve label by id in project")
    @GetMapping("/{id}")
    public LabelResponseDto retrieveLabelById(
            @PathVariable final Long id,
            @RequestParam final Long projectId,
            final Authentication authentication
    ) {
        final User user = (User) authentication.getPrincipal();

        return labelService.findById(id, projectId, user);
    }

    @Operation(summary = "Update label in project")
    @PutMapping
    public LabelResponseDto updateLabel(
            @RequestBody @Valid final UpdateLabelRequestDto requestDto,
            @RequestParam final Long id,
            @RequestParam final Long projectId,
            final Authentication authentication
    ) {
        final User user = (User) authentication.getPrincipal();

        return labelService.updateById(requestDto, id, projectId, user);
    }

    @Operation(summary = "Delete label from project")
    @DeleteMapping
    public void deleteLabel(
            @RequestParam final Long id,
            @RequestParam final Long projectId,
            final Authentication authentication
    ) {
        final User user = (User) authentication.getPrincipal();

        labelService.deleteById(id, projectId, user);
    }
}
