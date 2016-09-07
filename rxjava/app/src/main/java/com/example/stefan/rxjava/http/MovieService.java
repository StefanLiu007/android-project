package com.example.stefan.rxjava.http;

import com.example.stefan.rxjava.entity.HttpResult;
import com.example.stefan.rxjava.entity.Subjects;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Stefan on 2016/7/17.
 */
public interface MovieService {
    @GET("top250")
    Observable<HttpResult<List<Subjects>>> getTopMovie(@Query("start") int start, @Query("count") int count);
}
