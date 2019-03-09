package api.controllers;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import api.entities.Application;
import api.entities.Application.EnumStatut;
import api.entities.Offer;
import api.entities.Token;
import api.entities.User;
import api.repositories.ApplicationRepository;
import api.repositories.OfferRepository;
import api.repositories.TokenRepository;
import api.repositories.UserRepository;
import api.transferts.ApplicationTransfert;
import api.transferts.ErrorTransfert;
import api.transferts.StatutTransfert;

@RestController
public class ApplicationController {
	@Autowired
	private ApplicationRepository applicationRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OfferRepository offerRepository;
	@Autowired
	private TokenRepository tokenRepository;

	@RequestMapping(method = RequestMethod.POST, value = "/applications")
	public @ResponseBody ResponseEntity<Object> create(@RequestBody ApplicationTransfert application) {
		// todo vérifier si ne dépasse pas le nb de places
		if (application.getApplicantId() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorTransfert("Field 'applicantId' is expected"));
		}
		if (application.getOfferId() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorTransfert("Field 'offerId' is expected"));
		}
		if (application.getNbPlaces() == -1 || application.getNbPlaces() <= 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorTransfert("Field 'nbPlaces' is expected and should be positive."));
		}

		Optional<User> applicant = userRepository.findById(application.getApplicantId());
		if (!applicant.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorTransfert("Field 'applicantId' does not match to existing user."));
		}

		Optional<Offer> offer = offerRepository.findById(application.getOfferId());
		if (!offer.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorTransfert("Field 'offerId' does not match to existing offer."));
		}

		Date today = new Date();
		if (today.after(offer.get().getEndOfInscription())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorTransfert("This offer is not available now."));
		}

		Application newApplication = new Application(applicant.get(), offer.get(), application.getNbPlaces(),
				application.isHelpCooking());

		applicationRepository.save(newApplication);
		return ResponseEntity.ok(newApplication);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/applications/{id}")
	public @ResponseBody ResponseEntity<Object> get(@PathVariable(value = "id") long id) {
		Optional<Application> existingApplication = applicationRepository.findById(id);

		if (existingApplication.isPresent()) {
			return ResponseEntity.ok(existingApplication);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorTransfert("Application not found."));
		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/applications/{id}")
	public @ResponseBody ResponseEntity<Object> put(@PathVariable(value = "id") long id,
			@RequestBody StatutTransfert statut, @RequestHeader HttpHeaders header) {
		Optional<Application> existingApplication = applicationRepository.findById(id);

		if (!existingApplication.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorTransfert("Application not found."));
		}

		if (statut.getStatut() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorTransfert("Field 'statut' is expected."));
		}

		String statutUpperCase = statut.getStatut().toUpperCase();
		if (!statutUpperCase.equals("ACCEPTED") && !statutUpperCase.equals("CANCELED")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorTransfert("Statut must be 'ACCEPTED' or 'CANCELED'."));
		}

		if (statutUpperCase.equals("ACCEPTED")) {
			Token token = tokenRepository.findByToken(header.get("token").get(0)).get();
			if (token.getUserId() == existingApplication.get().getOffer().getCreator().getId()) {
				existingApplication.get().setStatut(EnumStatut.ACCEPTED);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ErrorTransfert("Only creator can accept the application."));
			}
		} else {
			existingApplication.get().setStatut(EnumStatut.CANCELED);
		}

		applicationRepository.save(existingApplication.get());
		return ResponseEntity.ok(existingApplication);
	}
}
