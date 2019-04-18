package com.aiwuyu.commons.util;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.aiwuyu.commons.Constants;
import com.aiwuyu.commons.Signature;

/**
 * <P> 请求签名 <P>
 * @author caojiayao 
 * @version $Id: DigestUtil.java, v 0.1 2019年4月17日 下午8:16:33 caojiayao Exp $
 */
public enum SignUtil implements Signature {
    RSA("RSA") {

        /**
         * 签名字符串
         *
         * @param content 需要签名的字符串
         * @param privateKey 私钥(BASE64编码)
         * @param charset 编码格式
         * @return 签名结果(BASE64编码)
         * @throws Exception
         */
        @Override
        public String sign(final String content, final String privateKey, final Charset charset) throws Exception {
            try {
                PrivateKey priKey = getPrivateKeyFromPKCS8("RSA", new ByteArrayInputStream(privateKey.getBytes()));

                java.security.Signature signature = java.security.Signature.getInstance("SHA1WithRSA");

                signature.initSign(priKey);

                if (charset == null) {
                    signature.update(content.getBytes());
                } else {
                    signature.update(content.getBytes(charset));
                }

                byte[] signed = signature.sign();

                return new String(Base64.encodeBase64(signed));
            } catch (InvalidKeySpecException ie) {
                throw new RuntimeException("RSA私钥格式不正确，请检查是否正确配置了PKCS8格式的私钥", ie);
            } catch (Exception e) {
                throw new RuntimeException("RSAcontent = " + content + "; charset = " + charset, e);
            }
        }

        /**
         * 签名字符串
         *
         * @param content 需要签名的字符串
         * @param privateKey 私钥(BASE64编码)
         * @param charset 编码格式
         * @return 签名结果(BASE64编码)
         * @throws Exception
         */
        @Override
        public Map<String, Object> sign(Map<String, Object> contextMap, String prikey, Charset charset)
            throws Exception {

            final String context = getSignContent(contextMap);

            final String sign = sign(context, prikey, charset);

            contextMap.put("sign", sign);
            contextMap.put("signType", this.name());
            return contextMap;
        }

        /**
         * 签名字符串
         *
         * @param text 需要签名的字符串
         * @param sign 客户签名结果
         * @param key 公钥(BASE64编码)
         * @param input_charset 编码格式
         * @return 验签结果
         * @throws Exception
         */
        @Override
        public boolean verify(final String content, final String sign, final String publicKey, final Charset charset)
            throws Exception {
            try {
                PublicKey pubKey = getPublicKeyFromX509("RSA", new ByteArrayInputStream(publicKey.getBytes()));

                java.security.Signature signature = java.security.Signature.getInstance("SHA1WithRSA");

                signature.initVerify(pubKey);

                if (charset == null) {
                    signature.update(content.getBytes());
                } else {
                    signature.update(content.getBytes(charset));
                }

                return signature.verify(Base64.decodeBase64(sign.getBytes()));
            } catch (Exception e) {
                throw new RuntimeException("RSAcontent = " + content + ",sign=" + sign + ",charset = " + charset, e);
            }
        }

        /* (non-Javadoc)
         * @see com.Aiwuyu.commons.lang.Digest#verify(java.util.Map, java.lang.String, java.lang.String, java.nio.charset.Charset)
         */
        @Override
        public boolean verify(Map<String, Object> contextMap, String sign, String pubkey, Charset charset)
            throws Exception {

            contextMap.remove("sign");
            contextMap.remove("signType");

            final String context = getSignContent(contextMap);

            return verify(context, sign, pubkey, charset);
        }

    },
    MD5("MD5") {

        /**
         * MD5加密
         * 
         * @param text
         * @param key
         * @param charset
         * @return 签名结果
         * @throws Exception
         */
        @Override
        public String sign(final String text, final String key, final Charset charset) throws Exception {
            return DigestUtils.md5Hex(getContentBytes(text + key, charset));
        }

        /* (non-Javadoc)
         * @see com.Aiwuyu.commons.lang.Digest#sign(java.util.Map, java.lang.String, java.nio.charset.Charset)
         */
        @Override
        public Map<String, Object> sign(Map<String, Object> contextMap, String prikey, Charset charset)
            throws Exception {

            final String context = getSignContent(contextMap);

            final String sign = sign(context, prikey, charset);

            contextMap.put("sign", sign);
            contextMap.put("signType", this.name());
            return contextMap;
        }

        /**
         * MD5校验
         * 
         * @param text
         * @param publicKey
         * @param publicKey
         * @param charset
         * @return 验签结果
         * @throws Exception
         */
        @Override
        public boolean verify(final String text, final String sign, final String key, final Charset charset)
            throws Exception {
            return StringUtils.equals(sign, DigestUtils.md5Hex(getContentBytes(text + key, charset)));
        }

        /* (non-Javadoc)
         * @see com.Aiwuyu.commons.lang.Digest#verify(java.util.Map, java.lang.String, java.lang.String, java.nio.charset.Charset)
         */
        @Override
        public boolean verify(Map<String, Object> contextMap, String sign, String pubkey, Charset charset)
            throws Exception {

            contextMap.remove("sign");
            contextMap.remove("signType");

            final String context = getSignContent(contextMap);

            return verify(context, sign, pubkey, charset);
        }

    },;

    /**
     * 
     * @param sortedParams
     * @return
     */
    public String getSignContent(Map<String, Object> sortedParams) {
        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList<String>(sortedParams.keySet());
        Collections.sort(keys);
        int index = 0;
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            Object value = sortedParams.get(key);
            if (StringUtils.isNotEmpty(key) && value != null) {
                content.append((index == 0 ? "" : "&") + key + "=" + getValue(value));
                index++;
            }
        }
        return content.toString();
    }
    
    private String getValue(Object value) {
        if(value instanceof Date) {
            return DateFormatUtils.format((Date)value, Constants.SHARE_DEFAULT_FORMAT);
        } else {
            return value.toString();
        }
    }

    public PrivateKey getPrivateKeyFromPKCS8(String algorithm, InputStream ins) throws Exception {
        if (ins == null || StringUtils.isEmpty(algorithm)) {
            return null;
        }

        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

        byte[] encodedKey = IOUtils.toByteArray(ins);

        encodedKey = Base64.decodeBase64(encodedKey);

        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
    }

    public PublicKey getPublicKeyFromX509(String algorithm, InputStream ins) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

        StringWriter writer = new StringWriter();
        IOUtils.copy(new InputStreamReader(ins), writer);

        byte[] encodedKey = writer.toString().getBytes();

        encodedKey = Base64.decodeBase64(encodedKey);

        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }

    private String code;

    /**
     * 获取加密正文byte数据
     * 
     * @param content
     * @param charset
     * @return
     * @throws UnsupportedEncodingException
     */
    byte[] getContentBytes(final String content, final Charset charset) throws UnsupportedEncodingException {
        return null == charset ? content.getBytes() : content.getBytes(charset);
    }

    private SignUtil(final String code) {
        this.code = code;
    }

    /**
     * @param code
     * @return
     */
    public final static Signature byCode(final String code) {
        if (isBlank(code)) {
            return null;
        }
        for (final SignUtil digest : values()) {
            if (equalsIgnoreCase(digest.code, code)) {
                return digest;
            }
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        String context = "abcdefdsd";

        String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAOa+ufCJYPp/FFeV"
            + "Ylf7SfeB3k+Vc0kaqMnEnEQf6YMz8zw6tRYncA/PCxu+zNoSsedJbATp1RBS0UhY"
            + "T9XaqAPvqCcrSvJfeRjyjVDI9vWKDj6sy45zcVxBisytzLt6P/bTGdSL/L7Hcrs4"
            + "vZkBm2zHXa9ytYjol6nrVoTL+YjdAgMBAAECgYEAzKNVsDeGJGJTniFD6JdZ0AID"
            + "MT1v0uOr0dVmaRyMMen+y+mc9BrhzYNnbKHYd4ag9GgvPmRKq4FnTevUUerrmXLm"
            + "Pb6pT1q/znExpNvGzvzj/9xDrIic7LCtglHR8QijczNL/lqyt1DMeOF6w7gBDR7H"
            + "SAbiHwiYc6op0WlJvtkCQQD3SIA5luAt1FsFyQBMi4Mht/lYKwAkHZM+dnwEGxkz"
            + "IYZh5uGxBuKJ0GhNH97D/1v/oxGN7ED1Ts4gihXO3nQTAkEA7uD73kiZutSmK9bd"
            + "tODgh+nB7vtEnu/nAjTVb5jVKAeUl10H8zB8CggUJ/NvMWNZDyss4/CfZ0sYTIOj"
            + "Ky5NTwJBAJI9uEdWqQGxgpsrQhgCsexxBu9TjuPM8dWQ8Mrk08RfYT/o8TRmY/co"
            + "32YtIOs8y8WlRK5wOAJbNqAbycXr3S0CQQDDDVB8A5oLVSHOgCcOT+Tm9beWNPOm"
            + "N8vQIGX7NrcudWoeYidgqsFNy6TxrjOoBzAsU5vXD56JUGcp6v7r/mW/AkAWawKv"
            + "KGPJxDgq48X8bmJNLV0YFOwjcpDdQ6/GQHYuVEfyQ7IgkhJDWn/T6IaZ4uIbhAg3" + "m3wn0j+Ss0eTXv+Q";

        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDmvrnwiWD6fxRXlWJX+0n3gd5P"
            + "lXNJGqjJxJxEH+mDM/M8OrUWJ3APzwsbvszaErHnSWwE6dUQUtFIWE/V2qgD76gn"
            + "K0ryX3kY8o1QyPb1ig4+rMuOc3FcQYrMrcy7ej/20xnUi/y+x3K7OL2ZAZtsx12v" + "crWI6Jep61aEy/mI3QIDAQAB";

        String sign = SignUtil.RSA.sign(context, privateKey, Charset.defaultCharset());

        System.out.println("sign:" + sign);

        boolean verify = SignUtil.RSA.verify(context, sign, publicKey, Charset.defaultCharset());

        System.out.println("verify:" + verify);
    }

}
