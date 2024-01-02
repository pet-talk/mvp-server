package petalk.mvp.core;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice(annotations = RestController.class)
public class ExControllerAdvice {


  @ExceptionHandler
  public ResponseEntity<ApiResponse<ParameterValids>> exHandler(ValidationErrorException e) {

    ParameterValids valids = ParameterValids.from(e.getErrors());
    ApiResponse<ParameterValids> response = ApiResponse.validationError(valids);

    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler
  public ResponseEntity<ApiResponse<String>> exHandler(RuntimeException e) {
    ApiResponse<String> response = ApiResponse.fail(e.getMessage());
    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler
  public ResponseEntity<ApiResponse<String>> exHandler(Exception e) {
    ApiResponse<String> response = ApiResponse.fail(e.getMessage());
    return ResponseEntity.internalServerError().body(response);
  }

}
