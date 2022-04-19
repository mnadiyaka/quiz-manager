package com.sigma.controller;

import com.sigma.model.dto.QuizResultsDto;
import com.sigma.model.entity.QuizResults;
import com.sigma.service.QuizResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/result")
@Slf4j
public class ResultController {

    private final QuizResultService quizResultService;

    @GetMapping("/all")
    public Set<QuizResultsDto> getTeams() {
        return quizResultService.getAllRes();
    }

    @PatchMapping("")
    public QuizResults setScore(QuizResultsDto quizResults) {
        return quizResultService.updateRes(quizResults);
    }

    @RequestMapping(value = "team", method = RequestMethod.GET)
    public @ResponseBody Set<QuizResultsDto> filterRes(@RequestParam(value = "teamId", required = false) String teamId, @RequestParam(value = "totalScore", required = false) String score) {
        return quizResultService.filterData(teamId, score);
    }
}
