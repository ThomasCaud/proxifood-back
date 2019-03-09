package api.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import api.entities.Location;

public interface LocationRepository extends CrudRepository<Location, Long> {
	Optional<Location> findById(long id);
}
