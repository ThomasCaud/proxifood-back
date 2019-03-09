package api.controllers;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.hash.Hashing;

import api.entities.Location;
import api.entities.User;
import api.repositories.ConversationRepository;
import api.repositories.LocationRepository;
import api.repositories.UserRepository;
import api.transferts.ErrorTransfert;
import transferts.LocationTransfert;
import transferts.UserTransfert;

@RestController
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private ConversationRepository conversationRepository;

	@RequestMapping(method = RequestMethod.POST, value = "/users")
	public @ResponseBody ResponseEntity<Object> create(@RequestBody UserTransfert user) {
		if(user.getLogin() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorTransfert("Field 'login' is expected"));
		}
		if(user.getDateOfBirth() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorTransfert("Field 'dateOfBirth' is expected"));
		}
		if(user.getEmail() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorTransfert("Field 'email' is expected"));
		}
		if(user.getFirstName() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorTransfert("Field 'firstName' is expected"));
		}
		if(user.getLastName() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorTransfert("Field 'lastName' is expected"));
		}
		if(user.getPassword() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorTransfert("Field 'password' is expected"));
		}
		if (userRepository.findByLogin(user.getLogin()) != null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorTransfert("The login " + user.getLogin() + " is already used.", 1));
		}
		
		if (userRepository.findByEmail(user.getEmail()) != null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorTransfert("The email " + user.getEmail() + " is already used.", 2));
		}

		String hashedPassword = Hashing.sha256().hashString(user.getPassword(), StandardCharsets.UTF_8).toString();
		User n = new User(user.getLogin(), user.getEmail(), hashedPassword, user.getFirstName(), user.getLastName(), user.getDescription(), user.getDateOfBirth());
		n.setHomeAddress(locationRepository.save(n.getHomeAddress()));
		userRepository.save(n);
		n.setPassword("");
		return ResponseEntity.ok(n);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/users")
	public @ResponseBody ResponseEntity<Iterable<User>> getAll() {
		return ResponseEntity.ok(userRepository.findAll());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
	public @ResponseBody ResponseEntity<Optional<User>> get(@PathVariable(value = "id") long id) {
		return ResponseEntity.ok(userRepository.findById(id));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/users/{id}/offers")
	public @ResponseBody ResponseEntity<Object> getOffers(@PathVariable(value = "id") long id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found.");
		}
		return ResponseEntity.ok(user.get().getOffers());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/users/{id}/applications")
	public @ResponseBody ResponseEntity<Object> getApplications(@PathVariable(value = "id") long id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorTransfert("User not found."));
		}
		return ResponseEntity.ok(user.get().getApplications());
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/users/{id}/conversations")
	public @ResponseBody ResponseEntity<Object> getConversations(@PathVariable(value = "id") long id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorTransfert("User not found."));
		}
		return ResponseEntity.ok(conversationRepository.findByUserAOrUserB(user.get(), user.get()));
	}

	
	@RequestMapping(method = RequestMethod.GET, value = "/users/{id}/receivedComments")
	public @ResponseBody ResponseEntity<Object> getReceivedComments(@PathVariable(value = "id") long id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorTransfert("User not found."));
		}
		return ResponseEntity.ok(user.get().getReceivedComments());
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/users/{id}/postedComments")
	public @ResponseBody ResponseEntity<Object> getPostedComments(@PathVariable(value = "id") long id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorTransfert("User not found."));
		}
		return ResponseEntity.ok(user.get().getPostedComments());
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/users/{id}")
	public @ResponseBody ResponseEntity<Object> update(@PathVariable(value = "id") long id, @RequestBody UserTransfert userTrans) {
		Optional<User> optUserBean = userRepository.findById(id);
		
		if(!optUserBean.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorTransfert("User not found."));
		}
		
		User userBean = optUserBean.get();
		
		if(userTrans.getEmail() != null) {
			if (userRepository.findByEmail(userTrans.getEmail()) != null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ErrorTransfert("The email " + userTrans.getEmail() + " is already used.", 2));
			}
			userBean.setEmail(userTrans.getEmail());
		}
		if(userTrans.getFirstName() != null) {
			userBean.setFirstName(userTrans.getFirstName());
		}
		if(userTrans.getLastName() != null) {
			userBean.setLastName(userTrans.getLastName());
		}
		if(userTrans.getPassword() != null) {
			String hashedPassword = Hashing.sha256().hashString(userTrans.getPassword(), StandardCharsets.UTF_8).toString();
			userBean.setPassword(hashedPassword);
		}
		if(userTrans.getDescription() != null) {
			userBean.setDescription(userTrans.getDescription());
		}
		if(userTrans.getHomeAddressId() != null) {
			Optional<Location> optLocation = locationRepository.findById(userTrans.getHomeAddressId());
			if(optLocation.isPresent()) {
				userBean.setHomeAddress(optLocation.get());
			}
		}

		userRepository.save(userBean);
		return ResponseEntity.ok(userBean);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/users/{id}")
	public @ResponseBody ResponseEntity<Optional<User>> delete(@PathVariable(value = "id") long id) {
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent()) {
			userRepository.delete(user.get());
		}
		return ResponseEntity.ok(user);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/users/{id}/address")
	public @ResponseBody ResponseEntity<Object> updateHomeAddress(@PathVariable(value = "id") long id, @RequestBody LocationTransfert locationTrans) {
		Optional<User> optUser = userRepository.findById(id);
		if(!optUser.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorTransfert("Location with id=" + id + " not found."));
		}
		
		if(locationTrans.getCity() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorTransfert("Field 'city' is expected"));
		}
		if(locationTrans.getCountry() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorTransfert("Field 'country' is expected"));
		}
		if(locationTrans.getLatitude() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorTransfert("Field 'latitude' is expected"));
		}
		if(locationTrans.getLongitude() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorTransfert("Field 'longitude' is expected"));
		}
		if(locationTrans.getNumber() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorTransfert("Field 'number' is expected"));
		}
		if(locationTrans.getStreet() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorTransfert("Field 'street' is expected"));
		}
		if(locationTrans.getZipcode() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorTransfert("Field 'zipcode' is expected"));
		}
		if(locationTrans.getFormattedAddress() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorTransfert("Field 'formattedAddress' is expected"));
		}
		
		Location locationBean = optUser.get().getHomeAddress();
		locationBean.setCity(locationTrans.getCity());
		locationBean.setComplementary(locationTrans.getComplementary());
		locationBean.setCountry(locationTrans.getCountry());
		locationBean.setFormattedAddress(locationTrans.getFormattedAddress());
		locationBean.setLatitude(locationTrans.getLatitude());
		locationBean.setLongitude(locationTrans.getLongitude());
		locationBean.setNumber(locationTrans.getNumber());
		locationBean.setStreet(locationTrans.getStreet());
		locationBean.setZipcode(locationTrans.getZipcode());
		
		locationRepository.save(locationBean);
		return ResponseEntity.ok(locationBean);
	}
}
