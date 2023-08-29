package io.github.libedi.awssdkutils.ses.event.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.libedi.awssdkutils.ses.event.type.DelayType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * SesDeliveryDelay
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
public class SesDeliveryDelay implements TimeLocalizable {

    private DelayType       delayType;
    private List<Recipient> delayedRecipients;
    private String          expirationTime;
    @JsonProperty("reportingMTA")
    private String          reportingMta;
    private String          timestamp;

}
