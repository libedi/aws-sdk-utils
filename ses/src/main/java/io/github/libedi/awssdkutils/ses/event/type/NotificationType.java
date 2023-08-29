package io.github.libedi.awssdkutils.ses.event.type;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * NotificationType
 *
 * @author "Sangjun,Park"
 *
 */
@RequiredArgsConstructor
@Getter
public enum NotificationType implements CodeEnum {

    /** 전송 : 전송 요청 성공 */
    SEND("Send", DeliveryStatus.NO, false, false),
    /** 렌더링 오류 : 템플릿 렌더링 오류 */
    RENDERING_FAILURE("Rendering Failure", DeliveryStatus.FAIL, true, false),
    /** 거부 : 이메일에 바이러스가 포함된 것으로 판단하여 SES가 전송 거부 */
    REJECT("Reject", DeliveryStatus.FAIL, false, false),
    /** 배달 : SES에서 수신자 메일서버 전송 성공 */
    DELIVERY("Delivery", DeliveryStatus.YES, false, false),
    /** 하드 바운스 : 수신자의 메일서버가 영구적으로 거부 */
    BOUNCE("Bounce", DeliveryStatus.FAIL, true, true),
    /** 수신 거부 : 수신자가 스팸 처리 */
    COMPLAINT("Complaint", DeliveryStatus.FAIL, true, true),
    /** 배달 지연 : 일시적인 문제 발생으로 수신자 메일서버에 전송할 수 없음. ex) 받은 편지함이 꽉참, 수신 메일서버의 일시적인 문제 발생 등등 */
    DELIVERY_DELAY("DeliveryDelay", DeliveryStatus.FAIL, true, false),
    /** 구독 : 수신자가 이메일에서 구독 취소 설정 */
    SUBSCRIPTION("Subscription", DeliveryStatus.YES, false, false),
    /** 열기 : 수신자가 메시지 열기 확인 */
    OPEN("Open", DeliveryStatus.YES, false, false),
    /** 클릭 : 수신자가 이메일 링크를 1개 이상 클릭 */
    CLICK("Click", DeliveryStatus.YES, false, false);
    
    private final String         code;
    private final DeliveryStatus deliveryStatus;
    private final boolean        errorType;
    private final boolean        errorSubType;

    private static final Map<String, NotificationType> map = Arrays.stream(values())
            .collect(Collectors.toMap(CodeEnum::getCode, Function.identity()));

    @JsonCreator
    public static NotificationType from(final String code) {
        return map.get(code);
    }

}
