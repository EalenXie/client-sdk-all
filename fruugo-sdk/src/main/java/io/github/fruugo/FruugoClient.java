package io.github.fruugo;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestOperations;

import java.util.Collections;

/**
 * @author 无敌暴龙战士
 * @since 2022/08/10 10:39
 */
public abstract class FruugoClient {
    private final RestOperations restOperations;

    protected FruugoClient(RestOperations restOperations) {
        this.restOperations = restOperations;
    }

    public RestOperations getRestOperations() {
        return restOperations;
    }

    /**
     * https://fruugo.atlassian.net/wiki/spaces/RR/pages/66158675/Requests+Calls
     */
    public static final String HOST = "https://www.fruugo.com";

    /**
     * 获取包含认证的Fruugo Bearer 标准请求头
     */
    public HttpHeaders getBearerHeaders(String accountNumber, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(accountNumber, password);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
        return headers;
    }
}
