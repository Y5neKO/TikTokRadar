package com.y5neko.tiktokradar.request;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GetHTTPConnection {
    public static HttpURLConnection getHttpURLConnection(String urlString, String payload, String cookie) throws IOException {
        URL url = new URL(urlString);

        // 打开连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // 设置请求方法为POST
        connection.setRequestMethod("POST");

        // 设置请求头
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; utf-8");
        connection.setRequestProperty("Accept", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Referer", "https://www.douyin.com/");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36");
        connection.setRequestProperty("Cookie", cookie);
        // 发送POST请求必须设置的两个属性
        connection.setDoOutput(true);

        // 发送请求数据
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = payload.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        return connection;
    }
}
