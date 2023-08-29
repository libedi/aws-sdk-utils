package io.github.libedi.awssdkutils.ses.event.type;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * ComplaintFeedbackType : <a href=
 * "https://docs.aws.amazon.com/ses/latest/dg/notification-contents.html#complaint-types">AWS
 * Documentation</a>
 *
 * @author "Sangjun,Park"
 *
 */
@RequiredArgsConstructor
@Getter
public enum ComplaintFeedbackType implements CodeEnum {

    /** abuse : 원치 않는 이메일 또는 기타 유형의 이메일 침해 */
    ABUSE("abuse"),
    /** auth-failure : 이메일 인증 실패 보고서 */
    AUTH_FAILURE("auth-failure"),
    /** fraud : 일종의 사기 또는 피싱 활동 */
    FRAUD("fraud"),
    /** not-spam : 보고서를 제공하는 엔터티가 메시지를 스팸으로 간주하지 않음 */
    NOT_SPAM("not-spam"),
    /** other : 다른 등록된 유형에 들어맞지 않는 기타 피드백 */
    OTHER("other"),
    /** virus : 발원 메시지에서 바이러스가 발견되었다는 보고서 */
    VIRUS("virus");

    private final String code;

    private static final Map<String, ComplaintFeedbackType> map = Arrays.stream(values())
            .collect(Collectors.toMap(CodeEnum::getCode, Function.identity()));

    @JsonCreator
    public static ComplaintFeedbackType from(final String code) {
        return map.get(code);
    }

}
