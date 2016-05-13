package jnix.server;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import jnix.server.handler.HandlerLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {
    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    private final Map<HttpServer, HttpContext> httpServers = new HashMap<>();
    private final HttpRootHandler handler;

    public Server() throws IOException {
        handler = new HttpRootHandler();
    }

    public void addLocation(final HandlerLocation hl) {
        handler.addLocation(hl);
    }

    public void start() {
        for (final Map.Entry<HttpServer, HttpContext> entry : httpServers.entrySet()) {
            entry.getKey().start();
        }
    }

    public void stop() {
        for (final Map.Entry<HttpServer, HttpContext> entry : httpServers.entrySet()) {
            entry.getKey().stop(1000);
        }
    }

    public void addListen(final InetSocketAddress bind) throws IOException {
        final HttpServer httpServer = HttpServer.create(bind, 0);
        httpServer.setExecutor(null);

        httpServers.put(httpServer, httpServer.createContext("/", handler));
    }

    public void addAllLocation(final List<HandlerLocation> handlerLocations) {
        handler.addAllLocation(handlerLocations);
    }
}
