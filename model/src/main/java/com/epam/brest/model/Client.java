package com.epam.brest.model;

import org.springframework.lang.NonNull;

import java.util.Objects;
/**
 * POJO Client for model.
 */
public class Client {
    /**
     * Client Id.
     */
    private Integer clientId;
    /**
     * Client Name.
     */
    private String clientName;

    /**
     * Constructor without arguments.
     */
    public Client(){
    }

    /**
     * Constructor with  clientId and client name.
     *
     * @param clientId client ID
     * @param clientName client name
     */
    public Client(Integer clientId, String clientName) {
        this.clientId = clientId;
        this.clientName = clientName;
    }

    /**
     * Constructor with client name.
     *
     * @param clientName client name
     */
    public Client(String clientName) {
        this.clientName = clientName;
    }

    /**
     * Returns <code>Integer</code> representation of this clientId.
     *
     * @return clientId Client Id.
     */
    public Integer getClientId() {
        return clientId;
    }

    /**
     * Set the client's identifier.
     *
     * @param clientId Client Id.
     */
    public void setClientId( final Integer clientId) {
        this.clientId = clientId;
    }

    /**
     * Returns <code>String</code> representation of this clientName.
     *
     * @return clientName Client Name.
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * Sets the client's name.
     *
     * @param clientName Client Name.
     */
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
