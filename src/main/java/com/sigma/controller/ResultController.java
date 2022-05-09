package com.sigma.controller;

import com.sigma.model.dto.QuizResultsSearchDto;
import com.sigma.model.dto.QuizResultsDto;
import com.sigma.model.entity.QuizResults;
import com.sigma.service.QuizResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/result")
@Slf4j
@PreAuthorize("hasRole('CAPTAIN')")
public class ResultController {

    private final QuizResultService quizResultService;

    @GetMapping("/all")
    public Set<QuizResultsDto> getTeams() {
        return quizResultService.getAllRes();
    }

    @PatchMapping("")
    public String setScore(@RequestBody QuizResultsDto quizResults) {
        quizResultService.updateRes(quizResults);
        return "score entered";
    }

    @GetMapping(value = "filter")
    public @ResponseBody
    List<QuizResultsDto> filterRes(@RequestBody QuizResultsSearchDto data) {
        return quizResultService.filterData(data);
    }
}