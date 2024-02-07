package petalk.mvp.storage.auth.adapter;

import lombok.RequiredArgsConstructor;
import petalk.mvp.application.auth.command.out.LoadUserPort;
import petalk.mvp.core.PersistenceAdapter;
import petalk.mvp.domain.auth.AuthUser;
import petalk.mvp.domain.auth.SocialAuthId;
import petalk.mvp.domain.auth.SocialAuthUser;
import petalk.mvp.storage.auth.mapper.UserMapper;
import petalk.mvp.storage.postgre.AuthUserJpaRepository;
import petalk.mvp.storage.postgre.AuthUserSocialInfoJpaRepository;
import petalk.mvp.storage.postgre.model.AuthUserSocialInfoJpa;

import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class LoadUserAdapter implements LoadUserPort {

    private final AuthUserJpaRepository userRepository;
    private final AuthUserSocialInfoJpaRepository socialInfoRepository;

    private final UserMapper userMapper;

    @Override
    public Optional<AuthUser> loadUser(SocialAuthUser socialAuthUser) {
        SocialAuthId socialId = socialAuthUser.getSocialId();

        Optional<AuthUserSocialInfoJpa> optionalSocialInfo = socialInfoRepository
                .findOneBySocialIdAndSocialType(socialId.getValue(), socialAuthUser.getSocialType());

        if (optionalSocialInfo.isPresent()) {
            AuthUserSocialInfoJpa socialInfo = optionalSocialInfo.get();
            return userRepository
                    .findById(socialInfo.getUserId())
                    .map(user -> userMapper.from(user, socialInfo));
        }

        return Optional.empty();
    }
}
