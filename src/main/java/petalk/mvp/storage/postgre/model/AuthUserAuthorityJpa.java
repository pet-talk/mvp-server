package petalk.mvp.storage.postgre.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import petalk.mvp.core.BaseTimeEntity;

/**
 * The AuthUserAuthorityJpa class represents a user authority entity in the authentication system.
 * It is an entity class that maps to the "user_authority" table in the database.
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_authorities")
public class AuthUserAuthorityJpa extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_authority_id", nullable = false)
    private Long id;

    @Column(name = "authority_type", nullable = false)
    private String authorityType;

    public AuthUserAuthorityJpa(Long id, String authorityType) {
        this.id = id;
        this.authorityType = authorityType;
    }
}
