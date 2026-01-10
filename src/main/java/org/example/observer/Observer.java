package org.example.observer;

import org.example.model.Alert;

public interface Observer {
    void update(Alert alert);
}
