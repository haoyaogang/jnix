package jnix.config;

import lombok.Data;

import java.util.List;

@Data
public class CServer {
    private String name;
    private CSSL ssl;
    private List<String> listen;
    private List<CLocation> locations;
}
