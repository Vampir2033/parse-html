package com.simbirsoft.parsehtml.repositories;

import com.simbirsoft.parsehtml.entities.UserRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRequestRepository extends CrudRepository<UserRequest, Long> {

}
