package com.y5neko.tiktokradar;

import com.y5neko.tiktokradar.misc.Copyright;
import com.y5neko.tiktokradar.request.GetResponse;
import org.apache.commons.cli.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws ParseException, IOException {
        // 头部
        System.out.println(Copyright.logo);

        // CLI解析
        Options options = new Options();

        options.addOption("h", "help", false, "显示帮助");
        options.addOption("e", "exec", false, "执行查询");
        options.addOption("u", "base-url", true, "指定抖音Active_Status_Baseurl(默认情况下无需重新指定)");
        options.addOption("ss", "sessionid", true, "指定登录用户的sessionid");
        options.addOption("suid", "sec_user_ids", true, "指定要查询用户的sec user id");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        // 初始化配置文件
        File configFile = new File("config.properties");
        Path configFilePath = Paths.get("config.properties");
        if (!configFile.exists()) {
            Properties initProp = new Properties();
            initProp.setProperty("Active_Status_Baseurl", "https://www.douyin.com/aweme/v1/web/im/user/active/status/?aid=6383");
            initProp.setProperty("sessionid", "");
            initProp.setProperty("sec_user_ids", "");

            OutputStream configFileOutputStream = Files.newOutputStream(configFilePath);
            initProp.store(configFileOutputStream, null);
        }


        // 获取配置文件
        Properties prop = new Properties();
        InputStream configInput = null;
        configInput = Files.newInputStream(configFilePath);
        prop.load(configInput);

        // 主要处理逻辑
        if (cmd.hasOption("h") || args.length == 0) {
            HelpFormatter helpMenu =  new  HelpFormatter();
            helpMenu.printHelp( "java TikTokRadar.jar [OPTIONS]" , options);
        } else {
            boolean overwrite = false;

            if (cmd.hasOption("u")) {
                overwrite = true;
                prop.setProperty("Active_Status_Baseurl", cmd.getOptionValue("u"));
            }

            if (cmd.hasOption("ss")) {
                overwrite = true;
                prop.setProperty("sessionid", cmd.getOptionValue("ss"));
            }

            if (cmd.hasOption("suid")) {
                overwrite = true;
                prop.setProperty("sec_user_ids", cmd.getOptionValue("suid"));
            }

            if (overwrite){
                OutputStream configFileOutputStream = Files.newOutputStream(configFilePath);
                prop.store(configFileOutputStream, null);

                System.out.println("写入配置成功");
            }
        }

        if (cmd.hasOption("e")) {
            String urlString = prop.getProperty("Active_Status_Baseurl");
            String payload = "sec_user_ids=%5B%22[TEST SUID]%22%5D&source=heartbeat".replace("[TEST SUID]", prop.getProperty("sec_user_ids"));
            String cookie = "sessionid=[TEST COOKIE]".replace("[TEST COOKIE]", prop.getProperty("sessionid"));

            String result = GetResponse.getResponse(urlString, payload, cookie);

//            HttpURLConnection connection = GetHTTPConnection.getHttpURLConnection("https://www.douyin.com/user/" + prop.getProperty("sec_user_ids"), payload, cookie);
//            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
//                StringBuilder response = new StringBuilder();
//                String responseLine;
//                while ((responseLine = br.readLine()) != null) {
//                    response.append(responseLine.trim());
//                }
//
//                System.out.println(response.toString());
//            }

            System.out.println("用户首页: " + "https://www.douyin.com/user/" + prop.getProperty("sec_user_ids"));
            System.out.println("在线时间: " + result);
        }
    }
}
