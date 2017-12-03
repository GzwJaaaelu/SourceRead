package com.google.jaaaelu.gzw.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    //  创建 OkHttp 同步方法总结
    //  1.创建 OkHttpClient 与 Request，对应下面的 1/2
    //  2.将 Request 封装成 Call 对象，对应下面的 3
    //  3.调用 Call 的 execute() 发送同步请求，对应下面的 4
    //  注意点：发送请求hour，进行进入阻塞状态，直到收到相应

    //  创建 OkHttp 异步方法总结
    //  1.创建 OkHttpClient 与 Request，对应下面的 1/2
    //  2.将 Request 封装成 Call 对象，对应下面的 3
    //  3.调用 Call 的 enqueue() 发送异步请求，对应下面的 4
    //  在对应的 Callback 做不同的处理
    //  注意点：onResponse 和 onFailure 都是在工作线程中回调的

    //  同步和异步的区别：
    //  1.发起请求的方法调用
    //  2.阻塞线程与否

    //  无论同步还是异步，总结的前两步是一样的。

    //  Dispatcher 类是非常重要的，核心之一


    //  同步请求流程与源码分析
    //  1. 还是先要创建 OkHttpClient，这时候要看 Builder 的构造
    //      (1)Dispatcher 负责 OkHttp 的分发，是直接处理还是进行缓存等待，当然同步没有太多操作，只是放在队列中
    //      (2)ConnectionPool 是连接池，客户端与服务端的连接是可以想为一个 Connection，而每一个 ConnectionPool
    //       都会放在连接池中由它来进行统一的管理，如果当请求的是同一个 URL 时，可以选择复用
    //      (3)Builder 创建模式，完成了整个对象属性的创建
    //  2.构建携带请求信息的 Request 对象
    //  3.call.newCall(request) 创建 Call 对象
    //  4.调用 call.execute()
    //      (1)先判断是否执行过，同一个 Http 请求只能请求一次
    //      (2)开启监听事件
    //      (3)client.dispatcher().executed(this) 将请求添加到队列中
    //          Dispatcher 作用：维持 Call 请求发给它的一些状态，维护一个执行网络请求的线程池，把任务推到队列中去，还做了同村同步请求，移除同步请求
    //      (4)调用 getResponseWithInterceptorChain()，拦截器链的方法，这里面依次调用拦截器进行不同的操作
    //      (5)finally 中调用 client.dispatcher().finished(this)，回收 Http 请求
    //  总结：
    //      1.创建 OkHttpClient
    //      2.构建 Request，通过 OkHttpClient 和 Request 对象，构建出 Call 对象
    //      3.调用 call.execute()，最重要就是 Dispatcher

    private OkHttpClient mHttpClient;
    private static final String TAG = "HTTP";

    public static Intent createIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initHttpClient();
    }

    private void initHttpClient() {
        //  创建 OkHttp 请求
        //  1.创建 OkHttpClient，表示请求客户端类
        //  核心地位，很多功能都需要它来实现或者转发
        //  通过 Builder 模式来设置各种参数
        mHttpClient = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .build();

        //  开启缓存功能
        OkHttpClient client = new OkHttpClient
                .Builder()
                .cache(new Cache(new File("cache"), 24 * 1024 * 1024)).build();
    }

    /**
     * 同步请求
     *
     * @param view View 点击事件
     */
    public void request(View view) {
//        requestBySync();
        requestByASync();
    }

    /**
     * 同步请求
     */
    public void requestBySync() {
        //  2.创建 Request，也就是请求，包括请求报文的信息，请求头等等
        Request request = new Request.Builder().url("http://www.baidu.com")
                .get().build();
        //  3.创建 Call 对象，它代表一个 Http 请求
        //  把 Call 当做链接 Request 和 Response 的一个桥梁
        //  同步异步的分水岭，因为前三步都是一样的，第四步才有了区别
        Call call = mHttpClient.newCall(request);
        try {
            //  4.获取 Response，相应报文的信息
            //  call.execute() 这个调用的方式是同步的
            Response response = call.execute();
            Log.e(TAG, response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步请求
     */
    public void requestByASync() {
        //  2.创建 Request，也就是请求，包括请求报文的信息，请求头等等
        Request request = new Request.Builder().url("http://www.baidu.com")
                .get().build();
        //  3.创建 Call 对象，它代表一个 Http 请求
        //  把 Call 当做链接 Request 和 Response 的一个桥梁
        //  同步异步的分水岭，因为前三步都是一样的，第四步才有了区别
        Call call = mHttpClient.newCall(request);
        //  4.获取 Response，相应报文的信息
        //  call.enqueue() 这个调用的方式是异步的
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "onFailure");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.e(TAG, response.body().string());
            }
        });
    }
}
