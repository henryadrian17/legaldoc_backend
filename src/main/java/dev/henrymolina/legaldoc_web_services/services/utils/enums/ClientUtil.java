package dev.henrymolina.legaldoc_web_services.services.utils.enums;

import org.springframework.http.HttpHeaders;

import java.util.HashMap;

public class ClientUtil {


    public static HttpHeaders addHeaders(HashMap<String,String> mapHeaders){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(mapHeaders);
        return httpHeaders;
    }

}
