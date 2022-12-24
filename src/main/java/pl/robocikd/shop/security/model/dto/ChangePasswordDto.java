package pl.robocikd.shop.security.model.dto;

import lombok.Getter;

@Getter
public class ChangePasswordDto {
    private String password;
    private String repeatPassword;
    private String hash;
}
