package io.github.libedi.awssdkutils.ses.event.domain;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * TopicPreference
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
public class TopicPreference {

    private Boolean                       unsubscribeAll;
    private List<TopicSubscriptionStatus> topicSubscriptionStatus;
    private List<TopicSubscriptionStatus> topicDefaultSubscriptionStatus;

}
