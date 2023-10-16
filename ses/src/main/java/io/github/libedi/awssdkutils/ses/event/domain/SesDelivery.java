package io.github.libedi.awssdkutils.ses.event.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * SesDelivery
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
public class SesDelivery implements TimeLocalizable {

    private String       timestamp;
    private long         processingTimeMillis;
    private List<String> recipients;
    private String       smtpResponse;
    @JsonProperty("reportingMTA")
    private String       reportingMta;
    private String       remoteMtaIp;

}
