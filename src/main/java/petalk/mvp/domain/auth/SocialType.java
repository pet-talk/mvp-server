package petalk.mvp.domain.auth;

public enum SocialType {
    NAVER, KAKAO, GOOGLE;

    public static SocialType from(String socialType) {
        String value = socialType.toUpperCase();

        if (value.equals("NAVER")) {
            return NAVER;
        }

        if (value.equals("KAKAO")) {
            return KAKAO;
        }

        if (value.equals("GOOGLE")) {
            return GOOGLE;
        }

        throw new IllegalArgumentException("잘못된 소셜 타입입니다.");
    }
}
