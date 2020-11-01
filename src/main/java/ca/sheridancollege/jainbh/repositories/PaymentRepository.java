package ca.sheridancollege.jainbh.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.jainbh.beans.Payment;


public interface PaymentRepository extends JpaRepository<Payment, Long> {

	public List<Payment> findByUsername(String username);
}
