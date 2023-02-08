package com.laptech.restapi.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class DataResponse extends BaseResponse {
    private Object data;

    public DataResponse(HttpStatus status, String message, Object data) {
        super(status, message);
        this.data = data;
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

    public static ResponseEntity<DataResponse> success(String actionName, Object newData) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new DataResponse(
                        HttpStatus.CREATED,
                        actionName + " successfully!",
                        newData
                ));
    }
}
