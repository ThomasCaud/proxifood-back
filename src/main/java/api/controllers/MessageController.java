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

import api.entities.Conversation;
import api.entities.Message;
import api.entities.User;
import api.repositories.ConversationRepository;
import api.repositories.MessageRepository;
import api.repositories.UserRepository;
import api.transferts.ErrorTransfert;
import api.transferts.MessageTransfert;

@RestController
public class MessageController {
	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ConversationRepository conversationRepository;

	@RequestMapping(method = RequestMethod.POST, value = "/messages")
	public @ResponseBody ResponseEntity<Object> create(@RequestBody MessageTransfert message) {

		if (message.getMessage() == null || message.getMessage().length() == 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorTransfert("Field 'message' is expected and should not be empty."));
		}

		if (message.getReceiverId() == -1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorTransfert("Field 'receiverId' is expected."));
		}

		if (message.getSenderId() == -1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorTransfert("Field 'senderId' is expected."));
		}

		// pour éviter deux conversations entre deux même utilisateurs
		// on fait en sorte de trier les deux id par ordre croissant
		long idUser1 = message.getReceiverId() < message.getSenderId() ? message.getReceiverId()
				: message.getSenderId();
		Optional<User> user1 = userRepository.findById(idUser1);
		if (!user1.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorTransfert("User " + idUser1 + " not found."));
		}

		long idUser2 = message.getReceiverId() > message.getSenderId() ? message.getReceiverId()
				: message.getSenderId();
		Optional<User> user2 = userRepository.findById(idUser2);
		if (!user2.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorTransfert("User " + idUser2 + " not found."));
		}

		Conversation conversation = conversationRepository.findByUserAAndUserB(user1.get(), user2.get());
		if (conversation == null) {
			conversation = new Conversation(user1.get(), user2.get());
			System.out.println(conversation);
			conversationRepository.save(conversation);
		}

		User sender = (idUser1 == message.getSenderId()) ? user1.get() : user2.get();
		Message newMessage = new Message(sender, message.getMessage(), conversation);
		messageRepository.save(newMessage);

		return ResponseEntity.ok(newMessage);
	}

}
