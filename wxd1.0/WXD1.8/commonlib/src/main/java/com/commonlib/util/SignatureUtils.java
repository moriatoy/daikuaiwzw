package com.commonlib.util;


import org.apache.commons.codec.net.URLCodec;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


/**
 * Created by wangjiongye on 16/4/18.
 */
public class SignatureUtils {
    public static String doHmacSha256Signature(String secret, String input) {
        return doSignature(secret, input, "HmacSHA256");
    }

    public static String doSignature(String secret, String input,
                                     String algorithm) {
        try {
            return Base64.encodeBase64String(
                    doSignature(secret, input.getBytes("utf8"), algorithm))
                    .trim();
        } catch (Exception e) {
            return "";
        }
    }

    public static byte[] doSignature(String secret, byte[] input,
                                     String algorithm) {
        try {
            Mac mac = Mac.getInstance(algorithm);
            mac.init(new SecretKeySpec(secret.getBytes("utf8"), algorithm));
            return mac.doFinal(input);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("not support algorithm:" + algorithm);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String genSignature(String secretkey, String action,
                                      String version, String accessKeyId, String signatureMethod,
                                      String signatureVersion, Long timestamp, String requestId,
                                      Map<String, String> parameters) {
        Map<String, String> sortMap = new TreeMap<String, String>();
        sortMap.put("action", action);
        sortMap.put("version", version);
        sortMap.put("accessKeyId", accessKeyId);
        sortMap.put("signatureMethod", signatureMethod);
        sortMap.put("signatureVersion", signatureVersion);
        sortMap.put("timestamp", timestamp.toString());

        if (!StringUtil.isNullOrEmpty(requestId)) {
            sortMap.put("requestId", requestId);
        }

        for (Entry<String, String> entry : parameters.entrySet()) {
            sortMap.put(entry.getKey(), entry.getValue());
        }

        try {
            String querySource = SignatureUtils.doFormUrlEncode(sortMap)
                    .replace("+", "%20").replace("*", "%2A")
                    .replace("%7E", "~");
            String stringToSign = "POST" + "&" + SignatureUtils.encode("/") + "&"
                    + SignatureUtils.encode(querySource);
            String signStr = doHmacSha256Signature(secretkey + "&",
                    stringToSign);
            return SignatureUtils.encode(signStr);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generatePostSign(String secretkey, Map<String, String> sortMap) {

        try {
            String querySource = SignatureUtils.doFormUrlEncode(sortMap)
                    .replace("+", "%20").replace("*", "%2A")
                    .replace("%7E", "~");
            String stringToSign = "POST" + "&" + SignatureUtils.encode("/") + "&"
                    + SignatureUtils.encode(querySource);
            String signStr = doHmacSha256Signature(secretkey + "&",
                    stringToSign);
            return signStr;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String doFormUrlEncode(Map<String, String> params)
            throws UnsupportedEncodingException {
        StringBuffer buf = new StringBuffer();
        for (Entry<String, String> pair : params.entrySet()) {
            URLCodec codec = new URLCodec();
            if (pair.getKey() != null) {
                buf.append("&");
                buf.append(codec.encode(pair.getKey(), "utf-8"));
                buf.append("=");
                if (pair.getValue() != null) {
                    buf.append(codec.encode(pair.getValue(), "utf-8"));
                }
            }
        }
        if (buf.length() > 0) {
            buf.deleteCharAt(0);
        }
        return buf.toString();
    }

    public static String encode(String param)
            throws UnsupportedEncodingException {
        URLCodec codec = new URLCodec();
        return codec.encode(param, "utf-8");
    }

}
