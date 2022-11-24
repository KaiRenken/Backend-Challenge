package de.neusta.backendchallenge.service.Exception;

import de.neusta.backendchallenge.controller.ErrorDto;

public class PersonAlreadyExistsException extends RuntimeException {

    private final ErrorDto errorDto;

    public PersonAlreadyExistsException(ErrorDto errorDto) {
        super();
        this.errorDto = errorDto;
    }

    public ErrorDto getErrorDto() {
        return errorDto;
    }
}
