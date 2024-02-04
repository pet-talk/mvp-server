package petalk.mvp.auth.storage.adapter;

import lombok.RequiredArgsConstructor;
import petalk.mvp.auth.domain.command.User;
import petalk.mvp.auth.domain.command.UserSocialInfo;
import petalk.mvp.auth.domain.command.out.LoadUserPort;
import petalk.mvp.auth.storage.mapper.UserMapper;
import petalk.mvp.auth.storage.repository.AuthUserJpaRepository;
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
