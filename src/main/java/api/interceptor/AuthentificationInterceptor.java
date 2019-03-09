package api.interceptor;

import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import api.entities.Token;
import api.repositories.TokenRepository;

public class AuthentificationInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	TokenRepository tokenRepository;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if (request.getRequestURI().equals("/users") && request.getMethod().equals("POST"))
			return true;

		if (request.getRequestURI().equals("/auth/token") && request.getMethod().equals("POST"))
			return true;

		if (request.getRequestURI().contains("swagger")) {
			return true;
		}

		if (request.getRequestURI().equals("/error") && request.getMethod().equals("POST"))
			return true;

		String tokenStr = request.getHeader("token");
		if (tokenStr == null) {
			response.getWriter().write("You need a token for processing request.");
			return false;
		}

		Optional<Token> token = tokenRepository.findByToken(tokenStr);
		if (!token.isPresent()) {
			response.getWriter().write("Invalid token.");
			return false;
		}
		if (token.get().isValid()) {
			token.get().setUpdatedAt(new Date());
			tokenRepository.save(token.get());
			return true;
		} else {
			response.getWriter().write("Your token has expired.");
			return false;
		}
	}
}
