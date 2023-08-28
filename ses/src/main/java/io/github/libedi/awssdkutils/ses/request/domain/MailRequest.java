package io.github.libedi.awssdkutils.ses.request.domain;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.util.CollectionUtils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.ToString;

/**
 * 메일 요청 정보
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
public class MailRequest {

    @Valid
    @NotNull
    private Email sender;

    @Valid
    @NotEmpty
    @Singular
    private List<Email> recipients;

    @Valid
    @Singular
    private List<Email> ccs;

    @Valid
    @Singular
    private List<Email> bccs;

    @NotBlank
    private String subject;
    private String content;

    @Valid
    @Singular
    private List<Attachment> attachments;

    /**
     * 발신자 이름 포함 여부
     *
     * @return
     */
    public boolean hasSenderName() {
        return sender.hasName();
    }

    /**
     * 참조자 포함 여부
     *
     * @return
     */
    public boolean hasCc() {
        return !CollectionUtils.isEmpty(ccs);
    }

    /**
     * 숨은 참조자 포함 여부
     *
     * @return
     */
    public boolean hasBcc() {
        return !CollectionUtils.isEmpty(bccs);
    }

    /**
     * 파일 첨부 여부
     *
     * @return
     */
    public boolean hasAttachment() {
        return !CollectionUtils.isEmpty(attachments);
    }

}
