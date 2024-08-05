package com.example.bombisa;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("posts")  // Endpoint de la API
    Call<List<Post>> getPosts();
}
