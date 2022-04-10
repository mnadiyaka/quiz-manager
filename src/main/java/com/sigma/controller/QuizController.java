package com.sigma.controller;

import com.sigma.model.dto.QuizDto;
import com.sigma.model.entity.Quiz;
import com.sigma.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class QuizController {

    private final QuizService quizService;

    @PostMapping("/{userId}/createQuiz")
    public Quiz createQuiz(@RequestBody QuizDto quizDto, @PathVariable Long userId) {
        return quizService.createQuiz(quizDto);
    }
}
