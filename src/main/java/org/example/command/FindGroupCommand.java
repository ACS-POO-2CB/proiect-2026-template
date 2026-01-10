package org.example.command;

import org.example.exception.MissingIpAddressException;
import org.example.model.ResourceGroup;
import org.example.repository.Database;

import java.util.Map;

public class FindGroupCommand implements Command {
    private Map<String, String> data;
    private int lineNumber;

    public FindGroupCommand(Map<String, String> data, int lineNumber) {
        this.data = data;
        this.lineNumber = lineNumber;
    }

    @Override
    public String execute() {
        try {
            String ipAddress = data.get("ip_address");
            if (ipAddress == null || ipAddress.isEmpty()) {
                throw new MissingIpAddressException("Server IP Address was not provided.");
            }

            ResourceGroup targetGroup = null;
            for (ResourceGroup group : Database.getInstance().getResourceGroups()) {
                if (group.getIpAddress().equals(ipAddress)) {
                    targetGroup = group;
                    break;
                }
            }

            if (targetGroup != null) {
                return "FIND GROUP: " + ipAddress;
            } else {
                return "FIND GROUP: Group not found: ipAddress = " + ipAddress;
            }

        } catch (MissingIpAddressException e) {
            return "FIND GROUP: MissingIpAddressException: " + e.getMessage() + " ## line no: " + lineNumber;
        } catch (Exception e) {
            return "FIND GROUP: Error: " + e.getMessage() + " ## line no: " + lineNumber;
        }
    }
}
