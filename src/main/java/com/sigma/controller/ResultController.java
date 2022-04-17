package com.sigma.controller;

import com.sigma.model.dto.QuizResultsDto;
import com.sigma.model.entity.QuizResults;
import com.sigma.service.QuizResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/result")
@Slf4j
public class ResultController {

    private final QuizResultService quizResultService;

    @GetMapping("/all")
    public List<QuizResults> getTeams() {
        return quizResultService.getAllRes();
    }

    @PatchMapping("")
    public QuizResults setScore(QuizResultsDto quizResults) {
        return quizResultService.updateRes(quizResults);
    }
}
