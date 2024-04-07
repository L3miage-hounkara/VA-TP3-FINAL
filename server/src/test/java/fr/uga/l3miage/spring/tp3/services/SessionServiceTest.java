/*package fr.uga.l3miage.spring.tp3.services;

import fr.uga.l3miage.spring.tp3.components.ExamComponent;
import fr.uga.l3miage.spring.tp3.components.SessionComponent;
import fr.uga.l3miage.spring.tp3.exceptions.rest.CreationSessionRestException;
import fr.uga.l3miage.spring.tp3.exceptions.technical.ExamNotFoundException;
import fr.uga.l3miage.spring.tp3.mappers.SessionMapper;
import fr.uga.l3miage.spring.tp3.models.EcosSessionEntity;
import fr.uga.l3miage.spring.tp3.models.ExamEntity;
import fr.uga.l3miage.spring.tp3.request.SessionCreationRequest;
import fr.uga.l3miage.spring.tp3.request.SessionProgrammationCreationRequest;
import fr.uga.l3miage.spring.tp3.request.SessionProgrammationStepCreationRequest;
import fr.uga.l3miage.spring.tp3.responses.SessionResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        // Given
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
        // Given
        SessionCreationRequest request = SessionCreationRequest.builder()
                .name("test")
                .examsId(new HashSet<>())
                .ecosSessionProgrammation(SessionProgrammationCreationRequest.builder().steps(new HashSet<>()).build())
                .build();

        when(sessionComponent.createSession(any())).thenReturn(null);

        // When
        SessionResponse response = sessionService.createSession(request);

        // Then
        //assertThat(response).isNull();
    }

    @Test
    void endSessionSuccess() {
        // Given
        EcosSessionEntity ecosSessionEntity = EcosSessionEntity.builder()
                .name("test")
                .build();

        when(sessionComponent.endSession(any())).thenReturn(ecosSessionEntity);

        // When
        SessionResponse response = sessionService.endSession(1L);

        // Then
        assertThat(response).isNotNull();
    }

    @Test
    void createSessionDontThrow() throws ExamNotFoundException {
        // Given
        SessionCreationRequest sessionCreationRequest = SessionCreationRequest.builder().build();
        EcosSessionEntity ecosSessionEntity = EcosSessionEntity.builder().build();
        SessionProgrammationCreationRequest sessionProgrammationCreationRequest = SessionProgrammationCreationRequest.builder().build();
        SessionProgrammationStepCreationRequest sessionProgrammationStepCreationRequest = SessionProgrammationStepCreationRequest.builder().build();
        ExamEntity examEntity = ExamEntity.builder().id(1L).build();

        sessionProgrammationCreationRequest.setSteps(Set.of(sessionProgrammationStepCreationRequest));
        sessionCreationRequest.setExamsId(Set.of(examEntity.getId()));
        sessionCreationRequest.setEcosSessionProgrammation(sessionProgrammationCreationRequest);

        when(sessionComponent.createSession(any(EcosSessionEntity.class))).thenReturn(ecosSessionEntity);
        when(examComponent.getAllById(Set.of(anyLong()))).thenReturn(Set.of(examEntity));

        // When
        SessionResponse sessionResponseActual = sessionService.createSession(sessionCreationRequest);
        SessionResponse sessionResponseExpected = sessionMapper.toResponse(ecosSessionEntity);

        // Then
        assertThat(sessionResponseActual).isEqualTo(sessionResponseExpected);
    }

    @Test
    void createSessionThrowCreateSessionRestException() {
        // Given
        when(examComponent.getAllById(Set.of(anyLong()))).thenThrow(ExamNotFoundException.class);

        // When - Then
        assertThrows(CreationSessionRestException.class, () -> sessionService.createSession(any(SessionCreationRequest.class)));
    }
}
*/
