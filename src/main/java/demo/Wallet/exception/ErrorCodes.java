package demo.Wallet.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCodes {

    WALLET_NOT_FOUND(10), USER_NOT_FOUND(11), INVALID_USERNAME_OR_PASSWORD(13), INSUFFICIENT_BALANCE(14), PAYEE_NOT_FOUND(15);
    private final int code;

}
