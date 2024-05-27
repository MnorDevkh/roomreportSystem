package com.example.reportsystem.service.question.serviceImpl;

import com.example.reportsystem.model.question.*;
import com.example.reportsystem.model.question.dto.AnswerListDto;
import com.example.reportsystem.model.question.dto.QuestionListDto;
import com.example.reportsystem.model.question.request.*;
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
        try {
            // Map request data to entity objects
            Questions questions = mapQuestionsRequestToEntity(request);

            // Save the questions entity to the repository
            Questions savedQuestions = examQuestionsRepository.save(questions);

            // Create and return the response
            QuestionResponse questionResponse = createQuestionResponse(savedQuestions);
            return ResponseEntity.status(HttpStatus.CREATED).body(questionResponse);
        } catch (Exception e) {
            // Handle exceptions and return appropriate response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private Questions mapQuestionsRequestToEntity(QuestionsRequest request) {
        // Create a new Questions entity and set its attributes using data from the request
        Questions questions = new Questions();
        questions.setTitle(request.getTitle());
        questions.setSemester(request.getSemester());
        questions.setYear(request.getYear());
        questions.setSubject(request.getSubject());
        questions.setShift(request.getShift());
        questions.setLecture(request.getLecture());

        // Map SessionsRequest to Sessions entity
        Sessions sessions = new Sessions();
        sessions.setSessionI(mapSessionIRequestToEntity(request.getSessionRequest().getSessionI()));
        sessions.setSessionII(mapSessionIIRequestToEntity(request.getSessionRequest().getSessionII()));
//        sessions.setSessionIII(mapSessionIIIRequestToEntity(request.getSessionRequest().getSessionIII()));
//        sessions.setSessionIV(mapSessionIVRequestToEntity(request.getSessionRequest().getSessionIV()));
//        sessions.setSessionV(mapSessionVRequestToEntity(request.getSessionRequest().getSessionV()));

        // Associate the Sessions entity with the Questions entity
        questions.setSessions(sessions);

        return questions;
    }

    private SessionII mapSessionIIRequestToEntity(SessionIIRequest sessionIIRequest) {
        if (sessionIIRequest == null) {
            return null; // or throw an exception if necessary
        }
        SessionII sessionII = SessionII.builder()

                .build();
        List<SessionII> sessionIIList = new ArrayList<>();
        for (SessionIIAQRequest sessionIIAQRequest : sessionIIRequest.getSessionIIAQRequests()){
            SessionIIAQ sessionIIAQ = SessionIIAQ.builder()
                    .questionList(sessionIIAQRequest.getQuestion())
                    .build();
        }

        sessionII.setQuestionTitle(sessionIIRequest.getQuestionTitle());
        // Map other attributes as needed
        return sessionII;
    }

    private SessionI mapSessionIRequestToEntity(SessionIRequest sessionIRequest) {
        if (sessionIRequest == null) {
            return null; // or throw an exception if necessary
        }
        SessionI sessionI = new SessionI();
        sessionI.setQuestionTitle(sessionIRequest.getQuestionTitle());
        // Map other attributes as needed
        return sessionI;
    }


    @Override
    public ResponseEntity<List<QuestionResponse>> findAll() {
        List<Questions> questionsList = examQuestionsRepository.findAll();
        List<QuestionResponse> questionResponses = new ArrayList<>();
        for (Questions questions : questionsList) {
            QuestionResponse questionResponse = createQuestionResponse(questions);
            questionResponses.add(questionResponse);
        }
        return ResponseEntity.status(HttpStatus.OK).body(questionResponses);
    }

    private QuestionResponse createQuestionResponse(Questions questions) {
        List<SessionsResponse> sessionsResponses = new ArrayList<>();

        Sessions sessions = questions.getSessions();
        if (sessions != null) {
//            List<AnswerListDto> answerListDtos = new ArrayList<>();
//            for (AnswerList answerList : sessions.getAnswerList()) {
//                AnswerListDto answerListDto = AnswerListDto.builder()
//                        .id(answerList.getId())
//                        .answer(answerList.getAnswer())
//                        .build();
//                answerListDtos.add(answerListDto);
//            }
//
//            List<QuestionListDto> questionListDtos = new ArrayList<>();
//            for (QuestionList questionList : sessions.getQuestionList()) {
//                QuestionListDto questionListDto = QuestionListDto.builder()
//                        .id(questionList.getId())
//                        .question(questionList.getQuestion())
//                        .build();
//                questionListDtos.add(questionListDto);
//            }

            SessionsResponse sessionsResponse = SessionsResponse.builder()
                    .id(sessions.getId())
                    .sessionI(sessions.getSessionI())
                    .sessionII(sessions.getSessionII())
                    .sessionIII(sessions.getSessionIII())
                    .sessionIV(sessions.getSessionIV())
                    .sessionV(sessions.getSessionV())
                    .build();
            sessionsResponses.add(sessionsResponse);
        }

        return QuestionResponse.builder()
                .id(questions.getId())
                .lecture(questions.getLecture())
                .year(questions.getYear())
                .title(questions.getTitle())
                .semester(questions.getSemester())
                .sessions(sessionsResponses)
                .subject(questions.getSubject())
                .shift(questions.getShift())
                .build();
    }

}
