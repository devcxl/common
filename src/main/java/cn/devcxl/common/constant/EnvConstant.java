package cn.devcxl.common.constant;

/**
 * @author devcxl
 */
public class EnvConstant {
    public static final String DEV = "dev";
    public static final String TEST = "test";
    public static final String PROD = "prod";

    public static final String ENV_VALUE = "${spring.profiles.active}";
}
