package com.sky.skyentity.bean;

public class QuerySMSSendResp {
    //请求状态码
    private String Code;
    //状态码的描述
    private String Message;
    //请求ID
    private String RequestId;
    //短信发送明细
    private QuerySMSSendDetailListResp querySMSSendDetailListResp;
    //短信发送总条数
    private String TotalCount;

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
    }

    public QuerySMSSendDetailListResp getQuerySMSSendDetailListResp() {
        return querySMSSendDetailListResp;
    }

    public void setQuerySMSSendDetailListResp(QuerySMSSendDetailListResp querySMSSendDetailListResp) {
        this.querySMSSendDetailListResp = querySMSSendDetailListResp;
    }

    public String getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(String totalCount) {
        TotalCount = totalCount;
    }
}
