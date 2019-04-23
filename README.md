# aiwuyu-server-sdk
## 一. 服务SDK介绍
爱物语服务 SDK是针对爱物语服务端快速接入的工具，提供RSA、MD5加签和渠道登录POJO定义。

* 合登录交互逻辑
![Image text](https://aiwuyu-cms-prd.oss-cn-hangzhou.aliyuncs.com/Pic/sdk-doc/%E8%81%94%E5%90%88%E7%99%BB%E5%BD%95%E4%BA%A4%E4%BA%92%E9%80%BB%E8%BE%91.jpg)

## 二. 接入指南
* Maven 依赖 ，jar已上传中央仓库
```
<dependency>
    <groupId>com.aliyun.oss</groupId>
    <artifactId>aliyun-sdk-oss</artifactId>
    <version>2.8.3</version>
</dependency>
```
* 渠道签名使用方式
```java
public class AiwuyuServerSdkTest {
    /** 渠道私钥 **/
    String privateKey = "渠道私钥";
    /** 加签类型 **/
    String signType = "RSA";
    @Test
    public void channelSign_test() {
        ChannelLoginReq channelLoginReq = new ChannelLoginReq();
        // 日期格式 yyyy-MM-dd HH:mm:ss
        channelLoginReq.setReqDate(DateFormatUtils.format(new Date(), Constants.SHARE_DEFAULT_FORMAT));
        channelLoginReq.setChannelCode("渠道CODE");
        channelLoginReq.setUid("渠道用户ID");
        
        
        // 获得渠道签名
        final String channelSignJson =
            AiwuyuServerSdk.channelSign(channelLoginReq, signType, privateKey, Charset.defaultCharset());

        System.out.println(channelSignJson);
    }
}
```


## 三.  API
#### 1. 公共参数
参数		|类型|是否必填|描述											|备注
:--		|:-- |:--|:--										|:--
reqDate|String|是	|请求时间	|格式(yyyy-MM-dd HH:mm:ss)
reqNo|String|是	|请求编号	|用于表示请求,随机UUID
merchantCode|String|否	|商户编号	|渠道所属商户,从爱物语申请
channelCode|String|是	|渠道编号	|渠道编号,从爱物语申请
signType|String|是	|签名类型	|RSA,MD5
sign|String|是	|签名	|加签内容

###### 1.1 联合登录
参数		|类型|是否必填|描述											|备注
:--		|:-- |:--|:--										|:--
uid|String|是	|渠道用户ID	|渠道第三方用户ID
loginDate|String|否	|登录时间	|
