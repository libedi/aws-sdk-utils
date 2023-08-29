package io.github.libedi.awssdkutils.ses.event.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * SesSubscription
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
public class SesSubscription implements TimeLocalizable {

    private String          contactList;
    private String          timestamp;
    @JsonProperty("source")
    private String          sourceAddress;
    private TopicPreference newTopicPreferences;
    private TopicPreference oldTopicPreferences;

}
