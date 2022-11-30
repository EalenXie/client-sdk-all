package io.github.vo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.vo.dto.FulfillmentDTO;
import io.github.vo.dto.OrdersDTO;
import io.github.vo.vo.*;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

/**
 * @author EalenXie
 * @since 2022/08/05 15:51
 */
public class ShopifyOrderClient extends ShopifyClient {

    private final ObjectMapper objectMapper;

    public ShopifyOrderClient() {
        this(new RestTemplate());
    }

    public ShopifyOrderClient(RestOperations restOperations) {
        super(restOperations);
        this.objectMapper = new ObjectMapper();
    }

    public ShopifyOrderClient(ObjectMapper objectMapper, RestOperations restOperations) {
        super(restOperations);
        this.objectMapper = objectMapper;
    }

    public HttpHeaders getPasswordHeader(String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(SHOPIFY_ACCESS_TOKEN, password);
        return headers;
    }


    /**
     * 文档: https://shopify.dev/api/admin-rest/2021-10/resources/order
     *
     * @param host 请详见查看  {@link ShopifyClient#getShopifyApiHost(String, String, String, String)}
     */
    public ResponseEntity<OrdersResource> getOrders(String host, String password, OrdersDTO dto) {
        String url = String.format("%s/orders.json", host);
        HttpHeaders header = getPasswordHeader(password);
        header.setContentType(MediaType.APPLICATION_JSON);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        Map<String, String> args = objectMapper.convertValue(dto, new TypeReference<Map<String, String>>() {
        });
        LinkedMultiValueMap<String, String> req = new LinkedMultiValueMap<>();
        req.setAll(args);
        builder.queryParams(req);
        URI uri = builder.build().encode().toUri();
        return getRestOperations().exchange(uri, HttpMethod.GET, new HttpEntity<>(null, header), OrdersResource.class);
    }


    /**
     * 拉取shopify下一页
     * https://shopify.dev/api/admin-rest/2021-10/resources/order#get-orders
     *
     * @param nextHost 下一页的接口url
     * @param password 密码
     */
    public ResponseEntity<OrdersResource> getOrders(String nextHost, String password) {
        HttpHeaders header = getPasswordHeader(password);
        header.setContentType(MediaType.APPLICATION_JSON);
        return getRestOperations().exchange(URI.create(nextHost), HttpMethod.GET, new HttpEntity<>(null, header), OrdersResource.class);
    }


    /**
     * 文档: https://shopify.dev/api/admin-rest/2021-10/resources/order-risk#get-orders-order-id-risks
     * 检索订单的所有订单风险列表
     */
    public OrdersRiskResource getOrdersRisks(Long orderId, String host, String password) {
        HttpHeaders header = getPasswordHeader(password);
        String url = String.format("%s/orders/%s/risks.json", host, orderId);
        header.setContentType(MediaType.APPLICATION_JSON);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        URI uri = builder.build().encode().toUri();
        return getRestOperations().exchange(uri, HttpMethod.GET, new HttpEntity<>(null, header), OrdersRiskResource.class).getBody();
    }

    /**
     * 文档: https://shopify.dev/api/admin-rest/2021-10/resources/transaction#get-orders-order-id-transactions
     * 获取交易流水号
     */
    public FinanceResource getShopifyFinance(Long orderId, String host, String password) {
        HttpHeaders header = getPasswordHeader(password);
        String url = String.format("%s/orders/%s/transactions.json", host, orderId);
        header.setContentType(MediaType.APPLICATION_JSON);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        URI uri = builder.build().encode().toUri();
        return getRestOperations().exchange(uri, HttpMethod.GET, new HttpEntity<>(null, header), FinanceResource.class).getBody();
    }

    /**
     * https://shopify.dev/api/admin-rest/2022-07/resources/location
     * 获取位置
     */
    public LocationResource location(String host, String password) {
        HttpHeaders header = getPasswordHeader(password);
        String url = String.format("%s/locations.json", host);
        header.setContentType(MediaType.APPLICATION_JSON);
        return getRestOperations().exchange(URI.create(url), HttpMethod.GET, new HttpEntity<>(null, header), LocationResource.class).getBody();
    }

    /**
     * https://shopify.dev/api/admin-rest/2022-07/resources/fulfillment#post-fulfillments
     * 为一个或多个发货订单创建发货  2022版本的发货接口
     */
    public FulfillmentResource fulfillment(String host, String password, FulfillmentDTO dto) {
        HttpHeaders header = getPasswordHeader(password);
        String url = String.format("%s/fulfillments.json", host);
        header.setContentType(MediaType.APPLICATION_JSON);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        URI uri = builder.build().encode().toUri();
        return getRestOperations().exchange(uri, HttpMethod.POST, new HttpEntity<>(dto, header), FulfillmentResource.class).getBody();
    }


    /**
     * 检索与订单关联的发货   用于查看标记发货成功几个商品和数量
     * https://shopify.dev/api/admin-rest/2022-07/resources/fulfillment#get-fulfillment-orders-fulfillment-order-id-fulfillments
     */
    public FulfillmentsResource fulfillments(String host, String password, String orderId) {
        HttpHeaders header = getPasswordHeader(password);
        String url = String.format("%s/orders/%s/fulfillments.json", host, orderId);
        header.setContentType(MediaType.APPLICATION_JSON);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        URI uri = builder.build().encode().toUri();
        return getRestOperations().exchange(uri, HttpMethod.GET, new HttpEntity<>(null, header), FulfillmentsResource.class).getBody();
    }

    /**
     * 检索与订单关联的发货订单列表  用于查询标发需要的参数
     * https://shopify.dev/api/admin-rest/2022-07/resources/fulfillmentorder#get-orders-order-id-fulfillment-orders
     */
    public FulfillmentOrderResource fulfillmentOrders(String host, String password, String orderId) {
        HttpHeaders header = getPasswordHeader(password);
        String url = String.format("%s/orders/%s/fulfillment_orders.json", host, orderId);
        header.setContentType(MediaType.APPLICATION_JSON);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        URI uri = builder.build().encode().toUri();
        return getRestOperations().exchange(uri, HttpMethod.GET, new HttpEntity<>(null, header), FulfillmentOrderResource.class).getBody();
    }


    /**
     * 运营商服务
     * https://shopify.dev/api/admin-rest/2022-07/resources/carrierservice#get-carrier-services
     */
    public CarrierServiceResource carrierServices(String host, String password) {
        HttpHeaders header = getPasswordHeader(password);
        String url = String.format("%s/carrier_services.json", host);
        header.setContentType(MediaType.APPLICATION_JSON);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        URI uri = builder.build().encode().toUri();
        return getRestOperations().exchange(uri, HttpMethod.GET, new HttpEntity<>(null, header), CarrierServiceResource.class).getBody();
    }


    /**
     * 获取单个订单
     * https://shopify.dev/api/admin-rest/2022-07/resources/order#get-orders-order-id
     */
    public OrderResource getOrder(String host, String password, String orderId) {
        HttpHeaders header = getPasswordHeader(password);
        String url = String.format("%s/orders/%s.json", host, orderId);
        header.setContentType(MediaType.APPLICATION_JSON);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        URI uri = builder.build().encode().toUri();
        return getRestOperations().exchange(uri, HttpMethod.GET, new HttpEntity<>(null, header), OrderResource.class).getBody();
    }
}
