package io.github.wish;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.wish.dto.*;
import io.github.wish.vo.NameVO;
import io.github.wish.vo.WishDownloadJob;
import io.github.wish.vo.WishOrder;
import io.github.wish.vo.WishResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Created by EalenXie on 2022/2/23 9:51
 * https://china-merchant.wish.com/documentation/api/v3/reference#tag/Orders
 */
public class WishOrderClient extends WishClient {

    private final ObjectMapper mapper;

    public WishOrderClient() {
        this(new RestTemplate());
    }

    public WishOrderClient(RestOperations restOperations) {
        this(new ObjectMapper(), restOperations);
    }

    public WishOrderClient(ObjectMapper objectMapper, RestOperations restOperations) {
        super(restOperations);
        this.mapper = objectMapper;
    }


    /**
     * 刷新授权
     * 接口文档  https://www.merchant.wish.com/documentation/api/v3/reference#operation/oauthRefreshToken
     */
    public String refreshToken(String token, AuthDTO dto) {
        HttpHeaders headers = getBearerHeaders(token);
        dto.setGrantType("refresh_token");
        LinkedMultiValueMap<String, String> req = queryParam(dto);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/api/v3/oauth/refresh_token", isSandbox() ? SANDBOX_HOST : HOST)).queryParams(req);
        return getRestOperations().exchange(builder.build().encode().toUri(), HttpMethod.GET, new HttpEntity<>(null, headers), String.class).getBody();
    }


    /**
     * 获取订单详情
     * 接口文档  https://china-merchant.wish.com/documentation/api/v3/reference#operation/GetOrder
     *
     * @param orderId     订单Id
     * @param accessToken 令牌
     * @return {@link WishOrder} 订单详情
     */
    public WishResponse<WishOrder> getOrder(String orderId, String accessToken) {
        HttpHeaders headers = getBearerHeaders(accessToken);
        return getRestOperations().exchange(URI.create(String.format("%s/api/v3/orders/%s", isSandbox() ? SANDBOX_HOST : HOST, orderId)), HttpMethod.GET, new HttpEntity<>(null, headers), new ParameterizedTypeReference<WishResponse<WishOrder>>() {
        }).getBody();
    }

    /**
     * 获取订单列表
     * 接口文档  https://china-merchant.wish.com/documentation/api/v3/reference#operation/GetMultipleOrders
     *
     * @param dto         订单请求参数
     * @param accessToken 令牌
     * @return {@link WishOrder} 订单详情
     */
    public WishResponse<List<WishOrder>> getOrders(OrdersDTO dto, String accessToken) {
        HttpHeaders headers = getBearerHeaders(accessToken);
        LinkedMultiValueMap<String, String> req = queryParam(dto);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/api/v3/orders", isSandbox() ? SANDBOX_HOST : HOST)).queryParams(req);
        return getRestOperations().exchange(builder.build().encode().toUri(), HttpMethod.GET, new HttpEntity<>(null, headers), new ParameterizedTypeReference<WishResponse<List<WishOrder>>>() {
        }).getBody();
    }

    @SuppressWarnings("all")
    public LinkedMultiValueMap<String, String> queryParam(Object dto) {
        Map<String, String> args = mapper.convertValue(dto, new TypeReference<Map<String, String>>() {
        });
        LinkedMultiValueMap<String, String> req = new LinkedMultiValueMap<>();
        req.setAll(args);
        return req;
    }


    /**
     * 获取货运公司
     * 接口文档  https://china-merchant.wish.com/documentation/api/v3/reference#operation/GetShippingCarriers
     *
     * @param dto         请求参数
     * @param accessToken 令牌
     * @return {@link NameVO} 物流公司
     */
    public List<NameVO> shippingCarriers(ShippingCarriersDTO dto, String accessToken) {
        HttpHeaders headers = getBearerHeaders(accessToken);
        LinkedMultiValueMap<String, String> req = queryParam(dto);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/api/v3/shipping_carriers", isSandbox() ? SANDBOX_HOST : HOST)).queryParams(req);
        return getRestOperations().exchange(builder.build().encode().toUri(), HttpMethod.GET, new HttpEntity<>(null, headers), new ParameterizedTypeReference<List<NameVO>>() {
        }).getBody();
    }


    /**
     * 批量获取订单下载
     * 接口文档  https://china-merchant.wish.com/documentation/api/v3/reference#operation/downloadOrders
     *
     * @param dto         请求参数
     * @param accessToken 令牌
     * @return {@link WishDownloadJob} 订单下载信息
     */
    public WishDownloadJob batchDownloadOrders(OrdersDTO dto, String accessToken) {
        HttpHeaders headers = getBearerHeaders(accessToken);
        LinkedMultiValueMap<String, String> req = queryParam(dto);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/api/v3/bulk_get", isSandbox() ? SANDBOX_HOST : HOST)).queryParams(req);
        return getRestOperations().exchange(builder.build().encode().toUri(), HttpMethod.POST, new HttpEntity<>(null, headers), WishDownloadJob.class).getBody();
    }


    /**
     * 获取订单下载
     * 接口文档 https://china-merchant.wish.com/documentation/api/v3/reference#operation/getOrderDownloadJob
     *
     * @param accessToken 令牌
     * @return {@link WishDownloadJob} 订单下载信息
     */
    public WishDownloadJob batchDownloadJobStatus(String jobId, String accessToken) {
        HttpHeaders headers = getBearerHeaders(accessToken);
        return getRestOperations().exchange(String.format("%s/api/v3/bulk_get/%s", isSandbox() ? SANDBOX_HOST : HOST, jobId), HttpMethod.GET, new HttpEntity<>(null, headers), WishDownloadJob.class).getBody();
    }

    /**
     * 发货或更新跟踪订单。 此操作为异步操作。
     * 接口文档  https://china-merchant.wish.com/documentation/api/v3/reference#operation/ShipOrder
     *
     * @param orderId     订单id
     * @param dto         请求参数
     * @param accessToken 令牌
     * @return {@link WishOrder} 发货返回信息
     */
    public WishOrder shipOrder(String orderId, TrackingDTO dto, String accessToken) {
        HttpHeaders headers = getBearerHeaders(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TrackingDTO> httpEntity = new HttpEntity<>(dto, headers);
        return getRestOperations().exchange(String.format("%s/api/v3/orders/%s/tracking", isSandbox() ? SANDBOX_HOST : HOST, orderId), HttpMethod.PUT, httpEntity, WishOrder.class).getBody();
    }

    /**
     * 获取退货原因
     * 接口文档 https://china-merchant.wish.com/documentation/api/v3/reference#operation/GetValidRefundReasons
     *
     * @param orderId     订单id
     * @param accessToken 令牌
     */
    public List<String> refundReasons(String orderId, String accessToken) {
        HttpHeaders headers = getBearerHeaders(accessToken);
        return getRestOperations().exchange(String.format("%s/api/v3/orders/%s/refund_reasons", isSandbox() ? SANDBOX_HOST : HOST, orderId), HttpMethod.GET, new HttpEntity<>(null, headers), new ParameterizedTypeReference<List<String>>() {
        }).getBody();
    }

    /**
     * 取消订单
     * 接口文档  https://china-merchant.wish.com/documentation/api/v3/reference#operation/RefundOrder
     *
     * @param orderId     订单id
     * @param dto         请求参数
     * @param accessToken 令牌
     * @return {@link WishOrder} 取消订单返回结果
     */
    public WishOrder refund(String orderId, RefundDTO dto, String accessToken) {
        HttpHeaders headers = getBearerHeaders(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return getRestOperations().exchange(String.format("%s/api/v3/orders/%s/refund", isSandbox() ? SANDBOX_HOST : HOST, orderId), HttpMethod.PUT, new HttpEntity<>(dto, headers), WishOrder.class).getBody();
    }


    /**
     * 更新订单
     * 接口文档 https://china-merchant.wish.com/documentation/api/v3/reference#operation/UpdateOrder
     *
     * @param orderId     订单Id
     * @param accessToken 令牌
     * @return {@link WishOrder} 订单返回信息
     */
    public WishOrder updateLTLOrder(String orderId, UpdateLtlDTO dto, String accessToken) {
        HttpHeaders headers = getBearerHeaders(accessToken);
        return getRestOperations().exchange(URI.create(String.format("%s/api/v3/orders/%s", isSandbox() ? SANDBOX_HOST : HOST, orderId)), HttpMethod.PUT, new HttpEntity<>(dto, headers), WishOrder.class).getBody();
    }

    /**
     * 修改物流地址
     * 接口文档 https://china-merchant.wish.com/documentation/api/v3/reference#operation/ModifyAddress
     *
     * @param orderId     订单Id
     * @param accessToken 令牌
     * @return {@link WishOrder} 订单返回信息
     */
    public WishOrder modifyAddress(String orderId, ModifyAddressDTO dto, String accessToken) {
        HttpHeaders headers = getBearerHeaders(accessToken);
        return getRestOperations().exchange(URI.create(String.format("%s/api/v3/orders/%s/address", isSandbox() ? SANDBOX_HOST : HOST, orderId)), HttpMethod.PUT, new HttpEntity<>(dto, headers), WishOrder.class).getBody();
    }

}
