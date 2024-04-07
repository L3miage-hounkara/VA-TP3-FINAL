package fr.uga.l3miage.spring.tp3.services;

import fr.uga.l3miage.spring.tp3.components.ExamComponent;
import fr.uga.l3miage.spring.tp3.components.SessionComponent;

import fr.uga.l3miage.spring.tp3.mappers.SessionMapper;
import fr.uga.l3miage.spring.tp3.models.EcosSessionEntity;
import fr.uga.l3miage.spring.tp3.request.SessionCreationRequest;
import fr.uga.l3miage.spring.tp3.request.SessionProgrammationCreationRequest;
import fr.uga.l3miage.spring.tp3.responses.SessionResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;


import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class SessionServiceTest {

    @Autowired
    SessionService sessionService;

    @MockBean
    SessionComponent sessionComponent;

    @MockBean
    ExamComponent examComponent;

    @SpyBean
    SessionMapper sessionMapper;

    @Test
    void createSessionSuccess() {
        SessionCreationRequest request = SessionCreationRequest.builder()
                .name("test")
                .examsId(new HashSet<>())
                .ecosSessionProgrammation(SessionProgrammationCreationRequest.builder().steps(new HashSet<>()).build())
                .build();

        EcosSessionEntity ecosSessionEntity = EcosSessionEntity.builder()
                .name("test")
                .build();

        when(sessionComponent.createSession(any())).thenReturn(ecosSessionEntity);

        // When
        SessionResponse response = sessionService.createSession(request);

        // Then
        assertThat(response).isNotNull();
    }

    @Test
    void createSessionFailure() {

        SessionCreationRequest request = SessionCreationRequest.builder()
                .name("test")
                .examsId(new HashSet<>())
                .ecosSessionProgrammation(SessionProgrammationCreationRequest.builder().steps(new HashSet<>()).build())
                .build();

        when(sessionComponent.createSession(any())).thenReturn(null);


        SessionResponse response = sessionService.createSession(request);


        assertThat(response).isNull();
    }

    @Test
    void endSessionSuccess() {

        EcosSessionEntity ecosSessionEntity = EcosSessionEntity.builder()
                .name("test")
                .build();

        when(sessionComponent.endSession(any())).thenReturn(ecosSessionEntity);


        SessionResponse response = sessionService.endSession(1L);


        assertThat(response).isNotNull();
    }
}
