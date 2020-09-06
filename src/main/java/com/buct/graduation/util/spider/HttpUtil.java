package com.buct.graduation.util.spider;

import com.buct.graduation.model.spider.IpPort;
import com.buct.graduation.util.GlobalName;
import com.buct.graduation.util.LogUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HttpUtil {
    public static String tempPath = "/html";
    public static void main(String[] args) throws Exception {
        HttpUtil util = new HttpUtil();
        String html = util.getHtml("https://webapi.fenqubiao.com/api/user/search?year=2015&keyword=IEEE&user=BUCT_admin&password=1204705");
        if(html == null || html.equals(""))
            return;
        System.out.println(html);
    }

    public String getHtmlOld(String url){
        System.out.println("visit "+url);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        RequestConfig defaultConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
        httpGet.setConfig(defaultConfig);
        try{
            response = httpClient.execute(httpGet);
            InputStream in = response.getEntity().getContent();
            InputStreamReader reader = new InputStreamReader(in, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            String json = null;
            while ((json = bufferedReader.readLine()) != null){
                sb.append(json);
            }
            bufferedReader.close();
            reader.close();
            in.close();
            //conn.disconnect()
            String html = sb.toString();
            //log(u + ":"+html+"\n\t");
            response.close();
            httpClient.close();
            return html;
        }catch (IOException e){
            e.printStackTrace();
        }
        return "";
    }

    public static String logFile = "htmllog.txt";
    public static void logtest(Object obj){
        PrintWriter out = null;
        try{
            out = new PrintWriter(new FileWriter(logFile, true));
        }catch (IOException e){
            e.printStackTrace();
        }
        out.append(obj.toString());
        out.close();
    }

    public static void writeFile(String content, String path){
//        try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)))){
//            bw.write(content);
//            bw.flush();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }

    /**
     * doGet
     * @param url
     * @return
     */
    public String getHtml(String url){
        System.out.println("start visit:"+url);
        if(url.startsWith("http://www.letpub.com")){
            try {
                Random random = new Random();
                Thread.sleep(1000*random.nextInt(3) + 3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        try {
//            url = URLEncoder.encode(url, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        CloseableHttpClient httpClient = HttpClients.createDefault();
//        // 使用代理 IP ✔
//        HttpHost proxy = new HttpHost("106.54.100.69", 8888);

        RequestConfig defaultConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
        HttpGet request = new HttpGet(url);
        request.setConfig(defaultConfig);
        // 模拟浏览器 ✔
        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");
        CloseableHttpResponse response = null;
        String result = "";
        try {
            response = httpClient.execute(request);
            HttpEntity entry = response.getEntity();
            int code = response.getStatusLine().getStatusCode();
            System.out.println("result code:" + code + "  visit:"+url);
            result = EntityUtils.toString(entry);
            LogUtil.getInstance().addLog("log-访问"+url+"成功");
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.getInstance().addLog("error-访问"+url+"失败");
            return null;
        }
        HttpClientUtils.closeQuietly(response);
        HttpClientUtils.closeQuietly(httpClient);
        return result;
    }

    public String getHtmlWithIp(String url){
//        if(url.startsWith("http://www.letpub.com")){
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        IpPort ip = IpPoolUtil.getIP();
//        ip.setPort(81);
//        ip.setIp("101.4.136.34");
        System.out.println("times"+ip.times+" ip"+ip.getIp()+" start visit:"+url);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 使用代理 IP ✔
        HttpHost proxy = new HttpHost(ip.getIp(), ip.getPort());
        RequestConfig defaultConfig = RequestConfig.custom().
                setCookieSpec(CookieSpecs.STANDARD)
                .setConnectTimeout(10000) // 设置连接超时时间 10秒钟
                .setSocketTimeout(30000) // 设置读取超时时间10秒钟
                .setProxy(proxy).build();
        HttpGet request = new HttpGet(url);
        request.setConfig(defaultConfig);
        // 模拟浏览器 ✔
        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");

         //模拟浏览器 ✔
//        request.setHeader("User-Agent",
//                "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");

        CloseableHttpResponse response = null;
        String result = "";
        try {
            response = httpClient.execute(request);
            HttpEntity entry = response.getEntity();
            int code = response.getStatusLine().getStatusCode();
            System.out.println("result code:" + code + "  visit:"+url);
            if(code != 200){
                ip.setStatus(GlobalName.IP_OFFLINE);
                return getHtmlWithIp(url);
            }
            result = EntityUtils.toString(entry);
            ip.setStatus(GlobalName.IP_FREE);
//            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("http not work");
            ip.setStatus(GlobalName.IP_OFFLINE);
            ip.notwork();//badTimes++
            LogUtil.getInstance().addLog("error-代理ip"+ip.getIp()+":"+ip.getPort()+"访问"+url+"失败");
            return getHtmlWithIp(url);
        } finally {
            IpPoolUtil.releaseIP(ip);
        }
        HttpClientUtils.closeQuietly(response);
        HttpClientUtils.closeQuietly(httpClient);
        LogUtil.getInstance().addLog("logs-代理ip"+ip.getIp()+":"+ip.getPort()+"访问"+url+"成功");
        return result;
    }

    public String doPostForm(String url) throws IOException {
        System.out.println("visit "+url);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);
        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("orgId", "b11647e8-0e36-44fd-84fa-262c4fcfbe43"));
        list.add(new BasicNameValuePair("instanceId", "1b0acfbb-d7f2-4f0d-a026-734c686ee4ba"));
        request.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
        CloseableHttpResponse response = httpClient.execute(request);
        HttpEntity entry = response.getEntity();
        int code = response.getStatusLine().getStatusCode();
        System.out.println("\tcode:" + code);
        String result = EntityUtils.toString(entry);
        HttpClientUtils.closeQuietly(response);
        HttpClientUtils.closeQuietly(httpClient);
        return result;
    }

    public String doPostJSON() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost("https://xingzhu-song.chinacloudsites.cn/kvMapping/valid?orgId=b11647e8-0e36-44fd-84fa-262c4fcfbe43");
        String body = "{" +
                "  \"serviceId\": \"pip\",\n" +
                "  \"key\": \"pipDnsLabel\",\n" +
                "  \"value\": \"abcdefg\",\n" +
                "  \"dependenceOn\": {\"pipLocation\":\"chinanorth\"}\n" +
                "}";
        request.setEntity(new StringEntity(body, "UTF-8"));
        request.setHeader("content-type", "application/json");
        CloseableHttpResponse response = httpClient.execute(request);
        HttpEntity entry = response.getEntity();
        int code = response.getStatusLine().getStatusCode();
        System.out.println("\tcode:" + code);
        String result = EntityUtils.toString(entry);
//        System.out.println("\tresult:" + result);
        HttpClientUtils.closeQuietly(response);
        HttpClientUtils.closeQuietly(httpClient);
        return result;
    }

    public void testUrl() throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder("https://xingzhu-song.chinacloudsites.cn");
        uriBuilder.setPath("kvMapping");
        uriBuilder.setCharset(Charset.forName("UTF-8"));
        uriBuilder.setParameter("key1", "value1");
        uriBuilder.setParameter("key2", "value2");
        //uriBuilder.setUserInfo("username","password"); //https://username:password@xingzhu-song.chinacloudsites.cn/kvMapping?key1=value1&key2=value2
        URI uri = uriBuilder.build();
        System.out.println(uri);//https://xingzhu-song.chinacloudsites.cn/kvMapping?key1=value1&key2=value2
    }
}
