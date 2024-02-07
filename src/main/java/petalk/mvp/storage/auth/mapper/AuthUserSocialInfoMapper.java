package petalk.mvp.storage.auth.mapper;

import petalk.mvp.core.Mapper;
import petalk.mvp.domain.auth.AuthUser;
import petalk.mvp.storage.postgre.model.AuthUserSocialInfoJpa;

@Mapper
public class AuthUserSocialInfoMapper {

    public AuthUserSocialInfoJpa from(AuthUser user) {
        return new AuthUserSocialInfoJpa(user.getId().getValue(), user.getEmail(), user.getSocialType(), user.getSocialId().getValue(), user.getSocialName());
    }
}
