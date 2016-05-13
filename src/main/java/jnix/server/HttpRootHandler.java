package jnix.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import jnix.server.handler.HandlerLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by plaguemorin on 13/05/16.
 */
public class HttpRootHandler implements HttpHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRootHandler.class);

    private List<HandlerLocation> locationList = new ArrayList<>();

    @Override
    public void handle(final HttpExchange httpExchange) throws IOException {
        for (final HandlerLocation handlerLocation : locationList) {
            if (handlerLocation.matches(httpExchange)) {
                handlerLocation.handle(httpExchange);
            }
        }
    }

    public void addLocation(final HandlerLocation handlerLocation) {
        this.locationList.add(handlerLocation);
    }

    public void addAllLocation(final List<HandlerLocation> handlerLocations) {
        this.locationList.addAll(handlerLocations);
    }
}
