package oggy.bankingapp.banksystem.security.jwt;

import oggy.bankingapp.banksystem.entity.Users1;
import oggy.bankingapp.banksystem.service.UserService;
import oggy.bankingapp.banksystem.service.implementation.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;
import java.util.Optional;

@Controller
@RequestMapping("/api")
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final AccountService accountService;
    DataSource dataSource;
    private final UserService userService;

    @Autowired
    public UserController(PasswordEncoder passwordEncoder, AccountService accountService, DataSource dataSource, UserService userService) {
        this.accountService = accountService;
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users")
    public String createUser(@RequestParam String username, @RequestParam String password,@RequestParam String role) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        if(userDetailsManager.userExists(username)) {
            return "User already exists";
        }

        UserDetails user = User.withUsername(username)
                .password(passwordEncoder.encode(password))
                .roles(role)
                .build();
        userDetailsManager.createUser(user);
        return "User created";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/userss")
    public ResponseEntity<String> addUser(@RequestBody Users1 users1) {
        return accountService.createUser1(users1);
    }


}
