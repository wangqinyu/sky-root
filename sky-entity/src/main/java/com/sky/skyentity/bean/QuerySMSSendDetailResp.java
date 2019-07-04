package com.sky.skyentity.bean;

public class QuerySMSSendDetailResp {
    //短信内容
    private String Content;
    //运营商短信状态码(短信发送成功：DELIVRD)
    private String ErrCode;
    //外部流水扩展字段
    private String OutId;
    //接收短信的手机号码
    private String PhoneNum;
    //短信接收日期和时间
    private String ReceiveDate;
    //短信发送日期和时间
    private String SendDate;
    //短信发送状态，包括：1：等待回执。2：发送失败。3：发送成功。
    private String SendStatus;
    //短信模板ID
    private String TemplateCode;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getErrCode() {
        return ErrCode;
    }

    public void setErrCode(String errCode) {
        ErrCode = errCode;
    }

    public String getOutId() {
        return OutId;
    }

    public void setOutId(String outId) {
        OutId = outId;
    }

    public String getPhoneNum() {
        return PhoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        PhoneNum = phoneNum;
    }

    public String getReceiveDate() {
        return ReceiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        ReceiveDate = receiveDate;
    }

    public String getSendDate() {
        return SendDate;
    }

    public void setSendDate(String sendDate) {
        SendDate = sendDate;
    }

    public String getSendStatus() {
        return SendStatus;
    }

    public void setSendStatus(String sendStatus) {
        SendStatus = sendStatus;
    }

    public String getTemplateCode() {
        return TemplateCode;
    }

    public void setTemplateCode(String templateCode) {
        TemplateCode = templateCode;
    }
}
