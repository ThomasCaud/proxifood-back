package api.repositories;

import org.springframework.data.repository.CrudRepository;

import api.entities.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {

}
