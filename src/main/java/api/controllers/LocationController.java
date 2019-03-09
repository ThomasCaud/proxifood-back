package api.controllers;

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

import api.entities.Location;
import api.repositories.LocationRepository;
import api.transferts.ErrorTransfert;
import transferts.LocationTransfert;

@RestController
public class LocationController {
	@Autowired
	private LocationRepository locationRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/locations")
	public @ResponseBody ResponseEntity<Iterable<Location>> getAll() {
		return ResponseEntity.ok(locationRepository.findAll());
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/locations/{id}")
	public @ResponseBody ResponseEntity<Optional<Location>> get(@PathVariable(value = "id") long id) {
		return ResponseEntity.ok(locationRepository.findById(id));
	}

}
