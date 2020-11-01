package ca.sheridancollege.jainbh.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.jainbh.beans.Receipt;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

	public List<Receipt> findByUsername(String username);
}
