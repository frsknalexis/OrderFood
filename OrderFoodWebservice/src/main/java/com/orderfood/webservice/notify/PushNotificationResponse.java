package com.orderfood.webservice.notify;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PushNotificationResponse {
    private int status;
    private String message;
}
