package jnix.server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;
import jnix.Main;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequest extends HttpExchange {
    private final static Pattern PATTERN_VAR = Pattern.compile("\\$\\{([A-Za-z\\.]+)\\}");
    private final Map<String, String> vars = new HashMap<>();
    private final HttpExchange httpExchange;

    public HttpRequest(final HttpExchange httpExchange) {
        this.httpExchange = httpExchange;

        vars.putAll(Main.globalVariables);
        vars.put("uri", this.httpExchange.getRequestURI().getPath());
    }


    public String resolve(final String string) {
        Matcher m = PATTERN_VAR.matcher(string);
        if (!m.matches()) {
            return string;
        }

        StringBuffer stringBuffer = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(stringBuffer, "test");
        }

        return stringBuffer.toString();
    }

    @Override
    public Headers getRequestHeaders() {
        return httpExchange.getRequestHeaders();
    }

    @Override
    public Headers getResponseHeaders() {
        return httpExchange.getResponseHeaders();
    }

    @Override
    public URI getRequestURI() {
        return httpExchange.getRequestURI();
    }

    @Override
    public String getRequestMethod() {
        return httpExchange.getRequestMethod();
    }

    @Override
    public HttpContext getHttpContext() {
        return httpExchange.getHttpContext();
    }

    @Override
    public void close() {
        httpExchange.close();
    }

    @Override
    public InputStream getRequestBody() {
        return httpExchange.getRequestBody();
    }

    @Override
    public OutputStream getResponseBody() {
        return httpExchange.getResponseBody();
    }

    @Override
    public void sendResponseHeaders(final int i, final long l) throws IOException {
        httpExchange.sendResponseHeaders(i, l);
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return httpExchange.getRemoteAddress();
    }

    @Override
    public int getResponseCode() {
        return httpExchange.getResponseCode();
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return httpExchange.getLocalAddress();
    }

    @Override
    public String getProtocol() {
        return httpExchange.getProtocol();
    }

    @Override
    public Object getAttribute(final String s) {
        return httpExchange.getAttribute(s);
    }

    @Override
    public void setAttribute(final String s, final Object o) {
        httpExchange.setAttribute(s, o);
    }

    @Override
    public void setStreams(final InputStream inputStream, final OutputStream outputStream) {
        httpExchange.setStreams(inputStream, outputStream);
    }

    @Override
    public HttpPrincipal getPrincipal() {
        return httpExchange.getPrincipal();
    }
}
