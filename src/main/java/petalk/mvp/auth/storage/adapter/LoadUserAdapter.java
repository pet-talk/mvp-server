package petalk.mvp.auth.storage.adapter;

import lombok.RequiredArgsConstructor;
import petalk.mvp.auth.domain.command.out.LoadUserPort;
import petalk.mvp.auth.domain.command.SocialAuthId;
import petalk.mvp.auth.domain.command.SocialAuthUser;
import petalk.mvp.auth.domain.command.SocialType;
import petalk.mvp.auth.domain.command.User;
import petalk.mvp.auth.storage.mapper.UserMapper;
import petalk.mvp.auth.storage.model.AuthUserSocialInfoJpa;
import petalk.mvp.auth.storage.repository.AuthUserJpaRepository;
import petalk.mvp.auth.storage.repository.AuthUserSocialInfoJpaRepository;
import petalk.mvp.core.PersistenceAdapter;

import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class LoadUserAdapter implements LoadUserPort {

    private final AuthUserJpaRepository userRepository;
    private final AuthUserSocialInfoJpaRepository socialInfoRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<User> loadUser(SocialAuthUser socialAuthUser) {
        SocialAuthId socialId = socialAuthUser.getSocialId();
        SocialType socialType = socialAuthUser.getSocialType();

        Optional<AuthUserSocialInfoJpa> optionalSocialInfo = socialInfoRepository
                .findOneBySocialIdAndSocialType(socialId.getValue(), socialType);

        if (optionalSocialInfo.isPresent()) {
            AuthUserSocialInfoJpa socialInfo = optionalSocialInfo.get();
            return userRepository
                    .findById(socialInfo.getUserId())
                    .map(userMapper::from);
        }

        return Optional.empty();
    }
}
