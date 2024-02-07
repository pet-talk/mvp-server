package petalk.mvp.http.auth.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import petalk.mvp.domain.auth.SocialType;
import petalk.mvp.http.auth.request.GoogleProfileRequester;
import petalk.mvp.http.auth.request.NaverProfileRequester;
import petalk.mvp.http.auth.request.SocialProfileRequester;

/**
 * 소셜 프로필 http 요청자를 생성하는 팩토리 클래스입니다.
 *
 * 제공 받은 Authenticator 를 통해 어떤 소셜 로그인인지 판단하여 해당 소셜 로그인에 맞는 GetSocialProfileRequester 를 반환합니다.
 */
@Component
@RequiredArgsConstructor
public class GetProfileRequesterFactory {

    private final NaverProfileRequester getNaverProfileRequester;

    private final GoogleProfileRequester getGoogleProfileRequester;

    public SocialProfileRequester getProfileRequester(SocialType socialType) {
        if (getNaverProfileRequester.isCorrectType(socialType)) {
            return getNaverProfileRequester;
        }

        if (getGoogleProfileRequester.isCorrectType(socialType)) {
            return getGoogleProfileRequester;
        }
        return null;
    }
}
