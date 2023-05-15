
package cn.devcxl.common.exception;

import cn.devcxl.common.exception.base.GlobalException;
import cn.devcxl.common.exception.interfaces.ErrorCode;

/**
 * 业务错误
 *
 * @author devcxl
 */
public class BusinessException extends GlobalException {

    public BusinessException(ErrorCode<?> errorCode) {
        super(errorCode);
    }

}
