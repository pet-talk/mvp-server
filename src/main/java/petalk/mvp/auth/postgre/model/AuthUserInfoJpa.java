package petalk.mvp.auth.postgre.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_infoes")
public class AuthUserInfoJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_info_id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    public AuthUserInfoJpa(Long id, UUID userId) {
        this.id = id;
        this.userId = userId;
    }
}
