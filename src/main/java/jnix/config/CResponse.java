package jnix.config;

import lombok.Data;

import java.util.Map;

@Data
public class CResponse {
    private String proxy;
    private Map<String, String> proxyHeaders;

    private String file;
}
