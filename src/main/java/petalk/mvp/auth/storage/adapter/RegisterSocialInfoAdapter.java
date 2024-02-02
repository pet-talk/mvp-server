package petalk.mvp.auth.storage.adapter;

import lombok.RequiredArgsConstructor;
import petalk.mvp.auth.domain.command.out.RegisterSocialInfoPort;
import petalk.mvp.auth.domain.command.UserSocialInfo;
import petalk.mvp.auth.storage.mapper.AuthUserSocialInfoMapper;
import petalk.mvp.auth.storage.model.AuthUserSocialInfoJpa;
import petalk.mvp.auth.storage.repository.AuthUserSocialInfoJpaRepository;
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
