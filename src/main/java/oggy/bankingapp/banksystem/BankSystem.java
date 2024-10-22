package oggy.bankingapp.banksystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.InvalidDataAccessApiUsageException;

@SpringBootApplication
public class BankSystem {

	public static void main(String[] args) throws Exception, InvalidDataAccessApiUsageException {

		var context = SpringApplication.run(BankSystem.class, args);

	}

}
