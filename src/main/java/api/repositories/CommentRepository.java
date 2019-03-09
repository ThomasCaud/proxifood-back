package api.repositories;

import org.springframework.data.repository.CrudRepository;

import api.entities.Comment;
import api.entities.Conversation;
import api.entities.User;

public interface CommentRepository extends CrudRepository<Comment, Long> {
	Comment findBySenderAndReceiver(User sender, User receiver);
}
