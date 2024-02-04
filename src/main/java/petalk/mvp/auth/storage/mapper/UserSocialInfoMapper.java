package petalk.mvp.auth.storage.mapper;

import petalk.mvp.auth.domain.command.SocialAuthId;
import petalk.mvp.auth.domain.command.User;
import petalk.mvp.auth.domain.command.UserSocialInfo;
import petalk.mvp.auth.storage.model.AuthUserSocialInfoJpa;
import petalk.mvp.core.Mapper;

/**
 * 이 클래스는 UserSocialInfo 객체를 매핑하는 작업을 담당합니다.
 */
@Mapper
public class UserSocialInfoMapper {

    public UserSocialInfo from(AuthUserSocialInfoJpa userSocialInfoJpa) {
        return UserSocialInfo.exist(
                UserSocialInfo.SocialInfoId.from(userSocialInfoJpa.getId()),
                User.UserId.from(userSocialInfoJpa.getUserId()),
                userSocialInfoJpa.getEmail(),
                userSocialInfoJpa.getSocialType(),
                SocialAuthId.from(userSocialInfoJpa.getSocialId()),
                userSocialInfoJpa.getSocialName());
    }
}
