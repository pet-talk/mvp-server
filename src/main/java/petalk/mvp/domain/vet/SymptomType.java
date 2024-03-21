package petalk.mvp.domain.vet;

public enum SymptomType {
    SKIN, DIGESTION, EYE, EAR, NOSE, MOUTH, RESPIRATION, URINARY, REPRODUCTIVE, NERVOUS, MUSCLE, BONE, BLOOD, IMMUNE, ENDOCRINE, CARDIOVASCULAR, LYMPHATIC, OTHER;

    public static SymptomType from(String value) {
        return SymptomType.valueOf(value.toUpperCase());
    }
}
