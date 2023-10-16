package io.github.libedi.awssdkutils.ses.request.creator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.mail.javamail.MimeMessageHelper;

import io.github.libedi.awssdkutils.ses.request.domain.Attachment;
import io.github.libedi.awssdkutils.ses.request.domain.Email;
import io.github.libedi.awssdkutils.ses.request.domain.MailRequest;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.sesv2.model.EmailContent;
import software.amazon.awssdk.services.sesv2.model.RawMessage;
import software.amazon.awssdk.services.sesv2.model.SendEmailRequest;

/**
 * MIME 이메일 형식 요청 생성
 *
 * @author "Sangjun,Park"
 *
 */
public class RawEmailRequestCreator extends AbstractEmailRequestCreator {

    private static final String ENCODING_BASE64 = "B";

    /**
     * MIME 이메일 형식 요청 생성
     * 
     * @param encodingCharset
     */
    public RawEmailRequestCreator(final Charset encodingCharset) {
        super(encodingCharset);
    }

    @Override
    public SendEmailRequest createFor(final MailRequest mailRequest, final String configurationSetName) {
        try {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            createMimeMessage(mailRequest, encodingCharset).writeTo(baos);

            final ByteBuffer buffer = ByteBuffer.wrap(baos.toByteArray());
            final byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);

            return SendEmailRequest.builder()
                    .content(EmailContent.builder()
                            .raw(RawMessage.builder()
                                    .data(SdkBytes.fromByteArray(bytes))
                                    .build())
                            .build())
                    .configurationSetName(configurationSetName).build();
        } catch (final MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * MimeMessage 생성
     * 
     * @param mailRequest
     * @param encodingCharset
     * @return
     * @throws MessagingException
     * @throws IOException
     */
    private MimeMessage createMimeMessage(final MailRequest mailRequest, final Charset encodingCharset)
            throws MessagingException, IOException {
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
        messageHelper.setText(mailRequest.getContent(), isHtmlContent(mailRequest.getContent()));
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
                .collect(Collectors.toList()).toArray(new InternetAddress[0]);
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
