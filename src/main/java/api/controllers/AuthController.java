package api.controllers;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.hash.Hashing;

import api.entities.Token;
import api.entities.User;
import api.repositories.TokenRepository;
import api.repositories.UserRepository;
import api.transferts.AuthTransfert;
import api.transferts.ErrorTransfert;

@RestController
public class AuthController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TokenRepository tokenRepository;

	@RequestMapping(method = RequestMethod.POST, value = "/auth/token")
	public @ResponseBody ResponseEntity<Object> create(@RequestBody AuthTransfert auth) {
		if (auth.getLogin().length() == 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorTransfert("Field 'login' is expected."));
		}

		if (auth.getPassword().length() == 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorTransfert("Field 'password' is expected."));
		}

		User user = userRepository.findByLogin(auth.getLogin());
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorTransfert("User not found."));
		}

		String hashedPassword = Hashing.sha256().hashString(auth.getPassword(), StandardCharsets.UTF_8).toString();
		if (!user.getPassword().equals(hashedPassword)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorTransfert("ID and password do not match"));
		}

		Optional<Token> optionalToken = tokenRepository.findById(user.getId());
		Token token = null;
		if (optionalToken.isPresent()) {
			token = optionalToken.get();
			token.setUpdatedAt(new Date());
		} else {
			token = new Token(user.getId());
		}

		tokenRepository.save(token);
		return ResponseEntity.ok(token);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/auth/token")
	public @ResponseBody ResponseEntity<Object> delete(@RequestHeader(value = "token") String token) {
		Optional<Token> existingToken = tokenRepository.findByToken(token);

		if (existingToken.isPresent()) {
			tokenRepository.delete(existingToken.get());
			return ResponseEntity.ok(existingToken);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorTransfert("Token not found."));
		}
	}
}
