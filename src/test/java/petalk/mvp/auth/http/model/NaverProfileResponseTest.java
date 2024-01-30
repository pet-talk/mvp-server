package petalk.mvp.auth.http.model;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import petalk.mvp.core.annotation.UnitTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 네이버 프로필 응답 모델을 테스트합니다.
 */
@UnitTest
@DisplayName("네이버 프로필 응답 유닛 테스트")
class NaverProfileResponseTest {
    private final Gson gson = new Gson();

    /**
     * @given 네이버에서 승인된 응답을 받고
     * @given 네이버 프로필 응답으로 변환했다면
     * @when 소셜 프로필로 변환했을 때
     * @then 네이버 프로필을 반환한다.
     */
    @Test
    @DisplayName("승인된 네이버 프로필 응답을 소셜 프로필로 변환한다.")
    void whenRequestNaverProfileThenReturnNaverProfile() {
        //given
        ResponseEntity<String> response = getSuccessResponse();

        //given
        NaverProfileResponse profileResponse = NaverProfileResponse.from(response, gson);

        //when
        Optional<SocialProfile> profile = profileResponse.mapProfile();

        //then
        assertThat(profile).isPresent()
                .get()
                .extracting("resultCode", "message")
                .containsExactly("00", "success");

        assertThat(profile).isPresent()
                .get()
                .isInstanceOf(NaverProfile.class)
                .extracting("response")
                .extracting("id", "email", "nickname", "name", "gender", "age", "birthday", "profileImage", "birthYear", "mobile")
                .containsExactly("32742776", "openapi@naver.com", "OpenAPI", "오픈 API", "F", "40-49", "10-01", "https://ssl.pstatic.net/static/pwe/address/nodata_33x33.gif", "1900", "010-0000-0000");

    }

    private static ResponseEntity<String> getSuccessResponse() {
        String response =  "{\n" +
                "  \"resultcode\": \"00\",\n" +
                "  \"message\": \"success\",\n" +
                "  \"response\": {\n" +
                "    \"email\": \"openapi@naver.com\",\n" +
                "    \"nickname\": \"OpenAPI\",\n" +
                "    \"profile_image\": \"https://ssl.pstatic.net/static/pwe/address/nodata_33x33.gif\",\n" +
                "    \"age\": \"40-49\",\n" +
                "    \"gender\": \"F\",\n" +
                "    \"id\": \"32742776\",\n" +
                "    \"name\": \"오픈 API\",\n" +
                "    \"birthday\": \"10-01\",\n" +
                "    \"birthyear\": \"1900\",\n" +
                "    \"mobile\": \"010-0000-0000\"\n" +
                "  }\n" +
                "}";

        return ResponseEntity.ok().body(response);
    }

    /**
     * @given 네이버에서 승인되지 않은 응답을 받고
     * @given 네이버 프로필 응답으로 변환했다면
     * @when 소셜 프로필로 변환했을 때
     * @then 빈 값을 반환한다.
     */
    @Test
    @DisplayName("승인되지 않은 네이버 프로필 응답은 빈 값을 반환한다.")
    void whenFailedThenReturnEmpty() {
        //given
        ResponseEntity<String> response = getFailedResponse();

        //given
        NaverProfileResponse profileResponse = NaverProfileResponse.from(response, gson);

        //when
        Optional<SocialProfile> profile = profileResponse.mapProfile();

        //then
        assertThat(profile).isEmpty();
    }

    private static ResponseEntity<String> getFailedResponse() {
        String response =  "";

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}