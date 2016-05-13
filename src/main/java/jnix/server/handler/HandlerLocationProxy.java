package jnix.server.handler;

import jnix.server.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Created by plaguemorin on 13/05/16.
 */
public class HandlerLocationProxy extends HandlerLocation {
    private static final Logger LOGGER = LoggerFactory.getLogger(HandlerLocationProxy.class);
    private final String proxyTo;

    public HandlerLocationProxy(final Pattern patternUrl, final String proxy) {
        super(patternUrl);
        this.proxyTo = proxy;
    }

    @Override
    public void handle(final HttpRequest httpContext) throws IOException {

    }
}
