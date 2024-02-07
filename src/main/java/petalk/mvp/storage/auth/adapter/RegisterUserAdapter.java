package petalk.mvp.storage.auth.adapter;

import lombok.RequiredArgsConstructor;
import petalk.mvp.application.auth.command.out.RegisterUserPort;
import petalk.mvp.domain.auth.AuthUser;
import petalk.mvp.storage.auth.mapper.AuthUserJpaMapper;
import petalk.mvp.storage.auth.mapper.AuthUserSocialInfoMapper;
import petalk.mvp.storage.postgre.AuthUserSocialInfoJpaRepository;
import petalk.mvp.storage.postgre.model.AuthUserJpa;
import petalk.mvp.storage.postgre.AuthUserJpaRepository;
import petalk.mvp.core.PersistenceAdapter;
import petalk.mvp.storage.postgre.model.AuthUserSocialInfoJpa;

@PersistenceAdapter
@RequiredArgsConstructor
public class RegisterUserAdapter implements RegisterUserPort {

    private final AuthUserJpaMapper authUserJpaMapper;
    private final AuthUserJpaRepository authUserJpaRepository;

    private final AuthUserSocialInfoMapper socialInfoMapper;
    private final AuthUserSocialInfoJpaRepository socialInfoJpaRepository;

    @Override
    public void registerUser(AuthUser user) {
        AuthUserJpa authUserJpa = authUserJpaMapper.from(user);
        AuthUserSocialInfoJpa socialInfoJpa = socialInfoMapper.from(user);

        socialInfoJpaRepository.save(socialInfoJpa);
        authUserJpaRepository.save(authUserJpa);
    }
}
