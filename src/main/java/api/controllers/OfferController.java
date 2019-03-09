package api.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import api.entities.Location;
import api.entities.Offer;
import api.entities.User;
import api.repositories.ApplicationRepository;
import api.repositories.OfferRepository;
import api.repositories.UserRepository;
import api.transferts.ErrorTransfert;
import api.transferts.OfferTransfert;

@RestController
public class OfferController {
	@Autowired
	private OfferRepository offerRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ApplicationRepository applicationRepository;

	@RequestMapping(method = RequestMethod.POST, value = "/offers", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> create(@RequestBody OfferTransfert offer) {

		Offer newOffer = new Offer(offer);
		Optional<User> creator = userRepository.findById(offer.getCreatorId());
		if (!creator.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorTransfert("creatorId does not match to existing user."));
		}
		newOffer.setCreator(creator.get());

		offerRepository.save(newOffer);
		return ResponseEntity.ok(newOffer);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/offers")
	public @ResponseBody ResponseEntity<Iterable<Offer>> getAll(
			@RequestHeader(value = "longitudeMin") Optional<Float> longitudeMin,
			@RequestHeader(value = "longitudeMax") Optional<Float> longitudeMax,
			@RequestHeader(value = "latitudeMin") Optional<Float> latitudeMin,
			@RequestHeader(value = "latitudeMax") Optional<Float> latitudeMax) {
		if (!longitudeMin.isPresent() || !longitudeMax.isPresent() || !latitudeMin.isPresent()
				|| !latitudeMax.isPresent()) {
			 return ResponseEntity.ok(offerRepository.findAll());
		}

		float longMin = longitudeMin.get();
		float longMax = longitudeMax.get();
		float latMin = latitudeMin.get();
		float latMax = latitudeMax.get();
		Iterable<Offer> allOffers = offerRepository.findAll();
		ArrayList<Offer> nearOffers = new ArrayList<Offer>();

		Iterator<Offer> iterator = allOffers.iterator();
		while (iterator.hasNext()) {
			Offer offer = iterator.next();
			Location homeAddress = offer.getCreator().getHomeAddress();
			if (homeAddress != null && homeAddress.getLongitude() != null && homeAddress.getLatitude() != null) {
				double longitude = homeAddress.getLongitude();
				double latitude = homeAddress.getLatitude();

				if (longitude > longMin && longitude < longMax && latitude > latMin && latitude < latMax) {
					nearOffers.add(offer);
				}
			}
		}

		return ResponseEntity.ok(nearOffers);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/offers/{id}")
	public @ResponseBody ResponseEntity<Object> get(@PathVariable(value = "id") long id) {
		Optional<Offer> existingOffer = offerRepository.findById(id);

		if (existingOffer.isPresent()) {
			return ResponseEntity.ok(existingOffer);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorTransfert("Offer not found."));
		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/offers/{id}")
	public @ResponseBody ResponseEntity<Object> put(@PathVariable(value = "id") long id,
			@RequestBody OfferTransfert updatedOffer) {
		Optional<Offer> existingOffer = offerRepository.findById(id);

		if (!existingOffer.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorTransfert("Offer not found."));
		}

		Offer offer = existingOffer.get();
		if (updatedOffer.isAllowHelpCooking() != offer.isAllowHelpCooking()) {
			offer.setAllowHelpCooking(updatedOffer.isAllowHelpCooking());
		}

		if (updatedOffer.getDate() != null) {
			offer.setDate(updatedOffer.getDate());
		}

		if (updatedOffer.getEndOfInscription() != null) {
			offer.setEndOfInscription(updatedOffer.getEndOfInscription());
		}

		if (updatedOffer.getMeal().length() > 0) {
			offer.setMeal(updatedOffer.getMeal());
		}

		if (updatedOffer.getDescription().length() > 0) {
			offer.setDescription(updatedOffer.getDescription());
		}

		if (updatedOffer.getNbPlaces() > 0) {
			offer.setNbPlaces(updatedOffer.getNbPlaces());
		}

		if (updatedOffer.getPrice() >= 0) {
			offer.setPrice(updatedOffer.getPrice());
		}

		offerRepository.save(offer);
		return ResponseEntity.ok(existingOffer);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/offers/{id}")
	public @ResponseBody ResponseEntity<Object> delete(@PathVariable(value = "id") long id) {
		Optional<Offer> existingOffer = offerRepository.findById(id);

		if (existingOffer.isPresent()) {
			offerRepository.delete(existingOffer.get());
			return ResponseEntity.ok(existingOffer);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorTransfert("Offer not found."));
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/offers/{id}/applications")
	public @ResponseBody ResponseEntity<Object> getApplications(@PathVariable(value = "id") long id) {
		Optional<Offer> existingOffer = offerRepository.findById(id);

		if (existingOffer.isPresent()) {
			return ResponseEntity.ok(applicationRepository.findByOffer(existingOffer.get()));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorTransfert("Offer not found."));
		}
	}
}
