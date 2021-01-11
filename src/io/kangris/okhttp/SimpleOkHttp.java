package io.kangris.okhttp;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.*;
import com.google.appinventor.components.runtime.*;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

@DesignerComponent(version = 1,
    description = "Simple OkHttp" ,
    category = ComponentCategory.EXTENSION,
    nonVisible = true,
    iconName = "images/extension.png")
@SimpleObject(external = true)
@UsesPermissions(permissionNames = "android.permission.INTERNET")
@UsesLibraries(libraries = "okhttp.jar")
public class SimpleOkHttp extends AndroidNonvisibleComponent implements Component {
    private ComponentContainer container;
    private Activity activity;

    public SimpleOkHttp(ComponentContainer container) {
        super(container.$form());
        activity = container.$context();
    }

    @SimpleFunction
    public void Call(String url){
        OkHttpClient http = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        http.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                OnFailure();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String restData = response.body().string();
                OnResponse(resData);
            }

    }

    @SimpleEvent
    public void OnFailure() {
        EventDispatcher.dispatchEvent(this, "OnFailure");
    }

    @SimpleEvent
    public void OnResponse(String resData) {
        EventDispatcher.dispatchEvent(this, "OnResponse", resData);
    }
}
