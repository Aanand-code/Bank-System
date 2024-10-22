package oggy.bankingapp.banksystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class AccountID implements Serializable {


    private String accountNumber = accountNumberRandom();

    private String phoneNumber;

    private String emailId;


    // To generate random bank account number

    public static String accountNumberRandom(){
        Random random = new Random();
        String start = "500";
        int firstNum = random.nextInt(9) + 1;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(start);
        stringBuilder.append(firstNum);
        for (int i = 0; i < 11; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }
}