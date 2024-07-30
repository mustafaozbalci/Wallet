package demo.Wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseModel {
    private final Integer status;
    private final String message;
    private final transient Object data;
}
