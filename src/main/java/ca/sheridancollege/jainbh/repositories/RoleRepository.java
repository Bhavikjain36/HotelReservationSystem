package ca.sheridancollege.jainbh.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.jainbh.beans.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	public Role findByRolename(String rolename);
	
}
