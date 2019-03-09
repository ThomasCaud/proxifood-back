package api.repositories;

import org.springframework.data.repository.CrudRepository;

import api.entities.Conversation;
import api.entities.User;

public interface ConversationRepository extends CrudRepository<Conversation, Long> {
	Conversation findByUserAAndUserB(User user1, User user2);
	Iterable<Conversation> findByUserAOrUserB(User userA, User userB);
}
