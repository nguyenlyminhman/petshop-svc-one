package com.pts.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.netty.http.client.HttpClient;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Map;

public class WebClientUtils {

    private static final long DEFAULT_TIMEOUT_SECONDS = 30;
    private static final int DEFAULT_RETRY_TIMES = 2;

    private static final WebClient webClient = buildWebClient(DEFAULT_TIMEOUT_SECONDS, DEFAULT_RETRY_TIMES);

    private static WebClient buildWebClient(long timeoutSeconds, int retryTimes) {
        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(timeoutSeconds));

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .filter((request, next) -> next.exchange(request)
                        .retryWhen(Retry.fixedDelay(retryTimes, Duration.ofSeconds(1))))
                .build();
    }

    public static <T> T get(String url, Map<String, String> headers, Map<String, String> queryParams, Class<T> responseType) {
        try {
            WebClient.RequestHeadersSpec<?> request = isFullUrl(url)
                    ? webClient.get().uri(java.net.URI.create(url))
                    : webClient.get().uri(uriBuilder -> {
                uriBuilder.path(url);
                if (queryParams != null) {
                    queryParams.forEach(uriBuilder::queryParam);
                }
                return uriBuilder.build();
            });

            if (headers != null) {
                request = request.headers(h -> h.setAll(headers));
            }

            return request.retrieve()
                    .bodyToMono(responseType)
                    .block();
        } catch (WebClientResponseException e) {
            System.err.println("GET error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw e;
        }
    }

    public static <T> T post(String url, Object body, Map<String, String> headers, Map<String, String> queryParams, Class<T> responseType) {
        try {
            WebClient.RequestBodyUriSpec request = webClient.post();

            WebClient.RequestHeadersSpec<?> spec = isFullUrl(url)
                    ? request.uri(java.net.URI.create(url))
                    : request.uri(uriBuilder -> {
                uriBuilder.path(url);
                if (queryParams != null) {
                    queryParams.forEach(uriBuilder::queryParam);
                }
                return uriBuilder.build();
            });

            if (headers != null) {
                spec = ((WebClient.RequestBodySpec) spec).headers(h -> h.setAll(headers));
            }

            return ((WebClient.RequestBodySpec) spec)
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(responseType)
                    .block();
        } catch (WebClientResponseException e) {
            System.err.println("POST error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw e;
        }
    }

    private static boolean isFullUrl(String url) {
        return url.startsWith("http://") || url.startsWith("https://");
    }
}

