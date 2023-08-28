package io.github.libedi.awssdkutils.ses.request.domain;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.util.StringUtils;

import io.github.libedi.awssdkutils.ses.request.InvalidMailRequestException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 첨부파일 정보
 *
 * @author "Sangjun,Park"
 *
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
@EqualsAndHashCode
public class Attachment {

    private static final int DEFAULT_BUFFER_SIZE = 8192;

    private String fileName;
    private Path path;
    private File file;
    private InputStream inputStream;
    
    /**
     * 파일명 반환
     *
     * @return
     */
    public String getFileName() {
        if (StringUtils.hasText(fileName)) {
            return fileName;
        }
        if (path != null) {
            return path.getFileName().toString();
        }
        if (file != null) {
            return file.getName();
        }
        return "attachment";
    }

    /**
     * 파일 byte 반환
     *
     * @return
     * @throws IOException
     */
    public byte[] getBytes() throws IOException {
        if (path != null) {
            return Files.readAllBytes(path);
        }
        if (file != null) {
            return Files.readAllBytes(file.toPath());
        }
        if (inputStream != null) {
            return readAllInputStream(inputStream);
        }
        throw new InvalidMailRequestException("attachment", "No file information.");
    }

    private byte[] readAllInputStream(final InputStream inputStream) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int nRead;
        final byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        while ((nRead = inputStream.read(buffer, 0, buffer.length)) != -1) {
            baos.write(buffer, 0, nRead);
        }
        baos.flush();
        return baos.toByteArray();
    }
}
