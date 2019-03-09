package api.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import api.entities.Conversation;
import api.repositories.ConversationRepository;
import api.transferts.ErrorTransfert;

@RestController
public class ConversationController {
	@Autowired
	private ConversationRepository conversationRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/conversations/{id}")
	public @ResponseBody ResponseEntity<Object> get(@PathVariable(value = "id") long id) {
		Optional<Conversation> existingConversation = conversationRepository.findById(id);

		if (existingConversation.isPresent()) {
			return ResponseEntity.ok(existingConversation);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorTransfert("Conversation not found."));
		}
	}
}
