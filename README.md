# aiwuyu-server-sdk
## 一. 服务端SDK介绍
爱物语服务端SDK是针对爱物语服务端快速接入的工具，提供RSA、MD5加签和联合登录POJO定义。

* 联合登录交互逻辑
![Image text](https://aiwuyu-cms-prd.oss-cn-hangzhou.aliyuncs.com/Pic/sdk-doc/%E8%81%94%E5%90%88%E7%99%BB%E5%BD%95%E4%BA%A4%E4%BA%92%E9%80%BB%E8%BE%91_new.jpg)

## 二. 接入指南
* Maven 依赖 ，jar已上传中央仓库
```
<dependency>
  <groupId>com.aiwuyu.commons</groupId>
  <artifactId>aiwuyu-server-sdk</artifactId>
  <version>1.0.0</version>
</dependency>
```
* 渠道签名
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
loginDate|String|否	|登录时间	|格式(yyyy-MM-dd HH:mm:ss)

## 四.  签名方式
#### 1. 签名说明
* 确定需要签名的参数，null和空字符串不参与签名
* 签名参数进行排序，自然排序
* 以key=value形式通过&拼接参数，key=value&key2=value2
* 可以使用MD5、RDS签名算法生成签名
* 支持输入2048位和1024位的RSA私钥
* 目前仅支持PKCS8格式的私钥和X509格式公钥
* 签名仅支持字符集UTF-8

#### 2. 验证方式
```java
public class AiwuyuServerSdkTest2 {
    /** 渠道私钥 **/
    String privateKey = "渠道私钥";
    /** 渠道公钥 **/
    String publicKey = "渠道公钥";
    /** 加签类型 **/
    String signType = "RSA";

    @Test
    public void verifySign_test() {
        ChannelLoginReq channelLoginReq = new ChannelLoginReq();
        // 日期格式 yyyy-MM-dd HH:mm:ss
        channelLoginReq.setReqDate(DateFormatUtils.format(new Date(), Constants.SHARE_DEFAULT_FORMAT));
        channelLoginReq.setChannelCode("渠道CODE");
        channelLoginReq.setUid("渠道用户ID");

        // 获得验签结果
        final boolean verifySignResult =
            AiwuyuServerSdk.verifySign(channelLoginReq, signType, publicKey, Charset.defaultCharset());

        System.out.println(verifySignResult);
    }
}
```
#### 3. RSA公私钥生成方式
* 可参考支付宝生成工具(https://docs.open.alipay.com/291/106097/)

## 五.  渠道注册
#### 1. 邮件申请方式
* 将生成好的RSA公钥或者MD5密钥发送至caojiayao001@aiwuyu.com
* 邮件发送模版
```
  商户名称：xx
  渠道编号：xxchannel
  签名方式：RSA\MD5
  签名密钥：XXXXXXX
```
#### 2. 商户平台注册(敬请期待)
