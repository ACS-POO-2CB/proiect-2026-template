package org.example.model;

import org.example.observer.Observer;
import org.example.observer.Subject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Server implements Subject {
    private String ipAddress;
    private Location location;
    private User owner;
    
    // Optional
    private String hostname;
    private ServerStatus status;
    private Integer cpuCores;
    private Integer ramGb;
    private Integer storageGb;

    private List<Observer> observers;

    private Server(Builder builder) {
        this.ipAddress = builder.ipAddress;
        this.location = builder.location;
        this.owner = builder.owner;
        this.hostname = builder.hostname;
        this.status = builder.status;
        this.cpuCores = builder.cpuCores;
        this.ramGb = builder.ramGb;
        this.storageGb = builder.storageGb;
        this.observers = new ArrayList<>();
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public Location getLocation() {
        return location;
    }

    public User getOwner() {
        return owner;
    }

    public String getHostname() {
        return hostname;
    }
    
    public ServerStatus getStatus() {
        return status;
    }

    public Integer getCpuCores() {
        return cpuCores;
    }

    public Integer getRamGb() {
        return ramGb;
    }

    public Integer getStorageGb() {
        return storageGb;
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Alert alert) {
        for (Observer observer : observers) {
            observer.update(alert);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Server server = (Server) o;
        return Objects.equals(ipAddress, server.ipAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ipAddress);
    }

    public static class Builder {
        private String ipAddress;
        private Location location;
        private User owner;
        private String hostname;
        private ServerStatus status;
        private Integer cpuCores;
        private Integer ramGb;
        private Integer storageGb;

        public Builder setIpAddress(String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        public Builder setLocation(Location location) {
            this.location = location;
            return this;
        }

        public Builder setOwner(User owner) {
            this.owner = owner;
            return this;
        }

        public Builder setHostname(String hostname) {
            this.hostname = hostname;
            return this;
        }

        public Builder setStatus(ServerStatus status) {
            this.status = status;
            return this;
        }

        public Builder setCpuCores(Integer cpuCores) {
            this.cpuCores = cpuCores;
            return this;
        }

        public Builder setRamGb(Integer ramGb) {
            this.ramGb = ramGb;
            return this;
        }

        public Builder setStorageGb(Integer storageGb) {
            this.storageGb = storageGb;
            return this;
        }

        public Server build() {
            return new Server(this);
        }
    }
}