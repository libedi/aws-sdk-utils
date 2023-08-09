package io.github.libedi.awssdkutils.secretsmanager;

import java.beans.ConstructorProperties;

/**
 * RDS 보안 암호 정보
 * 
 * @author "Sangjun,Park"
 *
 */
public class RdsSecret {

    private String host;
    private String port;
    private String username;
    private String password;

    public RdsSecret() {
    }

    @ConstructorProperties({ "host", "port", "username", "password" })
    public RdsSecret(final String host, final String port, final String username, final String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("RdsSecret [host=").append(host)
                .append(", port=").append(port)
                .append(", username=").append(username)
                .append(", password=").append(password)
                .append("]").toString();
    }

}