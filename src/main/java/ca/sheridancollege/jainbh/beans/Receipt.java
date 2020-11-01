package ca.sheridancollege.jainbh.beans;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

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
public class Receipt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NonNull
	private String username;
	@NonNull
	private String reservedfor;
	@NonNull
	private String hotelname;
	@NonNull
	private String roomtype;
	@NonNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate checkin;
	@NonNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate checkout;
	@NonNull
	private Double cost;
	@NonNull
	private Integer nights;
	@NonNull
	private Double totalamount;
	@NonNull
	private String cardholder;
	@NonNull
	private String cardnumber;
}
