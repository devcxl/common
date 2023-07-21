package cn.devcxl.common.exception.handler;

import cn.devcxl.common.base.CommonResp;
import cn.devcxl.common.exception.*;
import cn.devcxl.common.exception.enums.CommonErrorCode;
import cn.devcxl.common.exception.interfaces.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.stream.Collectors;

/**
 * @author devcxl
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static String getMessage(BindingResult ex) {
        return ex.getFieldErrors().stream().map(fieldError -> {
            if (fieldError != null) {
                // 返回消息组装  格式  属性名 + 注解消息
                return fieldError.getField() + " " + fieldError.getDefaultMessage();
            } else {
                return null;
            }
        }).collect(Collectors.joining("; "));
    }

    /**
     * 处理spring参数校验validation异常
     *
     * @param e
     * @return 错误信息
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {BindException.class, ValidationException.class, MethodArgumentNotValidException.class})
    public CommonResp<String> handleValidatedException(Exception e) {

        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            String message = getMessage(ex.getBindingResult());
            return CommonResp.fail(CommonErrorCode.BAD_REQUEST, message);
        } else if (e instanceof ConstraintViolationException) {
            ConstraintViolationException ex = (ConstraintViolationException) e;
            String message = ex.getConstraintViolations().stream()
                    .map( cv -> cv == null ? "null" : cv.getPropertyPath() + " " + cv.getMessage() )
                    .collect( Collectors.joining( ", " ) );
            return CommonResp.fail(CommonErrorCode.BAD_REQUEST, message);
        } else if (e instanceof BindException) {
            BindException ex = (BindException) e;
            String message = getMessage(ex.getBindingResult());
            return CommonResp.fail(CommonErrorCode.BAD_REQUEST, message);
        }

//        List<ObjectError> errors =ex.getBindingResult().getAllErrors();
//        StringBuffer errorMsg=new StringBuffer();
//        errors.stream().forEach(x -> errorMsg.append(x.getDefaultMessage()).append(";"));
//        log.error("---MethodArgumentNotValidException Handler--- ERROR: {}", errorMsg.toString());
//        result.setMsg(errorMsg.toString());
        return CommonResp.fail(CommonErrorCode.BAD_REQUEST);
    }

    /**
     * API异常处理
     *
     * @param e
     * @return
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ExceptionHandler(value = CommonException.class)
    public CommonResp<ErrorCode<?>> handle(CommonException e, HttpServletRequest request) {
        ErrorCode<?> errorCode = e.getErrorCode();
        log.error("请求异常[{}],code:{},message:{}", request.getRequestURI(), errorCode.getCode(), errorCode.getMessage());
        return CommonResp.fail(e.getErrorCode());
    }

}
