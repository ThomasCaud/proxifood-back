package api.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import api.entities.Comment;
import api.entities.Conversation;
import api.entities.Message;
import api.entities.User;
import api.repositories.CommentRepository;
import api.repositories.ConversationRepository;
import api.repositories.MessageRepository;
import api.repositories.UserRepository;
import api.transferts.CommentTransfert;
import api.transferts.ErrorTransfert;
import api.transferts.MessageTransfert;

@RestController
public class CommentController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CommentRepository commentRepository;

	@RequestMapping(method = RequestMethod.POST, value = "/comments")
	public @ResponseBody ResponseEntity<Object> create(@RequestBody CommentTransfert comment) {

		if (comment.getMessage() == null || comment.getMessage().length() == 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorTransfert("Field 'message' is expected and should not be empty."));
		}

		if (comment.getReceiverId() == -1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorTransfert("Field 'receiverId' is expected."));
		}

		if (comment.getSenderId() == -1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorTransfert("Field 'senderId' is expected."));
		}
		
		if(comment.getNote() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorTransfert("Field 'note' is expected."));
		}

		Optional<User> sender = userRepository.findById(comment.getSenderId());
		if (!sender.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorTransfert("User " + comment.getSenderId() + " not found."));
		}
		
		Optional<User> receiver = userRepository.findById(comment.getReceiverId());
		if (!receiver.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorTransfert("User " + comment.getReceiverId() + " not found."));
		}
		
		if(commentRepository.findBySenderAndReceiver(sender.get(), receiver.get()) != null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorTransfert("The user " + sender.get().getLogin() + " has already poster a comment about " + receiver.get().getLogin(), 3));
		}
		
		if(comment.getNote() < 1 || comment.getNote() > 5) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorTransfert("The note given to the user must be between 1 and 5", 4));
		}
		
		if(comment.getSenderId() == comment.getReceiverId()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorTransfert("A User cannot post a comment to himself", 5));
		}
		
		Comment newComment = new Comment(sender.get(),receiver.get(), comment.getMessage(), comment.getNote());
		
		commentRepository.save(newComment);

		return ResponseEntity.ok(newComment);
	}

}
