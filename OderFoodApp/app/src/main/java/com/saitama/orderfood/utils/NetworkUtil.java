package com.saitama.orderfood.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetworkUtil {
    public static boolean isInternetAvailable() {
        try {
            InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (UnknownHostException e) {
            return false;
        }
    }
}
