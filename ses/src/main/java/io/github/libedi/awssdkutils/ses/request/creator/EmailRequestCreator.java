package io.github.libedi.awssdkutils.ses.request.creator;

import java.nio.charset.Charset;

import io.github.libedi.awssdkutils.ses.request.domain.MailRequest;
import software.amazon.awssdk.services.sesv2.model.SendEmailRequest;

/**
 * EmailRequestCreator
 *
 * @author "Sangjun,Park"
 *
 */
public interface EmailRequestCreator {

    /**
     * 이메일 형식 요청 생성
     * 
     * @param mailRequest
     * @param configurationSetName
     * @return
     */
    SendEmailRequest createFor(MailRequest mailRequest, String configurationSetName);

    /**
     * EmailRequestCreator 생성
     * 
     * @param hasAttachment
     * @param encodingCharset
     * @return
     */
    static EmailRequestCreator get(final boolean hasAttachment, final Charset encodingCharset) {
        return hasAttachment ? new RawEmailRequestCreator(encodingCharset)
                : new SimpleEmailRequestCreator(encodingCharset);
    }

}
