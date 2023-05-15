package cn.devcxl.common.exception;

import cn.devcxl.common.exception.base.GlobalException;
import cn.devcxl.common.exception.interfaces.ErrorCode;

/**
 * 资源未找到
 *
 * @author devcxl
 */
public class NotFoundException extends GlobalException {
    public NotFoundException(ErrorCode<?> errorCode) {
        super(errorCode);
    }
}
