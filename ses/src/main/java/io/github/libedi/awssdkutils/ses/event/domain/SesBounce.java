package io.github.libedi.awssdkutils.ses.event.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.libedi.awssdkutils.ses.event.type.BounceSubType;
import io.github.libedi.awssdkutils.ses.event.type.BounceType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * SesBounce
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
public class SesBounce implements TimeLocalizable {

    private BounceType      bounceType;
    private BounceSubType   bounceSubType;
    private List<Recipient> bouncedRecipients;
    private String          timestamp;
    private String          feedbackId;
    private String          remoteMtaIp;
    @JsonProperty("reportingMTA")
    private String          reportingMta;

}
