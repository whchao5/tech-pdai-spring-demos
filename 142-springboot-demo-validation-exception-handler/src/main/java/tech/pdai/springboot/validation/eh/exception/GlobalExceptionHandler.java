package tech.pdai.springboot.validation.eh.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.CustomDateEditor;
//import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import tech.pdai.springboot.validation.eh.utils.response.ResponseResult;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author pdai
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @InitBinder
    public void handleInitBinder(WebDataBinder dataBinder){
        dataBinder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), false));
    }

//    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {BindException.class, ValidationException.class, MethodArgumentNotValidException.class})
    public ResponseResult<String> handleParameterVerificationException(Exception e) {
        StringBuilder exceptionStringBuilder = new StringBuilder();
        log.error("Exception: ", e);
        if (e instanceof BindException) {
            BindingResult bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
            bindingResult.getAllErrors()
                    .forEach(a -> exceptionStringBuilder.append(((FieldError) a).getField() + ": " + a.getDefaultMessage()));
        } else if (e instanceof ConstraintViolationException) {
            if (e.getMessage() != null) {
                exceptionStringBuilder.append(e.getMessage());
            }
        } else {
            exceptionStringBuilder.append("invalid parameter");
        }
        return  ResponseResult.fail(exceptionStringBuilder.toString());
    }

}
