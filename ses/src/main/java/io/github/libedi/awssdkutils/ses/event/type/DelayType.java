package io.github.libedi.awssdkutils.ses.event.type;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * DelayType
 *
 * @author "Sangjun,Park"
 *
 */
@RequiredArgsConstructor
@Getter
public enum DelayType implements CodeEnum {

    /** InternalFailure : 내부 Amazon SES로 문제로 인한 메시지 지연 */
    INTERNAL_FAILURE("InternalFailure"),
    /** General : SMTP 대화 중에 일반 오류 발생 */
    GENERAL("General"),
    /** MailboxFull : 수신자의 사서함이 꽉 차서 추가 메시지 수신 불가 */
    MAILBOX_FULL("MailboxFull"),
    /** SpamDetected : 수신자의 메일 서버가 사용자의 계정에서 많은 양의 원치 않는 이메일을 감지 */
    SPAM_DETECTED("SpamDetected"),
    /** RecipientServerError : 수신자의 이메일 서버에 일시적인 문제가 생겨 메시지 전송 불가 */
    RECIPIENT_SERVER_ERROR("RecipientServerError"),
    /** IPFailure : 수신자의 이메일 공급자가 메시지를 보내는 IP 주소를 차단하거나 제한 */
    IP_FAILURE("IPFailure"),
    /** TransientCommunicationFailure : 수신자의 이메일 공급자와의 SMTP 대화 중에 일시적인 통신 오류가 발생 */
    TRANSIENT_COMMUNICATION_FAILURE("TransientCommunicationFailure"),
    /** BYOIPHostNameLookupUnavailable : SES에서 IP 주소의 DNS 호스트 이름을 찾을 수 없음 */
    BYOIP_HOSTNAME_LOOKUP_UNAVAILABLE("BYOIPHostNameLookupUnavailable"),
    /** Undetermined : SES에서 전송 지연 사유 확인 불가 */
    UNDETERMINED("Undetermined"),
    /** SendingDeferral : SES가 내부적으로 메시지를 지연시키는 것이 적절하다고 판단 */
    SENDING_DEFERRAL("SendingDeferral");

    private final String code;

    private static final Map<String, DelayType> map = Arrays.stream(values())
            .collect(Collectors.toMap(CodeEnum::getCode, Function.identity()));

    @JsonCreator
    public static DelayType from(final String code) {
        return map.get(code);
    }

}
