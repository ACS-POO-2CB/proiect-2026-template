package org.example.command;

import org.example.exception.MissingIpAddressException;
import org.example.exception.UserException;
import org.example.model.ResourceGroup;
import org.example.model.User;
import org.example.repository.Database;

import java.util.Map;

public class RemoveMemberCommand implements Command {
    private Map<String, String> data;
    private int lineNumber;

    public RemoveMemberCommand(Map<String, String> data, int lineNumber) {
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

            String name = data.get("use_name");
            if (name == null) name = data.get("user_name");

            String role = data.get("user_role");
            if (name == null || name.isEmpty() || role == null || role.isEmpty()) {
                throw new UserException("Name and role can't be empty.");
            }

            ResourceGroup targetGroup = null;
            for (ResourceGroup group : Database.getInstance().getResourceGroups()) {
                if (group.getIpAddress().equals(ipAddress)) {
                    targetGroup = group;
                    break;
                }
            }

            if (targetGroup == null) {
                return "REMOVE MEMBER: Group not found: ipAddress = " + ipAddress;
            }

            User targetUser = null;
            for (User user : targetGroup.getMembers()) {
                if (user.getName().equals(name) && user.getRole().equals(role)) {
                    targetUser = user;
                    break;
                }
            }

            if (targetUser != null) {
                targetGroup.removeMember(targetUser);
                return "REMOVE MEMBER: " + ipAddress + ": name = " + name + " && role = " + role;
            } else {
                return "REMOVE MEMBER: Member not found: ipAddress = " + ipAddress + ": name = " + name + " && role = " + role;
            }

        } catch (MissingIpAddressException e) {
            return "REMOVE MEMBER: MissingIpAddressException: " + e.getMessage() + " ## line no: " + lineNumber;
        } catch (UserException e) {
            return "REMOVE MEMBER: UserException: " + e.getMessage() + " ## line no: " + lineNumber;
        } catch (Exception e) {
            return "REMOVE MEMBER: Error: " + e.getMessage() + " ## line no: " + lineNumber;
        }
    }
}
