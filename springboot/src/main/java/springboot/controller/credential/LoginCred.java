package springboot.controller.credential;

import lombok.Data;

@Data
public class LoginCred {
    private Integer id;
    private String username;
    private String phone;
    private String email;
    private String token;
}
