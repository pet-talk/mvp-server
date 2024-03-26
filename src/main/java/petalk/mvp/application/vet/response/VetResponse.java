package petalk.mvp.application.vet.response;

import java.util.Collections;
import java.util.List;


/**
 * 의사의 정보를 나타내는 클래스입니다. <br>
 * 의사의 정보는 의사의 이름, 소개, 이미지를 의미합니다. <br>
 * 의사의 리뷰는 평균 평점, 리뷰 개수를 의미합니다. <br>
 * 의사의 전문분야는 증상, 동물을 의미합니다. <br>
 */
public class VetResponse {

    // 의사의 아이디
    private final Long vetId;

    // 의사의 정보
    private final VetInfo info;

    // 진료비
    private final float consultationPrice;

    // 최근 응답시간
    private final int recentResponseTime;

    // 리뷰
    private final VetReview reviews;

    // 전문분야
    private final VetExpertise expertises;

    public VetResponse(Long vetId, VetInfo info, float consultationPrice, int recentResponseTime, VetReview reviews, VetExpertise expertises) {
        this.vetId = vetId;
        this.info = info;
        this.consultationPrice = consultationPrice;
        this.recentResponseTime = recentResponseTime;
        this.reviews = reviews;
        this.expertises = expertises;
    }

    public Long getVetId() {
        return vetId;
    }

    public VetInfo getInfo() {
        return info;
    }

    public float getConsultationPrice() {
        return consultationPrice;
    }

    public int getRecentResponseTime() {
        return recentResponseTime;
    }

    public VetReview getReviews() {
        return reviews;
    }

    public VetExpertise getExpertises() {
        return expertises;
    }

    /**
     * 의사의 전문 분야를 나타내는 클래스입니다. <br>
     */
    public static class VetExpertise {
        // 증상
        private final List<String> symptoms;
        // 동물
        private final List<String> animals;

        public VetExpertise(List<String> symptoms, List<String> animals) {
            this.symptoms = Collections.unmodifiableList(symptoms);
            this.animals = Collections.unmodifiableList(animals);
        }

        public List<String> getSymptoms() {
            return symptoms;
        }

        public List<String> getAnimals() {
            return animals;
        }
    }

    /**
     * 의사의 리뷰 정보를 나타내는 클래스입니다. <br>
     */
    public static class VetReview {
        // 평균 평점
        private final double averageRating;
        // 리뷰 개수
        private final int reviewCount;

        public VetReview(double averageRating, int reviewCount) {
            this.averageRating = averageRating;
            this.reviewCount = reviewCount;
        }

        public double getAverageRating() {
            return averageRating;
        }

        public int getReviewCount() {
            return reviewCount;
        }
    }

    /**
     * 의사의 정보를 나타내는 클래스입니다. <br>
     */
    public static class VetInfo {
        private final String vetName;
        private final String introduction;
        private final String vetImage;

        public VetInfo(String vetName, String introduction, String vetImage) {
            this.vetName = vetName;
            this.introduction = introduction;
            this.vetImage = vetImage;
        }

        public String getVetName() {
            return vetName;
        }

        public String getIntroduction() {
            return introduction;
        }

        public String getVetImage() {
            return vetImage;
        }
    }

}
