package petalk.mvp.domain.auth.command;

public enum SocialType {
    NAVER, KAKAO, GOOGLE;

    public static SocialType from(String socialType) {
        if (socialType == null) {
            throw new IllegalArgumentException("소셜 타입이 null입니다.");
        }

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
