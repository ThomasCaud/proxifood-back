package api.entities;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Token {
	@Id
	private long userId;
	@NotEmpty
	private String token;
	@NotNull
	private Date createdAt;
	@NotNull
	private Date updatedAt;
	@NotNull
	private Integer duration;

	public static String getGeneratedToken() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public Token() {
		setUserId(-1);
		setToken(Token.getGeneratedToken());
		setCreatedAt(new Date());
		setUpdatedAt(new Date());
		setDuration(3600);
	}

	public Token(final long userId) {
		setUserId(userId);
		setToken(Token.getGeneratedToken());
		setCreatedAt(new Date());
		setUpdatedAt(new Date());
		setDuration(3600);
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "Token [userId=" + userId + ", token=" + token + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", duration=" + duration + "]";
	}

	public boolean isValid() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.getUpdatedAt());
		calendar.add(Calendar.SECOND, getDuration());
		return calendar.getTime().after(new Date());
	}
}
