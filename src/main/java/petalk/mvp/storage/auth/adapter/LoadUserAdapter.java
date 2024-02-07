package petalk.mvp.storage.auth.adapter;

import lombok.RequiredArgsConstructor;
import petalk.mvp.domain.auth.User;
import petalk.mvp.domain.auth.UserSocialInfo;
import petalk.mvp.application.auth.command.out.LoadUserPort;
import petalk.mvp.storage.auth.mapper.UserMapper;
import petalk.mvp.storage.postgre.AuthUserJpaRepository;
import petalk.mvp.core.PersistenceAdapter;

import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class LoadUserAdapter implements LoadUserPort {

    private final AuthUserJpaRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<User> loadUser(UserSocialInfo socialInfo) {
        return userRepository
                .findById(socialInfo.getUserId().getValue())
                .map(userMapper::from);

    }
}
