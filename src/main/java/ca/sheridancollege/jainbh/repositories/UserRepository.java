package ca.sheridancollege.jainbh.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.jainbh.beans.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public User findByUsername(String username);
}
