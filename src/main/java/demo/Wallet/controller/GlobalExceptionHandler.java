package demo.Wallet.controller;

import demo.Wallet.dto.ResponseModel;
import demo.Wallet.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    //    @ExceptionHandler(WalletNotFoundException.class)
//    public ResponseEntity<String> walletNotFound(WalletNotFoundException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(InsufficientBalanceException.class)
//    public ResponseEntity<String> handleInsufficientBalanceException(InsufficientBalanceException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
//    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseModel> handleBadRequestException(BadRequestException ex) {
        ResponseModel responseModel = new ResponseModel(ex.getErrorCode().getCode(), ex.getMessage(), null);
        return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
    }


}