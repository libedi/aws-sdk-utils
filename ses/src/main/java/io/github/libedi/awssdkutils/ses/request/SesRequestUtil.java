package io.github.libedi.awssdkutils.ses.request;

import java.nio.charset.Charset;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.util.CollectionUtils;

import io.github.libedi.awssdkutils.ses.request.creator.EmailRequestCreator;
import io.github.libedi.awssdkutils.ses.request.domain.MailRequest;
import software.amazon.awssdk.services.sesv2.SesV2Client;
import software.amazon.awssdk.services.sesv2.model.SendEmailRequest;

/**
 * SesRequestUtil
 *
 * @author "Sangjun,Park"
 *
 */
public class SesRequestUtil {

    private final SesV2Client sesV2Client;
    private final Validator   validator;
    private final Charset     encodingCharset;
    private final String      configurationSetName;

    /**
     * SesRequestUtil
     * 
     * @param sesV2Client
     * @param validator
     * @param encodingCharset
     * @param configurationSetName
     */
    public SesRequestUtil(final SesV2Client sesV2Client, final Validator validator, final Charset encodingCharset,
            final String configurationSetName) {
        this.sesV2Client = sesV2Client;
        this.validator = validator;
        this.encodingCharset = encodingCharset;
        this.configurationSetName = configurationSetName;
    }

    /**
     * 이메일 발송
     *
     * @param mailRequest
     * @return messageId
     */
    public String send(final MailRequest mailRequest) {
        validateMailRequest(mailRequest);
        final SendEmailRequest request = EmailRequestCreator.get(mailRequest.hasAttachment(), encodingCharset)
                .createFor(mailRequest, configurationSetName);
        return sesV2Client.sendEmail(request).messageId();
    }

    /**
     * 메일 요청 정보 유효성 검사
     *
     * @param mailRequest
     */
    private void validateMailRequest(final MailRequest mailRequest) {
        final Set<ConstraintViolation<MailRequest>> violation = validator.validate(mailRequest);
        if (!CollectionUtils.isEmpty(violation)) {
            throw new InvalidMailRequestException(violation);
        }
    }

}
