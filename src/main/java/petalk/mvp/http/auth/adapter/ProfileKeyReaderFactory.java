package petalk.mvp.http.auth.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import petalk.mvp.domain.auth.SocialType;

import java.util.Arrays;
import java.util.List;


/**
 * 소셜 프로필 키 리더를 생성하는 팩토리 클래스입니다.
 * <p>
 * 제공 받은 소셜 타입을 통해 어떤 소셜 로그인인지 판단하여 해당 소셜 로그인에 맞는 소셜 프로필 키 리더를 반환합니다.
 */
@Component
public class ProfileKeyReaderFactory {

    private final List<SocialProfileKeyReader> profileKeyReaders;

    @Autowired
    public ProfileKeyReaderFactory(SocialProfileKeyReader... profileKeyReaders) {
        this.profileKeyReaders = Arrays.asList(profileKeyReaders);
    }

    public SocialProfileKeyReader getOauthTokenRequester(SocialType socialType) {

        return profileKeyReaders
                .stream()
                .filter(socialTokenRequester -> socialTokenRequester.isCorrectType(socialType))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 소셜 로그인입니다."));

    }
}
