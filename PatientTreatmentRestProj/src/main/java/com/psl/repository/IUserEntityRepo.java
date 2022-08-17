package com.psl.repository;

import org.springframework.data.repository.CrudRepository;
import com.psl.entity.UserEntity;

public interface IUserEntityRepo extends CrudRepository<UserEntity, String>{
}//interface
