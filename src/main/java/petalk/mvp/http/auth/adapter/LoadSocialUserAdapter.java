package petalk.mvp.http.auth.adapter;

import petalk.mvp.application.auth.command.out.LoadSocialUserPort;
import petalk.mvp.core.PersistenceAdapter;
import petalk.mvp.domain.auth.AuthorizationCode;
import petalk.mvp.domain.auth.SocialAuthUser;
import petalk.mvp.domain.auth.SocialType;

import java.util.Optional;

/**
 * 소셜 사용자 정보 로드 어댑터 클래스입니다.
 * <p>
 * 이 클래스는 LoadSocialUserPort 인터페이스의 구현입니다. <br>
 * 제공된 인증기를 활용하여 소셜 사용자 정보를 로드하는 역할을 합니다.
 */
@PersistenceAdapter
public class LoadSocialUserAdapter implements LoadSocialUserPort {

    private final ProfileKeyReaderFactory profileKeyReaderFactory;
    private final ProfileReaderFactory profileReaderFactory;


    public LoadSocialUserAdapter(ProfileKeyReaderFactory profileKeyReaderFactory, ProfileReaderFactory profileReaderFactory) {
        this.profileKeyReaderFactory = profileKeyReaderFactory;
        this.profileReaderFactory = profileReaderFactory;
    }

    @Override
    public Optional<SocialAuthUser> loadSocialUser(AuthorizationCode code, SocialType socialType) {
        SocialProfileKeyReader profileKeyReader = profileKeyReaderFactory.getOauthTokenRequester(socialType);
        SocialProfileReader profileRequester = profileReaderFactory.getProfileRequester(socialType);

        return profileKeyReader
                .getKey(code)
                .flatMap(profileRequester::getProfile)
                .map(SocialProfile::toSocialAuthUser);

    }
}
