package ua.reed.aws.nasa.pic.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.rds.RdsClient;
import software.amazon.awssdk.services.rds.RdsUtilities;
import software.amazon.awssdk.services.rds.model.GenerateAuthenticationTokenRequest;

import java.net.URI;

@RequiredArgsConstructor
public class RdsIamDataSource extends HikariDataSource {

    private final RdsClient rdsClient;

    @Override
    public String getPassword() {
        RdsUtilities utilities = rdsClient.utilities();
        URI jdbcUri = parseJdbcUrl(getJdbcUrl());
        GenerateAuthenticationTokenRequest request = GenerateAuthenticationTokenRequest.builder()
                .username(getUsername())
                .hostname(jdbcUri.getHost())
                .port(jdbcUri.getPort())
                .build();
        return utilities.generateAuthenticationToken(request);
    }

    private URI parseJdbcUrl(final String jdbcUrl) {
        return URI.create(jdbcUrl.substring(5));
    }
}
