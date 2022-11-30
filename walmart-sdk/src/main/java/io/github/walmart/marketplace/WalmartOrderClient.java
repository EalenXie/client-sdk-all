package io.github.walmart.marketplace;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.walmart.marketplace.vo.WalmartOrder;
import io.github.walmart.marketplace.vo.WalmartOrders;
import io.github.walmart.marketplace.vo.WalmartOrdersResp;
import io.github.walmart.marketplace.dto.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

/**
 * Created by EalenXie on 2022/3/16 14:02
 * https://developer.walmart.com/api/us/mp/orders
 */
public class WalmartOrderClient extends WalmartClient {

    private final ObjectMapper mapper;

    public WalmartOrderClient(RestOperations restOperations) {
        this(new ObjectMapper(), restOperations);
    }

    public WalmartOrderClient(ObjectMapper objectMapper, RestOperations restOperations) {
        super(restOperations);
        this.mapper = objectMapper;
    }

    public WalmartOrderClient(ObjectMapper objectMapper) {
        this.mapper = objectMapper;
    }

    /**
     * https://developer.walmart.com/api/us/mp/orders#operation/getAllReleasedOrders
     * 获取订单
     */
    public WalmartOrdersResp orders(BasicInfo basicInfo, String accessToken, OrdersDTO dto) {
        HttpHeaders headers = getBasicHeaders(basicInfo.getClientId(), basicInfo.getClientSecret());
        headers.set(WM_SEC_ACCESS_TOKEN, accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> args = mapper.convertValue(dto, new TypeReference<Map<String, String>>() {
        });
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/v3/orders", isSandBoxMode() ? SANDBOX_HOST : HOST));
        LinkedMultiValueMap<String, String> req = new LinkedMultiValueMap<>();
        req.setAll(args);
        builder.queryParams(req);
        URI uri = builder.build().encode().toUri();
        return getRestOperations().exchange(uri, HttpMethod.GET, new HttpEntity<>(null, headers), WalmartOrdersResp.class).getBody();
    }

    /**
     * https://developer .walmart.com/api/us/mp/orders#operation/getAllReleasedOrders
     * 获取订单
     */
    public WalmartOrders orders(BasicInfo basicInfo, String accessToken, String nextCursor) {
        HttpHeaders headers = getBasicHeaders(basicInfo.getClientId(), basicInfo.getClientSecret());
        headers.set(WM_SEC_ACCESS_TOKEN, accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/v3/orders%s", isSandBoxMode() ? SANDBOX_HOST : HOST, nextCursor));
        URI uri = builder.build().encode().toUri();
        return getRestOperations().exchange(uri, HttpMethod.GET, new HttpEntity<>(null, headers), WalmartOrders.class).getBody();
    }


    /**
     * https://developer.walmart.com/api/us/mp/orders#operation/getAnOrder
     * 根据订单号获取订单详情
     */
    public WalmartOrder order(BasicInfo basicInfo, String accessToken, String orderId) {
        HttpHeaders headers = getBasicHeaders(basicInfo.getClientId(), basicInfo.getClientSecret());
        headers.set(WM_SEC_ACCESS_TOKEN, accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        String url = String.format("%s/v3/orders/%s", isSandBoxMode() ? SANDBOX_HOST : HOST, orderId);
        return getRestOperations().exchange(URI.create(url), HttpMethod.GET, new HttpEntity<>(null, headers), WalmartOrder.class).getBody();
    }

    /**
     * https://developer.walmart.com/api/us/mp/orders#operation/getAllReleasedOrders
     * 获取所有已下达的订单
     */
    public WalmartOrders releasedOrders(BasicInfo basicInfo, String accessToken) {
        HttpHeaders headers = getBasicHeaders(basicInfo.getClientId(), basicInfo.getClientSecret());
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(WM_SEC_ACCESS_TOKEN, accessToken);
        return getRestOperations().exchange(URI.create(String.format("%s/v3/orders/released", isSandBoxMode() ? SANDBOX_HOST : HOST)), HttpMethod.GET, new HttpEntity<>(null, headers), WalmartOrders.class).getBody();
    }

    /**
     * https://developer.walmart.com/api/us/mp/orders#operation/refundOrderLines
     * 退款
     */
    public WalmartOrder refund(String purchaseOrderId, BasicInfo basicInfo, String accessToken, RefundDTO dto) {
        HttpHeaders headers = getBasicHeaders(basicInfo.getClientId(), basicInfo.getClientSecret());
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(WM_SEC_ACCESS_TOKEN, accessToken);
        return getRestOperations().exchange(String.format("%s/v3/orders/%s/refund", isSandBoxMode() ? SANDBOX_HOST : HOST, purchaseOrderId), HttpMethod.POST, new HttpEntity<>(dto, headers), WalmartOrder.class).getBody();
    }

    /**
     * https://developer.walmart.com/api/us/mp/orders#operation/shippingUpdates
     * 标记发货
     */
    public String shipment(String purchaseOrderId, BasicInfo basicInfo, String accessToken, ShipmentDTO dto) {
        HttpHeaders headers = getBasicHeaders(basicInfo.getClientId(), basicInfo.getClientSecret());
        headers.set(WM_SEC_ACCESS_TOKEN, accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return getRestOperations().exchange(String.format("%s/v3/orders/%s/shipping", isSandBoxMode() ? SANDBOX_HOST : HOST, purchaseOrderId), HttpMethod.POST, new HttpEntity<>(dto, headers), String.class).getBody();
    }

    /**
     * https://developer.walmart.com/api/us/mp/orders#operation/acknowledgeOrders
     * 确认订单
     */
    public String acknowledge(String purchaseOrderId, BasicInfo basicInfo, String accessToken) {
        HttpHeaders headers = getBasicHeaders(basicInfo.getClientId(), basicInfo.getClientSecret());
        headers.set(WM_SEC_ACCESS_TOKEN, accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return getRestOperations().exchange(String.format("%s/v3/orders/%s/acknowledge", isSandBoxMode() ? SANDBOX_HOST : HOST, purchaseOrderId), HttpMethod.POST, new HttpEntity<>(null, headers), String.class).getBody();
    }

    /**
     * https://developer.walmart.com/api/us/mp/orders#operation/cancelOrderLines
     * 取消订单
     */
    public WalmartOrder cancel(String purchaseOrderId, BasicInfo basicInfo, String accessToken, CancelDTO dto) {
        HttpHeaders headers = getBasicHeaders(basicInfo.getClientId(), basicInfo.getClientSecret());
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(WM_SEC_ACCESS_TOKEN, accessToken);
        return getRestOperations().exchange(String.format("%s/v3/orders/%s/cancel", isSandBoxMode() ? SANDBOX_HOST : HOST, purchaseOrderId), HttpMethod.POST, new HttpEntity<>(dto, headers), WalmartOrder.class).getBody();
    }

}
