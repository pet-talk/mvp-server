package petalk.mvp.presentation.support;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import petalk.mvp.core.errors.ParameterValids;
import petalk.mvp.core.errors.ValidationErrorException;


@RestControllerAdvice(annotations = RestController.class)
@Slf4j
public class ExControllerAdvice {

  @ExceptionHandler
  public ResponseEntity<ApiResult<ParameterValids>> exHandler(ValidationErrorException e, HttpSession session) {

    ParameterValids valids = ParameterValids.from(e.getErrors());
    ApiResult<ParameterValids> response = ApiResult.validationError(valids);

    MDC.put("valids error", valids.toString());
    MDC.put("session id", session.getId());
    log.warn("validation error");
    MDC.clear();

    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler
  public ResponseEntity<ApiResult<String>> exHandler(RuntimeException e, HttpSession session) {
    ApiResult<String> response = ApiResult.fail(e.getMessage());
    MDC.put("session id", session.getId());
    log.error(e.getMessage(), e);
    MDC.clear();

    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ApiResult<String>> handle404(NoHandlerFoundException exception) {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler
  public ResponseEntity<ApiResult<String>> exHandler(Exception e, HttpSession session) {
    ApiResult<String> response = ApiResult.fail(e.getMessage());
    MDC.put("session id", session.getId());
    log.error(e.getMessage(), e);
    MDC.clear();

    return ResponseEntity.internalServerError().body(response);
  }

}
