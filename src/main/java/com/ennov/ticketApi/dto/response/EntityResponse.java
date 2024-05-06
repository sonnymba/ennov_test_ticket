package com.ennov.ticketApi.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString

public class EntityResponse {
    private int code;
    private String message;

    public EntityResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public EntityResponse(int code) {
        this.code = code;
        this.message = "Opération effectuée avec succès";
    }

    public EntityResponse(String message) {
        this.code = 200;
        this.message = message;
    }

    public EntityResponse() {
        this.code = 200;
        this.message = "Opération effectuée avec succès";
    }

}
