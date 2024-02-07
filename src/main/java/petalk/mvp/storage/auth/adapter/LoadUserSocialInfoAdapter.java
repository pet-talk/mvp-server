package petalk.mvp.storage.auth.adapter;

import lombok.RequiredArgsConstructor;
import petalk.mvp.domain.auth.SocialAuthId;
import petalk.mvp.domain.auth.SocialAuthUser;
import petalk.mvp.domain.auth.SocialType;
import petalk.mvp.domain.auth.UserSocialInfo;
import petalk.mvp.application.auth.command.out.LoadUserSocialInfoPort;
import petalk.mvp.storage.auth.mapper.UserSocialInfoMapper;
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
