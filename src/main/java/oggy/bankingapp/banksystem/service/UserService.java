package oggy.bankingapp.banksystem.service;


import ch.qos.logback.classic.encoder.JsonEncoder;
import oggy.bankingapp.banksystem.entity.Users1;
import oggy.bankingapp.banksystem.repo.AccountRepo;
import oggy.bankingapp.banksystem.repo.RoleRepo;
import oggy.bankingapp.banksystem.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Service
public class UserService {


}
