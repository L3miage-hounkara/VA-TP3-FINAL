package fr.uga.l3miage.spring.tp3.repositories;

import fr.uga.l3miage.spring.tp3.enums.TestCenterCode;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.CandidateEvaluationGridEntity;
import fr.uga.l3miage.spring.tp3.models.TestCenterEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
public class CandidateRepositoryTest {

    @Autowired
    private TestCenterRepository testCenterRepository;

    @Autowired
    private CandidateEvaluationGridRepository candidateEvaluationGridRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @AfterEach
    public void cleanUp() {
        candidateEvaluationGridRepository.deleteAll();
        candidateRepository.deleteAll();
        testCenterRepository.deleteAll();
    }

    @Test
    void findAllByTestCenterEntityCode() {
        TestCenterEntity testCenter1 = TestCenterEntity
                .builder().
                code(TestCenterCode.GRE).
                build();
        TestCenterEntity testCenter2 = TestCenterEntity
                .builder().
                code(TestCenterCode.NCE).
                build();

        CandidateEntity candidate1 = CandidateEntity
                .builder()
                .email("abdelkoddous.hounkara@etu.univ-grenoble-alpes.fr")
                .testCenterEntity(testCenter1)
                .build();

        CandidateEntity candidate2 = CandidateEntity
                .builder()
                .email("nassima.moumou@etu.univ-grenoble-alpes.fr")
                .testCenterEntity(testCenter2)
                .build();


        testCenterRepository.save(testCenter1);
        testCenterRepository.save(testCenter2);
        candidateRepository.save(candidate1);
        candidateRepository.save(candidate2);

        Set<CandidateEntity> candidateEntitiesResponses = candidateRepository.findAllByTestCenterEntityCode(TestCenterCode.GRE);


        assertThat(candidateEntitiesResponses).hasSize(1);
        assertThat(candidateEntitiesResponses.stream().findFirst().get().getTestCenterEntity().getCode()).isEqualTo(TestCenterCode.GRE);
    }


    @Test
    void findAllByCandidateEvaluationGridEntitiesGradeLessThan() {

        CandidateEvaluationGridEntity candidateEvaluationGrid = CandidateEvaluationGridEntity
                .builder()
                .grade(10)
                .build();

        CandidateEntity candidate = CandidateEntity
                .builder()
                .email("nassima.moumou@etu.univ-grenoble-alpes.fr")
                .build();

        candidateEvaluationGrid.setCandidateEntity(candidate);

        candidateRepository.save(candidate);
        candidateEvaluationGridRepository.save(candidateEvaluationGrid);

        Set<CandidateEntity> candidateEntitiesResponses = candidateRepository.findAllByCandidateEvaluationGridEntitiesGradeLessThan(12);

        assertThat(candidateEntitiesResponses).hasSize(1);
    }


    @Test
    void findAllByHasExtraTimeFalseAndBirthDateBefore() {
        CandidateEntity candidate = CandidateEntity
                .builder()
                .email("abdelkoddous.hounkara@etu.univ-grenoble-alpes.fr")
                .hasExtraTime(false)
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();

        candidateRepository.save(candidate);

        Set<CandidateEntity> candidateEntitiesResponses = candidateRepository.findAllByHasExtraTimeFalseAndBirthDateBefore(LocalDate.of(2001, 1, 1));

        assertThat(candidateEntitiesResponses).hasSize(1);
    }


    @Test
    void findAllByHasExtraTimeTrueAndBirthDateBefore() {
        CandidateEntity candidate = CandidateEntity
                .builder()
                .email("nassima.moumou@etu.univ-grenoble-alpes.fr")
                .hasExtraTime(true)
                .birthDate(LocalDate.of(2002, 8, 27))
                .build();

        candidateRepository.save(candidate);

        Set<CandidateEntity> candidateEntitiesResponses = candidateRepository.findAllByHasExtraTimeFalseAndBirthDateBefore(LocalDate.of(2002, 8, 27));

        assertThat(candidateEntitiesResponses).hasSize(0);
    }

    @Test
    void findAllByHasExtraTimFalseAndBirthDateAfter() {
        CandidateEntity candidate = CandidateEntity
                .builder()
                .email("abdelkoddous.hounkara@etu.univ-grenoble-alpes.fr")
                .hasExtraTime(false)
                .birthDate(LocalDate.of(2002, 5, 22))
                .build();

        candidateRepository.save(candidate);

        Set<CandidateEntity> candidateEntitiesResponses = candidateRepository.findAllByHasExtraTimeFalseAndBirthDateBefore(LocalDate.of(2002, 5, 20));

        assertThat(candidateEntitiesResponses).hasSize(0);
    }
}