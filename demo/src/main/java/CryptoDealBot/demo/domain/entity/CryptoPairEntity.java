package CryptoDealBot.demo.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@AllArgsConstructor
@Data
@NonNull
public class CryptoPairEntity {
    private String symbol;
    private double markPrice;
    private double indexPrice;
    private double estimatedSettlePrice;
    private double lastFundingRate;
    private Date nextFundingTime;
    private double interestRate;
    private Date time;
}

/* EXAMPLE
* {
    "symbol": "BTCUSDT",
    "markPrice": "11793.63104562",  // mark price
    "indexPrice": "11781.80495970", // index price
    "estimatedSettlePrice": "11781.16138815", // Estimated Settle Price, only useful in the last hour before the settlement starts.
    "lastFundingRate": "0.00038246",  // This is the Latest funding rate
    "nextFundingTime": 1597392000000,
    "interestRate": "0.00010000",
    "time": 1597370495002
}
* */