package com.server.trading.auto.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class EnvUtil {
    @Value("upbit.open.api.access.key")
    public static String accessKey;
    @Value("upbit.open.api.secret.key")
    public static String secretKey;
    @Value("upbit.open.api.server.key")
    public static String serverUrl;
}
