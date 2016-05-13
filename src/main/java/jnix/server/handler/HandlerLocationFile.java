package jnix.server.handler;

import jnix.server.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Created by plaguemorin on 13/05/16.
 */
public class HandlerLocationFile extends HandlerLocation {
    private static final Logger LOGGER = LoggerFactory.getLogger(HandlerLocationFile.class);
    private final String file;

    public HandlerLocationFile(final Pattern patternUrl, final String file) {
        super(patternUrl);
        this.file = file;
    }

    @Override
    public void handle(final HttpRequest httpExchange) throws IOException {
        File f = new File(httpExchange.resolve(file));
    }
}
