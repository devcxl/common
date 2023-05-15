package cn.devcxl.common.exception.handler;

import cn.devcxl.common.base.CommonResp;
import cn.devcxl.common.exception.*;
import cn.devcxl.common.exception.base.GlobalException;
import cn.devcxl.common.exception.enums.CommonErrorCode;
import cn.hutool.extra.servlet.ServletUtil;
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
            return CommonResp.fail(CommonErrorCode.BAD_REQUEST.setMessage(message));
        } else if (e instanceof ConstraintViolationException) {
            ConstraintViolationException ex = (ConstraintViolationException) e;
            String message = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("; "));
            return CommonResp.fail(CommonErrorCode.BAD_REQUEST.setMessage(message));
        } else if (e instanceof BindException) {
            BindException ex = (BindException) e;

            String message = getMessage(ex.getBindingResult());
            return CommonResp.fail(CommonErrorCode.BAD_REQUEST.setMessage(message));
        }

//        List<ObjectError> errors =ex.getBindingResult().getAllErrors();
//        StringBuffer errorMsg=new StringBuffer();
//        errors.stream().forEach(x -> errorMsg.append(x.getDefaultMessage()).append(";"));
//        log.error("---MethodArgumentNotValidException Handler--- ERROR: {}", errorMsg.toString());
//        result.setMsg(errorMsg.toString());
        return CommonResp.fail(CommonErrorCode.BAD_REQUEST);
    }

    /**
     * 处理参数异常
     *
     * @param e
     * @return 错误信息
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public CommonResp<String> handlerClientException(BadRequestException e) {
        log.error("参数异常", e);
        return CommonResp.fail(e.getErrorCode());
    }

    /**
     * 处理访问权限异常
     *
     * @param e
     * @return 错误信息
     */
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public CommonResp<String> handlerClientException(ForbiddenException e) {
        log.error("访问权限异常", e);
        return CommonResp.fail(e.getErrorCode());
    }


    /**
     * 处理未找到异常
     *
     * @param e
     * @return 错误信息
     */
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public CommonResp<String> handlerClientException(NotFoundException e) {
        log.error("未找到异常", e);
        return CommonResp.fail(e.getErrorCode());
    }

    /**
     * 处理未认证异常
     *
     * @param e
     * @return 错误信息
     */
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public CommonResp<String> handlerClientException(UnauthorizedException e, HttpServletRequest request) {
        String uri = request.getRequestURI();
        String ip = ServletUtil.getClientIP(request);
        log.error("{}访问：{}未认证", ip, uri, e);
        return CommonResp.fail(e.getErrorCode());
    }

    /**
     * 访问次数过多异常
     *
     * @param e
     * @return 错误信息
     */
    @ResponseStatus(code = HttpStatus.TOO_MANY_REQUESTS)
    @ExceptionHandler(TooManyRequestsException.class)
    public CommonResp<String> handlerClientException(TooManyRequestsException e) {
        log.error("访问次数过多异常", e);
        return CommonResp.fail(e.getErrorCode());
    }

    /**
     * 业务异常
     *
     * @param e
     * @return
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ExceptionHandler(BusinessException.class)
    public CommonResp<String> handler(GlobalException e) {
        log.error("业务异常", e);
        return CommonResp.fail(e.getErrorCode());
    }

}
