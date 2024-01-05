package petalk.mvp.auth.present.controller;

import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Profile("test")
@RestController
public class AuthenticateTestController {

    private final String AUTHORIZATION_HEADER = "Authorization";
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(AuthenticateTestController.class);

    @PostMapping("/auth/authenticate/naver/profile")
    public ResponseEntity<String> test(@RequestHeader(AUTHORIZATION_HEADER) String authorization) {

        logger.debug("naver profile request: {}", authorization);

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

    @GetMapping("/auth/authenticate/naver/token")
    public ResponseEntity<String> test(@RequestParam String grant_type, @RequestParam String client_id, @RequestParam String client_secret, @RequestParam String redirect_uri, @RequestParam String code, @RequestParam String state) {
        logger.debug("naver token request grant_type: {}", grant_type);
        logger.debug("naver token request client_id: {}", client_id);
        logger.debug("naver token request client_secret: {}", client_secret);
        logger.debug("naver token request redirect_uri: {}", redirect_uri);
        logger.debug("naver token request code: {}", code);
        logger.debug("naver token request state: {}", state);

        String response = "{\n" +
                "    \"access_token\":\"AAAAQosjWDJieBiQZc3to9YQp6HDLvrmyKC+6+iZ3gq7qrkqf50ljZC+Lgoqrg\",\n" +
                "    \"refresh_token\":\"c8ceMEJisO4Se7uGisHoX0f5JEii7JnipglQipkOn5Zp3tyP7dHQoP0zNKHUq2gY\",\n" +
                "    \"token_type\":\"bearer\",\n" +
                "    \"expires_in\":\"3600\"\n" +
                "}";

        return ResponseEntity.ok().body(response);
    }

}
