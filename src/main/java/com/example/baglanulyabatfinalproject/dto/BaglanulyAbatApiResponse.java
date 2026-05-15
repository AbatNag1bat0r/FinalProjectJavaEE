package com.example.baglanulyabatfinalproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaglanulyAbatApiResponse<T> {
    private Boolean success;
    private String message;
    private T data;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    public static <T> BaglanulyAbatApiResponse<T> success(T data){
        return BaglanulyAbatApiResponse.<T>builder()
                .success(true)
                .message("Operation successful")
                .data(data)
                .build();
    }
    public static <T> BaglanulyAbatApiResponse<T> success(String message, T data){
        return BaglanulyAbatApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }
    public static <T> BaglanulyAbatApiResponse<T> error(String message){
        return BaglanulyAbatApiResponse.<T>builder()
                .success(false)
                .message(message)
                .build();
    }
}
