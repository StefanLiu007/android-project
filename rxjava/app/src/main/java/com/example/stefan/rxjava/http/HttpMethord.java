package com.example.stefan.rxjava.http;

import com.example.stefan.rxjava.entity.HttpResult;
import com.example.stefan.rxjava.entity.Subjects;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Stefan on 2016/7/17.
 */
public class HttpMethord {

    private MethordInMian methordInMian;
    private static final int TIME_OUT = 5;

    MovieService service;

    private HttpMethord() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        String baseurls = "https://api.douban.com/v2/movie/";
        Retrofit retrofit = new Retrofit.Builder()
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseurls)
                .build();
        service = retrofit.create(MovieService.class);
//        Call<MovieEntity> call = service.getTopMovie(0, 10);
//        call.enqueue(new Callback<MovieEntity>() {
//            @Override
//            public void onResponse(Call<MovieEntity> call, Response<MovieEntity> response) {
//                methordInMian.upDateUi(response.body().toString());
//            }
//
//            @Override
//            public void onFailure(Call<MovieEntity> call, Throwable t) {
//                methordInMian.upDateUi(t.getMessage());
//            }
//        });
//        service.getTopMovie(0, 10)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<MovieEntity>() {
//                    @Override
//                    public void onCompleted() {
//                        ToastUtil.showToast("完成");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        methordInMian.upDateUi(e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(MovieEntity movieEntity) {
//                        methordInMian.upDateUi(movieEntity.toString());
//                    }
//                });
    }

    public void setMethordInMian(MethordInMian methordInMian) {
        this.methordInMian = methordInMian;
    }

    private static class CreatHttp {
        private static final HttpMethord INSTENCE = new HttpMethord();
    }

    public static HttpMethord getInstence() {
        return CreatHttp.INSTENCE;
    }

    public void getMovie(Subscriber<List<Subjects>> subscriber, int start, int end) {
        service.getTopMovie(start, end)
                .map(new ResultFunc<List<Subjects>>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    private class ResultFunc<T> implements Func1<HttpResult<T>,T>{
        @Override
        public T call(HttpResult<T> tHttpResult) {
            if (tHttpResult.getCount() == 0){
                throw new ApiException(100);
            }
            return tHttpResult.getSubject();
        }
    }
}
