package com.tencent.common.plugin;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import com.tencent.basesupport.ModuleProxy;
import com.tencent.basesupport.PackageInfo;
import com.tencent.common.connectivitydetect.ConnectivityDetector;
import com.tencent.common.manifest.AppManifest;
import com.tencent.common.manifest.annotation.Extension;
import com.tencent.common.manifest.annotation.Service;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.common.utils.ByteUtils;
import com.tencent.common.utils.FileUtilsF;
import com.tencent.common.utils.Md5Utils;
import com.tencent.common.utils.StringUtils;
import com.tencent.mtt.ContextHolder;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class QBPluginSystem
  implements QBPluginFactory.IBindPluginCallback
{
  public static final int ERROR_BINDSERVER_FAILED = 3004;
  public static final int ERROR_CPU_NOT_SUPPORT = 3013;
  public static final int ERROR_EXCEPTION = 3006;
  public static final int ERROR_FORCE_USELOACL_BUT_NOTPREPARE = 3033;
  public static final int ERROR_GETPLUGININFO_FAILED = 3031;
  public static final int ERROR_GETPLUGININFO_FAILED_GETLISTFAILD = 3028;
  public static final int ERROR_GETPLUGININFO_FAILED_NOPLUGINIFO = 3029;
  public static final int ERROR_GETPLUGININFO_SENDWUP_ERROR = 3030;
  public static final int ERROR_INSTALL_FAILED = 3005;
  public static final int ERROR_INVALID_PLUGIN_URL = 3007;
  public static final int ERROR_LOAD_PARAM_ERROR = 3034;
  public static final int ERROR_NO_NETWORK = 3012;
  public static final int ERROR_NO_SPACE = 3014;
  public static final int ERROR_STARTDOWNLOAD_FAILED = 3011;
  public static final int ERROR_STARTDOWNLOAD_FAILED_EXCP = 3017;
  public static final int ERROR_STARTDOWNLOAD_FAILED_NOINFO = 3015;
  public static final int ERROR_STARTDOWNLOAD_FAILED_NOINFO_CNN = 3018;
  public static final int ERROR_STARTDOWNLOAD_FAILED_TES_SPECIAL = 3016;
  public static final int ERROR_USER_STOP = 3010;
  public static final int ERROR_USE_PLUGIN_SERVICE_NULL = 3001;
  public static final int ERR_DOWNLOAD_OTHER_BASE = 4000;
  public static final int ERR_INSTALL_ERROR_DATA_SPACE_NOMORE = 6008;
  public static final int ERR_INSTALL_ERROR_FILEMD5_CHECKSIGN_FAIL = 6006;
  public static final int ERR_INSTALL_ERROR_FILEMD5_NOT_MATCH = 6005;
  public static final int ERR_INSTALL_ERROR_FILEORDIR_EMPTY = 6001;
  public static final int ERR_INSTALL_ERROR_FILESIZE_NOT_MATCH = 6004;
  public static final int ERR_INSTALL_ERROR_FILE_NOT_EXIST = 6003;
  public static final int ERR_INSTALL_ERROR_GENPLUGIN_DATAFILE_ERROR = 6010;
  public static final int ERR_INSTALL_ERROR_GETINSTALLDIR_FAILD = 6007;
  public static final int ERR_INSTALL_ERROR_OTHER_PROCESS_INSTALLING = 6011;
  public static final int ERR_INSTALL_ERROR_UNZIP_ERROR = 6009;
  public static final int ERR_INSTALL_ERROR_USERFORBIT_INSTALL = 6002;
  public static final int ERR_LOAD_FAILED_BASE = 5000;
  public static final int FROM_INIT = 0;
  public static final int FROM_QB = 1;
  public static final int FROM_TBS = 2;
  public static int LOAD_PLUGIN_FIRST = 0;
  public static int LOAD_PLUGIN_MORE_NOSAME = 2;
  public static int LOAD_PLUGIN_MORE_SAME = 0;
  public static final long MAXTIME_WAIT_DOWNLOADTASK_PROGRESS = 60000L;
  public static final long MAXTIME_WAIT_WUPREQUEST_RESULT = 10000L;
  public static final long MINTIME_CHECK_TIME_INTERVAL = 10000L;
  public static final int MSG_CALLBACK_ONDOWNLOAD_CREATED = 6;
  public static final int MSG_CALLBACK_ONDOWNLOAD_PROGRESS = 7;
  public static final int MSG_CALLBACK_ONDOWNLOAD_STARTED = 5;
  public static final int MSG_CALLBACK_ONDOWNLOAD_SUCCESS = 8;
  public static final int MSG_CALLBACK_ONNOTIFY_DOWNLOAD = 9;
  public static final int MSG_CALLBACK_ONPREPARE_FINISHED = 3;
  public static final int MSG_CALLBACK_ONPREPARE_START = 4;
  public static final int MSG_PLUGIN_BACK_DOWNLOAD_ONLY = 8;
  public static final int MSG_PLUGIN_CHECK_INSTALL_TIME_BASE = 8000;
  public static final int MSG_PLUGIN_CHECK_PROGRESS_TIME_BASE = 2000;
  public static final int MSG_PLUGIN_CHECK_TIMEOUT = 11;
  public static final int MSG_PLUGIN_GET_PLUGIN_LIST = 4;
  public static final int MSG_PLUGIN_INIT_PLUGIN_STATUS = 9;
  public static final int MSG_PLUGIN_INSTALL_FAILD = 13;
  public static final int MSG_PLUGIN_INSTALL_PLUGIN = 10;
  public static final int MSG_PLUGIN_INSTALL_SUCCESSED = 12;
  public static final int MSG_PLUGIN_LOAD_PLUGIN_AND_DOWNLOAD = 6;
  public static final int MSG_PLUGIN_REBIND_SERCER = 3;
  public static final int MSG_PLUGIN_START_USE_PLUGIN = 2;
  public static final int MSG_PLUGIN_START_USE_PLUGIN_AFTER_GETINFO = 1;
  public static final int MSG_PLUGIN_STOP = 7;
  public static final int PLUGIN_FLAG_BACKDOWNLOAD_ONLY = 4;
  public static final int PLUGIN_FLAG_FORTRYLOCAL = 2;
  public static final int PLUGIN_FLAG_NORMAL = 0;
  public static final int PLUGIN_FLAG_PRELOADUSE = 1;
  public static final String PLUGIN_PKG_PREFIX_FONT;
  public static final int PLUGIN_POS_NULL = 0;
  public static final int PLUGIN_POS_SETTING = 2;
  public static final int PLUGIN_POS_TOOL = 1;
  public static final int PLUGIN_STATUS_DOWNLOADED = 4;
  public static final int PLUGIN_STATUS_DOWNLOADING = 3;
  public static final int PLUGIN_STATUS_GETPLGINFO = 2;
  public static final int PLUGIN_STATUS_GETPLGINFOING = 1;
  public static final int PLUGIN_STATUS_INIT = -1;
  public static final int PLUGIN_STATUS_INSTALLED = 6;
  public static final int PLUGIN_STATUS_INSTALLING = 5;
  public static final int PLUGIN_STATUS_LOADED = 8;
  public static final int PLUGIN_STATUS_LOADING = 7;
  public static final int PLUGIN_STATUS_START = 0;
  public static final int PLUGIN_TYPE_ADDON = 6;
  public static final int PLUGIN_TYPE_FEEDSPLUGIN = 12;
  public static final int PLUGIN_TYPE_FONT = 9;
  public static final int PLUGIN_TYPE_JAR = 2;
  public static final int PLUGIN_TYPE_MIUIPLUGIN = 13;
  public static final int PLUGIN_TYPE_NONE = 0;
  public static final int PLUGIN_TYPE_NOVELPLUGIN = 11;
  public static final int PLUGIN_TYPE_OTHER = 3;
  public static final int PLUGIN_TYPE_PLAYER = 7;
  public static final int PLUGIN_TYPE_QBLINK = 5;
  public static final int PLUGIN_TYPE_SNIFFERPLUGIN = 10;
  public static final int PLUGIN_TYPE_ZIP = 1;
  public static final int PLUGIN_USE_TYPE_INI = 0;
  public static final int PlUGIN_PREPARE_OK = 0;
  public static final int PlUGIN_PREPARE_TYPE_ERROR_BINDPLUGIN = 4;
  public static final int PlUGIN_PREPARE_TYPE_ERROR_DOWN = 1;
  public static final int PlUGIN_PREPARE_TYPE_ERROR_GETPLUGININFO = 5;
  public static final int PlUGIN_PREPARE_TYPE_ERROR_INSTALL = 2;
  public static final int PlUGIN_PREPARE_TYPE_ERROR_LOAD = 3;
  public static final int PlUGIN_PREPARE_UNKNOW_ERROR = -1;
  public static final int QBPLUGIN_UPDATETYPE_INSTALL_FORCE_UPDATE = 3;
  public static final int QBPLUGIN_UPDATETYPE_INSTALL_NEED_UNZIP = 4;
  public static final int QBPLUGIN_UPDATETYPE_INSTALL_NONEED_UPDATE = 1;
  public static final int QBPLUGIN_UPDATETYPE_INSTALL_RECOMMAND_UPDATE = 2;
  public static final int QBPLUGIN_UPDATETYPE_NOT_INSTALL = 0;
  public static final int QBPLUGIN_UPDATETYPE_PARAM_ERROR = -1;
  public static final int QBPLUGIN_UPDATETYPE_WUP_NO_RESULT = -2;
  public static String TAG = "QBPluginSystem";
  private static IQBPluginServiceProvider jdField_a_of_type_ComTencentCommonPluginQBPluginSystem$IQBPluginServiceProvider;
  private static QBPluginSystem jdField_a_of_type_ComTencentCommonPluginQBPluginSystem;
  public static boolean gTbsPluginInit = false;
  private int jdField_a_of_type_Int = 0;
  Context jdField_a_of_type_AndroidContentContext = null;
  Handler jdField_a_of_type_AndroidOsHandler = null;
  QBPluginProxy jdField_a_of_type_ComTencentCommonPluginQBPluginProxy = null;
  d jdField_a_of_type_ComTencentCommonPluginQBPluginSystem$d = null;
  Object jdField_a_of_type_JavaLangObject = new Object();
  ArrayList<Object[]> jdField_a_of_type_JavaUtilArrayList = new ArrayList();
  HashMap<PluginPkgKey, PluginInfo> jdField_a_of_type_JavaUtilHashMap = null;
  boolean jdField_a_of_type_Boolean = false;
  Handler jdField_b_of_type_AndroidOsHandler = null;
  boolean jdField_b_of_type_Boolean = false;
  
  static
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(PackageInfo.PKGNAME());
    localStringBuilder.append(".plugin.font");
    PLUGIN_PKG_PREFIX_FONT = localStringBuilder.toString();
    LOAD_PLUGIN_FIRST = 0;
    LOAD_PLUGIN_MORE_SAME = 1;
  }
  
  private QBPluginSystem(Context paramContext)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    if (this.jdField_a_of_type_JavaUtilHashMap == null) {
      this.jdField_a_of_type_JavaUtilHashMap = new HashMap();
    }
    this.jdField_b_of_type_AndroidOsHandler = new Handler(Looper.getMainLooper())
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        int m = paramAnonymousMessage.what;
        int k = 0;
        int j = 0;
        int i = 0;
        Object localObject1;
        Object localObject2;
        long l;
        Object localObject3;
        switch (m)
        {
        default: 
          return;
        case 8: 
          localObject1 = (QBPluginSystem.DownloadMsgObject)paramAnonymousMessage.obj;
          paramAnonymousMessage = new QBPluginSystem.PluginPkgKey(((QBPluginSystem.DownloadMsgObject)localObject1).packageName, ((QBPluginSystem.DownloadMsgObject)localObject1).infoFrom);
          localObject2 = QBPluginSystem.a(QBPluginSystem.this, paramAnonymousMessage);
          if (localObject2 == null) {
            return;
          }
          l = ((QBPluginSystem.PluginInfo)localObject2).mStartTimeOfTaskStep;
          localObject3 = QBPluginSystem.a(QBPluginSystem.this, (QBPluginSystem.PluginInfo)localObject2);
          i = 0;
          while (i < ((ArrayList)localObject3).size())
          {
            if (((ArrayList)localObject3).get(i) != null) {
              ((IQBPluginSystemCallback)((ArrayList)localObject3).get(i)).onDownloadSuccessed(((QBPluginSystem.DownloadMsgObject)localObject1).packageName, ((QBPluginSystem.DownloadMsgObject)localObject1).downloadDir);
            }
            i += 1;
          }
          localObject3 = ((QBPluginSystem.DownloadMsgObject)localObject1).packageName;
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("617(");
          localStringBuilder.append(System.currentTimeMillis() - l);
          localStringBuilder.append(")");
          PluginStatBehavior.addLogPath((String)localObject3, 4, localStringBuilder.toString());
          localObject3 = QBPluginSystem.TAG;
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("MSG_CALLBACK_ONDOWNLOAD_SUCCESS: ");
          localStringBuilder.append(((QBPluginSystem.DownloadMsgObject)localObject1).packageName);
          localStringBuilder.append(", PLUGIN_FLAG_BACKDOWNLOAD_ONLY=");
          localStringBuilder.append(((QBPluginSystem.PluginInfo)localObject2).isPluginFlagOn(4));
          localStringBuilder.append(", PLUGIN_FLAG_PRELOADUSE=");
          localStringBuilder.append(((QBPluginSystem.PluginInfo)localObject2).isPluginFlagOn(1));
          FLogger.i((String)localObject3, localStringBuilder.toString());
          if (((QBPluginSystem.PluginInfo)localObject2).isPluginFlagOn(1))
          {
            localObject3 = QBPluginSystem.TAG;
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("skip unzip: ");
            localStringBuilder.append(((QBPluginSystem.DownloadMsgObject)localObject1).packageName);
            FLogger.i((String)localObject3, localStringBuilder.toString());
            ((QBPluginSystem.PluginInfo)localObject2).initPluginStatus();
            QBPluginSystem.this.b(paramAnonymousMessage, 0, 1);
            return;
          }
          ((QBPluginSystem.PluginInfo)localObject2).mStartTimeOfTaskStep = System.currentTimeMillis();
          localObject1 = QBPluginSystem.this.a.obtainMessage();
          ((Message)localObject1).what = 10;
          ((Message)localObject1).obj = paramAnonymousMessage;
          QBPluginSystem.this.a.sendMessage((Message)localObject1);
          return;
        case 7: 
          paramAnonymousMessage = (QBPluginSystem.DownloadMsgObject)paramAnonymousMessage.obj;
          localObject1 = QBPluginSystem.a(QBPluginSystem.this, new QBPluginSystem.PluginPkgKey(paramAnonymousMessage.packageName, paramAnonymousMessage.infoFrom));
          if (localObject1 == null) {
            return;
          }
          ((QBPluginSystem.PluginInfo)localObject1).mStartTimeOfTaskStep = System.currentTimeMillis();
          localObject1 = QBPluginSystem.a(QBPluginSystem.this, (QBPluginSystem.PluginInfo)localObject1);
        }
        while (i < ((ArrayList)localObject1).size())
        {
          if (((ArrayList)localObject1).get(i) != null) {
            ((IQBPluginSystemCallback)((ArrayList)localObject1).get(i)).onDownloadProgress(paramAnonymousMessage.packageName, paramAnonymousMessage.progress, paramAnonymousMessage.progress);
          }
          i += 1;
          continue;
          paramAnonymousMessage = (QBPluginSystem.DownloadMsgObject)paramAnonymousMessage.obj;
          localObject1 = QBPluginSystem.a(QBPluginSystem.this, new QBPluginSystem.PluginPkgKey(paramAnonymousMessage.packageName, paramAnonymousMessage.infoFrom));
          if (localObject1 == null) {
            return;
          }
          ((QBPluginSystem.PluginInfo)localObject1).mStartTimeOfTaskStep = System.currentTimeMillis();
          localObject1 = QBPluginSystem.a(QBPluginSystem.this, (QBPluginSystem.PluginInfo)localObject1);
          i = k;
          while (i < ((ArrayList)localObject1).size())
          {
            if (((ArrayList)localObject1).get(i) != null) {
              ((IQBPluginSystemCallback)((ArrayList)localObject1).get(i)).onDownloadCreateed(paramAnonymousMessage.packageName, paramAnonymousMessage.url);
            }
            i += 1;
            continue;
            paramAnonymousMessage = (QBPluginSystem.DownloadMsgObject)paramAnonymousMessage.obj;
            localObject1 = QBPluginSystem.a(QBPluginSystem.this, new QBPluginSystem.PluginPkgKey(paramAnonymousMessage.packageName, paramAnonymousMessage.infoFrom));
            if (localObject1 == null) {
              return;
            }
            l = ((QBPluginSystem.PluginInfo)localObject1).mStartTimeOfTaskStep;
            ((QBPluginSystem.PluginInfo)localObject1).mStartTimeOfTaskStep = System.currentTimeMillis();
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("609(");
            ((StringBuilder)localObject2).append(((QBPluginSystem.PluginInfo)localObject1).mStartTimeOfTaskStep - l);
            ((StringBuilder)localObject2).append(")");
            localObject2 = ((StringBuilder)localObject2).toString();
            PluginStatBehavior.addLogPath(paramAnonymousMessage.packageName, 4, (String)localObject2);
            localObject2 = QBPluginSystem.a(QBPluginSystem.this, (QBPluginSystem.PluginInfo)localObject1);
            i = 0;
            while (i < ((ArrayList)localObject2).size())
            {
              if (((ArrayList)localObject2).get(i) != null) {
                ((IQBPluginSystemCallback)((ArrayList)localObject2).get(i)).onDownloadStart(paramAnonymousMessage.packageName, StringUtils.parseInt(((QBPluginSystem.PluginInfo)localObject1).mPluginItemInfo.mPackageSize, 0));
              }
              i += 1;
              continue;
              paramAnonymousMessage = (QBPluginSystem.MsgClassPluginCallback)paramAnonymousMessage.obj;
              paramAnonymousMessage.mCallback.onPrepareStart(paramAnonymousMessage.mPluginKey.pkgName);
              return;
              localObject1 = (QBPluginSystem.PluginPkgKey)paramAnonymousMessage.obj;
              if (paramAnonymousMessage.arg1 == 0) {
                i = 0;
              } else {
                i = paramAnonymousMessage.arg2;
              }
              localObject2 = QBPluginSystem.TAG;
              localObject3 = new StringBuilder();
              ((StringBuilder)localObject3).append("onPrepareFinished packageName:");
              ((StringBuilder)localObject3).append(((QBPluginSystem.PluginPkgKey)localObject1).pkgName);
              ((StringBuilder)localObject3).append(", failOpType:");
              ((StringBuilder)localObject3).append(i);
              ((StringBuilder)localObject3).append(", errCode:");
              ((StringBuilder)localObject3).append(paramAnonymousMessage.arg1);
              FLogger.i((String)localObject2, ((StringBuilder)localObject3).toString());
              localObject2 = QBPluginSystem.a(QBPluginSystem.this, (QBPluginSystem.PluginPkgKey)localObject1);
              localObject3 = QBPluginSystem.a(QBPluginSystem.this, (QBPluginSystem.PluginInfo)localObject2);
              while (j < ((ArrayList)localObject3).size())
              {
                if (((ArrayList)localObject3).get(j) != null) {
                  ((IQBPluginSystemCallback)((ArrayList)localObject3).get(j)).onPrepareFinished(((QBPluginSystem.PluginPkgKey)localObject1).pkgName, ((QBPluginSystem.PluginInfo)localObject2).mPluginItemInfo, i, paramAnonymousMessage.arg1);
                }
                j += 1;
              }
              QBPluginSystem.this.a((QBPluginSystem.PluginPkgKey)localObject1);
            }
          }
        }
      }
    };
    paramContext = new HandlerThread("pluginThread");
    paramContext.start();
    this.jdField_a_of_type_AndroidOsHandler = new Handler(paramContext.getLooper())
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        int k = paramAnonymousMessage.what;
        int i = 0;
        int j = 0;
        Object localObject1 = null;
        Object localObject4;
        long l;
        Object localObject5;
        Object localObject6;
        switch (k)
        {
        case 4: 
        case 5: 
        case 6: 
        default: 
          return;
        case 13: 
          localObject1 = new QBPluginSystem.b(null);
          ((QBPluginSystem.b)localObject1).jdField_a_of_type_Int = QBPluginSystem.LOAD_PLUGIN_FIRST;
          ??? = (QBPluginSystem.PluginPkgKey)paramAnonymousMessage.obj;
          localObject4 = QBPluginSystem.a(QBPluginSystem.this, (QBPluginSystem.PluginPkgKey)???);
          l = ((QBPluginSystem.PluginInfo)localObject4).mStartTimeOfTaskStep;
          localObject5 = QBPluginSystem.TAG;
          localObject6 = new StringBuilder();
          ((StringBuilder)localObject6).append("插件系统使用主流程:");
          ((StringBuilder)localObject6).append(((QBPluginSystem.PluginPkgKey)???).pkgName);
          ((StringBuilder)localObject6).append(",from=");
          ((StringBuilder)localObject6).append(((QBPluginSystem.PluginPkgKey)???).infoFrom);
          ((StringBuilder)localObject6).append(",安装失败流程结束，from本地");
          FLogger.i((String)localObject5, ((StringBuilder)localObject6).toString());
          ((QBPluginSystem.PluginInfo)localObject4).initPluginStatus();
          localObject5 = ((QBPluginSystem.PluginPkgKey)???).pkgName;
          localObject6 = new StringBuilder();
          ((StringBuilder)localObject6).append("652(");
          ((StringBuilder)localObject6).append(((QBPluginSystem.PluginInfo)localObject4).mStartTimeOfTaskStep - l);
          ((StringBuilder)localObject6).append(")");
          PluginStatBehavior.addLogPath((String)localObject5, 4, ((StringBuilder)localObject6).toString());
          PluginStatBehavior.setFinCode(((QBPluginSystem.PluginPkgKey)???).pkgName, 4, 652);
          ((QBPluginSystem.b)localObject1).b = paramAnonymousMessage.arg1;
          ((QBPluginSystem.b)localObject1).c = 2;
          if (paramAnonymousMessage.arg1 == 6009)
          {
            paramAnonymousMessage = ((QBPluginSystem.PluginInfo)localObject4).mPluginItemInfo.mAntiHijackUrl;
            if (!TextUtils.isEmpty(paramAnonymousMessage))
            {
              ((QBPluginSystem.PluginInfo)localObject4).mPluginItemInfo.mAntiHijackUrl = ((QBPluginSystem.PluginInfo)localObject4).mPluginItemInfo.mUrl;
              ((QBPluginSystem.PluginInfo)localObject4).mPluginItemInfo.mUrl = paramAnonymousMessage;
              QBPluginDBHelper.getInstance(QBPluginServiceImpl.getInstance().callerAppContext()).setPluginUrl(((QBPluginSystem.PluginInfo)localObject4).mPluginItemInfo.mPackageName, ((QBPluginSystem.PluginInfo)localObject4).mPluginItemInfo.mUrl, ((QBPluginSystem.PluginInfo)localObject4).mPluginItemInfo.mInfoFrom);
              QBPluginDBHelper.getInstance(QBPluginServiceImpl.getInstance().callerAppContext()).setPluginHijackUrl(((QBPluginSystem.PluginInfo)localObject4).mPluginItemInfo.mPackageName, ((QBPluginSystem.PluginInfo)localObject4).mPluginItemInfo.mAntiHijackUrl, ((QBPluginSystem.PluginInfo)localObject4).mPluginItemInfo.mInfoFrom);
            }
          }
          QBPluginSystem.this.a((QBPluginSystem.PluginPkgKey)???, (QBPluginSystem.PluginInfo)localObject4, (QBPluginSystem.b)localObject1);
          return;
        case 12: 
          localObject4 = (QBPluginItemInfo)paramAnonymousMessage.obj;
          if (localObject4 != null)
          {
            localObject1 = new QBPluginSystem.b(null);
            ((QBPluginSystem.b)localObject1).jdField_a_of_type_Int = QBPluginSystem.LOAD_PLUGIN_FIRST;
            paramAnonymousMessage = new QBPluginSystem.PluginPkgKey(((QBPluginItemInfo)localObject4).mPackageName, paramAnonymousMessage.arg2);
            ??? = QBPluginSystem.a(QBPluginSystem.this, paramAnonymousMessage);
            l = ((QBPluginSystem.PluginInfo)???).mStartTimeOfTaskStep;
            ((QBPluginSystem.PluginInfo)???).mPluginItemInfo.mIsInstall = ((QBPluginItemInfo)localObject4).mIsInstall;
            ((QBPluginSystem.PluginInfo)???).mPluginItemInfo.mInstallDir = ((QBPluginItemInfo)localObject4).mInstallDir;
            ((QBPluginSystem.PluginInfo)???).mPluginItemInfo.mUnzipDir = ((QBPluginItemInfo)localObject4).mUnzipDir;
            ((QBPluginSystem.PluginInfo)???).mPluginItemInfo.mDownloadFileName = ((QBPluginItemInfo)localObject4).mDownloadFileName;
            ((QBPluginSystem.PluginInfo)???).mPluginItemInfo.mInstallVersion = ((QBPluginItemInfo)localObject4).mInstallVersion;
            ((QBPluginSystem.PluginInfo)???).setPluginStatus(6);
            localObject4 = QBPluginSystem.TAG;
            localObject5 = new StringBuilder();
            ((StringBuilder)localObject5).append("插件系统使用主流程:");
            ((StringBuilder)localObject5).append(paramAnonymousMessage.pkgName);
            ((StringBuilder)localObject5).append(",from=");
            ((StringBuilder)localObject5).append(paramAnonymousMessage.infoFrom);
            ((StringBuilder)localObject5).append(",安装成功，from下载");
            FLogger.i((String)localObject4, ((StringBuilder)localObject5).toString());
            localObject4 = QBPluginSystem.TAG;
            localObject5 = new StringBuilder();
            ((StringBuilder)localObject5).append("插件系统使用主流程:");
            ((StringBuilder)localObject5).append(paramAnonymousMessage.pkgName);
            ((StringBuilder)localObject5).append(" 安装成功 ");
            ((StringBuilder)localObject5).append(((QBPluginSystem.PluginInfo)???).mPluginItemInfo.mInstallDir);
            ((StringBuilder)localObject5).append(",unzipDir=");
            ((StringBuilder)localObject5).append(((QBPluginSystem.PluginInfo)???).mPluginItemInfo.mUnzipDir);
            FLogger.i((String)localObject4, ((StringBuilder)localObject5).toString());
            localObject4 = paramAnonymousMessage.pkgName;
            localObject5 = new StringBuilder();
            ((StringBuilder)localObject5).append("50(");
            ((StringBuilder)localObject5).append(((QBPluginSystem.PluginInfo)???).mStartTimeOfTaskStep - l);
            ((StringBuilder)localObject5).append(")");
            PluginStatBehavior.addLogPath((String)localObject4, 4, ((StringBuilder)localObject5).toString());
            QBPluginSystem.this.a(paramAnonymousMessage, (QBPluginSystem.PluginInfo)???, (QBPluginSystem.b)localObject1);
            return;
          }
          break;
        case 11: 
          QBPluginSystem.this.jdField_a_of_type_AndroidOsHandler.removeMessages(11);
        }
        label3346:
        label3363:
        for (;;)
        {
          synchronized (QBPluginSystem.this.jdField_a_of_type_JavaUtilHashMap)
          {
            localObject4 = QBPluginSystem.this.jdField_a_of_type_JavaUtilHashMap.entrySet().iterator();
            l = System.currentTimeMillis();
            i = j;
            if (((Iterator)localObject4).hasNext())
            {
              paramAnonymousMessage = (Map.Entry)((Iterator)localObject4).next();
              localObject5 = (QBPluginSystem.PluginInfo)paramAnonymousMessage.getValue();
              localObject6 = (QBPluginSystem.PluginPkgKey)paramAnonymousMessage.getKey();
              if ((!((QBPluginSystem.PluginInfo)localObject5).mCheckTimedoCheck) && (l - ((QBPluginSystem.PluginInfo)localObject5).mStartTimeOfTaskStep > 60000L))
              {
                j = ((QBPluginSystem.PluginInfo)localObject5).getPluginStatus();
                if (j != 3) {
                  if (j != 5) {
                    if (j == 7) {}
                  }
                }
              }
            }
            switch (j)
            {
            case 0: 
            case 1: 
              paramAnonymousMessage = QBPluginSystem.TAG;
              localObject1 = new StringBuilder();
              ((StringBuilder)localObject1).append("插件=");
              ((StringBuilder)localObject1).append(((QBPluginSystem.PluginPkgKey)localObject6).pkgName);
              ((StringBuilder)localObject1).append(",status=");
              ((StringBuilder)localObject1).append(((QBPluginSystem.PluginInfo)localObject5).getPluginStatus());
              ((StringBuilder)localObject1).append(",超时=");
              ((StringBuilder)localObject1).append(l - ((QBPluginSystem.PluginInfo)localObject5).mStartTimeOfTaskStep);
              FLogger.i(paramAnonymousMessage, ((StringBuilder)localObject1).toString());
              PluginStatBehavior.setOpType(((QBPluginSystem.PluginPkgKey)localObject6).pkgName, 4);
              paramAnonymousMessage = new StringBuilder();
              paramAnonymousMessage.append("660(");
              paramAnonymousMessage.append(l - ((QBPluginSystem.PluginInfo)localObject5).mStartTimeOfTaskStep);
              paramAnonymousMessage.append("_");
              paramAnonymousMessage.append(((QBPluginSystem.PluginPkgKey)localObject6).pkgName);
              paramAnonymousMessage.append(")");
              paramAnonymousMessage = paramAnonymousMessage.toString();
              PluginStatBehavior.addLogPath(((QBPluginSystem.PluginPkgKey)localObject6).pkgName, 4, paramAnonymousMessage);
              PluginStatBehavior.setFinCode(((QBPluginSystem.PluginPkgKey)localObject6).pkgName, 4, 660);
              continue;
              paramAnonymousMessage = QBPluginSystem.TAG;
              localObject1 = new StringBuilder();
              ((StringBuilder)localObject1).append("插件=");
              ((StringBuilder)localObject1).append(((QBPluginSystem.PluginPkgKey)localObject6).pkgName);
              ((StringBuilder)localObject1).append(",status=");
              ((StringBuilder)localObject1).append(((QBPluginSystem.PluginInfo)localObject5).getPluginStatus());
              ((StringBuilder)localObject1).append(",超时=");
              ((StringBuilder)localObject1).append(l - ((QBPluginSystem.PluginInfo)localObject5).mStartTimeOfTaskStep);
              FLogger.i(paramAnonymousMessage, ((StringBuilder)localObject1).toString());
              PluginStatBehavior.setOpType(((QBPluginSystem.PluginPkgKey)localObject6).pkgName, 4);
              paramAnonymousMessage = new StringBuilder();
              paramAnonymousMessage.append("662(");
              paramAnonymousMessage.append(l - ((QBPluginSystem.PluginInfo)localObject5).mStartTimeOfTaskStep);
              paramAnonymousMessage.append("_");
              paramAnonymousMessage.append(((QBPluginSystem.PluginPkgKey)localObject6).pkgName);
              paramAnonymousMessage.append(")");
              paramAnonymousMessage = paramAnonymousMessage.toString();
              PluginStatBehavior.addLogPath(((QBPluginSystem.PluginPkgKey)localObject6).pkgName, 4, paramAnonymousMessage);
              PluginStatBehavior.setFinCode(((QBPluginSystem.PluginPkgKey)localObject6).pkgName, 4, 662);
              continue;
              paramAnonymousMessage = QBPluginSystem.TAG;
              localObject1 = new StringBuilder();
              ((StringBuilder)localObject1).append("插件=");
              ((StringBuilder)localObject1).append(((QBPluginSystem.PluginPkgKey)localObject6).pkgName);
              ((StringBuilder)localObject1).append(",status=");
              ((StringBuilder)localObject1).append(((QBPluginSystem.PluginInfo)localObject5).getPluginStatus());
              ((StringBuilder)localObject1).append(",超时=");
              ((StringBuilder)localObject1).append(l - ((QBPluginSystem.PluginInfo)localObject5).mStartTimeOfTaskStep);
              FLogger.i(paramAnonymousMessage, ((StringBuilder)localObject1).toString());
              PluginStatBehavior.setOpType(((QBPluginSystem.PluginPkgKey)localObject6).pkgName, 4);
              paramAnonymousMessage = new StringBuilder();
              paramAnonymousMessage.append("661(");
              paramAnonymousMessage.append(l - ((QBPluginSystem.PluginInfo)localObject5).mStartTimeOfTaskStep);
              paramAnonymousMessage.append("_");
              paramAnonymousMessage.append(((QBPluginSystem.PluginPkgKey)localObject6).pkgName);
              paramAnonymousMessage.append(")");
              paramAnonymousMessage = paramAnonymousMessage.toString();
              PluginStatBehavior.addLogPath(((QBPluginSystem.PluginPkgKey)localObject6).pkgName, 4, paramAnonymousMessage);
              PluginStatBehavior.setFinCode(((QBPluginSystem.PluginPkgKey)localObject6).pkgName, 4, 661);
              continue;
              paramAnonymousMessage = QBPluginSystem.TAG;
              localObject1 = new StringBuilder();
              ((StringBuilder)localObject1).append("插件=");
              ((StringBuilder)localObject1).append(((QBPluginSystem.PluginPkgKey)localObject6).pkgName);
              ((StringBuilder)localObject1).append(",status=");
              ((StringBuilder)localObject1).append(((QBPluginSystem.PluginInfo)localObject5).getPluginStatus());
              ((StringBuilder)localObject1).append(",超时=");
              ((StringBuilder)localObject1).append(l - ((QBPluginSystem.PluginInfo)localObject5).mStartTimeOfTaskStep);
              FLogger.i(paramAnonymousMessage, ((StringBuilder)localObject1).toString());
              PluginStatBehavior.setOpType(((QBPluginSystem.PluginPkgKey)localObject6).pkgName, 4);
              localObject1 = "";
              if (((QBPluginSystem.PluginInfo)localObject5).mPluginItemInfo != null)
              {
                localObject7 = ((QBPluginSystem.PluginInfo)localObject5).mPluginItemInfo.mDownloadDir;
                String str1 = ((QBPluginSystem.PluginInfo)localObject5).mPluginItemInfo.mPackageName;
                String str2 = ((QBPluginSystem.PluginInfo)localObject5).mPluginItemInfo.mVersion;
                paramAnonymousMessage = (Message)localObject1;
                if (!TextUtils.isEmpty((CharSequence)localObject7))
                {
                  paramAnonymousMessage = (Message)localObject1;
                  if (!TextUtils.isEmpty(str1))
                  {
                    paramAnonymousMessage = (Message)localObject1;
                    if (!TextUtils.isEmpty(str2))
                    {
                      paramAnonymousMessage = new StringBuilder();
                      paramAnonymousMessage.append(str1);
                      paramAnonymousMessage.append(str2);
                      paramAnonymousMessage.append(".zip");
                      localObject1 = paramAnonymousMessage.toString();
                      paramAnonymousMessage = new File((String)localObject7, (String)localObject1);
                      localObject7 = new StringBuilder();
                      ((StringBuilder)localObject7).append("");
                      ((StringBuilder)localObject7).append((String)localObject1);
                      ((StringBuilder)localObject7).append("_");
                      localObject1 = ((StringBuilder)localObject7).toString();
                      if ((paramAnonymousMessage.isFile()) && (paramAnonymousMessage.exists()))
                      {
                        localObject7 = new StringBuilder();
                        ((StringBuilder)localObject7).append((String)localObject1);
                        ((StringBuilder)localObject7).append(paramAnonymousMessage.length());
                        paramAnonymousMessage = ((StringBuilder)localObject7).toString();
                      }
                      else
                      {
                        paramAnonymousMessage = new StringBuilder();
                        paramAnonymousMessage.append((String)localObject1);
                        paramAnonymousMessage.append("NO_EXIST");
                        paramAnonymousMessage = paramAnonymousMessage.toString();
                      }
                    }
                  }
                }
              }
              else
              {
                paramAnonymousMessage = new StringBuilder();
                paramAnonymousMessage.append("");
                paramAnonymousMessage.append("NO_INFO");
                paramAnonymousMessage = paramAnonymousMessage.toString();
              }
              localObject1 = new StringBuilder();
              ((StringBuilder)localObject1).append("422(");
              ((StringBuilder)localObject1).append(l - ((QBPluginSystem.PluginInfo)localObject5).mStartTimeOfTaskStep);
              ((StringBuilder)localObject1).append("_");
              ((StringBuilder)localObject1).append(((QBPluginSystem.PluginPkgKey)localObject6).pkgName);
              ((StringBuilder)localObject1).append("_");
              ((StringBuilder)localObject1).append(paramAnonymousMessage);
              ((StringBuilder)localObject1).append(")");
              paramAnonymousMessage = ((StringBuilder)localObject1).toString();
              PluginStatBehavior.addLogPath(((QBPluginSystem.PluginPkgKey)localObject6).pkgName, 4, paramAnonymousMessage);
              PluginStatBehavior.setFinCode(((QBPluginSystem.PluginPkgKey)localObject6).pkgName, 4, 422);
              ((QBPluginSystem.PluginInfo)localObject5).mCheckTimedoCheck = true;
              if (((QBPluginSystem.PluginInfo)localObject5).mCheckTimedoCheck) {
                break label3363;
              }
              i = 1;
              break label3363;
              if (i != 0) {
                QBPluginSystem.a(QBPluginSystem.this);
              }
              return;
            }
          }
          paramAnonymousMessage = (QBPluginSystem.PluginPkgKey)paramAnonymousMessage.obj;
          localObject1 = QBPluginSystem.a(QBPluginSystem.this, paramAnonymousMessage);
          ((QBPluginSystem.PluginInfo)localObject1).setPluginStatus(4);
          ??? = new QBPluginSystem.b(null);
          ((QBPluginSystem.b)???).jdField_a_of_type_Boolean = true;
          QBPluginSystem.this.a(paramAnonymousMessage, (QBPluginSystem.PluginInfo)localObject1, (QBPluginSystem.b)???);
          return;
          paramAnonymousMessage = (QBPluginSystem.PluginPkgKey)paramAnonymousMessage.obj;
          paramAnonymousMessage = QBPluginSystem.a(QBPluginSystem.this, paramAnonymousMessage);
          if (paramAnonymousMessage != null)
          {
            paramAnonymousMessage.initPluginStatus();
            return;
            localObject1 = (QBPluginSystem.PluginPkgKey)paramAnonymousMessage.obj;
            i = paramAnonymousMessage.arg1;
            if (QBPluginSystem.this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy == null) {
              return;
            }
            paramAnonymousMessage = QBPluginSystem.TAG;
            ??? = new StringBuilder();
            ((StringBuilder)???).append("插件静默下载=");
            ((StringBuilder)???).append(((QBPluginSystem.PluginPkgKey)localObject1).pkgName);
            ((StringBuilder)???).append("需要下载 needCheck=");
            ((StringBuilder)???).append(i);
            FLogger.i(paramAnonymousMessage, ((StringBuilder)???).toString());
            if (i != 0) {}
          }
          try
          {
            bool = QBPluginSystem.this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy.isNewVersionFileDownloaded(((QBPluginSystem.PluginPkgKey)localObject1).pkgName, ((QBPluginSystem.PluginPkgKey)localObject1).infoFrom, null);
            paramAnonymousMessage = QBPluginSystem.TAG;
            ??? = new StringBuilder();
            ((StringBuilder)???).append("插件静默下载=");
            ((StringBuilder)???).append(((QBPluginSystem.PluginPkgKey)localObject1).pkgName);
            ((StringBuilder)???).append("需要下载 isFileDownloaded=");
            ((StringBuilder)???).append(bool);
            FLogger.i(paramAnonymousMessage, ((StringBuilder)???).toString());
            if ((bool) || (!QBPluginSystem.a(QBPluginSystem.this, (QBPluginSystem.PluginPkgKey)localObject1)) || (QBPluginSystem.this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy == null)) {
              break label3346;
            }
            QBPluginSystem.this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy.updatePluginNeesUpdateFlag(((QBPluginSystem.PluginPkgKey)localObject1).pkgName, 0, ((QBPluginSystem.PluginPkgKey)localObject1).infoFrom);
            return;
          }
          catch (RemoteException paramAnonymousMessage)
          {
            boolean bool;
            for (;;) {}
          }
          QBPluginFactory.getInstance(QBPluginSystem.this.jdField_a_of_type_AndroidContentContext).bindPluginService(QBPluginSystem.this, ((QBPluginSystem.PluginPkgKey)localObject1).infoFrom);
          return;
          try
          {
            paramAnonymousMessage = QBPluginSystem.this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy.getPluginInfo(((QBPluginSystem.PluginPkgKey)localObject1).pkgName, ((QBPluginSystem.PluginPkgKey)localObject1).infoFrom);
            if ((paramAnonymousMessage == null) || (paramAnonymousMessage.isNeedUpdate != 1)) {
              break label3346;
            }
            bool = QBPluginSystem.this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy.isNewVersionFileDownloaded(((QBPluginSystem.PluginPkgKey)localObject1).pkgName, ((QBPluginSystem.PluginPkgKey)localObject1).infoFrom, null);
            paramAnonymousMessage = QBPluginSystem.TAG;
            ??? = new StringBuilder();
            ((StringBuilder)???).append("插件静默下载=");
            ((StringBuilder)???).append(((QBPluginSystem.PluginPkgKey)localObject1).pkgName);
            ((StringBuilder)???).append("需要下载 isFileDownloaded=");
            ((StringBuilder)???).append(bool);
            FLogger.i(paramAnonymousMessage, ((StringBuilder)???).toString());
            if ((bool) || (!QBPluginSystem.a(QBPluginSystem.this, (QBPluginSystem.PluginPkgKey)localObject1)) || (QBPluginSystem.this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy == null)) {
              break label3346;
            }
            QBPluginSystem.this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy.updatePluginNeesUpdateFlag(((QBPluginSystem.PluginPkgKey)localObject1).pkgName, 0, ((QBPluginSystem.PluginPkgKey)localObject1).infoFrom);
            return;
          }
          catch (RemoteException paramAnonymousMessage)
          {
            for (;;) {}
          }
          QBPluginFactory.getInstance(QBPluginSystem.this.jdField_a_of_type_AndroidContentContext).bindPluginService(QBPluginSystem.this, ((QBPluginSystem.PluginPkgKey)localObject1).infoFrom);
          return;
          paramAnonymousMessage = (QBPluginSystem.PluginPkgKey)paramAnonymousMessage.obj;
          if (QBPluginSystem.this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy != null)
          {
            try
            {
              QBPluginSystem.this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy.stopDownloadPlugin(paramAnonymousMessage.pkgName, false, true, paramAnonymousMessage.infoFrom);
              return;
            }
            catch (NullPointerException paramAnonymousMessage)
            {
              paramAnonymousMessage.printStackTrace();
              return;
            }
            catch (RemoteException localRemoteException1)
            {
              localRemoteException1.printStackTrace();
              QBPluginFactory.getInstance(QBPluginSystem.this.jdField_a_of_type_AndroidContentContext).bindPluginService(QBPluginSystem.this, paramAnonymousMessage.infoFrom);
              return;
            }
            FLogger.d(QBPluginSystem.TAG, "MSG_PLUGIN_REBIND_SERCER");
            QBPluginFactory.getInstance(QBPluginSystem.this.jdField_a_of_type_AndroidContentContext).bindPluginService((QBPluginFactory.IBindPluginCallback)paramAnonymousMessage.obj, 0);
            return;
            if (QBPluginSystem.this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy == null) {
              synchronized (QBPluginSystem.this.jdField_a_of_type_JavaUtilArrayList)
              {
                QBPluginSystem.this.jdField_a_of_type_JavaUtilArrayList.add((Object[])paramAnonymousMessage.obj);
                return;
              }
            }
            FLogger.d(QBPluginSystem.TAG, "MSG_PLUGIN_START_USE_PLUGIN");
            paramAnonymousMessage = (Object[])paramAnonymousMessage.obj;
            if (paramAnonymousMessage.length > 2)
            {
              QBPluginSystem.this.a((QBPluginSystem.PluginPkgKey)paramAnonymousMessage[0], Integer.valueOf(paramAnonymousMessage[1].toString()).intValue(), ((Integer)paramAnonymousMessage[2]).intValue());
              return;
              ??? = (QBPluginSystem.c)paramAnonymousMessage.obj;
              localObject6 = ((QBPluginSystem.c)???).jdField_a_of_type_ComTencentCommonPluginQBPluginItemInfo;
              ??? = ((QBPluginSystem.c)???).jdField_a_of_type_ComTencentCommonPluginQBPluginSystem$PluginPkgKey;
              localObject4 = QBPluginSystem.a(QBPluginSystem.this, (QBPluginSystem.PluginPkgKey)???);
              localObject5 = new QBPluginSystem.b(null);
              j = paramAnonymousMessage.arg1;
              l = ((QBPluginSystem.PluginInfo)localObject4).mStartTimeOfTaskStep;
              if (localObject6 == null)
              {
                ((QBPluginSystem.PluginInfo)localObject4).initPluginStatus();
                if (j != 0)
                {
                  paramAnonymousMessage = ((QBPluginSystem.PluginPkgKey)???).pkgName;
                  ??? = new StringBuilder();
                  ((StringBuilder)???).append("651(");
                  ((StringBuilder)???).append(((QBPluginSystem.PluginInfo)localObject4).mStartTimeOfTaskStep - l);
                  ((StringBuilder)???).append("_");
                  ((StringBuilder)???).append(j);
                  ((StringBuilder)???).append("_");
                  ((StringBuilder)???).append(((QBPluginSystem.PluginPkgKey)???).infoFrom);
                  ((StringBuilder)???).append("_");
                  ((StringBuilder)???).append(PluginStatBehavior.getLastRetCode(PluginStatBehavior.getOpTyepPluginList(((QBPluginSystem.PluginPkgKey)???).infoFrom)));
                  ((StringBuilder)???).append(")");
                  PluginStatBehavior.addLogPath(paramAnonymousMessage, 4, ((StringBuilder)???).toString());
                  if (j == QBPluginServiceImpl.REQ_ERROR_REQUEST_SEND_FAILED_NETWORK_ERR) {
                    i = 658;
                  } else if (j == QBPluginServiceImpl.REQ_ERROR_NO_NEED_SEND_REQUEST) {
                    i = 659;
                  } else if (j < QBPluginServiceImpl.REQ_ERROR_NO_NEED_SEND_REQUEST) {
                    i = 657;
                  } else {
                    i = 651;
                  }
                  PluginStatBehavior.setFinCode(((QBPluginSystem.PluginPkgKey)???).pkgName, 4, i);
                  ((QBPluginSystem.b)localObject5).b = 3028;
                }
                else
                {
                  try
                  {
                    paramAnonymousMessage = QBPluginSystem.this.getAllPluginList(((QBPluginSystem.PluginPkgKey)???).infoFrom);
                  }
                  catch (RemoteException paramAnonymousMessage)
                  {
                    FLogger.e(QBPluginSystem.TAG, paramAnonymousMessage);
                    paramAnonymousMessage = (Message)???;
                  }
                  ??? = PluginStatBehavior.getLogPath("PluginDB", 7);
                  localObject6 = ((QBPluginSystem.PluginPkgKey)???).pkgName;
                  localObject7 = new StringBuilder();
                  ((StringBuilder)localObject7).append("650(");
                  if (paramAnonymousMessage != null) {
                    i = paramAnonymousMessage.size();
                  }
                  ((StringBuilder)localObject7).append(i);
                  ((StringBuilder)localObject7).append("_");
                  ((StringBuilder)localObject7).append(((QBPluginSystem.PluginInfo)localObject4).mStartTimeOfTaskStep - l);
                  ((StringBuilder)localObject7).append("_");
                  ((StringBuilder)localObject7).append(j);
                  ((StringBuilder)localObject7).append("_");
                  ((StringBuilder)localObject7).append(((QBPluginSystem.PluginPkgKey)???).infoFrom);
                  ((StringBuilder)localObject7).append("_");
                  ((StringBuilder)localObject7).append(PluginStatBehavior.getLastRetCode(PluginStatBehavior.getOpTyepPluginList(((QBPluginSystem.PluginPkgKey)???).infoFrom)));
                  ((StringBuilder)localObject7).append("_");
                  ((StringBuilder)localObject7).append((String)???);
                  ((StringBuilder)localObject7).append(")");
                  PluginStatBehavior.addLogPath((String)localObject6, 4, ((StringBuilder)localObject7).toString());
                  PluginStatBehavior.setFinCode(((QBPluginSystem.PluginPkgKey)???).pkgName, 4, 650);
                  ((QBPluginSystem.b)localObject5).b = 3029;
                }
                ((QBPluginSystem.b)localObject5).c = 5;
              }
            }
          }
          try
          {
            QBPluginSystem.this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy.setPluginJarZipType(((QBPluginSystem.PluginPkgKey)???).pkgName, paramAnonymousMessage.arg2, ((QBPluginSystem.PluginPkgKey)???).infoFrom);
          }
          catch (RemoteException localRemoteException2)
          {
            for (;;) {}
          }
          QBPluginFactory.getInstance(QBPluginSystem.this.jdField_a_of_type_AndroidContentContext).bindPluginService(QBPluginSystem.this, ((QBPluginSystem.PluginPkgKey)???).infoFrom);
          ((QBPluginSystem.PluginInfo)localObject4).setPluginStatus(2);
          ??? = ((QBPluginSystem.PluginPkgKey)???).pkgName;
          Object localObject7 = new StringBuilder();
          ((StringBuilder)localObject7).append("608(");
          ((StringBuilder)localObject7).append(((QBPluginSystem.PluginInfo)localObject4).mStartTimeOfTaskStep - l);
          ((StringBuilder)localObject7).append(")");
          PluginStatBehavior.addLogPath((String)???, 4, ((StringBuilder)localObject7).toString());
          ((QBPluginItemInfo)localObject6).mZipJarPluginType = paramAnonymousMessage.arg2;
          ((QBPluginSystem.PluginInfo)localObject4).mPluginItemInfo = ((QBPluginItemInfo)localObject6);
          QBPluginSystem.this.a((QBPluginSystem.PluginPkgKey)???, (QBPluginSystem.PluginInfo)localObject4, (QBPluginSystem.b)localObject5);
          return;
        }
      }
    };
    this.jdField_a_of_type_ComTencentCommonPluginQBPluginSystem$d = new d(this);
  }
  
  private int a(PluginPkgKey paramPluginPkgKey, PluginInfo paramPluginInfo, b paramb)
    throws RemoteException
  {
    int i = checkLocalPluginUpdateType(paramPluginPkgKey.pkgName, paramPluginInfo.mPluginItemInfo.mZipJarPluginType, paramPluginPkgKey.infoFrom, paramPluginInfo.mPluginItemInfo);
    Object localObject = TAG;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("usePlugin localPluginUpdateType:");
    localStringBuilder.append(i);
    localStringBuilder.append(",pluignType=");
    localStringBuilder.append(paramPluginInfo.mPluginItemInfo.mZipJarPluginType);
    FLogger.i((String)localObject, localStringBuilder.toString());
    if ((i != 2) && (i != 3) && (i != 0) && (i != 4))
    {
      if (i == 1)
      {
        PluginStatBehavior.addLogPath(paramPluginPkgKey.pkgName, 4, 604);
        paramPluginInfo.setPluginStatus(6);
        paramb.jdField_a_of_type_Int = LOAD_PLUGIN_MORE_SAME;
      }
      else
      {
        paramPluginInfo.setPluginStatus(-1);
        PluginStatBehavior.addLogPath(paramPluginPkgKey.pkgName, 4, 655);
        PluginStatBehavior.setFinCode(paramPluginPkgKey.pkgName, 4, 655);
        paramb.b = 3034;
        paramb.c = 3;
      }
    }
    else if (this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy.isNewVersionFileDownloaded(paramPluginPkgKey.pkgName, paramPluginPkgKey.infoFrom, paramPluginInfo.mPluginItemInfo))
    {
      PluginStatBehavior.addLogPath(paramPluginPkgKey.pkgName, 4, 606);
      paramPluginPkgKey = paramPluginPkgKey.pkgName;
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("606lpt[");
      ((StringBuilder)localObject).append(i);
      ((StringBuilder)localObject).append("]");
      PluginStatBehavior.addLogPath(paramPluginPkgKey, 3, ((StringBuilder)localObject).toString());
      paramPluginInfo.setPluginStatus(4);
      paramb.jdField_a_of_type_Boolean = false;
    }
    else if (i == 2)
    {
      PluginStatBehavior.addLogPath(paramPluginPkgKey.pkgName, 4, 605);
      paramPluginInfo.setPluginStatus(6);
      a(paramPluginPkgKey, 0);
      paramb.jdField_a_of_type_Int = LOAD_PLUGIN_MORE_NOSAME;
    }
    else
    {
      PluginStatBehavior.addLogPath(paramPluginPkgKey.pkgName, 4, 609);
      if (paramPluginInfo.isPluginFlagOn(2))
      {
        PluginStatBehavior.addLogPath(paramPluginPkgKey.pkgName, 4, 648);
        PluginStatBehavior.setFinCode(paramPluginPkgKey.pkgName, 4, 648);
        paramb.b = 3033;
        paramb.c = 3;
        paramPluginInfo.setPluginStatus(-1);
      }
      else
      {
        paramPluginInfo.setPluginStatus(3);
      }
    }
    return paramPluginInfo.getPluginStatus();
  }
  
  private IInstallPluginCallback.Stub a(PluginPkgKey paramPluginPkgKey)
  {
    if (TextUtils.isEmpty(paramPluginPkgKey.pkgName))
    {
      FLogger.d(TAG, "getPluginInstallListener:: packageName is null");
      return null;
    }
    Object localObject = a(paramPluginPkgKey);
    if (localObject != null) {
      return ((PluginInfo)localObject).mInstallPluginCallback;
    }
    localObject = TAG;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getPluginInstallListener:: packageName ");
    localStringBuilder.append(paramPluginPkgKey.pkgName);
    localStringBuilder.append(",info is null");
    FLogger.d((String)localObject, localStringBuilder.toString());
    localObject = TAG;
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("getPluginInstallListener:: packageName ");
    localStringBuilder.append(paramPluginPkgKey.pkgName);
    localStringBuilder.append(",listener is null");
    FLogger.d((String)localObject, localStringBuilder.toString());
    return null;
  }
  
  static IQBPluginServiceProvider a()
  {
    IQBPluginServiceProvider localIQBPluginServiceProvider = jdField_a_of_type_ComTencentCommonPluginQBPluginSystem$IQBPluginServiceProvider;
    if (localIQBPluginServiceProvider != null) {
      return localIQBPluginServiceProvider;
    }
    try
    {
      localIQBPluginServiceProvider = jdField_a_of_type_ComTencentCommonPluginQBPluginSystem$IQBPluginServiceProvider;
      if (localIQBPluginServiceProvider == null) {
        try
        {
          jdField_a_of_type_ComTencentCommonPluginQBPluginSystem$IQBPluginServiceProvider = (IQBPluginServiceProvider)AppManifest.getInstance().queryExtension(IQBPluginServiceProvider.class, null);
        }
        catch (Throwable localThrowable)
        {
          jdField_a_of_type_ComTencentCommonPluginQBPluginSystem$IQBPluginServiceProvider = null;
          localThrowable.printStackTrace();
        }
      }
      return jdField_a_of_type_ComTencentCommonPluginQBPluginSystem$IQBPluginServiceProvider;
    }
    finally {}
  }
  
  private PluginInfo a(PluginPkgKey paramPluginPkgKey)
  {
    synchronized (this.jdField_a_of_type_JavaUtilHashMap)
    {
      paramPluginPkgKey = (PluginInfo)this.jdField_a_of_type_JavaUtilHashMap.get(paramPluginPkgKey);
      return paramPluginPkgKey;
    }
  }
  
  private ArrayList<IQBPluginSystemCallback> a(PluginInfo paramPluginInfo)
  {
    ArrayList localArrayList = new ArrayList();
    if (paramPluginInfo != null)
    {
      if (paramPluginInfo.mSystemCallback == null) {
        return localArrayList;
      }
      try
      {
        Iterator localIterator = paramPluginInfo.mSystemCallback.iterator();
        while (localIterator.hasNext()) {
          localArrayList.add((IQBPluginSystemCallback)localIterator.next());
        }
        return localArrayList;
      }
      finally {}
    }
    return localArrayList1;
  }
  
  private void a()
  {
    Handler localHandler = this.jdField_a_of_type_AndroidOsHandler;
    localHandler.sendMessageDelayed(localHandler.obtainMessage(11), 10000L);
  }
  
  private void a(PluginPkgKey paramPluginPkgKey, PluginInfo paramPluginInfo, boolean paramBoolean)
  {
    Object localObject = new b(null);
    if (paramBoolean) {}
    try
    {
      PluginStatBehavior.addLogPath(paramPluginPkgKey.pkgName, 4, 621);
      ((b)localObject).jdField_a_of_type_Int = LOAD_PLUGIN_FIRST;
      paramPluginInfo.setPluginStatus(5);
      localObject = TAG;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("插件系统使用主流程:");
      localStringBuilder.append(paramPluginPkgKey.pkgName);
      localStringBuilder.append(",from=");
      localStringBuilder.append(paramPluginPkgKey.infoFrom);
      localStringBuilder.append(",安装中，from+");
      localStringBuilder.append(paramBoolean);
      localStringBuilder.append("(true 下载，false本地)");
      FLogger.i((String)localObject, localStringBuilder.toString());
      this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy.installPlugin(paramPluginPkgKey.pkgName, paramPluginPkgKey.infoFrom, paramPluginInfo.mPluginItemInfo.mZipJarPluginType, a(paramPluginPkgKey), paramPluginInfo.mPluginItemInfo, paramBoolean);
      return;
    }
    catch (Exception paramPluginPkgKey)
    {
      paramPluginPkgKey.printStackTrace();
    }
  }
  
  private void a(String paramString, int paramInt1, IQBPluginSystemCallback paramIQBPluginSystemCallback, ILoadPluginCallback paramILoadPluginCallback, IInstallPluginCallback.Stub paramStub, int paramInt2, int paramInt3)
  {
    PluginStatBehavior.setOpType(paramString, 4);
    paramString = new PluginPkgKey(paramString, paramInt2);
    a(paramString, paramIQBPluginSystemCallback, paramILoadPluginCallback, paramStub);
    if (paramIQBPluginSystemCallback != null) {
      a(paramString, paramIQBPluginSystemCallback);
    }
    c(paramString, paramInt1, paramInt3);
  }
  
  private void b(PluginPkgKey paramPluginPkgKey, PluginInfo paramPluginInfo)
  {
    String str = TAG;
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("startPluginDownloadTask= ");
    ((StringBuilder)localObject2).append(paramPluginPkgKey.pkgName);
    ((StringBuilder)localObject2).append(",taskFlag=");
    ((StringBuilder)localObject2).append(paramPluginInfo.mPluginFlag);
    FLogger.i(str, ((StringBuilder)localObject2).toString());
    long l1 = paramPluginInfo.mStartTimeOfTaskStep;
    int k = 3017;
    int i = k;
    int j = k;
    try
    {
      long l2 = System.currentTimeMillis();
      i = k;
      j = k;
      k = this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy.startDownloadPlugin(paramPluginPkgKey.pkgName, paramPluginPkgKey.infoFrom, paramPluginInfo.mPluginFlag, paramPluginInfo.mPluginItemInfo);
      i = k;
      j = k;
      long l3 = System.currentTimeMillis();
      i = k;
      j = k;
      str = paramPluginPkgKey.pkgName;
      i = k;
      j = k;
      localObject2 = new StringBuilder();
      i = k;
      j = k;
      ((StringBuilder)localObject2).append("615(normal:");
      i = k;
      j = k;
      ((StringBuilder)localObject2).append(l3 - l2);
      i = k;
      j = k;
      ((StringBuilder)localObject2).append(")");
      i = k;
      j = k;
      PluginStatBehavior.addLogPath(str, 4, ((StringBuilder)localObject2).toString());
      i = k;
    }
    catch (NullPointerException localNullPointerException)
    {
      localNullPointerException.printStackTrace();
    }
    catch (RemoteException localRemoteException)
    {
      QBPluginFactory.getInstance(ContextHolder.getAppContext()).bindPluginService(this, paramPluginPkgKey.infoFrom);
      localRemoteException.printStackTrace();
      i = j;
    }
    if (i != 0)
    {
      Object localObject1 = TAG;
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("插件系统使用主流程:");
      ((StringBuilder)localObject2).append(paramPluginPkgKey.pkgName);
      ((StringBuilder)localObject2).append(",from=");
      ((StringBuilder)localObject2).append(paramPluginPkgKey.infoFrom);
      ((StringBuilder)localObject2).append(",发起下载失败，流程结束");
      FLogger.i((String)localObject1, ((StringBuilder)localObject2).toString());
      localObject1 = PluginStatBehavior.getLogPath("PluginDB", 7);
      localObject2 = paramPluginPkgKey.pkgName;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("3011(");
      localStringBuilder.append(i);
      localStringBuilder.append("_");
      localStringBuilder.append(paramPluginInfo.mStartTimeOfTaskStep - l1);
      localStringBuilder.append("_");
      localStringBuilder.append((String)localObject1);
      localStringBuilder.append(")");
      PluginStatBehavior.addLogPath((String)localObject2, 4, localStringBuilder.toString());
      PluginStatBehavior.setFinCode(paramPluginPkgKey.pkgName, 4, i);
      paramPluginInfo.initPluginStatus();
      localObject1 = new b(null);
      ((b)localObject1).b = i;
      ((b)localObject1).c = 1;
      a(paramPluginPkgKey, paramPluginInfo, (b)localObject1);
      return;
    }
  }
  
  private boolean b(PluginPkgKey paramPluginPkgKey)
  {
    int k = -1;
    int i = k;
    int j = k;
    try
    {
      long l1 = System.currentTimeMillis();
      i = k;
      j = k;
      k = this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy.startDownloadPlugin(paramPluginPkgKey.pkgName, paramPluginPkgKey.infoFrom, 4, null);
      i = k;
      j = k;
      long l2 = System.currentTimeMillis();
      i = k;
      j = k;
      String str1 = paramPluginPkgKey.pkgName;
      i = k;
      j = k;
      localStringBuilder = new StringBuilder();
      i = k;
      j = k;
      localStringBuilder.append("615(bg:");
      i = k;
      j = k;
      localStringBuilder.append(l2 - l1);
      i = k;
      j = k;
      localStringBuilder.append(")");
      i = k;
      j = k;
      PluginStatBehavior.addLogPath(str1, 4, localStringBuilder.toString());
      i = k;
    }
    catch (NullPointerException localNullPointerException)
    {
      localNullPointerException.printStackTrace();
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
      i = j;
    }
    String str2 = TAG;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("插件静默下载=");
    localStringBuilder.append(paramPluginPkgKey.pkgName);
    localStringBuilder.append(",ret=");
    localStringBuilder.append(i);
    FLogger.i(str2, localStringBuilder.toString());
    return i == 0;
  }
  
  public static QBPluginSystem getInstance(Context paramContext)
  {
    try
    {
      if (jdField_a_of_type_ComTencentCommonPluginQBPluginSystem == null)
      {
        jdField_a_of_type_ComTencentCommonPluginQBPluginSystem = new QBPluginSystem(paramContext);
        if (a() != null)
        {
          IQBPluginService localIQBPluginService = a().getService();
          if (localIQBPluginService != null) {
            QBPluginFactory.getInstance(paramContext).setLocalPluginServiceImpl(localIQBPluginService);
          }
        }
        QBPluginFactory.getInstance(paramContext).bindPluginService(jdField_a_of_type_ComTencentCommonPluginQBPluginSystem, 0);
      }
      paramContext = jdField_a_of_type_ComTencentCommonPluginQBPluginSystem;
      return paramContext;
    }
    finally {}
  }
  
  public static QBPluginSystem getInstance(Context paramContext, int paramInt)
  {
    try
    {
      if (jdField_a_of_type_ComTencentCommonPluginQBPluginSystem == null)
      {
        jdField_a_of_type_ComTencentCommonPluginQBPluginSystem = new QBPluginSystem(paramContext);
        if (a() != null)
        {
          IQBPluginService localIQBPluginService = a().getService();
          if (localIQBPluginService != null) {
            QBPluginFactory.getInstance(paramContext).setLocalPluginServiceImpl(localIQBPluginService);
          }
        }
        QBPluginFactory.getInstance(paramContext).bindPluginService(jdField_a_of_type_ComTencentCommonPluginQBPluginSystem, paramInt);
        paramContext = jdField_a_of_type_ComTencentCommonPluginQBPluginSystem;
        return paramContext;
      }
      if ((!gTbsPluginInit) && (paramInt == 2)) {
        QBPluginFactory.getInstance(paramContext).bindPluginService(jdField_a_of_type_ComTencentCommonPluginQBPluginSystem, paramInt);
      }
      paramContext = jdField_a_of_type_ComTencentCommonPluginQBPluginSystem;
      return paramContext;
    }
    finally {}
  }
  
  public static String getStatKey(String paramString)
  {
    String str = paramString;
    if (paramString.split("\\.") != null) {
      str = paramString.split("\\.")[(paramString.split("\\.").length - 1)];
    }
    paramString = new StringBuilder();
    paramString.append("_");
    paramString.append(str.toUpperCase());
    return paramString.toString();
  }
  
  public static void setQBPluginServiceProvider(IQBPluginServiceProvider paramIQBPluginServiceProvider)
  {
    jdField_a_of_type_ComTencentCommonPluginQBPluginSystem$IQBPluginServiceProvider = paramIQBPluginServiceProvider;
  }
  
  public void LoadLocalPlugin(String paramString, IQBPluginSystemCallback paramIQBPluginSystemCallback, ILoadPluginCallback paramILoadPluginCallback, int paramInt)
  {
    a(paramString, 1, paramIQBPluginSystemCallback, paramILoadPluginCallback, null, paramInt, 2);
  }
  
  int a(PluginPkgKey paramPluginPkgKey, PluginInfo paramPluginInfo, int paramInt)
  {
    String str = paramPluginInfo.mPluginItemInfo.mPackageName;
    long l = paramPluginInfo.mStartTimeOfTaskStep;
    StringBuilder localStringBuilder;
    if (paramPluginInfo.mLoadCallBack != null)
    {
      int j = paramPluginInfo.mLoadCallBack.load(str, paramPluginInfo.mPluginItemInfo, paramInt);
      str = TAG;
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("插件系统使用主流程:");
      localStringBuilder.append(paramPluginPkgKey.pkgName);
      localStringBuilder.append(",from=");
      localStringBuilder.append(paramPluginPkgKey.infoFrom);
      localStringBuilder.append(",设置了load回调的加载结束，loadRet=");
      localStringBuilder.append(j);
      FLogger.i(str, localStringBuilder.toString());
      if (j == 0) {
        paramInt = 0;
      } else {
        paramInt = j + 5000;
      }
      int i;
      if (j == 0) {
        i = 8;
      } else {
        i = -1;
      }
      paramPluginInfo.setPluginStatus(i);
      str = TAG;
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("loadPlugin info.mLoadResult:");
      localStringBuilder.append(j);
      FLogger.i(str, localStringBuilder.toString());
    }
    else
    {
      str = TAG;
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("插件系统使用主流程:");
      localStringBuilder.append(paramPluginPkgKey.pkgName);
      localStringBuilder.append(",from=");
      localStringBuilder.append(paramPluginPkgKey.infoFrom);
      localStringBuilder.append(",未设置load回调的加载结束，loadRet=0，status=");
      localStringBuilder.append(paramPluginInfo.getPluginStatus());
      FLogger.i(str, localStringBuilder.toString());
      paramInt = 0;
    }
    if (paramInt != 0)
    {
      str = paramPluginPkgKey.pkgName;
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("655(");
      localStringBuilder.append(paramPluginInfo.mStartTimeOfTaskStep - l);
      localStringBuilder.append(")");
      PluginStatBehavior.addLogPath(str, 4, localStringBuilder.toString());
      PluginStatBehavior.setFinCode(paramPluginPkgKey.pkgName, 4, 655);
    }
    else
    {
      str = paramPluginPkgKey.pkgName;
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("60(");
      localStringBuilder.append(paramPluginInfo.mStartTimeOfTaskStep - l);
      localStringBuilder.append(")");
      PluginStatBehavior.addLogPath(str, 4, localStringBuilder.toString());
      PluginStatBehavior.setFinCode(paramPluginPkgKey.pkgName, 4, 0);
    }
    paramPluginInfo.clearAllPluginFlag();
    b(paramPluginPkgKey, paramInt, 3);
    return paramInt;
  }
  
  void a(PluginPkgKey paramPluginPkgKey)
  {
    FLogger.d(TAG, "sendInitPluginStatusToPluginThread");
    Message localMessage = this.jdField_a_of_type_AndroidOsHandler.obtainMessage();
    localMessage.what = 9;
    localMessage.obj = paramPluginPkgKey;
    this.jdField_a_of_type_AndroidOsHandler.sendMessage(localMessage);
  }
  
  void a(PluginPkgKey paramPluginPkgKey, int paramInt)
  {
    FLogger.d(TAG, "sendBackDownloadMsgToPluginThread");
    Message localMessage = this.jdField_b_of_type_AndroidOsHandler.obtainMessage();
    localMessage.what = 8;
    localMessage.obj = paramPluginPkgKey;
    localMessage.arg1 = paramInt;
    this.jdField_a_of_type_AndroidOsHandler.sendMessage(localMessage);
  }
  
  void a(PluginPkgKey paramPluginPkgKey, int paramInt1, int paramInt2)
  {
    if (this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy == null)
    {
      PluginStatBehavior.addLogPath(paramPluginPkgKey.pkgName, 4, 3001);
      PluginStatBehavior.setFinCode(paramPluginPkgKey.pkgName, 4, 3001);
      QBPluginFactory.getInstance(ContextHolder.getAppContext()).bindPluginService(this, paramPluginPkgKey.infoFrom);
      return;
    }
    Object localObject1 = a(paramPluginPkgKey);
    if (((PluginInfo)localObject1).isPluginTaskRuning(paramInt2))
    {
      localObject1 = TAG;
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("插件系统使用主流程:添加的插件任务已经有任务在并发");
      ((StringBuilder)localObject2).append(paramPluginPkgKey.pkgName);
      ((StringBuilder)localObject2).append(",from=");
      ((StringBuilder)localObject2).append(paramPluginPkgKey.infoFrom);
      FLogger.i((String)localObject1, ((StringBuilder)localObject2).toString());
      return;
    }
    if (((PluginInfo)localObject1).isPluginTaskLoaded())
    {
      localObject2 = TAG;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("插件系统使用主流程:插件已经加载成功过了直接调用onPrepareFinish结束:");
      localStringBuilder.append(paramPluginPkgKey.pkgName);
      localStringBuilder.append(",from=");
      localStringBuilder.append(paramPluginPkgKey.infoFrom);
      FLogger.i((String)localObject2, localStringBuilder.toString());
      ((PluginInfo)localObject1).clearAllPluginFlag();
      b(paramPluginPkgKey, 0, 0);
      a(paramPluginPkgKey, 1);
      return;
    }
    if (((PluginInfo)localObject1).getPluginStatus() == -1) {
      ((PluginInfo)localObject1).setPluginStatus(0);
    }
    ((PluginInfo)localObject1).setPluginFlag(paramInt2);
    Object localObject2 = new b(null);
    ((b)localObject2).d = paramInt1;
    if (((PluginInfo)localObject1).getPluginStatus() == 6) {
      ((b)localObject2).jdField_a_of_type_Int = LOAD_PLUGIN_MORE_SAME;
    }
    a(paramPluginPkgKey, (PluginInfo)localObject1, (b)localObject2);
  }
  
  void a(PluginPkgKey paramPluginPkgKey, IQBPluginSystemCallback paramIQBPluginSystemCallback)
  {
    FLogger.d(TAG, "sendFinishedMsg");
    Message localMessage = this.jdField_b_of_type_AndroidOsHandler.obtainMessage();
    localMessage.what = 4;
    localMessage.obj = new MsgClassPluginCallback(paramPluginPkgKey, paramIQBPluginSystemCallback);
    this.jdField_b_of_type_AndroidOsHandler.sendMessage(localMessage);
  }
  
  void a(PluginPkgKey paramPluginPkgKey, PluginInfo paramPluginInfo)
  {
    b localb = new b(null);
    try
    {
      this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy.addPluginListener(paramPluginPkgKey.pkgName, this.jdField_a_of_type_ComTencentCommonPluginQBPluginSystem$d);
      a(paramPluginPkgKey, paramPluginInfo, localb);
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
      paramPluginInfo.initPluginStatus();
      PluginStatBehavior.addLogPath(paramPluginPkgKey.pkgName, 4, 649);
      PluginStatBehavior.setFinCode(paramPluginPkgKey.pkgName, 4, 649);
      localb.b = 3006;
      localb.c = 3;
    }
    a(paramPluginPkgKey, paramPluginInfo, localb);
  }
  
  void a(PluginPkgKey paramPluginPkgKey, PluginInfo paramPluginInfo, int paramInt1, int paramInt2)
  {
    Object localObject = TAG;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("dealWithStartUsePlugin:");
    localStringBuilder.append(paramPluginPkgKey.pkgName);
    localStringBuilder.append(",");
    localStringBuilder.append(paramPluginPkgKey.infoFrom);
    localStringBuilder.append(",pluginType=");
    localStringBuilder.append(paramInt1);
    FLogger.i((String)localObject, localStringBuilder.toString());
    try
    {
      try
      {
        long l = paramPluginInfo.mStartTimeOfTaskStep;
        paramPluginInfo.setPluginStatus(1);
        localObject = paramPluginPkgKey.pkgName;
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("607(");
        localStringBuilder.append(paramInt2);
        localStringBuilder.append("-");
        localStringBuilder.append(paramPluginInfo.mStartTimeOfTaskStep - l);
        localStringBuilder.append(")");
        PluginStatBehavior.addLogPath((String)localObject, 4, localStringBuilder.toString());
        localObject = new a(paramPluginPkgKey, paramInt1, this.jdField_a_of_type_AndroidOsHandler);
        bool = this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy.getPluginInfoAsync(paramPluginPkgKey.pkgName, (IGetPluginInfoCallback.Stub)localObject, paramPluginPkgKey.infoFrom);
      }
      catch (NullPointerException localNullPointerException)
      {
        localNullPointerException.printStackTrace();
        break label234;
      }
    }
    catch (RemoteException localRemoteException)
    {
      boolean bool;
      b localb;
      for (;;) {}
    }
    QBPluginFactory.getInstance(this.jdField_a_of_type_AndroidContentContext).bindPluginService(this, paramPluginPkgKey.infoFrom);
    FLogger.w(TAG, "usePlugin QBPluginService Exception,Reconnected.");
    label234:
    bool = false;
    if (!bool)
    {
      paramPluginInfo.initPluginStatus();
      localb = new b(null);
      PluginStatBehavior.addLogPath(paramPluginPkgKey.pkgName, 4, 656);
      PluginStatBehavior.setFinCode(paramPluginPkgKey.pkgName, 4, 656);
      localb.b = 3030;
      localb.c = 5;
      a(paramPluginPkgKey, paramPluginInfo, localb);
      return;
    }
  }
  
  void a(PluginPkgKey paramPluginPkgKey, PluginInfo paramPluginInfo, b paramb)
  {
    StringBuilder localStringBuilder;
    switch (paramPluginInfo.getPluginStatus())
    {
    case 1: 
    case 5: 
    default: 
      return;
    case 6: 
      localObject = TAG;
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("插件系统使用主流程:");
      localStringBuilder.append(paramPluginPkgKey.pkgName);
      localStringBuilder.append(",from=");
      localStringBuilder.append(paramPluginPkgKey.infoFrom);
      localStringBuilder.append(",本地版本加载 ");
      localStringBuilder.append(paramPluginInfo.mPluginItemInfo.mZipJarPluginType);
      FLogger.i((String)localObject, localStringBuilder.toString());
      a(paramPluginPkgKey, paramPluginInfo, paramb.jdField_a_of_type_Int);
      return;
    case 4: 
      localObject = TAG;
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("插件系统使用主流程:");
      localStringBuilder.append(paramPluginPkgKey.pkgName);
      localStringBuilder.append(",from=");
      localStringBuilder.append(paramPluginPkgKey.infoFrom);
      localStringBuilder.append(",需要本地安装  ");
      localStringBuilder.append(paramPluginInfo.mPluginItemInfo.mZipJarPluginType);
      FLogger.i((String)localObject, localStringBuilder.toString());
      a(paramPluginPkgKey, paramPluginInfo, paramb.jdField_a_of_type_Boolean);
      return;
    case 3: 
      paramb = TAG;
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("插件系统使用主流程:");
      ((StringBuilder)localObject).append(paramPluginPkgKey.pkgName);
      ((StringBuilder)localObject).append(",from=");
      ((StringBuilder)localObject).append(paramPluginPkgKey.infoFrom);
      ((StringBuilder)localObject).append(",需要下载");
      FLogger.i(paramb, ((StringBuilder)localObject).toString());
      b(paramPluginPkgKey, paramPluginInfo);
      return;
    case 2: 
      a(paramPluginPkgKey, paramPluginInfo);
      return;
    case 0: 
      localObject = TAG;
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("插件系统使用主流程:");
      localStringBuilder.append(paramPluginPkgKey.pkgName);
      localStringBuilder.append(",from=");
      localStringBuilder.append(paramPluginPkgKey.infoFrom);
      localStringBuilder.append(",插件任务类型=");
      localStringBuilder.append(paramPluginInfo.mPluginFlag);
      localStringBuilder.append("(0x0 normal,0x1 预加载，0x2 强制本地,0x4 静默下载)pluginType=");
      localStringBuilder.append(paramb.d);
      FLogger.i((String)localObject, localStringBuilder.toString());
      a(paramPluginPkgKey, paramPluginInfo, paramb.d, paramb.e);
      return;
    }
    paramPluginInfo = TAG;
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("插件系统使用主流程:");
    ((StringBuilder)localObject).append(paramPluginPkgKey.pkgName);
    ((StringBuilder)localObject).append(",from=");
    ((StringBuilder)localObject).append(paramPluginPkgKey.infoFrom);
    ((StringBuilder)localObject).append(",状态异常流程结束,错误码：");
    ((StringBuilder)localObject).append(paramb.b);
    ((StringBuilder)localObject).append("，引起错误的操作类型");
    ((StringBuilder)localObject).append(paramb.c);
    FLogger.i(paramPluginInfo, ((StringBuilder)localObject).toString());
    b(paramPluginPkgKey, paramb.b, paramb.c);
  }
  
  boolean a(PluginPkgKey paramPluginPkgKey)
  {
    ??? = TAG;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("流程结束，删除pluginInfo的所有listner:removeAllPluginSystemListener packageName:");
    localStringBuilder.append(paramPluginPkgKey.pkgName);
    FLogger.i((String)???, localStringBuilder.toString());
    synchronized (this.jdField_a_of_type_JavaUtilHashMap)
    {
      paramPluginPkgKey = (PluginInfo)this.jdField_a_of_type_JavaUtilHashMap.get(paramPluginPkgKey);
      if (paramPluginPkgKey != null)
      {
        paramPluginPkgKey.mLoadCallBack = null;
        paramPluginPkgKey.mInstallPluginCallback = null;
        if (paramPluginPkgKey.mSystemCallback == null) {
          return false;
        }
        try
        {
          paramPluginPkgKey.mSystemCallback.clear();
        }
        finally {}
      }
      return true;
    }
  }
  
  boolean a(PluginPkgKey paramPluginPkgKey, IQBPluginSystemCallback paramIQBPluginSystemCallback, ILoadPluginCallback paramILoadPluginCallback, IInstallPluginCallback.Stub paramStub)
  {
    synchronized (this.jdField_a_of_type_JavaUtilHashMap)
    {
      PluginInfo localPluginInfo = (PluginInfo)this.jdField_a_of_type_JavaUtilHashMap.get(paramPluginPkgKey);
      if (localPluginInfo == null)
      {
        localPluginInfo = new PluginInfo();
        localPluginInfo.mLoadCallBack = paramILoadPluginCallback;
        localPluginInfo.mInstallPluginCallback = paramStub;
        localPluginInfo.mSystemCallback.add(paramIQBPluginSystemCallback);
        this.jdField_a_of_type_JavaUtilHashMap.put(paramPluginPkgKey, localPluginInfo);
        paramIQBPluginSystemCallback = TAG;
        paramILoadPluginCallback = new StringBuilder();
        paramILoadPluginCallback.append("addPluginCallback packageName:");
        paramILoadPluginCallback.append(paramPluginPkgKey.pkgName);
        paramILoadPluginCallback.append(" firsttime");
        FLogger.d(paramIQBPluginSystemCallback, paramILoadPluginCallback.toString());
      }
      else
      {
        if (paramILoadPluginCallback != null)
        {
          StringBuilder localStringBuilder;
          if (localPluginInfo.mLoadCallBack == null)
          {
            localPluginInfo.mLoadCallBack = paramILoadPluginCallback;
            paramILoadPluginCallback = TAG;
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("addPluginLoadCallback packageName:");
            localStringBuilder.append(paramPluginPkgKey.pkgName);
            localStringBuilder.append(" not first time. has no other");
            FLogger.d(paramILoadPluginCallback, localStringBuilder.toString());
          }
          else
          {
            paramILoadPluginCallback = TAG;
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("addPluginLoadCallback packageName:");
            localStringBuilder.append(paramPluginPkgKey.pkgName);
            localStringBuilder.append(" not first time. has  others");
            FLogger.d(paramILoadPluginCallback, localStringBuilder.toString());
          }
        }
        if ((paramStub != null) && (localPluginInfo.mInstallPluginCallback == null)) {
          localPluginInfo.mInstallPluginCallback = paramStub;
        }
        if (!localPluginInfo.mSystemCallback.contains(paramIQBPluginSystemCallback)) {
          try
          {
            localPluginInfo.mSystemCallback.add(paramIQBPluginSystemCallback);
          }
          finally {}
        }
      }
      a();
      return true;
    }
  }
  
  void b(PluginPkgKey paramPluginPkgKey, int paramInt1, int paramInt2)
  {
    Object localObject1 = TAG;
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("sendFinishedMsgToCallbackThread ");
    ((StringBuilder)localObject2).append(paramPluginPkgKey.pkgName);
    ((StringBuilder)localObject2).append(",infoFrom=");
    ((StringBuilder)localObject2).append(paramPluginPkgKey.infoFrom);
    ((StringBuilder)localObject2).append(",errCode=");
    ((StringBuilder)localObject2).append(paramInt1);
    ((StringBuilder)localObject2).append(",failOpType=");
    ((StringBuilder)localObject2).append(paramInt2);
    FLogger.i((String)localObject1, ((StringBuilder)localObject2).toString());
    localObject1 = (IPluginUseMPX)IPluginUseMPX.PROXY.get();
    localObject2 = paramPluginPkgKey.pkgName;
    boolean bool;
    if (paramInt1 == 0) {
      bool = true;
    } else {
      bool = false;
    }
    ((IPluginUseMPX)localObject1).set((String)localObject2, bool);
    localObject1 = this.jdField_b_of_type_AndroidOsHandler.obtainMessage();
    ((Message)localObject1).what = 3;
    ((Message)localObject1).obj = paramPluginPkgKey;
    ((Message)localObject1).arg1 = paramInt1;
    ((Message)localObject1).arg2 = paramInt2;
    this.jdField_b_of_type_AndroidOsHandler.sendMessage((Message)localObject1);
  }
  
  void c(PluginPkgKey paramPluginPkgKey, int paramInt1, int paramInt2)
  {
    Object[] arrayOfObject = new Object[4];
    arrayOfObject[0] = paramPluginPkgKey;
    arrayOfObject[1] = Integer.valueOf(paramInt1);
    arrayOfObject[2] = Integer.valueOf(paramInt2);
    paramPluginPkgKey = this.jdField_a_of_type_AndroidOsHandler.obtainMessage(2, arrayOfObject);
    this.jdField_a_of_type_AndroidOsHandler.sendMessage(paramPluginPkgKey);
  }
  
  public boolean canPluginUse(String paramString, int paramInt1, int paramInt2)
  {
    QBPluginProxy localQBPluginProxy = this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy;
    if (localQBPluginProxy == null) {
      return false;
    }
    try
    {
      localQBPluginProxy.setPluginJarZipType(paramString, paramInt1, paramInt2);
      paramInt1 = checkLocalPluginUpdateType(paramString, paramInt1, paramInt2, null);
      if ((paramInt1 != 1) && (paramInt1 != 2)) {
        return paramInt1 == 4;
      }
      return true;
    }
    catch (NullPointerException paramString)
    {
      paramString.printStackTrace();
      return false;
    }
    catch (RemoteException paramString)
    {
      QBPluginFactory.getInstance(ContextHolder.getAppContext()).bindPluginService(this, paramInt2);
      paramString.printStackTrace();
    }
    return false;
  }
  
  public int checkLocalPluginUpdateType(String paramString, int paramInt1, int paramInt2, QBPluginItemInfo paramQBPluginItemInfo)
  {
    QBPluginProxy localQBPluginProxy = this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy;
    if (localQBPluginProxy == null) {
      return -1;
    }
    try
    {
      int i = localQBPluginProxy.checkLocalPlugin(paramString, paramInt1, paramInt2);
      if (i == 0) {
        return 0;
      }
      if (this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy.checkNeedUpdate(paramString, paramInt1, paramInt2, paramQBPluginItemInfo))
      {
        boolean bool = this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy.needForceUpdate(paramString, paramInt2, paramQBPluginItemInfo);
        if (bool) {
          return 3;
        }
        return 2;
      }
      if (i == 1) {
        return 1;
      }
      return 4;
    }
    catch (NullPointerException paramString)
    {
      paramString.printStackTrace();
      return -1;
      QBPluginFactory.getInstance(ContextHolder.getAppContext()).bindPluginService(this, paramInt2);
      return -1;
    }
    catch (RemoteException paramString)
    {
      for (;;) {}
    }
  }
  
  public void downloadPluginBackGround(String paramString, int paramInt1, IQBPluginSystemCallback paramIQBPluginSystemCallback, int paramInt2)
  {
    String str = TAG;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("downloadPluginBackGround: ");
    localStringBuilder.append(paramString);
    localStringBuilder.append(", won't unzip");
    FLogger.i(str, localStringBuilder.toString());
    a(paramString, paramInt1, paramIQBPluginSystemCallback, null, null, paramInt2, 1);
  }
  
  public ArrayList<QBPluginItemInfo> getAllPluginList(int paramInt)
    throws RemoteException
  {
    QBPluginProxy localQBPluginProxy = this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy;
    if (localQBPluginProxy != null) {
      return localQBPluginProxy.getAllPluginList(paramInt);
    }
    return null;
  }
  
  public QBPluginItemInfo getNewPluginInfo(String paramString, int paramInt)
    throws RemoteException
  {
    QBPluginProxy localQBPluginProxy = this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy;
    if (localQBPluginProxy != null) {
      return localQBPluginProxy.getPluginInfo(paramString, paramInt);
    }
    return null;
  }
  
  public QBPluginItemInfo getPluginInfo(String paramString, int paramInt)
    throws RemoteException
  {
    Object localObject2 = a(new PluginPkgKey(paramString, paramInt));
    Object localObject1 = localObject2;
    if (localObject2 == null) {
      localObject1 = new PluginInfo();
    }
    if (((PluginInfo)localObject1).mPluginItemInfo != null) {
      return ((PluginInfo)localObject1).mPluginItemInfo;
    }
    localObject2 = null;
    QBPluginProxy localQBPluginProxy = this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy;
    if (localQBPluginProxy != null) {
      localObject2 = localQBPluginProxy.getPluginInfo(paramString, paramInt);
    }
    ((PluginInfo)localObject1).mPluginItemInfo = ((QBPluginItemInfo)localObject2);
    return ((PluginInfo)localObject1).mPluginItemInfo;
  }
  
  public ArrayList<QBPluginItemInfo> getPluginListByPos(int paramInt1, int paramInt2)
    throws RemoteException
  {
    QBPluginProxy localQBPluginProxy = this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy;
    if (localQBPluginProxy != null) {
      return localQBPluginProxy.getPluginListByPos(paramInt1, paramInt2);
    }
    return null;
  }
  
  public ArrayList<QBPluginItemInfo> getPluginListByType(int paramInt1, int paramInt2)
    throws RemoteException
  {
    QBPluginProxy localQBPluginProxy = this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy;
    if (localQBPluginProxy != null) {
      return localQBPluginProxy.getPluginListByType(paramInt1, paramInt2);
    }
    return null;
  }
  
  public int isPluginNeedDownload(String paramString, int paramInt1, int paramInt2)
  {
    QBPluginProxy localQBPluginProxy = this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy;
    if (localQBPluginProxy == null) {
      return -1;
    }
    try
    {
      localQBPluginProxy.setPluginJarZipType(paramString, paramInt1, paramInt2);
      if (this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy.isNewVersionFileDownloaded(paramString, paramInt2, null)) {
        return 1;
      }
      int i = checkLocalPluginUpdateType(paramString, paramInt1, paramInt2, null);
      if (i != 0)
      {
        if (i == 3) {
          return 3;
        }
        paramInt1 = i;
        if (i == 4) {
          paramInt1 = 1;
        }
        return paramInt1;
      }
      return 3;
    }
    catch (NullPointerException paramString)
    {
      paramString.printStackTrace();
      return -1;
    }
    catch (RemoteException paramString)
    {
      QBPluginFactory.getInstance(ContextHolder.getAppContext()).bindPluginService(this, paramInt2);
      paramString.printStackTrace();
    }
    return -1;
  }
  
  public void onBindPluginSuccess(QBPluginProxy arg1)
  {
    Object localObject1 = TAG;
    Object localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append("onBindPluginSuccess: ");
    ((StringBuilder)localObject3).append(???);
    FLogger.i((String)localObject1, ((StringBuilder)localObject3).toString());
    this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy = ???;
    this.jdField_a_of_type_Int = 0;
    if (this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy != null) {
      synchronized (this.jdField_a_of_type_JavaUtilArrayList)
      {
        localObject1 = this.jdField_a_of_type_JavaUtilArrayList.iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject3 = (Object[])((Iterator)localObject1).next();
          c((PluginPkgKey)localObject3[0], Integer.valueOf(localObject3[1].toString()).intValue(), ((Integer)localObject3[2]).intValue());
        }
        this.jdField_a_of_type_JavaUtilArrayList.clear();
        return;
      }
    }
  }
  
  public void onBindPluignFailed()
  {
    FLogger.w(TAG, "onBindPluignFailed");
    int i = this.jdField_a_of_type_Int;
    this.jdField_a_of_type_Int = (i + 1);
    if (i < 5)
    {
      ??? = this.jdField_a_of_type_AndroidOsHandler.obtainMessage();
      ((Message)???).what = 3;
      ((Message)???).obj = this;
      this.jdField_a_of_type_AndroidOsHandler.sendMessage((Message)???);
      return;
    }
    synchronized (this.jdField_a_of_type_JavaUtilArrayList)
    {
      Iterator localIterator = this.jdField_a_of_type_JavaUtilArrayList.iterator();
      while (localIterator.hasNext())
      {
        PluginPkgKey localPluginPkgKey = (PluginPkgKey)((Object[])localIterator.next())[0];
        PluginStatBehavior.setFinCode(localPluginPkgKey.pkgName, 4, 609);
        b(localPluginPkgKey, 3004, 4);
      }
      this.jdField_a_of_type_JavaUtilArrayList.clear();
      return;
    }
  }
  
  public boolean refreshPluginListIfNeeded(String paramString, int paramInt, boolean paramBoolean, IGetPluginInfoCallback.Stub paramStub)
  {
    if (paramBoolean) {}
    try
    {
      return this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy.refreshPluginListForced(paramString, paramInt, paramStub);
    }
    catch (Throwable paramString)
    {
      paramString.printStackTrace();
      break label59;
      QBPluginFactory.getInstance(this.jdField_a_of_type_AndroidContentContext).bindPluginService(this, paramInt);
      FLogger.w(TAG, "usePlugin QBPluginService Exception,Reconnected.");
      return false;
    }
    catch (RemoteException paramString)
    {
      for (;;) {}
    }
    paramBoolean = this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy.getPluginInfoAsync(paramString, paramStub, paramInt);
    return paramBoolean;
  }
  
  public boolean removePluginListner(String arg1, int paramInt, IQBPluginSystemCallback paramIQBPluginSystemCallback)
  {
    Object localObject = new PluginPkgKey(???, paramInt);
    String str = TAG;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("插件系统使用主流程:");
    localStringBuilder.append(???);
    localStringBuilder.append(",from=");
    localStringBuilder.append(???);
    localStringBuilder.append("删除callcb");
    localStringBuilder.append(paramIQBPluginSystemCallback);
    FLogger.i(str, localStringBuilder.toString());
    if (paramIQBPluginSystemCallback == null) {
      return false;
    }
    synchronized (this.jdField_a_of_type_JavaUtilHashMap)
    {
      localObject = (PluginInfo)this.jdField_a_of_type_JavaUtilHashMap.get(localObject);
      if (localObject != null) {
        try
        {
          ((PluginInfo)localObject).mSystemCallback.remove(paramIQBPluginSystemCallback);
        }
        finally {}
      }
      return true;
    }
  }
  
  public void stopPluginTask(String paramString, int paramInt)
  {
    String str = TAG;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("插件系统使用主流程:");
    localStringBuilder.append(paramString);
    localStringBuilder.append(",from=");
    localStringBuilder.append(paramString);
    localStringBuilder.append("停止插件下载任务");
    FLogger.i(str, localStringBuilder.toString());
    PluginStatBehavior.addLogPath(paramString, 4, 622);
    paramString = this.jdField_a_of_type_AndroidOsHandler.obtainMessage(7, 3010, 0, new PluginPkgKey(paramString, paramInt));
    this.jdField_a_of_type_AndroidOsHandler.sendMessage(paramString);
  }
  
  public void usePluginAsync(String paramString, int paramInt1, IQBPluginSystemCallback paramIQBPluginSystemCallback, ILoadPluginCallback paramILoadPluginCallback, IInstallPluginCallback.Stub paramStub, int paramInt2)
  {
    a(paramString, paramInt1, paramIQBPluginSystemCallback, paramILoadPluginCallback, paramStub, paramInt2, 0);
  }
  
  public static class DownloadMsgObject
  {
    public String downloadDir;
    public String downloadFileName;
    public int downloadedSize;
    public int errorCode;
    public int httpStatus;
    public int infoFrom;
    public String packageName;
    public int progress;
    public int soSize;
    public int status;
    public int taskid;
    public String url;
  }
  
  public static abstract interface ILoadPluginCallback
  {
    public abstract int load(String paramString, QBPluginItemInfo paramQBPluginItemInfo, int paramInt);
  }
  
  @Service
  public static abstract interface IPluginUseMPX
  {
    public static final ModuleProxy<IPluginUseMPX> PROXY = ModuleProxy.newProxy(IPluginUseMPX.class, new IPluginUseMPX()
    {
      public boolean get(String paramAnonymousString)
      {
        return false;
      }
      
      public void set(String paramAnonymousString, boolean paramAnonymousBoolean) {}
    });
    
    public abstract boolean get(String paramString);
    
    public abstract void set(String paramString, boolean paramBoolean);
  }
  
  @Extension
  public static abstract interface IQBPluginServiceProvider
  {
    public abstract IQBPluginService getService();
  }
  
  public static class MsgClassPluginCallback
  {
    public IQBPluginSystemCallback mCallback;
    public QBPluginSystem.PluginPkgKey mPluginKey;
    
    MsgClassPluginCallback(QBPluginSystem.PluginPkgKey paramPluginPkgKey, IQBPluginSystemCallback paramIQBPluginSystemCallback)
    {
      this.mPluginKey = paramPluginPkgKey;
      this.mCallback = paramIQBPluginSystemCallback;
    }
  }
  
  public static class PluginInfo
  {
    public boolean mCheckTimedoCheck = false;
    public IInstallPluginCallback.Stub mInstallPluginCallback = null;
    public QBPluginSystem.ILoadPluginCallback mLoadCallBack = null;
    public int mPluginFlag = 0;
    public QBPluginItemInfo mPluginItemInfo = null;
    public int mPluginStatus = -1;
    public int mPluginUseStatus = 0;
    public long mStartTimeOfTaskStep = System.currentTimeMillis();
    public ArrayList<IQBPluginSystemCallback> mSystemCallback = new ArrayList();
    
    public void clearAllPluginFlag()
    {
      this.mPluginFlag = 0;
    }
    
    public void clearPluginFlag(int paramInt)
    {
      this.mPluginFlag = ((paramInt ^ 0xFFFFFFFF) & this.mPluginFlag);
    }
    
    public int getPluginStatus()
    {
      return this.mPluginStatus;
    }
    
    public void initPluginStatus()
    {
      long l = System.currentTimeMillis();
      String str = QBPluginSystem.TAG;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("initPluginStatus before:");
      localStringBuilder.append(this.mPluginStatus);
      localStringBuilder.append(" --->");
      localStringBuilder.append(-1);
      localStringBuilder.append(",lastTime=");
      localStringBuilder.append(l - this.mStartTimeOfTaskStep);
      localStringBuilder.append("ms");
      FLogger.d(str, localStringBuilder.toString());
      this.mPluginStatus = -1;
      clearAllPluginFlag();
      this.mStartTimeOfTaskStep = l;
      this.mCheckTimedoCheck = false;
    }
    
    public boolean isPluginFlagOn(int paramInt)
    {
      return (paramInt & this.mPluginFlag) != 0;
    }
    
    public boolean isPluginTaskLoaded()
    {
      int i = this.mPluginStatus;
      boolean bool = true;
      if (i != 8)
      {
        if ((i == 6) && (this.mLoadCallBack == null)) {
          return true;
        }
        if ((this.mPluginItemInfo != null) && (((QBPluginSystem.IPluginUseMPX)QBPluginSystem.IPluginUseMPX.PROXY.get()).get(this.mPluginItemInfo.mPackageName))) {
          return true;
        }
        bool = false;
      }
      return bool;
    }
    
    public boolean isPluginTaskRuning(int paramInt)
    {
      int i = this.mPluginStatus;
      boolean bool2 = false;
      boolean bool1 = bool2;
      if (i != -1) {
        if ((i >= 8) || (this.mLoadCallBack == null))
        {
          bool1 = bool2;
          if (this.mPluginStatus < 6)
          {
            bool1 = bool2;
            if (this.mLoadCallBack != null) {}
          }
        }
        else if ((this.mPluginStatus == 6) && (this.mLoadCallBack != null))
        {
          bool1 = bool2;
        }
        else if ((this.mPluginStatus == 3) && (paramInt != this.mPluginFlag))
        {
          bool1 = bool2;
        }
        else if ((this.mPluginStatus <= 4) && (paramInt == 0) && (this.mPluginFlag != 0))
        {
          bool1 = bool2;
        }
        else
        {
          bool1 = true;
        }
      }
      String str = QBPluginSystem.TAG;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("插件系统使用主流程:插件是否在流程中 result=");
      localStringBuilder.append(bool1);
      localStringBuilder.append(",mPluginStatus=");
      localStringBuilder.append(this.mPluginStatus);
      localStringBuilder.append(",mLoadCallBack=");
      localStringBuilder.append(this.mLoadCallBack);
      localStringBuilder.append(",mPluginFlag=");
      localStringBuilder.append(this.mPluginFlag);
      localStringBuilder.append("->");
      localStringBuilder.append(paramInt);
      localStringBuilder.append("@");
      Object localObject = this.mPluginItemInfo;
      if (localObject == null) {
        localObject = null;
      } else {
        localObject = ((QBPluginItemInfo)localObject).mPackageName;
      }
      localStringBuilder.append((String)localObject);
      FLogger.i(str, localStringBuilder.toString());
      return bool1;
    }
    
    public void setPluginFlag(int paramInt)
    {
      String str = QBPluginSystem.TAG;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("setPluginFlag=");
      localStringBuilder.append(paramInt);
      FLogger.d(str, localStringBuilder.toString());
      this.mPluginFlag = paramInt;
    }
    
    public void setPluginStatus(int paramInt)
    {
      long l = System.currentTimeMillis();
      String str = QBPluginSystem.TAG;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("setPluginStatus before:");
      localStringBuilder.append(this.mPluginStatus);
      localStringBuilder.append(" --->");
      localStringBuilder.append(paramInt);
      localStringBuilder.append(",lastTime=");
      localStringBuilder.append(l - this.mStartTimeOfTaskStep);
      localStringBuilder.append("ms");
      FLogger.i(str, localStringBuilder.toString());
      this.mPluginStatus = paramInt;
      this.mStartTimeOfTaskStep = l;
      this.mCheckTimedoCheck = false;
    }
  }
  
  public static class PluginInstallMsg
  {
    public String mInstallAbsPath;
    public boolean mInstallFromDownload;
    public IInstallPluginCallback.Stub mInstallPluginCb;
    public boolean mIsNewDownloadVersion = false;
    public QBPluginSystem.PluginPkgKey mPluginKey;
    public int mPluginType;
    public QBPluginItemInfo mQBPluginItemInfo = null;
  }
  
  public static class PluginPkgKey
  {
    public int infoFrom = 0;
    public String pkgName = null;
    
    PluginPkgKey(String paramString, int paramInt)
    {
      this.pkgName = paramString;
      this.infoFrom = paramInt;
    }
    
    public boolean equals(Object paramObject)
    {
      if (!(paramObject instanceof PluginPkgKey)) {
        return false;
      }
      paramObject = (PluginPkgKey)paramObject;
      return (this.pkgName.equalsIgnoreCase(((PluginPkgKey)paramObject).pkgName)) && (this.infoFrom == ((PluginPkgKey)paramObject).infoFrom);
    }
    
    public int hashCode()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(this.pkgName);
      localStringBuilder.append(this.infoFrom);
      return localStringBuilder.toString().hashCode();
    }
  }
  
  private static class a
    extends IGetPluginInfoCallback.Stub
  {
    public int a;
    public Handler a;
    public QBPluginSystem.PluginPkgKey a;
    
    a(QBPluginSystem.PluginPkgKey paramPluginPkgKey, int paramInt, Handler paramHandler)
    {
      this.jdField_a_of_type_ComTencentCommonPluginQBPluginSystem$PluginPkgKey = null;
      this.jdField_a_of_type_Int = 0;
      this.jdField_a_of_type_AndroidOsHandler = null;
      this.jdField_a_of_type_Int = paramInt;
      this.jdField_a_of_type_ComTencentCommonPluginQBPluginSystem$PluginPkgKey = paramPluginPkgKey;
      this.jdField_a_of_type_AndroidOsHandler = paramHandler;
    }
    
    public boolean onRecvPluignInfo(String paramString, int paramInt, QBPluginItemInfo paramQBPluginItemInfo)
      throws RemoteException
    {
      Object localObject = QBPluginSystem.TAG;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("插件系统异步返回插件信息 ");
      localStringBuilder.append(paramString);
      localStringBuilder.append(",pluginListStat=");
      localStringBuilder.append(paramInt);
      localStringBuilder.append(" pluginInfo=");
      localStringBuilder.append(paramQBPluginItemInfo);
      FLogger.i((String)localObject, localStringBuilder.toString());
      paramString = this.jdField_a_of_type_AndroidOsHandler.obtainMessage(1);
      localObject = new QBPluginSystem.c(null);
      ((QBPluginSystem.c)localObject).jdField_a_of_type_ComTencentCommonPluginQBPluginItemInfo = paramQBPluginItemInfo;
      ((QBPluginSystem.c)localObject).jdField_a_of_type_ComTencentCommonPluginQBPluginSystem$PluginPkgKey = this.jdField_a_of_type_ComTencentCommonPluginQBPluginSystem$PluginPkgKey;
      paramString.obj = localObject;
      paramString.arg1 = paramInt;
      paramString.arg2 = this.jdField_a_of_type_Int;
      this.jdField_a_of_type_AndroidOsHandler.sendMessage(paramString);
      return true;
    }
  }
  
  private static class b
  {
    public int a;
    public boolean a;
    public int b = 0;
    public int c = 0;
    public int d = 0;
    public int e = 0;
    
    private b()
    {
      this.jdField_a_of_type_Boolean = false;
      this.jdField_a_of_type_Int = 0;
    }
  }
  
  private static class c
  {
    public QBPluginItemInfo a;
    public QBPluginSystem.PluginPkgKey a;
  }
  
  private static class d
    implements IPluginDownInstallCallback
  {
    private QBPluginSystem a = null;
    
    d(QBPluginSystem paramQBPluginSystem)
    {
      this.a = paramQBPluginSystem;
    }
    
    public void onGetPluginListFailed(int paramInt)
    {
      synchronized (this.a.jdField_a_of_type_JavaLangObject)
      {
        if (!this.a.jdField_a_of_type_Boolean) {
          FLogger.i(QBPluginSystem.TAG, "onGetPluginListFailed");
        }
        this.a.jdField_a_of_type_Boolean = true;
        return;
      }
    }
    
    public void onGetPluginListSucc(int paramInt)
    {
      this.a.jdField_a_of_type_Boolean = true;
    }
    
    public void onPluginDownloadCreated(String paramString1, String paramString2, int paramInt1, int paramInt2)
    {
      Object localObject1 = QBPluginSystem.TAG;
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("onPluginDownloadCreated packageName:");
      ((StringBuilder)localObject2).append(paramString1);
      ((StringBuilder)localObject2).append(", url:");
      ((StringBuilder)localObject2).append(paramString2);
      FLogger.i((String)localObject1, ((StringBuilder)localObject2).toString());
      localObject1 = this.a.b.obtainMessage();
      localObject2 = new QBPluginSystem.DownloadMsgObject();
      ((QBPluginSystem.DownloadMsgObject)localObject2).packageName = paramString1;
      ((QBPluginSystem.DownloadMsgObject)localObject2).url = paramString2;
      ((QBPluginSystem.DownloadMsgObject)localObject2).status = paramInt1;
      ((QBPluginSystem.DownloadMsgObject)localObject2).infoFrom = paramInt2;
      ((Message)localObject1).obj = localObject2;
      ((Message)localObject1).what = 6;
      this.a.b.sendMessage((Message)localObject1);
    }
    
    public void onPluginDownloadFailed(final String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3)
    {
      Object localObject = QBPluginSystem.TAG;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("onPluginDownloadFailed packageName:");
      localStringBuilder.append(paramString1);
      localStringBuilder.append(", url:");
      localStringBuilder.append(paramString2);
      localStringBuilder.append(", httpStatus:");
      localStringBuilder.append(paramInt1);
      localStringBuilder.append(", errorCode:");
      localStringBuilder.append(paramInt2);
      FLogger.i((String)localObject, localStringBuilder.toString());
      final int i = 101;
      if ((paramInt1 >= 400) && (paramInt1 < 600)) {
        i = 3007;
      } else if ((paramInt2 != 55536) && ((paramInt1 != -1) || (paramInt2 != -1)) && (paramInt1 != 6))
      {
        if ((paramInt2 != 1) && (paramInt2 != 2) && (paramInt2 != 41))
        {
          if (paramInt2 != 101) {
            i = paramInt2 + 4000;
          }
        }
        else {
          i = 3014;
        }
      }
      else {
        i = 3010;
      }
      paramString1 = new QBPluginSystem.PluginPkgKey(paramString1, paramInt3);
      localObject = QBPluginSystem.a(this.a, paramString1);
      if ((!((QBPluginSystem.PluginInfo)localObject).isPluginTaskLoaded()) && (paramString2.equalsIgnoreCase(((QBPluginSystem.PluginInfo)localObject).mPluginItemInfo.mUrl)))
      {
        ((QBPluginSystem.PluginInfo)localObject).mPluginItemInfo.mDownloadDir = "";
        paramString2 = QBPluginSystem.TAG;
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("插件系统使用主流程:");
        localStringBuilder.append(paramString1.pkgName);
        localStringBuilder.append(",from=");
        localStringBuilder.append(paramString1.infoFrom);
        localStringBuilder.append(",插件下载失败，流程结束");
        FLogger.i(paramString2, localStringBuilder.toString());
        this.a.a(paramString1);
        long l = ((QBPluginSystem.PluginInfo)localObject).mStartTimeOfTaskStep;
        ((QBPluginSystem.PluginInfo)localObject).mStartTimeOfTaskStep = System.currentTimeMillis();
        paramString2 = paramString1.pkgName;
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(i);
        localStringBuilder.append("(");
        localStringBuilder.append(paramInt1);
        localStringBuilder.append("_");
        localStringBuilder.append(paramInt2);
        localStringBuilder.append("_");
        localStringBuilder.append(((QBPluginSystem.PluginInfo)localObject).mStartTimeOfTaskStep - l);
        localStringBuilder.append(")");
        PluginStatBehavior.addLogPath(paramString2, 4, localStringBuilder.toString());
        BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
        {
          public void doRun()
          {
            final boolean bool = ConnectivityDetector.detectWithTCPPing();
            QBPluginSystem.this.b.post(new Runnable()
            {
              public void run()
              {
                if (!bool)
                {
                  PluginStatBehavior.setFinCode(QBPluginSystem.d.1.this.jdField_a_of_type_ComTencentCommonPluginQBPluginSystem$PluginPkgKey.pkgName, 4, 653);
                  QBPluginSystem.d.a(QBPluginSystem.d.1.this.jdField_a_of_type_ComTencentCommonPluginQBPluginSystem$d).b(QBPluginSystem.d.1.this.jdField_a_of_type_ComTencentCommonPluginQBPluginSystem$PluginPkgKey, 653, 1);
                }
                else if (QBPluginSystem.d.1.this.jdField_a_of_type_Int == 3010)
                {
                  PluginStatBehavior.setFinCode(QBPluginSystem.d.1.this.jdField_a_of_type_ComTencentCommonPluginQBPluginSystem$PluginPkgKey.pkgName, 4, 431);
                  QBPluginSystem.d.a(QBPluginSystem.d.1.this.jdField_a_of_type_ComTencentCommonPluginQBPluginSystem$d).b(QBPluginSystem.d.1.this.jdField_a_of_type_ComTencentCommonPluginQBPluginSystem$PluginPkgKey, 3010, 1);
                }
                else if (QBPluginSystem.d.1.this.jdField_a_of_type_Int == 3010)
                {
                  PluginStatBehavior.setFinCode(QBPluginSystem.d.1.this.jdField_a_of_type_ComTencentCommonPluginQBPluginSystem$PluginPkgKey.pkgName, 4, 431);
                }
                else
                {
                  PluginStatBehavior.setFinCode(QBPluginSystem.d.1.this.jdField_a_of_type_ComTencentCommonPluginQBPluginSystem$PluginPkgKey.pkgName, 4, 414);
                }
                QBPluginSystem.d.a(QBPluginSystem.d.1.this.jdField_a_of_type_ComTencentCommonPluginQBPluginSystem$d).b(QBPluginSystem.d.1.this.jdField_a_of_type_ComTencentCommonPluginQBPluginSystem$PluginPkgKey, QBPluginSystem.d.1.this.jdField_a_of_type_Int, 1);
              }
            });
          }
        });
      }
    }
    
    public void onPluginDownloadProgress(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      Object localObject1 = QBPluginSystem.TAG;
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("onPluginDownloadProgress packageName:");
      ((StringBuilder)localObject2).append(paramString1);
      ((StringBuilder)localObject2).append(", url:");
      ((StringBuilder)localObject2).append(paramString2);
      ((StringBuilder)localObject2).append(", downloadedSize:");
      ((StringBuilder)localObject2).append(paramInt1);
      ((StringBuilder)localObject2).append(", progress:");
      ((StringBuilder)localObject2).append(paramInt2);
      FLogger.d((String)localObject1, ((StringBuilder)localObject2).toString());
      localObject1 = this.a.b.obtainMessage();
      localObject2 = new QBPluginSystem.DownloadMsgObject();
      ((QBPluginSystem.DownloadMsgObject)localObject2).packageName = paramString1;
      ((QBPluginSystem.DownloadMsgObject)localObject2).url = paramString2;
      ((QBPluginSystem.DownloadMsgObject)localObject2).downloadedSize = paramInt1;
      ((QBPluginSystem.DownloadMsgObject)localObject2).progress = paramInt2;
      ((QBPluginSystem.DownloadMsgObject)localObject2).status = paramInt3;
      ((QBPluginSystem.DownloadMsgObject)localObject2).infoFrom = paramInt4;
      ((Message)localObject1).obj = localObject2;
      ((Message)localObject1).what = 7;
      this.a.b.sendMessage((Message)localObject1);
    }
    
    public void onPluginDownloadStarted(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      Object localObject1 = QBPluginSystem.TAG;
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("onPluginDownloadStarted packageName:");
      ((StringBuilder)localObject2).append(paramString1);
      ((StringBuilder)localObject2).append(", url:");
      ((StringBuilder)localObject2).append(paramString2);
      ((StringBuilder)localObject2).append(", downloadedSize:");
      ((StringBuilder)localObject2).append(paramInt1);
      ((StringBuilder)localObject2).append(", progress:");
      ((StringBuilder)localObject2).append(paramInt2);
      FLogger.i((String)localObject1, ((StringBuilder)localObject2).toString());
      localObject1 = this.a.b.obtainMessage();
      localObject2 = new QBPluginSystem.DownloadMsgObject();
      ((QBPluginSystem.DownloadMsgObject)localObject2).infoFrom = paramInt4;
      ((QBPluginSystem.DownloadMsgObject)localObject2).packageName = paramString1;
      ((QBPluginSystem.DownloadMsgObject)localObject2).url = paramString2;
      ((QBPluginSystem.DownloadMsgObject)localObject2).downloadedSize = paramInt1;
      ((QBPluginSystem.DownloadMsgObject)localObject2).progress = paramInt2;
      ((QBPluginSystem.DownloadMsgObject)localObject2).status = paramInt3;
      ((Message)localObject1).obj = localObject2;
      ((Message)localObject1).what = 5;
      this.a.b.sendMessage((Message)localObject1);
    }
    
    public void onPluginDownloadSuccessed(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
    {
      Object localObject1 = QBPluginSystem.TAG;
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("onPluginDownloadSuccessed packageName:");
      ((StringBuilder)localObject2).append(paramString1);
      ((StringBuilder)localObject2).append(", downloadDir:");
      ((StringBuilder)localObject2).append(paramString2);
      ((StringBuilder)localObject2).append(", url:");
      ((StringBuilder)localObject2).append(paramString4);
      ((StringBuilder)localObject2).append(", downloadFileName:");
      ((StringBuilder)localObject2).append(paramString3);
      ((StringBuilder)localObject2).append(",useAnti=");
      ((StringBuilder)localObject2).append(paramBoolean);
      FLogger.i((String)localObject1, ((StringBuilder)localObject2).toString());
      localObject1 = this.a.b.obtainMessage();
      localObject2 = new QBPluginSystem.DownloadMsgObject();
      QBPluginSystem.PluginInfo localPluginInfo = QBPluginSystem.a(this.a, new QBPluginSystem.PluginPkgKey(paramString1, paramInt4));
      QBPluginSystem.PluginPkgKey localPluginPkgKey = new QBPluginSystem.PluginPkgKey(paramString1, paramInt4);
      if ((!localPluginInfo.isPluginTaskLoaded()) && (paramString4.equalsIgnoreCase(localPluginInfo.mPluginItemInfo.mUrl)))
      {
        long l1 = paramInt1;
        long l2 = StringUtils.parseLong(localPluginInfo.mPluginItemInfo.mPackageSize, -1L);
        String str1 = localPluginInfo.mPluginItemInfo.mMd5;
        ByteBuffer localByteBuffer = FileUtilsF.read(new File(paramString2, paramString3).getAbsolutePath(), 0L, 256);
        if ((l2 > 0L) && (l1 != l2))
        {
          PluginStatBehavior.setSvrMd5(paramString1, 4, new String(localByteBuffer.array(), 0, localByteBuffer.position()));
          FileUtilsF.getInstance().releaseByteBuffer(localByteBuffer);
          paramString2 = new StringBuilder();
          paramString2.append("427PS[");
          paramString2.append(l2);
          paramString2.append("]FS[");
          paramString2.append(l1);
          paramString2.append("]");
          paramString2.append(paramBoolean);
          PluginStatBehavior.addLogPath(paramString1, 4, paramString2.toString());
          PluginStatBehavior.setFinCode(paramString1, 4, 427);
          this.a.b(localPluginPkgKey, 427, 1);
          return;
        }
        String str2 = ByteUtils.byteToHexString(Md5Utils.getMD5(localByteBuffer.array(), 0, localByteBuffer.position()));
        if ((!TextUtils.isEmpty(str2)) && (!TextUtils.isEmpty(str1)) && (str2.length() == str1.length()) && (!str2.equals(str1)))
        {
          PluginStatBehavior.setSvrMd5(paramString1, 4, new String(localByteBuffer.array(), 0, localByteBuffer.position()));
          FileUtilsF.getInstance().releaseByteBuffer(localByteBuffer);
          paramString2 = new StringBuilder();
          paramString2.append("[");
          paramString2.append(str2);
          paramString2.append("],[");
          paramString2.append(str1);
          paramString2.append("]");
          paramString2.append(paramBoolean);
          PluginStatBehavior.setLocalMd5(paramString1, 4, paramString2.toString());
          PluginStatBehavior.addLogPath(paramString1, 4, 428);
          PluginStatBehavior.setFinCode(paramString1, 4, 428);
          this.a.b(localPluginPkgKey, 428, 1);
          return;
        }
        FileUtilsF.getInstance().releaseByteBuffer(localByteBuffer);
        ((QBPluginSystem.DownloadMsgObject)localObject2).packageName = paramString1;
        paramString1 = localPluginInfo.mPluginItemInfo;
        ((QBPluginSystem.DownloadMsgObject)localObject2).downloadDir = paramString2;
        paramString1.mDownloadDir = paramString2;
        paramString1 = localPluginInfo.mPluginItemInfo;
        ((QBPluginSystem.DownloadMsgObject)localObject2).downloadFileName = paramString3;
        paramString1.mDownloadFileName = paramString3;
        ((QBPluginSystem.DownloadMsgObject)localObject2).url = paramString4;
        ((QBPluginSystem.DownloadMsgObject)localObject2).downloadedSize = paramInt1;
        ((QBPluginSystem.DownloadMsgObject)localObject2).progress = paramInt2;
        ((QBPluginSystem.DownloadMsgObject)localObject2).status = paramInt3;
        ((QBPluginSystem.DownloadMsgObject)localObject2).infoFrom = paramInt4;
        ((Message)localObject1).obj = localObject2;
        ((Message)localObject1).what = 8;
        this.a.b.sendMessage((Message)localObject1);
      }
    }
    
    public void onPluginInstallFailed(String paramString, int paramInt1, int paramInt2)
    {
      Object localObject = QBPluginSystem.TAG;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("onPluginInstallFailed,this:");
      localStringBuilder.append(this);
      localStringBuilder.append(",pluginName:");
      localStringBuilder.append(paramString);
      localStringBuilder.append(", erroCode:");
      localStringBuilder.append(paramInt1);
      FLogger.i((String)localObject, localStringBuilder.toString());
      localObject = this.a.jdField_a_of_type_AndroidOsHandler.obtainMessage();
      ((Message)localObject).what = 13;
      ((Message)localObject).obj = new QBPluginSystem.PluginPkgKey(paramString, paramInt2);
      ((Message)localObject).arg1 = paramInt1;
      this.a.jdField_a_of_type_AndroidOsHandler.sendMessage((Message)localObject);
    }
    
    public void onPluginInstallSuccessed(String paramString, QBPluginItemInfo paramQBPluginItemInfo, int paramInt1, int paramInt2)
    {
      String str = QBPluginSystem.TAG;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("onPluginInstallSuccessed packageName:");
      localStringBuilder.append(paramString);
      localStringBuilder.append(", retCode:");
      localStringBuilder.append(paramInt1);
      FLogger.i(str, localStringBuilder.toString());
      paramString = this.a.jdField_a_of_type_AndroidOsHandler.obtainMessage();
      paramString.what = 12;
      paramString.obj = paramQBPluginItemInfo;
      paramString.arg1 = paramInt1;
      paramString.arg2 = paramInt2;
      this.a.jdField_a_of_type_AndroidOsHandler.sendMessage(paramString);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\plugin\QBPluginSystem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */