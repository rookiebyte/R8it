package com.rit.starterboot.configuration.feign;

import com.rit.robusta.util.KeyStores;
import com.rit.robusta.util.Strings;
import com.rit.starterboot.configuration.exception.ConfigurationException;
import com.rit.starterboot.configuration.feign.properties.ConnectionType;
import com.rit.starterboot.configuration.feign.properties.HttpClientProperties;
import com.rit.starterboot.configuration.feign.properties.HttpSSLClientProperties;
import feign.Client;
import feign.Feign;
import feign.httpclient.ApacheHttpClient;
import lombok.AllArgsConstructor;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.cloud.openfeign.loadbalancer.FeignBlockingLoadBalancerClient;
import org.springframework.context.ApplicationContext;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.List;

@AllArgsConstructor
public class FeignClientFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeignClientFactory.class);

    private final ApplicationContext applicationContext;
    private final LoadBalancerClient loadBalancerClient;
    private final LoadBalancerClientFactory loadBalancerClientFactory;

    public <T> T create(Class<T> clientClass, HttpClientProperties properties) {
        var builder = new FeignClientBuilder(applicationContext).forType(clientClass, nameFor(clientClass, properties.name()))
                                                                .customize(it -> clientCustomizer(it, properties));
        if (Strings.isNotBlank(properties.url())) {
            builder.url(properties.url());
        }
        return builder.build();
    }

    private String nameFor(Class<?> clientClass, String name) {
        return Strings.isNotBlank(name) ? name : clientClass.getSimpleName();
    }

    private void clientCustomizer(Feign.Builder builder, HttpClientProperties properties) {
        builder.errorDecoder(new FeignClientErrorDecoder());
        builder.client(client(properties));
    }

    private Client client(HttpClientProperties properties) {
        var delegate = new ApacheHttpClient(buildHttpClient(properties));
        if (properties.type() == ConnectionType.LOAD_BALANCER_LABEL) {
            return new FeignBlockingLoadBalancerClient(delegate, loadBalancerClient, loadBalancerClientFactory, List.of());
        }
        return delegate;
    }

    private HttpClient buildHttpClient(HttpClientProperties properties) {
        var builder = HttpClientBuilder.create();
        if (properties.ssl() != null) {
            builder.setSSLContext(sslContext(properties.ssl()));
        }
        return builder.build();
    }

    private SSLContext sslContext(HttpSSLClientProperties ssl) {
        var keystore = loadKeystore(ssl.keyStore(), ssl.keyStorePasswordAsArray());
        var truststore = loadKeystore(ssl.trustStore(), ssl.trustStorePasswordAsArray());
        try {
            return SSLContextBuilder.create()
                                    .loadKeyMaterial(keystore, ssl.keyPasswordAsArray())
                                    .loadTrustMaterial(truststore, null).build();
        } catch (NoSuchAlgorithmException | KeyStoreException | UnrecoverableKeyException | KeyManagementException ex) {
            throw new ConfigurationException(ex);
        }
    }

    private KeyStore loadKeystore(String keyStorePath, char... password) {
        try {
            return KeyStores.readKeyStore(keyStorePath, password);
        } catch (KeyStoreException ex) {
            LOGGER.error("Unable to load keystore: {}", keyStorePath);
            throw new ConfigurationException(ex);
        }
    }
}
