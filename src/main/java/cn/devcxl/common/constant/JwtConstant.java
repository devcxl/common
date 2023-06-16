package cn.devcxl.common.constant;

/**
 * @author devcxl
 */
public class JwtConstant {


    /**
     * jwt 签发者标识
     */
    public static final String IIS = "iss";

    /**
     * jwt 过期时间标识
     */
    public static final String EXPIRATION = "exp";

    /**
     * jwt 用户名标识
     */
    public static final String SUB = "sub";

    /**
     * 请求头token标识
     */
    public static final String TOKEN_HEADER_NAME = "Authorization";


    /**
     * 请求头token前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 默认token过期时间
     */
    public static final Integer DEFAULT_EXPIRATION = 60 * 60 * 24;

}
