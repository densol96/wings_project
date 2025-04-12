package lv.wings.exception.admin;

import lombok.Getter;

@Getter
public class FailedJobException extends RuntimeException {

    private String jobName;

    public FailedJobException(String jobName, String message) {
        super(message);
        this.jobName = jobName;
    }
}
