package api.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import api.entities.Offer;

public interface OfferRepository extends CrudRepository<Offer, Long> {
	Optional<Offer> findById(Long id);
}
