package org.example.model;

public class Location {
    private String country;
    private String city;
    private String address;
    private Double latitude;
    private Double longitude;

    private Location(Builder builder) {
        this.country = builder.country;
        this.city = builder.city;
        this.address = builder.address;
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public static class Builder {
        private String country;
        private String city;
        private String address;
        private Double latitude;
        private Double longitude;

        public Builder setCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setLatitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder setLongitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Location build() {
            return new Location(this);
        }
    }
}
