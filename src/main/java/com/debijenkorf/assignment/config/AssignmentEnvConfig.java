package com.debijenkorf.assignment.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "environment")
public class AssignmentEnvConfig {

    String log_stdout, source_root_url, aws_s3_endpoint, aws_accesskey, aws_secretkey, logdb_endpoint, logdb_name, logdb_username, logdb_password;

    public String getLog_stdout() {
        return log_stdout;
    }

    public void setLog_stdout(String log_stdout) {
        this.log_stdout = log_stdout;
    }

    public String getSource_root_url() {
        return source_root_url;
    }

    public void setSource_root_url(String source_root_url) {
        this.source_root_url = source_root_url;
    }

    public String getAws_s3_endpoint() {
        return aws_s3_endpoint;
    }

    public void setAws_s3_endpoint(String aws_s3_endpoint) {
        this.aws_s3_endpoint = aws_s3_endpoint;
    }

    public String getAws_accesskey() {
        return aws_accesskey;
    }

    public void setAws_accesskey(String aws_accesskey) {
        this.aws_accesskey = aws_accesskey;
    }

    public String getAws_secretkey() {
        return aws_secretkey;
    }

    public void setAws_secretkey(String aws_secretkey) {
        this.aws_secretkey = aws_secretkey;
    }

    public String getLogdb_endpoint() {
        return logdb_endpoint;
    }

    public void setLogdb_endpoint(String logdb_endpoint) {
        this.logdb_endpoint = logdb_endpoint;
    }

    public String getLogdb_name() {
        return logdb_name;
    }

    public void setLogdb_name(String logdb_name) {
        this.logdb_name = logdb_name;
    }

    public String getLogdb_username() {
        return logdb_username;
    }

    public void setLogdb_username(String logdb_username) {
        this.logdb_username = logdb_username;
    }

    public String getLogdb_password() {
        return logdb_password;
    }

    public void setLogdb_password(String logdb_password) {
        this.logdb_password = logdb_password;
    }
}
