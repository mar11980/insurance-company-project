package vaudoise.insurance.controlleradvice;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.validation.ValidationAdviceTrait;
import vaudoise.insurance.exception.ClientNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler implements ProblemHandling, ValidationAdviceTrait {

    @org.springframework.web.bind.annotation.ExceptionHandler(ClientNotFoundException.class)
    public Problem handleClientNotFound(ClientNotFoundException ex) {
        return Problem.builder()
                .withTitle("Client not found")
                .withStatus(Status.NOT_FOUND)
                .withDetail(ex.getMessage())
                .build();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
    public Problem handleIllegalArgument(IllegalArgumentException ex) {
        return Problem.builder()
                .withTitle("Bad request")
                .withStatus(Status.BAD_REQUEST)
                .withDetail(ex.getMessage())
                .build();
    }

}
