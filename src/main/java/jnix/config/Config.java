package jnix.config;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Config {
    private Map<String, String> variables;
    private List<CServer> servers;
}
