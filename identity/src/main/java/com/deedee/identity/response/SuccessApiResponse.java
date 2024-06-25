package com.deedee.identity.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuccessApiResponse<T> {
    private Meta meta;
    private T data;

    public static <T> SuccessApiResponse<T> buildResponse(T data) {
        SuccessApiResponse<T> response = new SuccessApiResponse<>();
        SuccessApiResponse.Meta meta = new SuccessApiResponse.Meta();
        meta.setTraceId(UUID.randomUUID().toString());
        meta.setSuccess(true);
        response.setMeta(meta);
        response.setData(data);
        return response;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Meta {
        private String traceId;
        private boolean success;
    }
}
