package com.sportingCenterBackEnd.repo;

import com.sportingCenterBackEnd.model.ConfirmationToken;
import com.sportingCenterBackEnd.model.User;
import com.sportingCenterBackEnd.model.UserCode;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserCodeRepository extends CrudRepository<UserCode, String> {

    @Query("select uc from UserCode uc where uc.code = :code")
    UserCode findByCode(String code);
}
