package io.github.vo;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestOperations;

import java.util.List;

/**
 * @author EalenXie
 * @since 2022/08/05 15:50
 */
public abstract class ShopifyClient {

    private final RestOperations restOperations;

    public static final String SHOPIFY_ACCESS_TOKEN = "X-Shopify-Access-Token";


    protected ShopifyClient(RestOperations restOperations) {
        this.restOperations = restOperations;
    }

    public RestOperations getRestOperations() {
        return restOperations;
    }


    /**
     * 获取Shopify 接口地址
     * 拼接公式: https://{apiKey}:{adminAccessToken}@{hostname}/admin/api/{apiVersion}
     *
     * @param apiKey           api key
     * @param adminAccessToken admin access token
     * @param hostname         店铺host
     * @param apiVersion       接口版本
     */
    public String getShopifyApiHost(String apiKey, String adminAccessToken, String hostname, String apiVersion) {
        return String.format("https://%s:%s@%s/admin/api/%s", apiKey, adminAccessToken, hostname, apiVersion);
    }

    @Nullable
    public String getShopifyNext(HttpHeaders respHeader) {
        List<String> links = respHeader.get("link");
        if (ObjectUtils.isEmpty(links)) return null;
        for (String link : links) {
            String[] split;
            if (link.contains(",")) {
                split = link.split(",");
            } else {
                split = new String[]{link};
            }
            for (String l : split) {
                if (l.contains("rel=\"next\"")) {
                    int index = l.indexOf("<");
                    int lastIndex = l.lastIndexOf(">");
                    return l.substring(index + 1, lastIndex);
                }
            }
        }
        return null;
    }

}
