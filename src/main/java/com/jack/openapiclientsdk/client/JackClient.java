package com.jack.openapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.jack.openapiclientsdk.Utils.SignUtils;
import com.jack.openapiclientsdk.model.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

public class JackClient {

    private String accessKey;

    private String secretKey;

    public JackClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name){
        HashMap<String,Object> paramMap = new HashMap<>();
        paramMap.put("name",name);
        String result = HttpUtil.get("http://localhost:8123/aoi/name",paramMap);
        System.out.println(result);
        return result;
    }

    public String getNameByPost(@RequestParam String name){
        HashMap<String,Object> paraMap = new HashMap<>();
        paraMap.put("name",name);
        String results = HttpUtil.post("http://localhost:8123/api/name",paraMap);
        System.out.println(results);
        return results;
    }

    public Map<String ,String> getHeadMap(String body){
        Map<String ,String> hashMap = new HashMap<>();
        hashMap.put("accessKey",accessKey);
        hashMap.put("secretKey",secretKey);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        hashMap.put("body", body);
        hashMap.put("timestamp",String.valueOf(System.currentTimeMillis()/1000));
        hashMap.put("sign", SignUtils.genSign(hashMap.toString(),secretKey));
        return hashMap;
}


    public String getUserNameByPost(@RequestBody User  user){

//        String accessKey = request.getHeader("accessKey");
//        String secretKey = request.getHeader("secretKey");
//        if(!accessKey.equals("Jack")  || !secretKey.equals("abcdefg")){
//            throw new RuntimeException("无权限");
//        }
        String json = JSONUtil.toJsonStr(user);
        HttpResponse httpResponse = HttpRequest.post("http://localhost:8123/api/name/user")
                .addHeaders(getHeadMap(json))
                .body(json)
                .execute();
        System.out.println(httpResponse.getStatus());
        String result = httpResponse.body();
        return result;
    }
}
