package com.codepath.nytimessearch1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramyarao on 6/24/16.
 */
public class ArticleResponse {
    List<Response> responses;
    public ArticleResponse() {
        responses = new ArrayList<Response>();
    }

    public static ArticleResponse parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        ArticleResponse articleResponse = gson.fromJson(response, ArticleResponse.class);
        return articleResponse;
    }
}
