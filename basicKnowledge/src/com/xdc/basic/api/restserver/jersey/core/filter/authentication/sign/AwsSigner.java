package com.xdc.basic.api.restserver.jersey.core.filter.authentication.sign;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.HttpHeaders;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import com.xdc.basic.api.restserver.jersey.core.filter.authentication.credentials.Credentials;
import com.xdc.basic.api.restserver.jersey.core.filter.authentication.sign.api.AuthenticationRequest;
import com.xdc.basic.api.restserver.jersey.core.filter.authentication.sign.api.AwsHttpHeaders;
import com.xdc.basic.api.restserver.jersey.core.filter.authentication.sign.api.Signer;

public class AwsSigner implements Signer
{
    /**
     * 哈希算法
     */
    private HashAlgorithm hashAlgorithm;

    /**
     * 签名算法
     */
    private SignAlgorithm signAlgorithm;

    public AwsSigner()
    {
        hashAlgorithm = HashAlgorithm.SHA256;
        signAlgorithm = SignAlgorithm.HmacSHA256;
    }

    @Override
    public HashAlgorithm getHashAlgorithm()
    {
        return hashAlgorithm;
    }

    public void setHashAlgorithm(HashAlgorithm hashAlgorithm)
    {
        this.hashAlgorithm = hashAlgorithm;
    }

    @Override
    public SignAlgorithm getSignAlgorithm()
    {
        return signAlgorithm;
    }

    public void setSignAlgorithm(SignAlgorithm signAlgorithm)
    {
        this.signAlgorithm = signAlgorithm;
    }

    @Override
    public String sign(Credentials credentials, AuthenticationRequest request) throws NotAuthorizedException
    {
        if (null == credentials)
        {
            return null;
        }

        String signaturingText = getSignaturingText(request);
        try
        {
            byte[] hashedSignaturingText = hash(signaturingText);
            return signaturedAndBase64(credentials, hashedSignaturingText);
        }
        catch (Exception e)
        {
            throw new NotAuthorizedException("Signature failed.", e);
        }
    }

    /**
     * 从请求中获取待签名文本信息。
     * 
     * @param request
     *            请求
     * @return 待签名的文本信息
     */
    private String getSignaturingText(AuthenticationRequest request)
    {
        StringBuilder sb = new StringBuilder(2048);

        // 拼接http方法及URI(已解码)
        sb.append(request.getMethod()).append('\n');
        sb.append(request.getPath(true)).append('\n');

        // 拼接http查询参数(已解码，按参数名字典序排序）
        appendCanonicalParamsString(sb, request).append('\n');

        // 拼接HTTP请求头参数
        appendCanonicalHeadersString(sb, request);

        return sb.toString();
    }

    /**
     * 拼装查询参数到待签名文本信息中。
     * 
     * @param request
     *            请求
     * @param sb
     *            待签名文本信息的缓冲区
     * @return 包含了查询参数的待签名文本信息的缓冲区
     */
    private StringBuilder appendCanonicalParamsString(StringBuilder sb, AuthenticationRequest request)
    {
        TreeMap<String, String> sortedParams = new TreeMap<String, String>();

        Map<String, String> params = request.getQueryParams(true);
        for (Entry<String, String> entry : params.entrySet())
        {
            sortedParams.put(entry.getKey(), entry.getValue());
        }

        Iterator<Entry<String, String>> it = sortedParams.entrySet().iterator();
        while (it.hasNext())
        {
            Entry<String, String> entry = it.next();
            sb.append(entry.getKey());
            sb.append('=');
            sb.append(entry.getValue());
            if (it.hasNext())
            {
                sb.append('&');
            }
        }

        return sb;
    }

    /**
     * 拼装HTTP请求Header参数到待签名文本中。
     * 
     * @param request
     *            请求
     * @param sb
     *            待签名文本信息的缓冲区
     * @return 包含了HTTP请求Header参数的待签名文本信息的缓冲区
     */
    private StringBuilder appendCanonicalHeadersString(StringBuilder sb, AuthenticationRequest request)
    {
        Map<String, String> sortedHeaders = new TreeMap<String, String>();

        Map<String, String> headers = request.getHeaders();
        for (Entry<String, String> entry : headers.entrySet())
        {
            // 只签名有AWS前缀的header
            if (StringUtils.startsWithIgnoreCase(entry.getKey(), AwsHttpHeaders.X_HEADER_PREFIX))
            {
                sortedHeaders.put(entry.getKey(), entry.getValue());
            }
        }

        // 优先取X-AWS-Date参数，取不到再取Date参数
        if (!sortedHeaders.containsKey(AwsHttpHeaders.X_DATE))
        {
            sortedHeaders.put(HttpHeaders.DATE, headers.get(HttpHeaders.DATE));
        }

        Iterator<Entry<String, String>> it = sortedHeaders.entrySet().iterator();
        while (it.hasNext())
        {
            Entry<String, String> entry = it.next();
            sb.append(StringUtils.lowerCase(entry.getKey(), Locale.ENGLISH));
            sb.append('=');
            sb.append(entry.getValue());
            if (it.hasNext())
            {
                sb.append('\n');
            }
        }

        return sb;
    }

    /**
     * 对待签名文本进行Hash（使用SHA256算法）。
     * 
     * @param textToSign
     *            待签名文本
     * @return 待签名文本HASH之后结果
     * @throws NoSuchAlgorithmException
     *             如果是不支持的HASH算法
     * @throws UnsupportedEncodingException
     *             如果是不支持编码
     */
    private byte[] hash(String signaturingText) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        MessageDigest md = MessageDigest.getInstance(hashAlgorithm.getName());
        md.update(signaturingText.getBytes("UTF-8"));
        return md.digest();
    }

    /**
     * 对请求Hash结果执行签名运算，并返回经过Base64编码后的字符串。
     * 
     * @param credentials
     *            密钥key值对
     * @param hashed
     *            已Hash的需签名文本
     * @return 执行签名运算后经过Base64编码后的字符串
     * 
     * @throws InvalidKeyException
     *             如果用于生成签名的SecretKey不满足签名算法要求
     * @throws NoSuchAlgorithmException
     *             如果是不支持的HASH算法
     * @throws UnsupportedEncodingException
     *             如果是不支持编码
     */
    private String signaturedAndBase64(Credentials credentials, byte[] hashedSignaturingText)
            throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException
    {
        if (null == credentials)
        {
            return null;
        }

        Mac mac = Mac.getInstance(signAlgorithm.name());
        mac.init(new SecretKeySpec(Base64.decodeBase64(credentials.getSecretKey()), signAlgorithm.name()));
        byte[] signature = mac.doFinal(hashedSignaturingText);
        return new String(Base64.encodeBase64(signature));
    }
}
