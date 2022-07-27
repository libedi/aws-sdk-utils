package io.github.libedi.awssdkutils.secretsmanager;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * RDS 보안 암호 정보
 * 
 * @author "Sangjun,Park"
 *
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class RdsSecret {
    private String host;
    private String port;
    private String username;
    private String password;
}
