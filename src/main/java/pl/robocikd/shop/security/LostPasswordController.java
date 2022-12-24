package pl.robocikd.shop.security;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.robocikd.shop.security.model.dto.ChangePasswordDto;
import pl.robocikd.shop.security.model.dto.EmailObject;
import pl.robocikd.shop.security.service.LostPasswordService;

@RestController
@RequiredArgsConstructor
public class LostPasswordController {

    private final LostPasswordService lostPasswordService;

    @PostMapping("/lostPassword")
    public void lostPassword(@RequestBody EmailObject emailObject) {
        lostPasswordService.sendLostPasswordLink(emailObject);
    }

    @PostMapping("/changePassword")
    public void changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        lostPasswordService.changePassword(changePasswordDto);
    }

}
