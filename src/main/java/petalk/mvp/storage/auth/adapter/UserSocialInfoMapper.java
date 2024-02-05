package petalk.mvp.storage.auth.adapter;

import petalk.mvp.domain.auth.command.SocialAuthId;
import petalk.mvp.domain.auth.command.User;
import petalk.mvp.domain.auth.command.UserSocialInfo;
import petalk.mvp.storage.postgre.model.AuthUserSocialInfoJpa;
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
