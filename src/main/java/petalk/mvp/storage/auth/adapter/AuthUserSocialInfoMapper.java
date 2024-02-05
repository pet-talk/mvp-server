package petalk.mvp.storage.auth.adapter;

import petalk.mvp.domain.auth.command.UserSocialInfo;
import petalk.mvp.storage.postgre.model.AuthUserSocialInfoJpa;
import petalk.mvp.core.Mapper;

@Mapper
public class AuthUserSocialInfoMapper {

    public AuthUserSocialInfoJpa from(UserSocialInfo userSocialInfo) {
        return new AuthUserSocialInfoJpa(userSocialInfo.getUserId().getValue(), userSocialInfo.getEmail(), userSocialInfo.getSocialType(), userSocialInfo.getSocialId().getValue(), userSocialInfo.getSocialName());
    }
}
