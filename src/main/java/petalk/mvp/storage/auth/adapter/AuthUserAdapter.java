package petalk.mvp.storage.auth.adapter;

import lombok.RequiredArgsConstructor;
import petalk.mvp.application.auth.command.out.LoadUserPort;
import petalk.mvp.application.auth.command.out.RegisterUserPort;
import petalk.mvp.domain.auth.AuthUser;
import petalk.mvp.domain.auth.SocialAuthId;
import petalk.mvp.domain.auth.SocialAuthUser;
import petalk.mvp.storage.auth.mapper.UserJpaMapper;
import petalk.mvp.storage.auth.mapper.AuthUserSocialInfoMapper;
import petalk.mvp.storage.auth.mapper.UserMapper;
import petalk.mvp.storage.postgre.AuthUserSocialInfoJpaRepository;
import petalk.mvp.storage.postgre.model.UserJpa;
import petalk.mvp.storage.postgre.UserJpaRepository;
import petalk.mvp.core.PersistenceAdapter;
import petalk.mvp.storage.postgre.model.AuthUserSocialInfoJpa;

import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class AuthUserAdapter implements RegisterUserPort, LoadUserPort {

    private final UserJpaMapper userJpaMapper;
    private final UserJpaRepository userRepository;
    private final AuthUserSocialInfoMapper socialInfoMapper;
    private final AuthUserSocialInfoJpaRepository socialInfoRepository;
    private final UserMapper userMapper;


    @Override
    public void registerUser(AuthUser user) {
        UserJpa userJpa = userJpaMapper.from(user);
        AuthUserSocialInfoJpa socialInfoJpa = socialInfoMapper.from(user);

        socialInfoRepository.save(socialInfoJpa);
        userRepository.save(userJpa);
    }

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
