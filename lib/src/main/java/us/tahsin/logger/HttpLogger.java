package us.tahsin.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpLogger {
    public static Logger logger = LogManager.getLogger(HttpLogger.class);

    public HttpResponse<?> executeWithLog(HttpClient httpClient, HttpRequest httpRequest) throws IOException, InterruptedException {
        var httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (httpResponse.statusCode() >= 200 && httpResponse.statusCode() < 300) logger.info("Success");
        else logger.error("Error");

        return httpResponse;
    }
}
