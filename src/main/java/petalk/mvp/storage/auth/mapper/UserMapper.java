package petalk.mvp.storage.auth.mapper;

import petalk.mvp.domain.auth.AuthUser;
import petalk.mvp.domain.auth.SocialAuthId;
import petalk.mvp.domain.auth.UserSocialInfo;
import petalk.mvp.storage.postgre.model.AuthUserJpa;
import petalk.mvp.core.Mapper;
import petalk.mvp.storage.postgre.model.AuthUserSocialInfoJpa;

/**
 * 이 클래스는 User 객체를 매핑하는 작업을 담당합니다.
 */
@Mapper
public class UserMapper {
    public AuthUser from(AuthUserJpa userJpa, AuthUserSocialInfoJpa socialInfoJpa) {
        return AuthUser.exist(
                AuthUser.UserId.from(userJpa.getId()),
                toUserSocialInfo(socialInfoJpa),
                userJpa.getNickname(),
                userJpa.getAuthorityType(),
                userJpa.getRegistrationDate());
    }

    private UserSocialInfo toUserSocialInfo(AuthUserSocialInfoJpa socialInfoJpa) {

        return UserSocialInfo.exist(
                UserSocialInfo.SocialInfoId.from(socialInfoJpa.getId()),
                socialInfoJpa.getEmail(),
                socialInfoJpa.getSocialType(),
                SocialAuthId.from(socialInfoJpa.getSocialId()),
                socialInfoJpa.getSocialName());
    }
}
