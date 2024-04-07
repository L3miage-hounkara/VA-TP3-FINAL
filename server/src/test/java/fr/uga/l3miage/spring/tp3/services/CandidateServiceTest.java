package fr.uga.l3miage.spring.tp3.services;

import fr.uga.l3miage.spring.tp3.components.CandidateComponent;
import fr.uga.l3miage.spring.tp3.exceptions.rest.CandidateNotFoundRestException;
import fr.uga.l3miage.spring.tp3.exceptions.technical.CandidateNotFoundException;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.CandidateEvaluationGridEntity;
import fr.uga.l3miage.spring.tp3.models.ExamEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CandidateServiceTest {

    @Autowired
    private CandidateService candidateService;

    @MockBean
    private CandidateComponent candidateComponent;

    @Test
    void testGetCandidateAverage() throws CandidateNotFoundException {
        ExamEntity examEntity = ExamEntity.builder()
                .id(1L)
                .name("test")
                .weight(1)
                .build();

        CandidateEvaluationGridEntity candidateEvaluationGridEntity = CandidateEvaluationGridEntity.builder()
                .grade(10)
                .examEntity(examEntity)
                .build();

        ExamEntity examEntity2 = ExamEntity.builder()
                .id(2L)
                .name("test2")
                .weight(2)
                .build();

        CandidateEvaluationGridEntity candidateEvaluationGridEntity2 = CandidateEvaluationGridEntity.builder()
                .grade(10)
                .examEntity(examEntity2)
                .build();

        CandidateEntity candidateEntity = CandidateEntity.builder()
                .id(1L)
                .candidateEvaluationGridEntities(Set.of(candidateEvaluationGridEntity, candidateEvaluationGridEntity2))
                .build();

        when(candidateComponent.getCandidatById(anyLong())).thenReturn(candidateEntity);


        assertEquals(10.0, candidateService.getCandidateAverage(1L), 0.01, "The average grade calculation is incorrect");
    }


    @Test
    void testGetCandidateAverageNotFound() throws CandidateNotFoundException {
        when(candidateComponent.getCandidatById(anyLong())).thenThrow(CandidateNotFoundException.class);
        assertThrows(CandidateNotFoundRestException.class, () -> candidateService.getCandidateAverage(1L));
    }
}
