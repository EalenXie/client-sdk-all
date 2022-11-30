package io.github.allegro.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by EalenXie on 2022/3/18 13:30
 */
@Getter
@Setter
public class EventStats {


    @JsonProperty("latestEvent")
    private LatestEvent latestEvent;

    @NoArgsConstructor
    @Data
    public static class LatestEvent {
        /**
         * 事件编号
         */
        @JsonProperty("id")
        private String id;
        /**
         * 事件发送时间
         */
        @JsonProperty("occurredAt")
        private String occurredAt;
    }
}
