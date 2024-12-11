package com.example.section.elasticSearch.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;


import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.example.section.repository")
@Log4j2
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    @Value("${spring.elasticsearch.username}")
    private String username;

    @Value("${spring.elasticsearch.password}")
    private String password;

    @Value("${spring.elasticsearch.host}")
    private String host;

    @Value("${spring.elasticsearch.ssl.certificate}")
    private Resource caCertificate;

    @Override
    public ClientConfiguration clientConfiguration() {
        log.info("host : "+host);
        SSLContext sslContext = createSslContext();
        return ClientConfiguration.builder()
                .connectedTo(host) // Elasticsearch 서버 호스트 설정
                .usingSsl(sslContext) // SSL 설정
                .withBasicAuth(username, password) // 인증 정보 설정
                .build();
    }
    private SSLContext createSslContext() {
        try (InputStream caInput = caCertificate.getInputStream()) {
            log.info("Loading CA certificate from: " + caCertificate.getURI());

            // CA 인증서 로드
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate caCert = (X509Certificate) cf.generateCertificate(caInput);

            // 키스토어 생성 및 인증서 추가
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry("caCert", caCert);

            // TrustManagerFactory 설정
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keyStore);

            // SSLContext 생성
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), new java.security.SecureRandom());

            log.info("SSL Context successfully created.");
            return sslContext;

        } catch (Exception e) {
            log.error("Failed to create SSL Context: " + e.getMessage(), e);
            throw new RuntimeException("Could not create SSL context", e);
        }
    }

}