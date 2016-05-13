package jnix.config;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CLocation {
    private String url;
    private List<String> method;
    private Map<String, String> headers;

    private List<CResponse> responses;
}
