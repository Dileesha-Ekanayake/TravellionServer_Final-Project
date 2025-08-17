package lk.travel.travellion.projection;

import org.springframework.beans.factory.annotation.Value;

/**
 * Projection for {@link lk.travel.travellion.entity.User}
 */
public interface UserAccountLocked {
    @Value("#{target.accountLocked}") // Ensure it maps correctly
    Boolean getAccountLocked();
}

