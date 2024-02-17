package petalk.mvp.acceptance.auth.controller;

import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Profile("test")
@RestController
public class AuthenticateTestController {

    private final String NAVER_AUTHORIZATION_HEADER = "Authorization";
    private final Logger log = org.slf4j.LoggerFactory.getLogger(AuthenticateTestController.class);

    @GetMapping("/auth/test/authenticate/naver/profile")
    public ResponseEntity<String> profileNaver(@RequestHeader(NAVER_AUTHORIZATION_HEADER) String authorization) {

        log.debug("naver profile request: {}", authorization);

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

    @GetMapping("/auth/test/authenticate/google/profile")
    public ResponseEntity<String> profileGoogle(@RequestParam(name = "access_token") String authorization) {

        log.debug("naver profile request: {}", authorization);

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

    @GetMapping(value = "/auth/test/authenticate/kakao/profile", headers = "Authorization")
    public ResponseEntity<String> profileKakao(
            @RequestHeader("Authorization") String authorization
    ) {
        log.debug("naver profile request header authorization: {}", authorization);

        String response = """
                {
                    "id":123456789,
                    "connected_at": "2022-04-11T01:45:28Z",
                    "kakao_account": {
                        "profile_nickname_needs_agreement": false,
                        "profile": {
                            "nickname": "홍길동"
                        }
                    },
                    "properties":{
                        "${CUSTOM_PROPERTY_KEY}": "${CUSTOM_PROPERTY_VALUE}"
                    }
                }
                """;

        return ResponseEntity.ok().body(response);
    }

}
