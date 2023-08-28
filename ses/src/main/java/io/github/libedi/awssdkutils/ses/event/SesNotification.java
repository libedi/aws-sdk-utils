package io.github.libedi.awssdkutils.ses.event;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import software.amazon.awssdk.utils.StringUtils;

/**
 * SesNotification
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
public class SesNotification {
    
    private static final String SES_EMAIL_NOTIFICATION_SUBJECT = "Amazon SES Email Event Notification";

    @JsonProperty("Type")
    private MessageType type;

    @JsonProperty("MessageId")
    private String messageId;

    @JsonProperty("TopicArn")
    private String topicArn;

    @JsonProperty("Subject")
    private String subject;

    @JsonProperty("Message")
    private String message;

    @JsonProperty("Timestamp")
    private String timestamp;

    @JsonProperty("SignatureVersion")
    private String signatureVersion;

    @JsonProperty("Signature")
    private String signature;

    @JsonProperty("SigningCertURL")
    private String signingCertURL;

    @JsonProperty("UnsubscribeURL")
    private String unsubscribeURL;

    /**
     * SES에서 송신한 이벤트 알림인지 여부
     * 
     * @return
     */
    public boolean isSesEmailEventNotification() {
        return StringUtils.equals(subject, SES_EMAIL_NOTIFICATION_SUBJECT);
    }

}
