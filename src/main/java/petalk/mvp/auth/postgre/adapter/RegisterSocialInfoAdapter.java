package petalk.mvp.auth.postgre.adapter;

import lombok.RequiredArgsConstructor;
import petalk.mvp.auth.application.command.out.RegisterSocialInfoPort;
import petalk.mvp.auth.domain.UserSocialInfo;
import petalk.mvp.auth.postgre.mapper.AuthUserSocialInfoMapper;
import petalk.mvp.auth.postgre.model.AuthUserSocialInfoJpa;
import petalk.mvp.auth.postgre.repository.AuthUserSocialInfoJpaRepository;
import petalk.mvp.core.PersistenceAdapter;

@PersistenceAdapter
@RequiredArgsConstructor
public class RegisterSocialInfoAdapter implements RegisterSocialInfoPort {

    private final AuthUserSocialInfoMapper socialInfoMapper;
    private final AuthUserSocialInfoJpaRepository socialInfoJpaRepository;
    @Override
    public void registerSocialInfo(UserSocialInfo userSocialInfo) {
        AuthUserSocialInfoJpa socialInfoJpa = socialInfoMapper.from(userSocialInfo);

        socialInfoJpaRepository.save(socialInfoJpa);
    }
}
