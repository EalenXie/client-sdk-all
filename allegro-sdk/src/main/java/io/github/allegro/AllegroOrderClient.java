package io.github.allegro;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.allegro.dto.*;
import io.github.allegro.vo.*;
import io.github.dto.*;
import io.github.vo.*;
import io.github.allegro.vo.checkoutform.CheckoutForm;
import io.github.allegro.vo.orderevent.OrderEvent;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Created by EalenXie on 2022/2/21 12:59
 * <a href="https://developer.allegro.pl/documentation">接口文档地址</a>
 * <a href="https://developer.allegro.pl/documentation/#tag/Order-management">订单接口文档</a>
 */
public class AllegroOrderClient extends AllegroClient {

    private final ObjectMapper mapper;

    public AllegroOrderClient() {
        this(new RestTemplate());
    }

    public AllegroOrderClient(RestOperations restOperations) {
        this(new ObjectMapper(), restOperations);
    }

    public AllegroOrderClient(ObjectMapper objectMapper, RestOperations restOperations) {
        super(restOperations);
        this.mapper = objectMapper;
    }

    /**
     * 获取用户订单
     * 接口文档  <a href="https://developer.allegro.pl/documentation/#operation/getListOfOrdersUsingGET">获取用户订单</a>
     *
     * @param dto         用户订单查询参数
     * @param accessToken 令牌
     * @return {@link CheckoutForms} 订单详情
     */
    public CheckoutForms userOrders(OrdersDTO dto, String accessToken) {
        HttpHeaders headers = getBearerHeaders(accessToken);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/order/checkout-forms", isSandBox() ? API_SANDBOX_HOST : API_HOST));
        LinkedMultiValueMap<String, String> req = getQueryParams(dto);
        builder.queryParams(req);
        URI uri = builder.build().encode().toUri();
        ResponseEntity<CheckoutForms> exchange = getRestOperations().exchange(uri, HttpMethod.GET, new HttpEntity<>(null, headers), CheckoutForms.class);
        return exchange.getBody();
    }

    /**
     * 获取订单详情
     * 接口文档  <a href="https://developer.allegro.pl/documentation/#operation/getOrdersDetailsUsingGET">获取订单详情</a>
     *
     * @param orderId     订单Id
     * @param accessToken 令牌
     * @return {@link CheckoutForm} 订单详情
     */
    public CheckoutForm ordersDetails(String orderId, String accessToken) {
        HttpHeaders headers = getBearerHeaders(accessToken);
        ResponseEntity<CheckoutForm> exchange = getRestOperations().exchange(URI.create(String.format("%s/order/checkout-forms/%s", isSandBox() ? API_SANDBOX_HOST : API_HOST, orderId)), HttpMethod.GET, new HttpEntity<>(null, headers), CheckoutForm.class);
        return exchange.getBody();
    }

    /**
     * 订单标记发货
     * <a href="https://developer.allegro.pl/documentation/#operation/createOrderShipmentsUsingPOST">订单标记发货</a>
     *
     * @param orderId     订单Id
     * @param dto         标记发货请求参数
     * @param accessToken 请求token
     * @return {@link Shipment} 标记发货
     */
    public Shipment shipments(String orderId, ShipmentDTO dto, String accessToken) {
        HttpHeaders headers = getBearerHeaders(accessToken);
        HttpEntity<ShipmentDTO> mapHttpEntity = new HttpEntity<>(dto, headers);
        ResponseEntity<Shipment> exchange = getRestOperations().exchange(String.format("%s/order/checkout-forms/%s/shipments", isSandBox() ? API_SANDBOX_HOST : API_HOST, orderId), HttpMethod.POST, mapHttpEntity, Shipment.class);
        return exchange.getBody();
    }

    /**
     * 获取物流追踪号
     * <a href="https://developer.allegro.pl/documentation/#operation/getOrderShipmentsUsingGET">获取物流追踪号</a>
     *
     * @param orderId     订单Id
     * @param accessToken 请求token
     * @return {@link Shipment} 物流返回对象
     */
    public Shipment shipments(String orderId, String accessToken) {
        HttpHeaders headers = getBearerHeaders(accessToken);
        ResponseEntity<Shipment> exchange = getRestOperations().exchange(String.format("%s/order/checkout-forms/%s/shipments", isSandBox() ? API_SANDBOX_HOST : API_HOST, orderId), HttpMethod.GET, new HttpEntity<>(null, headers), Shipment.class);
        return exchange.getBody();
    }


    /**
     * 获取订单事件查询
     * <a href="https://developer.allegro.pl/documentation/#operation/getOrderEventsUsingGET">获取订单事件查询</a>
     *
     * @param dto         事件查询参数
     * @param accessToken 请求token
     * @return {@link OrderEvent}
     */
    public OrderEvent events(OrderEventDTO dto, String accessToken) {
        HttpHeaders headers = getBearerHeaders(accessToken);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/order/events", isSandBox() ? API_SANDBOX_HOST : API_HOST));
        LinkedMultiValueMap<String, String> req = getQueryParams(dto);
        builder.queryParams(req);
        ResponseEntity<OrderEvent> exchange = getRestOperations().exchange(builder.build().encode().toUri(), HttpMethod.GET, new HttpEntity<>(null, headers), OrderEvent.class);
        return exchange.getBody();
    }

    /**
     * 获取订单事件统计
     * <a href="https://developer.allegro.pl/documentation/#operation/getOrderEventsStatisticsUsingGET">获取订单事件统计</a>
     *
     * @param accessToken 请求token
     * @return {@link EventStats}
     */
    public EventStats eventStats(String accessToken) {
        HttpHeaders headers = getBearerHeaders(accessToken);
        ResponseEntity<EventStats> exchange = getRestOperations().exchange(String.format("%s/order/event-stats", isSandBox() ? API_SANDBOX_HOST : API_HOST), HttpMethod.GET, new HttpEntity<>(null, headers), EventStats.class);
        return exchange.getBody();
    }


    /**
     * <a href="https://developer.allegro.pl/documentation/#operation/getOrdersCarriersUsingGET">获取订单物流</a>
     *
     * @param accessToken 请求token
     * @return {@link OrderCarriers}
     */
    public OrderCarriers orderCarriers(String accessToken) {
        HttpHeaders headers = getBearerHeaders(accessToken);
        ResponseEntity<OrderCarriers> exchange = getRestOperations().exchange(String.format("%s/order/carriers", isSandBox() ? API_SANDBOX_HOST : API_HOST), HttpMethod.GET, new HttpEntity<>(null, headers), OrderCarriers.class);
        return exchange.getBody();
    }


    /**
     * 修改订单状态
     * <a href="https://developer.allegro.pl/documentation/#operation/setOrderFulfillmentUsingPUT">修改订单状态</a>
     *
     * @param orderId     订单Id
     * @param dto         修改订单状态请求参数
     * @param accessToken 请求token
     */
    public void fulfillment(String orderId, FulfillmentDTO dto, String accessToken) {
        HttpHeaders headers = getBearerHeaders(accessToken);
        getRestOperations().put(String.format("%s/order/checkout-forms/%s/fulfillment", isSandBox() ? API_SANDBOX_HOST : API_HOST, orderId), new HttpEntity<>(dto, headers));
    }

    /**
     * 获取订单发票详细信息
     * <a href="https://developer.allegro.pl/documentation/#operation/getOrderInvoicesDetails">获取订单发票详细信息</a>
     *
     * @param orderId     订单Id
     * @param accessToken 请求token
     * @return {@link Invoices} 发票
     */
    public Invoices invoices(String orderId, String accessToken) {
        HttpHeaders headers = getBearerHeaders(accessToken);
        ResponseEntity<Invoices> exchange = getRestOperations().exchange(String.format("%s/order/checkout-forms/%s/invoices", isSandBox() ? API_SANDBOX_HOST : API_HOST, orderId), HttpMethod.GET, new HttpEntity<>(null, headers), Invoices.class);
        return exchange.getBody();
    }


    /**
     * 创建发票
     * <a href="https://developer.allegro.pl/documentation/#operation/addOrderInvoicesMetadata">创建发票</a>
     *
     * @param orderId     订单Id
     * @param dto         发票请求参数
     * @param accessToken 请求token
     * @return {@link IdVO} IdVO
     */
    public IdVO invoices(String orderId, InvoicesDTO dto, String accessToken) {
        HttpHeaders headers = getBearerHeaders(accessToken);
        String url = String.format("%s/order/checkout-forms/%s/invoices", isSandBox() ? API_SANDBOX_HOST : API_HOST, orderId);
        ResponseEntity<IdVO> exchange = getRestOperations().exchange(URI.create(url), HttpMethod.POST, new HttpEntity<>(dto, headers), IdVO.class);
        return exchange.getBody();
    }

    /**
     * 上传发票
     * <a href="https://developer.allegro.pl/documentation/#operation/uploadOrderInvoiceFile">上传发票</a>
     *
     * @param orderId   订单Id
     * @param file      发票文件
     * @param invoiceId 发票id
     */
    public void putInvoices(String orderId, String invoiceId, byte[] file, String accessToken) {
        HttpHeaders headers = getBearerHeaders(accessToken);
        String url = String.format("%s/order/checkout-forms/%s/invoices/%s/file", isSandBox() ? API_SANDBOX_HOST : API_HOST, orderId, invoiceId);
        getRestOperations().exchange(URI.create(url), HttpMethod.PUT, new HttpEntity<>(file, headers), String.class);
    }


    /**
     * 获取账单条目列表
     * https://developer.allegro.pl/documentation/#operation/getBillingEntries
     */
    public BillingVO billings(BillingDTO dto, String accessToken) {
        HttpHeaders headers = getBearerHeaders(accessToken);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/billing/billing-entries", isSandBox() ? API_SANDBOX_HOST : API_HOST));
        LinkedMultiValueMap<String, String> req = getQueryParams(dto);
        builder.queryParams(req);
        ResponseEntity<BillingVO> exchange = getRestOperations().exchange(builder.build().encode().toUri(), HttpMethod.GET, new HttpEntity<>(null, headers), BillingVO.class);
        return exchange.getBody();
    }


    /**
     * 获取费用类型
     * https://developer.allegro.pl/documentation/#operation/getBillingTypes
     */
    public List<BillingTypeVO> billingsType(String token, BillingTypeDTO dto) {
        HttpHeaders headers = getBearerHeaders(token);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/billing/billing-types", isSandBox() ? API_SANDBOX_HOST : API_HOST));
        LinkedMultiValueMap<String, String> req = getQueryParams(dto);
        builder.queryParams(req);
        ResponseEntity<String> exchange = getRestOperations().exchange(builder.build().encode().toUri(), HttpMethod.GET, new HttpEntity<>(null, headers), String.class);
        return readAllegroBody(exchange.getBody(), new TypeReference<List<BillingTypeVO>>() {
        });
    }

    @SuppressWarnings("all")
    public LinkedMultiValueMap<String, String> getQueryParams(Object dto) {
        Map<String, String> args = mapper.convertValue(dto, new TypeReference<Map<String, String>>() {
        });
        LinkedMultiValueMap<String, String> req = new LinkedMultiValueMap<>();
        req.setAll(args);
        return req;
    }


    public <T> T readAllegroBody(String body, TypeReference<T> valueTypeRef) {
        try {
            return mapper.readValue(body, valueTypeRef);
        } catch (IOException e) {
            throw new UnsupportedOperationException(e);
        }
    }
}
