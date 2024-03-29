package com.sigma.controller;

import com.sigma.model.dto.QuizDto;
import com.sigma.model.entity.Quiz;
import com.sigma.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz")
@Slf4j
@PreAuthorize("hasRole('ADMIN')")
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/all")
    public List<QuizDto> getQuizzes() {
        return quizService.getAllQuizzes();
    }

    @PostMapping("")
    public Quiz createQuiz(@RequestBody QuizDto quizDto) {
        return quizService.createQuiz(quizDto);
    }

    @PatchMapping("/{quizId}")
    public String updateQuiz(@RequestBody QuizDto quizDto, @PathVariable Long quizId) {
        quizService.updateQuiz(quizDto, quizId);
        return "quiz created";
    }

    @DeleteMapping("/{quizId}")
    public String deleteQuiz(@PathVariable("quizId") Long quizId) {
        quizService.deleteQuiz(quizId);
        return "deleted quiz";
    }

    @PreAuthorize("hasRole('CAPTAIN')")
    @PatchMapping("/{quizId}/apply/{teamId}")
    public String applyForQuiz(@PathVariable Long quizId, @PathVariable Long teamId){
        quizService.applyForQuiz(quizId, teamId);
        return "applied";
    }

    @PatchMapping("/{quizId}/change/{state}")
    public String  closeQuiz(@PathVariable Long quizId, @PathVariable String state) {
        quizService.changeQuizState(quizId, state);
        return state;
    }

    @PatchMapping("/{quizId}/location/{locationId}")
    public Quiz assignLocation(@PathVariable Long locationId, @PathVariable Long quizId) {
        return quizService.assignLocation(quizId, locationId);
    }
}