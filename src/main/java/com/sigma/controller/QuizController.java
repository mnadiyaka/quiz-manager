package com.sigma.controller;

import com.sigma.model.dto.QuizDto;
import com.sigma.model.entity.Quiz;
import com.sigma.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/{userId}")
@Slf4j
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/quizzes")
    public List<QuizDto> getTeams(@PathVariable Long userId) {
        return quizService.getAllQuizzes();
    }

    @PostMapping("/createQuiz")
    public Quiz createQuiz(@RequestBody QuizDto quizDto, @PathVariable Long userId) {
        return quizService.createQuiz(quizDto, userId);
    }

    @PostMapping("/quiz/{quizId}/update")
    public Quiz updateQuiz(@RequestBody QuizDto quizDto, @PathVariable Long userId, @PathVariable Long quizId) {
        return quizService.updateQuiz(quizDto, quizId, userId);
    }

    @DeleteMapping("/quiz/{quizId}/delete")
    public String deleteQuiz(@PathVariable("userId") Long userId, @PathVariable("quizId") Long quizId) {
        quizService.deleteQuiz(userId, quizId);
        return "deleted quiz";
    }
}
