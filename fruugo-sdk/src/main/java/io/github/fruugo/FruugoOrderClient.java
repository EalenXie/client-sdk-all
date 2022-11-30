package io.github.fruugo;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.fruugo.dto.FruugoShipDTO;
import io.github.fruugo.dto.OrdersDTO;
import io.github.fruugo.vo.FruugoOrders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;


public class FruugoOrderClient extends FruugoClient {

    private final ObjectMapper mapper;

    public FruugoOrderClient() {
        this(new RestTemplate());

    }

    public FruugoOrderClient(RestOperations restOperations) {
        this(new ObjectMapper(), restOperations);
    }

    public FruugoOrderClient(ObjectMapper objectMapper, RestOperations restOperations) {
        super(restOperations);
        this.mapper = objectMapper;
    }

    /**
     * https://fruugo.atlassian.net/wiki/spaces/RR/pages/66158675/Requests+Calls
     * 获取订单
     */
    public FruugoOrders orders(String accountNumber, String password, OrdersDTO dto) {
        HttpHeaders headers = getBearerHeaders(accountNumber, password);
        Map<String, String> args = mapper.convertValue(dto, new TypeReference<Map<String, String>>() {
        });
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/orders/download/v2", HOST));
        LinkedMultiValueMap<String, String> req = new LinkedMultiValueMap<>();
        req.setAll(args);
        builder.queryParams(req);
        URI uri = builder.build().encode().toUri();
        return getRestOperations().exchange(uri, HttpMethod.GET, new HttpEntity<>(null, headers), FruugoOrders.class).getBody();
    }

    /**
     * https://fruugo.atlassian.net/wiki/spaces/RR/pages/66158675/Requests+Calls 同一个地址的
     * 获取订单
     */
    public String confirmOrder(String accountNumber, String password, String orderId) {
        HttpHeaders headers = getBearerHeaders(accountNumber, password);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/orders/confirm", HOST));
        LinkedMultiValueMap<String, String> req = new LinkedMultiValueMap<>();
        req.add("orderId", orderId);
        URI uri = builder.build().encode().toUri();
        return getRestOperations().exchange(uri, HttpMethod.POST, new HttpEntity<>(req, headers), String.class).getBody();
    }

    /**
     * https://fruugo.atlassian.net/wiki/spaces/RR/pages/66158675/Requests+Calls
     * 标记发货
     */
    public String shipments(String accountNumber, String password, FruugoShipDTO dto) {
        HttpHeaders headers = getBearerHeaders(accountNumber, password);
        Map<String, String> args = mapper.convertValue(dto, new TypeReference<Map<String, String>>() {
        });
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/orders/ship", HOST));
        LinkedMultiValueMap<String, String> req = new LinkedMultiValueMap<>();
        req.setAll(args);
        URI uri = builder.build().encode().toUri();
        return getRestOperations().exchange(uri, HttpMethod.POST, new HttpEntity<>(req, headers), String.class).getBody();

    }
}
