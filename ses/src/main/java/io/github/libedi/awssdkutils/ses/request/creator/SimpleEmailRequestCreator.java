package io.github.libedi.awssdkutils.ses.request.creator;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import io.github.libedi.awssdkutils.ses.request.domain.Email;
import io.github.libedi.awssdkutils.ses.request.domain.MailRequest;
import software.amazon.awssdk.services.sesv2.model.Body;
import software.amazon.awssdk.services.sesv2.model.Content;
import software.amazon.awssdk.services.sesv2.model.Destination;
import software.amazon.awssdk.services.sesv2.model.EmailContent;
import software.amazon.awssdk.services.sesv2.model.Message;
import software.amazon.awssdk.services.sesv2.model.SendEmailRequest;
import software.amazon.awssdk.services.sesv2.model.Body.Builder;

/**
 * 표준 이메일 형식 요청 생성
 *
 * @author "Sangjun,Park"
 *
 */
public class SimpleEmailRequestCreator extends AbstractEmailRequestCreator {

    /**
     * 표준 이메일 형식 요청 생성
     * 
     * @param encodingCharset
     */
    public SimpleEmailRequestCreator(final Charset encodingCharset) {
        super(encodingCharset);
    }

    @Override
    public SendEmailRequest createFor(final MailRequest mailRequest, final String configurationSetName) {
        return SendEmailRequest.builder()
                .fromEmailAddress(mailRequest.getSender().getFormattedAddress(encodingCharset.toString()))
                .destination(Destination.builder()
                        .toAddresses(convertFormattedAddressList(mailRequest.getRecipients()))
                        .ccAddresses(convertFormattedAddressList(mailRequest.getCcs()))
                        .bccAddresses(convertFormattedAddressList(mailRequest.getBccs()))
                        .build())
                .content(EmailContent.builder()
                        .simple(Message.builder()
                                .subject(Content.builder()
                                        .data(mailRequest.getSubject())
                                        .charset(encodingCharset.toString())
                                        .build())
                                .body(createBody(mailRequest.getContent()))
                                .build())
                        .build())
                .configurationSetName(configurationSetName)
                .build();
    }
    
    /**
     * 형식화된 이메일 주소 반환
     *
     * @param emailList
     * @return
     */
    private Collection<String> convertFormattedAddressList(final List<Email> emailList) {
        return emailList.stream()
                .map(email -> email.getFormattedAddress(encodingCharset.toString()))
                .collect(Collectors.toList());
    }

    /**
     * 메시지 body 생성
     *
     * @param content
     * @return
     */
    private Body createBody(final String content) {
        final Builder builder = Body.builder();
        final Content bodyContent = Content.builder()
                .data(content)
                .charset(encodingCharset.toString())
                .build();
        if (isHtmlContent(content)) {
            builder.html(bodyContent);
        } else {
            builder.text(bodyContent);
        }
        return builder.build();
    }

}
