package com.y5neko.tiktokradar.request;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.y5neko.tiktokradar.misc.Tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class GetResponse {
    public static String getResponse(String urlString, String payload, String cookie){
        try {
            // 创建URL对象
            HttpURLConnection connection = GetHTTPConnection.getHttpURLConnection(urlString, payload, cookie);

            // 获取响应状态码
            int responseCode = connection.getResponseCode();
//            System.out.println("Response Code: " + responseCode);

            // 读取响应内容
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                JSONObject result = JSONObject.parseObject(response.toString());
                JSONArray dataArray = result.getJSONArray("data");
                if (dataArray != null && !dataArray.isEmpty()) {
                    JSONObject firstDataObject = dataArray.getJSONObject(0);
                    long lastActiveTime = firstDataObject.getLongValue("last_active_time");

                    return Tools.convertTimestampToDate(lastActiveTime);
                } else {
                    return result.toJSONString();
                }
            }
        } catch (Exception e) {
            System.err.println();
        }
        return "获取失败";
    }
}
