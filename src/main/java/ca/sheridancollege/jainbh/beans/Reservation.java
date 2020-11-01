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
public class Reservation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NonNull
	private String name;
	@NonNull
	private String username;
	@NonNull
	private String contactno;
	@NonNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate checkin;
	@NonNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate checkout;
	@NonNull
	private String checkintime;
	@NonNull
	private Integer nights;
}