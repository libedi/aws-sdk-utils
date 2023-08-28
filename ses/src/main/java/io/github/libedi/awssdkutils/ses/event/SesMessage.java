package io.github.libedi.awssdkutils.ses.event;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class SesMessage {

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

}
