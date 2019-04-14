package com.ypc.common.utils;

public class ReportUtils {
    /**
     *
     * n年数据的增长率=[(本期/前n年)^(1/(n-1))-1]×100%
     * 同比增长率=（当年的指标值-去年同期的值）÷去年同期的值*100%
     * 环比增长率=（本期的某个指标的值-上一期这个指标的值）/上一期这个指标的值
     * @param yesterYearNum
     * @param todayYearNum
     * @return
     */
    public static String getGrowthRate(Integer todayYearNum,Integer yesterYearNum){
        String growthRate="+∞";
        if(null!=yesterYearNum && yesterYearNum != 0){
            growthRate = ((todayYearNum-yesterYearNum)/yesterYearNum*100)+"%";
        }
        return growthRate;
    }

    public static String getGrowthRate(Float todayYearNum,Float yesterYearNum){
        String growthRate="+∞";
        if(null!=yesterYearNum && yesterYearNum != 0){
            growthRate = ((todayYearNum-yesterYearNum)/yesterYearNum*100)+"%";
        }
        return growthRate;
    }

    /**
     * 是否是增长
     * @param yesterYearNum
     * @param todayYearNum
     * @return
     */
    public static Boolean isGrowth(Integer todayYearNum,Integer yesterYearNum){
        Boolean res = false;
        if(null != todayYearNum && null != yesterYearNum){
            res = yesterYearNum>=todayYearNum?true:false;
        }
        return res;
    }

    public static Boolean isGrowth(Float todayYearNum,Float yesterYearNum){
        Boolean res = false;
        if(null != todayYearNum && null != yesterYearNum){
            res = yesterYearNum>=todayYearNum?true:false;
        }
        return res;
    }
}
