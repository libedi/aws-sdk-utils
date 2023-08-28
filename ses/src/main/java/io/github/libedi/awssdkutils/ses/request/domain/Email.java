package io.github.libedi.awssdkutils.ses.request.domain;

import java.io.UnsupportedEncodingException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.validation.constraints.NotBlank;

import org.springframework.util.StringUtils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 이메일 정보
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
public class Email {

    @javax.validation.constraints.Email
    @NotBlank
    private String address;
    private String name;

    /**
     * 이름 포함 여부
     *
     * @return
     */
    public boolean hasName() {
        return StringUtils.hasText(name);
    }

    /**
     * RFC822에 따라 형식화된 이름과 이메일 주소 반환 : "이름 <이메일 주소>"
     *
     * @param encodingCharset
     * @return
     */
    public String getFormattedAddress(final String encodingCharset) {
        try {
            return hasName() ? new InternetAddress(address, name, encodingCharset).toString() : address;
        } catch (final UnsupportedEncodingException e) {
            return address;
        }
    }

    /**
     * RFC822 형식의 이메일 주소 클래스 InternetAddress 반환
     *
     * @param encodingCharset
     * @return
     */
    public InternetAddress getInternetAddress(final String encodingCharset) {
        try {
            return hasName() ? new InternetAddress(address, name, encodingCharset) : new InternetAddress(address);
        } catch (AddressException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}
