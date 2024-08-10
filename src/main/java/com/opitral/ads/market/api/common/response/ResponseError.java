package com.opitral.ads.market.api.common.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseError {
    private int code;
    private String message;
    private List<String> errors;

    public ResponseError(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
