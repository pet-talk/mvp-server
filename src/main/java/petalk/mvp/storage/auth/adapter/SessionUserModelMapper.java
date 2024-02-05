package petalk.mvp.storage.auth.adapter;

import petalk.mvp.domain.auth.command.SessionUserInfo;

import petalk.mvp.core.Mapper;
import petalk.mvp.core.auth.AuthRoleAuthority;
import petalk.mvp.core.auth.SessionUserModel;

@Mapper
public class SessionUserModelMapper {

    public SessionUserModel from(SessionUserInfo user) {
        AuthRoleAuthority authRoleAuthority = AuthRoleAuthority.from(user.getAuthority());
        return SessionUserModel.of(user.getId().getValue(), authRoleAuthority);
    }
}
