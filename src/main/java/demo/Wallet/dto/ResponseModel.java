package demo.Wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseModel<T> {
    private final Integer status;
    private final String message;
    private final T data;
}
