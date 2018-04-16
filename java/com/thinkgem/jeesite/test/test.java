package com.thinkgem.jeesite.test;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/27.
 */
public class test {
    public static void main(String[] a) {

        /*//System.out.println(DateUtils.getDate("yyyy"));
        List<HashMap> ls=new ArrayList();
        HashMap m=new HashMap();
        m.put("VALUE",1);
        m.put("LABEL",2);
        ls.add(m);
        m=new HashMap();
        m.put("VALUE",3);
        m.put("LABEL",4);
        ls.add(m);
        Map ytList = Collections3.extractToMap(ls,"VALUE","VALUE");
        System.out.println(ytList);*/
        String serviceAddress="http://10.66.235.239:8080/a/requestb";
        String result="";
        HttpPost httpPost = new HttpPost(serviceAddress);
        CloseableHttpClient httpClient= HttpClients.custom().build();
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        HashMap h=new HashMap();

        h.put("username", "admin");
        h.put("password", "111111");
        h.put("call","getJqsq");
        h.put("params", JsonMapper.getInstance().toJson(h));
        nvps.add(new BasicNameValuePair("requestBody", JsonMapper.getInstance().toJson(h)));
        System.out.println("----------------------"+JsonMapper.getInstance().toJson(h));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpResponse resp = null;
        try {
            resp = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        httpClient.getConnectionManager().shutdown();
        if(resp.getStatusLine().getStatusCode() == 200) {
            HttpEntity he = resp.getEntity();
            try {
                result = EntityUtils.toString(he,"UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
