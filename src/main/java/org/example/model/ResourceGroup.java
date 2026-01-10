package org.example.model;

import org.example.observer.Observer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResourceGroup implements Observer {
    private List<User> members;
    private String ipAddress;

    public ResourceGroup(String ipAddress) {
        this.ipAddress = ipAddress;
        this.members = new ArrayList<>();
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public List<User> getMembers() {
        return members;
    }

    public void addMember(User user) {
        members.add(user);
    }

    public void removeMember(User user) {
        members.remove(user);
    }

    @Override
    public void update(Alert alert) {
        // Notification logic
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourceGroup that = (ResourceGroup) o;
        return Objects.equals(ipAddress, that.ipAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ipAddress);
    }
}