package consts;

import java.time.Duration;

public class Header {
    public static final String deviceId = "device-id";
    public static final String imDeviceId = "im-device-id";
    public static final String deviceType = "device-type";
    public static final String osVer = "os-ver";
    public static final String appVer = "app-ver";
    public static final String lang = "lang";
    public static final String ts = "ts";
    public static final String tsSign = "ts-sign";
    public static final String uid = "uid";
    public static final String ocode = "ocode";
    public static final String said = "said";
    public static final String phoneModel = "p-model";

    public static final String COOKIE_PATH = "/";
    public static final String HEADER_CLIENT_IP = "CLIENT-IP";

    // 15天= 15*24*60分钟 = 21600
    public static final long DEFAULT_TOKEN_EXPIRE_MINNUTE = 21600;
    public static final long DEFAULT_TOKEN_EXPIRE_MILLISECONDS = Duration.ofMinutes(DEFAULT_TOKEN_EXPIRE_MINNUTE).toMillis();
    public static final long DEFAULT_TOKEN_EXPIRE_SECONDS = DEFAULT_TOKEN_EXPIRE_MILLISECONDS / 1000;
}
