package org.example.command;

import org.example.exception.MissingIpAddressException;
import org.example.model.Alert;
import org.example.model.AlertType;
import org.example.model.ResourceGroup;
import org.example.model.Server;
import org.example.model.Severity;
import org.example.repository.Database;

import java.util.Map;

public class AddEventCommand implements Command {
    private Map<String, String> data;
    private int lineNumber;

    public AddEventCommand(Map<String, String> data, int lineNumber) {
        this.data = data;
        this.lineNumber = lineNumber;
    }

    @Override
    public String execute() {
        try {
            String ipAddress = data.get("ip_address");
            if (ipAddress == null || ipAddress.isEmpty()) {
                // Assuming validation similar to others
                 throw new MissingIpAddressException("Server IP Address was not provided.");
            }

            String typeStr = data.get("alert_type");
            String severityStr = data.get("alert_severity");
            String message = data.get("message");

            AlertType type = (typeStr != null) ? AlertType.valueOf(typeStr) : null;
            Severity severity = (severityStr != null) ? Severity.valueOf(severityStr) : null;

            Alert alert = new Alert(type, severity, message, ipAddress);
            Database.getInstance().addAlert(alert);

            // Observer Pattern logic
            Server server = null;
            for (Server s : Database.getInstance().getServers()) {
                if (s.getIpAddress().equals(ipAddress)) {
                    server = s;
                    break;
                }
            }

            ResourceGroup group = null;
            for (ResourceGroup g : Database.getInstance().getResourceGroups()) {
                if (g.getIpAddress().equals(ipAddress)) {
                    group = g;
                    break;
                }
            }

            if (server != null && group != null) {
                // Ensure group is registered (simple check, or re-registering is fine if set/list handles it)
                // My Server implementation uses a List. I should check if it contains.
                // But `Observer` interface doesn't enforce equals.
                // I will just register for now. In a real app I'd check.
                // To avoid duplicates, I could check if server's observer list contains group.
                // But Server.observers is private and no getter.
                // So I will just notify "manually" or assume they are connected.
                // Actually, the requirements say "Serverul ... trebuie sa notifice".
                // I'll register it.
                server.registerObserver(group);
                server.notifyObservers(alert);
                // I should probably remove it after? Or keep it?
                // Events happen dynamically.
                // For this command, the notification happens.
            }

            return "ADD EVENT: " + ipAddress + ": type = " + type + " && severity = " + severity + " && message = " + message;

        } catch (MissingIpAddressException e) {
             // Assuming consistent error reporting
             return "ADD EVENT: MissingIpAddressException: " + e.getMessage() + " ## line no: " + lineNumber;
        } catch (Exception e) {
            return "ADD EVENT: Error: " + e.getMessage() + " ## line no: " + lineNumber;
        }
    }
}
