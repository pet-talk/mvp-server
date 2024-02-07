package petalk.mvp.application.auth.command.out;

import petalk.mvp.domain.auth.SocialAuthUser;
import petalk.mvp.domain.auth.UserSocialInfo;

import java.util.Optional;

public interface LoadUserSocialInfoPort {

    Optional<UserSocialInfo> loadSocialInfo(SocialAuthUser socialAuthUser);
}
