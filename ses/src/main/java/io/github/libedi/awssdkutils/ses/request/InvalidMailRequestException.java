package io.github.libedi.awssdkutils.ses.request;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

import io.github.libedi.awssdkutils.ses.request.domain.MailRequest;

/**
 * 메일 요청 정보 유효성 검증 예외
 *
 * @author "Sangjun,Park"
 *
 */
public class InvalidMailRequestException extends RuntimeException {

    private static final long serialVersionUID = 1725686635212721311L;

    public InvalidMailRequestException(final Set<ConstraintViolation<MailRequest>> violation) {
        super(String.format("Please check the email request information.\n%s", violation.stream()
                .map(cv -> String.format("- %s: %s", cv.getPropertyPath(), cv.getMessage()))
                .collect(Collectors.joining("\n"))));
    }

    public InvalidMailRequestException(final String field, final String message) {
        super(String.format("Please check the email request information.\n- %s: %s", field, message));
    }

}
