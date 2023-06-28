package cn.devcxl.common.exception.controller;

import cn.devcxl.common.base.CommonResp;
import cn.devcxl.common.exception.enums.CommonErrorCode;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配置默认error页面Rest接口方式返回
 *
 * @author devcxl
 */
@RestController
@ConditionalOnProperty(value = "server.error.whitelabel.enabled", havingValue = "true")
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    @GetMapping(PATH)
    public CommonResp<String> error() {
        return CommonResp.fail(CommonErrorCode.NOT_FOUND);
    }

}