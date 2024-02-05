package petalk.mvp.storage.postgre.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import petalk.mvp.domain.auth.command.SocialType;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_social_info")
public class AuthUserSocialInfoJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_social_info_id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "email", nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_type", nullable = false)
    private SocialType socialType;

    @Column(name = "social_id", nullable = false)
    private String socialId;

    @Column(name = "social_name", nullable = false)
    private String socialName;

    public AuthUserSocialInfoJpa(Long id, UUID userId, String email, SocialType socialType, String socialId, String socialName) {
        this.id = id;
        this.userId = userId;
        this.email = email;
        this.socialType = socialType;
        this.socialId = socialId;
        this.socialName = socialName;
    }

    public AuthUserSocialInfoJpa(UUID userId, String email, SocialType socialType, String socialId, String socialName) {
        this.userId = userId;
        this.email = email;
        this.socialType = socialType;
        this.socialId = socialId;
        this.socialName = socialName;
    }
}
