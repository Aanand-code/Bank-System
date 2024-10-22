package oggy.bankingapp.banksystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "Customers_data")
@IdClass(AccountID.class)
public class Account {


    @Id
    @Column(name = "Account_number")
    private String accountNumber = AccountID.accountNumberRandom();

    @Id
    @Column(name = "Phone_number")
    private String phoneNumber;

    @Id
    @Column(name = "Email_id")
    private String  emailId;

    @Column(name = "Account_holder_name")
    private String name;

    @Column(name = "Age")
    private int age;

    @Column (name = "Balance")
    private double balance;

    @Column (name = "pin")
    @Pattern(regexp = "\\d{4}", message = "PIN must be a four-digit number")
    private String pin;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "account")
    private Users1 users1;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Role> roles;

    public Account(String accountNumber, String phoneNumber, String emailId, String name, int age, double balance, String pin) {
        this.accountNumber = accountNumber;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
        this.name = name;
        this.age = age;
        this.balance = balance;
        this.pin = pin;

    }
}
