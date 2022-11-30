package io.github;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dto.CreateShipDTO;
import io.github.dto.OrderDTO;
import io.github.dto.OrdersDTO;
import io.github.vo.ManoManoResp;
import io.github.vo.Order;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * @author EalenXie
 * @since 2022/08/15 17:12
 */
public class ManoManoClient {

    private final ObjectMapper mapper;

    private final RestOperations restOperations;
    /**
     * https://documenter.getpostman.com/view/6076660/TzCJf9gc#5227aae3-833e-4cd5-befa-ea3fc0f663d0
     */
    public static final String HOST = "https://ws.monechelle.com";

    public ManoManoClient() {
        this.restOperations = new RestTemplate();
        this.mapper = new ObjectMapper();
    }

    public ManoManoClient(RestOperations restOperations) {
        this.restOperations = restOperations;
        this.mapper = new ObjectMapper();
    }


    public ManoManoClient(ObjectMapper objectMapper, RestOperations restOperations) {
        this.restOperations = restOperations;
        this.mapper = objectMapper;
    }

    /**
     * 获取包含认证的 ManoMano  请求头
     */
    @SuppressWarnings("all")
    public HttpHeaders getHeaders() {
        HttpHeaders header = new HttpHeaders();
        header.set("User-Agent", "CompanyName-V1.1");
        return header;
    }

    public RestOperations getRestOperations() {
        return restOperations;
    }

    /**
     * 查询订单列表
     * https://documenter.getpostman.com/view/6076660/TzCJf9gc#f9d85312-cd3d-475b-bab2-061590285605
     *
     * @return {@link ManoManoResp}
     */
    public ManoManoResp<List<Order>> orders(OrdersDTO dto) {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("login", dto.getLogin());
        multiValueMap.add("password", dto.getPassword());
        return getRestOperations().exchange(getManoManoURI(dto), HttpMethod.POST, new HttpEntity<>(multiValueMap, getHeaders()), new ParameterizedTypeReference<ManoManoResp<List<Order>>>() {
        }).getBody();
    }


    /**
     * 查询订单
     * https://documenter.getpostman.com/view/6076660/TzCJf9gc#40d664c3-d4a7-422b-a9e1-873a666f68fc
     */
    public ManoManoResp<List<Order>> order(OrderDTO dto) {
        return getRestOperations().exchange(getManoManoURI(dto), HttpMethod.POST, new HttpEntity<>(null, getHeaders()), new ParameterizedTypeReference<ManoManoResp<List<Order>>>() {
        }).getBody();
    }


    /**
     * 订单关联运单号
     * https://documenter.getpostman.com/view/6076660/TzCJf9gc#5f2547d2-0faa-445d-958e-7664250597fe
     *
     * @param dto 请求参数
     */
    public ManoManoResp<Void> createShipping(CreateShipDTO dto) {
        return getRestOperations().exchange(getManoManoURI(dto), HttpMethod.POST, new HttpEntity<>(null, getHeaders()), new ParameterizedTypeReference<ManoManoResp<Void>>() {
        }).getBody();
    }

    @SuppressWarnings("all")
    public URI getManoManoURI(Object dto) {
        Map<String, String> args = mapper.convertValue(dto, new TypeReference<Map<String, String>>() {
        });
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(HOST);
        LinkedMultiValueMap<String, String> req = new LinkedMultiValueMap<>();
        req.setAll(args);
        builder.queryParams(req);
        return builder.build().encode().toUri();
    }
}
