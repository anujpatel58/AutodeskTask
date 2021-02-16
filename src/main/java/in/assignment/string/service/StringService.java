package in.assignment.string.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.boot.web.server.WebServerException;
import org.springframework.stereotype.Service;

@Service
public class StringService {
	enum Rule {
		reverse('1'), md5('2');

		private char rule;

		Rule(char rule) {
			this.rule = rule;
		}

		public char getRule() {
			return rule;
		}

		public static Rule getByValue(char c) {
			for (Rule e : Rule.values())
				if (c == e.rule)
					return e;
			return null;
		}
	}

	public String runRule(String message, char rule) {
		switch (Rule.getByValue(rule)) {
		case reverse:
			return reverse(message);
		case md5:
			return getMd5(message);
		default:
			throw new WebServerException("invalid rule", null);
		}
	}

	private String reverse(String input) {
		char[] chars = input.toCharArray();
		char[] output = new char[chars.length];
		for (int i = chars.length - 1; i >= 0; i--) {
			output[chars.length - 1 - i] = chars[i];
		}
		return String.copyValueOf(output);
	}

	private String getMd5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger no = new BigInteger(1, messageDigest);

			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
