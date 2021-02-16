package in.assignment.string.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.WebServerException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReplyController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StringService service;

	@GetMapping("/reply")
	public ReplyMessage replying() {
		return new ReplyMessage("Message is empty");
	}

	@GetMapping("/reply/{message}")
	public ReplyMessage replying(@PathVariable String message) {
		return new ReplyMessage(message);
	}

	@GetMapping("/v2/reply/{rule}-{message}")
	public ReplyMessage replying(@PathVariable String rule, @PathVariable String message) {
		logger.info("rule: {}, message: {}", rule, message);
		if (rule == null || rule.length() < 2 || rule.length() > 2) {
			throw new WebServerException("invalid rule query", null);
		}
		String res1 = service.runRule(message, rule.charAt(0));
		String res2 = service.runRule(res1, rule.charAt(1));
		logger.info("response for first rule: {}, and response for second rule: {}", res1, res2);
		return new ReplyMessage(res2);
	}
}