package com.server.trading.auto.service.api.account;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.trading.auto.dto.AccountSaveRequestDto;
import com.server.trading.auto.service.api.UpbitAbstract;
import com.server.trading.auto.service.api.candle.CurrentValue;
import com.server.trading.auto.util.api.ExceptionCode;
import com.server.trading.auto.util.exception.UpbitApiException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Getter
@Service
@RequiredArgsConstructor
public class GetAccounts extends UpbitAbstract {

    @Value("${upbit.open.api.access.key}")
    private String accessKey;
    @Value("${upbit.open.api.secret.key}")
    private String secretKey;
    @Value("${upbit.open.api.server.url}")
    private String serverUrl;

    private final CurrentValue currentValue;

    private List<AccountSaveRequestDto> account = new ArrayList<>();    // list of asset (coin, KRW)
    private Map<String, Double> coins = new HashMap<>();                // asset of each coin
    private Double asset = 0.0;                                         // total asset (coins + KRW)

    @Override
    public void call() {
        account();
    }

    @Override
    public void clear() {
        account.clear();
        coins.clear();
        asset = 0.0;
    }

    private void account() {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String jwtToken = JWT.create()
                .withClaim("access_key", accessKey)
                .withClaim("nonce", UUID.randomUUID().toString())
                .sign(algorithm);

        String authenticationToken = "Bearer " + jwtToken;

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "/v1/accounts");
            request.setHeader("Content-Type", "application/json");
            request.addHeader("Authorization", authenticationToken);

            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();

            String json = EntityUtils.toString(entity, "UTF-8");
            ObjectMapper objectMapper = new ObjectMapper();
            account = objectMapper.readValue(json, new TypeReference<List<AccountSaveRequestDto>>() {});

            Map<String, Integer> currentValues = currentValue.getCurrentValue();
            coins = new HashMap<>();
            Double add;
            for (AccountSaveRequestDto dto : account) {
                if (currentValues.containsKey(dto.getCurrency())) {
                    add = currentValues.get(dto.getCurrency()) * dto.getBalance();
                    asset += add;
                    coins.put(dto.getCurrency(), add);
                }
                if (dto.getCurrency().equals("KRW")) {
                    add = (Double) dto.getBalance();
                    asset += add;
                    coins.put("KRW", add);
                }
            }
        } catch (IOException e) {
            throw new UpbitApiException(ExceptionCode.ACCOUNT_FAILURE, e.getMessage());
        }
    }
}
