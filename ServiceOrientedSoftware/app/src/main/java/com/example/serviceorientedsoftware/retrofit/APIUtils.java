package com.example.serviceorientedsoftware.retrofit;

import com.example.serviceorientedsoftware.constants.Constants;

public class APIUtils {

    public static  DataClient getData(String base_url){

        return Retrofit_Client.getClient(base_url).create(DataClient.class);

    }
}
