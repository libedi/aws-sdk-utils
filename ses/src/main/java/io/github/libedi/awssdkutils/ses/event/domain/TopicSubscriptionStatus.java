package io.github.libedi.awssdkutils.ses.event.domain;

import io.github.libedi.awssdkutils.ses.event.type.SubscriptionStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * TopicSubscriptionStatus
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
public class TopicSubscriptionStatus {

    private String             topicName;
    private SubscriptionStatus subscriptionStatus;

}
