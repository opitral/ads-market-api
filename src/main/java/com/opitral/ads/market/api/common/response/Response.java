package com.opitral.ads.market.api.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {
    private T result;
    private ResponseError error;

    public static Response<?> of(ResponseError error) {
        Response<?> response = new Response<>();
        response.setError(error);
        return response;
    }

    public static <T> Response<T> of(T result) {
        Response<T> response = new Response<>();
        response.setResult(result);
        return response;
    }
}
