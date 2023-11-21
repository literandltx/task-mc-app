package com.literandltx.taskmcapp.service;

import com.literandltx.taskmcapp.dto.labels.CreateLabelRequestDto;
import com.literandltx.taskmcapp.dto.labels.LabelResponseDto;
import com.literandltx.taskmcapp.dto.labels.UpdateLabelRequestDto;
import com.literandltx.taskmcapp.mapper.LabelMapper;
import com.literandltx.taskmcapp.model.Label;
import com.literandltx.taskmcapp.model.Project;
import com.literandltx.taskmcapp.model.User;
import com.literandltx.taskmcapp.repository.LabelRepository;
import com.literandltx.taskmcapp.repository.ProjectRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LabelServiceImpl implements LabelService {
    private final LabelMapper labelMapper;
    private final LabelRepository labelRepository;
    private final ProjectRepository projectRepository;

    @Override
    public LabelResponseDto create(
            CreateLabelRequestDto requestDto,
            Long projectId,
            User user
    ) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new RuntimeException("Cannot find project with id: " + projectId));
        if (!Objects.equals(project.getUser().getId(), user.getId())) {
            throw new RuntimeException("User do not have project with id: " + projectId);
        }
        Label model = labelMapper.toModel(requestDto);
        model.setProject(project);

        return labelMapper.toDto(labelRepository.save(model));
    }

    @Override
    public List<LabelResponseDto> findAll(
            Long projectId,
            User user,
            Pageable pageable
    ) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new RuntimeException("Cannot find project with id: " + projectId));
        if (!Objects.equals(project.getUser().getId(), user.getId())) {
            throw new RuntimeException("User do not have project with id: " + projectId);
        }

        return labelRepository.findAllByProjectId(pageable, projectId).stream()
                .map(labelMapper::toDto)
                .toList();
    }

    @Override
    public LabelResponseDto findById(
            Long id,
            Long projectId,
            User user
    ) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new RuntimeException("Cannot find project with id: " + projectId));
        if (!Objects.equals(project.getUser().getId(), user.getId())) {
            throw new RuntimeException("User do not have project with id: " + projectId);
        }

        Label label = labelRepository.findByIdAndProjectId(id, projectId).orElseThrow(
                () -> new RuntimeException("Cannot find label with id: " + id));

        return labelMapper.toDto(label);
    }

    @Override
    public LabelResponseDto updateById(
            UpdateLabelRequestDto requestDto,
            Long id,
            Long projectId,
            User user
    ) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new RuntimeException("Cannot find project with id: " + projectId));
        if (!Objects.equals(project.getUser().getId(), user.getId())) {
            throw new RuntimeException("User do not have project with id: " + projectId);
        }

        if (!labelRepository.existsByIdAndProjectId(id, projectId)) {
            throw new RuntimeException("Cannot update label with id: " + id);
        }

        Label model = labelMapper.toModel(requestDto);
        model.setId(id);
        model.setProject(project);

        return labelMapper.toDto(model);
    }

    @Override
    public void deleteById(
            Long id,
            Long projectId,
            User user
    ) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new RuntimeException("Cannot find project with id: " + projectId));
        if (!Objects.equals(project.getUser().getId(), user.getId())) {
            throw new RuntimeException("User do not have project with id: " + projectId);
        }

        if (!labelRepository.existsByIdAndProjectId(id, projectId)) {
            throw new RuntimeException("Cannot delete label with id: " + id);
        }

        labelRepository.deleteById(id);
    }
}
