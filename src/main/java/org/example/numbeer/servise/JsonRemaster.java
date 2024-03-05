package org.example.numbeer.servise;

import com.google.gson.Gson;


public class JsonRemaster {
    private JsonRemaster() {
    }

    public static String gettingJsonKeyValues(String jsonResponse) {
        Gson gson = new Gson();
        InfoInResponse info;
        info = gson.fromJson(jsonResponse, InfoInResponse.class);

        return info.text;
    }
}
