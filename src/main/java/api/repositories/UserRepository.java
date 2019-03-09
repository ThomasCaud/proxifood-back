package api.repositories;

import org.springframework.data.repository.CrudRepository;

import api.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByLogin(String login);

	User findByEmail(String email);
}
