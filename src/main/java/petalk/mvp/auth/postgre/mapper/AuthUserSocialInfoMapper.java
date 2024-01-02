package petalk.mvp.auth.postgre.mapper;

import petalk.mvp.auth.domain.UserSocialInfo;
import petalk.mvp.auth.postgre.model.AuthUserSocialInfoJpa;
import petalk.mvp.core.Mapper;

@Mapper
public class AuthUserSocialInfoMapper {

    public AuthUserSocialInfoJpa from(UserSocialInfo userSocialInfo) {
        return new AuthUserSocialInfoJpa(userSocialInfo.getUserId().getValue(), userSocialInfo.getEmail(), userSocialInfo.getSocialType(), userSocialInfo.getSocialId().getValue(), userSocialInfo.getSocialName());
    }
}
