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
 * SesMail
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
public class SesMail implements TimeLocalizable {

    private String          timestamp;
    private String          messageId;
    @JsonProperty("source")
    private String          sourceAddress;
    private String          sourceArn;
    private String          sourceIp;
    private String          sendingAccountId;
    private String          callerIdentity;
    private List<String>    destination;
    private Boolean         headersTruncated;
    private List<SesHeader> headers;
    private CommonHeaders   commonHeaders;
    private Map<String, List<String>> tages;

}
