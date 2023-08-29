package io.github.libedi.awssdkutils.ses.event.type;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * BounceSubType : <a href=
 * "https://docs.aws.amazon.com/ses/latest/dg/notification-contents.html#bounce-types">AWS
 * Documentation</a>
 *
 * @author "Sangjun,Park"
 *
 */
@RequiredArgsConstructor
@Getter
public enum BounceSubType implements CodeEnum {
    
    /** Undetermined : 반송 원인을 알 수 없음 */
    UNDETERMINED("Undetermined"),
    /** General : 수신자의 이메일 공급자가 반송 시킴. {@link BounceType}이 PERMANENT 인 경우, 이메일 제거 요망 */
    GENERAL("General"),
    /** NoEmail : 수신자의 이메일 공급자가 이메일이 존재하지 않음을 의미하는 반송 메시지 보냄. 이메일 제거 요망 */
    NO_EMAIL("NoEmail"),
    /** Suppressed : 수신자의 이메일 주소가 SES 전역 금지 목록에 존재함 */
    SUPPRESSED("Suppressed"),
    /** OnAccountSuppressionList : 수신자의 이메일 주소가 SES 계정수준 금지 목록에 존재함 */
    ON_ACCOUNT_SUPPRESSION_LIST("OnAccountSuppressionList"),
    /** MailboxFull : 수신자의 받은 편지함이 꽉참. 편지함이 비워지면 이메일 발송 가능 */
    MAILBOX_FULL("MailboxFull"),
    /** MessageTooLarge : 보낸 메시지의 크기가 너무 큼. 메시지의 크기를 줄일 경우 발송 가능*/
    MESSAGE_TOO_LARGE("MessageTooLarge"),
    /** ContentRejected : 메시지에 수신자의 이메일 공급자가 허용하지 않는 콘텐츠가 포함되어 있음. 콘텐츠를 변경할 경우 발송 가능 */
    CONTENT_REJECTED("ContentRejected"),
    /** AttachmentRejected : 수신자의 이메일 공급자가 허용하지 않는 첨부파일이 포함되어 있음. 첨부파일을 제거/변경할 경우 발송 가능 */
    ATTACHMENT_REJECTED("AttachmentRejected");
    
    private final String code;
    
    private static final Map<String, BounceSubType> map = Arrays.stream(values())
            .collect(Collectors.toMap(CodeEnum::getCode, Function.identity()));

    @JsonCreator
    public static BounceSubType from(final String code) {
        return map.get(code);
    }

}
