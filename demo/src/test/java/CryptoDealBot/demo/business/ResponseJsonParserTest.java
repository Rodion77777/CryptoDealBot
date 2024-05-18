package CryptoDealBot.demo.business;

import CryptoDealBot.demo.domain.entity.CryptoPairEntity;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ResponseJsonParserTest {

    private final String jsonResponseExample = "{\"symbol\":\"ETHUSDT\",\"markPrice\":\"3134.58000000\",\"indexPrice\":\"3136.19227273\",\"estimatedSettlePrice\":\"3135.78509656\",\"lastFundingRate\":\"0.00010000\",\"interestRate\":\"0.00010000\",\"nextFundingTime\":1716048000000,\"time\":1716032308000}";
    private final String symbol = "ETHUSDT";
    private final double assertMarkPrice = 3134.58000000;
    private final double indexPrice = 3136.19227273;
    private final double estimatedSettlePrice = 3135.78509656;
    private final double lastFundingRate = 0.00010000;
    private final double interestRate = 0.00010000;
    private final Date nextFundingTime = new Date(1716048000000L);
    private final Date time = new Date(1716032308000L);

    @Test
    void getMarkPrice() {
        try {
            double markPrice = ResponseJsonParser.getMarkPrice(jsonResponseExample);
            assertEquals(assertMarkPrice, markPrice);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void parseJsonToCryptoPairEntity() {
        try {
            CryptoPairEntity cpe = ResponseJsonParser.parseJsonToCryptoPairEntity(jsonResponseExample);
            assertEquals(symbol, cpe.getSymbol());
            assertEquals(assertMarkPrice, cpe.getMarkPrice());
            assertEquals(indexPrice, cpe.getIndexPrice());
            assertEquals(estimatedSettlePrice, cpe.getEstimatedSettlePrice());
            assertEquals(lastFundingRate, cpe.getLastFundingRate());
            assertEquals(interestRate, cpe.getInterestRate());
            assertEquals(nextFundingTime, cpe.getNextFundingTime());
            assertEquals(time, cpe.getTime());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}