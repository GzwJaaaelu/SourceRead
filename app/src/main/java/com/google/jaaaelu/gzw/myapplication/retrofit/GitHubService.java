package com.google.jaaaelu.gzw.myapplication.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017/12/6.
 * 网络请求接口
 */

public interface GitHubService {

    //  官网例子
    //  @GET 表示是一个 GET 请求
    @GET("users/{user}/repos")
    //  Path 的作用是如果传递进来的 user 为空的话，就用 "user" 这个作为默认值传递进去
    Call<List<Repo>> listRepos(@Path("user") String user);

    //  括号里面的是相对 URL 地址
    @GET(".../...")
    Call<List<MyResponse>> getCall();
}
