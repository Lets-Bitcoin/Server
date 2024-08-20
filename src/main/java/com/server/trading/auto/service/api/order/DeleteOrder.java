package com.server.trading.auto.service.api.order;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.server.trading.auto.dto.OrderWaitResponseDto;
import com.server.trading.auto.util.api.ExceptionCode;
import com.server.trading.auto.util.exception.UpbitApiException;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteOrder {

    @Value("${upbit.open.api.access.key}")
    private String accessKey;
    @Value("${upbit.open.api.secret.key}")
    private String secretKey;
    @Value("${upbit.open.api.server.url}")
    private String serverUrl;

    private final GetOrders getOrders;

    public void deleteOrder() {
        HashMap<String, String> params = new HashMap<>();

        for (OrderWaitResponseDto dto : getOrders.getOrders()) {
            params.put("uuid", dto.getUuid());

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
                HttpDelete request = new HttpDelete(serverUrl + "/v1/order?" + queryString);
                request.setHeader("Content-Type", "application/json");
                request.addHeader("Authorization", authenticationToken);

                HttpResponse response = client.execute(request);
                HttpEntity entity = response.getEntity();

                System.out.println(EntityUtils.toString(entity, "UTF-8"));
            } catch (IOException e) {
                throw new UpbitApiException(ExceptionCode.DELETE_ORDER_FAILURE, e.getMessage());
            }
        }
    }
}
