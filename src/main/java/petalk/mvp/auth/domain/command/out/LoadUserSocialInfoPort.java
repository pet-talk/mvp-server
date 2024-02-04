package petalk.mvp.auth.domain.command.out;

import petalk.mvp.auth.domain.command.SocialAuthUser;
import petalk.mvp.auth.domain.command.UserSocialInfo;

import java.util.Optional;

public interface LoadUserSocialInfoPort {

    Optional<UserSocialInfo> loadSocialInfo(SocialAuthUser socialAuthUser);
}
