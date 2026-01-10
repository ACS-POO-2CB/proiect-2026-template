package org.example.command;

import org.example.exception.MissingIpAddressException;
import org.example.model.ResourceGroup;
import org.example.repository.Database;

import java.util.Map;

public class AddGroupCommand implements Command {
    private Map<String, String> data;
    private int lineNumber;

    public AddGroupCommand(Map<String, String> data, int lineNumber) {
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

            ResourceGroup group = new ResourceGroup(ipAddress);
            Database.getInstance().addResourceGroup(group);

            return "ADD GROUP: " + ipAddress;

        } catch (MissingIpAddressException e) {
            return "ADD GROUP: MissingIpAddressException: " + e.getMessage() + " ## line no: " + lineNumber;
        } catch (Exception e) {
            return "ADD GROUP: Error: " + e.getMessage() + " ## line no: " + lineNumber;
        }
    }
}
