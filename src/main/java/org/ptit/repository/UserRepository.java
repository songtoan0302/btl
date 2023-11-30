package org.ptit.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.ptit.domain.User593;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link User593} entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User593, Long> {
    Optional<User593> findOneByActivationKey(String activationKey);
    List<User593> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant dateTime);
    Optional<User593> findOneByResetKey(String resetKey);
    Optional<User593> findOneByEmailIgnoreCase(String email);
    Optional<User593> findOneByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    Optional<User593> findOneWithAuthoritiesByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    Optional<User593> findOneWithAuthoritiesByEmailIgnoreCase(String email);

    Page<User593> findAllByIdNotNullAndActivatedIsTrue(Pageable pageable);
}
