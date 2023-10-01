package us.tahsin.logger;

import org.gaul.httpbin.HttpBin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpLoggerTest {
    private final String baseURL = "http://127.0.0.1";
    private final HttpBin httpBin = new HttpBin(URI.create(baseURL + ":0"));
    private final HttpLogger httpLogger = new HttpLogger();
    private final HttpClient httpClient = HttpClient.newHttpClient();

    HttpLoggerTest() throws Exception {
    }

    @BeforeEach
    void setup() throws Exception {
        httpBin.start();
    }

    @AfterEach
    void destroy() throws Exception {
        httpBin.stop();
    }

    @Test
    void shouldLogInfo() throws Exception {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        requestBuilder.uri(new URI(getURL("/")));
        requestBuilder.GET();
        HttpRequest httpRequest = requestBuilder.build();

        var response = httpLogger.executeWithLog(httpClient, httpRequest);
        assertEquals(200, response.statusCode());
    }

    @Test
    void shouldLogError() throws Exception {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        requestBuilder.uri(new URI(getURL("/status/400")));
        requestBuilder.GET();
        HttpRequest httpRequest = requestBuilder.build();

        var response = httpLogger.executeWithLog(httpClient, httpRequest);
        assertEquals(400, response.statusCode());
    }

    private String getURL(String suffix) {
        return baseURL + ":" + httpBin.getPort() + suffix;
    }
}
