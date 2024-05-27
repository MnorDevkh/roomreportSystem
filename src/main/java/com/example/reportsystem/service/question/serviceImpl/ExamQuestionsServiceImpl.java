package com.example.reportsystem.service.question.serviceImpl;

import com.example.reportsystem.model.question.AnswerList;
import com.example.reportsystem.model.question.QuestionList;
import com.example.reportsystem.model.question.Questions;
import com.example.reportsystem.model.question.Sessions;
import com.example.reportsystem.model.question.dto.AnswerListDto;
import com.example.reportsystem.model.question.dto.QuestionListDto;
import com.example.reportsystem.model.question.request.AnswerListRequest;
import com.example.reportsystem.model.question.request.QuestionListRequest;
import com.example.reportsystem.model.question.request.QuestionsRequest;
import com.example.reportsystem.model.question.request.SessionsRequest;
import com.example.reportsystem.model.question.response.QuestionResponse;
import com.example.reportsystem.model.question.response.SessionsResponse;
import com.example.reportsystem.repository.question.ExamQuestionsRepository;
import com.example.reportsystem.service.question.ExamQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExamQuestionsServiceImpl implements ExamQuestionsService {

    @Autowired
    ExamQuestionsRepository examQuestionsRepository;


    @Override
    public ResponseEntity<QuestionResponse> add(QuestionsRequest request) {
        Questions questions = Questions.builder()
                .subject(request.getSubject())
                .shift(request.getShift())
                .year(request.getYear())
                .semester(request.getSemester())
                .lecture(request.getLecture())
                .title(request.getTitle())
                .build();
        List<Sessions> sessions = new ArrayList<>();
        for (SessionsRequest sessionsRequest : request.getSessions()) {
            Sessions sessionsObj = Sessions.builder()
                    .questionTitle(sessionsRequest.getQuestionTitle())
                    .questions(questions)
                    .build();
            sessions.add(sessionsObj);
            List<QuestionList> questionLists = new ArrayList<>();
            for (QuestionListRequest questionListRequest : sessionsRequest.getQuestionList()) {
                QuestionList questionList = QuestionList.builder()
                        .question(questionListRequest.getQuestion())
                        .sessions(sessionsObj) // Associate QuestionList with Sessions
                        .build();
                questionLists.add(questionList);
            }
            sessionsObj.setQuestionList(questionLists);
            List<AnswerList> answerLists = new ArrayList<>();
            for (AnswerListRequest answerListRequest : sessionsRequest.getAnswerListRequests()) {
                AnswerList answerList = AnswerList.builder()
                        .answer(answerListRequest.getAnswer())
                        .sessions(sessionsObj) // Associate AnswerList with Sessions
                        .build();
                answerLists.add(answerList);
            }
            sessionsObj.setAnswerList(answerLists);
        }

        // Set the list of sessions to the questions entity
        questions.setSessions(sessions);
        // Save the questions entity to the repository
        Questions savedQuestions = examQuestionsRepository.save(questions);
        QuestionResponse questionResponse = questionResponse(savedQuestions);
//        telegramBot.sendMessage("1","hi");
        return ResponseEntity.status(HttpStatus.CREATED).body(questionResponse);
    }

    @Override
    public ResponseEntity<List<QuestionResponse>> findAll() {
        List<Questions> questionsList = examQuestionsRepository.findAll();
        List<QuestionResponse> questionListsResponses = new ArrayList<>();
        for (Questions questions : questionsList) {
            QuestionResponse questionResponse = questionResponse(questions);
            questionListsResponses.add(questionResponse);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(questionListsResponses);
    }

    private QuestionResponse questionResponse(Questions questions) {
        List<SessionsResponse> sessionsList = new ArrayList<>();

        for (Sessions sessions : questions.getSessions()) {
            List<AnswerListDto> answerListDtoList = new ArrayList<>();
            for(AnswerList answerList: sessions.getAnswerList()){
                AnswerListDto answerListDto = AnswerListDto.builder()
                        .id(answerList.getId())
                        .answer(answerList.getAnswer())
                        .build();
                answerListDtoList.add(answerListDto);
            }
            List<QuestionListDto> questionListDtoList = new ArrayList<>();
            for(QuestionList questionList: sessions.getQuestionList()){
                QuestionListDto questionListDto = QuestionListDto.builder()
                        .id(questionList.getId())
                        .question(questionList.getQuestion())
                        .build();
                questionListDtoList.add(questionListDto);
            }
            SessionsResponse sessionsResponse = SessionsResponse.builder()
                    .id(sessions.getId())
                    .questionTitle(sessions.getQuestionTitle())
                    .questionList(questionListDtoList)
                    .answerList(answerListDtoList)
                    .build();
            sessionsList.add(sessionsResponse);
        }
        return QuestionResponse.builder()
                .id(questions.getId())
                .lecture(questions.getLecture())
                .year(questions.getYear())
                .title(questions.getTitle())
                .semester(questions.getSemester())
                .sessions(sessionsList)
                .subject(questions.getSubject())
                .shift(questions.getShift())
                .build();
    }

}
