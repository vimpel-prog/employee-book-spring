package com.skypro.employee.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid Data entered")
public class InvalidEmployeeArgumentException extends RuntimeException{
}
