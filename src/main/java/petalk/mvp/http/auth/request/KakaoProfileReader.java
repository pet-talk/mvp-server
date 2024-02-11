package petalk.mvp.http.auth.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;
import petalk.mvp.domain.auth.SocialType;

// import auth adapter
import petalk.mvp.http.auth.adapter.SocialProfile;
import petalk.mvp.http.auth.adapter.SocialProfileReader;
import petalk.mvp.http.auth.adapter.SocialTokenResponse;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 카카오 프로필을 불러오는 구현체입니다.
 */
@Component
@Slf4j
public class KakaoProfileReader implements SocialProfileReader {
    private final ObjectMapper objectMapper;
    private final Base64 base64;

    public KakaoProfileReader(ObjectMapper objectMapper, Base64 base64) {
        this.objectMapper = objectMapper;
        this.base64 = base64;
    }

    @Override
    public Optional<SocialProfile> getProfile(SocialTokenResponse tokenResponse) {
        String key = tokenResponse.generateKey();

        String profileStr = extractJson(key);

        log.debug("kakao profile key: {}", profileStr);

        try {
            KakaoProfile kakaoProfile = objectMapper.readValue(profileStr, KakaoProfile.class);
            return Optional.of(kakaoProfile);
        } catch (Exception e) {
            log.error("kakao profile parsing error", e);
            return Optional.empty();
        }
    }
    private String extractJson(String key) {
        // base64 디코딩
        String content = new String(base64.decode(key.getBytes()));
        log.debug("kakao profile key decoding: {}", content);

        // {"aud": ... } 형태의 JSON 문자열을 추출
        Pattern pattern = Pattern.compile("\\{[^{}]*\"aud\":\"[^\"]*\"[^{}]*\\}");
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(0); // Return the first match that looks like a JSON object
        }
        return null;
    }

    @Override
    public boolean isCorrectType(SocialType type) {
        return SocialType.KAKAO.equals(type);
    }
}
