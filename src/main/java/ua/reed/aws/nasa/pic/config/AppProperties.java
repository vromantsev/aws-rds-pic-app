package ua.reed.aws.nasa.pic.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AppProperties {

    @Value("${app.api.external.url}")
    private String url;

    @Value("${app.api.external.api-key}")
    private String apiKey;

    @Value("${app.api.headers.sol}")
    private String solHeader;

    @Value("${app.api.headers.camera}")
    private String cameraHeader;

    @Value("${app.api.headers.api-key}")
    private String apiKeyHeader;
}
