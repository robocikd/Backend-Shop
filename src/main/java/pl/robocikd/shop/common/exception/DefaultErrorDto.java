package pl.robocikd.shop.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
@Getter
@AllArgsConstructor
public class DefaultErrorDto {
    private Date timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
