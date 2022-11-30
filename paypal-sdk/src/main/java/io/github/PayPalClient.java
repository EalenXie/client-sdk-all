package io.github;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dto.BalancesDTO;
import io.github.dto.CaptureDTO;
import io.github.dto.ReauthorizeDTO;
import io.github.dto.TransactionsDTO;
import io.github.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.SortedMap;

/**
 * Created by EalenXie on 2022/3/29 11:36
 * https://developer.paypal.com/
 */
@Slf4j
public class PayPalClient {

    private final RestOperations restOperations;

    private final ObjectMapper mapper = new ObjectMapper();
    /**
     * 是否沙箱环境
     */
    private boolean sandBox = true;
    /**
     * 正式环境接口地址
     */
    private static final String HOST = "https://api-m.paypal.com/v1";
    /**
     * 沙箱环境认证接口地址
     */
    private static final String HOST_SANDBOX = "https://api-m.sandbox.paypal.com/v1";


    public PayPalClient() {
        this(new RestTemplate());
    }

    public PayPalClient(RestOperations restOperations) {
        this.restOperations = restOperations;
    }


    public boolean isSandBox() {
        return sandBox;
    }

    public void setSandBox(boolean sandBox) {
        this.sandBox = sandBox;
    }


    public HttpHeaders getBasicHeader(String clientId, String clientSecret) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientId, clientSecret);
        return headers;
    }


    /**
     * 客户端模式获取访问令牌
     * https://developer.paypal.com/api/rest/authentication/
     *
     * @param clientId     客户端ID
     * @param clientSecret 密钥
     */
    public PayPalAccessToken clientCredentialsAccessToken(String clientId, String clientSecret) {
        HttpHeaders headers = getBasicHeader(clientId, clientSecret);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Accept-Language", "en_US");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/oauth2/token", sandBox ? HOST_SANDBOX : HOST));
        builder.queryParam("grant_type", "client_credentials");
        URI uri = builder.build().encode().toUri();
        return restOperations.exchange(uri, HttpMethod.POST, new HttpEntity<>(null, headers), PayPalAccessToken.class).getBody();
    }

    /**
     * 显示付款项目的详细信息
     * <p>
     * <p>
     * https://developer.paypal.com/docs/api/referenced-payouts/v1/#referenced-payouts_get_batch_details
     *
     * @param payoutsItemId 付款批次id
     */
    @SuppressWarnings("all")
    public ReferencedPayoutsItems clientReferencedPayoutsItems(String payoutsItemId, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        headers.set("PayPal-Partner-Attribution-Id", "bn1234");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/payments/referenced-payouts-items/%s", sandBox ? HOST_SANDBOX : HOST, payoutsItemId));
        URI uri = builder.build().encode().toUri();
        return restOperations.exchange(uri, HttpMethod.GET, new HttpEntity<>(null, headers), ReferencedPayoutsItems.class).getBody();
    }

    /**
     * 获取用户信息
     * https://developer.paypal.com/docs/api/referenced-payouts/v1/#referenced-payouts_get_batch_details
     */
    public UserInfo getUserInfo(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/identity/oauth2/userinfo?schema=paypalv1.1", sandBox ? HOST_SANDBOX : HOST));
        return restOperations.exchange(builder.build().toUri(), HttpMethod.GET, new HttpEntity<>(null, headers), UserInfo.class).getBody();
    }


    /**
     * 列出交易
     * https://developer.paypal.com/docs/api/transaction-search/v1/#transactions_get
     *
     * @param token 访问令牌
     * @param dto   请求参数封装的对象TransactionsDTO
     */
    public TransactionDetailsVO transactions(String token, TransactionsDTO dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        //访问路径
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/reporting/transactions", sandBox ? HOST_SANDBOX : HOST));
        // 设置请求参数
        setQueryParams(builder, dto);
        return restOperations.exchange(builder.build().toUri(), HttpMethod.GET, new HttpEntity<>(null, headers), TransactionDetailsVO.class).getBody();
    }

    /**
     * 列出所有余额
     * https://developer.paypal.com/docs/api/transaction-search/v1/#balances_get
     *
     * @param token 访问令牌
     * @param dto   请求参数对象
     */
    public BalancesVO balances(String token, BalancesDTO dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        //访问路径
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/reporting/balances", sandBox ? HOST_SANDBOX : HOST));
        // 设置请求参数
        setQueryParams(builder, dto);
        return restOperations.exchange(builder.build().toUri(), HttpMethod.GET, new HttpEntity<>(null, headers), BalancesVO.class).getBody();
    }

    /**
     * 设置url请求参数
     */
    public void setQueryParams(UriComponentsBuilder builder, Object dto) {
        SortedMap<String, String> sortedMap = mapper.convertValue(dto, new TypeReference<SortedMap<String, String>>() {
        });
        LinkedMultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        if (!sortedMap.isEmpty()) {
            queryParams.setAll(sortedMap);
            builder.queryParams(queryParams);
        }
    }

    /**
     * 列出参考批次付款中的项目
     * <p>
     * https://developer.paypal.com/docs/api/referenced-payouts/v1/#referenced-payouts_get_batch_details
     */
    public ResponseEntity<Payouts> clientReferencedPayouts(String payoutsBatchId, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/payments/referenced-payouts/%s", sandBox ? HOST_SANDBOX : HOST, payoutsBatchId));
        return restOperations.exchange(builder.encode().build().toUri(), HttpMethod.GET, new HttpEntity<>(null, headers), Payouts.class);
    }

    /**
     * 显示授权付款的详细信息
     * <p>
     * https://developer.paypal.com/docs/api/payments/v2/#authorizations_get
     */
    public ResponseEntity<PaymentDetails> showAuthorizedPaymentDetails(String token, String authorizationId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/v2/payments/authorizations/%s", sandBox ? HOST_SANDBOX : HOST, authorizationId));
        return restOperations.exchange(builder.encode().build().toUri(), HttpMethod.GET, new HttpEntity<>(null, headers), PaymentDetails.class);
    }

    /**
     * 捕获授权支付
     * <p>
     * https://developer.paypal.com/docs/api/payments/v2/#authorizations_capture
     */
    public ResponseEntity<PaymentDetails> captureAuthorizedPayment(String token, String authorizationId, CaptureDTO dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/v2/payments/authorizations/%s/capture", sandBox ? HOST_SANDBOX : HOST, authorizationId));
        return restOperations.exchange(builder.encode().build().toUri(), HttpMethod.POST, new HttpEntity<>(dto, headers), PaymentDetails.class);
    }

    /**
     * 通过ID重新授权已授权的PayPal账户支付
     * <p>
     * https://developer.paypal.com/docs/api/payments/v2/#authorizations_reauthorize
     */
    public ResponseEntity<PaymentDetails> reauthorizePayment(String token, String authorizationId, ReauthorizeDTO dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/v2/payments/authorizations/%s/reauthorize", sandBox ? HOST_SANDBOX : HOST, authorizationId));
        return restOperations.exchange(builder.encode().build().toUri(), HttpMethod.POST, new HttpEntity<>(dto, headers), PaymentDetails.class);
    }

    /**
     * 通过ID取消或取消已授权的付款。您不能取消已完全捕获的授权付款。
     * <p>
     * https://developer.paypal.com/docs/api/payments/v2/#authorizations_void
     */
    public ResponseEntity<Void> voidAuthorizedPayment(String token, String authorizationId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/v2/payments/authorizations/%s/void", sandBox ? HOST_SANDBOX : HOST, authorizationId));
        return restOperations.exchange(builder.encode().build().toUri(), HttpMethod.POST, new HttpEntity<>(null, headers), Void.class);
    }

    /**
     * 显示捕获付款的详细信息
     * <p>
     * https://developer.paypal.com/docs/api/payments/v2/#captures_get
     */
    public ResponseEntity<PaymentDetails> capturedPaymentDetails(String token, String captureId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/v2/payments/captures/%s", sandBox ? HOST_SANDBOX : HOST, captureId));
        return restOperations.exchange(builder.encode().build().toUri(), HttpMethod.GET, new HttpEntity<>(null, headers), PaymentDetails.class);
    }

    /**
     * 通过ID退还捕获的付款。要获得全额退款，请在JSON请求体中包含一个空有效负载。对于部分退款，在JSON请求体中包含一个金额对象。
     * <p>
     * https://developer.paypal.com/docs/api/payments/v2/#captures_refund
     */
    public ResponseEntity<PaymentDetails> refundCapturedPayment(String token, String captureId, CaptureDTO dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/v2/payments/captures/%s/refund", sandBox ? HOST_SANDBOX : HOST, captureId));
        return restOperations.exchange(builder.encode().build().toUri(), HttpMethod.POST, new HttpEntity<>(dto, headers), PaymentDetails.class);
    }

    /**
     * 通过ID退还捕获的付款。要获得全额退款，请在JSON请求体中包含一个空有效负载。对于部分退款，在JSON请求体中包含一个金额对象。
     * <p>
     * https://developer.paypal.com/docs/api/payments/v2/#refunds_get
     */
    public ResponseEntity<PaymentDetails> showRefundDetails(String token, String refundId, CaptureDTO dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/v2/payments/refunds/%s", sandBox ? HOST_SANDBOX : HOST, refundId));
        return restOperations.exchange(builder.encode().build().toUri(), HttpMethod.POST, new HttpEntity<>(dto, headers), PaymentDetails.class);
    }

}
