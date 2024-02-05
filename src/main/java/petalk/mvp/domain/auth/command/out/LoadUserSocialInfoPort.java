package petalk.mvp.domain.auth.command.out;

import petalk.mvp.domain.auth.command.SocialAuthUser;
import petalk.mvp.domain.auth.command.UserSocialInfo;

import java.util.Optional;

public interface LoadUserSocialInfoPort {

    Optional<UserSocialInfo> loadSocialInfo(SocialAuthUser socialAuthUser);
}
