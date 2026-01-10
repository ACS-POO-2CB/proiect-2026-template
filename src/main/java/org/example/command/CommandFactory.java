package org.example.command;

import java.util.Map;

public class CommandFactory {
    public static Command createCommand(String commandType, Map<String, String> data, int lineNumber) {
        switch (commandType) {
            case "ADD SERVER":
                return new AddServerCommand(data, lineNumber);
            case "ADD GROUP":
                return new AddGroupCommand(data, lineNumber);
            case "ADD MEMBER":
                return new AddMemberCommand(data, lineNumber);
            case "FIND GROUP":
                return new FindGroupCommand(data, lineNumber);
            case "REMOVE GROUP":
                return new RemoveGroupCommand(data, lineNumber);
            case "FIND MEMBER":
                return new FindMemberCommand(data, lineNumber);
            case "REMOVE MEMBER":
                return new RemoveMemberCommand(data, lineNumber);
            case "ADD EVENT":
                return new AddEventCommand(data, lineNumber);
            default:
                // Unknown command, maybe return null or a specific ErrorCommand
                return null;
        }
    }
}
