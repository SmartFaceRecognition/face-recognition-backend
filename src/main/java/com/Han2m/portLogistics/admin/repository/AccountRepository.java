package com.Han2m.portLogistics.admin.repository;

import com.Han2m.portLogistics.admin.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountId(String accountId);

    Boolean existsAccountByAccountId(String accountId);
}
