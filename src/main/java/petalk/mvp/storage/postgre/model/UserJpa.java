package petalk.mvp.storage.postgre.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;
import petalk.mvp.domain.auth.UserAuthority;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * JPA를 사용하여 데이터베이스에 저장된 인증된 사용자를 나타냅니다.
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users")
public class UserJpa extends BaseTimeEntity implements Persistable<UUID> {

    @Id
    @Column(name = "user_id", nullable = false)
    private UUID id;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "authority_type", nullable = false)
    private UserAuthority authorityType;

    public UserJpa(UUID id, String nickname, LocalDateTime registrationDate, UserAuthority authorityType) {
        this.id = id;
        this.nickname = nickname;
        this.registrationDate = registrationDate;
        this.authorityType = authorityType;
    }

    @Override
    public boolean isNew() {
        return getCreatedDate() == null;
    }
}
