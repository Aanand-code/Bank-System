package oggy.bankingapp.banksystem.repo;

import oggy.bankingapp.banksystem.entity.Users1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users1, String> {

    Users1 findByEmailId(String emailId);

}
