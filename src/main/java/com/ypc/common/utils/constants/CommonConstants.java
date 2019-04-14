package com.ypc.common.utils.constants;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CommonConstants {

    public static final String DEFAULT_PWD = "admin";
    public static final String DB_NAME = "icmes_print";

    //云端服务器ip
    public static final String SERVER_IP = "47.98.184.160";
    //本地服务器ip
    public static final String LOCAL_SERVER_IP = "47.98.184.160";

    public static final Float ELECTRIC_CHARGE = (float) 1.0;

    public static final String TRUE = "1";

    public static final String FALSE = "0";

    public static final String SUCCESS = "1";

    public static final String FAILED = "0";

    /**
     * 生效的
     */
    public static final String VALID = "1";
    /**
     * 无效的
     */
    public static final String INVALID = "0";

    public static final String SUCCESS_TIP = "操作成功!";

    public static final String FAILED_TIP = "操作失败!";

    public static final String OPT_SIGN = "jhOptSign";

    public static final String OPT_ACTION_ADD = "jhOptAdd";

    public static final String OPT_ACTION_MODIFY = "jhOptModify";

    public static final String OPT_SUBMIT = "jhSubmitSign";

    /**
     * 操作模式参数名
     */
    public static final String OPT_MODE = "optMode";
    /**
     * 操作模式 可编辑
     */
    public static final String OPT_MODE_EDIT = "edit";
    /**
     * 操作模式 查看
     */
    public static final String OPT_MODE_VIEW = "view";

    /**
     * 操作模式 审核
     */
    public static final String OPT_MODE_AUDIT = "audit";

    /**
     * 待处理数据说明提示
     */
    public static final String TODO_TIP = "todoTip";


    /**
     * 进行中任务的页面标识
     */
//	public static final String TASK_STATUS="taskStatus";

    /**
     * 任务任务的页面标识
     */
//	public static final String AUDIT_TASK="auditTask";

    /**
     * 分表规则 1按年 2按月
     */
    public static final int SUB_RULE = 1;
    //分表时间配置按年分表 秒 分 时 日 月 年
    public static final String SUB_TABLE = "0 59 23 31 12 ?";

    /**
     * 超级管理员角色代码
     */
    public static final String SUPER_ADMIN = "superAdmin";


    /**
     * 未登录状态
     */
    public static final int STATUS_LOGIN = 401;
    /**
     * 系统锁定状态
     */
    public static final int STATUS_LOCK = 403;


    public static Map<String, String> OPT_MODE_MAP = new HashMap<String, String>();

    static {
        OPT_MODE_MAP.put(OPT_MODE_EDIT, "编辑");
        OPT_MODE_MAP.put(OPT_MODE_VIEW, "查看");
    }

    public static Map<String, String> VALID_MAP = new HashMap<String, String>();

    static {
        VALID_MAP.put(VALID, "有效");
        VALID_MAP.put(INVALID, "无效");
    }

    public static LinkedHashMap<Boolean, String> BOOLEAN_VALID_MAP = new LinkedHashMap<Boolean, String>();

    static {
        BOOLEAN_VALID_MAP.put(Boolean.TRUE, "有效");
        BOOLEAN_VALID_MAP.put(Boolean.FALSE, "无效");
    }

    public static LinkedHashMap<Boolean, String> YES_NO_MAP = new LinkedHashMap<Boolean, String>();

    static {
        YES_NO_MAP.put(Boolean.TRUE, "是");
        YES_NO_MAP.put(Boolean.FALSE, "否");
    }

    /**
     * 特别标记的tiles的后缀
     */
    public static String TILES_SUFFIX = ".page";

    /**
     * 质检率标准
     */
    public static float QUALITYCHECK_RATI = 0.85f;

    /**
     * 只发给发起审核的人员的消息类别  自动回复类别
     */
    public static String MMM = "mmm";
    /**
     * 进货检审核通知
     */
    public static String M001 = "m001";

    /**
     * 巡检审核通知
     */
    public static String M002 = "m002";

    /**
     * 成品检审核通知
     */
    public static String M003 = "m003";

    /**
     * 机长自检审核通知
     */
    public static String M004 = "m004";

    /**
     * 产品标准审核通知
     */
    public static String M005 = "m005";

    /**
     * 异常申诉确认通知
     */
    public static String M006 = "m006";

    /**
     * 保养标准新增通知
     */
    public static String M007 = "m007";

    /**
     * 点检标准新增通知
     */
    public static String M008 = "m008";

    /**
     * 保养计划审核通知
     */
    public static String M009 = "m009";

    /**
     * 保养信息审核通知
     */
    public static String M010 = "m010";

    /**
     * 维修申请审核通知
     */
    public static String M011 = "m011";

    /**
     * 维修记录审核通知
     */
    public static String M012 = "m012";

    /**
     * 保养单调整通知
     */
    public static String M013 = "m013";

    /**
     * 系统生成保养单通知
     */
    public static String M014 = "m014";

    /**
     * 领料申请审核通知
     */
    public static String M015 = "m015";

    /**
     * 废品统计审核
     */
    public static String M016 = "m016";

    /**
     * 退料申请审核通知
     */
    public static String M017 = "m017";
    /**
     * 排程通知
     */
    public static String M018 = "m018";

    /**
     * 系统运维通知
     */
    public static String M019 = "m019";
    /**
     * 生产消息
     */
    public static String M01 = "m01";


    /**采集产量Key*/
    public static String COLLECT_PRODUCT_AMOUNT_KEY="collectProductAmount";

    /**采集key*/
    public static String COLLECT_DATA_KEY = "collectData";

    /**是否采集产量信息 1是 0否*/
    public static String IS_COLLECT_PRODUCT_AMOUNT_KEY="isCollectProductAmount";
    
    /**设备状态信息*/
    public static String COLLECT_DEVICE_STATUS_KEY = "collectDeviceStatus";

    /**报工最后时间*/
    public static String TASKCONFIRM_LAST_TIME = "taskConfirmLastTime";
    
    /**报工放行时长*/
    public static Float CONFIRM_EARLY_RANGE_HOURS = (float) -0.5;
    public static Float CONFIRM_LATE_RANGE_HOURS = (float) 0.5;

    /*********************************  ES  ***********************************/
    /**基础信息索引*/
    public static final String FBINDEX = "factorybase";

    /**基础信息type*/
    public static final String BASTTYPE = "machine1";



    public static final String RUNTIMEINDEX = "runtime";
    /**产品信息*/
    public static final String PRODUCTINFOINDEX = "product";
    public static final String PRODUCTINFOTYPE = "product";
    /**产品工位*/
    public static final String WORKSTATIONINDEX = "workstation";
    public static final String WORKSTATIONTYPE = "workstation";
    /**产品相机*/
    public static final String CAMERAINDEX = "camera";
    public static final String CAMERATYPE = "camera";
    /**产品参考图*/
    public static final String REFIMAGEINDEX = "refimage";
    public static final String REFIMAGETYPE = "refimage";
    /**车次*/
    public static final String LOTINDEX = "lot";
    public static final String LOTTYPE = "lot";
    /**检测信息*/
    public static final String SHEETINDEX = "sheet";
    public static final String SHEETTYPE = "sheet";
    /**缺陷信息*/
    public static final String DEFECTINFOINDEX = "defect";
    public static final String DEFECTINFOTYPE = "defect";
    /**缺陷参数*/
    public static final String DEFECTPARAMINDEX = "defectparam";
    public static final String DEFECTPARAMTYPE = "defectparam";
    /**运行信息*/
    public static final String RUNTIMEINFOINDEX = "runtime";
    public static final String RUNTIMEINFOTYPE = "runtime";

    /**副本数<=集群数-1*/
    public static final int REPLICAS = 0;

    /****工单状态 1计划、2下达、3生产中、4暂停、5中止  6完成****/
    public static final int JOB_STATUS_PLAN = 1;
    public static final int JOB_STATUS_CONFIRM = 2;
    public static final int JOB_STATUS_PRODUCING = 3;
    public static final int JOB_STATUS_PAUSE = 4;
    public static final int JOB_STATUS_BREAK_OFF = 5;
    public static final int JOB_STATUS_COMPLETE = 6;
}
