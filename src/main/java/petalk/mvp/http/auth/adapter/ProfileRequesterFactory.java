package petalk.mvp.http.auth.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import petalk.mvp.domain.auth.SocialType;

import java.util.Arrays;
import java.util.List;

/**
 * 소셜 프로필 http 요청자를 생성하는 팩토리 클래스입니다.
 *
 * 제공 받은 Authenticator 를 통해 어떤 소셜 로그인인지 판단하여 해당 소셜 로그인에 맞는 GetSocialProfileRequester 를 반환합니다.
 */
@Component
public class ProfileRequesterFactory {

    private final List<SocialProfileReader> socialProfileReaders;

    @Autowired
    public ProfileRequesterFactory(SocialProfileReader... socialProfileReaders) {
        this.socialProfileReaders = Arrays.asList(socialProfileReaders);
    }

    public SocialProfileReader getProfileRequester(SocialType socialType) {

        return socialProfileReaders
                .stream()
                .filter(socialTokenRequester -> socialTokenRequester.isCorrectType(socialType))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 소셜 로그인입니다."));
    }
}
