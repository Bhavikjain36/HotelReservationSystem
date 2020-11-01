package ca.sheridancollege.jainbh.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.jainbh.beans.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

	public List<Room> findByUsername(String username);
}
