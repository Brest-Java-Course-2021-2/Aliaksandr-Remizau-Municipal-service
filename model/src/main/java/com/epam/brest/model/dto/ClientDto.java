package com.epam.brest.model.dto;


/**
 * POJO Client for model.
 */
public class ClientDto {
    /**
     * Client Id.
     */
    private Integer clientId;
    /**
     * Client Name.
     */
    private String clientName;
    /**
     * Number of client's repairs.
     */
    private Integer numberOfRepairs;

    /**
     * Constructor without arguments.
     */

    public ClientDto(){
    }
    /**
     * Constructor with client name.
     *
     *
     * @param clientName client name
     *
     */

    public ClientDto(String clientName) {
        this.clientName = clientName;
    }

    /**
     * Constructor with client Id,client name,number of client's repairs.
     *
     * @param clientId client Id
     *
     * @param clientName client name
     *
     *  @param numberOfRepairs number of client's repairs
     */

    public ClientDto(Integer clientId, String clientName, Integer numberOfRepairs) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.numberOfRepairs = numberOfRepairs;
    }
    /**
     * Returns <code>Integer</code> representation of this clientId.
     *
     * @return departmentId Department Id.
     */

    public Integer getClientId() {
        return clientId;
    }
    /**
     * Sets the client's identifier.
     *
     * @param clientId Client Id.
     */

    public void setClientId(Integer clientId) {
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
    /**
     * Returns <code>Integer</code> representation number of repairs
     * for the Client.
     *
     * @return numberOfRepairs.
     */

    public Integer getNumberOfRepairs() {
        return numberOfRepairs;
    }/**
     * Sets the client's  number of repairs.
     *
     * @param numberOfRepairs Number of repairs.
     */

    public void setNumberOfRepairs(Integer numberOfRepairs) {
        this.numberOfRepairs = numberOfRepairs;
    }
    /**
     * {@inheritDoc}
     */

    @Override
    public String toString() {
        return "ClientDto{" +
                "clientId=" + clientId +
                ", clientName='" + clientName + '\'' +
                ", numberOfRepairs=" + numberOfRepairs +
                '}';
    }
}
