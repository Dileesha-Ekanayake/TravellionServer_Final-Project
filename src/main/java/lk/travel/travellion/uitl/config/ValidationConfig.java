package lk.travel.travellion.uitl.config;

import jakarta.persistence.EntityManager;
import lk.dileesha.jpavalidator.DuplicateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up validation-related components.
 * Provides a DuplicateValidator bean for ensuring entity uniqueness
 * within the persistence context.
 */
@Configuration
public class ValidationConfig {

    /**
     * Creates and provides a DuplicateValidator bean for validating entity uniqueness
     * using the provided EntityManager.
     *
     * @param entityManager the EntityManager used to interact with the persistence context
     *                      for validating entity uniqueness.
     * @return an instance of DuplicateValidator configured with the specified EntityManager.
     */
    @Bean
    public DuplicateValidator duplicateValidator(EntityManager entityManager) {
        return new DuplicateValidator(entityManager);
    }
}
