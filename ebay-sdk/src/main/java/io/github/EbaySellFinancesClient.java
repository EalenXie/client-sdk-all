package io.github;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dto.EbayPageDTO;
import io.github.dto.PayoutFilterDTO;
import io.github.dto.TransactionFilterDTO;
import io.github.vo.FundsSummaryVO;
import io.github.vo.TransactionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.SortedMap;

/**
 * Created by EalenXie on 2022/3/29 11:36
 * https://developer.ebay.com/api-docs/sell/finances/static/overview.html
 */
@Slf4j
public class EbaySellFinancesClient extends EbayClient {

    private final RestOperations restOperations;


    /**
     * 沙箱环境认证接口地址
     */
    private static final String HOST_SANDBOX = "https://apiz.sandbox.ebay.com";

    /**
     * 正式环境地址
     */
    private static final String API_HOST = "https://apiz.ebay.com";


    private final ObjectMapper mapper;

    public EbaySellFinancesClient() {
        this(new RestTemplate(), new ObjectMapper());
    }

    public EbaySellFinancesClient(RestOperations restOperations, ObjectMapper objectMapper) {
        this.mapper = objectMapper;
        this.restOperations = restOperations;
    }


    /**
     * 查询交易
     * https://developer.ebay.com/api-docs/sell/finances/resources/transaction/methods/getTransactions
     */
    public TransactionVO transaction(String accessToken, EbayPageDTO page, TransactionFilterDTO filter) {
        HttpHeaders headers = getEbayHeader(accessToken);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/sell/finances/v1/transaction", isSandBox() ? HOST_SANDBOX : API_HOST));
        builder.queryParams(getQueryParams(page));
        builder.query(filterQueryString(filter));
        URI uri = builder.build().encode().toUri();
        return restOperations.exchange(uri, HttpMethod.GET, new HttpEntity<>(null, headers), TransactionVO.class).getBody();
    }

    /**
     * 查询支出 (发款记录)
     * https://developer.ebay.com/api-docs/sell/finances/resources/payout/methods/getPayout
     */
    public String payout(String accessToken, String payoutId) {
        HttpHeaders headers = getEbayHeader(accessToken);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/sell/finances/v1/payout/%s", isSandBox() ? HOST_SANDBOX : API_HOST, payoutId));
        URI uri = builder.build().encode().toUri();
        return restOperations.exchange(uri, HttpMethod.GET, new HttpEntity<>(null, headers), String.class).getBody();
    }


    /**
     * 查询支出 (发款记录)
     * https://developer.ebay.com/api-docs/sell/finances/resources/payout/methods/getPayouts
     */
    public String payouts(String accessToken, EbayPageDTO page, PayoutFilterDTO filter) {
        HttpHeaders headers = getEbayHeader(accessToken);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/sell/finances/v1/payout", isSandBox() ? HOST_SANDBOX : API_HOST));
        builder.queryParams(getQueryParams(page));
        builder.query(filterQueryString(filter));
        URI uri = builder.build().encode().toUri();
        return restOperations.exchange(uri, HttpMethod.GET, new HttpEntity<>(null, headers), String.class).getBody();
    }

    /**
     * 资金查询(余额查询)
     * 查询可用资金、正在处理的资金、暂停的资金
     * https://developer.ebay.com/api-docs/sell/finances/resources/seller_funds_summary/methods/getSellerFundsSummary
     */
    public FundsSummaryVO sellerFundsSummary(String accessToken) {
        HttpHeaders headers = getEbayHeader(accessToken);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/sell/finances/v1/seller_funds_summary", isSandBox() ? HOST_SANDBOX : API_HOST));
        URI uri = builder.build().encode().toUri();
        return restOperations.exchange(uri, HttpMethod.GET, new HttpEntity<>(null, headers), FundsSummaryVO.class).getBody();
    }


    /**
     * 将请求的DTO参数转化为 LinkedMultiValueMap 路径参数
     *
     * @param dto 请求DTO
     */
    public LinkedMultiValueMap<String, String> getQueryParams(Object dto) {
        SortedMap<String, String> sortedMap = mapper.convertValue(dto, new TypeReference<SortedMap<String, String>>() {
        });
        LinkedMultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.setAll(sortedMap);
        return queryParams;
    }

}
