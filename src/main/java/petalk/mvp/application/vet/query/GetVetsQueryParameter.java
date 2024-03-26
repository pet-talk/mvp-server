package petalk.mvp.application.vet.query;

import petalk.mvp.core.pagination.Sort;
import petalk.mvp.domain.vet.AnimalType;
import petalk.mvp.domain.vet.SymptomType;

import java.util.Optional;

/**
 * GetVetsQueryParameter는 수의사 목록 조회 쿼리 파라미터입니다.
 */
public class GetVetsQueryParameter {

    private final int size;
    private String cursor;
    private final Sort sort;
    private SymptomType symptomType;
    private AnimalType animalType;
    private String name;

    public GetVetsQueryParameter(int size, String cursor, Sort sort, SymptomType symptomType, AnimalType animalType, String name) {
        this.size = size;
        this.cursor = cursor;
        this.sort = sort;
        this.symptomType = symptomType;
        this.animalType = animalType;
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public Optional<String> getCursor() {
        return Optional.ofNullable(cursor);
    }

    public Sort getSort() {
        return sort;
    }

    public Optional<SymptomType> getSymptomType() {
        return Optional.ofNullable(symptomType);
    }

    public Optional<AnimalType> getAnimalType() {
        return Optional.ofNullable(animalType);
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }
}
