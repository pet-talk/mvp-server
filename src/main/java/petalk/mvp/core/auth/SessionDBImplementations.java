package petalk.mvp.core.auth;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 세션을 저장하는 클래스입니다.
 */
@Component
public class SessionDBImplementations {

    Map<UUID, SessionModel> concurrentHashMap = new ConcurrentHashMap<>();

    public void save(SessionModel session) {
        concurrentHashMap.put(session.getSessionId(), session);
    }

    /**
     * 세션을 찾습니다.
     *
     * 세션이 없거나 만료되었을 경우 Optional.empty() 를 반환합니다.
     * @param sessionId 세션 아이디
     * @return 세션
     */
    public Optional<SessionModel> findById(UUID sessionId) {
        SessionModel sessionModel = concurrentHashMap.get(sessionId);
        if (sessionModel == null) {
            return Optional.empty();
        }
        if (sessionModel.isExpired(LocalDateTime.now())) {
            concurrentHashMap.remove(sessionId);
            return Optional.empty();
        }
        return Optional.of(concurrentHashMap.get(sessionId));
    }

    public void deleteById(UUID sessionId) {
        concurrentHashMap.remove(sessionId);
    }

}
