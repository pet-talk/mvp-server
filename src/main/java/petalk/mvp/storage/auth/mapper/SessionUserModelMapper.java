package petalk.mvp.storage.auth.mapper;

import petalk.mvp.domain.auth.SessionUserInfo;

import petalk.mvp.core.Mapper;
import petalk.mvp.support.auth.AuthRoleAuthority;
import petalk.mvp.support.auth.Provider;

@Mapper
public class SessionUserModelMapper {

    public Provider from(SessionUserInfo user) {
        AuthRoleAuthority authRoleAuthority = AuthRoleAuthority.from(user.getAuthority());
        return Provider.of(user.getId().getValue(), authRoleAuthority);
    }
}
