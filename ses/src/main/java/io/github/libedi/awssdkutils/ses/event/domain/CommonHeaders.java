package io.github.libedi.awssdkutils.ses.event.domain;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * CommonHeaders
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
public class CommonHeaders {

    private List<String> from;
    private List<String> to;
    private String       date;
    private String       messageId;
    private String       subject;

}
