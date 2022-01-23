package com.micronaute;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class StatusCommand {
    private String status;

    public StatusCommand(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}