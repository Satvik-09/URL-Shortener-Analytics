package com.satvik.url_shortner.repository;

import com.satvik.url_shortner.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository  extends JpaRepository<UserEntity,String> {
    Optional<UserEntity> findbyUsername(String username);

}
