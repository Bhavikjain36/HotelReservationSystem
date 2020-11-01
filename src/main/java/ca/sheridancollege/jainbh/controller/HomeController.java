package ca.sheridancollege.jainbh.controller;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.jainbh.beans.Hotel;
import ca.sheridancollege.jainbh.beans.Payment;
import ca.sheridancollege.jainbh.beans.Receipt;
import ca.sheridancollege.jainbh.beans.Reservation;
import ca.sheridancollege.jainbh.beans.Room;
import ca.sheridancollege.jainbh.beans.User;
import ca.sheridancollege.jainbh.repositories.HotelRepository;
import ca.sheridancollege.jainbh.repositories.PaymentRepository;
import ca.sheridancollege.jainbh.repositories.ReceiptRepository;
import ca.sheridancollege.jainbh.repositories.ReservationRepository;
import ca.sheridancollege.jainbh.repositories.RoleRepository;
import ca.sheridancollege.jainbh.repositories.RoomRepository;
import ca.sheridancollege.jainbh.repositories.UserRepository;
import lombok.AllArgsConstructor;
import se.walkercrou.places.GooglePlaces;
import se.walkercrou.places.Place;

@Controller
@AllArgsConstructor
public class HomeController {
	
	private HotelRepository hotelRepository;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private ReservationRepository reservationRepository;
	private RoomRepository roomRepository;
	private PaymentRepository paymentRepository;
	private ReceiptRepository receiptRepository;

	
	private String encodePassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/user")
	public String userIndex(Model model, Authentication authentication) {
		
		String name = authentication.getName();
		
		List<String> roles = new ArrayList<String>();
		
		for (GrantedAuthority ga: authentication.getAuthorities()) {
			roles.add(ga.getAuthority());
			}
		model.addAttribute("name", name);
		model.addAttribute("roles", roles);
		return "user/displayHotel";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/access-denied")
	public String accessDenied() {
		return "/error/access-denied";
	}
	
	@GetMapping("/register")
	public String goRegistration() {
		return "register";
	}
	
	@PostMapping("/register")
	public String doRegistration(Model model,@RequestParam String username, @RequestParam String password) {
	
		
		User user= new User(username, encodePassword(password), Byte.valueOf("1"));
		user.getRoles().add(roleRepository.findByRolename("ROLE_USER"));
		
		userRepository.save(user);
		model.addAttribute("userList",userRepository.findAll());
		return "redirect:/";
	}
	
	@GetMapping("/insertHotel/{username}")
	public String insertHotel(Model model, @RequestParam String search, Authentication authentication, @PathVariable String username) {

		String name = authentication.getName();
		GooglePlaces client = new GooglePlaces("AIzaSyBYECHZyoJCg0SVF0L67oS0xSki8hPGs7M");
		List<Place> places = client.getPlacesByQuery(search, GooglePlaces.MAXIMUM_RESULTS);
		
		Place hotelInfo = null;{
		for (Place place : places) {
		    if (place.getName().equals(search)) {
		    	hotelInfo = place;
		        break;
		    }
		}
		if(hotelInfo==null) {
			  hotelRepository.deleteByUsername(username);
		 }

		if (hotelInfo != null ) {

			 //Scope scope = (hotelInfo.getScope() == null || hotelInfo.getScope().toString().isEmpty()) ? null : Scope.valueOf(hotelInfo.getScope().toString());
			 Place hotelInfoDetails = hotelInfo.getDetails(); // sends a GET request for more details
			 System.out.println("Name: " + hotelInfoDetails.getName());
			 System.out.println("Phone: " + hotelInfoDetails.getPhoneNumber());
			 System.out.println("Website: " + hotelInfoDetails.getWebsite());
			 System.out.println("Address: " + hotelInfoDetails.getAddress());
			 System.out.println("Reviews: " + hotelInfoDetails.getReviews().size());

			 hotelRepository.deleteByUsername(username);
			 Hotel hotel = new Hotel();
			 hotel.setUsername(name);
			 hotel.setName(hotelInfoDetails.getName());
			 hotel.setPhone(hotelInfoDetails.getPhoneNumber());
			 hotel.setWebsite(hotelInfoDetails.getWebsite());
			 hotel.setAddress(hotelInfoDetails.getVicinity());
			 ;
			 hotel.setReview(String.valueOf(hotelInfoDetails.getReviews().size()));

			 hotelRepository.save(hotel);
			 model.addAttribute("hotelList", hotelRepository.findByUsername(name));
			 model.addAttribute("name", name);
		 }
		

	}
		return "user/displayHotel";
	}
	
	@GetMapping("/viewRooms/{id}")
	public String viewRooms(Authentication auth, Model model, @PathVariable Long id) {
		model.addAttribute("name", auth.getName());
		model.addAttribute("hotelList",hotelRepository.findById(id).get());
		return "user/rooms";
	}
	
	@GetMapping("/search")
	public String search(Model model, Authentication auth) {
		model.addAttribute("name", auth.getName());
	
		return "user/displayHotel";
	}
	
	@PostMapping("/insertRoomDetails/{id}")
	public String insertRoomDetails(Authentication auth, Model model,@PathVariable Long id,
			@RequestParam String Roomtype, @RequestParam String bed, @RequestParam Double cost) {
		
		Room room = new Room();
		room.setRoomtype(Roomtype);
		room.setBed(bed);
		room.setCost(cost);
		room.setUsername(auth.getName());
		roomRepository.save(room);
		System.out.println(Roomtype + "" + bed + "" +cost);
		model.addAttribute("hotelDetails",hotelRepository.findById(id).get());
		return "user/userDetails";
	}
	
	@PostMapping("/insertReservation/{id}")
	public String insertReservation(Authentication auth, Model model, @PathVariable Long id,
			@RequestParam String name, @RequestParam String contactno, 
			@RequestParam String checkin, @RequestParam String checkout, @RequestParam String checkintime) {
		
		LocalDate checkindate = LocalDate.parse(checkin);
		LocalDate checkoutdate = LocalDate.parse(checkout);
		Reservation reserve = new Reservation();
		reserve.setName(name);
		reserve.setUsername(auth.getName());
		reserve.setContactno(contactno);
		reserve.setCheckin(checkindate);
		reserve.setCheckout(checkoutdate);
		reserve.setCheckintime(checkintime);
		Period period = Period.between(checkindate, checkoutdate);
		 int diff = period.getDays();
	     reserve.setNights(diff);
		reservationRepository.save(reserve);
	   
		double tax = 0.13;
		model.addAttribute("tax",tax);
		model.addAttribute("nights",diff);
		model.addAttribute("name", auth.getName());
		model.addAttribute("hotelDetails",hotelRepository.findById(id).get());
		model.addAttribute("reservationDetails",reservationRepository.findById(id).get());
		model.addAttribute("roomDetails",roomRepository.findById(id).get());
		return "user/payment";
	}
	
	@PostMapping("/insertPaymentDetails/{id}")
	public String insertPaymentDetails(Authentication auth, Model model,@PathVariable Long id,@RequestParam String cardtype, @RequestParam String cardholder,
			@RequestParam String cardnumber, @RequestParam String cvv, @RequestParam String month, @RequestParam String year) {
		Payment payment = new Payment();
		payment.setCardtype(cardtype);
		payment.setCardholder(cardholder);
		payment.setCardnumber(cardnumber);
		payment.setUsername(auth.getName());
		payment.setCvv(cvv);
		payment.setMonth(month);
		payment.setYear(year);
		paymentRepository.save(payment);
		model.addAttribute("name", auth.getName());
		model.addAttribute("hotelDetails",hotelRepository.findById(id).get());
		model.addAttribute("reservationDetails",reservationRepository.findById(id).get());
		model.addAttribute("roomDetails",roomRepository.findById(id).get());
		model.addAttribute("paymentDetails",paymentRepository.findById(id).get());
		
		String hotelname =  hotelRepository.findById(id).get().getName();
		String reservedfor = reservationRepository.findById(id).get().getName();
		LocalDate checkout = reservationRepository.findById(id).get().getCheckout();
		Integer nights = reservationRepository.findById(id).get().getNights();
		LocalDate checkin = reservationRepository.findById(id).get().getCheckin();
	    String roomtype = roomRepository.findById(id).get().getRoomtype();
		Double cost = roomRepository.findById(id).get().getCost();
		Double totalamount = nights * cost;
		
		
		Receipt receipt = new Receipt();
		receipt.setUsername(auth.getName());
		receipt.setReservedfor(reservedfor);
		receipt.setHotelname(hotelname);
		receipt.setRoomtype(roomtype);
		receipt.setCheckin(checkin);
		receipt.setCheckout(checkout);
		receipt.setCost(cost);
		receipt.setNights(nights);
		receipt.setTotalamount(totalamount);
		receipt.setCardholder(cardholder);
		receipt.setCardnumber(cardnumber);
		receiptRepository.save(receipt);
		return "user/bookingSummary";
	}
	
	@GetMapping("/viewBooking")
	public String viewBooking(Model model, Authentication auth) {
		model.addAttribute("name",auth.getName());
		model.addAttribute("viewBooking",receiptRepository.findByUsername(auth.getName()));
		return "user/mybooking";
	}
	
}
