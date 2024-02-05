package petalk.mvp.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;


@RestControllerAdvice(annotations = RestController.class)
public class ExControllerAdvice {

  private final Logger logger = LoggerFactory.getLogger(ExControllerAdvice.class);


  @ExceptionHandler
  public ResponseEntity<ApiResponse<ParameterValids>> exHandler(ValidationErrorException e) {

    ParameterValids valids = ParameterValids.from(e.getErrors());
    ApiResponse<ParameterValids> response = ApiResponse.validationError(valids);

    logger.error(e.getMessage(), e);

    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler
  public ResponseEntity<ApiResponse<String>> exHandler(RuntimeException e) {
    ApiResponse<String> response = ApiResponse.fail(e.getMessage());

    logger.error(e.getMessage(), e);

    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ApiResponse<String>> handle404(NoHandlerFoundException exception) {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler
  public ResponseEntity<ApiResponse<String>> exHandler(Exception e) {
    ApiResponse<String> response = ApiResponse.fail(e.getMessage());

    logger.error(e.getMessage(), e);

    return ResponseEntity.internalServerError().body(response);
  }

}
