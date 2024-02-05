package petalk.mvp.storage.auth.adapter;

import lombok.RequiredArgsConstructor;
import petalk.mvp.domain.auth.command.out.RegisterUserPort;
import petalk.mvp.domain.auth.command.User;
import petalk.mvp.storage.postgre.model.AuthUserJpa;
import petalk.mvp.storage.postgre.AuthUserJpaRepository;
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
