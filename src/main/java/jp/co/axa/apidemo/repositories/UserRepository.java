package jp.co.axa.apidemo.repositories;

import java.util.Optional;

import jp.co.axa.apidemo.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepository
 * Repository class for entity UserInfo.
 */
@Repository
public interface UserRepository extends JpaRepository<UserInfo,Long> {
    Optional<UserInfo> findUserByUserName(String userName);
}