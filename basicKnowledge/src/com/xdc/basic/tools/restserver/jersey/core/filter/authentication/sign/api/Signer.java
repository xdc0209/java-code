package com.xdc.basic.tools.restserver.jersey.core.filter.authentication.sign.api;

import javax.ws.rs.NotAuthorizedException;

import com.xdc.basic.tools.restserver.jersey.core.filter.authentication.credentials.Credentials;

/**
 * REST请求签名生成工具。
 */
public interface Signer
{
    /**
     * Hash算法。
     */
    static enum HashAlgorithm
    {
        /**
         * SHA1 Hash算法
         */
        SHA1("SHA-1"),

        /**
         * SHA256 Hash算法
         */
        SHA256("SHA-256"),

        /**
         * SHA364 Hash算法
         */
        SHA364("SHA-364");

        /**
         * Hash算法名称
         */
        private String name;

        private HashAlgorithm(String name)
        {
            this.name = name;
        }

        /**
         * 查询Hash算法名称。
         */
        public String getName()
        {
            return name;
        }
    }

    /**
     * 签名算法。
     */
    static enum SignAlgorithm
    {
        /**
         * HmacSHA1签名方式
         */
        HmacSHA1,

        /**
         * HmacSHA256签名方式
         */
        HmacSHA256,

        /**
         * HmacSHA364签名方式
         */
        HmacSHA364,
    }

    /**
     * 查询HASH算法。
     */
    HashAlgorithm getHashAlgorithm();

    /**
     * 查询签名算法。
     */
    SignAlgorithm getSignAlgorithm();

    /**
     * 对请求进行签名。
     * 
     * @param request
     *            HTTP请求
     * @param credentials
     *            Client访问凭证
     * @return 签名后的字符串
     * @throws NotAuthorizedException
     *             如果生成签名失败
     */
    String sign(Credentials credentials, AuthenticationRequest request) throws NotAuthorizedException;
}
