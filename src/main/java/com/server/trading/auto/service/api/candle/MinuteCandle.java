package com.server.trading.auto.service.api.candle;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.server.trading.auto.dto.MinuteCandleValueResponseDto;
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
public class MinuteCandle extends UpbitAbstract {

    @Override
    public void call() {
        minuteCandle();
    }

    @Override
    public void clear() {
        candles.clear();
    }

    private final Map<String, List<MinuteCandleValueResponseDto>> candles = new HashMap<>(); // minute candle data (trading price)

    private void minuteCandle() {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();

        for (String coin : CoinListUtil.getList()) {
            ArrayList<MinuteCandleValueResponseDto> price = new ArrayList<>();
            Request request = new Request.Builder()
                    .url("https://api.upbit.com/v1/candles/minutes/5?market=KRW-" + coin + "&count=200")
                    .get()
                    .addHeader("accept", "application/json")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String jsonData = response.body().string();
                    JsonArray jsonArray = gson.fromJson(jsonData, JsonArray.class);

                    for (JsonElement element : jsonArray) {
                        JsonObject candle = element.getAsJsonObject();

                        MinuteCandleValueResponseDto dto = new MinuteCandleValueResponseDto(candle);
                        price.add(dto);
                    }
                } else {
                    throw new UpbitApiException(ExceptionCode.MINUTE_CANDLE_FAILURE, null);
                }
            } catch (IOException e) {
                throw new UpbitApiException(ExceptionCode.MINUTE_CANDLE_FAILURE, e.getMessage());
            }

            candles.put(coin, price.stream().toList());
        }
    }
}
