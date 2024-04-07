package fr.uga.l3miage.spring.tp3.components;

import fr.uga.l3miage.spring.tp3.components.SessionComponent;
import fr.uga.l3miage.spring.tp3.models.EcosSessionEntity;
import fr.uga.l3miage.spring.tp3.models.EcosSessionProgrammationEntity;
import fr.uga.l3miage.spring.tp3.repositories.EcosSessionProgrammationRepository;
import fr.uga.l3miage.spring.tp3.repositories.EcosSessionProgrammationStepRepository;
import fr.uga.l3miage.spring.tp3.repositories.EcosSessionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureTestDatabase
public class SessionComponentTest {

    @Autowired
    private SessionComponent sessionComponent;

    @MockBean
    private EcosSessionRepository ecosSessionRepository;

    @MockBean
    private EcosSessionProgrammationRepository ecosSessionProgrammationRepository;

    @MockBean
    private EcosSessionProgrammationStepRepository ecosSessionProgrammationStepRepository;

    @Test
    void createSessionSuccess() {
        EcosSessionEntity sessionEntity = EcosSessionEntity.builder()
                .id(1L)
                .examEntities(new HashSet<>())
                .name("Session Test")
                .ecosSessionProgrammationEntity(EcosSessionProgrammationEntity.builder().build())
                .build();

        when(ecosSessionProgrammationRepository.save(any(EcosSessionProgrammationEntity.class)))
                .thenReturn(sessionEntity.getEcosSessionProgrammationEntity());
        when(ecosSessionRepository.save(any(EcosSessionEntity.class)))
                .thenReturn(sessionEntity);

        EcosSessionEntity createdSession = sessionComponent.createSession(sessionEntity);

        assertNotNull(createdSession, "La session créée ne devrait pas être nulle.");
        verify(ecosSessionProgrammationRepository).save(sessionEntity.getEcosSessionProgrammationEntity());
        verify(ecosSessionRepository).save(sessionEntity);
    }

    @Test
    void endSessionSuccess() {
        EcosSessionEntity sessionEntity = EcosSessionEntity.builder()
                .id(1L)
                .examEntities(new HashSet<>())
                .name("Session Test")
                .ecosSessionProgrammationEntity(EcosSessionProgrammationEntity.builder().build())
                .build();

        when(ecosSessionRepository.findById(1L))
                .thenReturn(java.util.Optional.of(sessionEntity));
        when(ecosSessionRepository.save(any(EcosSessionEntity.class)))
                .thenReturn(sessionEntity);

        assertDoesNotThrow(() -> sessionComponent.endSession(1L));
    }
}
