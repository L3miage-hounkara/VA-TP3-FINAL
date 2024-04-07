package fr.uga.l3miage.spring.tp3.controllers;

import fr.uga.l3miage.spring.tp3.exceptions.CandidatNotFoundResponse;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.CandidateEvaluationGridEntity;
import fr.uga.l3miage.spring.tp3.models.ExamEntity;
import fr.uga.l3miage.spring.tp3.repositories.CandidateRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class CandidateControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CandidateRepository candidateRepository;

    @AfterEach
    public void cleanup() {
        candidateRepository.deleteAll();
    }

    @Test
    void getCandidateAverageFound() {

        CandidateEvaluationGridEntity grid1 = CandidateEvaluationGridEntity.builder()
                .grade(5).examEntity(ExamEntity.builder().weight(1).build()).build();
        CandidateEvaluationGridEntity grid2 = CandidateEvaluationGridEntity.builder()
                .grade(15).examEntity(ExamEntity.builder().weight(1).build()).build();

        HashSet<CandidateEvaluationGridEntity> gridEntities = new HashSet<>();
        gridEntities.add(grid1);
        gridEntities.add(grid2);

        CandidateEntity candidate = CandidateEntity.builder()
                .email("abdelkoddous.hounkara@etu.univ-grenoble-alpes.fr")
                .candidateEvaluationGridEntities(gridEntities)
                .build();


        candidate = candidateRepository.save(candidate);

        ResponseEntity<String> responseEntity = testRestTemplate.exchange("/api/candidates/{candidateId}/average", HttpMethod.GET, null, String.class, 1L);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getCandidateAverageNotFound() {
        final Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("candidateId", "id du candidat qui n'existe pas");

        CandidatNotFoundResponse expectedResponse = CandidatNotFoundResponse.builder()
                .uri(null)
                .errorMessage(null)
                .candidateId(null)
                .build();


        ResponseEntity<CandidatNotFoundResponse> response = testRestTemplate.exchange("/api/candidates/{candidateId}/average", HttpMethod.GET, null, CandidatNotFoundResponse.class, urlParams);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(expectedResponse);
    }
}