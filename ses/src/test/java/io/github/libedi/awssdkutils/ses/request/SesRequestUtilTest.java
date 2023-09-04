package io.github.libedi.awssdkutils.ses.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willAnswer;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.BodyPart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.core.io.ClassPathResource;

import autoparams.AutoSource;
import io.github.libedi.awssdkutils.ses.request.domain.Attachment;
import io.github.libedi.awssdkutils.ses.request.domain.Email;
import io.github.libedi.awssdkutils.ses.request.domain.MailRequest;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.sesv2.SesV2Client;
import software.amazon.awssdk.services.sesv2.model.SendEmailRequest;
import software.amazon.awssdk.services.sesv2.model.SendEmailResponse;

@Slf4j
@MockitoSettings
class SesRequestUtilTest {

    SesRequestUtil util;

    @Mock
    SesV2Client sesV2Client;

    Validator validator;

    Charset encodingCharset;

    String configurationSetName;

    @BeforeEach
    void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        encodingCharset = StandardCharsets.UTF_8;
        configurationSetName = "configuration-set";
        util = new SesRequestUtil(sesV2Client, validator, encodingCharset, configurationSetName);
        assertThat(util).isNotNull();
    }

    @DisplayName("메일 요청 정보 유효성 검사")
    @Test
    void validationMailInfo() {
        // given
        final MailRequest request = MailRequest.builder()
                .sender(Email.builder().build())
                .recipient(Email.builder().build())
                .attachment(Attachment.builder().build())
                .build();

        // when - then
        assertThatExceptionOfType(InvalidMailRequestException.class)
                .isThrownBy(() -> {
                    try {
                        util.send(request);
                    } catch (InvalidMailRequestException e) {
                        log.error(e.getMessage(), e);
                        throw e;
                    }
                });
    }

    @DisplayName("Simple 이메일 발송 테스트 : 텍스트")
    @ParameterizedTest
    @AutoSource
    void sendSimpleEmailForText(final String messageId) {
        // given
        final MailRequest mailRequest = MailRequest.builder()
                .sender(Email.builder()
                        .address("sender@test.com")
                        .name("발신자")
                        .build())
                .recipient(Email.builder()
                        .address("recipient@test.com")
                        .name("수신자")
                        .build())
                .cc(Email.builder()
                        .address("cc@test.com")
                        .name("참조자")
                        .build())
                .bcc(Email.builder()
                        .address("bcc@test.com")
                        .name("숨은참조자")
                        .build())
                .subject("제목")
                .content("테스트 content")
                .build();
        
        final SendEmailResponse expected = SendEmailResponse.builder()
                .messageId(messageId)
                .build();
        
        willAnswer(invocation -> {
            final SendEmailRequest argument = invocation.getArgument(0, SendEmailRequest.class);
            assertThat(argument.fromEmailAddress())
                    .isEqualTo(mailRequest.getSender().getFormattedAddress(encodingCharset.toString()));
            assertThat(argument.destination()).satisfies(destination -> {
                assertThat(destination.toAddresses()).isEqualTo(convertFormattedAddresses(mailRequest.getRecipients()));
                assertThat(destination.ccAddresses()).isEqualTo(convertFormattedAddresses(mailRequest.getCcs()));
                assertThat(destination.bccAddresses()).isEqualTo(convertFormattedAddresses(mailRequest.getBccs()));
            });
            assertThat(argument.content().simple()).satisfies(message -> {
                assertThat(message.subject().data()).isEqualTo(mailRequest.getSubject());
                assertThat(message.body().text().data()).isEqualTo(mailRequest.getContent());
            });
            assertThat(argument.configurationSetName()).isEqualTo(configurationSetName);
            System.out.println(argument);
            return expected;
        }).given(sesV2Client).sendEmail(any(SendEmailRequest.class));

        // when
        final String actual = util.send(mailRequest);

        // then
        assertThat(actual).isEqualTo(messageId);
        then(sesV2Client).should().sendEmail(any(SendEmailRequest.class));
    }

    @DisplayName("Simple 이메일 발송 테스트 : HTML")
    @ParameterizedTest
    @AutoSource
    void sendSimpleEmailForHtml(final String messageId) {
        // given
        final MailRequest mailRequest = MailRequest.builder()
                .sender(Email.builder()
                        .address("sender@test.com")
                        .name("발신자")
                        .build())
                .recipient(Email.builder()
                        .address("recipient@test.com")
                        .name("수신자")
                        .build())
                .cc(Email.builder()
                        .address("cc@test.com")
                        .name("참조자")
                        .build())
                .bcc(Email.builder()
                        .address("bcc@test.com")
                        .name("숨은참조자")
                        .build())
                .subject("제목")
                .content("\r\n\t    <!DOCTYPE html>\r\n<html>\r\n<head></head>\r\n<body>테스트 content</body>\r\n</html>")
                .build();
        
        final SendEmailResponse expected = SendEmailResponse.builder()
                .messageId(messageId)
                .build();
        
        willAnswer(invocation -> {
            final SendEmailRequest argument = invocation.getArgument(0, SendEmailRequest.class);
            assertThat(argument.fromEmailAddress())
                    .isEqualTo(mailRequest.getSender().getFormattedAddress(encodingCharset.toString()));
            assertThat(argument.destination()).satisfies(destination -> {
                assertThat(destination.toAddresses()).isEqualTo(convertFormattedAddresses(mailRequest.getRecipients()));
                assertThat(destination.ccAddresses()).isEqualTo(convertFormattedAddresses(mailRequest.getCcs()));
                assertThat(destination.bccAddresses()).isEqualTo(convertFormattedAddresses(mailRequest.getBccs()));
            });
            assertThat(argument.content().simple()).satisfies(message -> {
                assertThat(message.subject().data()).isEqualTo(mailRequest.getSubject());
                assertThat(message.body().html().data()).isEqualTo(mailRequest.getContent());
            });
            assertThat(argument.configurationSetName()).isEqualTo(configurationSetName);
            System.out.println(argument);
            return expected;
        }).given(sesV2Client).sendEmail(any(SendEmailRequest.class));

        // when
        final String actual = util.send(mailRequest);

        // then
        assertThat(actual).isEqualTo(messageId);
        then(sesV2Client).should().sendEmail(any(SendEmailRequest.class));
    }

    private List<String> convertFormattedAddresses(final List<Email> emailList) {
        return emailList.stream()
                .map(email -> email.getFormattedAddress(encodingCharset.toString()))
                .collect(Collectors.toList());
    }

    @DisplayName("Raw 이메일 발송 테스트")
    @ParameterizedTest
    @AutoSource
    void sendRawMail(final String messageId) throws Exception {
        // given
        final Path attachPath = Paths.get(new ClassPathResource("attach/attachment.txt").getURI());
        final MailRequest mailRequest = MailRequest.builder()
                .sender(Email.builder()
                        .address("sender@test.com")
                        .name("발신자")
                        .build())
                .recipient(Email.builder()
                        .address("recipient@test.com")
                        .name("수신자")
                        .build())
                .cc(Email.builder()
                        .address("cc@test.com")
                        .name("참조자")
                        .build())
                .bcc(Email.builder()
                        .address("bcc@test.com")
                        .name("숨은참조자")
                        .build())
                .subject("제목")
                .content("<!DOCTYPE html><html><head></head><body>테스트 content</body></html>")
                .attachment(Attachment.builder()
                        .path(attachPath)
                        .fileName("첨부파일.txt")
                        .build())
                .build();

        final SendEmailResponse expected = SendEmailResponse.builder()
                .messageId(messageId)
                .build();

        willAnswer(invocation -> {
            final SendEmailRequest argument = invocation.getArgument(0, SendEmailRequest.class);
            final InputStream inputStream = argument.content().raw().data().asInputStream();
            final MimeMessage mime = new MimeMessage(Session.getDefaultInstance(new Properties()), inputStream);
            
            assertThat(mime.getSubject()).isEqualTo(mailRequest.getSubject());
            
            final InternetAddress sender = (InternetAddress) mime.getFrom()[0];
            assertThat(sender).isEqualTo(mailRequest.getSender().getInternetAddress(encodingCharset.toString()));
            
            final InternetAddress[] recipients = (InternetAddress[]) mime.getRecipients(RecipientType.TO);
            assertThat(recipients).isEqualTo(convertInternetAddresses(mailRequest.getRecipients()));

            final InternetAddress[] ccs = (InternetAddress[]) mime.getRecipients(RecipientType.CC);
            assertThat(ccs).isEqualTo(convertInternetAddresses(mailRequest.getCcs()));

            final InternetAddress[] bccs = (InternetAddress[]) mime.getRecipients(RecipientType.BCC);
            assertThat(bccs).isEqualTo(convertInternetAddresses(mailRequest.getBccs()));

            final MimeMultipart parent = (MimeMultipart) mime.getContent();
            final BodyPart contentPart = parent.getBodyPart(0);
            final MimeMultipart htmlPart = (MimeMultipart) contentPart.getContent();
            assertThat(htmlPart.getBodyPart(0).getContent()).asString().isEqualTo(mailRequest.getContent());

            final BodyPart attachPart = parent.getBodyPart(1);
            assertThat(attachPart.getFileName()).isEqualTo(MimeUtility
                    .encodeText(mailRequest.getAttachments().get(0).getFileName(), encodingCharset.toString(), "B"));
            assertThat(attachPart.getInputStream()).hasSameContentAs(Files.newInputStream(attachPath));

            assertThat(argument.configurationSetName()).isEqualTo(configurationSetName);

            System.out.println(argument);

            return expected;
        }).given(sesV2Client).sendEmail(any(SendEmailRequest.class));

        // when
        String actual = util.send(mailRequest);

        // then
        assertThat(actual).isEqualTo(messageId);
        then(sesV2Client).should().sendEmail(any(SendEmailRequest.class));
    }
    
    private InternetAddress[] convertInternetAddresses(final List<Email> emailList) {
        return emailList.stream()
                .map(email -> email.getInternetAddress(encodingCharset.toString()))
                .collect(Collectors.toList())
                .toArray(new InternetAddress[0]);
    }

}
