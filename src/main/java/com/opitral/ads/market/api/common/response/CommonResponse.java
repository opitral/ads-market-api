package com.opitral.ads.market.api.common.response;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CommonResponse {
    private CommonResponse() {}

    public static final class PageListMapResponse extends Response<PageResult<Map<String, Object>>> {
        public PageListMapResponse() {}

        public static PageListMapResponse of(List<Map<String, Object>> list, long total) {
            return of(new PageResult<>(list, total));
        }

        public static PageListMapResponse of(PageResult<Map<String, Object>> result) {
            PageListMapResponse response = new PageListMapResponse();
            response.setResult(result);
            return response;
        }
    }

    public static final class ListMapResponse extends Response<List<Map<String, Object>>> {
        public ListMapResponse() {}

        public static ListMapResponse of(List<Map<String, Object>> i) {
            ListMapResponse response = new ListMapResponse();
            response.setResult(i);
            return response;
        }
    }

    public static final class MapResponse extends Response<Map<String, Object>> {
        public MapResponse() {}

        public static MapResponse of(Map<String, Object> i) {
            MapResponse response = new MapResponse();
            response.setResult(i);
            return response;
        }
    }

    public static final class BooleanResponse extends Response<Boolean> {
        public BooleanResponse() {}

        public static BooleanResponse of(Boolean i) {
            BooleanResponse response = new BooleanResponse();
            response.setResult(i);
            return response;
        }
    }

    public static final class StringResponse extends Response<String> {
        public StringResponse() {}

        public static StringResponse of(String s) {
            StringResponse response = new StringResponse();
            response.setResult(s);
            return response;
        }
    }

    public static final class LongResponse extends Response<Long> {
        public LongResponse() {}

        public static LongResponse of(Long i) {
            LongResponse response = new LongResponse();
            response.setResult(i);
            return response;
        }
    }

    public static final class UUIDResponse extends Response<UUID> {
        public UUIDResponse() {}

        public static UUIDResponse of(UUID i) {
            UUIDResponse response = new UUIDResponse();
            response.setResult(i);
            return response;
        }
    }

    public static final class IntegerResponse extends Response<Integer> {
        public IntegerResponse() {}

        public static IntegerResponse of(Integer i) {
            IntegerResponse response = new IntegerResponse();
            response.setResult(i);
            return response;
        }
    }
}
