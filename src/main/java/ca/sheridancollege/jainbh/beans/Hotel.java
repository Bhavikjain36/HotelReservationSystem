package ca.sheridancollege.jainbh.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

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
public class Hotel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NonNull
	private String name;
	@NonNull
	private String phone;
	@NonNull
	private String username;
	@NonNull
	private String website;
	@NonNull
	private String address;
	@NonNull
	private String review;	
	
	@ManyToMany(cascade=CascadeType.ALL, mappedBy="hotel")
	private List<User> users  = new ArrayList<User>();
}