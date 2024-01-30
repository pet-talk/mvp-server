package petalk.mvp.core.acceptance.utils;

import io.restassured.RestAssured;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;

/**
 * 인수 테스트에 공통적으로 사용되는 기능을 정의한다.
 */
@Profile("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractAcceptanceTest {

    @LocalServerPort
    int port;

    @Autowired
    protected EntityManager entityManager;
    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    @BeforeEach
    void init() {
        RestAssured.port = port;
        databaseCleanUp.afterPropertiesSet();
        databaseCleanUp.execute();
    }
}