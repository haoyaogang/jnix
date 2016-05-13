package jnix;

import jnix.config.CLocation;
import jnix.config.CResponse;
import jnix.config.CServer;
import jnix.config.Config;
import jnix.server.Server;
import jnix.server.handler.HandlerLocation;
import jnix.server.handler.HandlerLocationFile;
import jnix.server.handler.HandlerLocationProxy;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static Map<String, String> globalVariables;

    public static void main(String[] args) throws IOException {
        LOGGER.info("Starting...");

        // Parse command line options
        OptionSet options = parseOptions(args);

        // Parse our config
        Config config = parseConfig(options);
        if (config != null) {
            globalVariables = config.getVariables();

            for (final CServer cServer : config.getServers()) {
                final Server server = createServerFromConfig(cServer);

                server.start();
            }

        }
    }

    private String resolveVariables(final String name) {
        if (globalVariables.containsKey(name)) {
            return globalVariables.get(name);
        }

        return String.format("${%s}", name);
    }

    private static Server createServerFromConfig(final CServer cServer) throws IOException {
        LOGGER.info("Configuring {}", cServer.getName());
        final Server server = new Server();

        for (final String listen : cServer.getListen()) {
            server.addListen(createAddressFromConfig(listen));
        }

        for (final CLocation cLocation : cServer.getLocations()) {
            server.addAllLocation(createHandlerFromConfig(cLocation));
        }

        return server;
    }

    private static InetSocketAddress createAddressFromConfig(final String addr) {
        if (addr.contains(":")) {
            return new InetSocketAddress(
                    addr.substring(0, addr.indexOf(':')),
                    Integer.parseInt(addr.substring(addr.indexOf(':') + 1))
            );
        } else {
            return new InetSocketAddress(Integer.parseInt(addr));
        }
    }

    private static List<HandlerLocation> createHandlerFromConfig(final CLocation cLocation) {
        Pattern patternUrl = Pattern.compile(cLocation.getUrl());
        List<HandlerLocation> locations = new ArrayList<>(cLocation.getResponses().size());

        for (final CResponse cResponse : cLocation.getResponses()) {
            if (cResponse.getProxy() != null) {
                locations.add(new HandlerLocationProxy(patternUrl, cResponse.getProxy()));
            }
            if (cResponse.getFile() != null && !cResponse.getFile().isEmpty()) {
                locations.add(new HandlerLocationFile(patternUrl, cResponse.getFile()));
            }
        }

        return locations;
    }

    private static Config parseConfig(final OptionSet options) throws FileNotFoundException {
        Yaml yaml = new Yaml();
        String configFile = options.has("c") ? options.valueOf("c").toString() : "config.yml";
        LOGGER.info("Using config file: {}", configFile);
        return yaml.loadAs(new FileReader(new File(configFile)), Config.class);
    }

    private static OptionSet parseOptions(String... args) {
        OptionParser parser = new OptionParser();
        // -c <config.yml>
        parser.accepts("c").withRequiredArg();

        return parser.parse(args);
    }
}
