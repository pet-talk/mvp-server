package petalk.mvp.auth.http.adapter;

import petalk.mvp.auth.application.command.out.LoadSocialUserPort;
import petalk.mvp.auth.domain.Authenticator;
import petalk.mvp.auth.domain.SocialAuthUser;
import petalk.mvp.auth.http.factory.GetAccessTokenRequesterFactory;
import petalk.mvp.auth.http.factory.GetProfileRequesterFactory;
import petalk.mvp.auth.http.model.SocialProfile;
import petalk.mvp.auth.http.request.GetSocialProfileRequester;
import petalk.mvp.auth.http.request.GetSocialTokenRequester;
import petalk.mvp.core.PersistenceAdapter;

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
    public Optional<SocialAuthUser> loadSocialUser(Authenticator authenticator) {
        GetSocialTokenRequester getSocialTokenRequester = getAccessTokenRequesterFactory.getOauthTokenRequester(authenticator);
        GetSocialProfileRequester profileRequester = getProfileRequesterFactory.getProfileRequester(authenticator);

        return getSocialTokenRequester
                .getAccessToken(authenticator)
                .flatMap(accessToken -> profileRequester
                        .getProfile(accessToken)
                        .map(SocialProfile::toSocialAuthUser));

    }
}
