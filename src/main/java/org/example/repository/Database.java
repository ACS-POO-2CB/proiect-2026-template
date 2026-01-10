package org.example.repository;

import org.example.model.Alert;
import org.example.model.ResourceGroup;
import org.example.model.Server;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Database {
    private static Database instance;
    private Set<Server> servers;
    private Set<ResourceGroup> resourceGroups;
    private Set<Alert> alerts;

    private Database() {
        servers = new HashSet<>();
        resourceGroups = new HashSet<>();
        alerts = new HashSet<>();
    }

    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Set<Server> getServers() {
        return servers;
    }

    public Set<ResourceGroup> getResourceGroups() {
        return resourceGroups;
    }

    public Set<Alert> getAlerts() {
        return alerts;
    }

    public void addServer(Server server) {
        servers.add(server);
    }

    public void addServers(Collection<Server> serversList) {
        servers.addAll(serversList);
    }

    public void addResourceGroup(ResourceGroup group) {
        resourceGroups.add(group);
    }

    public void addResourceGroups(Collection<ResourceGroup> groupsList) {
        resourceGroups.addAll(groupsList);
    }

    public void addAlert(Alert alert) {
        alerts.add(alert);
    }
}
