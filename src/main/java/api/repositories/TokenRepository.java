package api.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import api.entities.Token;

public interface TokenRepository extends CrudRepository<Token, Long> {
	Optional<Token> findByToken(String token);
}
