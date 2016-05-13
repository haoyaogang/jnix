package jnix.server.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import jnix.server.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Created by plaguemorin on 13/05/16.
 */
public abstract class HandlerLocation implements HttpHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(HandlerLocation.class);
    private final Pattern url;

    public HandlerLocation(final Pattern url) {
        this.url = url;
    }

    @Override
    public final void handle(final HttpExchange httpExchange) throws IOException {
        final HttpRequest ctx = new HttpRequest(httpExchange);

        handle(ctx);
    }

    protected abstract void handle(final HttpRequest httpContext) throws IOException;

    public boolean matches(final HttpExchange httpExchange) {
        boolean uriMatches = this.url.matcher(httpExchange.getRequestURI().getPath()).matches();

        return uriMatches;
    }
}
