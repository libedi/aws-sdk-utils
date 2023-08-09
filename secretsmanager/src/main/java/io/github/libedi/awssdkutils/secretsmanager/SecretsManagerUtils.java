package io.github.libedi.awssdkutils.secretsmanager;

import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;

/**
 * SecretsManagerUtils
 * 
 * @author "Sangjun,Park"
 *
 */
public class SecretsManagerUtils {

    private final SecretsManagerClient client;
    private final ObjectMapper objectMapper;

    /**
     * SecretsManagerUtils 생성자 - 기본 리전: ap-northeast-2
     * 
     * @param objectMapper
     */
    public SecretsManagerUtils(final ObjectMapper objectMapper) {
        this(Region.AP_NORTHEAST_2, objectMapper);
    }

    /**
     * SecretsManagerUtils 생성자
     * 
     * @param region
     * @param objectMapper
     */
    public SecretsManagerUtils(final String region, final ObjectMapper objectMapper) {
        this(Region.of(region), objectMapper);
    }

    /**
     * SecretsManagerUtils 생성자
     * 
     * @param region       - null이면, 기본 리전: aws-global
     * @param objectMapper
     */
    public SecretsManagerUtils(final Region region, final ObjectMapper objectMapper) {
        client = SecretsManagerClient.builder()
                .region(region != null ? region : Region.AWS_GLOBAL)
                .build();
        this.objectMapper = Objects.requireNonNull(objectMapper, () -> "ObjectMapper must not be null.");
    }

    /**
     * Secret 정보 조회
     * 
     * @param secretName 보안 암호 이름
     * @return
     */
    public String getValue(final String secretName) {
        if (secretName == null) {
            throw new IllegalArgumentException("Secret Name must not be null.");
        }
        return client.getSecretValue(GetSecretValueRequest.builder()
                .secretId(secretName)
                .build())
                .secretString();
    }

    /**
     * Secret 정보 조회
     * 
     * @param <T>
     * @param secretName 보안 암호 이름
     * @param returnType 반환 타입
     * @return
     */
    public <T> T getValue(final String secretName, final Class<T> returnType) {
        try {
            return objectMapper.readValue(getValue(secretName), returnType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
