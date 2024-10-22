package oggy.bankingapp.banksystem.repo;


import jakarta.transaction.Transactional;
import oggy.bankingapp.banksystem.entity.Account;
import oggy.bankingapp.banksystem.entity.AccountID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<Account, AccountID> {
    Account findByAccountNumberAndPhoneNumber(String accountNumber, String phoneNumber);
    Account findByEmailIdAndPhoneNumber(String emailId, String phoneNumber);


    @Transactional
    @Modifying
    @Query("Delete From Account as a " +
            "Where a.accountNumber =:ac AND a.phoneNumber =:ph")
    void deleteByAccountNumberAndPhoneNumber(@Param("ac")String accountNumber, @Param("ph")String phoneNumber);


    @Modifying
    @Query("""
            Update Account as a \
            Set a.phoneNumber =:ph,a.age =:age, a.name =:na, a.emailId =:em, a.pin =:pin \
            Where a.accountNumber =:ac""")
    int updateAccount(@Param("ac")String accountNumber,
                      @Param("ph")String phoneNumber,
                      @Param("na")String name,
                      @Param("em")String emailId,
                      @Param("age")int age,
                      @Param("pin") String pin);


}
