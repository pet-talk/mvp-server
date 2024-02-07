package petalk.mvp.http.auth.adapter;

import petalk.mvp.application.auth.command.out.LoadSocialUserPort;
import petalk.mvp.core.PersistenceAdapter;
import petalk.mvp.domain.auth.AuthorizationCode;
import petalk.mvp.domain.auth.SocialAuthUser;
import petalk.mvp.domain.auth.SocialType;
import petalk.mvp.http.auth.request.SocialProfileRequester;
import petalk.mvp.http.auth.request.SocialTokenRequester;
import petalk.mvp.http.auth.request.SocialProfile;

import java.util.Optional;

/**
 * 소셜 사용자 정보 로드 어댑터 클래스입니다.
 * <p>
 * 이 클래스는 LoadSocialUserPort 인터페이스의 구현입니다.
 * 제공된 인증기를 활용하여 소셜 사용자 정보를 로드하는 역할을 합니다.
 */
@PersistenceAdapter
public class LoadSocialUserAdapter implements LoadSocialUserPort {

    private final GetAccessTokenRequesterFactory getAccessTokenRequesterFactory;
    private final GetProfileRequesterFactory getProfileRequesterFactory;


    public LoadSocialUserAdapter(GetAccessTokenRequesterFactory getAccessTokenRequesterFactory, GetProfileRequesterFactory getProfileRequesterFactory) {
        this.getAccessTokenRequesterFactory = getAccessTokenRequesterFactory;
        this.getProfileRequesterFactory = getProfileRequesterFactory;
    }

    @Override
    public Optional<SocialAuthUser> loadSocialUser(AuthorizationCode code, SocialType socialType) {
        SocialTokenRequester socialTokenRequester = getAccessTokenRequesterFactory.getOauthTokenRequester(socialType);
        SocialProfileRequester profileRequester = getProfileRequesterFactory.getProfileRequester(socialType);

        return socialTokenRequester
                .getAccessToken(code)
                .flatMap(profileRequester::getProfile)
                .map(SocialProfile::toSocialAuthUser);

    }
}
