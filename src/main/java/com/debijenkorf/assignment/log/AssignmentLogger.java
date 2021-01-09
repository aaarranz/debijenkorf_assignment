package com.debijenkorf.assignment.log;

import com.debijenkorf.assignment.config.AssignmentEnvConfig;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.logging.LogLevel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * Assignment Logger
 *
 * All logs in assignment must be requests through this singleton. Here it is decided if log must be send to stdout,
 * a file, a database or whatever, depending on Assignment configuration
 */

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AssignmentLogger {
    @Autowired
    private AssignmentEnvConfig envs;

    // Database connection
    static private Connection conn = null;
    static private boolean isConnectionInitialized = false;

    private boolean initiliazeDbConnection() {
        // Mock Database connection init with connection parameters
        return true;
    }

    public void log(Class instanceClass, LogLevel level, String message) {

        if (envs.getLog_stdout().equalsIgnoreCase("console")) {
            switch (level) {
                case TRACE:
                    LoggerFactory.getLogger(instanceClass).trace(message);
                    break;
                case DEBUG:
                    LoggerFactory.getLogger(instanceClass).debug(message);
                    break;
                case INFO:
                    LoggerFactory.getLogger(instanceClass).info(message);
                    break;
                case WARN:
                    LoggerFactory.getLogger(instanceClass).warn(message);
                    break;
                case ERROR:
                case FATAL:
                    LoggerFactory.getLogger(instanceClass).error(message);
                    break;
                default:
                    // Nothing
            }
        } else if (envs.getLog_stdout().equalsIgnoreCase("database")) {

            if(!isConnectionInitialized) {
                isConnectionInitialized = initiliazeDbConnection();
            }

            if(isConnectionInitialized) {
                // TODO: Mock Database statement for insert logs
            }
        }
    }
}
