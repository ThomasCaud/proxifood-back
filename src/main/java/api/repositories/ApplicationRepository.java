package api.repositories;

import org.springframework.data.repository.CrudRepository;

import api.entities.Application;
import api.entities.Offer;

public interface ApplicationRepository extends CrudRepository<Application, Long> {
	Iterable<Application> findByOffer(Offer offerB);
}
