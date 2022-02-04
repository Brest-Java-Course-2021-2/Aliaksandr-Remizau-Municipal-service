package com.epam.brest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.NonNull;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;
/**
 * POJO Client for model.
 */
@Schema(name="Client", description = "Client")
public class Client {
    /**
     * Client Id.
     */
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer clientId;
    /**
     * Client Name.
     */
    @NonNull
    @Schema(name = "Name", description = "name of the client")
    @NotEmpty(message = "Please provide client name.")
    @Size(min = 1, max = 250, message = "Client name must be between {min} and {max} characters.")
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
     * Set in builder the client's identifier.
     *
     * @param clientId Client Id.
     * @return Client .
     */
    public Client setClientId(Integer clientId) {
        this.clientId = clientId;
        return this;
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
     * Sets in builder the client's name .
     *
     * @param clientName Client Name.
     * @return  Client .
     */
   public Client setClientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", clientName='" + clientName + '\'' +
                '}';
    }
}
