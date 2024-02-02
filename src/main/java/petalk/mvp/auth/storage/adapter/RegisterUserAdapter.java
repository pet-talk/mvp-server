package petalk.mvp.auth.storage.adapter;

import lombok.RequiredArgsConstructor;
import petalk.mvp.auth.domain.command.out.RegisterUserPort;
import petalk.mvp.auth.domain.command.User;
import petalk.mvp.auth.storage.mapper.AuthUserJpaMapper;
import petalk.mvp.auth.storage.model.AuthUserJpa;
import petalk.mvp.auth.storage.repository.AuthUserJpaRepository;
import petalk.mvp.core.PersistenceAdapter;

@PersistenceAdapter
@RequiredArgsConstructor
public class RegisterUserAdapter implements RegisterUserPort {

    private final AuthUserJpaMapper authUserJpaMapper;
    private final AuthUserJpaRepository authUserJpaRepository;

    @Override
    public void registerUser(User user) {
        AuthUserJpa authUserJpa = authUserJpaMapper.from(user);

        authUserJpaRepository.save(authUserJpa);
    }
}
