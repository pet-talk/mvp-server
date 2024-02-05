package petalk.mvp.core.annotation;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Tag;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

/**
 * 인수 테스트 클래스임을 명시하는 어노테이션
 */
@Tag("acceptanceTest")
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public @interface AcceptanceTest {

}
