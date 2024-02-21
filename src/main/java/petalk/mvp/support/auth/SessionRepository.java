package petalk.mvp.support.auth;

import java.util.Optional;

public interface SessionRepository {

    void save(Provider sessionModel);

    Optional<Provider> getProvider(String sessionId);
}
