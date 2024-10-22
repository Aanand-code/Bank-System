package oggy.bankingapp.banksystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String jwtToken;
    private String username;
    private List<String> roles;
}
