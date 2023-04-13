package cn.yvenxx.zhima_community.provider;

import cn.yvenxx.zhima_community.dto.AccessTokenDTO;
import cn.yvenxx.zhima_community.dto.GithubUser;
import com.alibaba.fastjson2.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        /**
         * 获取AccessToken
         */
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try{
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken){
        /**
         * 通过accessToken来获取用户的属性
         */
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization","token "+ accessToken)  //这里把token通过header传过去
                .build();
        try{
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser gitHubUser = JSON.parseObject(string, GithubUser.class);
            return gitHubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
