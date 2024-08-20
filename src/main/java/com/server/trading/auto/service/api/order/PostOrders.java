package com.server.trading.auto.service.api.order;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;
import com.server.trading.auto.dto.AccountSaveRequestDto;
import com.server.trading.auto.service.api.account.GetAccounts;
import com.server.trading.auto.util.OrderType;
import com.server.trading.auto.util.api.ExceptionCode;
import com.server.trading.auto.util.exception.UpbitApiException;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PostOrders {

    @Value("${upbit.open.api.access.key}")
    private String accessKey;
    @Value("${upbit.open.api.secret.key}")
    private String secretKey;
    @Value("${upbit.open.api.server.url}")
    private String serverUrl;

    private final GetAccounts getAccounts;

    public void postOrder(String symbol, OrderType type, int percent) {
        Map<String, Double> coins = getAccounts.getCoins();
        List<AccountSaveRequestDto> account = getAccounts.getAccount();
        Double balance = account.stream()
                .filter(coin -> symbol.equals(coin.getCurrency()))
                .findFirst()
                .orElseThrow(
                        () -> new UpbitApiException(ExceptionCode.ORDER_NOT_EXIST, null)
                ).getBalance();

        HashMap<String, String> params = new HashMap<>();
        params.put("market", "KRW-" + symbol);

        System.out.println(String.valueOf(balance * ((double) percent / 100) * coins.get(symbol)));

        if (type == OrderType.BID) {
            params.put("side", type.getOrder());
            params.put("price", String.valueOf(coins.get(symbol) * ((double) percent / 100)));
            params.put("ord_type", "price");
        } else if (type == OrderType.ASK) {
            params.put("side", type.getOrder());
            params.put("volume", String.valueOf(balance * ((double) percent / 100)));
            params.put("ord_type", "market");
        }

        ArrayList<String> queryElements = new ArrayList<>();
        for(Map.Entry<String, String> entity : params.entrySet()) {
            queryElements.add(entity.getKey() + "=" + entity.getValue());
        }

        String queryString = String.join("&", queryElements.toArray(new String[0]));

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
            md.update(queryString.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String queryHash = String.format("%0128x", new BigInteger(1, md.digest()));

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String jwtToken = JWT.create()
                .withClaim("access_key", accessKey)
                .withClaim("nonce", UUID.randomUUID().toString())
                .withClaim("query_hash", queryHash)
                .withClaim("query_hash_alg", "SHA512")
                .sign(algorithm);

        String authenticationToken = "Bearer " + jwtToken;

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(serverUrl + "/v1/orders");
            request.setHeader("Content-Type", "application/json");
            request.addHeader("Authorization", authenticationToken);
            request.setEntity(new StringEntity(new Gson().toJson(params)));

            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();

            System.out.println(EntityUtils.toString(entity, "UTF-8"));
        } catch (IOException e) {
            throw new UpbitApiException(ExceptionCode.POST_ORDER_FAILURE, e.getMessage());
        }
    }
}
