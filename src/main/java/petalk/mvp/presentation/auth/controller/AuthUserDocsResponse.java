package petalk.mvp.presentation.auth.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import petalk.mvp.application.auth.response.AuthUserResponse;

@Getter
@Schema(description = "Member profile update request")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthUserDocsResponse {
    @Schema(description = "User ID")
    private String userId;

    @Schema(description = "User authority", example = "PET_OWNER, VET")
    private String userAuthority;

    @Schema(description = "User nickname")
    private String nickname;

    public AuthUserDocsResponse(String userId, String userAuthority, String nickname) {
        this.userId = userId;
        this.userAuthority = userAuthority;
        this.nickname = nickname;
    }

    public static AuthUserDocsResponse from(AuthUserResponse response) {
        return new AuthUserDocsResponse(response.getUserId().toString(), response.getUserAuthority(), response.getNickname());
    }
}
