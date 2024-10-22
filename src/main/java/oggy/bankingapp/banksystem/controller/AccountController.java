package oggy.bankingapp.banksystem.controller;

import oggy.bankingapp.banksystem.DTO.AccountDto;

import oggy.bankingapp.banksystem.security.jwt.JwtUtils;
import oggy.bankingapp.banksystem.entity.LoginRequest;
import oggy.bankingapp.banksystem.entity.LoginResponse;
import oggy.bankingapp.banksystem.service.implementation.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping
public class AccountController {


    private final AccountService accountService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AccountController(AccountService accountService, JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.accountService = accountService;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }
//   -----------------------------------------------------------------------------------------------------------
    //Post API or Add Account REST API


    @PostMapping("/signIN")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()));
        } catch (AuthenticationException e) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", e.getMessage());
            map.put("status", HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        LoginResponse loginResponse = new LoginResponse(userDetails.getUsername(), jwtToken, roles);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/newAccountAddByManager")
    public ResponseEntity<AccountDto> newAccountAddByManager(@RequestBody AccountDto accountDto)
    {
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }


    @PostMapping("/newAccountsAddedByManager")
    public ResponseEntity<List<AccountDto>> accountsAddedByManager(@RequestBody List<AccountDto> accountDto)
    {
        return new ResponseEntity<>(accountService.saveAccounts(accountDto), HttpStatus.CREATED);
    }


//   -------------------------------------------------------------------------------------------------------
//   -------------------------------------------------------------------------------------------------------

//   Get API or Get Account REST API

//   Information using email and phone number
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/accountInfo/{em}/{ph}")
    public ResponseEntity<AccountDto> getAccountByEmailIdAndPhoneNumber(@PathVariable("em") String emailId,
                                                                        @PathVariable("ph") String phoneNumber)
    {
        return accountService.findByEmailIdAndPhoneNumber(emailId,phoneNumber);
    }

//    Information using account number and phone number
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/account/{ac}/{ph}")
    public ResponseEntity<AccountDto> getAccountByAccountNumberAndPhoneNumber(@PathVariable("ac") String accountNumber,
                                                                              @PathVariable("ph") String phoneNumber)
    {
        return accountService.findByAccountNumberAndPhoneNumber(accountNumber,phoneNumber);
    }

//    Information of all accounts
//    http://localhost:8080/bankApi/accounts
    @GetMapping("/accounts")
    public ResponseEntity<List<AccountDto>> getAccounts()
    {
        return accountService.getAllAccounts();
    }

//    --------------------------------------------------------------------------------------------------------
//    --------------------------------------------------------------------------------------------------------

    //Put REST API
    @PutMapping("/{ac}/{ph}/deposit")
    public ResponseEntity<AccountDto> depositAmount(@PathVariable("ac") String accountNumber,
                                                    @PathVariable("ph") String phoneNumber,
                                                    @RequestBody Map<String, Double> request)
    {
        return accountService.deposit(accountNumber, phoneNumber, request);
    }

    @PutMapping("/{accountNumber}/{phoneNumber}/withdrawn")
    public ResponseEntity<AccountDto> withdrawnAmount(@PathVariable String accountNumber,
                                                      @PathVariable String phoneNumber,
                                                      @RequestBody Map<String, Double> request)
    {
        return accountService.withdrawn(accountNumber, phoneNumber, request);
    }

    @PutMapping("/updateDetails/{ac}/{ph}")
    public ResponseEntity<String> updateDetails(@PathVariable("ac") String accountNumber,
                                                @PathVariable("ph") String phoneNumber,
                                                @RequestBody AccountDto accountDto)
    {
        return accountService.updateDetails(accountNumber, phoneNumber, accountDto);
    }



//    Delete API
    @DeleteMapping("/delete/{ac}/{pn}")
    public ResponseEntity<String> deleteAccount(@PathVariable("ac") String accountNumber,
                                                @PathVariable("pn") String phoneNumber)
    {
        return accountService.deleteByAccountNumberAndPhoneNumber(accountNumber, phoneNumber);
    }

}