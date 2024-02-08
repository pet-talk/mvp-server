package petalk.mvp.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import petalk.mvp.core.errors.ParameterValids;
import petalk.mvp.core.errors.ValidationErrorException;


@RestControllerAdvice(annotations = RestController.class)
public class ExControllerAdvice {

  private final Logger logger = LoggerFactory.getLogger(ExControllerAdvice.class);


  @ExceptionHandler
  public ResponseEntity<ApiResult<ParameterValids>> exHandler(ValidationErrorException e) {

    ParameterValids valids = ParameterValids.from(e.getErrors());
    ApiResult<ParameterValids> response = ApiResult.validationError(valids);

    logger.error(e.getMessage(), e);

    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler
  public ResponseEntity<ApiResult<String>> exHandler(RuntimeException e) {
    ApiResult<String> response = ApiResult.fail(e.getMessage());

    logger.error(e.getMessage(), e);

    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ApiResult<String>> handle404(NoHandlerFoundException exception) {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler
  public ResponseEntity<ApiResult<String>> exHandler(Exception e) {
    ApiResult<String> response = ApiResult.fail(e.getMessage());

    logger.error(e.getMessage(), e);

    return ResponseEntity.internalServerError().body(response);
  }

}
