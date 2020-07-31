package pro.dengyi.syspermission.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import pro.dengyi.syspermission.model.SystemUser;

import java.util.Date;

/**
 * jwt工具类
 *
 * @author dengy
 */
public class JwtTokenUtil {

    /**
     * subject
     */
    public static final String SUBJECT = "dengyi";
    /**
     * 过期时间
     */
    public static final long EXPIRE = 1000 * 60 * 60 * 24;
    /**
     * 加密秘钥
     */
    public static final String APPSECRET = "dengyi123!@#";


    /**
     * 生成 jwt token方法
     *
     * @return token字符串
     */
    public static String genToken(SystemUser systemUser) {
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("userId", systemUser.getId())
                .claim("phoneNumber", systemUser.getPhoneNumber())
                .claim("isSassAdmin", systemUser.getIsSassAdmin())
                .claim("isCoAdmin", systemUser.getIsCoAdmin())
                .claim("nickName", systemUser.getNickName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256, APPSECRET).compact();
        return token;
    }

    /**
     * 解码jwt token
     *
     * @param token
     * @return
     */
    public static SystemUser decToken(String token) {
        SystemUser systemUser = new SystemUser();
        Claims body = Jwts.parser()
                .setSigningKey(APPSECRET)
                .parseClaimsJws(token)
                .getBody();
        String id = (String) body.get("userId");
        String phone = (String) body.get("phoneNumber");
        Boolean isSassAdmin = (Boolean) body.get("isSassAdmin");
        Boolean isCoAdmin = (Boolean) body.get("isCoAdmin");
        String nickName = (String) body.get("nickName");
        systemUser.setId(id);
        systemUser.setPhoneNumber(phone);
        systemUser.setIsSassAdmin(isSassAdmin);
        systemUser.setIsCoAdmin(isCoAdmin);
        systemUser.setNickName(nickName);
        return systemUser;

    }

}
