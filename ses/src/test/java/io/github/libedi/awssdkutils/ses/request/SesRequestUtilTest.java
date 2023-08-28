package io.github.libedi.awssdkutils.ses.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import io.github.libedi.awssdkutils.ses.request.InvalidMailRequestException;
import io.github.libedi.awssdkutils.ses.request.SesRequestUtil;
import io.github.libedi.awssdkutils.ses.request.domain.Attachment;
import io.github.libedi.awssdkutils.ses.request.domain.Email;
import io.github.libedi.awssdkutils.ses.request.domain.MailRequest;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.sesv2.SesV2Client;

@Slf4j
@MockitoSettings
class SesRequestUtilTest {

    SesRequestUtil util;

    @Mock
    SesV2Client sesV2Client;

    Validator validator;

    @BeforeEach
    void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        final Charset encodingCharset = StandardCharsets.UTF_8;
        final String configurationSetName = "configuration-set";
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

}
