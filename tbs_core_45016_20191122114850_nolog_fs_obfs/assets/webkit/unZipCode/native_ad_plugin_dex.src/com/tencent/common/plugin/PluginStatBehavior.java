package com.tencent.common.plugin;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import com.tencent.basesupport.PackageInfo;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.common.utils.GdiMeasureImpl;
import com.tencent.mtt.ContextHolder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PluginStatBehavior
{
  public static int LOAD_TYPE_DOWNLOAD = 3;
  public static int LOAD_TYPE_DOWNLOAD_BACK = 2;
  public static int LOAD_TYPE_FORCE_LOCAL = 1;
  public static int LOAD_TYPE_LOCAL = 0;
  public static final int OP_TYPE_PLUGIN_DB = 7;
  public static final int OP_TYPE_PLUGIN_DOWN = 2;
  public static final int OP_TYPE_PLUGIN_INSTALL = 3;
  public static final int OP_TYPE_PLUGIN_SERVICE_BIND = 5;
  public static final int OP_TYPE_PLUGIN_SERVICE_USE = 6;
  public static final int OP_TYPE_PLUGIN_USE = 4;
  public static final String PLUGIN_STAT_DB_PKGNAME = "PluginDB";
  public static final int PLUGIN_STAT_DOWN_TASK_CREATED = 1000;
  public static final int PLUGIN_STAT_PLUGINLIST_COMPATID_NOTMATCH = 332;
  public static final String PLUGIN_STAT_PLUGINLIST_PKGNAME = "PluginList";
  public static final int PLUGIN_STAT_PLUGINLIST_REQUEST_FAIL_INSERT_TODB_ERROR = 326;
  public static final int PLUGIN_STAT_PLUGINLIST_REQUEST_FAIL_PLUGINLISTSIZE_ZERO = 318;
  public static final int PLUGIN_STAT_PLUGINLIST_REQUEST_FAIL_PLUGINLIST_INSERT_DBFAILED = 320;
  public static final int PLUGIN_STAT_PLUGINLIST_REQUEST_FAIL_REQUESTING = 312;
  public static final int PLUGIN_STAT_PLUGINLIST_REQUEST_FAIL_SENDWUPERR = 314;
  public static final int PLUGIN_STAT_PLUGINLIST_REQUEST_FAIL_SVRNULLPLUGINLIST = 317;
  public static final int PLUGIN_STAT_PLUGINLIST_REQUEST_FAIL_SVRRET_ERROR = 321;
  public static final int PLUGIN_STAT_PLUGINLIST_REQUEST_FAIL_THIRDAPP_FORBITPLUGINLIST = 325;
  public static final int PLUGIN_STAT_PLUGINLIST_REQUEST_FAIL_WIFI_NOTLOGIN = 329;
  public static final int PLUGIN_STAT_PLUGINLIST_REQUEST_FAIL_WUPFAILED = 322;
  public static final int PLUGIN_STAT_PLUGINLIST_REQUEST_FAIL_WUPFAILED_NETOK = 327;
  public static final int PLUGIN_STAT_PLUGINLIST_REQUEST_FAIL_WUPRSPNOTPLG = 316;
  public static final int PLUGIN_STAT_PLUGINLIST_REQUEST_FAIL_WUPRSPNULL = 315;
  public static final int PLUGIN_STAT_PLUGINLIST_REQUEST_INIT_MEM = 330;
  public static final int PLUGIN_STAT_PLUGINLIST_REQUEST_NOFROM_TYPE = 331;
  public static final int PLUGIN_STAT_PLUGINLIST_REQUEST_STAT_PLUGINLIST_INSERT_TODB = 319;
  public static final int PLUGIN_STAT_PLUGINLIST_REQUEST_STAT_SENDWUPOK = 313;
  public static final int PLUGIN_STAT_PLUGINLIST_REQUEST_STAT_START = 311;
  public static final int PLUGIN_STAT_PLUGINLIST_REQUEST_STAT_START_FORWUP = 323;
  public static final int PLUGIN_STAT_PLUGINLIST_REQUEST_SUCCESS = 30;
  public static final int PLUGIN_STAT_PLUGINLIST_REQUEST_SUCC_PLUGINLISTSIZE_ZERO = 324;
  public static final int PLUGIN_STAT_PLUGINLIST_REQUEST_TIMEOUT_ERROR = 328;
  public static final int PLUGIN_STAT_PLUGINLIST_UPDATE_COMPATID = 333;
  public static final String PLUGIN_STAT_PLUGINSERVICE_BIND = "PluginServiceBind";
  public static final int PLUGIN_STAT_PLUGIN_DB_BASE = 360;
  public static final int PLUGIN_STAT_PLUGIN_DB_MAX = 399;
  public static final int PLUGIN_STAT_PLUGIN_DOWNLOAD_FAIL_DOWNLOAD_FAILD = 414;
  public static final int PLUGIN_STAT_PLUGIN_DOWNLOAD_FAIL_DOWNLOAD_NOPLUGIN_INFO = 420;
  public static final int PLUGIN_STAT_PLUGIN_DOWNLOAD_FAIL_DOWNLOAD_NOPLUGIN_INFO_CNN = 433;
  public static final int PLUGIN_STAT_PLUGIN_DOWNLOAD_FAIL_DOWNLOAD_NOT_THE_RIGHT_PROCESS = 424;
  public static final int PLUGIN_STAT_PLUGIN_DOWNLOAD_FAIL_DOWNLOAD_PARAM_ERROR = 415;
  public static final int PLUGIN_STAT_PLUGIN_DOWNLOAD_FAIL_DOWNLOAD_STARTDOWNLOAD_FAIL = 416;
  public static final int PLUGIN_STAT_PLUGIN_DOWNLOAD_FAIL_DOWNLOAD_THIRDAPP_FORBID = 423;
  public static final int PLUGIN_STAT_PLUGIN_DOWNLOAD_FAIL_DOWNLOAD_TIMEOUT = 422;
  public static final int PLUGIN_STAT_PLUGIN_DOWNLOAD_FAIL_DOWNLOAD_TIMEOUT_CNN = 432;
  public static final int PLUGIN_STAT_PLUGIN_DOWNLOAD_FAIL_FILESIZE_ERROR = 427;
  public static final int PLUGIN_STAT_PLUGIN_DOWNLOAD_FAIL_MD5_ERROR = 428;
  public static final int PLUGIN_STAT_PLUGIN_DOWNLOAD_FAIL_USER_CANCELLED = 431;
  public static final int PLUGIN_STAT_PLUGIN_DOWNLOAD_FOR_DELETE_DOWNLOADDIR = 426;
  public static final int PLUGIN_STAT_PLUGIN_DOWNLOAD_NETNOT_AVALIABLE = 429;
  public static final int PLUGIN_STAT_PLUGIN_DOWNLOAD_PREPARE_ANTIHIJACK = 425;
  public static final int PLUGIN_STAT_PLUGIN_DOWNLOAD_STAT_DOWNLOAD_LOCALFILE_NEW = 417;
  public static final int PLUGIN_STAT_PLUGIN_DOWNLOAD_STAT_DOWNLOAD_LOCALFILE_NODTASK = 418;
  public static final int PLUGIN_STAT_PLUGIN_DOWNLOAD_STAT_DOWNLOAD_LOCALFILE_ONTASKCOMPLETE = 419;
  public static final int PLUGIN_STAT_PLUGIN_DOWNLOAD_STAT_START_DOWNLOAD = 421;
  public static final int PLUGIN_STAT_PLUGIN_DOWNLOAD_STAT_TASKCREAT = 411;
  public static final int PLUGIN_STAT_PLUGIN_DOWNLOAD_STAT_TASKPROCESS = 413;
  public static final int PLUGIN_STAT_PLUGIN_DOWNLOAD_STAT_TASKSTART = 412;
  public static final int PLUGIN_STAT_PLUGIN_DOWNLOAD_SUCCESS = 40;
  public static final int PLUGIN_STAT_PLUGIN_DOWNLOAD_SUCCESS_BUT_FILENAME_EMPTY = 430;
  public static final int PLUGIN_STAT_PLUGIN_DOWNLOAD_TIMEOUT = 620;
  public static final int PLUGIN_STAT_PLUGIN_DWONLOAD_SUCCESS = 617;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_DOWNLOADBACK = 621;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_FAIL_CHECKSIGNFAILED = 517;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_FAIL_CHECKSIGNFAILEDEXP0 = 5170;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_FAIL_CHECKSIGNFAILEDEXP1 = 5171;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_FAIL_CHECKSIGNFAILEDEXP2 = 5172;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_FAIL_CHECKSIGNFAILEDEXP3 = 5173;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_FAIL_CHECKSIGNFAILEDEXP4 = 5174;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_FAIL_CHECKSIGNFAILEDNULL = 5175;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_FAIL_FILENOTEXIST = 512;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_FAIL_FILENOTEXIST_EXP = 513;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_FAIL_GETINSTALLDIR = 523;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_FAIL_GETPLUGININSTALLDIR_FAILD = 518;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_FAIL_GETPLUGININSTALLDIR_FAILD1 = 5181;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_FAIL_GETPLUGININSTALLDIR_FAILD3 = 5183;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_FAIL_GETPLUGININSTALLDIR_FAILD5 = 5184;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_FAIL_INSTALL_TIMEOUT = 528;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_FAIL_JAR_DELETEOLDDIR = 525;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_FAIL_JAR_NOTJARFILE = 524;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_FAIL_JAR_RENAME = 526;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_FAIL_JAR_UNZIPJARSO = 527;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_FAIL_MD5NOTSAME = 516;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_FAIL_MD5NOTSAME_FILESIZENOTOK = 568;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_FAIL_NOINFO = 629;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_FAIL_NOPLUGININFO = 522;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_FAIL_PARAMS_ERROR = 511;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_FAIL_UNZIP_CREATEDIR_FAILD = 520;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_FAIL_UNZIP_DELETEDIR_FAILD = 519;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_FAIL_UNZIP_FILENOTFOUND = 571;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_FAIL_UNZIP_READ_FILE = 572;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_FAIL_UNZIP_UNZIP_FAILD = 521;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_FAIL_UNZIP_WRITE_INVALID_BLOCKTYPE = 573;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_FAIL_UNZIP_WRITE_NOSPACE = 574;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_FAIL_UNZIP_WRITE_TONEWFILE = 570;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_FAIL_UNZIP_ZIPFILE_FORMATERROR = 575;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_FONT_COPY_FAILED = 563;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_GEN_PLUINGS_DATA_FAILED = 567;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_NONEED = 618;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_NOVELPLUGIN_ARCHIVE_ERROR = 565;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_NOVELPLUGIN_ARCHIVE_OPEN_ERROR = 566;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_STAT = 616;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_STAT_DELETE_SRC_FILE = 560;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_STAT_FROM_QBPLUGIN_PROXY = 562;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_STAT_LOCALMD5NULL = 515;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_STAT_RENAME_SRC_FILE = 561;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_STAT_SVRMD5NULL = 514;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_SUCCESS = 50;
  public static final int PLUGIN_STAT_PLUGIN_INSTALL_USER_STOP_INSTALL = 564;
  public static final int PLUGIN_STAT_PLUGIN_LOAD_AND_DOWDLOADBACK = 613;
  public static final int PLUGIN_STAT_PLUGIN_LOAD_FAIL = 655;
  public static final int PLUGIN_STAT_PLUGIN_LOAD_FAIL_COMPUTE_STATUS_ERROR = 649;
  public static final int PLUGIN_STAT_PLUGIN_LOAD_FAIL_DOWNLOAD = 654;
  public static final int PLUGIN_STAT_PLUGIN_LOAD_FAIL_DOWNLOAD_CNN = 653;
  public static final int PLUGIN_STAT_PLUGIN_LOAD_FAIL_FORCE_USELOCAL_ERROR = 648;
  public static final int PLUGIN_STAT_PLUGIN_LOAD_FAIL_GETPLUGININFO = 651;
  public static final int PLUGIN_STAT_PLUGIN_LOAD_FAIL_GETPLUGININFO_FAIL_NONET = 658;
  public static final int PLUGIN_STAT_PLUGIN_LOAD_FAIL_GETPLUGININFO_NOINFO = 650;
  public static final int PLUGIN_STAT_PLUGIN_LOAD_FAIL_GETPLUGININFO_PULLPLUGINLIST_BUT_NO_PLUGIN_INFO = 659;
  public static final int PLUGIN_STAT_PLUGIN_LOAD_FAIL_GETPLUGININFO_SEND = 656;
  public static final int PLUGIN_STAT_PLUGIN_LOAD_FAIL_GETPLUGININFO_SNDPLUGINLIST_FAIL = 657;
  public static final int PLUGIN_STAT_PLUGIN_LOAD_FAIL_INSTALL = 652;
  public static final int PLUGIN_STAT_PLUGIN_LOAD_FROMINSTALLBACK = 612;
  public static final int PLUGIN_STAT_PLUGIN_LOAD_GETPLUGININFO_TIMEOUT = 660;
  public static final int PLUGIN_STAT_PLUGIN_LOAD_INSTALL_TIMEOUT = 661;
  public static final int PLUGIN_STAT_PLUGIN_LOAD_LOADING_TIMEOUT = 662;
  public static final int PLUGIN_STAT_PLUGIN_LOAD_NONEED_UPDATE = 614;
  public static final int PLUGIN_STAT_PLUGIN_LOAD_PLUGINSERVICE_ERROR = 609;
  public static final int PLUGIN_STAT_PLUGIN_LOAD_REQUEST_DOWNLOAD = 615;
  public static final int PLUGIN_STAT_PLUGIN_LOAD_SUCCESS = 60;
  public static final int PLUGIN_STAT_PLUGIN_MESSAGE_DOWNLOAD_START = 609;
  public static final int PLUGIN_STAT_PLUGIN_SERVICE_BIND_FAILED = 211;
  public static final int PLUGIN_STAT_PLUGIN_SERVICE_BIND_SUCCESS = 20;
  public static final int PLUGIN_STAT_PLUGIN_SERVICE_FAILED_MAINPROCESS_SERVICEIMPL_NULL = 212;
  public static final int PLUGIN_STAT_PLUGIN_SERVICE_FAILED_SEND_INTENT_FAILED = 214;
  public static final int PLUGIN_STAT_PLUGIN_SERVICE_FAILED_START_PLUGINSERVICE_SECURITY_ERROR = 213;
  public static final int PLUGIN_STAT_PLUGIN_SERVICE_STAT_ONSERVCIE_CONNECT_FAILED = 217;
  public static final int PLUGIN_STAT_PLUGIN_SERVICE_STAT_ONSERVCIE_CONNECT_SUCCESS = 216;
  public static final int PLUGIN_STAT_PLUGIN_SERVICE_STAT_SEND_INTENT_SUCCESS = 215;
  public static final int PLUGIN_STAT_PLUGIN_SERVICE_STAT_SERVICE_EXCEPTION = 219;
  public static final int PLUGIN_STAT_PLUGIN_SERVICE_STAT_SERVICE_NULL = 218;
  public static final int PLUGIN_STAT_PLUGIN_STOP_BYUSER = 622;
  public static final int PLUGIN_STAT_PLUGIN_USEPLUGIN = 610;
  public static final int PLUGIN_STAT_PLUGIN_USEPLUGIN_GETINFO = 608;
  public static final int PLUGIN_STAT_PLUGIN_USEPLUGIN_STARTGETINFO = 607;
  public static final int PLUGIN_STAT_PLUGIN_USEPLUGIN_STARTINSTALL_ONDISK = 606;
  public static final int PLUGIN_STAT_PLUGIN_USEPLUGIN_STARTLOADPL_AND_DLBACK = 605;
  public static final int PLUGIN_STAT_PLUGIN_USEPLUGIN_STARTLOADPL_NOUPDATE = 604;
  public static final int PLUGIN_STAT_PLUGIN_WAIT_WUPRESULT = 611;
  public static final int RET_SUCCESS = 0;
  static PluginStatInfo jdField_a_of_type_ComTencentCommonPluginPluginStatBehavior$PluginStatInfo = null;
  static Object jdField_a_of_type_JavaLangObject = new Object();
  static final Map<String, Map<Integer, PluginStatInfo>> jdField_a_of_type_JavaUtilMap = new ConcurrentHashMap();
  public static Context mContext;
  public static long mLongBufferSize = 51200L;
  
  private static PluginStatInfo a(String paramString, int paramInt)
  {
    if ((!TextUtils.isEmpty(paramString)) && (paramInt != 0))
    {
      ??? = (Map)jdField_a_of_type_JavaUtilMap.get(paramString);
      Object localObject1 = ???;
      if (??? == null) {
        synchronized (jdField_a_of_type_JavaUtilMap)
        {
          if (!jdField_a_of_type_JavaUtilMap.containsKey(paramString))
          {
            localObject1 = new ConcurrentHashMap();
            jdField_a_of_type_JavaUtilMap.put(paramString, localObject1);
          }
          else
          {
            localObject1 = (Map)jdField_a_of_type_JavaUtilMap.get(paramString);
          }
        }
      }
      ??? = (PluginStatInfo)((Map)localObject1).get(Integer.valueOf(paramInt));
      if (??? == null) {
        try
        {
          if (!((Map)localObject1).containsKey(Integer.valueOf(paramInt)))
          {
            ??? = new PluginStatInfo();
            ((PluginStatInfo)???).plugin_pkgname = paramString;
            ((PluginStatInfo)???).pluign_op_type = paramInt;
            ((Map)localObject1).put(Integer.valueOf(paramInt), ???);
            paramString = (String)???;
          }
          else
          {
            paramString = (PluginStatInfo)((Map)localObject1).get(Integer.valueOf(paramInt));
          }
          return paramString;
        }
        finally {}
      }
      return (PluginStatInfo)???;
    }
    return null;
  }
  
  public static void addLogPath(String arg0, int paramInt1, int paramInt2)
  {
    if (!TextUtils.isEmpty(???))
    {
      if (paramInt1 == 0) {
        return;
      }
      Object localObject1 = a(???, paramInt1);
      if (localObject1 != null) {
        try
        {
          synchronized (((PluginStatInfo)localObject1).log_path)
          {
            if (((PluginStatInfo)localObject1).log_path.length() > mLongBufferSize) {
              ((PluginStatInfo)localObject1).log_path.setLength(0);
            }
            localObject1 = ((PluginStatInfo)localObject1).log_path;
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("_");
            localStringBuilder.append(paramInt2);
            ((StringBuilder)localObject1).append(localStringBuilder.toString());
            return;
          }
          return;
        }
        catch (Exception ???)
        {
          ???.printStackTrace();
        }
      }
    }
  }
  
  public static void addLogPath(String arg0, int paramInt, String paramString2)
  {
    if (!TextUtils.isEmpty(???))
    {
      if (paramInt == 0) {
        return;
      }
      Object localObject = a(???, paramInt);
      if (localObject != null) {
        try
        {
          synchronized (((PluginStatInfo)localObject).log_path)
          {
            if (((PluginStatInfo)localObject).log_path.length() > mLongBufferSize) {
              ((PluginStatInfo)localObject).log_path.setLength(0);
            }
            localObject = ((PluginStatInfo)localObject).log_path;
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("_");
            localStringBuilder.append(paramString2);
            ((StringBuilder)localObject).append(localStringBuilder.toString());
            return;
          }
          return;
        }
        catch (Exception ???)
        {
          ???.printStackTrace();
        }
      }
    }
  }
  
  public static void clearBehavior(String paramString, int paramInt)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      if (paramInt == 0) {
        return;
      }
      paramString = (Map)jdField_a_of_type_JavaUtilMap.get(paramString);
      if (paramString != null) {
        paramString.remove(Integer.valueOf(paramInt));
      }
      return;
    }
  }
  
  public static void clearUserOP(String paramString, int paramInt)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      if (paramInt == 0) {
        return;
      }
      paramString = a(paramString, paramInt);
      if (paramString != null) {
        try
        {
          synchronized (paramString.log_path)
          {
            paramString.log_path.setLength(0);
          }
          return;
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          paramString.wait_checkin_time = 0L;
          paramString.wait_download_time = 0L;
        }
      }
    }
  }
  
  public static int getLastRetCode(int paramInt)
  {
    if (paramInt == 1)
    {
      PluginStatInfo localPluginStatInfo = jdField_a_of_type_ComTencentCommonPluginPluginStatBehavior$PluginStatInfo;
      if (localPluginStatInfo != null) {
        return localPluginStatInfo.ret_code;
      }
    }
    return 0;
  }
  
  public static String getLogPath(String arg0, int paramInt)
  {
    if ((!TextUtils.isEmpty(???)) && (paramInt != 0))
    {
      Object localObject1 = a(???, paramInt);
      if (localObject1 != null) {
        try
        {
          synchronized (((PluginStatInfo)localObject1).log_path)
          {
            localObject1 = ((PluginStatInfo)localObject1).log_path.toString();
            return (String)localObject1;
          }
          return "";
        }
        catch (Exception ???)
        {
          ???.printStackTrace();
        }
      }
    }
    return "";
  }
  
  public static int getOpTyepPluginList(int paramInt)
  {
    if (paramInt == 1) {
      return 1;
    }
    return 11;
  }
  
  public static void incReqPluginListCount(String paramString, int paramInt)
  {
    paramString = a(paramString, paramInt);
    if (paramString != null) {
      paramString.request_plugin_count += 1;
    }
  }
  
  public static void init(Context paramContext)
  {
    if (mContext == null) {
      mContext = paramContext;
    }
  }
  
  public static void report(String paramString, int paramInt)
  {
    if ((!TextUtils.isEmpty(paramString)) && (paramInt != 0))
    {
      if (mContext == null) {
        return;
      }
      PluginStatInfo localPluginStatInfo = a(paramString, paramInt);
      if (localPluginStatInfo != null)
      {
        uploadToBeacon(localPluginStatInfo);
        clearBehavior(paramString, paramInt);
        if (paramInt == 1) {
          jdField_a_of_type_ComTencentCommonPluginPluginStatBehavior$PluginStatInfo = localPluginStatInfo;
        }
      }
      return;
    }
  }
  
  public static void reportPluginList(String paramString, final int paramInt1, final int paramInt2, final int paramInt3)
  {
    final QBPluginServiceImpl.IPluginRelateFunc localIPluginRelateFunc = QBPluginServiceImpl.msRelateFunc;
    if (localIPluginRelateFunc == null) {
      return;
    }
    BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        HashMap localHashMap = new HashMap();
        localHashMap.put("app_name", PackageInfo.PKGNAME());
        localHashMap.put("plugin_pkgname", this.jdField_a_of_type_JavaLangString);
        localHashMap.put("plugin_op_type", Integer.toString(paramInt1));
        localHashMap.put("ret_code", Integer.toString(paramInt2));
        localHashMap.put("req_md5", PluginSetting.getInstance(ContextHolder.getAppContext()).pluginListRspMD5(paramInt3));
        localHashMap.put("cpu_type", QBPluginProxy.getCpuType());
        localHashMap.put("sdk_ver", Integer.toString(GdiMeasureImpl.getSdkVersion()));
        localHashMap.put("info_from", Integer.toString(paramInt3));
        localHashMap.put("A61", localIPluginRelateFunc.getQUA());
        ArrayList localArrayList = QBPluginDBHelper.getInstance(ContextHolder.getAppContext()).getAllPluginList(paramInt3);
        localHashMap.put("list_size", Integer.toString(localArrayList.size()));
        int i = 0;
        int j = 0;
        for (;;)
        {
          StringBuilder localStringBuilder = new StringBuilder();
          while (j < localArrayList.size())
          {
            QBPluginItemInfo localQBPluginItemInfo = (QBPluginItemInfo)localArrayList.get(j);
            Object localObject2 = localQBPluginItemInfo.mMd5;
            localObject1 = localObject2;
            if (!TextUtils.isEmpty((CharSequence)localObject2))
            {
              localObject1 = localObject2;
              if (((String)localObject2).length() > 8) {
                localObject1 = ((String)localObject2).substring(0, 8);
              }
            }
            localObject2 = new StringBuilder("|");
            ((StringBuilder)localObject2).append(localQBPluginItemInfo.mPackageName);
            ((StringBuilder)localObject2).append(",");
            ((StringBuilder)localObject2).append(localQBPluginItemInfo.mVersion);
            ((StringBuilder)localObject2).append(",");
            ((StringBuilder)localObject2).append((String)localObject1);
            ((StringBuilder)localObject2).append(",");
            ((StringBuilder)localObject2).append(localQBPluginItemInfo.mPluginCompatiID);
            if (localStringBuilder.length() + ((StringBuilder)localObject2).length() >= 1000) {
              break;
            }
            localStringBuilder.append((CharSequence)localObject2);
            j += 1;
          }
          if (localStringBuilder.length() == 0)
          {
            localIPluginRelateFunc.upLoadToBeacon("MTT_PLUGIN_INFO_LIST", localHashMap);
            return;
          }
          Object localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("list_");
          ((StringBuilder)localObject1).append(i);
          localHashMap.put(((StringBuilder)localObject1).toString(), localStringBuilder.substring(1));
          i += 1;
        }
      }
    });
  }
  
  public static void setCheckInTime(String paramString, int paramInt, long paramLong)
  {
    paramString = a(paramString, paramInt);
    if (paramString != null) {
      paramString.wait_checkin_time = paramLong;
    }
  }
  
  public static void setDownloadDir(String paramString1, int paramInt, String paramString2)
  {
    paramString1 = a(paramString1, paramInt);
    if (paramString1 != null) {
      paramString1.plugin_download_dir = paramString2;
    }
  }
  
  public static void setDownloadFileName(String paramString1, int paramInt, String paramString2)
  {
    paramString1 = a(paramString1, paramInt);
    if (paramString1 != null) {
      paramString1.plugin_download_filename = paramString2;
    }
  }
  
  public static void setFileSize(String paramString, int paramInt, long paramLong)
  {
    paramString = a(paramString, paramInt);
    if (paramString != null) {
      paramString.file_size = paramLong;
    }
  }
  
  public static void setFinCode(String paramString, int paramInt1, int paramInt2)
  {
    if (paramInt1 == 6) {
      return;
    }
    PluginStatInfo localPluginStatInfo = a(paramString, paramInt1);
    if (localPluginStatInfo != null)
    {
      localPluginStatInfo.ret_code = paramInt2;
      localPluginStatInfo.time_end = System.currentTimeMillis();
      localPluginStatInfo.time_used = (localPluginStatInfo.time_end - localPluginStatInfo.time_start);
      localPluginStatInfo.time_end = 0L;
      localPluginStatInfo.time_start = 0L;
      report(paramString, paramInt1);
    }
  }
  
  public static void setInstallDir(String paramString1, int paramInt, String paramString2)
  {
    paramString1 = a(paramString1, paramInt);
    if (paramString1 != null) {
      paramString1.plugin_install_dir = paramString2;
    }
  }
  
  public static void setInstallFileName(String paramString1, int paramInt, String paramString2)
  {
    paramString1 = a(paramString1, paramInt);
    if (paramString1 != null) {
      paramString1.plugin_install_filename = paramString2;
    }
  }
  
  public static void setLoadPluginOKOrder(String paramString, int paramInt1, int paramInt2)
  {
    paramString = a(paramString, paramInt1);
    if (paramString != null) {
      paramString.plugin_loadok_order = paramInt2;
    }
  }
  
  public static void setLoadPluginType(String paramString, int paramInt1, int paramInt2)
  {
    paramString = a(paramString, paramInt1);
    if (paramString != null) {
      paramString.plugin_load_type = paramInt2;
    }
  }
  
  public static void setLocalMd5(String paramString1, int paramInt, String paramString2)
  {
    paramString1 = a(paramString1, paramInt);
    if (paramString1 != null) {
      paramString1.plugin_local_md5 = paramString2;
    }
  }
  
  public static void setOpType(String paramString, int paramInt)
  {
    paramString = a(paramString, paramInt);
    if (paramString != null) {
      paramString.setOpType(paramInt);
    }
  }
  
  public static void setPluginData(String paramString1, int paramInt, String paramString2)
  {
    paramString1 = a(paramString1, paramInt);
    if (paramString1 != null) {
      paramString1.plugin_data = paramString2;
    }
  }
  
  public static void setPluginExtInfo(String paramString1, int paramInt, String paramString2)
  {
    paramString1 = a(paramString1, paramInt);
    if (paramString1 != null)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("setPluginExtInfo:");
      localStringBuilder.append(paramString2);
      FLogger.d("XXXXX", localStringBuilder.toString());
      paramString1.plugin_ext_info = paramString2;
    }
  }
  
  public static void setPluginListRefreshTime(String paramString, int paramInt, long paramLong)
  {
    paramString = a(paramString, paramInt);
    if (paramString != null) {
      paramString.pluginlist_refresh_time = paramLong;
    }
  }
  
  public static void setSvrMd5(String paramString1, int paramInt, String paramString2)
  {
    paramString1 = a(paramString1, paramInt);
    if (paramString1 != null) {
      paramString1.plugin_pluginlist_md5 = paramString2;
    }
  }
  
  public static void setUseCount(String paramString, int paramInt1, int paramInt2)
  {
    paramString = a(paramString, paramInt1);
    if (paramString != null) {
      paramString.plugin_use_count = paramInt2;
    }
  }
  
  public static void setWaitDownloadTime(String paramString, int paramInt, long paramLong)
  {
    paramString = a(paramString, paramInt);
    if ((paramString != null) && (!TextUtils.isEmpty(paramString.log_path))) {}
    try
    {
      paramString = paramString.log_path;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("(waited:");
      localStringBuilder.append(paramLong);
      localStringBuilder.append(")");
      paramString.append(localStringBuilder.toString());
      return;
    }
    catch (Exception paramString) {}
  }
  
  public static void uploadToBeacon(PluginStatInfo paramPluginStatInfo)
  {
    QBPluginServiceImpl.IPluginRelateFunc localIPluginRelateFunc = QBPluginServiceImpl.msRelateFunc;
    if (paramPluginStatInfo != null)
    {
      if (localIPluginRelateFunc == null) {
        return;
      }
      PluginStatInfo localPluginStatInfo = jdField_a_of_type_ComTencentCommonPluginPluginStatBehavior$PluginStatInfo;
      synchronized (jdField_a_of_type_JavaLangObject)
      {
        HashMap localHashMap = new HashMap();
        if ((paramPluginStatInfo.ret_code != 0) && (paramPluginStatInfo.pluign_op_type == 4)) {
          localHashMap.put("A61", localIPluginRelateFunc.getQUA());
        }
        localHashMap.put("B1", String.valueOf(paramPluginStatInfo.pluign_op_type));
        localHashMap.put("B3", paramPluginStatInfo.plugin_pkgname);
        if (!TextUtils.isEmpty(paramPluginStatInfo.log_path))
        {
          localStringBuilder1 = paramPluginStatInfo.log_path;
          if (localPluginStatInfo != null) {}
          try
          {
            if (((paramPluginStatInfo.pluign_op_type == 2) && (paramPluginStatInfo.ret_code == 420)) || ((paramPluginStatInfo.pluign_op_type == 4) && ((paramPluginStatInfo.ret_code == 650) || (paramPluginStatInfo.ret_code == 332))))
            {
              StringBuilder localStringBuilder2 = new StringBuilder(localPluginStatInfo.log_path);
              localStringBuilder2.append(paramPluginStatInfo.log_path);
              localStringBuilder2.append("_");
              localStringBuilder2.append(localPluginStatInfo.ret_code);
              localHashMap.put("B4", localStringBuilder2.toString());
            }
            else
            {
              localHashMap.put("B4", paramPluginStatInfo.log_path.toString());
            }
          }
          finally {}
        }
        localHashMap.put("B5", String.valueOf(paramPluginStatInfo.ret_code));
        if (!TextUtils.isEmpty(paramPluginStatInfo.plugin_install_version)) {
          localHashMap.put("B6", paramPluginStatInfo.plugin_install_version);
        }
        if (!TextUtils.isEmpty(paramPluginStatInfo.plugin_local_md5)) {
          localHashMap.put("B7", paramPluginStatInfo.plugin_local_md5);
        }
        if (!TextUtils.isEmpty(paramPluginStatInfo.plugin_download_dir)) {
          localHashMap.put("B8", paramPluginStatInfo.plugin_download_dir);
        }
        if (!TextUtils.isEmpty(paramPluginStatInfo.plugin_download_filename)) {
          localHashMap.put("B9", paramPluginStatInfo.plugin_download_filename);
        }
        if (!TextUtils.isEmpty(paramPluginStatInfo.plugin_install_dir)) {
          localHashMap.put("B10", paramPluginStatInfo.plugin_install_dir);
        }
        if (!TextUtils.isEmpty(paramPluginStatInfo.plugin_pluginlist_md5)) {
          localHashMap.put("B11", paramPluginStatInfo.plugin_pluginlist_md5);
        }
        if (!TextUtils.isEmpty(paramPluginStatInfo.plugin_white_list)) {
          localHashMap.put("B12", paramPluginStatInfo.plugin_white_list);
        }
        if (paramPluginStatInfo.time_used != 0L) {
          localHashMap.put("B13", String.valueOf(paramPluginStatInfo.time_used));
        }
        if (!TextUtils.isEmpty(paramPluginStatInfo.plugin_install_filename)) {
          localHashMap.put("B14", paramPluginStatInfo.plugin_install_filename);
        }
        if (!TextUtils.isEmpty(paramPluginStatInfo.plugin_data)) {
          localHashMap.put("B15", paramPluginStatInfo.plugin_data);
        }
        if (!TextUtils.isEmpty(paramPluginStatInfo.plugin_ext_info)) {
          localHashMap.put("B16", paramPluginStatInfo.plugin_ext_info);
        }
        if (paramPluginStatInfo.file_size != 0L) {
          localHashMap.put("B17", String.valueOf(paramPluginStatInfo.file_size));
        }
        if (paramPluginStatInfo.pluginlist_refresh_time != 0L) {
          localHashMap.put("B19", String.valueOf(paramPluginStatInfo.pluginlist_refresh_time));
        }
        StringBuilder localStringBuilder1 = new StringBuilder();
        localStringBuilder1.append("OpType=");
        localStringBuilder1.append(paramPluginStatInfo.pluign_op_type);
        localStringBuilder1.append(",Detail=");
        localStringBuilder1.append(localHashMap.toString());
        FLogger.d("PluginStatInfo", localStringBuilder1.toString());
        if (mContext != null) {
          localIPluginRelateFunc.upLoadToBeacon("MTT_UPLOAD_PLUGIN", localHashMap);
        }
        return;
      }
    }
  }
  
  public static void userBehaviorStatistics(String paramString)
  {
    QBPluginServiceImpl.IPluginRelateFunc localIPluginRelateFunc = QBPluginServiceImpl.msRelateFunc;
    if (localIPluginRelateFunc != null) {
      localIPluginRelateFunc.userBehaviorStatistics(paramString);
    }
  }
  
  public static class PluginStatInfo
  {
    public long file_size;
    public StringBuilder log_path = new StringBuilder();
    public String plugin_data;
    public String plugin_download_dir;
    public String plugin_download_filename;
    public String plugin_ext_info;
    public String plugin_install_dir;
    public String plugin_install_filename;
    public String plugin_install_version;
    public int plugin_load_type;
    public int plugin_loadok_order;
    public String plugin_local_md5;
    public String plugin_pkgname;
    public String plugin_pluginlist_md5;
    public int plugin_use_count;
    public String plugin_white_list;
    public long pluginlist_refresh_time = 0L;
    public int pluign_op_type;
    public int request_plugin_count = 0;
    public int ret_code;
    public long time_end;
    public long time_start;
    public long time_used;
    public long wait_checkin_time;
    public long wait_download_time;
    
    public void setOpType(int paramInt)
    {
      this.pluign_op_type = paramInt;
      if (this.time_start == 0L) {
        this.time_start = System.currentTimeMillis();
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\plugin\PluginStatBehavior.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */