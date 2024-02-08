package petalk.mvp.core.errors;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParameterValids {
    private List<ParameterValid> valids;

    private ParameterValids(List<ParameterValid> valids) {
        this.valids = valids;
    }

    public static ParameterValids from(ValidationErrors errors) {
        List<ParameterValids.ParameterValid> response =
                errors.getErrors()
                        .stream()
                        .map(error -> new ParameterValids.ParameterValid(error.getField(), error.getMessage()))
                        .collect(Collectors.toList());

        return new ParameterValids(response);
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ParameterValid {
        private String field;
        private String message;

        public ParameterValid(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }
}
