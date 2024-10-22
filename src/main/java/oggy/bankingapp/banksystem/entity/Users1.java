package oggy.bankingapp.banksystem.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users1")
public class Users1 {

    @Id
    @Column(name = "email_id")
    private String  emailId;

    @Column(name = "password")
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private Account account;

}
