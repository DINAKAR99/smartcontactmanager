package cgg.smartcontactmanager.smartcontactmanager.dao;

import cgg.smartcontactmanager.smartcontactmanager.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByName(String username);

}
