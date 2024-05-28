package com.example.reportsystem.service.question.serviceImpl;

import com.example.reportsystem.model.question.*;
import com.example.reportsystem.model.question.dto.AnswerListDto;
import com.example.reportsystem.model.question.dto.QuestionListDto;
import com.example.reportsystem.model.question.request.*;
import com.example.reportsystem.model.question.response.QuestionResponse;
import com.example.reportsystem.model.question.response.SessionIResponse;
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
        System.out.println(request + "12131");
        try {
            Questions questions = mapQuestionsRequestToEntity(request);
            Questions savedQuestions = examQuestionsRepository.save(questions);
            QuestionResponse questionResponse = createQuestionResponse(savedQuestions);
            return ResponseEntity.status(HttpStatus.CREATED).body(questionResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    private Questions mapQuestionsRequestToEntity(QuestionsRequest request) {
        Questions questions = Questions.builder()
                .title(request.getTitle())
                .semester(request.getSemester())
                .year(request.getYear())
                .subject(request.getSubject())
                .shift(request.getShift())
                .lecture(request.getLecture())
                .sessions(mapSessionsRequestToEntity(request.getSessionRequest()))
                .build();
        return questions;
    }
    private Sessions mapSessionsRequestToEntity(SessionsRequest sessionsRequest) {
        if (sessionsRequest == null) {
            return null;
        }
        System.out.println(sessionsRequest.getSessionI().getQuestionTitle()+ "sessionsRequest.getSessionI()");
        Sessions sessions = Sessions.builder()
                .sessionI(mapSessionIRequestToEntity(sessionsRequest.getSessionI()))
//                .sessionII(mapSessionIIRequestToEntity(sessionsRequest.getSessionII()))
//                .sessionIII(mapSessionIIIRequestToEntity(sessionsRequest.getSessionIII()))
//                .sessionIV(mapSessionIVRequestToEntity(sessionsRequest.getSessionIV()))
//                .sessionV(mapSessionVRequestToEntity(sessionsRequest.getSessionV()))
                .build();
        System.out.println("sessions11111" + sessions.getSessionI().getQuestionTitle());
        return sessions;
    }
    private SessionI mapSessionIRequestToEntity(SessionIRequest sessionIRequest) {
        if (sessionIRequest == null) {
            return null;
        }
        SessionI sessionI = new SessionI();
        sessionI.setQuestionTitle(sessionIRequest.getQuestionTitle());
        sessionI.setQuestionList(mapquestionsList(sessionIRequest.getQuestionList()));
        sessionI.setAnswerList(mapAnswerList(sessionIRequest.getAnswerList()));
        System.out.println("sessions1"+sessionI.getQuestionList());
        return sessionI;
    }

    private List<AnswerList> mapAnswerList(List<AnswerListRequest> answerList) {
        List<AnswerList> mappedAnswerList = new ArrayList<>();

        if (answerList != null) {
            for (AnswerListRequest answerListRequest : answerList) {
                AnswerList mappedAnswer = new AnswerList();
                mappedAnswer.setAnswer(answerListRequest.getAnswer());
                // Map any other attributes if needed

                mappedAnswerList.add(mappedAnswer);
            }
        }

        return mappedAnswerList;
    }

    private List<QuestionList> mapquestionsList(List<QuestionListRequest> questionList) {
        List<QuestionList> mappedQuestionList = new ArrayList<>();

        if (questionList != null) {
            for (QuestionListRequest questionListRequest : questionList) {
                QuestionList mappedQuestion = new QuestionList();
                mappedQuestion.setQuestion(questionListRequest.getQuestion());
                // Map any other attributes if needed

                mappedQuestionList.add(mappedQuestion);
            }
        }

        return mappedQuestionList;
    }

//    private SessionII mapSessionIIRequestToEntity(SessionIIRequest sessionIIRequest) {
//        if (sessionIIRequest == null) {
//            return null; // or throw an exception if necessary
//        }
//        SessionII sessionII = SessionII.builder()
//
//                .build();
//        List<SessionII> sessionIIList = new ArrayList<>();
//        for (SessionIIAQRequest sessionIIAQRequest : sessionIIRequest.getSessionIIAQRequests()){
//            SessionIIAQ sessionIIAQ = SessionIIAQ.builder()
//                    .questionList(sessionIIAQRequest.getQuestion())
//                    .build();
//        }
//
//        sessionII.setQuestionTitle(sessionIIRequest.getQuestionTitle());
//        // Map other attributes as needed
//        return sessionII;
//    }




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
            List<AnswerListDto> answerListDtos = new ArrayList<>();
            for (AnswerList answerList : sessions.getSessionI().getAnswerList()) {
                AnswerListDto answerListDto = AnswerListDto.builder()
                        .id(answerList.getId())
                        .answer(answerList.getAnswer())
                        .build();
                answerListDtos.add(answerListDto);
            }

            List<QuestionListDto> questionListDtos = new ArrayList<>();
            for (QuestionList questionList : sessions.getSessionI().getQuestionList()) {
                QuestionListDto questionListDto = QuestionListDto.builder()
                        .id(questionList.getId())
                        .question(questionList.getQuestion())
                        .build();
                questionListDtos.add(questionListDto);
            }
            SessionIResponse sessionIResponse = SessionIResponse.builder()
                    .id(sessions.getId())
                    .questionTitle(sessions.getSessionI().getQuestionTitle())
                    .answerList(answerListDtos)
                    .questionList(questionListDtos)
                    .build();


            SessionsResponse sessionsResponse = SessionsResponse.builder()
                    .id(sessions.getId())
                    .sessionI(sessionIResponse)
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
