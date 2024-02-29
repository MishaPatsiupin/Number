package org.example.numbeer.server;

import com.google.gson.Gson;


public class JsonRemaster {
    private JsonRemaster() {
    }

    public static String gettingJsonKeyValues(String jsonResponse) {
        Gson gson = new Gson();
        InfoInJson info;
        info = gson.fromJson(jsonResponse, InfoInJson.class);

        return info.text;
    }
}
