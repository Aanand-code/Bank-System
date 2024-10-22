package oggy.bankingapp.banksystem.service;

import oggy.bankingapp.banksystem.DTO.AccountDto;
import oggy.bankingapp.banksystem.entity.Users1;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface AccountServiceDefine {

//    ---------------------------------------------------------------------------------------------------------
//    --------------------------------------------------------------------------------------------------------

    AccountDto createAccount(AccountDto account);

    List<AccountDto> saveAccounts(List<AccountDto> accountDos);

//    ----------------------------------------------------------------------------------------------------------
//    ----------------------------------------------------------------------------------------------------------

    ResponseEntity<AccountDto> findByEmailIdAndPhoneNumber(String emailId, String phoneNumber);

    ResponseEntity<AccountDto> findByAccountNumberAndPhoneNumber(String accountNumber, String phoneNumber);

    ResponseEntity<List<AccountDto>> getAllAccounts();

//    ----------------------------------------------------------------------------------------------------------
//    ----------------------------------------------------------------------------------------------------------

    ResponseEntity<AccountDto> deposit(String accountNumber, String phoneNumber, Map<String, Double> request);

    ResponseEntity<AccountDto> withdrawn(String accountNumber, String phoneNumber, Map<String, Double> request);

//    ------------------------------------------------------------------------------------------------------------
//    ------------------------------------------------------------------------------------------------------------

    ResponseEntity<String> updateDetails(String accountNumber, String phoneNumber, AccountDto accountDto);

    ResponseEntity<String> deleteByAccountNumberAndPhoneNumber(String accountNumber, String phoneNumber);

    ResponseEntity<String> createUser1(Users1 users1);
}
