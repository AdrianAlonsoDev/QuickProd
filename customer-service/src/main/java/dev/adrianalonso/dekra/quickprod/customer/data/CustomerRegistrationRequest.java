package dev.adrianalonso.dekra.quickprod.customer.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CustomerRegistrationRequest(
        @JsonProperty("firstName") String firstName,
        @JsonProperty("lastName") String lastName,
        String gender,
        @JsonProperty("email") String email,
        @JsonProperty("phone_Number") String phoneNumber,
        @JsonProperty("id_Type") String idType,
        @JsonProperty("id_Value") String idValue
) {
}
