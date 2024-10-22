package oggy.bankingapp.banksystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oggy.bankingapp.banksystem.entity.AccountID;
import oggy.bankingapp.banksystem.entity.Users1;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private String accountNumber = AccountID.accountNumberRandom();
    private String phoneNumber;
    private String emailId;
    private String name;
    private int age;
    private double balance;
    private String pin;
}
