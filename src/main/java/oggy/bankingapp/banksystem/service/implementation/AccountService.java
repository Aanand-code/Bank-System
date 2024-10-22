package oggy.bankingapp.banksystem.service.implementation;


import jakarta.transaction.Transactional;
import oggy.bankingapp.banksystem.DTO.AccountDto;
import oggy.bankingapp.banksystem.entity.Account;
import oggy.bankingapp.banksystem.entity.Users1;
import oggy.bankingapp.banksystem.repo.AccountRepo;
import oggy.bankingapp.banksystem.repo.RoleRepo;
import oggy.bankingapp.banksystem.repo.UserRepo;
import oggy.bankingapp.banksystem.service.AccountServiceDefine;
import oggy.bankingapp.banksystem.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService implements AccountServiceDefine {

    private final AccountRepo accountRepo;

    private final UserRepo userRepo;

    private final RoleRepo roleRepo;

    private final PasswordEncoder passwordEncoder;


    @Autowired
    public AccountService(AccountRepo accountRepo, UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.accountRepo = accountRepo;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    //To save Account
    @Override
    public AccountDto createAccount(AccountDto accountdto) {
        Account account = AccountMapper.mapToAccount(accountdto);
        Account savedAccount = accountRepo.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> saveAccounts(List<AccountDto> accountDos) {
        List<Account> account = accountDos.stream().map(AccountMapper::mapToAccount).collect(Collectors.toList());
        List<Account> accounts = accountRepo.saveAll(account);
        return accounts.stream().map(AccountMapper::mapToAccountDto).toList();
    }

    //To get Account

    @Override
    public ResponseEntity<AccountDto> findByEmailIdAndPhoneNumber(String emailId,String phoneNumber) {
        try {
            Optional<Account> optionalAccount = Optional.ofNullable(accountRepo.findByEmailIdAndPhoneNumber(emailId, phoneNumber));
            return optionalAccount.map(value -> ResponseEntity.ok(AccountMapper.mapToAccountDto(value)))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            System.out.println("No such account");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<AccountDto> findByAccountNumberAndPhoneNumber(String accountNumber, String phoneNumber) {
        try {
            Optional<Account> optionalAccount = Optional.ofNullable(accountRepo.findByAccountNumberAndPhoneNumber(accountNumber, phoneNumber));
            return optionalAccount.map(value -> ResponseEntity.ok(AccountMapper.mapToAccountDto(value)))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            System.out.println("No such account");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<List<AccountDto>> getAllAccounts()
    {
        List<Account> accounts = accountRepo.findAll();
        if (accounts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            List<AccountDto> accountDto = accounts.stream()
                    .map(AccountMapper::mapToAccountDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(accountDto);
        }
    }

//    ----------------------------------------------------------------------------------------------------------
//    ----------------------------------------------------------------------------------------------------------

//    Update Methods

    @Override
    public ResponseEntity<AccountDto> deposit(String accountNumber, String phoneNumber, Map<String, Double> request) {
        Double amount = request.get("amount");
        Optional<Account> optionalAccount = Optional.ofNullable(accountRepo.findByAccountNumberAndPhoneNumber(accountNumber, phoneNumber));
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            double total = account.getBalance() + amount;
            account.setBalance(total);
            Account savedAccount = accountRepo.save(account);
            return ResponseEntity.ok(AccountMapper.mapToAccountDto(savedAccount));
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<AccountDto> withdrawn(String accountNumber, String phoneNumber, Map<String, Double> request)  {
        Double amount = request.get("amount");
        Optional<Account> optionalAccount = Optional.ofNullable(accountRepo.findByAccountNumberAndPhoneNumber(accountNumber, phoneNumber));

        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            if(account.getBalance() < amount) {
                throw new RuntimeException("Not enough balance");
            }
            double total = account.getBalance() - amount;
            account.setBalance(total);
            Account savedAccount = accountRepo.save(account);
            return ResponseEntity.ok(AccountMapper.mapToAccountDto(savedAccount));
        }else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateDetails(String accountNumber, String phoneNumber, AccountDto accountDto){

        try {
            Optional<Account> optionalAccount = Optional.ofNullable(accountRepo.findByAccountNumberAndPhoneNumber(accountNumber, phoneNumber));
            if (optionalAccount.isPresent()) {
                int updatedRows = accountRepo.updateAccount(
                        accountNumber,
                        accountDto.getPhoneNumber(),
                        accountDto.getName(),
                        accountDto.getEmailId(),
                        accountDto.getAge(),
                        accountDto.getPin()
                );
                if (updatedRows > 0) {
                    return ResponseEntity.ok("Account updated");
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update failed");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }

    }


//    -----------------------------------------------------------------------------------------------
//    -----------------------------------------------------------------------------------------------

//    To Delete any Account

    @Override
    @Transactional
    public ResponseEntity<String> deleteByAccountNumberAndPhoneNumber(String accountNumber, String phoneNumber) {
        try {
            Optional<Account> account = Optional.ofNullable(accountRepo.findByAccountNumberAndPhoneNumber(accountNumber, phoneNumber));
            if(account.isPresent()){
                accountRepo.deleteByAccountNumberAndPhoneNumber(accountNumber, phoneNumber);
                return ResponseEntity.ok("Account deleted successfully");
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deletion Failed....");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }

    }


    @Override
    @Transactional
    public ResponseEntity<String> createUser1(Users1 users1) {
        Optional<Users1> optUser = Optional.ofNullable(userRepo.findByEmailId(users1.getEmailId()));
        if(optUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Already existed");
        } else{
            users1.setEmailId(users1.getEmailId());
            users1.setPassword(passwordEncoder.encode(users1.getPassword()));
            users1.setAccount(users1.getAccount());
//            users1.setRoles(users1.getRoles());
            userRepo.save(users1);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created");
        }

    }
}
