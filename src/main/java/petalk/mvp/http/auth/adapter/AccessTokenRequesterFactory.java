package petalk.mvp.http.auth.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import petalk.mvp.domain.auth.SocialType;

import java.util.Arrays;
import java.util.List;


/**
 * 소셜 액세스 토큰 http 요청자를 생성하는 팩토리 클래스입니다.
 * <p>
 * 제공 받은 Authenticator 를 통해 어떤 소셜 로그인인지 판단하여 해당 소셜 로그인에 맞는 GetSocialTokenRequester 를 반환합니다.
 */
@Component
public class AccessTokenRequesterFactory {

    private final List<SocialProfileKeyReader> profileKeyReaders;

    @Autowired
    public AccessTokenRequesterFactory(SocialProfileKeyReader... profileKeyReaders) {
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
