package com.google.jaaaelu.gzw.myapplication.retrofit;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.jaaaelu.gzw.myapplication.R;
import com.google.jaaaelu.gzw.myapplication.retrofit.proxy.dynamicProxy.Man;
import com.google.jaaaelu.gzw.myapplication.retrofit.proxy.dynamicProxy.Proxy;
import com.google.jaaaelu.gzw.myapplication.retrofit.proxy.dynamicProxy.Subject;
import com.google.jaaaelu.gzw.myapplication.retrofit.proxy.staticProxy.AbstractObject;
import com.google.jaaaelu.gzw.myapplication.retrofit.proxy.staticProxy.ProxyObject;
import com.google.jaaaelu.gzw.myapplication.retrofit.proxy.staticProxy.RealObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends AppCompatActivity {
    //  Retrofit 并不是网络请求框架，只是对网络请求框架的封装

    //  App 应用程序通过 Retrofit 请求网络，实际上是使用 Retrofit 接口层封装请求参数，之后有 OkHttp
    //  完成后续请求操作
    //  在服务端返回数据之后，OkHttp 将原始的结果交给 Retrofit，Retrofit 根据用户需求对结果进行解析

    //  使用七个步骤
    //  1.添加依赖与网络权限
    //  2.创建接受服务器返回类型的 Bean 类型（也就是 MyResponse 和 Repo）
    //  3.创建描述网络请求的接口（也就是 GitHubService），是通过注解来描述和配置的，内部机制是通过动
    //  态代理，来将我们接口的注解翻译成一个个 Http 请求，且接口中每个方法参数都必须要使用注解，否则
    //  会报错
    //  4.创建一个 Retrofit 实例，而请求的实际地址也就是设置的 baseUrl 再加上注解中的地址，
    //  5.创建网络请求接口实例（也就是 GitHubService 实例）,并获取 Call 对象实例
    //  6.调用 Call 对象的同步或者异步请求
    //  7.处理服务返回的数据
    
    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, RetrofitActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
    }

    public void netRequestWithRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                //  实际地址是 baseUrl 拼接上注解中的地址
                .baseUrl("https://api.github.com/")
                //  数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                //  网络请求适配器（可以使用 Android 默认的）
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        //  通过 Retrofit 的 create() 来创建 GitHubService
        //  这里使用了外观模式和动态代理模式
        GitHubService service = retrofit.create(GitHubService.class);
        Call repos = service.listRepos(null);
        //  同步方法
//        repos.execute();
        //  异步方法
        repos.enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                response.isSuccessful();
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t) {

            }
        });
    }

    public void request(View view) {
        testDynamicProxy();
    }

    private void testStaticProxy() {
        AbstractObject proxy = new ProxyObject(new RealObject());
        proxy.operation();
    }

    private void testDynamicProxy() {
        Subject man = new Man();
        Proxy p = new Proxy(man);
        //  通过 java.lang.reflect.Proxy.newProxyInstance(...) 方法来获得真实对象的代理对象
        //  反射中的 Proxy 中有一个 InvocationHandler 的对象，也就是这时把连个对象关联起来
        Subject subject = (Subject) java.lang.reflect.Proxy.newProxyInstance(
                //  被代理类的类加载器
                man.getClass().getClassLoader(),
                //  被代理实现的接口
                man.getClass().getInterfaces(),
                //  传入 InvocationHandler，这里关联
                p);
        //  通过代理对象调动真实对象相关接口中的实现方法，这个时候就会跳转到这个代理对象所关联的
        //  InvocationHandler 的 invoke(...) 方法
        subject.shopping("Gzw");
        subject.shopping("Jaaaelu");
        //  获得真实对象的代理对象所对应的 Class 对象的名称，用字符串表示
        System.out.println(subject.getClass().getName());
    }
}
