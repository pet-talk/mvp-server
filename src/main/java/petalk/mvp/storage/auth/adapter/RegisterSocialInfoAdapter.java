package petalk.mvp.storage.auth.adapter;

import lombok.RequiredArgsConstructor;

import petalk.mvp.application.auth.command.out.RegisterSocialInfoPort;
import petalk.mvp.domain.auth.UserSocialInfo;

import petalk.mvp.storage.auth.mapper.AuthUserSocialInfoMapper;
import petalk.mvp.storage.postgre.model.AuthUserSocialInfoJpa;
import petalk.mvp.storage.postgre.AuthUserSocialInfoJpaRepository;

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
