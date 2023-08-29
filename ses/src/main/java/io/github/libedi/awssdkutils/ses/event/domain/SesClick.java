package io.github.libedi.awssdkutils.ses.event.domain;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * SesClick
 *
 * @author "Sangjun,Park"
 *
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class SesClick implements TimeLocalizable {

    @JsonProperty("ipAddress")
    private String recipientIpAddress;
    private String timestamp;
    private String userAgent;
    private String link;
    private Map<String, List<String>> linkTags;

}
