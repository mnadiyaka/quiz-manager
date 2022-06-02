package com.sigma.controller;

import com.sigma.model.dto.AggregationStatisticResDto;
import com.sigma.model.dto.AggregationStatisticsDto;
import com.sigma.model.dto.QuizResultsSearchDto;
import com.sigma.model.dto.QuizResultsDto;
import com.sigma.service.QuizResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz-result")
@Slf4j
@PreAuthorize("hasRole('ADMIN')")
public class ResultController {

    private final QuizResultService quizResultService;

    @GetMapping("/all")
    public Set<QuizResultsDto> getTeams() {
        return quizResultService.getAllRes();
    }

    @GetMapping("/{quizId}")
    public List<QuizResultsDto> getTeamsByQuizId(@PathVariable Long quizId) {
        return quizResultService.findResultsByQuizId(quizId);
    }

    @PatchMapping("")
    public String setScore(@RequestBody QuizResultsDto quizResults) {
        quizResultService.updateRes(quizResults);
        return "score entered";
    }

    @PostMapping("/create/{quizId}")
    public String createResultTable(@PathVariable Long quizId) {
        quizResultService.createResultsTable(quizId);
        return "created";
    }

    @GetMapping(value = "filter")
    @PreAuthorize("hasAnyRole('CAPTAIN', 'ADMIN')")
    public @ResponseBody
    List<QuizResultsDto> filterRes(@RequestBody QuizResultsSearchDto data) {
        return quizResultService.getQuizResultsStatistics(data);
    }

    @GetMapping(value = "aggStat")
    @PreAuthorize("hasAnyRole('CAPTAIN', 'ADMIN')")
    public List<AggregationStatisticResDto> groupRes(@RequestBody AggregationStatisticsDto data) {
        return quizResultService.getAggregationStatistics(data);
    }
}