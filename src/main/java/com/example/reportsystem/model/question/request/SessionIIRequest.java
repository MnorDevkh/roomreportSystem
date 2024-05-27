package com.example.reportsystem.model.question.request;

import com.example.reportsystem.model.question.SessionIIAQ;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionIIRequest {
    private String questionTitle;
    private List<SessionIIAQRequest> sessionIIAQRequests;
}
