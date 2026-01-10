package org.example.observer;

public interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(org.example.model.Alert alert);
}
