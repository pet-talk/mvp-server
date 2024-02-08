package petalk.mvp.http.auth.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import petalk.mvp.core.annotation.UnitTest;
import petalk.mvp.http.auth.adapter.SocialProfile;
import petalk.mvp.http.auth.adapter.SocialProfileReader;
import petalk.mvp.http.auth.adapter.SocialTokenResponse;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

/**
 * 네이버 프로필 요청을 테스트합니다.
 */
@UnitTest
@DisplayName("네이버 프로필 요청 유닛 테스트")
class NaverProfileRequesterTest {
    private final RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final SocialProfileReader profileReader = new NaverProfileRequester(restTemplate, "url");

    /**
     * @given 토큰을 받았고
     * @given 네이버에서 승인된 응답을 받는다면
     * @when 소셜 프로필을 요청했을 때
     * @then 네이버 프로필을 반환한다.
     */
    @Test
    @DisplayName("승인된 네이버 프로필 응답을 소셜 프로필로 변환한다.")
    void whenRequestNaverProfileThenReturnNaverProfile() throws Exception {
        //given
        SocialTokenResponse tokenResponse = new NaverTokenResponse("token", "refresh", "type", "3600");

        //given
        ResponseEntity<NaverProfile> response = getSuccessResponse();

        given(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(NaverProfile.class)))
                .willReturn(response);

        //when
        Optional<SocialProfile> result = profileReader.getProfile(tokenResponse);

        //then
        assertThat(result)
                .isPresent().get()
                .isInstanceOf(NaverProfile.class)
                .extracting("info")
                .extracting("id", "email", "nickname", "name", "gender", "age", "birthday", "profileImage", "birthYear", "mobile")
                .containsExactly("32742776", "openapi@naver.com", "OpenAPI", "오픈 API", "F", "40-49", "10-01", "https://ssl.pstatic.net/static/pwe/address/nodata_33x33.gif", "1900", "010-0000-0000");

    }

    private ResponseEntity<NaverProfile> getSuccessResponse() throws JsonProcessingException {
        String str =  "{\n" +
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

        NaverProfile response = objectMapper.readValue(str, NaverProfile.class);


        return ResponseEntity.ok().body(response);
    }

    /**
     * @given 토큰을 받았고
     * @given 네이버에서 승인되지 않은 응답을 받는다면
     * @when 소셜 프로필을 요청했을 때
     * @then 빈 값을 반환한다.
     */
    @Test
    @DisplayName("승인되지 않은 네이버 프로필 응답은 빈 값을 반환한다.")
    void whenFailedThenReturnEmpty() throws Exception{
        //given
        SocialTokenResponse tokenResponse = new NaverTokenResponse("token", "refresh", "type", "3600");

        //given
        ResponseEntity<NaverProfile> response = getFailedResponse();

        given(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(NaverProfile.class)))
                .willReturn(response);

        //when
        Optional<SocialProfile> result = profileReader.getProfile(tokenResponse);

        //then
        assertThat(result).isEmpty();
    }

    private ResponseEntity<NaverProfile> getFailedResponse() throws JsonProcessingException {

        NaverProfile response = null;


        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}