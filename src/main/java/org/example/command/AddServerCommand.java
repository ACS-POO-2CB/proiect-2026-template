package org.example.command;

import org.example.exception.LocationException;
import org.example.exception.MissingIpAddressException;
import org.example.exception.UserException;
import org.example.model.Location;
import org.example.model.Server;
import org.example.model.ServerStatus;
import org.example.model.User;
import org.example.model.Operator;
import org.example.model.Admin;
import org.example.repository.Database;

import java.util.Map;

public class AddServerCommand implements Command {
    private Map<String, String> data;
    private int lineNumber;

    public AddServerCommand(Map<String, String> data, int lineNumber) {
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

            String country = data.get("country");
            if (country == null || country.isEmpty()) {
                throw new LocationException("Country is missing.");
            }

            String name = data.get("user_name");
            String role = data.get("user_role");
            if (name == null || name.isEmpty() || role == null || role.isEmpty()) {
                throw new UserException("Name and role can't be empty.");
            }

            // Create User
            String email = data.get("user_email");
            User owner;
            if ("Admin".equalsIgnoreCase(role)) {
                String department = data.get("user_department");
                String clearanceStr = data.get("user_clearance_level");
                int clearance = (clearanceStr != null && !clearanceStr.isEmpty()) ? Integer.parseInt(clearanceStr) : 0;
                owner = new Admin(name, role, email, department, clearance);
            } else if ("Operator".equalsIgnoreCase(role)) {
                String department = data.get("user_department");
                owner = new Operator(name, role, email, department);
            } else {
                owner = new User(name, role, email);
            }

            // Create Location
            String city = data.get("city");
            String address = data.get("address");
            String latStr = data.get("latitude");
            String lonStr = data.get("longitude");
            Double lat = (latStr != null && !latStr.isEmpty()) ? Double.parseDouble(latStr) : null;
            Double lon = (lonStr != null && !lonStr.isEmpty()) ? Double.parseDouble(lonStr) : null;

            Location location = new Location.Builder()
                    .setCountry(country)
                    .setCity(city)
                    .setAddress(address)
                    .setLatitude(lat)
                    .setLongitude(lon)
                    .build();

            // Create Server
            String hostname = data.get("hostname");
            String statusStr = data.get("server_status");
            ServerStatus status = (statusStr != null && !statusStr.isEmpty()) ? ServerStatus.valueOf(statusStr) : null;
            
            String cpuStr = data.get("cpu_cores");
            String ramStr = data.get("ram_gb");
            String storageStr = data.get("storage");
            
            Integer cpu = (cpuStr != null && !cpuStr.isEmpty()) ? Integer.parseInt(cpuStr) : null;
            Integer ram = (ramStr != null && !ramStr.isEmpty()) ? Integer.parseInt(ramStr) : null;
            Integer storage = (storageStr != null && !storageStr.isEmpty()) ? Integer.parseInt(storageStr) : null;

            Server server = new Server.Builder()
                    .setIpAddress(ipAddress)
                    .setLocation(location)
                    .setOwner(owner)
                    .setHostname(hostname)
                    .setStatus(status)
                    .setCpuCores(cpu)
                    .setRamGb(ram)
                    .setStorageGb(storage)
                    .build();

            Database.getInstance().addServer(server);
            
            // Format output: ADD SERVER: <ipAddress>: <serverStatus>
            return "ADD SERVER: " + ipAddress + ": " + status;

        } catch (MissingIpAddressException e) {
            return "ADD SERVER: MissingIpAddressException: " + e.getMessage() + " ## line no: " + lineNumber;
        } catch (UserException e) {
            return "ADD SERVER: UserException: " + e.getMessage() + " ## line no: " + lineNumber;
        } catch (LocationException e) {
            return "ADD SERVER: LocationException: " + e.getMessage() + " ## line no: " + lineNumber;
        } catch (Exception e) {
            return "ADD SERVER: Error: " + e.getMessage() + " ## line no: " + lineNumber;
        }
    }
}
