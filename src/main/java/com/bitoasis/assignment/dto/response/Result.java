package com.bitoasis.assignment.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Result<T> {
    public enum Status {
        FAIL, // The user did something wrong - usually to handle validations
        ERROR, // Something internal went wrong - usually to handle exceptions
        SUCCESS // Something went right :)
    }

    //private LinkedHashMap<String, Object> failures = new LinkedHashMap<>();

    private Status status;
    private String message;
    private T data;
    private List<Failure> failures;
    
    private Integer error;
    @JsonProperty("error_message")
    private String errorMessage;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private Object errorDetail;



    private Optional<Failure> createFailureFromReducedStream(Optional<Failure> optionalMapEntry) {
        if (optionalMapEntry.isPresent()) {
            var kvPair = optionalMapEntry.get();
            return Optional.of(kvPair);
        }
        return Optional.empty();
    }

    
    @JsonIgnore
    public Optional<Failure> getFirstFailure() {
        var result = failures
                .stream()
                .findFirst();
        return createFailureFromReducedStream(result);
    }

    @JsonIgnore
    public String getFirstFailureMessage() {
        if (getFirstFailure().isPresent()) {
            return getFirstFailure().get().message.toString();
        }
        return "";
    }

    @JsonIgnore
    public Optional<Failure> getLastFailure() {
        var result = failures
                .stream()
                .reduce((first, second) -> second);
        return createFailureFromReducedStream(result);
    }

    @JsonIgnore
    public void addFailure(String field, String message) {
        status = Status.FAIL;
        failures.add(Failure.builder().field(field).message( message).build());
    }

    public boolean isSuccessful() {
        return status == Status.SUCCESS;
    }

    public static <T> Result<T> success(T data) {
        return Result.<T>builder()
                .status(Status.SUCCESS)
                .data(data)
                .build();
    }

    public static <T> Result<T> success(String message) {
        return Result.<T>builder()
                .status(Status.SUCCESS)
                .message(message)
                .build();
    }

    public static <T> Result<T> fail(List<Failure> failures) {
        return Result.<T>builder()
                .failures(failures)
                .status(Status.FAIL).build();
    }

    
    public static <T> Result<T> fail(String message) {
        return Result.<T>builder()
                .message(message)
                .status(Status.FAIL).build();
    }

	
    public static <T> Result<T> fail(Result<?> result) {
        return Result.<T>builder()
                .message(result.getMessage())
                .failures(result.getFailures())
                .status(Status.FAIL).build();
    }

    public static <T> Result<T> error(String message) {
        return Result.<T>builder()
                .message(message)
                .status(Status.ERROR)
                .build();
    }

    public static <T> Result<T> error(String message, HttpStatus code) {
        return Result.<T>builder()
                .error(code.value())
                .errorMessage(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
    
    public static <T> Result<T> error(String errorMessage, List<Failure> failures, HttpStatus code) {
        return Result.<T>builder()
                .failures(failures)
                .errorMessage(errorMessage)
                .error(code.value())
                .timestamp(LocalDateTime.now())
                .build();
    }

}
