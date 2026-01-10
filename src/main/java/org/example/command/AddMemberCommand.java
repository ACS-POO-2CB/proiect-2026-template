package org.example.command;

import org.example.exception.MissingIpAddressException;
import org.example.exception.UserException;
import org.example.model.Admin;
import org.example.model.Operator;
import org.example.model.ResourceGroup;
import org.example.model.User;
import org.example.repository.Database;

import java.util.Map;

public class AddMemberCommand implements Command {
    private Map<String, String> data;
    private int lineNumber;

    public AddMemberCommand(Map<String, String> data, int lineNumber) {
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

            String name = data.get("use_name"); // typo in input header "use_name" vs "user_name"?
            // In groups_01.in it says "use_name". In servers_01.in "user_name".
            // I need to be careful. I'll try both or look at the input file specifically.
            if (name == null) name = data.get("user_name");

            String role = data.get("user_role");
            if (name == null || name.isEmpty() || role == null || role.isEmpty()) {
                throw new UserException("Name and role can't be empty.");
            }

            // Find group
            ResourceGroup targetGroup = null;
            for (ResourceGroup group : Database.getInstance().getResourceGroups()) {
                if (group.getIpAddress().equals(ipAddress)) {
                    targetGroup = group;
                    break;
                }
            }

            if (targetGroup == null) {
                // Undocumented behavior. 
                return "ADD MEMBER: Group not found: " + ipAddress; 
            }

            String email = data.get("user_email");
            User user;
            if ("Admin".equalsIgnoreCase(role)) {
                String department = data.get("department"); // input header "department"
                String clearanceStr = data.get("clearance_level"); // input header "clearance_level"
                int clearance = (clearanceStr != null && !clearanceStr.isEmpty()) ? Integer.parseInt(clearanceStr) : 0;
                user = new Admin(name, role, email, department, clearance);
            } else if ("Operator".equalsIgnoreCase(role)) {
                String department = data.get("department");
                user = new Operator(name, role, email, department);
            } else {
                user = new User(name, role, email);
            }

            targetGroup.addMember(user);

            return "ADD MEMBER: " + ipAddress + ": name = " + name + " && role = " + role;

        } catch (MissingIpAddressException e) {
            return "ADD MEMBER: MissingIpAddressException: " + e.getMessage() + " ## line no: " + lineNumber;
        } catch (UserException e) {
            return "ADD MEMBER: UserException: " + e.getMessage() + " ## line no: " + lineNumber;
        } catch (Exception e) {
            return "ADD MEMBER: Error: " + e.getMessage() + " ## line no: " + lineNumber;
        }
    }
}
