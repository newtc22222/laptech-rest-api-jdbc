package com.laptech.restapi.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public class DataResponse extends BaseResponse {
    private Long count;
    private Object data;

    public DataResponse(HttpStatus status, String message, Object data) {
        super(status, message);
        this.count = 0L;
        this.data = data;
    }

    public DataResponse(HttpStatus status, String message, long count, Object data) {
        super(status, message);
        this.count = count;
        this.data = data;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static ResponseEntity<BaseResponse> success(String actionName) {
        HttpStatus status = (actionName.contains("Delete") || actionName.contains("Remove"))
                ? HttpStatus.NO_CONTENT
                : HttpStatus.OK;
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse(
                        status,
                        actionName + " successfully!"
                ));
    }

    public static <T> ResponseEntity<DataResponse> success(String actionName, T newObject) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new DataResponse(
                        HttpStatus.CREATED,
                        actionName + " successfully!",
                        1,
                        newObject
                ));
    }

    public static <T> ResponseEntity<DataResponse> getObjectSuccess(String actionName, T object) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new DataResponse(
                        HttpStatus.OK,
                        actionName + " successfully!",
                        1,
                        object
                ));
    }

    public static <T> ResponseEntity<DataResponse> getCollectionSuccess(String actionName, Collection<T> collection) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new DataResponse(
                        HttpStatus.OK,
                        actionName + " successfully!",
                        collection.size(),
                        collection
                ));
    }

    public static <T> ResponseEntity<DataResponse> getCollectionSuccess(String actionName, long totalRecord, Collection<T> collection) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new DataResponse(
                        HttpStatus.OK,
                        actionName + " successfully!",
                        totalRecord,
                        collection
                ));
    }
}
