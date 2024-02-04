package petalk.mvp.auth.storage.adapter;

import lombok.RequiredArgsConstructor;
import petalk.mvp.auth.domain.command.SocialAuthId;
import petalk.mvp.auth.domain.command.SocialAuthUser;
import petalk.mvp.auth.domain.command.SocialType;
import petalk.mvp.auth.domain.command.UserSocialInfo;
import petalk.mvp.auth.domain.command.out.LoadUserSocialInfoPort;
import petalk.mvp.auth.storage.mapper.UserSocialInfoMapper;
import petalk.mvp.auth.storage.repository.AuthUserSocialInfoJpaRepository;
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
