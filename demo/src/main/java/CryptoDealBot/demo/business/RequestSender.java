package CryptoDealBot.demo.business;

import CryptoDealBot.demo.business.exceptions.TemporaryDataUnavailableException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

public class RequestSender {

    @Retryable(value = {TemporaryDataUnavailableException.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public static String sendNextRequest(String URL) {
        try {
            String response = new RestTemplate().getForEntity(URL, String.class).getBody();
            if (response == null || response.isEmpty())
                throw new TemporaryDataUnavailableException("Data is not available yet. Please try again.");
            return response;
        } catch (HttpClientErrorException | ResourceAccessException e) {
            return e.getMessage();
        }
    }
}
