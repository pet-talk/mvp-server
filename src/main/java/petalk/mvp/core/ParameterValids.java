package petalk.mvp.core;

import java.util.List;
import java.util.stream.Collectors;

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

    public static class ParameterValid {
        private String field;
        private String message;

        public ParameterValid(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public ParameterValid() {
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }
    }
}
