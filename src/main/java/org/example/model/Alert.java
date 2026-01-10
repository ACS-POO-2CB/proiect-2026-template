package org.example.model;

public class Alert {
    private AlertType type;
    private Severity severity;
    private String message;
    private String ipAddress;

    public Alert(AlertType type, Severity severity, String message, String ipAddress) {
        this.type = type;
        this.severity = severity;
        this.message = message;
        this.ipAddress = ipAddress;
    }

    public AlertType getType() {
        return type;
    }

    public Severity getSeverity() {
        return severity;
    }

    public String getMessage() {
        return message;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    @Override
    public String toString() {
        return "type = " + type + " && severity = " + severity + " && message = " + message;
    }
}
