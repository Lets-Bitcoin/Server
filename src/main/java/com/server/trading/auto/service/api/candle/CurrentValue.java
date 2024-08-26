package com.server.trading.auto.service.api.candle;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.server.trading.auto.dto.ValueSendRequestDto;
import com.server.trading.auto.service.api.UpbitAbstract;
import com.server.trading.auto.util.CoinListUtil;
import com.server.trading.auto.util.api.ExceptionCode;
import com.server.trading.auto.util.exception.UpbitApiException;
import lombok.Getter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Service
public class CurrentValue extends UpbitAbstract {

    @Override
    public void call() {
        currentValue();
    }

    @Override
    public void clear() {
        sendData.clear();
        currentValue.clear();
    }

    private final Map<String, Integer> currentValue = new HashMap<>();      // current value of each coin
    private final List<ValueSendRequestDto> sendData = new ArrayList<>();   // value data to send through kafka producer

    private void currentValue() {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();

        for (String symbol : CoinListUtil.getList()) {
            Request request = new Request.Builder()
                    .url("https://api.upbit.com/v1/ticker?markets=KRW-" + symbol)
                    .get()
                    .addHeader("accept", "application/json")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String jsonData = response.body().string();
                    JsonArray jsonArray = gson.fromJson(jsonData, JsonArray.class);

                    for (JsonElement element : jsonArray) {
                        JsonObject candle = element.getAsJsonObject();

                        ValueSendRequestDto send = new ValueSendRequestDto(candle);
                        sendData.add(send);

                        int tradePrice = send.getTrade_price();
                        currentValue.put(symbol, tradePrice);
                    }
                } else {
                    throw new UpbitApiException(ExceptionCode.CURRENT_VALUE_FAILURE, null);
                }
            } catch (IOException e) {
                throw new UpbitApiException(ExceptionCode.CURRENT_VALUE_FAILURE, e.getMessage());
            }
        }
    }
}
