package com.example.sdk.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.sdk.bean.form.PageQueryForm;
import com.example.sdk.bean.vo.GS.Credit;
import com.example.sdk.bean.vo.GS.GS;
import com.example.sdk.utils.HttpUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

/**
 * 用来获取碳中和项目的服务
 * Created at 2022/9/9 9:43
 * Author: @Qi Long
 * email: 592918942@qq.com
 */
@Service
public class GSService {

  public Credit getCreditByCreditId(BigInteger creditId) throws IOException {
    JSONObject creditJson = getCredit(creditId);
    Credit credit = new Credit();
    JSONObject projectAbstract = creditJson.getJSONObject("project");
    credit.setCreditId(creditId);
    BigInteger projectId = projectAbstract.getBigInteger("id");
    credit.setProjectId(projectId);
    BigInteger gsId = getProject(projectId).getBigInteger("sustaincert_id");
    credit.setGsId(gsId);
    credit.setNumberOfCredits(creditJson.getBigInteger("number_of_credits"));
    credit.setStartingCreditNumber(creditJson.getString("starting_credit_number"));
    credit.setEndingCreditNumber(creditJson.getString("ending_credit_number"));
    credit.setSerialNumber(creditJson.getString("serial_number"));
    credit.setBatchNumber(creditJson.getString("batch_number"));
    credit.setCreatedAt(creditJson.getString("created_at"));
    credit.setUpdatedAt(creditJson.getString("updated_at"));
    return credit;
//    project.put("summary",getProjectCreditsSummaryInfo(projectId));
  }

//  https://api.goldstandard.org/projects/1798/credits?page=1&size=25&issuances=true
  public JSONArray getCreditsByGsId(PageQueryForm<BigInteger> gsIdPageQuery) throws IOException {

    GS gsByGsId = getGsByGsId(gsIdPageQuery.getParam());
    BigInteger projectId = gsByGsId.getProjectId();
    HttpUtils.MyHttpRequest requset = new HttpUtils().builder()
            .setHost("api.goldstandard.org")
            .addPathSegment("projects")
            .addPathSegment(String.valueOf(projectId))
            .addPathSegment("credits")
            .addQueryParameter("page", String.valueOf((int) (gsIdPageQuery.getSkip() / gsIdPageQuery.getLimit()) + 1))
            .addQueryParameter("size", String.valueOf(gsIdPageQuery.getLimit())).build();
    JSONArray jsonArray = requset.sendAndGet(JSONArray.class);
    return jsonArray;
//    project.put("summary",getProjectCreditsSummaryInfo(projectId));
  }



//  https://api.goldstandard.org/credits/710
  public JSONObject getCredit(BigInteger creditId) throws IOException {
    HttpUtils.MyHttpRequest credits = new HttpUtils().builder()
            .setHost("api.goldstandard.org")
            .addPathSegment("credits")
            .addPathSegment(String.valueOf(creditId)).build();
    JSONObject jsonObject = credits.sendAndGet();
    return jsonObject;
  }
// https://api.goldstandard.org/projects/1798
  public JSONObject getProject(BigInteger projectId) throws IOException {
    HttpUtils.MyHttpRequest projects = new HttpUtils().builder()
            .setHost("api.goldstandard.org")
            .addPathSegment("projects")
            .addPathSegment(String.valueOf(projectId)).build();
    JSONObject jsonObject = projects.sendAndGet();
    return jsonObject;
  }
//  https://api.goldstandard.org/projects?query=2990&page=1&size=25&sortColumn=&sortDirection=
  public GS getGsByGsId(BigInteger gsid) throws IOException {
    HttpUtils.MyHttpRequest requset = new HttpUtils().builder()
            .setHost("api.goldstandard.org")
            .addPathSegment("projects")
            .addQueryParameter("query", String.valueOf(gsid)).build();
    JSONArray jsonArray = (JSONArray) requset.sendAndGet(JSONArray.class);
    JSONObject jsonObject = jsonArray.getJSONObject(0);
    GS gs = new GS();
    BigInteger projectId = jsonObject.getBigInteger("id");
    gs.setCreatedAt(jsonObject.getString("created_at"));
    gs.setUpdatedAt(jsonObject.getString("updated_at"));
    gs.setProjectId(projectId);
    gs.setGsId(gsid);
    JSONArray summaryInfo = getProjectCreditsSummaryInfo(projectId);
    gs.setProduct(summaryInfo);
    gs.setCredits(null);
    return gs;
  }

//  https://api.goldstandard.org/projects/1798/credits/summary
  public JSONArray getProjectCreditsSummaryInfo(BigInteger projectId) throws IOException {
    HttpUtils.MyHttpRequest projects = new HttpUtils().builder()
            .setHost("api.goldstandard.org")
            .addPathSegment("projects")
            .addPathSegment(String.valueOf(projectId))
            .addPathSegment("credits")
            .addPathSegment("summary").build();
    JSONArray jsonArray = (JSONArray) projects.sendAndGet(JSONArray.class);
    return jsonArray;
  }
}
