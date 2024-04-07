package fr.uga.l3miage.spring.tp3.controllers;

import fr.uga.l3miage.spring.tp3.request.SessionCreationRequest;
import fr.uga.l3miage.spring.tp3.request.SessionProgrammationCreationRequest;
import fr.uga.l3miage.spring.tp3.responses.SessionResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class SessionControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void createSessionSuccess() {
        SessionCreationRequest request = SessionCreationRequest.builder()
                .name("test")
                .startDate(LocalDateTime.MIN)
                .endDate(LocalDateTime.MAX)
                .examsId(new HashSet<>())
                .ecosSessionProgrammation(SessionProgrammationCreationRequest.builder().steps(new HashSet<>()).build())
                .build();

        ResponseEntity<SessionResponse> responseEntity = testRestTemplate.postForEntity("/api/sessions/create", request, SessionResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotNull();
    }

    @Test
    void creationSessionFail() {
        SessionCreationRequest request = SessionCreationRequest.builder()
                .startDate(LocalDateTime.MIN)
                .endDate(LocalDateTime.MAX)
                .examsId(new HashSet<>())
                .ecosSessionProgrammation(SessionProgrammationCreationRequest.builder().steps(new HashSet<>()).build())
                .build();

        ResponseEntity<SessionResponse> responseEntity = testRestTemplate.postForEntity("/api/sessions/create", request, SessionResponse.class);

        assertThat(responseEntity.getStatusCode()).isNotEqualTo(HttpStatus.CREATED);
    }

    @Test
    void endSessionSuccess() {

        Long validSessionId = 1L;

        ResponseEntity<SessionResponse> responseEntity = testRestTemplate.exchange("/api/sessions/end/{id}", HttpMethod.PUT, null, SessionResponse.class, validSessionId);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
    }
}