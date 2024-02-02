package petalk.mvp.auth.acceptance.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Profile("test")
@RestController
public class AuthenticateTestController {

    private final String NAVER_AUTHORIZATION_HEADER = "Authorization";
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(AuthenticateTestController.class);

    @GetMapping("/auth/test/authenticate/naver/profile")
    public ResponseEntity<String> profileNaver(@RequestHeader(NAVER_AUTHORIZATION_HEADER) String authorization) {

        logger.debug("naver profile request: {}", authorization);

        String response = """
                {
                    "resultcode": "00",
                    "message": "success",
                    "response": {
                        "email": "openapi@naver.com",
                        "nickname": "OpenAPI",
                        "profile_image": "https://ssl.pstatic.net/static/pwe/address/nodata_33x33.gif",
                        "age": "40-49",
                        "gender": "F",
                        "id": "32742776",
                        "name": "오픈 API",
                        "birthday": "10-01",
                        "birthyear": "1900",
                        "mobile": "010-0000-0000"
                    }
                }
                """;

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/auth/test/authenticate/naver/token")
    public ResponseEntity<String> tokenNaver(@RequestParam String grant_type, @RequestParam String client_id, @RequestParam String client_secret, @RequestParam String redirect_uri, @RequestParam String code, @RequestParam String state) {
        logger.debug("naver token request grant_type: {}", grant_type);
        logger.debug("naver token request client_id: {}", client_id);
        logger.debug("naver token request client_secret: {}", client_secret);
        logger.debug("naver token request redirect_uri: {}", redirect_uri);
        logger.debug("naver token request code: {}", code);
        logger.debug("naver token request state: {}", state);

        String response = """
                {
                    "access_token":"AAAAQosjWDJieBiQZc3to9YQp6HDLvrmyKC+6+iZ3gq7qrkqf50ljZC+Lgoqrg",
                    "refresh_token":"c8ceMEJisO4Se7uGisHoX0f5JEii7JnipglQipkOn5Zp3tyP7dHQoP0zNKHUq2gY",
                    "token_type":"bearer",
                    "expires_in":"3600"
                }""";

        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/auth/test/authenticate/google/profile")
    public ResponseEntity<String> profileGoogle(@RequestParam(name = "access_token") String authorization) {

        logger.debug("naver profile request: {}", authorization);

        String response = """
                {
                    "sub": "100159399304136315241",
                    "name": "펫톡",
                    "given_name": "펫톡",
                    "picture": "https://lh3.googleusercontent.com/a/ACg8ocIjhJYR-1OZEDqNeBAfXy8ZVbAhaNPPSa6iRZ21VLhU\\u003ds96-c",
                    "email": "pettalk2023@gmail.com",
                    "email_verified": true,
                    "locale": "ko"
                }
                """;

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/auth/test/authenticate/google/token")
    public ResponseEntity<String> tokenGoogle(@RequestBody GoogleRequest request) {
        logger.debug("naver token request grant_type: {}", request.getGrantType());
        logger.debug("naver token request client_id: {}", request.getClientId());
        logger.debug("naver token request client_secret: {}", request.getClientSecret());
        logger.debug("naver token request redirect_uri: {}", request.getRedirectUri());
        logger.debug("naver token request code: {}", request.getCode());

        String response = """
                {
                    "access_token": "ya29.a0AfB_byB5Ld-k2kr0tZzM5M0VufLWtWZQ2EbU81gEUjiRAr8Wx7RE3s8cg_qkN8AnPyLE3s6DjbjYYc0XJND5Bx-tNF9MBT_1Z2G2npHr90PwezQnJxpW8NRkocljCRT_bDsBgK8YEzxWzITRGeyE_fcgKGkWWnbCflHDaCgYKAYESARASFQHGX2MiRclYa4JcVE8lHTaBPBfE2g0171",
                    "expires_in": 3599,
                    "scope": "https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile openid",
                    "token_type": "Bearer",
                    "id_token": "eyJhbGciOiJSUzI1NiIsImtpZCI6Ijg1ZTU1MTA3NDY2YjdlMjk4MzYxOTljNThjNzU4MWY1YjkyM2JlNDQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI3NDcyMDA1NDg2MDgtZzY0YTJla3Vhc2hqazVjdnA3cHMzdWRnb2EzYnAwMHEuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI3NDcyMDA1NDg2MDgtZzY0YTJla3Vhc2hqazVjdnA3cHMzdWRnb2EzYnAwMHEuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDAxNTkzOTkzMDQxMzYzMTUyNDEiLCJlbWFpbCI6InBldHRhbGsyMDIzQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhdF9oYXNoIjoiZU5wak1acjNCcm94UU1RMVZpODlEQSIsIm5hbWUiOiLtjqvthqEiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EvQUNnOG9jSWpoSllSLTFPWkVEcU5lQkFmWHk4WlZiQWhhTlBQU2E2aVJaMjFWTGhVPXM5Ni1jIiwiZ2l2ZW5fbmFtZSI6Iu2Oq-2GoSIsImxvY2FsZSI6ImtvIiwiaWF0IjoxNzA2Njg5MzQ0LCJleHAiOjE3MDY2OTI5NDR9.Iaxv7YlLiGJ94fq1G-_7uthXbVnHYeuqtOLJq4m7PXNytlPiYKO4UErkBHNrU_rB8HtBT5nLehc4Qc6jMocdq26aDsZLwe5XduUCQKsO1twcNoJKOi0J_TqFZ7eeIOioWFx8ctEephWaanDSao5svqx5zHgDs_PSLxjIkC1AZC0ZocAtoQSjNj25_xB9Q56tVY8oDoZ4pfW-S63jBFijZk10VW_Xmd_WEROUe9OLp2RlgPNj-M8ytidiAAditG3uPKp6uV7HotqV4PIIIH8n5akUMyUBzhk7vQJrjqH5_1qCrgNoilO1OmlYn-OoJMN2ALXJINqwxvFpAq5utKlwPg"
                }
                """;

        return ResponseEntity.ok().body(response);
    }

    private static class GoogleRequest {
        @JsonProperty("grant_type")
        private String grantType;
        @JsonProperty("client_id")
        private String clientId;
        @JsonProperty("client_secret")
        private String clientSecret;
        @JsonProperty("redirect_uri")
        private String redirectUri;
        @JsonProperty("code")
        private String code;

        public GoogleRequest() {
        }

        public String getGrantType() {
            return grantType;
        }

        public String getClientId() {
            return clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public String getRedirectUri() {
            return redirectUri;
        }

        public String getCode() {
            return code;
        }
    }

}
