package io.github.libedi.awssdkutils.ses.event.type;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MessageType implements CodeEnum {

    /** SubscriptionConfimation : 구독확인 */
    SUBSCRIPTION_CONFIMATION("SubscriptionConfimation"),
    /** Notification : 알림 */
    NOTIFICATION("Notification"),
    /** UnsubscribeConfimation : 구독해지 확인 */
    UNSUBSCRIBE_CONFIMATION("UnsubscribeConfimation");

    private final String code;

    private static final Map<String, MessageType> map = Arrays.stream(values())
            .collect(Collectors.toMap(CodeEnum::getCode, Function.identity()));

    @JsonCreator
    public static MessageType from(final String code) {
        return map.get(code);
    }

}
