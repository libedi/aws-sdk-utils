package io.github.libedi.awssdkutils.ses.request.creator;

import java.nio.charset.Charset;

/**
 * AbstractEmailRequestCreator
 *
 * @author "Sangjun,Park"
 *
 */
public abstract class AbstractEmailRequestCreator implements EmailRequestCreator {

    protected final Charset encodingCharset;

    protected AbstractEmailRequestCreator(final Charset encodingCharset) {
        this.encodingCharset = encodingCharset;
    }

    /**
     * 메일이 HTML 형식인지 여부
     *
     * @param content
     * @return
     */
    protected boolean isHtmlContent(final String content) {
        return content.toUpperCase().contains("<!DOCTYPE HTML") || content.toUpperCase().contains("<HTML");
    }
}
