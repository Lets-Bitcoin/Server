package com.server.trading.auto;

import com.server.trading.auto.service.OrderService;
import com.server.trading.auto.service.ValueService;
import com.server.trading.auto.service.api.UpbitBucket;
import com.server.trading.auto.service.api.account.GetAccounts;
import com.server.trading.auto.service.api.candle.CurrentValue;
import com.server.trading.auto.service.api.candle.MinuteCandle;
import com.server.trading.auto.service.api.order.DeleteOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@RequiredArgsConstructor
@EnableScheduling
@SpringBootApplication
public class TradingServerApplication {

	private final OrderService orderService;
	private final ValueService valueService;

	private final DeleteOrder deleteOrder;
	private final GetAccounts getAccounts;
	private final CurrentValue currentValue;
	private final MinuteCandle minuteCandle;

	@Scheduled(fixedDelay = 30000)
	public void execute() {
		// get data from api
		UpbitBucket upbitBucket = new UpbitBucket();
		upbitBucket
				.add(currentValue)
				.add(minuteCandle)
				.add(getAccounts);
		upbitBucket.execute();
		// delete all orders not executed
		deleteOrder.deleteOrder();
		// trade
		orderService.order();
		// send data to batch server
		valueService.send();
		// clear data
		upbitBucket.clear();

		log.info("===== EXECUTED =====");
	}

	public static void main(String[] args) {
		SpringApplication.run(TradingServerApplication.class, args);
	}

}
