package io.github.libedi.awssdkutils.ses.event.type;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * BounceType : <a href=
 * "https://docs.aws.amazon.com/ses/latest/dg/notification-contents.html#bounce-types">AWS
 * Documentation</a>
 *
 * @author "Sangjun,Park"
 *
 */
@RequiredArgsConstructor
@Getter
public enum BounceType implements CodeEnum {
    
    /** Undetermined : 반송 원인을 알 수 없음 */
    UNDETERMINED("Undetermined"),
    /** Permanent : 영구적일 수 있는 반송. 수신자 목록 중 제거 대상 */
    PERMANENT("Permanent"),
    /** Transient : 일시적일 수 있는 반송. 원인 해결 후, 발송 가능 */
    TRANSIENT("Transient");

    private final String code;

    private static final Map<String, BounceType> map = Arrays.stream(values())
            .collect(Collectors.toMap(CodeEnum::getCode, Function.identity()));

    @JsonCreator
    public static BounceType from(final String code) {
        return map.get(code);
    }
}
