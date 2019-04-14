package com.ypc.common.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: ypc
 * @Date: 2018-12-23 13:21
 * @Description:
 * @Modified By:
 */
public class IpAdrressUtil {
    /**
     * 获取Ip地址
     * @param request
     * @return
     */
    public static String getIpAdrress(HttpServletRequest request) {
        String xIp = request.getHeader("X-Real-IP");
        String xFor = request.getHeader("X-Forwarded-For");
        if(StringUtils.hasText(xFor) && !"unKnown".equalsIgnoreCase(xFor)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = xFor.indexOf(",");
            if(index != -1){
                return xFor.substring(0,index);
            }else{
                return xFor;
            }
        }
        xFor = xIp;
        if(StringUtils.hasText(xFor) && !"unKnown".equalsIgnoreCase(xFor)){
            return xFor;
        }
        if (StringUtils.isEmpty(xFor) || "unknown".equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(xFor) || "unknown".equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(xFor) || "unknown".equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isEmpty(xFor) || "unknown".equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isEmpty(xFor) || "unknown".equalsIgnoreCase(xFor)) {
            xFor = request.getRemoteAddr();
        }
        return xFor;
    }

}
