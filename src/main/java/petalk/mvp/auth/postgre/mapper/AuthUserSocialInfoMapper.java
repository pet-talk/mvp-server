package petalk.mvp.auth.postgre.mapper;

import petalk.mvp.auth.domain.SocialAuthUser;
import petalk.mvp.auth.domain.User;
import petalk.mvp.auth.postgre.model.AuthUserSocialInfoJpa;
import petalk.mvp.core.Mapper;

@Mapper
public class AuthUserSocialInfoMapper {

    public AuthUserSocialInfoJpa from(User user, SocialAuthUser socialAuthUser) {
        return new AuthUserSocialInfoJpa(user.getId().getValue(), socialAuthUser.getEmail(), socialAuthUser.getSocialType(), socialAuthUser.getSocialId().getValue(), socialAuthUser.getName());
    }
}
