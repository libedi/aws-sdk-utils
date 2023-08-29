package io.github.libedi.awssdkutils.ses.event.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * DeliveryStatus
 *
 * @author "Sangjun,Park"
 *
 */
@RequiredArgsConstructor
@Getter
public enum DeliveryStatus implements CodeEnum {

    /** 수신자 전달 성공 : Y */
    YES("Y"),
    /** 미전달 : N */
    NO("N"),
    /** 수신자 전달 실패 : F */
    FAIL("F");

    private final String code;

}
