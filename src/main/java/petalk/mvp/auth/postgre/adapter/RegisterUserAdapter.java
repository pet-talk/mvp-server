package petalk.mvp.auth.postgre.adapter;

import lombok.RequiredArgsConstructor;
import petalk.mvp.auth.application.command.out.RegisterUserPort;
import petalk.mvp.auth.domain.User;
import petalk.mvp.auth.postgre.mapper.AuthUserJpaMapper;
import petalk.mvp.auth.postgre.model.AuthUserJpa;
import petalk.mvp.auth.postgre.repository.AuthUserJpaRepository;
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
