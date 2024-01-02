package petalk.mvp.auth.postgre.adapter;

import lombok.RequiredArgsConstructor;
import petalk.mvp.auth.application.command.out.LoadUserPort;
import petalk.mvp.auth.domain.SocialAuthId;
import petalk.mvp.auth.domain.SocialAuthUser;
import petalk.mvp.auth.domain.SocialType;
import petalk.mvp.auth.domain.User;
import petalk.mvp.auth.postgre.mapper.UserMapper;
import petalk.mvp.auth.postgre.model.AuthUserSocialInfoJpa;
import petalk.mvp.auth.postgre.repository.AuthUserJpaRepository;
import petalk.mvp.auth.postgre.repository.AuthUserSocialInfoJpaRepository;
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
