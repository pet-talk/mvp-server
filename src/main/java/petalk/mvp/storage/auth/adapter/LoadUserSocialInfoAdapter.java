package petalk.mvp.storage.auth.adapter;

import lombok.RequiredArgsConstructor;
import petalk.mvp.domain.auth.command.SocialAuthId;
import petalk.mvp.domain.auth.command.SocialAuthUser;
import petalk.mvp.domain.auth.command.SocialType;
import petalk.mvp.domain.auth.command.UserSocialInfo;
import petalk.mvp.domain.auth.command.out.LoadUserSocialInfoPort;
import petalk.mvp.storage.postgre.AuthUserSocialInfoJpaRepository;
import petalk.mvp.core.PersistenceAdapter;

import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class LoadUserSocialInfoAdapter implements LoadUserSocialInfoPort {
    private final AuthUserSocialInfoJpaRepository socialInfoRepository;
    private final UserSocialInfoMapper userSocialInfoMapper;

    @Override
    public Optional<UserSocialInfo> loadSocialInfo(SocialAuthUser socialAuthUser) {
        SocialAuthId socialId = socialAuthUser.getSocialId();
        SocialType socialType = socialAuthUser.getSocialType();

        return socialInfoRepository
                .findOneBySocialIdAndSocialType(socialId.getValue(), socialType)
                .map(userSocialInfoMapper::from);
    }
}
