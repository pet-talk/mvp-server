package petalk.mvp.core;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 요청 파라미터를 검증하기 위한 Validator를 나타내는 어노테이션
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Validator {
    @AliasFor(annotation = Component.class)
    String value() default "";
}
