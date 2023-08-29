package io.github.libedi.awssdkutils.ses.event.type;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * SubscriptionStatus
 *
 * @author "Sangjun,Park"
 *
 */
@RequiredArgsConstructor
@Getter
public enum SubscriptionStatus implements CodeEnum {

    /** OptIn : 선택 */
    OPT_IN("OptIn"),
    /** OptOut : 선택해제 */
    OPT_OUT("OptOut");

    private final String code;

    private static final Map<String, SubscriptionStatus> map = Arrays.stream(values())
            .collect(Collectors.toMap(CodeEnum::getCode, Function.identity()));

    @JsonCreator
    public static SubscriptionStatus from(final String code) {
        return map.get(code);
    }
}
