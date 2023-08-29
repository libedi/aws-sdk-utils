package io.github.libedi.awssdkutils.ses.event.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.libedi.awssdkutils.ses.event.type.BounceSubType;
import io.github.libedi.awssdkutils.ses.event.type.NotificationType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * SesMessage
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
public class SesMessage {

    @JsonProperty("eventType")
    private NotificationType    notificationType;
    private SesMail             mail;
    private SesBounce           bounce;
    private SesComplaint        complaint;
    private SesDelivery         delivery;
    private SesReject           reject;
    private SesOpen             open;
    private SesClick            click;
    private SesRenderingFailure failure;
    private SesDeliveryDelay    deliveryDelay;
    private SesSubscription     subscription;

    /**
     * SES 발송시 할당했던 메시지ID
     * 
     * @return
     */
    public String getMessageId() {
        return mail.getMessageId();
    }

    /**
     * 이벤트 발생 시간
     * 
     * @return
     */
    public LocalDateTime getTimestamp() {
        switch (notificationType) {
        case BOUNCE:
            return bounce.getLocalTimestamp();
        case CLICK:
            return click.getLocalTimestamp();
        case COMPLAINT:
            return complaint.getLocalTimestamp();
        case DELIVERY:
            return delivery.getLocalTimestamp();
        case DELIVERY_DELAY:
            return deliveryDelay.getLocalTimestamp();
        case OPEN:
            return open.getLocalTimestamp();
        case REJECT:
            return LocalDateTime.now();
        case RENDERING_FAILURE:
            return LocalDateTime.now();
        case SEND:
            return mail.getLocalTimestamp();
        case SUBSCRIPTION:
            return subscription.getLocalTimestamp();
        default:
            return null;
        }
    }

    /**
     * 이벤트 응답 메시지
     * 
     * @return
     */
    public String getResponseMessage() {
        switch (notificationType) {
        case BOUNCE:
            return getMessageByRecipient(bounce.getBouncedRecipients());
        case CLICK:
            return click.getLink();
        case COMPLAINT:
            return getMessageByRecipient(complaint.getComplainedRecipients());
        case DELIVERY:
            return delivery.getSmtpResponse();
        case DELIVERY_DELAY:
            return getMessageByRecipient(deliveryDelay.getDelayedRecipients());
        case OPEN:
            return open.getRecipientIpAddress();
        case REJECT:
            return reject.getReason();
        case RENDERING_FAILURE:
            return failure.getErrorMessage();
        case SUBSCRIPTION:
            return subscription.getNewTopicPreferences().getTopicSubscriptionStatus().stream()
                    .map(status -> new StringBuilder()
                            .append(status.getTopicName())
                            .append(":")
                            .append(status.getSubscriptionStatus().toString()))
                    .collect(Collectors.joining(","));
        default:
            return null;
        }
    }
    
    /**
     * 수신자 이메일별 응답 메시지 조회
     * 
     * @param recipients
     * @return
     */
    private String getMessageByRecipient(final List<Recipient> recipients) {
        return recipients.stream()
                .map(recipient -> new StringBuilder()
                        .append(recipient.getEmailAddress())
                        .append(":")
                        .append(recipient.getDiagnosticCode()))
                .collect(Collectors.joining(","));
    }

    /**
     * 이벤트 오류 유형
     * 
     * @return
     */
    public String getErrorType() {
        switch (notificationType) {
        case BOUNCE:
            return bounce.getBounceType().toString();
        case COMPLAINT:
            return complaint.getComplaintFeedbackType().toString();
        case DELIVERY_DELAY:
            return deliveryDelay.getDelayType().toString();
        case RENDERING_FAILURE:
            return failure.getTemplateName();
        default:
            return null;
        }
    }

    /**
     * 이벤트 오류 하위 유형
     * 
     * @return
     */
    public String getErrorSubType() {
        switch (notificationType) {
        case BOUNCE:
            return bounce.getBounceSubType().toString();
        case COMPLAINT:
            final BounceSubType complaintSubType = complaint.getComplaintSubType();
            return complaintSubType == null ? null : complaintSubType.toString();
        default:
            return null;
        }
    }

}
