package ca.sheridancollege.jainbh.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NonNull
	private String username;
	@NonNull
	private String cardtype;
	@NonNull
	private String cardholder;
	@NonNull
	private String cardnumber;
	@NonNull
	private String cvv;
	@NonNull
	private String month;
	@NonNull
	private String year;
}
