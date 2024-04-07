package fr.uga.l3miage.spring.tp3.components;


import fr.uga.l3miage.spring.tp3.exceptions.technical.CandidateNotFoundException;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.repositories.CandidateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureTestDatabase
public class CandidateComponentTest {

    @Autowired
    private CandidateComponent candidateComponent;

    @MockBean
    private CandidateRepository candidateRepository;

    @Test
    void getCandidateByIdThrowsNotFoundException() {
        // Configurer le mock pour retourner un Optional vide quand findById est appelé
        when(candidateRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Tester que la méthode getCandidatById lance une CandidateNotFoundException si aucun candidat n'est trouvé
        assertThrows(CandidateNotFoundException.class, () -> candidateComponent.getCandidatById(1L));
    }

    @Test
    void getCandidateByIdNoException() {
        // Configurer un candidat de test à retourner par le mock
        CandidateEntity candidateEntity = CandidateEntity.builder()
                .email("abdelkoddous.hounkara@etu.univ-grenoble-alpes.fr")
                .build();
        when(candidateRepository.findById(anyLong())).thenReturn(Optional.of(candidateEntity));

        // Tester que aucune exception n'est lancée lorsque le candidat est trouvé
        assertDoesNotThrow(() -> candidateComponent.getCandidatById(1L));
    }
}
