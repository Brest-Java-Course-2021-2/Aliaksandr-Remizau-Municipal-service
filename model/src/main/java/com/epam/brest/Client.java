package com.epam.brest;

import java.util.Objects;

public class Client {
    private Integer clientId;
    private String clientName;

    public Client(){
    }

    public Client(String clientName) {
        this.clientName = clientName;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ((!(o instanceof Client))) return false;
        Client client = (Client) o;
        return Objects.equals(getClientId(), client.getClientId()) &&
                Objects.equals(getClientName(), client.getClientName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClientId(), getClientName());
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", clientName='" + clientName + '\'' +
                '}';
    }
}
