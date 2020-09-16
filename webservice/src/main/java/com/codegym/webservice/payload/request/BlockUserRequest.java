package com.codegym.webservice.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BlockUserRequest {
    @NotBlank(message = "Lý do không được để trống.")
    private String reason;

    public BlockUserRequest() {
    }

    public BlockUserRequest(@NotBlank(message = "Lý do không được để trống.") String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
