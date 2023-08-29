package io.github.libedi.awssdkutils.ses.event.domain;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * TimeLocalizable
 *
 * @author "Sangjun,Park"
 *
 */
public interface TimeLocalizable {

    String getTimestamp();

    /**
     * timestamp 값을 현지 시각으로 변환
     * 
     * @return
     */
    default LocalDateTime getLocalTimestamp() {
        return ZonedDateTime.parse(getTimestamp()).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }
}
