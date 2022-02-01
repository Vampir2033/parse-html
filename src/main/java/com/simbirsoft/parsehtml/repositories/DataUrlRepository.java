package com.simbirsoft.parsehtml.repositories;

import com.simbirsoft.parsehtml.entities.DateUrl;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataUrlRepository extends CrudRepository<DateUrl, Long> {

}
