package io.github.libedi.awssdkutils.ses.request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.CollectionUtils;

import io.github.libedi.awssdkutils.ses.request.domain.Attachment;
import io.github.libedi.awssdkutils.ses.request.domain.Email;
import io.github.libedi.awssdkutils.ses.request.domain.MailRequest;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.sesv2.SesV2Client;
import software.amazon.awssdk.services.sesv2.model.Body;
import software.amazon.awssdk.services.sesv2.model.Body.Builder;
import software.amazon.awssdk.services.sesv2.model.Content;
import software.amazon.awssdk.services.sesv2.model.Destination;
import software.amazon.awssdk.services.sesv2.model.EmailContent;
import software.amazon.awssdk.services.sesv2.model.Message;
import software.amazon.awssdk.services.sesv2.model.RawMessage;
import software.amazon.awssdk.services.sesv2.model.SendEmailRequest;

/**
 * SesRequestUtil
 *
 * @author "Sangjun,Park"
 *
 */
public class SesRequestUtil {

    private static final String ENCODING_BASE64 = "B";

    private final SesV2Client sesV2Client;
    private final Validator   validator;
    private final Charset     encodingCharset;
    private final String      configurationSetName;

    public SesRequestUtil(final SesV2Client sesV2Client, final Validator validator, final Charset encodingCharset,
            final String configurationSetName) {
        this.sesV2Client = sesV2Client;
        this.validator = validator;
        this.encodingCharset = encodingCharset;
        this.configurationSetName = configurationSetName;
    }

    /**
     * 이메일 발송 : 표준 형식 / MIME 형식
     *
     * @param mailRequest
     * @return messageId
     */
    public String send(final MailRequest mailRequest) {
        validateMailRequest(mailRequest);
        final SendEmailRequest request = mailRequest.hasAttachment() ? createRawEmailRequest(mailRequest)
                : createSimpleEmailRequest(mailRequest);
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

    /**
     * 표준 이메일 형식 요청 생성
     *
     * @param mailRequest
     * @return
     */
    private SendEmailRequest createSimpleEmailRequest(final MailRequest mailRequest) {
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
        if (isHtmlCo1ntent(content)) {
            builder.html(bodyContent);
        } else {
            builder.text(bodyContent);
        }
        return builder.build();
    }

    /**
     * 메일이 HTML 형식인지 여부
     *
     * @param content
     * @return
     */
    private boolean isHtmlCo1ntent(final String content) {
        return content.toUpperCase().contains("<!DOCTYPE HTML") || content.toUpperCase().contains("<HTML");
    }

    /**
     * MIME 이메일 형식 요청 생성
     *
     * @param mailRequest
     * @return
     */
    private SendEmailRequest createRawEmailRequest(final MailRequest mailRequest) {
        try {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            createMimeMessage(mailRequest).writeTo(baos);

            final ByteBuffer buffer = ByteBuffer.wrap(baos.toByteArray());
            final byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);

            return SendEmailRequest.builder()
                    .content(EmailContent.builder()
                            .raw(RawMessage.builder()
                                    .data(SdkBytes.fromByteArray(bytes))
                                    .build())
                            .build())
                    .configurationSetName(configurationSetName)
                    .build();
        } catch (final MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * MimeMessage 생성
     * 
     * @param mailRequest
     * @return
     * @throws MessagingException
     * @throws IOException
     */
    private MimeMessage createMimeMessage(final MailRequest mailRequest) throws MessagingException, IOException {
        final MimeMessageHelper messageHelper = new MimeMessageHelper(
                new MimeMessage(Session.getDefaultInstance(new Properties())), true, encodingCharset.toString());
        messageHelper.setSubject(encodeTextByBase64(mailRequest.getSubject()));
        messageHelper.setFrom(mailRequest.getSender().getInternetAddress(encodingCharset.toString()));
        messageHelper.setTo(convertInternetAddresses(mailRequest.getRecipients()));
        if (mailRequest.hasCc()) {
            messageHelper.setCc(convertInternetAddresses(mailRequest.getCcs()));
        }
        if (mailRequest.hasBcc()) {
            messageHelper.setBcc(convertInternetAddresses(mailRequest.getBccs()));
        }
        messageHelper.setText(mailRequest.getContent(), isHtmlCo1ntent(mailRequest.getContent()));
        for (final Attachment attachment : mailRequest.getAttachments()) {
            messageHelper.addAttachment(encodeTextByBase64(attachment.getFileName()),
                    new ByteArrayDataSource(attachment.getBytes(), getMimeType(attachment.getFileName())));
        }
        return messageHelper.getMimeMessage();
    }

    /**
     * BASE64 인코딩
     * 
     * @param subject
     * @return
     * @throws UnsupportedEncodingException
     */
    private String encodeTextByBase64(final String text) throws UnsupportedEncodingException {
        return MimeUtility.encodeText(text, encodingCharset.toString(), ENCODING_BASE64);
    }
    
    /**
     * 이메일 정보를 InternetAddress 로 반환
     * 
     * @param emailList
     * @return
     */
    private InternetAddress[] convertInternetAddresses(final List<Email> emailList) {
        return emailList.stream()
                .map(email -> email.getInternetAddress(encodingCharset.toString()))
                .collect(Collectors.toList())
                .toArray(new InternetAddress[0]);
    }

    /**
     * MIME 타입 추출
     * 
     * @param fileName
     * @return
     */
    private String getMimeType(final String fileName) {
        return MediaTypeFactory.getMediaType(fileName)
                .orElse(MediaType.APPLICATION_OCTET_STREAM)
                .toString();
    }

}
