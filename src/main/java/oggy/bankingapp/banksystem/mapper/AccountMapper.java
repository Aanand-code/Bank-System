package oggy.bankingapp.banksystem.mapper;

import oggy.bankingapp.banksystem.DTO.AccountDto;
import oggy.bankingapp.banksystem.entity.Account;

public class AccountMapper {

    public static Account mapToAccount(AccountDto accountdto) {
        Account account = new Account(
                accountdto.getAccountNumber(),
                accountdto.getPhoneNumber(),
                accountdto.getEmailId(),
                accountdto.getName(),
                accountdto.getAge(),
                accountdto.getBalance(),
                accountdto.getPin()
        );
        return account;
    }
    public static AccountDto mapToAccountDto(Account account) {
        AccountDto accountdto = new AccountDto(
                account.getAccountNumber(),
                account.getPhoneNumber(),
                account.getEmailId(),
                account.getName(),
                account.getAge(),
                account.getBalance(),
                account.getPin()
        );
        return accountdto;
    }
//    public static Account mapToAccount1(AccountDto accountdto) {
//        Account account = new Account(
//                accountdto.getPhoneNumber(),
//                accountdto.getEmailId(),
//                accountdto.getName(),
//                accountdto.getName(),
//                accountdto.getAge()
//        );
//        return account;
//    }
}
