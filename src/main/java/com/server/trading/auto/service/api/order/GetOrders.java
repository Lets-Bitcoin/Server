package com.server.trading.auto.service.api.order;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.trading.auto.dto.OrderWaitResponseDto;
import com.server.trading.auto.util.CoinListUtil;
import com.server.trading.auto.util.api.ExceptionCode;
import com.server.trading.auto.util.exception.UpbitApiException;
import lombok.Getter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

@Getter
@Service
public class GetOrders {

    @Value("${upbit.open.api.access.key}")
    private String accessKey;
    @Value("${upbit.open.api.secret.key}")
    private String secretKey;
    @Value("${upbit.open.api.server.url}")
    private String serverUrl;

    private final List<OrderWaitResponseDto> orders = new ArrayList<>();

    public void getOrder() {
        HashMap<String, String> params = new HashMap<>();

        for (String symbol : CoinListUtil.getList()) {
            params.put("market", "KRW-" + symbol);

            String[] states = {
                    "wait",
                    "watch"
            };

            ArrayList<String> queryElements = new ArrayList<>();
            for(Map.Entry<String, String> entity : params.entrySet()) {
                queryElements.add(entity.getKey() + "=" + entity.getValue());
            }
            for(String state : states) {
                queryElements.add("states[]=" + state);
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
                HttpGet request = new HttpGet(serverUrl + "/v1/orders/open?" + queryString);
                request.setHeader("Content-Type", "application/json");
                request.addHeader("Authorization", authenticationToken);

                HttpResponse response = client.execute(request);
                HttpEntity entity = response.getEntity();

                String json = EntityUtils.toString(entity, "UTF-8");
                ObjectMapper objectMapper = new ObjectMapper();
                orders.addAll(objectMapper.readValue(json, new TypeReference<List<OrderWaitResponseDto>>() {}));
            } catch (IOException e) {
                throw new UpbitApiException(ExceptionCode.GET_ORDER_FAILURE, e.getMessage());
            }
        }
    }
}
