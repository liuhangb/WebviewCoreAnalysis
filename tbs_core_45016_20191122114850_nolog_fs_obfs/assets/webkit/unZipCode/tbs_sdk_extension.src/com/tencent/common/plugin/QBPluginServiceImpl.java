package com.tencent.common.plugin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.Signature;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import com.tencent.common.connectivitydetect.ConnectivityDetector;
import com.tencent.common.http.Apn;
import com.tencent.common.manifest.AppManifest;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.common.utils.ByteUtils;
import com.tencent.common.utils.FileUtils;
import com.tencent.common.utils.FileUtilsF;
import com.tencent.common.utils.GdiMeasureImpl;
import com.tencent.common.utils.Md5Utils;
import com.tencent.common.utils.SignatureUtil;
import com.tencent.common.utils.StorageDirs;
import com.tencent.common.utils.StringUtils;
import com.tencent.common.utils.TbsMode;
import com.tencent.common.wup.IWUPRequestCallBack;
import com.tencent.common.wup.WUPRequestBase;
import com.tencent.common.wup.WUPResponseBase;
import com.tencent.common.wup.WUPTaskProxy;
import com.tencent.mtt.ContextHolder;
import com.tencent.mtt.base.task.Task;
import com.tencent.mtt.base.task.TaskObserver;
import com.tencent.mtt.base.task.WalledGardenDetectTask;
import com.tencent.mtt.browser.download.engine.DownloadInfo;
import com.tencent.mtt.browser.download.engine.DownloadInfo.DownloadTaskConfirmObserver;
import com.tencent.mtt.browser.download.engine.DownloadTask;
import com.tencent.tbs.common.MTT.UniPluginReq;
import com.tencent.tbs.common.MTT.UniPluginRsp;
import com.tencent.tbs.common.download.BaseDownloadManager.OnDownloadedTaskListener;
import com.tencent.tbs.common.download.IDownloadManager;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;
import org.json.JSONObject;

public class QBPluginServiceImpl
  extends IQBPluginService.Stub
  implements IWUPRequestCallBack, TaskObserver, BaseDownloadManager.OnDownloadedTaskListener
{
  public static int ERROR_CHECK_PLUGIN_SIGN_FAILED = 30;
  public static int ERROR_DIR_DATA_NOSPACE = 17;
  public static int ERROR_GET_INSTALL_USER_STOP = 32;
  public static int ERROR_GET_PLUGININFO_FAILED = 31;
  public static final int LOCAL_PLUGIN_FOUND = 1;
  public static final int LOCAL_PLUGIN_NEEDUNZIP = 2;
  public static final int LOCAL_PLUGIN_NONE = 0;
  public static final boolean PLUGIN_DEVELOP_MODE = false;
  public static final int PLUGIN_POS_NULL = 0;
  public static final int PLUGIN_POS_SETTING = 2;
  public static final int PLUGIN_POS_TOOL = 1;
  public static final int PLUGIN_REQ_STATUS_REQING = 1;
  public static int REQ_ERROR_NO_IMPL = 0;
  public static int REQ_ERROR_NO_NEED_SEND_REQUEST = 0;
  public static int REQ_ERROR_REQUEST_SENDING = 1;
  public static int REQ_ERROR_REQUEST_SEND_FAILED = 0;
  public static int REQ_ERROR_REQUEST_SEND_FAILED_NETWORK_ERR = 0;
  public static int REQ_ERROR_REQUEST_SEND_FAILED_NETWORK_OK = 0;
  public static int REQ_ERROR_REQUEST_SEND_SUCCESS = 0;
  public static final String TAG = "QBPluginServiceImpl_TBS";
  public static final int TYPE_INSTALL_ON_DATA = 1;
  public static final int TYPE_INSTALL_ON_INTER_SDCARD = 2;
  public static final int TYPE_INSTALL_ON_OUTER_SDCARD = 3;
  public static int TYPE_PLUGIN_DOWNLOAD_CANCLED = 14;
  public static int TYPE_PLUGIN_DOWNLOAD_CREATE = 2;
  public static int TYPE_PLUGIN_DOWNLOAD_FAILED = 5;
  public static int TYPE_PLUGIN_DOWNLOAD_PROGRESS = 6;
  public static int TYPE_PLUGIN_DOWNLOAD_START = 3;
  public static int TYPE_PLUGIN_DOWNLOAD_SUCESSED = 4;
  public static int TYPE_PLUGIN_GETLIST_FAILED = 1;
  public static int TYPE_PLUGIN_GETLIST_SUCC = 0;
  public static int TYPE_PLUGIN_INSTALL_COMPLETE = 11;
  public static int TYPE_PLUGIN_INSTALL_FAILED = 13;
  public static int TYPE_PLUGIN_INSTALL_START = 12;
  public static int TYPE_PLUGIN_STOP_DOWNLOAD = 16;
  public static int TYPE_RETRY_PULL_PLUGINLIST = 20;
  private static Signature jdField_a_of_type_AndroidContentPmSignature;
  private static QBPluginServiceImpl jdField_a_of_type_ComTencentCommonPluginQBPluginServiceImpl;
  private static final AtomicInteger jdField_a_of_type_JavaUtilConcurrentAtomicAtomicInteger;
  public static long[] m24HourPullListTime = { 0L, 0L, 0L };
  public static Context mCallerAppContext;
  public static String mFileMode = "755";
  public static ILogPushUploadHelper mFilePushLogger;
  public static IPluginLoader mIPluginLoader;
  public static Context mTesProviderAppContext;
  public static IPluginDir msIPluginDirUi;
  public static IPluginRelateFunc msRelateFunc;
  public static HashMap<String, PluginConfigInfo> pluginConfigInfoHashMap;
  private int jdField_a_of_type_Int = 0;
  private long jdField_a_of_type_Long = 30000L;
  private PluginCallbackHandler jdField_a_of_type_ComTencentCommonPluginPluginCallbackHandler = null;
  IDownloadSpecicalWhiteList jdField_a_of_type_ComTencentCommonPluginQBPluginServiceImpl$IDownloadSpecicalWhiteList = null;
  private WalledGardenDetectTask jdField_a_of_type_ComTencentMttBaseTaskWalledGardenDetectTask = null;
  private IDownloadManager jdField_a_of_type_ComTencentTbsCommonDownloadIDownloadManager = null;
  ArrayList<IQBPluginCallback> jdField_a_of_type_JavaUtilArrayList = null;
  List<QBPluginSystem.PluginPkgKey> jdField_a_of_type_JavaUtilList = new ArrayList();
  boolean jdField_a_of_type_Boolean = false;
  private ArrayList<AsynGetPluginInfo> jdField_b_of_type_JavaUtilArrayList = new ArrayList();
  boolean jdField_b_of_type_Boolean = false;
  private boolean c = false;
  private boolean d = true;
  public HashMap<QBPluginSystem.PluginPkgKey, QBPluginInfo> mPackageName2PluginInfoMap = new HashMap();
  public int mReqStatusTbs = 0;
  public int mReqStatusUi = 0;
  public Object synPluginListObject = new Object();
  
  static
  {
    mCallerAppContext = null;
    mTesProviderAppContext = null;
    jdField_a_of_type_ComTencentCommonPluginQBPluginServiceImpl = null;
    pluginConfigInfoHashMap = new HashMap();
    msIPluginDirUi = null;
    msRelateFunc = null;
    mIPluginLoader = null;
    jdField_a_of_type_JavaUtilConcurrentAtomicAtomicInteger = new AtomicInteger(0);
    jdField_a_of_type_AndroidContentPmSignature = null;
    REQ_ERROR_NO_IMPL = -12;
    REQ_ERROR_REQUEST_SEND_FAILED = -11;
    REQ_ERROR_NO_NEED_SEND_REQUEST = -10;
    REQ_ERROR_REQUEST_SEND_FAILED_NETWORK_OK = -2;
    REQ_ERROR_REQUEST_SEND_FAILED_NETWORK_ERR = -1;
  }
  
  private QBPluginServiceImpl()
  {
    a(new TBSPluginCallbackHandler());
  }
  
  private int a(String paramString1, String paramString2, int paramInt)
  {
    synchronized (this.synPluginListObject)
    {
      PluginStatBehavior.setOpType("PluginList", PluginStatBehavior.getOpTyepPluginList(paramInt));
      PluginStatBehavior.addLogPath("PluginList", PluginStatBehavior.getOpTyepPluginList(paramInt), 311);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("requestPluginList  :");
      localStringBuilder.append(paramString1);
      localStringBuilder.append(",mReqStatus=");
      localStringBuilder.append(getPluginRequestListStatus(paramInt));
      FLogger.i("QBPluginServiceImpl_TBS", localStringBuilder.toString());
      paramString1 = getPluginRequest(paramString1, paramString2, paramInt);
      if (paramString1 == null)
      {
        paramInt = REQ_ERROR_REQUEST_SENDING;
        return paramInt;
      }
      if (WUPTaskProxy.send(paramString1))
      {
        FLogger.i("QBPluginServiceImpl_TBS", "getPluginRequest  :Send Out");
        setPluginRequestListStatus(1, paramInt);
        PluginStatBehavior.addLogPath("PluginList", PluginStatBehavior.getOpTyepPluginList(paramInt), 313);
        paramInt = REQ_ERROR_REQUEST_SEND_SUCCESS;
        return paramInt;
      }
      setPluginRequestListStatus(3, paramInt);
      FLogger.i("QBPluginServiceImpl_TBS", "getPluginRequest  :Send Failed");
      PluginStatBehavior.addLogPath("PluginList", PluginStatBehavior.getOpTyepPluginList(paramInt), 314);
      PluginStatBehavior.setFinCode("PluginList", PluginStatBehavior.getOpTyepPluginList(paramInt), 314);
      paramInt = REQ_ERROR_REQUEST_SEND_FAILED;
      return paramInt;
    }
  }
  
  private int a(String paramString1, String paramString2, AsynGetPluginInfo paramAsynGetPluginInfo, int paramInt)
  {
    for (;;)
    {
      boolean bool;
      Object localObject1;
      synchronized (this.synPluginListObject)
      {
        bool = PluginSetting.getInstance(callerAppContext()).pluginListSyncSameToSvr(paramInt);
        if ((bool) && (!can24HourPullList(paramInt)))
        {
          paramInt = REQ_ERROR_NO_NEED_SEND_REQUEST;
          return paramInt;
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append((String)localObject1);
          localStringBuilder.append(paramString2);
          paramString2 = localStringBuilder.toString();
          PluginStatBehavior.setOpType("PluginList", PluginStatBehavior.getOpTyepPluginList(paramInt));
          PluginStatBehavior.addLogPath("PluginList", PluginStatBehavior.getOpTyepPluginList(paramInt), 311);
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("requestPluginList  :");
          ((StringBuilder)localObject1).append(paramString1);
          ((StringBuilder)localObject1).append(",mReqStatus=");
          ((StringBuilder)localObject1).append(getPluginRequestListStatus(paramInt));
          FLogger.i("QBPluginServiceImpl_TBS", ((StringBuilder)localObject1).toString());
          paramString1 = getPluginRequest(paramString1, paramString2, paramInt);
          if (paramString1 == null)
          {
            paramString1 = this.jdField_b_of_type_JavaUtilArrayList;
            if (paramAsynGetPluginInfo != null) {}
            try
            {
              this.jdField_b_of_type_JavaUtilArrayList.add(paramAsynGetPluginInfo);
              paramInt = REQ_ERROR_REQUEST_SENDING;
              return paramInt;
            }
            finally {}
          }
          if (WUPTaskProxy.send(paramString1))
          {
            FLogger.i("QBPluginServiceImpl_TBS", "getPluginRequest  :Send Out");
            setPluginRequestListStatus(1, paramInt);
            PluginStatBehavior.addLogPath("PluginList", PluginStatBehavior.getOpTyepPluginList(paramInt), 313);
            paramString1 = this.jdField_b_of_type_JavaUtilArrayList;
            if (paramAsynGetPluginInfo != null) {}
            try
            {
              this.jdField_b_of_type_JavaUtilArrayList.add(paramAsynGetPluginInfo);
              paramInt = REQ_ERROR_REQUEST_SEND_SUCCESS;
              return paramInt;
            }
            finally {}
          }
          setPluginRequestListStatus(3, paramInt);
          FLogger.i("QBPluginServiceImpl_TBS", "getPluginRequest  :Send Failed");
          PluginStatBehavior.addLogPath("PluginList", PluginStatBehavior.getOpTyepPluginList(paramInt), 314);
          PluginStatBehavior.setFinCode("PluginList", PluginStatBehavior.getOpTyepPluginList(paramInt), 314);
          paramInt = REQ_ERROR_REQUEST_SEND_FAILED;
          return paramInt;
        }
      }
      if (!bool) {
        localObject1 = "NotYet_";
      } else {
        localObject1 = "24Hour_";
      }
    }
  }
  
  private void a(int paramInt)
  {
    synchronized (this.jdField_b_of_type_JavaUtilArrayList)
    {
      Iterator localIterator = this.jdField_b_of_type_JavaUtilArrayList.iterator();
      while (localIterator.hasNext())
      {
        AsynGetPluginInfo localAsynGetPluginInfo = (AsynGetPluginInfo)localIterator.next();
        if ((localAsynGetPluginInfo != null) && (!TextUtils.isEmpty(localAsynGetPluginInfo.pkgName)) && (localAsynGetPluginInfo.mCallBack != null))
        {
          QBPluginItemInfo localQBPluginItemInfo = QBPluginDBHelper.getInstance(dbHelperContext()).getPluginItemInfo(localAsynGetPluginInfo.pkgName, localAsynGetPluginInfo.infoFrom);
          Object localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("callGetPluginInfoBack ret_code=");
          ((StringBuilder)localObject2).append(paramInt);
          ((StringBuilder)localObject2).append(",callback.pkgName=");
          ((StringBuilder)localObject2).append(localAsynGetPluginInfo.pkgName);
          ((StringBuilder)localObject2).append(",callback.infoFrom=");
          ((StringBuilder)localObject2).append(localAsynGetPluginInfo.infoFrom);
          ((StringBuilder)localObject2).append(",pluginItemInfo=");
          ((StringBuilder)localObject2).append(localQBPluginItemInfo);
          FLogger.i("QBPluginServiceImpl_TBS", ((StringBuilder)localObject2).toString());
          try
          {
            localObject2 = localAsynGetPluginInfo.pkgName;
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("mem=");
            localStringBuilder.append(QBPluginDBHelper.getInstance(dbHelperContext()).mPluginDatasInited);
            localStringBuilder.append("_");
            localStringBuilder.append(QBPluginDBHelper.getInstance(dbHelperContext()).mPluginGetError);
            PluginStatBehavior.addLogPath((String)localObject2, 4, localStringBuilder.toString());
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("getPluginInfoAsy packageName=");
            ((StringBuilder)localObject2).append(localAsynGetPluginInfo.pkgName);
            ((StringBuilder)localObject2).append(",infoFrom=");
            ((StringBuilder)localObject2).append(0);
            ((StringBuilder)localObject2).append(" callGetPluginInfoBack");
            FLogger.i("QBPluginServiceImpl_TBS", ((StringBuilder)localObject2).toString());
            localAsynGetPluginInfo.mCallBack.onRecvPluignInfo(localAsynGetPluginInfo.pkgName, paramInt, localQBPluginItemInfo);
          }
          catch (RemoteException localRemoteException)
          {
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("getPluginInfoAsy packageName=");
            ((StringBuilder)localObject2).append(localAsynGetPluginInfo.pkgName);
            ((StringBuilder)localObject2).append(",infoFrom=");
            ((StringBuilder)localObject2).append(0);
            ((StringBuilder)localObject2).append(" nocallGetPluginInfoBack Exception");
            FLogger.i("QBPluginServiceImpl_TBS", ((StringBuilder)localObject2).toString());
            localRemoteException.printStackTrace();
          }
        }
      }
      this.jdField_b_of_type_JavaUtilArrayList.clear();
      return;
    }
  }
  
  private void a(int paramInt, IPluginDir paramIPluginDir)
  {
    IPluginLocalConfigExt[] arrayOfIPluginLocalConfigExt = (IPluginLocalConfigExt[])AppManifest.getInstance().queryExtensions(IPluginLocalConfigExt.class);
    int j = arrayOfIPluginLocalConfigExt.length;
    int i = 0;
    while (i < j)
    {
      Object localObject = arrayOfIPluginLocalConfigExt[i].addPluginLocalConfig(paramInt, paramIPluginDir);
      if (localObject != null)
      {
        localObject = ((Map)localObject).entrySet().iterator();
        while (((Iterator)localObject).hasNext())
        {
          Map.Entry localEntry = (Map.Entry)((Iterator)localObject).next();
          pluginConfigInfoHashMap.put(localEntry.getKey(), localEntry.getValue());
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("initPluginConfigInfo: ");
          localStringBuilder.append((String)localEntry.getKey());
          localStringBuilder.append(" -> ");
          localStringBuilder.append(localEntry.getValue());
          localStringBuilder.append("");
          FLogger.i("QBPluginServiceImpl_TBS", localStringBuilder.toString());
        }
      }
      i += 1;
    }
  }
  
  private void a(PluginCallbackHandler paramPluginCallbackHandler)
  {
    paramPluginCallbackHandler.init(this);
    this.jdField_a_of_type_ComTencentCommonPluginPluginCallbackHandler = paramPluginCallbackHandler;
    this.jdField_a_of_type_ComTencentCommonPluginPluginCallbackHandler.setCallBackList(this.jdField_a_of_type_JavaUtilArrayList);
  }
  
  private void a(QBPluginItemInfo paramQBPluginItemInfo, int paramInt1, int paramInt2, int paramInt3)
  {
    Message localMessage = getPluginCallbackHandler().obtainMessage();
    localMessage.obj = paramQBPluginItemInfo;
    localMessage.what = paramInt1;
    localMessage.arg1 = paramInt2;
    localMessage.arg2 = paramInt3;
    getPluginCallbackHandler().sendMessage(localMessage);
  }
  
  private void a(String paramString, int paramInt1, int paramInt2, IInstallPluginCallback paramIInstallPluginCallback, QBPluginItemInfo paramQBPluginItemInfo, boolean paramBoolean)
    throws RemoteException
  {
    if ((!TextUtils.isEmpty(paramQBPluginItemInfo.mDownloadDir)) && (!TextUtils.isEmpty(paramQBPluginItemInfo.mDownloadFileName)))
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append(paramQBPluginItemInfo.mDownloadDir);
      ((StringBuilder)localObject).append("/");
      ((StringBuilder)localObject).append(paramQBPluginItemInfo.mDownloadFileName);
      String str1 = ((StringBuilder)localObject).toString();
      PluginStatBehavior.setOpType(paramString, 3);
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(str1);
      ((StringBuilder)localObject).append(";installFrom=");
      ((StringBuilder)localObject).append(paramBoolean);
      PluginStatBehavior.setInstallFileName(paramString, 3, ((StringBuilder)localObject).toString());
      if ((paramIInstallPluginCallback != null) && (!paramIInstallPluginCallback.canInstallPlugin(paramString, paramQBPluginItemInfo)))
      {
        paramIInstallPluginCallback = new StringBuilder();
        paramIInstallPluginCallback.append("installPluginImpl,pluginName:");
        paramIInstallPluginCallback.append(paramString);
        paramIInstallPluginCallback.append(" user not permit to install");
        FLogger.i("QBPluginServiceImpl_TBS", paramIInstallPluginCallback.toString());
        PluginStatBehavior.addLogPath(paramString, 3, 564);
        PluginStatBehavior.setFinCode(paramString, 3, 564);
        a(paramQBPluginItemInfo, TYPE_PLUGIN_INSTALL_FAILED, 6002, paramInt1);
        return;
      }
      int i = StringUtils.parseInt(paramQBPluginItemInfo.mVersion, -1);
      String str2 = paramQBPluginItemInfo.mMd5;
      localObject = new File(str1);
      int j = preInstallCheck(paramString, (File)localObject, paramQBPluginItemInfo, paramInt2, i, str2, paramBoolean);
      if (j != 0)
      {
        paramIInstallPluginCallback = new StringBuilder();
        paramIInstallPluginCallback.append("installPluginImpl(");
        paramIInstallPluginCallback.append(paramString);
        paramIInstallPluginCallback.append(") preInstallCheck=false");
        FLogger.i("QBPluginServiceImpl_TBS", paramIInstallPluginCallback.toString());
        if (((File)localObject).exists()) {
          FileUtilsF.deleteQuietly((File)localObject);
        }
        a(paramQBPluginItemInfo, TYPE_PLUGIN_INSTALL_FAILED, j, paramInt1);
        return;
      }
      paramBoolean = false;
      switch (paramInt2)
      {
      default: 
        switch (paramInt2)
        {
        default: 
          paramBoolean = false;
          paramInt2 = 0;
          break;
        case 11: 
          paramBoolean = PluginUtils.installNovelPlugin(callerAppContext(), dbHelperContext(), str1, paramString, i, paramQBPluginItemInfo, paramIInstallPluginCallback, paramInt1);
          paramInt2 = 0;
          break;
        case 10: 
        case 12: 
        case 13: 
          paramBoolean = PluginUtils.installCopyTypePlugin(callerAppContext(), dbHelperContext(), (File)localObject, str1, paramString, i, paramQBPluginItemInfo, paramIInstallPluginCallback, paramInt1);
          paramInt2 = 0;
          break;
        case 9: 
          paramBoolean = PluginUtils.installFontPlugin(callerAppContext(), dbHelperContext(), str1, paramString, i, paramQBPluginItemInfo, paramIInstallPluginCallback, paramInt1);
          paramInt2 = 0;
        }
        break;
      case 2: 
        paramBoolean = PluginUtils.installJarPlugin(callerAppContext(), dbHelperContext(), str1, paramString, 2, i, str2, paramQBPluginItemInfo, paramInt1);
        paramInt2 = 0;
        break;
      case 1: 
        paramInt2 = ZipPluginUtils.installPluginFromSdCard(callerAppContext(), dbHelperContext(), (File)localObject, str1, paramString, i, str2, paramQBPluginItemInfo, paramInt1);
        if (paramInt2 == 0) {
          paramBoolean = true;
        }
        break;
      }
      paramIInstallPluginCallback = new StringBuilder();
      paramIInstallPluginCallback.append("installPluginImpl(");
      paramIInstallPluginCallback.append(paramString);
      paramIInstallPluginCallback.append(") needNotify= installRet=");
      paramIInstallPluginCallback.append(paramBoolean);
      paramIInstallPluginCallback.append(" installRetCode=");
      paramIInstallPluginCallback.append(paramInt2);
      FLogger.i("QBPluginServiceImpl_TBS", paramIInstallPluginCallback.toString());
      if (paramBoolean)
      {
        paramIInstallPluginCallback = QBPluginDBHelper.getInstance(dbHelperContext());
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(i);
        ((StringBuilder)localObject).append("");
        paramIInstallPluginCallback.updatePluginInstallVersion(paramString, ((StringBuilder)localObject).toString(), paramInt1);
        a(paramQBPluginItemInfo, TYPE_PLUGIN_INSTALL_COMPLETE, paramInt2, paramInt1);
        return;
      }
      a(paramQBPluginItemInfo, TYPE_PLUGIN_INSTALL_FAILED, paramInt2, paramInt1);
      if (((File)localObject).exists()) {
        ((File)localObject).delete();
      }
      return;
    }
    a(paramQBPluginItemInfo, TYPE_PLUGIN_INSTALL_FAILED, 6001, paramInt1);
  }
  
  /* Error */
  private static java.security.cert.Certificate[] a(java.util.jar.JarFile paramJarFile, java.util.jar.JarEntry paramJarEntry, byte[] paramArrayOfByte, String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokevirtual 629	java/util/jar/JarFile:getInputStream	(Ljava/util/zip/ZipEntry;)Ljava/io/InputStream;
    //   5: astore 4
    //   7: aload 4
    //   9: astore_0
    //   10: aload 4
    //   12: aload_2
    //   13: iconst_0
    //   14: aload_2
    //   15: arraylength
    //   16: invokevirtual 635	java/io/InputStream:read	([BII)I
    //   19: iconst_m1
    //   20: if_icmpeq +6 -> 26
    //   23: goto -16 -> 7
    //   26: aload 4
    //   28: astore_0
    //   29: aload 4
    //   31: invokevirtual 638	java/io/InputStream:close	()V
    //   34: aload_1
    //   35: ifnull +219 -> 254
    //   38: aload 4
    //   40: astore_0
    //   41: aload_1
    //   42: invokevirtual 644	java/util/jar/JarEntry:getCertificates	()[Ljava/security/cert/Certificate;
    //   45: astore_1
    //   46: goto +3 -> 49
    //   49: aload_1
    //   50: ifnonnull +14 -> 64
    //   53: aload 4
    //   55: astore_0
    //   56: aload_3
    //   57: iconst_3
    //   58: sipush 5175
    //   61: invokestatic 269	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;II)V
    //   64: aload 4
    //   66: ifnull +15 -> 81
    //   69: aload 4
    //   71: invokevirtual 638	java/io/InputStream:close	()V
    //   74: aload_1
    //   75: areturn
    //   76: astore_0
    //   77: aload_0
    //   78: invokevirtual 645	java/io/IOException:printStackTrace	()V
    //   81: aload_1
    //   82: areturn
    //   83: astore_1
    //   84: goto +17 -> 101
    //   87: astore_1
    //   88: goto +82 -> 170
    //   91: astore_1
    //   92: aconst_null
    //   93: astore_0
    //   94: goto +142 -> 236
    //   97: astore_1
    //   98: aconst_null
    //   99: astore 4
    //   101: aload 4
    //   103: astore_0
    //   104: new 271	java/lang/StringBuilder
    //   107: dup
    //   108: invokespecial 272	java/lang/StringBuilder:<init>	()V
    //   111: astore_2
    //   112: aload 4
    //   114: astore_0
    //   115: aload_2
    //   116: sipush 5171
    //   119: invokevirtual 286	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   122: pop
    //   123: aload 4
    //   125: astore_0
    //   126: aload_2
    //   127: aload_1
    //   128: invokevirtual 648	java/lang/RuntimeException:getMessage	()Ljava/lang/String;
    //   131: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   134: pop
    //   135: aload 4
    //   137: astore_0
    //   138: aload_3
    //   139: iconst_3
    //   140: aload_2
    //   141: invokevirtual 290	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   144: invokestatic 413	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;ILjava/lang/String;)V
    //   147: aload 4
    //   149: ifnull +84 -> 233
    //   152: aload 4
    //   154: invokevirtual 638	java/io/InputStream:close	()V
    //   157: aconst_null
    //   158: areturn
    //   159: astore_0
    //   160: aload_0
    //   161: invokevirtual 645	java/io/IOException:printStackTrace	()V
    //   164: aconst_null
    //   165: areturn
    //   166: astore_1
    //   167: aconst_null
    //   168: astore 4
    //   170: aload 4
    //   172: astore_0
    //   173: new 271	java/lang/StringBuilder
    //   176: dup
    //   177: invokespecial 272	java/lang/StringBuilder:<init>	()V
    //   180: astore_2
    //   181: aload 4
    //   183: astore_0
    //   184: aload_2
    //   185: sipush 5172
    //   188: invokevirtual 286	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   191: pop
    //   192: aload 4
    //   194: astore_0
    //   195: aload_2
    //   196: aload_1
    //   197: invokevirtual 649	java/io/IOException:getMessage	()Ljava/lang/String;
    //   200: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   203: pop
    //   204: aload 4
    //   206: astore_0
    //   207: aload_3
    //   208: iconst_3
    //   209: aload_2
    //   210: invokevirtual 290	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   213: invokestatic 413	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;ILjava/lang/String;)V
    //   216: aload 4
    //   218: astore_0
    //   219: aload_1
    //   220: invokevirtual 645	java/io/IOException:printStackTrace	()V
    //   223: aload 4
    //   225: ifnull +8 -> 233
    //   228: aload 4
    //   230: invokevirtual 638	java/io/InputStream:close	()V
    //   233: aconst_null
    //   234: areturn
    //   235: astore_1
    //   236: aload_0
    //   237: ifnull +15 -> 252
    //   240: aload_0
    //   241: invokevirtual 638	java/io/InputStream:close	()V
    //   244: goto +8 -> 252
    //   247: astore_0
    //   248: aload_0
    //   249: invokevirtual 645	java/io/IOException:printStackTrace	()V
    //   252: aload_1
    //   253: athrow
    //   254: aconst_null
    //   255: astore_1
    //   256: goto -207 -> 49
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	259	0	paramJarFile	java.util.jar.JarFile
    //   0	259	1	paramJarEntry	java.util.jar.JarEntry
    //   0	259	2	paramArrayOfByte	byte[]
    //   0	259	3	paramString	String
    //   5	224	4	localInputStream	java.io.InputStream
    // Exception table:
    //   from	to	target	type
    //   69	74	76	java/io/IOException
    //   10	23	83	java/lang/RuntimeException
    //   29	34	83	java/lang/RuntimeException
    //   41	46	83	java/lang/RuntimeException
    //   56	64	83	java/lang/RuntimeException
    //   10	23	87	java/io/IOException
    //   29	34	87	java/io/IOException
    //   41	46	87	java/io/IOException
    //   56	64	87	java/io/IOException
    //   0	7	91	finally
    //   0	7	97	java/lang/RuntimeException
    //   152	157	159	java/io/IOException
    //   228	233	159	java/io/IOException
    //   0	7	166	java/io/IOException
    //   10	23	235	finally
    //   29	34	235	finally
    //   41	46	235	finally
    //   56	64	235	finally
    //   104	112	235	finally
    //   115	123	235	finally
    //   126	135	235	finally
    //   138	147	235	finally
    //   173	181	235	finally
    //   184	192	235	finally
    //   195	204	235	finally
    //   207	216	235	finally
    //   219	223	235	finally
    //   240	244	247	java/io/IOException
  }
  
  private void b()
  {
    if (msIPluginDirUi != null) {
      synchronized (pluginConfigInfoHashMap)
      {
        File localFile = msIPluginDirUi.getSpecialInstallDir(13);
        if (localFile != null) {
          pluginConfigInfoHashMap.put("com.tencent.qb.plugin.mipush", new PluginConfigInfo(13, "6800", localFile.getAbsolutePath(), "com.tencent.mtt.mipush.jar"));
        }
        a(1, msIPluginDirUi);
        return;
      }
    }
  }
  
  public static boolean checkPluginSign(String paramString)
  {
    try
    {
      paramString = SignatureUtil.collectCertificates(paramString);
      if ((paramString != null) && (paramString.length > 0))
      {
        paramString = paramString[0];
        return sSignature().equals(paramString);
      }
      return paramString == null;
    }
    catch (Throwable paramString)
    {
      paramString.printStackTrace();
    }
    return true;
  }
  
  public static boolean checkPluginSign(String paramString1, String paramString2, int paramInt)
  {
    try
    {
      paramString1 = collectCertificates(paramString1, paramString2);
      if ((paramString1 != null) && (paramString1.length > 0))
      {
        paramString1 = paramString1[0];
        if (sSignature().equals(paramString1)) {
          return true;
        }
        PluginStatBehavior.setLocalMd5(paramString2, 3, sSignature().toString());
        if (paramString1 != null) {
          PluginStatBehavior.setSvrMd5(paramString2, 3, paramString1.toString());
        }
        PluginStatBehavior.setFinCode(paramString2, 3, 517);
        return false;
      }
      return true;
    }
    catch (NoSuchMethodError paramString1)
    {
      paramString1.printStackTrace();
    }
    return true;
  }
  
  /* Error */
  public static Signature[] collectCertificates(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: sipush 8192
    //   3: newarray <illegal type>
    //   5: astore 8
    //   7: new 625	java/util/jar/JarFile
    //   10: dup
    //   11: aload_0
    //   12: invokespecial 703	java/util/jar/JarFile:<init>	(Ljava/lang/String;)V
    //   15: astore 5
    //   17: aload 5
    //   19: astore_0
    //   20: aload 5
    //   22: invokevirtual 707	java/util/jar/JarFile:entries	()Ljava/util/Enumeration;
    //   25: astore 9
    //   27: aconst_null
    //   28: astore 6
    //   30: aload 5
    //   32: astore_0
    //   33: aload 9
    //   35: invokeinterface 712 1 0
    //   40: istore 4
    //   42: iconst_0
    //   43: istore_2
    //   44: iload 4
    //   46: ifeq +203 -> 249
    //   49: aload 5
    //   51: astore_0
    //   52: aload 9
    //   54: invokeinterface 715 1 0
    //   59: checkcast 640	java/util/jar/JarEntry
    //   62: astore 7
    //   64: aload 5
    //   66: astore_0
    //   67: aload 7
    //   69: invokevirtual 718	java/util/jar/JarEntry:isDirectory	()Z
    //   72: ifeq +6 -> 78
    //   75: goto -45 -> 30
    //   78: aload 5
    //   80: astore_0
    //   81: aload 7
    //   83: invokevirtual 721	java/util/jar/JarEntry:getName	()Ljava/lang/String;
    //   86: ldc_w 723
    //   89: invokevirtual 726	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   92: ifeq +6 -> 98
    //   95: goto -65 -> 30
    //   98: aload 5
    //   100: astore_0
    //   101: aload 5
    //   103: aload 7
    //   105: aload 8
    //   107: aload_1
    //   108: invokestatic 728	com/tencent/common/plugin/QBPluginServiceImpl:a	(Ljava/util/jar/JarFile;Ljava/util/jar/JarEntry;[BLjava/lang/String;)[Ljava/security/cert/Certificate;
    //   111: astore 7
    //   113: aload 7
    //   115: ifnonnull +17 -> 132
    //   118: aload 5
    //   120: invokevirtual 729	java/util/jar/JarFile:close	()V
    //   123: aconst_null
    //   124: areturn
    //   125: astore_0
    //   126: aload_0
    //   127: invokevirtual 645	java/io/IOException:printStackTrace	()V
    //   130: aconst_null
    //   131: areturn
    //   132: aload 6
    //   134: ifnonnull +10 -> 144
    //   137: aload 7
    //   139: astore 6
    //   141: goto -111 -> 30
    //   144: iconst_0
    //   145: istore_2
    //   146: aload 5
    //   148: astore_0
    //   149: iload_2
    //   150: aload 6
    //   152: arraylength
    //   153: if_icmpge -123 -> 30
    //   156: iconst_0
    //   157: istore_3
    //   158: aload 5
    //   160: astore_0
    //   161: iload_3
    //   162: aload 7
    //   164: arraylength
    //   165: if_icmpge +375 -> 540
    //   168: aload 6
    //   170: iload_2
    //   171: aaload
    //   172: ifnull +361 -> 533
    //   175: aload 5
    //   177: astore_0
    //   178: aload 6
    //   180: iload_2
    //   181: aaload
    //   182: aload 7
    //   184: iload_3
    //   185: aaload
    //   186: invokevirtual 732	java/security/cert/Certificate:equals	(Ljava/lang/Object;)Z
    //   189: ifeq +344 -> 533
    //   192: iconst_1
    //   193: istore_3
    //   194: goto +3 -> 197
    //   197: iload_3
    //   198: ifeq +18 -> 216
    //   201: aload 5
    //   203: astore_0
    //   204: aload 6
    //   206: arraylength
    //   207: aload 7
    //   209: arraylength
    //   210: if_icmpeq +335 -> 545
    //   213: goto +3 -> 216
    //   216: aload 5
    //   218: astore_0
    //   219: aload 5
    //   221: invokevirtual 729	java/util/jar/JarFile:close	()V
    //   224: aload 5
    //   226: astore_0
    //   227: aload_1
    //   228: iconst_3
    //   229: sipush 5173
    //   232: invokestatic 269	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;II)V
    //   235: aload 5
    //   237: invokevirtual 729	java/util/jar/JarFile:close	()V
    //   240: aconst_null
    //   241: areturn
    //   242: astore_0
    //   243: aload_0
    //   244: invokevirtual 645	java/io/IOException:printStackTrace	()V
    //   247: aconst_null
    //   248: areturn
    //   249: aload 6
    //   251: ifnull +79 -> 330
    //   254: aload 5
    //   256: astore_0
    //   257: aload 6
    //   259: arraylength
    //   260: ifle +70 -> 330
    //   263: aload 5
    //   265: astore_0
    //   266: aload 6
    //   268: arraylength
    //   269: istore_3
    //   270: aload 5
    //   272: astore_0
    //   273: aload 6
    //   275: arraylength
    //   276: anewarray 683	android/content/pm/Signature
    //   279: astore 7
    //   281: iload_2
    //   282: iload_3
    //   283: if_icmpge +31 -> 314
    //   286: aload 5
    //   288: astore_0
    //   289: aload 7
    //   291: iload_2
    //   292: new 683	android/content/pm/Signature
    //   295: dup
    //   296: aload 6
    //   298: iload_2
    //   299: aaload
    //   300: invokevirtual 736	java/security/cert/Certificate:getEncoded	()[B
    //   303: invokespecial 739	android/content/pm/Signature:<init>	([B)V
    //   306: aastore
    //   307: iload_2
    //   308: iconst_1
    //   309: iadd
    //   310: istore_2
    //   311: goto -30 -> 281
    //   314: aload 5
    //   316: invokevirtual 729	java/util/jar/JarFile:close	()V
    //   319: aload 7
    //   321: areturn
    //   322: astore_0
    //   323: aload_0
    //   324: invokevirtual 645	java/io/IOException:printStackTrace	()V
    //   327: aload 7
    //   329: areturn
    //   330: aload 5
    //   332: astore_0
    //   333: aload_1
    //   334: iconst_3
    //   335: sipush 5174
    //   338: invokestatic 269	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;II)V
    //   341: aload 5
    //   343: invokevirtual 729	java/util/jar/JarFile:close	()V
    //   346: aconst_null
    //   347: areturn
    //   348: astore_0
    //   349: aload_0
    //   350: invokevirtual 645	java/io/IOException:printStackTrace	()V
    //   353: aconst_null
    //   354: areturn
    //   355: astore 6
    //   357: goto +14 -> 371
    //   360: astore_1
    //   361: aconst_null
    //   362: astore_0
    //   363: goto +152 -> 515
    //   366: astore 6
    //   368: aconst_null
    //   369: astore 5
    //   371: aload 5
    //   373: astore_0
    //   374: aload 6
    //   376: invokevirtual 740	java/lang/Exception:printStackTrace	()V
    //   379: aload 5
    //   381: astore_0
    //   382: aload 6
    //   384: invokevirtual 741	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   387: astore 6
    //   389: aload 5
    //   391: astore_0
    //   392: new 271	java/lang/StringBuilder
    //   395: dup
    //   396: invokespecial 272	java/lang/StringBuilder:<init>	()V
    //   399: astore 7
    //   401: aload 5
    //   403: astore_0
    //   404: aload 7
    //   406: sipush 5170
    //   409: invokevirtual 286	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   412: pop
    //   413: aload 5
    //   415: astore_0
    //   416: aload 7
    //   418: aload 6
    //   420: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   423: pop
    //   424: aload 5
    //   426: astore_0
    //   427: aload_1
    //   428: iconst_3
    //   429: aload 7
    //   431: invokevirtual 290	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   434: invokestatic 413	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;ILjava/lang/String;)V
    //   437: aload 5
    //   439: astore_0
    //   440: aload 6
    //   442: invokestatic 367	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   445: ifne +50 -> 495
    //   448: aload 5
    //   450: astore_0
    //   451: aload 6
    //   453: invokevirtual 744	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   456: ldc_w 746
    //   459: invokevirtual 749	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   462: ifeq +33 -> 495
    //   465: aload 5
    //   467: astore_0
    //   468: aload_1
    //   469: iconst_3
    //   470: sipush 517
    //   473: invokestatic 317	com/tencent/common/plugin/PluginStatBehavior:setFinCode	(Ljava/lang/String;II)V
    //   476: aload 5
    //   478: ifnull +15 -> 493
    //   481: aload 5
    //   483: invokevirtual 729	java/util/jar/JarFile:close	()V
    //   486: aconst_null
    //   487: areturn
    //   488: astore_0
    //   489: aload_0
    //   490: invokevirtual 645	java/io/IOException:printStackTrace	()V
    //   493: aconst_null
    //   494: areturn
    //   495: aload 5
    //   497: ifnull +15 -> 512
    //   500: aload 5
    //   502: invokevirtual 729	java/util/jar/JarFile:close	()V
    //   505: aconst_null
    //   506: areturn
    //   507: astore_0
    //   508: aload_0
    //   509: invokevirtual 645	java/io/IOException:printStackTrace	()V
    //   512: aconst_null
    //   513: areturn
    //   514: astore_1
    //   515: aload_0
    //   516: ifnull +15 -> 531
    //   519: aload_0
    //   520: invokevirtual 729	java/util/jar/JarFile:close	()V
    //   523: goto +8 -> 531
    //   526: astore_0
    //   527: aload_0
    //   528: invokevirtual 645	java/io/IOException:printStackTrace	()V
    //   531: aload_1
    //   532: athrow
    //   533: iload_3
    //   534: iconst_1
    //   535: iadd
    //   536: istore_3
    //   537: goto -379 -> 158
    //   540: iconst_0
    //   541: istore_3
    //   542: goto -345 -> 197
    //   545: iload_2
    //   546: iconst_1
    //   547: iadd
    //   548: istore_2
    //   549: goto -403 -> 146
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	552	0	paramString1	String
    //   0	552	1	paramString2	String
    //   43	506	2	i	int
    //   157	385	3	j	int
    //   40	5	4	bool	boolean
    //   15	486	5	localJarFile	java.util.jar.JarFile
    //   28	269	6	localObject1	Object
    //   355	1	6	localException1	Exception
    //   366	17	6	localException2	Exception
    //   387	65	6	str	String
    //   62	368	7	localObject2	Object
    //   5	101	8	arrayOfByte	byte[]
    //   25	28	9	localEnumeration	java.util.Enumeration
    // Exception table:
    //   from	to	target	type
    //   118	123	125	java/io/IOException
    //   235	240	242	java/io/IOException
    //   314	319	322	java/io/IOException
    //   341	346	348	java/io/IOException
    //   20	27	355	java/lang/Exception
    //   33	42	355	java/lang/Exception
    //   52	64	355	java/lang/Exception
    //   67	75	355	java/lang/Exception
    //   81	95	355	java/lang/Exception
    //   101	113	355	java/lang/Exception
    //   149	156	355	java/lang/Exception
    //   161	168	355	java/lang/Exception
    //   178	192	355	java/lang/Exception
    //   204	213	355	java/lang/Exception
    //   219	224	355	java/lang/Exception
    //   227	235	355	java/lang/Exception
    //   257	263	355	java/lang/Exception
    //   266	270	355	java/lang/Exception
    //   273	281	355	java/lang/Exception
    //   289	307	355	java/lang/Exception
    //   333	341	355	java/lang/Exception
    //   7	17	360	finally
    //   7	17	366	java/lang/Exception
    //   481	486	488	java/io/IOException
    //   500	505	507	java/io/IOException
    //   20	27	514	finally
    //   33	42	514	finally
    //   52	64	514	finally
    //   67	75	514	finally
    //   81	95	514	finally
    //   101	113	514	finally
    //   149	156	514	finally
    //   161	168	514	finally
    //   178	192	514	finally
    //   204	213	514	finally
    //   219	224	514	finally
    //   227	235	514	finally
    //   257	263	514	finally
    //   266	270	514	finally
    //   273	281	514	finally
    //   289	307	514	finally
    //   333	341	514	finally
    //   374	379	514	finally
    //   382	389	514	finally
    //   392	401	514	finally
    //   404	413	514	finally
    //   416	424	514	finally
    //   427	437	514	finally
    //   440	448	514	finally
    //   451	465	514	finally
    //   468	476	514	finally
    //   519	523	526	java/io/IOException
  }
  
  public static QBPluginServiceImpl getInstance()
  {
    try
    {
      if (jdField_a_of_type_ComTencentCommonPluginQBPluginServiceImpl == null) {
        jdField_a_of_type_ComTencentCommonPluginQBPluginServiceImpl = new QBPluginServiceImpl();
      }
      QBPluginServiceImpl localQBPluginServiceImpl = jdField_a_of_type_ComTencentCommonPluginQBPluginServiceImpl;
      return localQBPluginServiceImpl;
    }
    finally {}
  }
  
  public static File getPluginDownloadDir(Context paramContext, String paramString1, String paramString2)
  {
    if (TextUtils.isEmpty(paramString2)) {
      return null;
    }
    Object localObject;
    if (!TbsMode.TBSISQB())
    {
      localObject = FileUtils.getTesCorePrivateDir(paramContext);
      if (localObject == null) {
        return null;
      }
      localObject = FileUtilsF.createDir(((File)localObject).getAbsoluteFile(), "plugins");
      if (localObject != null) {
        return FileUtilsF.createDir(((File)localObject).getAbsoluteFile(), paramString2);
      }
    }
    if (msIPluginDirUi == null) {
      return null;
    }
    try
    {
      if ((!TextUtils.isEmpty(paramString1)) && (paramString1.toLowerCase().endsWith(".zip")))
      {
        paramString1 = new StringBuilder();
        paramString1.append("sd=");
        paramString1.append(msIPluginDirUi.hasSdcard());
        PluginStatBehavior.addLogPath(paramString2, 2, paramString1.toString());
        if (msIPluginDirUi.hasSdcard())
        {
          paramString1 = msIPluginDirUi.getQQBrowserDownloadDir();
          if (paramString1 != null)
          {
            localObject = msIPluginDirUi.getSdcardSpace(paramString1.getAbsolutePath());
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("sddirest=");
            localStringBuilder.append(((SdcardSizeInfo)localObject).rest);
            localStringBuilder.append(",");
            localStringBuilder.append(paramString1.getAbsolutePath());
            PluginStatBehavior.addLogPath(paramString2, 2, localStringBuilder.toString());
            if (((SdcardSizeInfo)localObject).rest >= 10485760L)
            {
              paramString1 = PluginFileUtils.createDir(paramString1.getAbsoluteFile(), "plugins", mFileMode);
              if (paramString1 != null) {
                return PluginFileUtils.createDir(paramString1.getAbsoluteFile(), paramString2, mFileMode);
              }
              PluginStatBehavior.addLogPath(paramString2, 2, "pluginDirnull");
            }
          }
        }
      }
      else
      {
        paramString1 = msIPluginDirUi.getQQBrowserDownloadDir();
        if (paramString1 != null)
        {
          paramString1 = PluginFileUtils.createDir(paramString1.getAbsoluteFile(), "plugins", mFileMode);
          if (paramString1 != null)
          {
            paramString1 = PluginFileUtils.createDir(paramString1.getAbsoluteFile(), paramString2, mFileMode);
            return paramString1;
          }
        }
        return null;
      }
    }
    catch (Throwable paramString1)
    {
      paramString1.printStackTrace();
      try
      {
        paramContext = PluginFileUtils.createDir(PluginFileUtils.getPluginDir(paramContext).getAbsoluteFile(), paramString2, mFileMode);
        return paramContext;
      }
      catch (Throwable paramContext)
      {
        paramContext.printStackTrace();
      }
    }
    return null;
  }
  
  public static File getPluginInstallDir(Context paramContext, String paramString, boolean paramBoolean)
  {
    File localFile1;
    Object localObject;
    if (!TbsMode.TBSISQB())
    {
      localFile1 = FileUtils.getTesDataShareDir(paramContext);
      if (localFile1 == null) {
        return null;
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("getPluginInstallDir  tesDataShare = ");
      ((StringBuilder)localObject).append(localFile1.getAbsolutePath());
      FLogger.i("QBPluginServiceImpl_TBS", ((StringBuilder)localObject).toString());
      localFile1 = PluginFileUtils.createDir(localFile1.getAbsoluteFile(), "plugins", mFileMode);
      if (localFile1 != null) {
        return PluginFileUtils.createDir(localFile1.getAbsoluteFile(), paramString, mFileMode);
      }
    }
    if (!paramBoolean) {
      try
      {
        paramContext = StorageDirs.getInternalFilesDir("plugins");
        if (paramContext == null)
        {
          PluginStatBehavior.addLogPath(paramString, 3, 5181);
          return null;
        }
        paramContext = FileUtilsF.createDir(paramContext, paramString);
        if (paramContext == null) {
          PluginStatBehavior.addLogPath(paramString, 3, 5183);
        }
        return paramContext;
      }
      catch (Throwable paramContext)
      {
        PluginStatBehavior.addLogPath(paramString, 3, 5184);
        paramContext.printStackTrace();
        return null;
      }
    }
    for (;;)
    {
      try
      {
        if (msIPluginDirUi.hasSdcard())
        {
          localFile1 = msIPluginDirUi.getQQBrowserDownloadDir();
          if (localFile1 != null)
          {
            localFile1 = FileUtilsF.createDir(localFile1, "plugins");
            if (localFile1 != null)
            {
              localFile1 = FileUtilsF.createDir(localFile1, paramString);
              localObject = localFile1;
              if (localFile1 == null)
              {
                File localFile2 = PluginFileUtils.getPluginDir(paramContext);
                localObject = localFile1;
                if (localFile2 != null)
                {
                  localObject = localFile1;
                  if (FileUtilsF.getDataFreeSpace(paramContext) >= 20971520L) {
                    localObject = FileUtilsF.createDir(localFile2, paramString);
                  }
                }
              }
              return (File)localObject;
            }
          }
        }
      }
      catch (Throwable paramContext)
      {
        paramContext.printStackTrace();
        return null;
      }
      localFile1 = null;
    }
  }
  
  public static IPluginRelateFunc getPluginRelateFunc(int paramInt)
  {
    return msRelateFunc;
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
  
  public static boolean isPluignSystemInit()
  {
    return jdField_a_of_type_JavaUtilConcurrentAtomicAtomicInteger.get() < 4;
  }
  
  public static int preInstallCheck(String paramString1, File paramFile, QBPluginItemInfo paramQBPluginItemInfo, int paramInt1, int paramInt2, String paramString2, boolean paramBoolean)
  {
    if ((paramFile != null) && (paramFile.exists()) && (paramFile.length() != 0L))
    {
      Object localObject = FileUtilsF.read(paramFile.getAbsolutePath(), 0L, 256);
      long l = StringUtils.parseLong(paramQBPluginItemInfo.mPackageSize, -1L);
      if ((l >= 0L) && (l != paramFile.length()))
      {
        PluginStatBehavior.setLocalMd5(paramString1, 3, new String(((ByteBuffer)localObject).array(), 0, ((ByteBuffer)localObject).position()));
        FileUtilsF.getInstance().releaseByteBuffer((ByteBuffer)localObject);
        paramString2 = new StringBuilder();
        paramString2.append("FS[");
        paramString2.append(paramFile.length());
        paramString2.append("]PS[");
        paramString2.append(paramQBPluginItemInfo.mPackageSize);
        paramString2.append("_");
        paramString2.append(paramBoolean);
        PluginStatBehavior.addLogPath(paramString1, 3, paramString2.toString());
        PluginStatBehavior.setFinCode(paramString1, 3, 568);
        return 6004;
      }
      paramQBPluginItemInfo = ByteUtils.byteToHexString(Md5Utils.getMD5(((ByteBuffer)localObject).array(), 0, ((ByteBuffer)localObject).position()));
      FileUtilsF.getInstance().releaseByteBuffer((ByteBuffer)localObject);
      PluginStatBehavior.setSvrMd5(paramString1, 3, paramString2);
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("[");
      ((StringBuilder)localObject).append(paramQBPluginItemInfo);
      ((StringBuilder)localObject).append("],[");
      ((StringBuilder)localObject).append(paramString2);
      ((StringBuilder)localObject).append("]");
      PluginStatBehavior.setLocalMd5(paramString1, 3, ((StringBuilder)localObject).toString());
      if ((!TextUtils.isEmpty(paramQBPluginItemInfo)) && (!TextUtils.isEmpty(paramString2)) && (paramQBPluginItemInfo.length() == paramString2.length()) && (!paramQBPluginItemInfo.equals(paramString2)))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("InstallPluginFailed filePath=");
        ((StringBuilder)localObject).append(paramFile.getAbsolutePath());
        ((StringBuilder)localObject).append(",pkgName=");
        ((StringBuilder)localObject).append(paramString1);
        ((StringBuilder)localObject).append("localFileMd5=");
        ((StringBuilder)localObject).append(paramQBPluginItemInfo);
        ((StringBuilder)localObject).append(",pluginListMd5=");
        ((StringBuilder)localObject).append(paramString2);
        ((StringBuilder)localObject).append(",本地文件计算md5和服务器md5不一样，不安装....");
        FLogger.i("QBPluginServiceImpl_TBS", ((StringBuilder)localObject).toString());
        paramFile = new StringBuilder();
        paramFile.append("516_");
        paramFile.append(paramBoolean);
        PluginStatBehavior.addLogPath(paramString1, 3, paramFile.toString());
        PluginStatBehavior.setFinCode(paramString1, 3, 516);
        return 6005;
      }
      paramBoolean = true;
      if (paramInt1 != 9) {
        paramBoolean = checkPluginSign(paramFile.getAbsolutePath(), paramString1, paramInt1);
      }
      if (!paramBoolean)
      {
        paramFile = new StringBuilder();
        paramFile.append("preInstallCheck ");
        paramFile.append(paramString1);
        paramFile.append(",checkPluginSign failed");
        FLogger.i("QBPluginServiceImpl_TBS", paramFile.toString());
        return 6006;
      }
      return 0;
    }
    if (paramFile != null)
    {
      PluginStatBehavior.setDownloadDir(paramString1, 3, paramFile.getParent());
      PluginStatBehavior.setDownloadFileName(paramString1, 3, paramFile.getName());
    }
    PluginStatBehavior.addLogPath(paramString1, 3, 512);
    PluginStatBehavior.setFinCode(paramString1, 3, 512);
    paramFile = new StringBuilder();
    paramFile.append("preInstallCheck ");
    paramFile.append(paramString1);
    paramFile.append(",localFileNotExist");
    FLogger.i("QBPluginServiceImpl_TBS", paramFile.toString());
    return 6003;
  }
  
  public static Signature sSignature()
  {
    if (jdField_a_of_type_AndroidContentPmSignature == null) {
      jdField_a_of_type_AndroidContentPmSignature = new Signature("30820257308201c0a00302010202044e9d598c300d06092a864886f70d01010505003070310b30090603550406130238363110300e060355040813075369436875616e3110300e060355040713074368656e6744753110300e060355040a130754656e63656e743110300e060355040b130754656e63656e743119301706035504031310506c7567696e20515142726f77736572301e170d3131313031383130343834345a170d3431313031303130343834345a3070310b30090603550406130238363110300e060355040813075369436875616e3110300e060355040713074368656e6744753110300e060355040a130754656e63656e743110300e060355040b130754656e63656e743119301706035504031310506c7567696e20515142726f7773657230819f300d06092a864886f70d010101050003818d00308189028181008c48633deb6c0f4876ec16f8955ee0b773f320d3aa3e7339f0469e40730d0251d1d26c85fd5e39ddc8ecca5a98c1493ce52c9fb1bda6b3e015ccf71a8ebb0817f60c4c523269ecd0afdef21c9faf862bc69bbb8df6e88376f9f87f0139e323b73dc1b59172de5a2789f1895a3cc65d784ad3dd0919231d90f8f19c778698d1210203010001300d06092a864886f70d010105050003818100210e39580e198469e63f6fc43557295c94ebb60f0a4c81b3c15749b69f85ad8d9a0ef006137c5a1f624cc535fa30d54052477a9246266973f09b3189818a77f7a1039de32b74777e2838c4c5f81b2198531f3cf8a0b4a4fecbaf9ee3536e1b15dee7f28e389e2aff656afa6f4bb408f1910c75180250be64b720a1179df64b20");
    }
    return jdField_a_of_type_AndroidContentPmSignature;
  }
  
  public static void setTbsPluginLoader(IPluginLoader paramIPluginLoader)
  {
    mIPluginLoader = paramIPluginLoader;
  }
  
  void a()
  {
    this.jdField_a_of_type_ComTencentCommonPluginPluginCallbackHandler.removeMessages(TYPE_RETRY_PULL_PLUGINLIST);
  }
  
  void a(WUPRequestBase paramWUPRequestBase)
  {
    int i = paramWUPRequestBase.getType();
    if (!PluginSetting.getInstance(callerAppContext()).pluginListSyncSameToSvr(i))
    {
      paramWUPRequestBase = this.jdField_a_of_type_ComTencentCommonPluginPluginCallbackHandler.obtainMessage(TYPE_RETRY_PULL_PLUGINLIST);
      paramWUPRequestBase.arg1 = i;
      this.jdField_a_of_type_ComTencentCommonPluginPluginCallbackHandler.sendMessageDelayed(paramWUPRequestBase, this.jdField_a_of_type_Long);
      this.jdField_a_of_type_Long *= 2L;
      if (this.jdField_a_of_type_Long > 3600000L) {
        this.jdField_a_of_type_Long = 3600000L;
      }
    }
  }
  
  public boolean addPluginInfoToLocalHashMap(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    synchronized (this.mPackageName2PluginInfoMap)
    {
      if (TextUtils.isEmpty(paramString1)) {
        return false;
      }
      QBPluginInfo localQBPluginInfo = new QBPluginInfo();
      localQBPluginInfo.mPackageName = paramString2;
      localQBPluginInfo.mLocalPath = paramString1;
      localQBPluginInfo.mVersionCode = paramInt1;
      this.mPackageName2PluginInfoMap.put(new QBPluginSystem.PluginPkgKey(paramString2, paramInt2), localQBPluginInfo);
      return true;
    }
  }
  
  public boolean addPluginJarFile2PluginInfoList(String arg1, int paramInt)
  {
    if (TextUtils.isEmpty(???)) {
      return false;
    }
    Object localObject1 = new JarFileInfo();
    if (!JarFileParser.parseAPKFile(callerAppContext(), ???, (JarFileInfo)localObject1)) {
      return false;
    }
    if (((JarFileInfo)localObject1).mPackageName == null) {
      return false;
    }
    if (!((JarFileInfo)localObject1).mBrokenJarFile) {
      ((JarFileInfo)localObject1).mCanonicalPath = ???;
    }
    if (getLocalPluginInfo(((JarFileInfo)localObject1).mPackageName, 2, paramInt) == null)
    {
      localObject1 = PluginUtils.convert(callerAppContext(), (JarFileInfo)localObject1);
      if (localObject1 != null) {
        synchronized (this.mPackageName2PluginInfoMap)
        {
          QBPluginSystem.PluginPkgKey localPluginPkgKey = new QBPluginSystem.PluginPkgKey(((QBPluginInfo)localObject1).mPackageName, 1);
          this.mPackageName2PluginInfoMap.put(localPluginPkgKey, localObject1);
          return true;
        }
      }
    }
    return false;
  }
  
  public Context callerAppContext()
  {
    Context localContext = mCallerAppContext;
    if (localContext != null) {
      return localContext;
    }
    return ContextHolder.getAppContext();
  }
  
  public boolean can24HourPullList(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt <= 2)) {
      try
      {
        long l = System.currentTimeMillis();
        if (m24HourPullListTime[paramInt] <= 0L) {
          m24HourPullListTime[paramInt] = PluginSetting.getInstance(callerAppContext()).pluginListLastTime(paramInt);
        }
        if (l - m24HourPullListTime[paramInt] >= 86400000L)
        {
          PluginSetting.getInstance(callerAppContext()).setPluginListLastTime(paramInt, l);
          m24HourPullListTime[paramInt] = l;
          FLogger.i("QBPluginServiceImpl_TBS", "24Hour need pullpluginList");
          return true;
        }
        return false;
      }
      finally {}
    }
    return false;
  }
  
  /* Error */
  public boolean canMultiProcessPlugin(Context paramContext, String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: invokestatic 758	com/tencent/common/utils/TbsMode:TBSISQB	()Z
    //   5: istore 6
    //   7: iload 6
    //   9: ifeq +7 -> 16
    //   12: aload_0
    //   13: monitorexit
    //   14: iconst_1
    //   15: ireturn
    //   16: invokestatic 1040	android/os/Process:myPid	()I
    //   19: istore 5
    //   21: aload_1
    //   22: invokevirtual 1045	android/content/Context:getPackageName	()Ljava/lang/String;
    //   25: astore 14
    //   27: new 271	java/lang/StringBuilder
    //   30: dup
    //   31: invokespecial 272	java/lang/StringBuilder:<init>	()V
    //   34: astore 7
    //   36: aload 7
    //   38: ldc_w 1047
    //   41: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   44: pop
    //   45: aload 7
    //   47: aload 14
    //   49: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   52: pop
    //   53: ldc 82
    //   55: aload 7
    //   57: invokevirtual 290	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   60: invokestatic 296	com/tencent/basesupport/FLogger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   63: aload 14
    //   65: invokestatic 367	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   68: istore 6
    //   70: iload 6
    //   72: ifeq +7 -> 79
    //   75: aload_0
    //   76: monitorexit
    //   77: iconst_1
    //   78: ireturn
    //   79: aload_1
    //   80: invokestatic 764	com/tencent/common/utils/FileUtils:getTesCorePrivateDir	(Landroid/content/Context;)Ljava/io/File;
    //   83: astore 8
    //   85: aload 8
    //   87: ifnull +1985 -> 2072
    //   90: aload 8
    //   92: invokevirtual 575	java/io/File:exists	()Z
    //   95: istore 6
    //   97: iload 6
    //   99: ifne +6 -> 105
    //   102: goto +1970 -> 2072
    //   105: aconst_null
    //   106: astore 11
    //   108: aconst_null
    //   109: astore 10
    //   111: aconst_null
    //   112: astore 7
    //   114: aconst_null
    //   115: astore 9
    //   117: new 271	java/lang/StringBuilder
    //   120: dup
    //   121: invokespecial 272	java/lang/StringBuilder:<init>	()V
    //   124: astore 12
    //   126: aload 12
    //   128: aload 8
    //   130: invokevirtual 660	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   133: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   136: pop
    //   137: aload 12
    //   139: ldc_w 528
    //   142: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   145: pop
    //   146: aload 12
    //   148: aload 14
    //   150: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   153: pop
    //   154: aload 12
    //   156: ldc_w 407
    //   159: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   162: pop
    //   163: aload 12
    //   165: aload_2
    //   166: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   169: pop
    //   170: aload 12
    //   172: ldc_w 1049
    //   175: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   178: pop
    //   179: new 1051	java/io/RandomAccessFile
    //   182: dup
    //   183: aload 12
    //   185: invokevirtual 290	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   188: ldc_w 1053
    //   191: invokespecial 1055	java/io/RandomAccessFile:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   194: astore 8
    //   196: aload 8
    //   198: invokevirtual 1059	java/io/RandomAccessFile:getChannel	()Ljava/nio/channels/FileChannel;
    //   201: astore 12
    //   203: aload 9
    //   205: astore 11
    //   207: aload 12
    //   209: astore 9
    //   211: aload 10
    //   213: astore 7
    //   215: aload 8
    //   217: astore 10
    //   219: aload 12
    //   221: invokevirtual 1065	java/nio/channels/FileChannel:lock	()Ljava/nio/channels/FileLock;
    //   224: astore 13
    //   226: aload 13
    //   228: astore 11
    //   230: aload 12
    //   232: astore 9
    //   234: aload 13
    //   236: astore 7
    //   238: aload 8
    //   240: astore 10
    //   242: aload 8
    //   244: invokevirtual 1066	java/io/RandomAccessFile:length	()J
    //   247: lconst_0
    //   248: lcmp
    //   249: ifeq +1840 -> 2089
    //   252: aload 13
    //   254: astore 11
    //   256: aload 12
    //   258: astore 9
    //   260: aload 13
    //   262: astore 7
    //   264: aload 8
    //   266: astore 10
    //   268: aload 8
    //   270: invokevirtual 1069	java/io/RandomAccessFile:readInt	()I
    //   273: istore_3
    //   274: goto +3 -> 277
    //   277: aload 13
    //   279: astore 11
    //   281: aload 12
    //   283: astore 9
    //   285: aload 13
    //   287: astore 7
    //   289: aload 8
    //   291: astore 10
    //   293: new 271	java/lang/StringBuilder
    //   296: dup
    //   297: invokespecial 272	java/lang/StringBuilder:<init>	()V
    //   300: astore 15
    //   302: aload 13
    //   304: astore 11
    //   306: aload 12
    //   308: astore 9
    //   310: aload 13
    //   312: astore 7
    //   314: aload 8
    //   316: astore 10
    //   318: aload 15
    //   320: ldc_w 1071
    //   323: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   326: pop
    //   327: aload 13
    //   329: astore 11
    //   331: aload 12
    //   333: astore 9
    //   335: aload 13
    //   337: astore 7
    //   339: aload 8
    //   341: astore 10
    //   343: aload 15
    //   345: aload 14
    //   347: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   350: pop
    //   351: aload 13
    //   353: astore 11
    //   355: aload 12
    //   357: astore 9
    //   359: aload 13
    //   361: astore 7
    //   363: aload 8
    //   365: astore 10
    //   367: aload 15
    //   369: ldc_w 1073
    //   372: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   375: pop
    //   376: aload 13
    //   378: astore 11
    //   380: aload 12
    //   382: astore 9
    //   384: aload 13
    //   386: astore 7
    //   388: aload 8
    //   390: astore 10
    //   392: aload 15
    //   394: aload_2
    //   395: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   398: pop
    //   399: aload 13
    //   401: astore 11
    //   403: aload 12
    //   405: astore 9
    //   407: aload 13
    //   409: astore 7
    //   411: aload 8
    //   413: astore 10
    //   415: aload 15
    //   417: ldc_w 1075
    //   420: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   423: pop
    //   424: aload 13
    //   426: astore 11
    //   428: aload 12
    //   430: astore 9
    //   432: aload 13
    //   434: astore 7
    //   436: aload 8
    //   438: astore 10
    //   440: aload 15
    //   442: iload_3
    //   443: invokevirtual 286	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   446: pop
    //   447: aload 13
    //   449: astore 11
    //   451: aload 12
    //   453: astore 9
    //   455: aload 13
    //   457: astore 7
    //   459: aload 8
    //   461: astore 10
    //   463: ldc 82
    //   465: aload 15
    //   467: invokevirtual 290	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   470: invokestatic 296	com/tencent/basesupport/FLogger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   473: iload_3
    //   474: iconst_m1
    //   475: if_icmpne +269 -> 744
    //   478: aload 13
    //   480: astore 11
    //   482: aload 12
    //   484: astore 9
    //   486: aload 13
    //   488: astore 7
    //   490: aload 8
    //   492: astore 10
    //   494: new 271	java/lang/StringBuilder
    //   497: dup
    //   498: invokespecial 272	java/lang/StringBuilder:<init>	()V
    //   501: astore_1
    //   502: aload 13
    //   504: astore 11
    //   506: aload 12
    //   508: astore 9
    //   510: aload 13
    //   512: astore 7
    //   514: aload 8
    //   516: astore 10
    //   518: aload_1
    //   519: ldc_w 1071
    //   522: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   525: pop
    //   526: aload 13
    //   528: astore 11
    //   530: aload 12
    //   532: astore 9
    //   534: aload 13
    //   536: astore 7
    //   538: aload 8
    //   540: astore 10
    //   542: aload_1
    //   543: aload 14
    //   545: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   548: pop
    //   549: aload 13
    //   551: astore 11
    //   553: aload 12
    //   555: astore 9
    //   557: aload 13
    //   559: astore 7
    //   561: aload 8
    //   563: astore 10
    //   565: aload_1
    //   566: ldc_w 1073
    //   569: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   572: pop
    //   573: aload 13
    //   575: astore 11
    //   577: aload 12
    //   579: astore 9
    //   581: aload 13
    //   583: astore 7
    //   585: aload 8
    //   587: astore 10
    //   589: aload_1
    //   590: aload_2
    //   591: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   594: pop
    //   595: aload 13
    //   597: astore 11
    //   599: aload 12
    //   601: astore 9
    //   603: aload 13
    //   605: astore 7
    //   607: aload 8
    //   609: astore 10
    //   611: aload_1
    //   612: ldc_w 1077
    //   615: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   618: pop
    //   619: aload 13
    //   621: astore 11
    //   623: aload 12
    //   625: astore 9
    //   627: aload 13
    //   629: astore 7
    //   631: aload 8
    //   633: astore 10
    //   635: aload_1
    //   636: iload 5
    //   638: invokevirtual 286	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   641: pop
    //   642: aload 13
    //   644: astore 11
    //   646: aload 12
    //   648: astore 9
    //   650: aload 13
    //   652: astore 7
    //   654: aload 8
    //   656: astore 10
    //   658: ldc 82
    //   660: aload_1
    //   661: invokevirtual 290	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   664: invokestatic 296	com/tencent/basesupport/FLogger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   667: aload 13
    //   669: astore 11
    //   671: aload 12
    //   673: astore 9
    //   675: aload 13
    //   677: astore 7
    //   679: aload 8
    //   681: astore 10
    //   683: aload 8
    //   685: lconst_0
    //   686: invokevirtual 1081	java/io/RandomAccessFile:seek	(J)V
    //   689: aload 13
    //   691: astore 11
    //   693: aload 12
    //   695: astore 9
    //   697: aload 13
    //   699: astore 7
    //   701: aload 8
    //   703: astore 10
    //   705: aload 8
    //   707: iload 5
    //   709: invokevirtual 1084	java/io/RandomAccessFile:writeInt	(I)V
    //   712: aload 13
    //   714: ifnull +16 -> 730
    //   717: aload 13
    //   719: invokevirtual 1089	java/nio/channels/FileLock:release	()V
    //   722: goto +8 -> 730
    //   725: astore_1
    //   726: aload_1
    //   727: invokevirtual 645	java/io/IOException:printStackTrace	()V
    //   730: aload 12
    //   732: invokestatic 1093	com/tencent/common/utils/FileUtilsF:closeQuietly	(Ljava/io/Closeable;)V
    //   735: aload 8
    //   737: invokestatic 1093	com/tencent/common/utils/FileUtilsF:closeQuietly	(Ljava/io/Closeable;)V
    //   740: aload_0
    //   741: monitorexit
    //   742: iconst_1
    //   743: ireturn
    //   744: aload 13
    //   746: astore 11
    //   748: aload 12
    //   750: astore 9
    //   752: aload 13
    //   754: astore 7
    //   756: aload 8
    //   758: astore 10
    //   760: new 271	java/lang/StringBuilder
    //   763: dup
    //   764: invokespecial 272	java/lang/StringBuilder:<init>	()V
    //   767: astore 15
    //   769: aload 13
    //   771: astore 11
    //   773: aload 12
    //   775: astore 9
    //   777: aload 13
    //   779: astore 7
    //   781: aload 8
    //   783: astore 10
    //   785: aload 15
    //   787: ldc_w 1071
    //   790: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   793: pop
    //   794: aload 13
    //   796: astore 11
    //   798: aload 12
    //   800: astore 9
    //   802: aload 13
    //   804: astore 7
    //   806: aload 8
    //   808: astore 10
    //   810: aload 15
    //   812: aload 14
    //   814: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   817: pop
    //   818: aload 13
    //   820: astore 11
    //   822: aload 12
    //   824: astore 9
    //   826: aload 13
    //   828: astore 7
    //   830: aload 8
    //   832: astore 10
    //   834: aload 15
    //   836: ldc_w 1073
    //   839: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   842: pop
    //   843: aload 13
    //   845: astore 11
    //   847: aload 12
    //   849: astore 9
    //   851: aload 13
    //   853: astore 7
    //   855: aload 8
    //   857: astore 10
    //   859: aload 15
    //   861: aload_2
    //   862: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   865: pop
    //   866: aload 13
    //   868: astore 11
    //   870: aload 12
    //   872: astore 9
    //   874: aload 13
    //   876: astore 7
    //   878: aload 8
    //   880: astore 10
    //   882: aload 15
    //   884: ldc_w 1095
    //   887: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   890: pop
    //   891: aload 13
    //   893: astore 11
    //   895: aload 12
    //   897: astore 9
    //   899: aload 13
    //   901: astore 7
    //   903: aload 8
    //   905: astore 10
    //   907: aload 15
    //   909: iload_3
    //   910: invokevirtual 286	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   913: pop
    //   914: aload 13
    //   916: astore 11
    //   918: aload 12
    //   920: astore 9
    //   922: aload 13
    //   924: astore 7
    //   926: aload 8
    //   928: astore 10
    //   930: aload 15
    //   932: ldc_w 1097
    //   935: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   938: pop
    //   939: aload 13
    //   941: astore 11
    //   943: aload 12
    //   945: astore 9
    //   947: aload 13
    //   949: astore 7
    //   951: aload 8
    //   953: astore 10
    //   955: ldc 82
    //   957: aload 15
    //   959: invokevirtual 290	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   962: invokestatic 296	com/tencent/basesupport/FLogger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   965: iload_3
    //   966: iload 5
    //   968: if_icmpne +35 -> 1003
    //   971: aload 13
    //   973: ifnull +16 -> 989
    //   976: aload 13
    //   978: invokevirtual 1089	java/nio/channels/FileLock:release	()V
    //   981: goto +8 -> 989
    //   984: astore_1
    //   985: aload_1
    //   986: invokevirtual 645	java/io/IOException:printStackTrace	()V
    //   989: aload 12
    //   991: invokestatic 1093	com/tencent/common/utils/FileUtilsF:closeQuietly	(Ljava/io/Closeable;)V
    //   994: aload 8
    //   996: invokestatic 1093	com/tencent/common/utils/FileUtilsF:closeQuietly	(Ljava/io/Closeable;)V
    //   999: aload_0
    //   1000: monitorexit
    //   1001: iconst_1
    //   1002: ireturn
    //   1003: aload 13
    //   1005: astore 11
    //   1007: aload 12
    //   1009: astore 9
    //   1011: aload 13
    //   1013: astore 7
    //   1015: aload 8
    //   1017: astore 10
    //   1019: aload_1
    //   1020: ldc_w 1099
    //   1023: invokevirtual 1103	android/content/Context:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
    //   1026: checkcast 1105	android/app/ActivityManager
    //   1029: invokevirtual 1109	android/app/ActivityManager:getRunningAppProcesses	()Ljava/util/List;
    //   1032: invokeinterface 1112 1 0
    //   1037: astore 15
    //   1039: aload 13
    //   1041: astore 11
    //   1043: aload 12
    //   1045: astore 9
    //   1047: aload 13
    //   1049: astore 7
    //   1051: aload 8
    //   1053: astore 10
    //   1055: aload 15
    //   1057: invokeinterface 354 1 0
    //   1062: ifeq +1032 -> 2094
    //   1065: aload 13
    //   1067: astore 11
    //   1069: aload 12
    //   1071: astore 9
    //   1073: aload 13
    //   1075: astore 7
    //   1077: aload 8
    //   1079: astore 10
    //   1081: aload 15
    //   1083: invokeinterface 358 1 0
    //   1088: checkcast 1114	android/app/ActivityManager$RunningAppProcessInfo
    //   1091: astore_1
    //   1092: aload 13
    //   1094: astore 11
    //   1096: aload 12
    //   1098: astore 9
    //   1100: aload 13
    //   1102: astore 7
    //   1104: aload 8
    //   1106: astore 10
    //   1108: aload_1
    //   1109: getfield 1117	android/app/ActivityManager$RunningAppProcessInfo:pid	I
    //   1112: iload_3
    //   1113: if_icmpne -74 -> 1039
    //   1116: aload 13
    //   1118: astore 11
    //   1120: aload 12
    //   1122: astore 9
    //   1124: aload 13
    //   1126: astore 7
    //   1128: aload 8
    //   1130: astore 10
    //   1132: new 271	java/lang/StringBuilder
    //   1135: dup
    //   1136: invokespecial 272	java/lang/StringBuilder:<init>	()V
    //   1139: astore 15
    //   1141: aload 13
    //   1143: astore 11
    //   1145: aload 12
    //   1147: astore 9
    //   1149: aload 13
    //   1151: astore 7
    //   1153: aload 8
    //   1155: astore 10
    //   1157: aload 15
    //   1159: ldc_w 1071
    //   1162: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1165: pop
    //   1166: aload 13
    //   1168: astore 11
    //   1170: aload 12
    //   1172: astore 9
    //   1174: aload 13
    //   1176: astore 7
    //   1178: aload 8
    //   1180: astore 10
    //   1182: aload 15
    //   1184: aload 14
    //   1186: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1189: pop
    //   1190: aload 13
    //   1192: astore 11
    //   1194: aload 12
    //   1196: astore 9
    //   1198: aload 13
    //   1200: astore 7
    //   1202: aload 8
    //   1204: astore 10
    //   1206: aload 15
    //   1208: ldc_w 1073
    //   1211: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1214: pop
    //   1215: aload 13
    //   1217: astore 11
    //   1219: aload 12
    //   1221: astore 9
    //   1223: aload 13
    //   1225: astore 7
    //   1227: aload 8
    //   1229: astore 10
    //   1231: aload 15
    //   1233: aload_2
    //   1234: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1237: pop
    //   1238: aload 13
    //   1240: astore 11
    //   1242: aload 12
    //   1244: astore 9
    //   1246: aload 13
    //   1248: astore 7
    //   1250: aload 8
    //   1252: astore 10
    //   1254: aload 15
    //   1256: ldc_w 1119
    //   1259: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1262: pop
    //   1263: aload 13
    //   1265: astore 11
    //   1267: aload 12
    //   1269: astore 9
    //   1271: aload 13
    //   1273: astore 7
    //   1275: aload 8
    //   1277: astore 10
    //   1279: aload 15
    //   1281: aload_1
    //   1282: getfield 1122	android/app/ActivityManager$RunningAppProcessInfo:processName	Ljava/lang/String;
    //   1285: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1288: pop
    //   1289: aload 13
    //   1291: astore 11
    //   1293: aload 12
    //   1295: astore 9
    //   1297: aload 13
    //   1299: astore 7
    //   1301: aload 8
    //   1303: astore 10
    //   1305: aload 15
    //   1307: ldc_w 1124
    //   1310: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1313: pop
    //   1314: aload 13
    //   1316: astore 11
    //   1318: aload 12
    //   1320: astore 9
    //   1322: aload 13
    //   1324: astore 7
    //   1326: aload 8
    //   1328: astore 10
    //   1330: ldc 82
    //   1332: aload 15
    //   1334: invokevirtual 290	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1337: invokestatic 296	com/tencent/basesupport/FLogger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   1340: iconst_1
    //   1341: istore 4
    //   1343: goto +3 -> 1346
    //   1346: iload 4
    //   1348: ifeq +201 -> 1549
    //   1351: aload 13
    //   1353: astore 11
    //   1355: aload 12
    //   1357: astore 9
    //   1359: aload 13
    //   1361: astore 7
    //   1363: aload 8
    //   1365: astore 10
    //   1367: new 271	java/lang/StringBuilder
    //   1370: dup
    //   1371: invokespecial 272	java/lang/StringBuilder:<init>	()V
    //   1374: astore_1
    //   1375: aload 13
    //   1377: astore 11
    //   1379: aload 12
    //   1381: astore 9
    //   1383: aload 13
    //   1385: astore 7
    //   1387: aload 8
    //   1389: astore 10
    //   1391: aload_1
    //   1392: ldc_w 1071
    //   1395: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1398: pop
    //   1399: aload 13
    //   1401: astore 11
    //   1403: aload 12
    //   1405: astore 9
    //   1407: aload 13
    //   1409: astore 7
    //   1411: aload 8
    //   1413: astore 10
    //   1415: aload_1
    //   1416: aload 14
    //   1418: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1421: pop
    //   1422: aload 13
    //   1424: astore 11
    //   1426: aload 12
    //   1428: astore 9
    //   1430: aload 13
    //   1432: astore 7
    //   1434: aload 8
    //   1436: astore 10
    //   1438: aload_1
    //   1439: ldc_w 1073
    //   1442: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1445: pop
    //   1446: aload 13
    //   1448: astore 11
    //   1450: aload 12
    //   1452: astore 9
    //   1454: aload 13
    //   1456: astore 7
    //   1458: aload 8
    //   1460: astore 10
    //   1462: aload_1
    //   1463: aload_2
    //   1464: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1467: pop
    //   1468: aload 13
    //   1470: astore 11
    //   1472: aload 12
    //   1474: astore 9
    //   1476: aload 13
    //   1478: astore 7
    //   1480: aload 8
    //   1482: astore 10
    //   1484: aload_1
    //   1485: ldc_w 1126
    //   1488: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1491: pop
    //   1492: aload 13
    //   1494: astore 11
    //   1496: aload 12
    //   1498: astore 9
    //   1500: aload 13
    //   1502: astore 7
    //   1504: aload 8
    //   1506: astore 10
    //   1508: ldc 82
    //   1510: aload_1
    //   1511: invokevirtual 290	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1514: invokestatic 296	com/tencent/basesupport/FLogger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   1517: aload 13
    //   1519: ifnull +16 -> 1535
    //   1522: aload 13
    //   1524: invokevirtual 1089	java/nio/channels/FileLock:release	()V
    //   1527: goto +8 -> 1535
    //   1530: astore_1
    //   1531: aload_1
    //   1532: invokevirtual 645	java/io/IOException:printStackTrace	()V
    //   1535: aload 12
    //   1537: invokestatic 1093	com/tencent/common/utils/FileUtilsF:closeQuietly	(Ljava/io/Closeable;)V
    //   1540: aload 8
    //   1542: invokestatic 1093	com/tencent/common/utils/FileUtilsF:closeQuietly	(Ljava/io/Closeable;)V
    //   1545: aload_0
    //   1546: monitorexit
    //   1547: iconst_0
    //   1548: ireturn
    //   1549: aload 13
    //   1551: astore 11
    //   1553: aload 12
    //   1555: astore 9
    //   1557: aload 13
    //   1559: astore 7
    //   1561: aload 8
    //   1563: astore 10
    //   1565: new 271	java/lang/StringBuilder
    //   1568: dup
    //   1569: invokespecial 272	java/lang/StringBuilder:<init>	()V
    //   1572: astore_1
    //   1573: aload 13
    //   1575: astore 11
    //   1577: aload 12
    //   1579: astore 9
    //   1581: aload 13
    //   1583: astore 7
    //   1585: aload 8
    //   1587: astore 10
    //   1589: aload_1
    //   1590: ldc_w 1128
    //   1593: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1596: pop
    //   1597: aload 13
    //   1599: astore 11
    //   1601: aload 12
    //   1603: astore 9
    //   1605: aload 13
    //   1607: astore 7
    //   1609: aload 8
    //   1611: astore 10
    //   1613: aload_1
    //   1614: iload_3
    //   1615: invokevirtual 286	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1618: pop
    //   1619: aload 13
    //   1621: astore 11
    //   1623: aload 12
    //   1625: astore 9
    //   1627: aload 13
    //   1629: astore 7
    //   1631: aload 8
    //   1633: astore 10
    //   1635: ldc 82
    //   1637: aload_1
    //   1638: invokevirtual 290	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1641: invokestatic 296	com/tencent/basesupport/FLogger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   1644: aload 13
    //   1646: astore 11
    //   1648: aload 12
    //   1650: astore 9
    //   1652: aload 13
    //   1654: astore 7
    //   1656: aload 8
    //   1658: astore 10
    //   1660: aload 8
    //   1662: lconst_0
    //   1663: invokevirtual 1081	java/io/RandomAccessFile:seek	(J)V
    //   1666: aload 13
    //   1668: astore 11
    //   1670: aload 12
    //   1672: astore 9
    //   1674: aload 13
    //   1676: astore 7
    //   1678: aload 8
    //   1680: astore 10
    //   1682: aload 8
    //   1684: iload 5
    //   1686: invokevirtual 1084	java/io/RandomAccessFile:writeInt	(I)V
    //   1689: aload 13
    //   1691: astore 11
    //   1693: aload 12
    //   1695: astore 9
    //   1697: aload 13
    //   1699: astore 7
    //   1701: aload 8
    //   1703: astore 10
    //   1705: new 271	java/lang/StringBuilder
    //   1708: dup
    //   1709: invokespecial 272	java/lang/StringBuilder:<init>	()V
    //   1712: astore_1
    //   1713: aload 13
    //   1715: astore 11
    //   1717: aload 12
    //   1719: astore 9
    //   1721: aload 13
    //   1723: astore 7
    //   1725: aload 8
    //   1727: astore 10
    //   1729: aload_1
    //   1730: ldc_w 1071
    //   1733: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1736: pop
    //   1737: aload 13
    //   1739: astore 11
    //   1741: aload 12
    //   1743: astore 9
    //   1745: aload 13
    //   1747: astore 7
    //   1749: aload 8
    //   1751: astore 10
    //   1753: aload_1
    //   1754: aload 14
    //   1756: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1759: pop
    //   1760: aload 13
    //   1762: astore 11
    //   1764: aload 12
    //   1766: astore 9
    //   1768: aload 13
    //   1770: astore 7
    //   1772: aload 8
    //   1774: astore 10
    //   1776: aload_1
    //   1777: ldc_w 1073
    //   1780: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1783: pop
    //   1784: aload 13
    //   1786: astore 11
    //   1788: aload 12
    //   1790: astore 9
    //   1792: aload 13
    //   1794: astore 7
    //   1796: aload 8
    //   1798: astore 10
    //   1800: aload_1
    //   1801: aload_2
    //   1802: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1805: pop
    //   1806: aload 13
    //   1808: astore 11
    //   1810: aload 12
    //   1812: astore 9
    //   1814: aload 13
    //   1816: astore 7
    //   1818: aload 8
    //   1820: astore 10
    //   1822: aload_1
    //   1823: ldc_w 1130
    //   1826: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1829: pop
    //   1830: aload 13
    //   1832: astore 11
    //   1834: aload 12
    //   1836: astore 9
    //   1838: aload 13
    //   1840: astore 7
    //   1842: aload 8
    //   1844: astore 10
    //   1846: aload_1
    //   1847: iload 5
    //   1849: invokevirtual 286	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1852: pop
    //   1853: aload 13
    //   1855: astore 11
    //   1857: aload 12
    //   1859: astore 9
    //   1861: aload 13
    //   1863: astore 7
    //   1865: aload 8
    //   1867: astore 10
    //   1869: aload_1
    //   1870: ldc_w 1132
    //   1873: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1876: pop
    //   1877: aload 13
    //   1879: astore 11
    //   1881: aload 12
    //   1883: astore 9
    //   1885: aload 13
    //   1887: astore 7
    //   1889: aload 8
    //   1891: astore 10
    //   1893: ldc 82
    //   1895: aload_1
    //   1896: invokevirtual 290	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1899: invokestatic 296	com/tencent/basesupport/FLogger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   1902: aload 13
    //   1904: ifnull +16 -> 1920
    //   1907: aload 13
    //   1909: invokevirtual 1089	java/nio/channels/FileLock:release	()V
    //   1912: goto +8 -> 1920
    //   1915: astore_1
    //   1916: aload_1
    //   1917: invokevirtual 645	java/io/IOException:printStackTrace	()V
    //   1920: aload 12
    //   1922: invokestatic 1093	com/tencent/common/utils/FileUtilsF:closeQuietly	(Ljava/io/Closeable;)V
    //   1925: aload 8
    //   1927: invokestatic 1093	com/tencent/common/utils/FileUtilsF:closeQuietly	(Ljava/io/Closeable;)V
    //   1930: aload_0
    //   1931: monitorexit
    //   1932: iconst_1
    //   1933: ireturn
    //   1934: astore_2
    //   1935: aload 12
    //   1937: astore_1
    //   1938: goto +33 -> 1971
    //   1941: astore_1
    //   1942: aconst_null
    //   1943: astore 9
    //   1945: goto +97 -> 2042
    //   1948: astore_2
    //   1949: aconst_null
    //   1950: astore_1
    //   1951: goto +20 -> 1971
    //   1954: astore_1
    //   1955: aconst_null
    //   1956: astore 9
    //   1958: aload 9
    //   1960: astore 8
    //   1962: goto +80 -> 2042
    //   1965: astore_2
    //   1966: aconst_null
    //   1967: astore_1
    //   1968: aload_1
    //   1969: astore 8
    //   1971: aload_1
    //   1972: astore 9
    //   1974: aload 11
    //   1976: astore 7
    //   1978: aload 8
    //   1980: astore 10
    //   1982: ldc 82
    //   1984: ldc_w 1134
    //   1987: aload_2
    //   1988: invokestatic 1138	com/tencent/basesupport/FLogger:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   1991: aload_1
    //   1992: astore 9
    //   1994: aload 11
    //   1996: astore 7
    //   1998: aload 8
    //   2000: astore 10
    //   2002: aload_2
    //   2003: invokevirtual 740	java/lang/Exception:printStackTrace	()V
    //   2006: aload 11
    //   2008: ifnull +16 -> 2024
    //   2011: aload 11
    //   2013: invokevirtual 1089	java/nio/channels/FileLock:release	()V
    //   2016: goto +8 -> 2024
    //   2019: astore_2
    //   2020: aload_2
    //   2021: invokevirtual 645	java/io/IOException:printStackTrace	()V
    //   2024: aload_1
    //   2025: invokestatic 1093	com/tencent/common/utils/FileUtilsF:closeQuietly	(Ljava/io/Closeable;)V
    //   2028: aload 8
    //   2030: invokestatic 1093	com/tencent/common/utils/FileUtilsF:closeQuietly	(Ljava/io/Closeable;)V
    //   2033: aload_0
    //   2034: monitorexit
    //   2035: iconst_1
    //   2036: ireturn
    //   2037: astore_1
    //   2038: aload 10
    //   2040: astore 8
    //   2042: aload 7
    //   2044: ifnull +16 -> 2060
    //   2047: aload 7
    //   2049: invokevirtual 1089	java/nio/channels/FileLock:release	()V
    //   2052: goto +8 -> 2060
    //   2055: astore_2
    //   2056: aload_2
    //   2057: invokevirtual 645	java/io/IOException:printStackTrace	()V
    //   2060: aload 9
    //   2062: invokestatic 1093	com/tencent/common/utils/FileUtilsF:closeQuietly	(Ljava/io/Closeable;)V
    //   2065: aload 8
    //   2067: invokestatic 1093	com/tencent/common/utils/FileUtilsF:closeQuietly	(Ljava/io/Closeable;)V
    //   2070: aload_1
    //   2071: athrow
    //   2072: ldc 82
    //   2074: ldc_w 1140
    //   2077: invokestatic 296	com/tencent/basesupport/FLogger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   2080: aload_0
    //   2081: monitorexit
    //   2082: iconst_1
    //   2083: ireturn
    //   2084: astore_1
    //   2085: aload_0
    //   2086: monitorexit
    //   2087: aload_1
    //   2088: athrow
    //   2089: iconst_m1
    //   2090: istore_3
    //   2091: goto -1814 -> 277
    //   2094: iconst_0
    //   2095: istore 4
    //   2097: goto -751 -> 1346
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	2100	0	this	QBPluginServiceImpl
    //   0	2100	1	paramContext	Context
    //   0	2100	2	paramString	String
    //   273	1818	3	i	int
    //   1341	755	4	j	int
    //   19	1829	5	k	int
    //   5	93	6	bool	boolean
    //   34	2014	7	localObject1	Object
    //   83	1983	8	localObject2	Object
    //   115	1946	9	localObject3	Object
    //   109	1930	10	localObject4	Object
    //   106	1906	11	localObject5	Object
    //   124	1812	12	localObject6	Object
    //   224	1684	13	localFileLock	java.nio.channels.FileLock
    //   25	1730	14	str	String
    //   300	1033	15	localObject7	Object
    // Exception table:
    //   from	to	target	type
    //   717	722	725	java/io/IOException
    //   976	981	984	java/io/IOException
    //   1522	1527	1530	java/io/IOException
    //   1907	1912	1915	java/io/IOException
    //   219	226	1934	java/lang/Exception
    //   242	252	1934	java/lang/Exception
    //   268	274	1934	java/lang/Exception
    //   293	302	1934	java/lang/Exception
    //   318	327	1934	java/lang/Exception
    //   343	351	1934	java/lang/Exception
    //   367	376	1934	java/lang/Exception
    //   392	399	1934	java/lang/Exception
    //   415	424	1934	java/lang/Exception
    //   440	447	1934	java/lang/Exception
    //   463	473	1934	java/lang/Exception
    //   494	502	1934	java/lang/Exception
    //   518	526	1934	java/lang/Exception
    //   542	549	1934	java/lang/Exception
    //   565	573	1934	java/lang/Exception
    //   589	595	1934	java/lang/Exception
    //   611	619	1934	java/lang/Exception
    //   635	642	1934	java/lang/Exception
    //   658	667	1934	java/lang/Exception
    //   683	689	1934	java/lang/Exception
    //   705	712	1934	java/lang/Exception
    //   760	769	1934	java/lang/Exception
    //   785	794	1934	java/lang/Exception
    //   810	818	1934	java/lang/Exception
    //   834	843	1934	java/lang/Exception
    //   859	866	1934	java/lang/Exception
    //   882	891	1934	java/lang/Exception
    //   907	914	1934	java/lang/Exception
    //   930	939	1934	java/lang/Exception
    //   955	965	1934	java/lang/Exception
    //   1019	1039	1934	java/lang/Exception
    //   1055	1065	1934	java/lang/Exception
    //   1081	1092	1934	java/lang/Exception
    //   1108	1116	1934	java/lang/Exception
    //   1132	1141	1934	java/lang/Exception
    //   1157	1166	1934	java/lang/Exception
    //   1182	1190	1934	java/lang/Exception
    //   1206	1215	1934	java/lang/Exception
    //   1231	1238	1934	java/lang/Exception
    //   1254	1263	1934	java/lang/Exception
    //   1279	1289	1934	java/lang/Exception
    //   1305	1314	1934	java/lang/Exception
    //   1330	1340	1934	java/lang/Exception
    //   1367	1375	1934	java/lang/Exception
    //   1391	1399	1934	java/lang/Exception
    //   1415	1422	1934	java/lang/Exception
    //   1438	1446	1934	java/lang/Exception
    //   1462	1468	1934	java/lang/Exception
    //   1484	1492	1934	java/lang/Exception
    //   1508	1517	1934	java/lang/Exception
    //   1565	1573	1934	java/lang/Exception
    //   1589	1597	1934	java/lang/Exception
    //   1613	1619	1934	java/lang/Exception
    //   1635	1644	1934	java/lang/Exception
    //   1660	1666	1934	java/lang/Exception
    //   1682	1689	1934	java/lang/Exception
    //   1705	1713	1934	java/lang/Exception
    //   1729	1737	1934	java/lang/Exception
    //   1753	1760	1934	java/lang/Exception
    //   1776	1784	1934	java/lang/Exception
    //   1800	1806	1934	java/lang/Exception
    //   1822	1830	1934	java/lang/Exception
    //   1846	1853	1934	java/lang/Exception
    //   1869	1877	1934	java/lang/Exception
    //   1893	1902	1934	java/lang/Exception
    //   196	203	1941	finally
    //   196	203	1948	java/lang/Exception
    //   117	196	1954	finally
    //   117	196	1965	java/lang/Exception
    //   2011	2016	2019	java/io/IOException
    //   219	226	2037	finally
    //   242	252	2037	finally
    //   268	274	2037	finally
    //   293	302	2037	finally
    //   318	327	2037	finally
    //   343	351	2037	finally
    //   367	376	2037	finally
    //   392	399	2037	finally
    //   415	424	2037	finally
    //   440	447	2037	finally
    //   463	473	2037	finally
    //   494	502	2037	finally
    //   518	526	2037	finally
    //   542	549	2037	finally
    //   565	573	2037	finally
    //   589	595	2037	finally
    //   611	619	2037	finally
    //   635	642	2037	finally
    //   658	667	2037	finally
    //   683	689	2037	finally
    //   705	712	2037	finally
    //   760	769	2037	finally
    //   785	794	2037	finally
    //   810	818	2037	finally
    //   834	843	2037	finally
    //   859	866	2037	finally
    //   882	891	2037	finally
    //   907	914	2037	finally
    //   930	939	2037	finally
    //   955	965	2037	finally
    //   1019	1039	2037	finally
    //   1055	1065	2037	finally
    //   1081	1092	2037	finally
    //   1108	1116	2037	finally
    //   1132	1141	2037	finally
    //   1157	1166	2037	finally
    //   1182	1190	2037	finally
    //   1206	1215	2037	finally
    //   1231	1238	2037	finally
    //   1254	1263	2037	finally
    //   1279	1289	2037	finally
    //   1305	1314	2037	finally
    //   1330	1340	2037	finally
    //   1367	1375	2037	finally
    //   1391	1399	2037	finally
    //   1415	1422	2037	finally
    //   1438	1446	2037	finally
    //   1462	1468	2037	finally
    //   1484	1492	2037	finally
    //   1508	1517	2037	finally
    //   1565	1573	2037	finally
    //   1589	1597	2037	finally
    //   1613	1619	2037	finally
    //   1635	1644	2037	finally
    //   1660	1666	2037	finally
    //   1682	1689	2037	finally
    //   1705	1713	2037	finally
    //   1729	1737	2037	finally
    //   1753	1760	2037	finally
    //   1776	1784	2037	finally
    //   1800	1806	2037	finally
    //   1822	1830	2037	finally
    //   1846	1853	2037	finally
    //   1869	1877	2037	finally
    //   1893	1902	2037	finally
    //   1982	1991	2037	finally
    //   2002	2006	2037	finally
    //   2047	2052	2055	java/io/IOException
    //   2	7	2084	finally
    //   16	70	2084	finally
    //   79	85	2084	finally
    //   90	97	2084	finally
    //   717	722	2084	finally
    //   726	730	2084	finally
    //   730	740	2084	finally
    //   976	981	2084	finally
    //   985	989	2084	finally
    //   989	999	2084	finally
    //   1522	1527	2084	finally
    //   1531	1535	2084	finally
    //   1535	1545	2084	finally
    //   1907	1912	2084	finally
    //   1916	1920	2084	finally
    //   1920	1930	2084	finally
    //   2011	2016	2084	finally
    //   2020	2024	2084	finally
    //   2024	2033	2084	finally
    //   2047	2052	2084	finally
    //   2056	2060	2084	finally
    //   2060	2072	2084	finally
    //   2072	2080	2084	finally
  }
  
  public int checkLocalPlugin(String paramString, int paramInt1, int paramInt2)
    throws RemoteException
  {
    int j = 0;
    int k = 0;
    int i = j;
    for (;;)
    {
      try
      {
        Object localObject = getLocalPluginInfo(paramString, paramInt1, paramInt2);
        paramInt1 = k;
        if (localObject != null)
        {
          i = j;
          if (!((QBPluginInfo)localObject).mNeedUnzipFromBack) {
            break label159;
          }
          paramInt1 = 2;
        }
        i = paramInt1;
        localObject = new StringBuilder();
        i = paramInt1;
        ((StringBuilder)localObject).append("CheckLocalPlugin,this:");
        i = paramInt1;
        ((StringBuilder)localObject).append(this);
        i = paramInt1;
        ((StringBuilder)localObject).append(",pluginName:");
        i = paramInt1;
        ((StringBuilder)localObject).append(paramString);
        i = paramInt1;
        ((StringBuilder)localObject).append(",ret=");
        i = paramInt1;
        ((StringBuilder)localObject).append(paramInt1);
        i = paramInt1;
        ((StringBuilder)localObject).append(",(0 none, 1 found, 2 needunzip)");
        i = paramInt1;
        FLogger.i("QBPluginServiceImpl_TBS", ((StringBuilder)localObject).toString());
        return paramInt1;
      }
      catch (NullPointerException paramString)
      {
        FLogger.e("QBPluginServiceImpl_TBS", paramString);
        return i;
      }
      label159:
      paramInt1 = 1;
    }
  }
  
  public boolean checkNeedUpdate(String paramString, int paramInt1, int paramInt2, QBPluginItemInfo paramQBPluginItemInfo)
    throws RemoteException
  {
    if (paramQBPluginItemInfo == null) {
      paramQBPluginItemInfo = getPluginInfo(paramString, paramInt2);
    }
    if (paramQBPluginItemInfo == null) {
      return false;
    }
    if ((getPluginConfigInfo(paramString) != null) && (!getPluginConfigInfo(paramString).compatableId.equals(paramQBPluginItemInfo.mPluginCompatiID))) {
      return false;
    }
    paramString = getLocalPluginInfo(paramString, paramInt1, paramInt2);
    if (paramString == null)
    {
      if (paramInt1 == 1) {
        return paramQBPluginItemInfo.mUpdateType > 0;
      }
      return true;
    }
    paramQBPluginItemInfo = paramQBPluginItemInfo.mVersion;
    if (TextUtils.isEmpty(paramQBPluginItemInfo)) {
      return false;
    }
    paramInt1 = paramString.mVersionCode;
    paramInt2 = StringUtils.parseInt(paramQBPluginItemInfo, -1);
    if (paramInt2 == -1) {
      return false;
    }
    return paramInt2 != paramInt1;
  }
  
  public boolean cleanPluginData()
  {
    boolean bool = TbsMode.TBSISQB();
    int i = 0;
    Object localObject1;
    if (bool)
    {
      PluginSetting.getInstance(callerAppContext()).setPluginListSyncSameToSvr(1, false);
      PluginSetting.getInstance(callerAppContext()).setPluginListRspMD5(1, "");
      localObject1 = QBPluginDBHelper.getInstance(dbHelperContext()).getPluginItemInfo("com.tencent.qb.plugin.docx", 1);
    }
    else
    {
      PluginSetting.getInstance(callerAppContext()).setPluginListSyncSameToSvr(2, false);
      PluginSetting.getInstance(callerAppContext()).setPluginListRspMD5(2, "");
      localObject1 = QBPluginDBHelper.getInstance(dbHelperContext()).getPluginItemInfo("com.tencent.qb.plugin.docx", 2);
    }
    if ((localObject1 != null) && ((((QBPluginItemInfo)localObject1).mDownloadDir != null) || (((QBPluginItemInfo)localObject1).mInstallDir != null)))
    {
      if (((QBPluginItemInfo)localObject1).mDownloadDir == null) {
        localObject2 = "";
      } else {
        localObject2 = ((QBPluginItemInfo)localObject1).mDownloadDir;
      }
      Object localObject3 = new File((String)localObject2);
      if (((QBPluginItemInfo)localObject1).mInstallDir == null) {
        localObject1 = "";
      } else {
        localObject1 = ((QBPluginItemInfo)localObject1).mInstallDir;
      }
      Object localObject2 = new File((String)localObject1);
      String[] arrayOfString = new String[6];
      arrayOfString[0] = "com.tencent.qb.plugin.pdf";
      arrayOfString[1] = "com.tencent.qb.plugin.pptx";
      arrayOfString[2] = "com.tencent.qb.plugin.xlsx";
      arrayOfString[3] = "com.tencent.qb.plugin.videodecode";
      arrayOfString[4] = "com.tencent.qb.plugin.epub";
      arrayOfString[5] = "com.tencent.qb.plugin.txt";
      localObject1 = "";
      try
      {
        if (((File)localObject3).exists())
        {
          localObject1 = ((File)localObject3).getParent();
          FileUtilsF.cleanDirectory((File)localObject3);
        }
        else if (!TbsMode.TBSISQB())
        {
          localObject3 = FileUtils.getTesCorePrivateDir(mCallerAppContext);
          if (localObject3 != null)
          {
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append(((File)localObject3).getAbsoluteFile());
            ((StringBuilder)localObject1).append("/");
            ((StringBuilder)localObject1).append("plugins");
            localObject1 = ((StringBuilder)localObject1).toString();
          }
        }
        else if (msIPluginDirUi != null)
        {
          localObject1 = msIPluginDirUi.getQQBrowserDownloadDir();
          localObject3 = new StringBuilder();
          ((StringBuilder)localObject3).append(((File)localObject1).getAbsoluteFile());
          ((StringBuilder)localObject3).append("/");
          ((StringBuilder)localObject3).append("plugins");
          localObject1 = ((StringBuilder)localObject3).toString();
        }
        bool = ((File)localObject2).exists();
        if (bool) {
          try
          {
            FileUtilsF.cleanDirectory((File)localObject2);
          }
          catch (Exception localException2)
          {
            localException2.printStackTrace();
          }
        }
        int j = arrayOfString.length;
        while (i < j)
        {
          Object localObject4 = arrayOfString[i];
          Object localObject5 = new StringBuilder();
          ((StringBuilder)localObject5).append((String)localObject1);
          ((StringBuilder)localObject5).append("/");
          ((StringBuilder)localObject5).append((String)localObject4);
          localObject5 = new File(((StringBuilder)localObject5).toString());
          if (((File)localObject5).exists()) {
            FileUtilsF.cleanDirectory((File)localObject5);
          }
          localObject5 = new StringBuilder();
          ((StringBuilder)localObject5).append(((File)localObject2).getParent());
          ((StringBuilder)localObject5).append("/");
          ((StringBuilder)localObject5).append((String)localObject4);
          localObject4 = new File(((StringBuilder)localObject5).toString());
          bool = ((File)localObject4).exists();
          if (bool) {
            try
            {
              FileUtilsF.cleanDirectory((File)localObject4);
            }
            catch (Exception localException3)
            {
              localException3.printStackTrace();
            }
          }
          i += 1;
        }
        return QBPluginDBHelper.getInstance(dbHelperContext()).cleanPluginData();
      }
      catch (Exception localException1)
      {
        localException1.printStackTrace();
      }
    }
  }
  
  public Context dbHelperContext()
  {
    Context localContext = mCallerAppContext;
    if (localContext != null) {
      return localContext;
    }
    return ContextHolder.getAppContext();
  }
  
  /* Error */
  public void deletePidFile(Context paramContext, String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: invokestatic 758	com/tencent/common/utils/TbsMode:TBSISQB	()Z
    //   5: istore_3
    //   6: iload_3
    //   7: ifeq +6 -> 13
    //   10: aload_0
    //   11: monitorexit
    //   12: return
    //   13: aload_1
    //   14: invokevirtual 1045	android/content/Context:getPackageName	()Ljava/lang/String;
    //   17: astore 4
    //   19: aload 4
    //   21: invokestatic 367	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   24: istore_3
    //   25: iload_3
    //   26: ifeq +6 -> 32
    //   29: aload_0
    //   30: monitorexit
    //   31: return
    //   32: aconst_null
    //   33: astore 8
    //   35: aconst_null
    //   36: astore 6
    //   38: aconst_null
    //   39: astore 7
    //   41: aconst_null
    //   42: astore 9
    //   44: new 271	java/lang/StringBuilder
    //   47: dup
    //   48: invokespecial 272	java/lang/StringBuilder:<init>	()V
    //   51: astore 5
    //   53: aload 5
    //   55: aload_1
    //   56: invokestatic 764	com/tencent/common/utils/FileUtils:getTesCorePrivateDir	(Landroid/content/Context;)Ljava/io/File;
    //   59: invokevirtual 660	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   62: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   65: pop
    //   66: aload 5
    //   68: ldc_w 528
    //   71: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   74: pop
    //   75: aload 5
    //   77: aload 4
    //   79: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   82: pop
    //   83: aload 5
    //   85: ldc_w 407
    //   88: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   91: pop
    //   92: aload 5
    //   94: aload_2
    //   95: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   98: pop
    //   99: aload 5
    //   101: ldc_w 1049
    //   104: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   107: pop
    //   108: aload 5
    //   110: invokevirtual 290	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   113: astore_1
    //   114: new 271	java/lang/StringBuilder
    //   117: dup
    //   118: invokespecial 272	java/lang/StringBuilder:<init>	()V
    //   121: astore_2
    //   122: aload_2
    //   123: ldc_w 1212
    //   126: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   129: pop
    //   130: aload_2
    //   131: aload_1
    //   132: invokevirtual 278	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   135: pop
    //   136: ldc 82
    //   138: aload_2
    //   139: invokevirtual 290	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   142: invokestatic 296	com/tencent/basesupport/FLogger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   145: new 1051	java/io/RandomAccessFile
    //   148: dup
    //   149: aload_1
    //   150: ldc_w 1053
    //   153: invokespecial 1055	java/io/RandomAccessFile:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   156: astore 5
    //   158: aload 5
    //   160: invokevirtual 1059	java/io/RandomAccessFile:getChannel	()Ljava/nio/channels/FileChannel;
    //   163: astore 4
    //   165: aload 9
    //   167: astore_1
    //   168: aload 8
    //   170: astore_2
    //   171: aload 4
    //   173: invokevirtual 1065	java/nio/channels/FileChannel:lock	()Ljava/nio/channels/FileLock;
    //   176: astore 6
    //   178: aload 6
    //   180: astore_1
    //   181: aload 6
    //   183: astore_2
    //   184: aload 5
    //   186: lconst_0
    //   187: invokevirtual 1081	java/io/RandomAccessFile:seek	(J)V
    //   190: aload 6
    //   192: astore_1
    //   193: aload 6
    //   195: astore_2
    //   196: aload 5
    //   198: iconst_m1
    //   199: invokevirtual 1084	java/io/RandomAccessFile:writeInt	(I)V
    //   202: aload 6
    //   204: astore_1
    //   205: aload 6
    //   207: astore_2
    //   208: aload 5
    //   210: invokevirtual 1213	java/io/RandomAccessFile:close	()V
    //   213: aload 4
    //   215: invokestatic 1093	com/tencent/common/utils/FileUtilsF:closeQuietly	(Ljava/io/Closeable;)V
    //   218: aload 5
    //   220: invokestatic 1093	com/tencent/common/utils/FileUtilsF:closeQuietly	(Ljava/io/Closeable;)V
    //   223: aload 6
    //   225: ifnull +111 -> 336
    //   228: aload 6
    //   230: invokevirtual 1089	java/nio/channels/FileLock:release	()V
    //   233: goto +103 -> 336
    //   236: astore_1
    //   237: aload_1
    //   238: invokevirtual 645	java/io/IOException:printStackTrace	()V
    //   241: goto +95 -> 336
    //   244: astore 6
    //   246: aload_1
    //   247: astore_2
    //   248: aload 6
    //   250: astore_1
    //   251: goto +95 -> 346
    //   254: astore 6
    //   256: aload_2
    //   257: astore_1
    //   258: aload 6
    //   260: astore_2
    //   261: goto +46 -> 307
    //   264: astore_1
    //   265: aconst_null
    //   266: astore_2
    //   267: aload 7
    //   269: astore 4
    //   271: goto +75 -> 346
    //   274: astore_2
    //   275: aconst_null
    //   276: astore_1
    //   277: aload 6
    //   279: astore 4
    //   281: goto +26 -> 307
    //   284: astore_1
    //   285: aconst_null
    //   286: astore_2
    //   287: aload_2
    //   288: astore 5
    //   290: aload 7
    //   292: astore 4
    //   294: goto +52 -> 346
    //   297: astore_2
    //   298: aconst_null
    //   299: astore_1
    //   300: aload_1
    //   301: astore 5
    //   303: aload 6
    //   305: astore 4
    //   307: aload_2
    //   308: invokevirtual 740	java/lang/Exception:printStackTrace	()V
    //   311: aload 4
    //   313: invokestatic 1093	com/tencent/common/utils/FileUtilsF:closeQuietly	(Ljava/io/Closeable;)V
    //   316: aload 5
    //   318: invokestatic 1093	com/tencent/common/utils/FileUtilsF:closeQuietly	(Ljava/io/Closeable;)V
    //   321: aload_1
    //   322: ifnull +14 -> 336
    //   325: aload_1
    //   326: invokevirtual 1089	java/nio/channels/FileLock:release	()V
    //   329: goto +7 -> 336
    //   332: astore_1
    //   333: goto -96 -> 237
    //   336: aload_0
    //   337: monitorexit
    //   338: return
    //   339: astore 6
    //   341: aload_1
    //   342: astore_2
    //   343: aload 6
    //   345: astore_1
    //   346: aload 4
    //   348: invokestatic 1093	com/tencent/common/utils/FileUtilsF:closeQuietly	(Ljava/io/Closeable;)V
    //   351: aload 5
    //   353: invokestatic 1093	com/tencent/common/utils/FileUtilsF:closeQuietly	(Ljava/io/Closeable;)V
    //   356: aload_2
    //   357: ifnull +15 -> 372
    //   360: aload_2
    //   361: invokevirtual 1089	java/nio/channels/FileLock:release	()V
    //   364: goto +8 -> 372
    //   367: astore_2
    //   368: aload_2
    //   369: invokevirtual 645	java/io/IOException:printStackTrace	()V
    //   372: aload_1
    //   373: athrow
    //   374: astore_1
    //   375: aload_0
    //   376: monitorexit
    //   377: aload_1
    //   378: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	379	0	this	QBPluginServiceImpl
    //   0	379	1	paramContext	Context
    //   0	379	2	paramString	String
    //   5	21	3	bool	boolean
    //   17	330	4	localObject1	Object
    //   51	301	5	localObject2	Object
    //   36	193	6	localFileLock	java.nio.channels.FileLock
    //   244	5	6	localObject3	Object
    //   254	50	6	localException	Exception
    //   339	5	6	localObject4	Object
    //   39	252	7	localObject5	Object
    //   33	136	8	localObject6	Object
    //   42	124	9	localObject7	Object
    // Exception table:
    //   from	to	target	type
    //   228	233	236	java/io/IOException
    //   171	178	244	finally
    //   184	190	244	finally
    //   196	202	244	finally
    //   208	213	244	finally
    //   171	178	254	java/lang/Exception
    //   184	190	254	java/lang/Exception
    //   196	202	254	java/lang/Exception
    //   208	213	254	java/lang/Exception
    //   158	165	264	finally
    //   158	165	274	java/lang/Exception
    //   44	158	284	finally
    //   44	158	297	java/lang/Exception
    //   325	329	332	java/io/IOException
    //   307	311	339	finally
    //   360	364	367	java/io/IOException
    //   2	6	374	finally
    //   13	25	374	finally
    //   213	223	374	finally
    //   228	233	374	finally
    //   237	241	374	finally
    //   311	321	374	finally
    //   325	329	374	finally
    //   346	356	374	finally
    //   360	364	374	finally
    //   368	372	374	finally
    //   372	374	374	finally
  }
  
  public void deleteTask(int paramInt, boolean paramBoolean)
  {
    this.jdField_a_of_type_ComTencentTbsCommonDownloadIDownloadManager.deleteTask(paramInt, paramBoolean);
  }
  
  public int downloadPlugin(final String paramString, int paramInt1, int paramInt2, QBPluginItemInfo paramQBPluginItemInfo)
    throws RemoteException
  {
    PluginStatBehavior.setOpType(paramString, 2);
    getStatKey(paramString);
    Object localObject3 = new Integer[1];
    localObject3[0] = null;
    Object localObject2 = getPluginInfoWithCode(paramString, paramInt1, (Integer[])localObject3);
    Object localObject1;
    if (paramQBPluginItemInfo == null) {
      localObject1 = localObject2;
    } else {
      localObject1 = paramQBPluginItemInfo;
    }
    if ((localObject2 != null) && (localObject1 != null))
    {
      boolean bool1;
      if ((paramQBPluginItemInfo != null) && (!TextUtils.isEmpty(paramQBPluginItemInfo.mVersion)) && (!TextUtils.isEmpty(((QBPluginItemInfo)localObject2).mVersion)) && (!paramQBPluginItemInfo.mVersion.equalsIgnoreCase(((QBPluginItemInfo)localObject2).mVersion))) {
        bool1 = true;
      } else {
        bool1 = false;
      }
      if ((TbsMode.thirdTbsMode()) && (!isSpecialPkg(paramString)))
      {
        paramQBPluginItemInfo = new StringBuilder();
        paramQBPluginItemInfo.append("DownloadPlugin ");
        paramQBPluginItemInfo.append(paramString);
        paramQBPluginItemInfo.append(" Third App and not special package");
        FLogger.i("QBPluginServiceImpl_TBS", paramQBPluginItemInfo.toString());
        PluginStatBehavior.addLogPath(paramString, 2, 423);
        PluginStatBehavior.setFinCode(paramString, 2, 423);
        return 3016;
      }
      if (TbsMode.TBSISQB()) {
        bool2 = true;
      } else {
        bool2 = canMultiProcessPlugin(callerAppContext(), paramString);
      }
      paramQBPluginItemInfo = new StringBuilder();
      paramQBPluginItemInfo.append("DownloadPlugin ");
      paramQBPluginItemInfo.append(paramString);
      paramQBPluginItemInfo.append(" canDownloadPlugIn=");
      paramQBPluginItemInfo.append(bool2);
      FLogger.i("QBPluginServiceImpl_TBS", paramQBPluginItemInfo.toString());
      if (!bool2)
      {
        PluginStatBehavior.addLogPath(paramString, 2, 424);
        PluginStatBehavior.setFinCode(paramString, 2, 424);
        return 3011;
      }
      try
      {
        if (newVersionFileDownloaded(paramString, paramInt1, (QBPluginItemInfo)localObject1))
        {
          PluginStatBehavior.addLogPath(paramString, 2, 417);
          if (this.jdField_a_of_type_ComTencentTbsCommonDownloadIDownloadManager.fireTaskSuccess(((QBPluginItemInfo)localObject1).mUrl, this))
          {
            paramQBPluginItemInfo = new StringBuilder();
            paramQBPluginItemInfo.append("downloadPlugin ");
            paramQBPluginItemInfo.append(paramString);
            paramQBPluginItemInfo.append("  newViersionFile is already on disk. call onTaskCompleted directly.");
            FLogger.i("QBPluginServiceImpl_TBS", paramQBPluginItemInfo.toString());
            return 0;
          }
          PluginStatBehavior.addLogPath(paramString, 2, 418);
        }
      }
      catch (RemoteException paramQBPluginItemInfo)
      {
        paramQBPluginItemInfo.printStackTrace();
      }
      if (!this.c)
      {
        setDownloadManager(getPluginRelateFunc(paramInt1).getDownloadManager());
        this.c = true;
      }
      if (!this.jdField_a_of_type_ComTencentTbsCommonDownloadIDownloadManager.hasInitCompleted()) {
        this.jdField_a_of_type_ComTencentTbsCommonDownloadIDownloadManager.init();
      }
      boolean bool2 = this.jdField_a_of_type_ComTencentTbsCommonDownloadIDownloadManager.checkTaskDownloading(((QBPluginItemInfo)localObject1).mUrl);
      paramQBPluginItemInfo = new DownloadInfo();
      paramQBPluginItemInfo.url = ((QBPluginItemInfo)localObject1).mUrl;
      if (!TextUtils.isEmpty(((QBPluginItemInfo)localObject1).mPackageSize))
      {
        long l = StringUtils.parseLong(((QBPluginItemInfo)localObject1).mPackageSize, -1L);
        if ((l > 0L) && (!TextUtils.isEmpty(((QBPluginItemInfo)localObject1).mAntiHijackUrl)))
        {
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("addAnitHijackUrl=");
          ((StringBuilder)localObject2).append(((QBPluginItemInfo)localObject1).mAntiHijackUrl);
          FLogger.i("QBPluginServiceImpl_TBS", ((StringBuilder)localObject2).toString());
          PluginStatBehavior.addLogPath(paramString, 2, 425);
          paramQBPluginItemInfo.retryUrls = new ArrayList();
          paramQBPluginItemInfo.retryUrls.add(((QBPluginItemInfo)localObject1).mAntiHijackUrl);
          paramQBPluginItemInfo.fileSize = l;
        }
      }
      localObject2 = getPluginDownloadDir(callerAppContext(), ((QBPluginItemInfo)localObject1).mUrl, paramString);
      if (localObject2 != null) {
        paramQBPluginItemInfo.fileFolderPath = ((File)localObject2).getAbsolutePath();
      }
      if ((!bool2) && (!bool1) && (!TextUtils.isEmpty(paramQBPluginItemInfo.fileFolderPath)) && ((TextUtils.isEmpty(((QBPluginItemInfo)localObject1).mInstallDir)) || (!((QBPluginItemInfo)localObject1).mInstallDir.toLowerCase().contains(paramQBPluginItemInfo.fileFolderPath.toLowerCase()))))
      {
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("downloadDir=");
        ((StringBuilder)localObject3).append(paramQBPluginItemInfo.fileFolderPath);
        ((StringBuilder)localObject3).append(",installDir=");
        ((StringBuilder)localObject3).append(((QBPluginItemInfo)localObject1).mInstallDir);
        ((StringBuilder)localObject3).append(",will delete all the downloadedFile isTaskDowning=");
        ((StringBuilder)localObject3).append(bool2);
        ((StringBuilder)localObject3).append(",maybeNewverion=");
        ((StringBuilder)localObject3).append(bool1);
        FLogger.i("QBPluginServiceImpl_TBS", ((StringBuilder)localObject3).toString());
        if (((TbsMode.TBSISQB()) && (!paramQBPluginItemInfo.fileFolderPath.toLowerCase().startsWith("/data"))) || (!TbsMode.TBSISQB())) {
          try
          {
            PluginStatBehavior.addLogPath(paramString, 2, 426);
            PluginStatBehavior.addLogPath(paramString, 4, 426);
            FileUtilsF.cleanDirectory((File)localObject2);
          }
          catch (IOException localIOException)
          {
            localIOException.printStackTrace();
          }
          catch (IllegalArgumentException localIllegalArgumentException)
          {
            localIllegalArgumentException.printStackTrace();
          }
        }
      }
      ((QBPluginItemInfo)localObject1).mDownloadDir = paramQBPluginItemInfo.fileFolderPath;
      QBPluginDBHelper.getInstance(getInstance().dbHelperContext()).updatePluginDownloadDir(paramString, ((QBPluginItemInfo)localObject1).mDownloadDir, paramInt1);
      paramQBPluginItemInfo.flag |= 0x20;
      if ((!bool2) && (((paramInt2 & 0x1) != 0) || ((paramInt2 & 0x4) != 0))) {
        paramQBPluginItemInfo.flag |= 0x10000000;
      } else {
        paramQBPluginItemInfo.flag |= 0x4;
      }
      paramQBPluginItemInfo.isPluginTask = true;
      paramQBPluginItemInfo.annotation = paramString;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramInt1);
      localStringBuilder.append("");
      paramQBPluginItemInfo.annotationExt = localStringBuilder.toString();
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("startDownloadPlugin ");
      localStringBuilder.append(paramString);
      localStringBuilder.append(",infoFrom=");
      localStringBuilder.append(paramQBPluginItemInfo.annotationExt);
      FLogger.i("QBPluginServiceImpl_TBS", localStringBuilder.toString());
      if (((QBPluginItemInfo)localObject1).mUrl.toLowerCase().endsWith(".zip"))
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(paramString);
        localStringBuilder.append(((QBPluginItemInfo)localObject1).mVersion);
        localStringBuilder.append(".zip");
        paramQBPluginItemInfo.fileName = localStringBuilder.toString();
      }
      PluginStatBehavior.setDownloadDir(paramString, 2, ((QBPluginItemInfo)localObject1).mDownloadDir);
      PluginStatBehavior.setDownloadFileName(paramString, 2, paramQBPluginItemInfo.fileName);
      paramQBPluginItemInfo.observer = new DownloadInfo.DownloadTaskConfirmObserver()
      {
        public void onTaskCancelled(DownloadInfo paramAnonymousDownloadInfo) {}
        
        public void onTaskCreated(DownloadTask paramAnonymousDownloadTask)
        {
          paramAnonymousDownloadTask = paramString;
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("1000(");
          localStringBuilder.append(System.currentTimeMillis());
          localStringBuilder.append(")");
          PluginStatBehavior.addLogPath(paramAnonymousDownloadTask, 2, localStringBuilder.toString());
          paramAnonymousDownloadTask = paramString;
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("1000(");
          localStringBuilder.append(System.currentTimeMillis());
          localStringBuilder.append(")");
          PluginStatBehavior.addLogPath(paramAnonymousDownloadTask, 4, localStringBuilder.toString());
        }
      };
      paramInt1 = this.jdField_a_of_type_ComTencentTbsCommonDownloadIDownloadManager.startDownload(paramQBPluginItemInfo);
      paramQBPluginItemInfo = new StringBuilder();
      paramQBPluginItemInfo.append("DownloadPlugin, mDownloadManager.startDownload(downloadInfo)=");
      paramQBPluginItemInfo.append(paramInt1);
      FLogger.i("QBPluginServiceImpl_TBS", paramQBPluginItemInfo.toString());
      if (paramInt1 > 0)
      {
        paramQBPluginItemInfo = new StringBuilder();
        paramQBPluginItemInfo.append("421(");
        paramQBPluginItemInfo.append(System.currentTimeMillis());
        paramQBPluginItemInfo.append(")");
        PluginStatBehavior.addLogPath(paramString, 2, paramQBPluginItemInfo.toString());
        paramQBPluginItemInfo = new StringBuilder();
        paramQBPluginItemInfo.append("421(");
        paramQBPluginItemInfo.append(System.currentTimeMillis());
        paramQBPluginItemInfo.append(")");
        PluginStatBehavior.addLogPath(paramString, 4, paramQBPluginItemInfo.toString());
        return 0;
      }
      paramQBPluginItemInfo = new StringBuilder();
      paramQBPluginItemInfo.append("416(");
      paramQBPluginItemInfo.append(paramInt1);
      paramQBPluginItemInfo.append(")");
      PluginStatBehavior.addLogPath(paramString, 2, paramQBPluginItemInfo.toString());
      paramQBPluginItemInfo = new StringBuilder();
      paramQBPluginItemInfo.append("416(");
      paramQBPluginItemInfo.append(paramInt1);
      paramQBPluginItemInfo.append(")");
      PluginStatBehavior.addLogPath(paramString, 4, paramQBPluginItemInfo.toString());
      PluginStatBehavior.setFinCode(paramString, 2, 416);
      return 3011;
    }
    if (ConnectivityDetector.checkNetworkConnectivity())
    {
      paramQBPluginItemInfo = PluginStatBehavior.getLogPath("PluginDB", 7);
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("420(cnn_");
      ((StringBuilder)localObject1).append(paramQBPluginItemInfo);
      ((StringBuilder)localObject1).append(")");
      PluginStatBehavior.addLogPath(paramString, 2, ((StringBuilder)localObject1).toString());
      if ((localObject3[0] != null) && (localObject3[0].intValue() == 332))
      {
        PluginStatBehavior.setFinCode(paramString, 2, 332);
        return 332;
      }
      PluginStatBehavior.setFinCode(paramString, 2, 420);
      return 3015;
    }
    PluginStatBehavior.addLogPath(paramString, 2, 433);
    PluginStatBehavior.setFinCode(paramString, 2, 433);
    return 3018;
  }
  
  public int forcePullPluginList(int paramInt)
    throws RemoteException
  {
    WUPRequestBase localWUPRequestBase = getPluginRequestForce("", "force", paramInt);
    if (localWUPRequestBase == null) {
      return REQ_ERROR_REQUEST_SENDING;
    }
    if (WUPTaskProxy.send(localWUPRequestBase))
    {
      FLogger.d("QBPluginServiceImpl_TBS", "getPluginRequest  :Send Out");
      setPluginRequestListStatus(1, paramInt);
      PluginStatBehavior.addLogPath("PluginList", PluginStatBehavior.getOpTyepPluginList(paramInt), 313);
      return REQ_ERROR_REQUEST_SEND_SUCCESS;
    }
    setPluginRequestListStatus(3, paramInt);
    FLogger.d("QBPluginServiceImpl_TBS", "getPluginRequest  :Send Failed");
    PluginStatBehavior.addLogPath("PluginList", PluginStatBehavior.getOpTyepPluginList(paramInt), 314);
    PluginStatBehavior.setFinCode("PluginList", PluginStatBehavior.getOpTyepPluginList(paramInt), 314);
    return REQ_ERROR_REQUEST_SEND_FAILED;
  }
  
  public boolean forceRequestPluginList(String paramString, int paramInt)
  {
    FLogger.e("QBPluginServiceImpl_TBS", new Exception("尽量别调用forceRequestPluginList，可能给后台造成预料之外的压力"));
    PluginSetting localPluginSetting = PluginSetting.getInstance(callerAppContext());
    boolean bool = false;
    localPluginSetting.setPluginListSyncSameToSvr(paramInt, false);
    if (a(paramString, "Force", paramInt) == 0) {
      bool = true;
    }
    return bool;
  }
  
  public void forcekillProcess()
  {
    PluginCallbackHandler localPluginCallbackHandler = this.jdField_a_of_type_ComTencentCommonPluginPluginCallbackHandler;
    if (localPluginCallbackHandler != null) {
      localPluginCallbackHandler.postDelayed(new Runnable()
      {
        public void run()
        {
          try
          {
            Process.killProcess(Process.myPid());
            return;
          }
          catch (Exception localException) {}
        }
      }, 2000L);
    }
  }
  
  public List<QBPluginItemInfo> getAllPluginList(final int paramInt)
    throws RemoteException
  {
    BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        QBPluginServiceImpl.a(QBPluginServiceImpl.this, null, "Visit_getAllList", null, paramInt);
      }
    });
    return QBPluginDBHelper.getInstance(dbHelperContext()).getAllPluginList(paramInt);
  }
  
  public List<QBPluginItemInfo> getAllPluginListWithnoReq(int paramInt)
    throws RemoteException
  {
    return QBPluginDBHelper.getInstance(dbHelperContext()).getAllPluginList(paramInt);
  }
  
  public QBPluginInfo getLocalPluginInfo(String paramString, int paramInt1, int paramInt2)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    int i = 0;
    QBPluginSystem.PluginPkgKey localPluginPkgKey = new QBPluginSystem.PluginPkgKey(paramString, paramInt2);
    synchronized (this.mPackageName2PluginInfoMap)
    {
      if ((QBPluginInfo)this.mPackageName2PluginInfoMap.get(localPluginPkgKey) == null) {
        i = 1;
      }
      if (i != 0) {
        if (paramInt1 == 1)
        {
          ??? = ZipPluginManager.getInstance().scanZipLocalPluginInfo(paramString, paramInt2);
          paramString = this.mPackageName2PluginInfoMap;
          if (??? != null) {}
          try
          {
            this.mPackageName2PluginInfoMap.put(localPluginPkgKey, ???);
          }
          finally {}
        }
        else if (paramInt1 == 2)
        {
          loadDiskPluginInfoList();
        }
        else if (paramInt1 == 3)
        {
          ??? = ZipPluginManager.getInstance().scanZipLocalPluginInfo(paramString, paramInt2);
          paramString = this.mPackageName2PluginInfoMap;
          if (??? != null) {}
          try
          {
            this.mPackageName2PluginInfoMap.put(localObject1, ???);
          }
          finally {}
        }
        else
        {
          ??? = ZipPluginManager.getInstance().scanPlugin(paramString, paramInt2);
          paramString = this.mPackageName2PluginInfoMap;
          if (??? != null) {}
          try
          {
            this.mPackageName2PluginInfoMap.put(localObject2, ???);
          }
          finally {}
        }
      }
      synchronized (this.mPackageName2PluginInfoMap)
      {
        paramString = (QBPluginInfo)this.mPackageName2PluginInfoMap.get(localObject3);
        if ((paramInt1 == 2) && (paramString != null))
        {
          ??? = paramString.mLocalPath;
          if (??? == null) {
            paramString = null;
          } else if (!new File((String)???).exists()) {
            paramString = null;
          }
          synchronized (this.mPackageName2PluginInfoMap)
          {
            this.mPackageName2PluginInfoMap.remove(localObject3);
            this.mPackageName2PluginInfoMap.put(localObject3, paramString);
            return paramString;
          }
        }
        return paramString;
      }
    }
  }
  
  public String getLocalPluinName()
  {
    return QBPluginDBHelper.getInstance(dbHelperContext()).getDbFileName();
  }
  
  public PluginCallbackHandler getPluginCallbackHandler()
  {
    return this.jdField_a_of_type_ComTencentCommonPluginPluginCallbackHandler;
  }
  
  public PluginConfigInfo getPluginConfigInfo(String paramString)
  {
    synchronized (pluginConfigInfoHashMap)
    {
      paramString = (PluginConfigInfo)pluginConfigInfoHashMap.get(paramString);
      return paramString;
    }
  }
  
  public QBPluginItemInfo getPluginInfo(String paramString, int paramInt)
    throws RemoteException
  {
    return getPluginInfoWithCode(paramString, paramInt, null);
  }
  
  public boolean getPluginInfoAsync(String paramString, IGetPluginInfoCallback paramIGetPluginInfoCallback, int paramInt)
    throws RemoteException
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  protected QBPluginItemInfo getPluginInfoWithCode(String paramString, int paramInt, Integer[] paramArrayOfInteger)
    throws RemoteException
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  public ArrayList<QBPluginItemInfo> getPluginListByPos(int paramInt1, final int paramInt2)
    throws RemoteException
  {
    BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        QBPluginServiceImpl.a(QBPluginServiceImpl.this, null, "Visit_getListByPos", null, paramInt2);
      }
    });
    return QBPluginDBHelper.getInstance(dbHelperContext()).getPluginListByPos(paramInt1, paramInt2);
  }
  
  public List<QBPluginItemInfo> getPluginListByType(int paramInt1, final int paramInt2)
    throws RemoteException
  {
    BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        QBPluginServiceImpl.a(QBPluginServiceImpl.this, null, "Visit_getListByType", null, paramInt2);
      }
    });
    return QBPluginDBHelper.getInstance(dbHelperContext()).getPluginListByType(paramInt1, paramInt2);
  }
  
  public String getPluginPath(String paramString, int paramInt)
    throws RemoteException
  {
    QBPluginInfo localQBPluginInfo = getLocalPluginInfo(paramString, 1, paramInt);
    Object localObject2 = "";
    Object localObject1 = localObject2;
    if (localQBPluginInfo != null)
    {
      localObject1 = localObject2;
      if (!TextUtils.isEmpty(localQBPluginInfo.mLocalPath))
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append(localQBPluginInfo.mLocalPath);
        ((StringBuilder)localObject1).append(File.separator);
        localObject1 = ((StringBuilder)localObject1).toString();
      }
    }
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("getPluginPath ");
    ((StringBuilder)localObject2).append(paramString);
    ((StringBuilder)localObject2).append(",basePath=");
    ((StringBuilder)localObject2).append((String)localObject1);
    FLogger.i("QBPluginServiceImpl_TBS", ((StringBuilder)localObject2).toString());
    return (String)localObject1;
  }
  
  public WUPRequestBase getPluginRequest(String paramString1, String paramString2, int paramInt)
  {
    if (getPluginRequestListStatus(paramInt) == 1)
    {
      i = PluginStatBehavior.getOpTyepPluginList(paramInt);
      paramString1 = new StringBuilder();
      paramString1.append("312(");
      paramString1.append(Thread.currentThread().getId());
      paramString1.append(")");
      PluginStatBehavior.addLogPath("PluginList", i, paramString1.toString());
      PluginStatBehavior.incReqPluginListCount("PluginList", PluginStatBehavior.getOpTyepPluginList(paramInt));
      return null;
    }
    PluginStatBehavior.setOpType("PluginList", PluginStatBehavior.getOpTyepPluginList(paramInt));
    PluginStatBehavior.addLogPath("PluginList", PluginStatBehavior.getOpTyepPluginList(paramInt), 323);
    String str = PluginSetting.getInstance(callerAppContext()).pluginListRspMD5(paramInt);
    UniPluginReq localUniPluginReq = new UniPluginReq();
    Object localObject = QBPluginProxy.getCpuType();
    localUniPluginReq.iSDKVersion = GdiMeasureImpl.getSdkVersion();
    if (TextUtils.isEmpty((CharSequence)localObject)) {
      localObject = PluginSetting.getInstance(callerAppContext()).getCpuType();
    } else {
      PluginSetting.getInstance(callerAppContext()).setCpuType((String)localObject);
    }
    localUniPluginReq.strCpu = ((String)localObject);
    localUniPluginReq.sMd5 = str;
    localUniPluginReq.vGuid = getPluginRelateFunc(paramInt).getByteGuid();
    localUniPluginReq.sQua = getPluginRelateFunc(paramInt).getQUA();
    if (paramInt == 1)
    {
      localUniPluginReq.sQua2ExInfo = "SR=QB";
    }
    else
    {
      if (paramInt != 2) {
        break label626;
      }
      localUniPluginReq.sQua2ExInfo = "SR=TBS";
    }
    int i = PluginStatBehavior.getOpTyepPluginList(paramInt);
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append(localUniPluginReq.sQua2ExInfo);
    ((StringBuilder)localObject).append(";");
    ((StringBuilder)localObject).append(localUniPluginReq.sMd5);
    ((StringBuilder)localObject).append(";");
    ((StringBuilder)localObject).append(localUniPluginReq.strCpu);
    PluginStatBehavior.setLocalMd5("PluginList", i, ((StringBuilder)localObject).toString());
    i = PluginStatBehavior.getOpTyepPluginList(paramInt);
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append(localUniPluginReq.sQua);
    ((StringBuilder)localObject).append("-");
    ((StringBuilder)localObject).append(localUniPluginReq.strCpu);
    ((StringBuilder)localObject).append(",req.sMd5=");
    ((StringBuilder)localObject).append(localUniPluginReq.sMd5);
    PluginStatBehavior.setSvrMd5("PluginList", i, ((StringBuilder)localObject).toString());
    PluginStatBehavior.setInstallFileName("PluginList", PluginStatBehavior.getOpTyepPluginList(paramInt), paramString2);
    i = PluginStatBehavior.getOpTyepPluginList(paramInt);
    paramString2 = new StringBuilder();
    paramString2.append(Process.myPid());
    paramString2.append("_");
    paramString2.append(System.currentTimeMillis());
    PluginStatBehavior.setDownloadDir("PluginList", i, paramString2.toString());
    paramString2 = new StringBuilder();
    paramString2.append("requestPluginList  iSDKVersion:");
    paramString2.append(localUniPluginReq.iSDKVersion);
    paramString2.append(" strCpu: ");
    paramString2.append(localUniPluginReq.strCpu);
    paramString2.append(" sMd5: ");
    paramString2.append(localUniPluginReq.sMd5);
    paramString2.append(" vGuid: ");
    paramString2.append(localUniPluginReq.vGuid);
    paramString2.append(" sQua: ");
    paramString2.append(localUniPluginReq.sQua);
    paramString2.append(" packageName: ");
    paramString2.append(paramString1);
    FLogger.i("QBPluginServiceImpl_TBS", paramString2.toString());
    paramString2 = new WUPRequestBase("uniplugin", "getPluginList", this);
    if (PluginSetting.getInstance(mCallerAppContext).getMiniQBDebugFlag()) {
      paramString2.setUrl("http://14.17.41.197:18000");
    }
    paramString2.put("stReq", localUniPluginReq);
    paramString2.setBindObject(paramString1);
    paramString2.setType((byte)paramInt);
    return paramString2;
    label626:
    PluginStatBehavior.addLogPath("PluginList", PluginStatBehavior.getOpTyepPluginList(paramInt), 331);
    PluginStatBehavior.setFinCode("PluginList", PluginStatBehavior.getOpTyepPluginList(paramInt), 331);
    return null;
  }
  
  public WUPRequestBase getPluginRequestForce(String paramString1, String paramString2, int paramInt)
  {
    if (getPluginRequestListStatus(paramInt) == 1)
    {
      i = PluginStatBehavior.getOpTyepPluginList(paramInt);
      paramString1 = new StringBuilder();
      paramString1.append("312(");
      paramString1.append(Thread.currentThread().getId());
      paramString1.append(")");
      PluginStatBehavior.addLogPath("PluginList", i, paramString1.toString());
      PluginStatBehavior.incReqPluginListCount("PluginList", PluginStatBehavior.getOpTyepPluginList(paramInt));
      return null;
    }
    PluginStatBehavior.setOpType("PluginList", PluginStatBehavior.getOpTyepPluginList(paramInt));
    PluginStatBehavior.addLogPath("PluginList", PluginStatBehavior.getOpTyepPluginList(paramInt), 323);
    UniPluginReq localUniPluginReq = new UniPluginReq();
    Object localObject = QBPluginProxy.getCpuType();
    localUniPluginReq.iSDKVersion = GdiMeasureImpl.getSdkVersion();
    if (TextUtils.isEmpty((CharSequence)localObject)) {
      localObject = PluginSetting.getInstance(callerAppContext()).getCpuType();
    } else {
      PluginSetting.getInstance(callerAppContext()).setCpuType((String)localObject);
    }
    localUniPluginReq.strCpu = ((String)localObject);
    localUniPluginReq.vGuid = getPluginRelateFunc(paramInt).getByteGuid();
    localUniPluginReq.sQua = getPluginRelateFunc(paramInt).getQUA();
    if (paramInt == 1)
    {
      localUniPluginReq.sQua2ExInfo = "SR=QB";
    }
    else
    {
      if (paramInt != 2) {
        break label627;
      }
      localUniPluginReq.sQua2ExInfo = "SR=TBS";
    }
    int i = PluginStatBehavior.getOpTyepPluginList(paramInt);
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append(localUniPluginReq.sQua2ExInfo);
    ((StringBuilder)localObject).append(";");
    ((StringBuilder)localObject).append(localUniPluginReq.sMd5);
    ((StringBuilder)localObject).append(";");
    ((StringBuilder)localObject).append(localUniPluginReq.strCpu);
    PluginStatBehavior.setLocalMd5("PluginList", i, ((StringBuilder)localObject).toString());
    i = PluginStatBehavior.getOpTyepPluginList(paramInt);
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append(localUniPluginReq.sQua);
    ((StringBuilder)localObject).append("-");
    ((StringBuilder)localObject).append(localUniPluginReq.strCpu);
    ((StringBuilder)localObject).append(",req.sMd5=");
    ((StringBuilder)localObject).append(localUniPluginReq.sMd5);
    PluginStatBehavior.setSvrMd5("PluginList", i, ((StringBuilder)localObject).toString());
    PluginStatBehavior.setInstallFileName("PluginList", PluginStatBehavior.getOpTyepPluginList(paramInt), paramString2);
    i = PluginStatBehavior.getOpTyepPluginList(paramInt);
    paramString2 = new StringBuilder();
    paramString2.append(Process.myPid());
    paramString2.append("_");
    paramString2.append(System.currentTimeMillis());
    PluginStatBehavior.setDownloadDir("PluginList", i, paramString2.toString());
    paramString2 = new StringBuilder();
    paramString2.append("requestPluginList  iSDKVersion:");
    paramString2.append(localUniPluginReq.iSDKVersion);
    paramString2.append(" strCpu: ");
    paramString2.append(localUniPluginReq.strCpu);
    paramString2.append(" sMd5: ");
    paramString2.append(localUniPluginReq.sMd5);
    paramString2.append(" vGuid: ");
    paramString2.append(localUniPluginReq.vGuid);
    paramString2.append(" sQua: ");
    paramString2.append(localUniPluginReq.sQua);
    FLogger.i("QBPluginServiceImpl_TBS", paramString2.toString());
    paramString2 = new WUPRequestBase("uniplugin", "getPluginList", this);
    if (PluginSetting.getInstance(mCallerAppContext).getMiniQBDebugFlag()) {
      paramString2.setUrl("http://14.17.41.197:18000");
    }
    paramString2.put("stReq", localUniPluginReq);
    paramString2.setBindObject(paramString1);
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("requestPluginList  :");
    ((StringBuilder)localObject).append(paramString1);
    FLogger.d("QBPluginServiceImpl_TBS", ((StringBuilder)localObject).toString());
    paramString2.setType((byte)paramInt);
    return paramString2;
    label627:
    PluginStatBehavior.addLogPath("PluginList", PluginStatBehavior.getOpTyepPluginList(paramInt), 331);
    PluginStatBehavior.setFinCode("PluginList", PluginStatBehavior.getOpTyepPluginList(paramInt), 331);
    return null;
  }
  
  public int getPluginRequestListStatus(int paramInt)
  {
    if (paramInt == 1) {
      return this.mReqStatusUi;
    }
    if (paramInt == 2) {
      return this.mReqStatusTbs;
    }
    return 0;
  }
  
  public boolean handlePluginCmd()
  {
    PluginSetting.getInstance(callerAppContext()).setPluginListSyncSameToSvr(1, false);
    int i = a(null, "PushCmd", 1);
    if (i != 0) {
      return false;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("CMD Pull QBLIst=");
    localStringBuilder.append(i);
    FLogger.i("handlePluginCmd", localStringBuilder.toString());
    i = a(null, "PushCmd", 2);
    if (i != 0) {
      return false;
    }
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("CMD Pull TBSLIst=");
    localStringBuilder.append(i);
    FLogger.i("handlePluginCmd", localStringBuilder.toString());
    return true;
  }
  
  public void handlePushRet(byte[] paramArrayOfByte)
  {
    PluginSetting.getInstance(callerAppContext()).setPluginListSyncSameToSvr(1, false);
    PluginSetting.getInstance(callerAppContext()).setPluginListSyncSameToSvr(2, false);
    a(null, "PushNotify", null, 1);
    FLogger.i("handlePluginCmd", "handlePushRet QBLIst=");
    a(null, "PushNotify", null, 2);
    FLogger.i("handlePluginCmd", "handlePushRet LIst=");
  }
  
  public boolean initApplicationCommon(Context paramContext1, Context paramContext2, boolean paramBoolean)
  {
    try
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("initApplicationCommon=");
      localStringBuilder.append(this);
      FLogger.i("QBPluginServiceImpl_TBS", localStringBuilder.toString());
      if (!jdField_a_of_type_JavaUtilConcurrentAtomicAtomicInteger.compareAndSet(0, 1))
      {
        FLogger.i("QBPluginServiceImpl_TBS", "PluginSystem Init initApplicationContext : already init");
        return true;
      }
      mCallerAppContext = paramContext1;
      mTesProviderAppContext = paramContext2;
      if (TbsMode.TBSISQB())
      {
        mFileMode = "700";
        paramContext2 = FileUtils.getTesDataShareDir(paramContext1);
        if (paramContext2 != null)
        {
          paramContext2 = new File(paramContext2, "plugins");
          if (paramContext2.exists()) {
            FileUtilsF.deleteFileOnThread(paramContext2);
          }
        }
        paramContext1 = FileUtils.getTesCorePrivateDir(paramContext1);
        if (paramContext1 != null)
        {
          paramContext1 = new File(paramContext1, "plugins");
          if (paramContext1.exists()) {
            FileUtilsF.deleteFileOnThread(paramContext1);
          }
        }
      }
      PluginStatBehavior.init(mCallerAppContext);
      initPluginConfigInfo();
      jdField_a_of_type_JavaUtilConcurrentAtomicAtomicInteger.compareAndSet(1, 2);
      return true;
    }
    finally {}
  }
  
  public void initPluginConfigInfo()
  {
    synchronized (pluginConfigInfoHashMap)
    {
      pluginConfigInfoHashMap.put("com.tencent.qb.plugin.screencut", new PluginConfigInfo(2, "7000", null, null));
      pluginConfigInfoHashMap.put("com.tencent.qb.plugin.refresh", new PluginConfigInfo(2, "7000", null, null));
      pluginConfigInfoHashMap.put("com.qq.wx.offlinevoice.synthesizer", new PluginConfigInfo(11, "7001", null, null));
      pluginConfigInfoHashMap.put("com.tencent.qb.plugin.cocos", new PluginConfigInfo(1, "6100", null, null));
      pluginConfigInfoHashMap.put("com.tencent.qb.plugin.cocos-js-v3", new PluginConfigInfo(1, "6100", null, null));
      pluginConfigInfoHashMap.put("com.tencent.qb.plugin.cocos-v2", new PluginConfigInfo(1, "6100", null, null));
      pluginConfigInfoHashMap.put("com.tencent.qb.plugin.cocos-v3", new PluginConfigInfo(1, "6100", null, null));
      pluginConfigInfoHashMap.put("com.tencent.qb.plugin.egret", new PluginConfigInfo(1, "6100", null, null));
      pluginConfigInfoHashMap.put("com.tencent.qb.plugin.LayaBoxPlayer", new PluginConfigInfo(1, "6100", null, null));
      pluginConfigInfoHashMap.put("com.tencent.qb.plugin.doc", new PluginConfigInfo(1, "6100", null, null));
      pluginConfigInfoHashMap.put("com.tencent.qb.plugin.docx", new PluginConfigInfo(1, "6100", null, null));
      pluginConfigInfoHashMap.put("com.tencent.qb.plugin.epub", new PluginConfigInfo(1, "6100", null, null));
      pluginConfigInfoHashMap.put("com.tencent.qb.plugin.pdf", new PluginConfigInfo(1, "6100", null, null));
      pluginConfigInfoHashMap.put("com.tencent.qb.plugin.ppt", new PluginConfigInfo(1, "6100", null, null));
      pluginConfigInfoHashMap.put("com.tencent.qb.plugin.pptx", new PluginConfigInfo(1, "6100", null, null));
      pluginConfigInfoHashMap.put("com.tencent.qb.plugin.tiff", new PluginConfigInfo(1, "6100", null, null));
      pluginConfigInfoHashMap.put("com.tencent.qb.plugin.xls", new PluginConfigInfo(1, "6100", null, null));
      pluginConfigInfoHashMap.put("com.tencent.qb.plugin.xlsx", new PluginConfigInfo(1, "6100", null, null));
      pluginConfigInfoHashMap.put("com.tencent.qb.plugin.chm", new PluginConfigInfo(1, "6100", null, null));
      pluginConfigInfoHashMap.put("com.tencent.qb.pluign.betatts", new PluginConfigInfo(1, "7400", null, null));
      pluginConfigInfoHashMap.put("com.tencent.qb.plugin.arrecog", new PluginConfigInfo(1, "8200", null, null));
      pluginConfigInfoHashMap.put("com.tencent.mtt.video.recorder.filter", new PluginConfigInfo(1, "8000", null, null));
      pluginConfigInfoHashMap.put("com.tencent.qb.plugin.arbase", new PluginConfigInfo(1, "8200", null, null));
      pluginConfigInfoHashMap.put("com.tencent.qb.plugin.arslam", new PluginConfigInfo(1, "8400", null, null));
      pluginConfigInfoHashMap.put("com.tencent.qb.plugin.arqbar", new PluginConfigInfo(1, "8801", null, null));
      pluginConfigInfoHashMap.put("com.tencent.qb.plugin.arcropper", new PluginConfigInfo(1, "9300000", null, null));
      pluginConfigInfoHashMap.put("com.tencent.qb.plugin.artext", new PluginConfigInfo(1, "9000000", null, null));
      pluginConfigInfoHashMap.put("com.tencent.qb.plugin.tflite", new PluginConfigInfo(1, "9300000", null, null));
      if (TbsMode.TBSISQB())
      {
        pluginConfigInfoHashMap.put("com.tencent.qb.plugin.videodecode", new PluginConfigInfo(1, "9400", null, null));
        pluginConfigInfoHashMap.put("com.tencent.qb.plugin.tvk.sdk", new PluginConfigInfo(1, "7400", null, null));
      }
      else
      {
        pluginConfigInfoHashMap.put("com.tencent.qb.plugin.videodecode", new PluginConfigInfo(1, "9400", null, null));
        pluginConfigInfoHashMap.put("com.tencent.qb.plugin.tvk.sdk", new PluginConfigInfo(1, "7000", null, null));
      }
      a(0, null);
      return;
    }
  }
  
  public boolean initPluginSystem(IPluginRelateFunc paramIPluginRelateFunc, IPluginDir paramIPluginDir, final int paramInt)
  {
    try
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("initPluginSystem=");
      localStringBuilder.append(this);
      localStringBuilder.append(",infoFrom=");
      localStringBuilder.append(paramInt);
      FLogger.i("QBPluginServiceImpl_TBS", localStringBuilder.toString());
      if (!jdField_a_of_type_JavaUtilConcurrentAtomicAtomicInteger.compareAndSet(2, 3))
      {
        paramIPluginRelateFunc = new StringBuilder();
        paramIPluginRelateFunc.append("PluginSystem Init  InitCommon not Finished or InitPluginSystem is pending, infoFrom=");
        paramIPluginRelateFunc.append(paramInt);
        FLogger.i("QBPluginServiceImpl_TBS", paramIPluginRelateFunc.toString());
        return false;
      }
      if (msRelateFunc == null) {
        msRelateFunc = paramIPluginRelateFunc;
      }
      if (paramInt == 1)
      {
        msIPluginDirUi = paramIPluginDir;
        b();
      }
      paramIPluginRelateFunc = new StringBuilder();
      paramIPluginRelateFunc.append("PluginSystem Init initApplicationContext FileMode:");
      paramIPluginRelateFunc.append(mFileMode);
      paramIPluginRelateFunc.append(" QBMode:");
      paramIPluginRelateFunc.append(TbsMode.TBSISQB());
      FLogger.i("QBPluginServiceImpl_TBS", paramIPluginRelateFunc.toString());
      BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
      {
        public void doRun()
        {
          int i = paramInt;
          if (i == 1)
          {
            boolean bool = QBPluginServiceImpl.this.forceRequestPluginList(null, i);
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("PluginSystem Init initApplicationContext SndPluginRequest: ");
            localStringBuilder.append(bool);
            FLogger.i("QBPluginServiceImpl_TBS", localStringBuilder.toString());
            return;
          }
          i = QBPluginServiceImpl.a(QBPluginServiceImpl.this, null, "initPluginSystem", null, i);
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("PluginSystem Init initApplicationContext SndPluginRequest: ");
          localStringBuilder.append(i);
          FLogger.i("QBPluginServiceImpl_TBS", localStringBuilder.toString());
        }
      });
      jdField_a_of_type_JavaUtilConcurrentAtomicAtomicInteger.compareAndSet(3, 4);
      return true;
    }
    finally {}
  }
  
  public void installPlugin(String arg1, int paramInt1, int paramInt2, IInstallPluginCallback paramIInstallPluginCallback, QBPluginItemInfo paramQBPluginItemInfo, boolean paramBoolean)
    throws RemoteException
  {
    QBPluginSystem.PluginPkgKey localPluginPkgKey = new QBPluginSystem.PluginPkgKey(???, paramInt1);
    synchronized (this.jdField_a_of_type_JavaUtilList)
    {
      if (this.jdField_a_of_type_JavaUtilList.contains(localPluginPkgKey)) {
        return;
      }
      this.jdField_a_of_type_JavaUtilList.add(localPluginPkgKey);
      if (!canMultiProcessPlugin(callerAppContext(), ???))
      {
        a(paramQBPluginItemInfo, TYPE_PLUGIN_INSTALL_FAILED, 6011, paramInt1);
        return;
      }
      a(???, paramInt1, paramInt2, paramIInstallPluginCallback, paramQBPluginItemInfo, paramBoolean);
      if (TbsMode.thirdTbsMode())
      {
        if ((!TextUtils.isEmpty(???)) && (isSpecialPkg(???))) {
          paramIInstallPluginCallback = callerAppContext();
        } else {
          paramIInstallPluginCallback = tesProviderAppContext();
        }
      }
      else {
        paramIInstallPluginCallback = callerAppContext();
      }
      deletePidFile(paramIInstallPluginCallback, ???);
      synchronized (this.jdField_a_of_type_JavaUtilList)
      {
        this.jdField_a_of_type_JavaUtilList.remove(localPluginPkgKey);
        return;
      }
    }
  }
  
  public boolean isNewVersionFileDownloaded(String paramString, int paramInt, QBPluginItemInfo paramQBPluginItemInfo)
    throws RemoteException
  {
    if (paramQBPluginItemInfo == null) {
      paramQBPluginItemInfo = getPluginInfo(paramString, paramInt);
    }
    if (paramQBPluginItemInfo == null) {
      return false;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("isNewVersionFileDownloaded ");
    localStringBuilder.append(paramString);
    localStringBuilder.append(",checkVersionInfo=");
    localStringBuilder.append(paramQBPluginItemInfo);
    localStringBuilder.append(",version=");
    localStringBuilder.append(paramQBPluginItemInfo.mVersion);
    FLogger.i("QBPluginServiceImpl_TBS", localStringBuilder.toString());
    if (checkNeedUpdate(paramString, paramQBPluginItemInfo.mZipJarPluginType, paramInt, paramQBPluginItemInfo)) {
      return newVersionFileDownloaded(paramString, paramInt, paramQBPluginItemInfo);
    }
    return false;
  }
  
  public boolean isSpecialPkg(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      if (this.jdField_a_of_type_ComTencentCommonPluginQBPluginServiceImpl$IDownloadSpecicalWhiteList == null) {
        return false;
      }
      if (paramString.equalsIgnoreCase("com.tencent.qb.plugin.videodecode"))
      {
        if (this.jdField_a_of_type_Int == 0)
        {
          localObject = getPluginInstallDir(tesProviderAppContext(), paramString, true);
          boolean bool;
          if (localObject != null)
          {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("isSpecialPkg  installDir = ");
            localStringBuilder.append(((File)localObject).getAbsolutePath());
            FLogger.i("QBPluginServiceImpl_TBS", localStringBuilder.toString());
            bool = ZipPluginManager.getInstance().scanPluginInDir(paramString, ((File)localObject).getAbsolutePath());
          }
          else
          {
            bool = false;
          }
          int i;
          if (bool) {
            i = 1;
          } else {
            i = 2;
          }
          this.jdField_a_of_type_Int = i;
        }
        if (this.jdField_a_of_type_Int == 2) {
          return true;
        }
      }
      Object localObject = this.jdField_a_of_type_ComTencentCommonPluginQBPluginServiceImpl$IDownloadSpecicalWhiteList.getDownloadSpecicalWhiteList();
      if ((localObject != null) && (((ArrayList)localObject).size() != 0)) {
        return ((ArrayList)localObject).contains(paramString);
      }
      if ((!paramString.equalsIgnoreCase("com.tencent.qb.plugin.egret")) && (!paramString.equalsIgnoreCase("com.tencent.qb.plugin.cocos")) && (!paramString.equalsIgnoreCase("com.tencent.qb.plugin.cocos-v2")) && (!paramString.equalsIgnoreCase("com.tencent.qb.plugin.cocos-v3")) && (!paramString.equalsIgnoreCase("com.tencent.qb.plugin.LayaBoxPlayer")) && (!paramString.equalsIgnoreCase("com.tencent.qb.plugin.cocos-js-v3")) && (!paramString.equalsIgnoreCase("com.tencent.qb.plugin.doc")) && (!paramString.equalsIgnoreCase("com.tencent.qb.plugin.docx")) && (!paramString.equalsIgnoreCase("com.tencent.qb.plugin.epub")) && (!paramString.equalsIgnoreCase("com.tencent.qb.plugin.txt")) && (!paramString.equalsIgnoreCase("com.tencent.qb.plugin.pdf")) && (!paramString.equalsIgnoreCase("com.tencent.qb.plugin.ppt")) && (!paramString.equalsIgnoreCase("com.tencent.qb.plugin.pptx")) && (!paramString.equalsIgnoreCase("com.tencent.qb.plugin.tiff")) && (!paramString.equalsIgnoreCase("com.tencent.qb.plugin.xls")) && (!paramString.equalsIgnoreCase("com.tencent.qb.plugin.xlsx")))
      {
        if (paramString.equalsIgnoreCase("com.tencent.qb.plugin.chm")) {
          return true;
        }
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("isSpecialPkg,mIsThirdTesCallMode:");
        ((StringBuilder)localObject).append(TbsMode.thirdTbsMode());
        ((StringBuilder)localObject).append(",packageName=");
        ((StringBuilder)localObject).append(paramString);
        ((StringBuilder)localObject).append(",mCheckTesProviderPluginResult=");
        ((StringBuilder)localObject).append(this.jdField_a_of_type_Int);
        FLogger.i("QBPluginServiceImpl_TBS", ((StringBuilder)localObject).toString());
        return false;
      }
      return true;
    }
    return false;
  }
  
  public void loadDiskPluginInfoList()
  {
    try
    {
      if ((!this.jdField_b_of_type_Boolean) && (!this.jdField_a_of_type_Boolean))
      {
        this.jdField_b_of_type_Boolean = true;
        ??? = QBPluginDBHelper.getInstance(dbHelperContext()).getAllPluginList(1);
        Object localObject3 = PluginUtils.scanLocalPluginJarFile(callerAppContext(), (ArrayList)???);
        if (localObject3 == null) {
          return;
        }
        synchronized (this.mPackageName2PluginInfoMap)
        {
          if (((HashMap)localObject3).size() > 0)
          {
            localObject3 = ((HashMap)localObject3).values().iterator();
            while (((Iterator)localObject3).hasNext())
            {
              Object localObject5 = (JarFileInfo)((Iterator)localObject3).next();
              localObject5 = PluginUtils.convert(callerAppContext(), (JarFileInfo)localObject5);
              if (localObject5 != null)
              {
                ((QBPluginInfo)localObject5).mPositionIndex = 0;
                QBPluginSystem.PluginPkgKey localPluginPkgKey = new QBPluginSystem.PluginPkgKey(((QBPluginInfo)localObject5).mPackageName, 1);
                this.mPackageName2PluginInfoMap.remove(localPluginPkgKey);
                this.mPackageName2PluginInfoMap.put(localPluginPkgKey, localObject5);
              }
            }
          }
          try
          {
            ??? = (ArrayList)getAllPluginList(1);
          }
          catch (RemoteException localRemoteException)
          {
            localRemoteException.printStackTrace();
          }
          this.jdField_b_of_type_Boolean = false;
          this.jdField_a_of_type_Boolean = true;
          return;
        }
      }
      return;
    }
    finally {}
  }
  
  public boolean needForceUpdate(String paramString, int paramInt, QBPluginItemInfo paramQBPluginItemInfo)
    throws RemoteException
  {
    if (paramQBPluginItemInfo == null) {
      paramQBPluginItemInfo = getPluginInfo(paramString, paramInt);
    }
    boolean bool = false;
    if (paramQBPluginItemInfo == null) {
      return false;
    }
    if (paramQBPluginItemInfo.mUpdateType == 1) {
      bool = true;
    }
    paramQBPluginItemInfo = new StringBuilder();
    paramQBPluginItemInfo.append("checkneedForceUpdate PluginPkgName=");
    paramQBPluginItemInfo.append(paramString);
    paramQBPluginItemInfo.append(",needForceUpdate=");
    paramQBPluginItemInfo.append(bool);
    FLogger.i("QBPluginServiceImpl_TBS", paramQBPluginItemInfo.toString());
    return bool;
  }
  
  public boolean newVersionFileDownloaded(String paramString, int paramInt, QBPluginItemInfo paramQBPluginItemInfo)
    throws RemoteException
  {
    boolean bool2 = TextUtils.isEmpty(paramQBPluginItemInfo.mMd5);
    boolean bool1 = false;
    if (bool2)
    {
      paramQBPluginItemInfo = new StringBuilder();
      paramQBPluginItemInfo.append("isNewVersionFileDownloaded pkgName=");
      paramQBPluginItemInfo.append(paramString);
      paramQBPluginItemInfo.append(",isFileDownloaded=false,pluginList MD5 Empty");
      FLogger.i("QBPluginServiceImpl_TBS", paramQBPluginItemInfo.toString());
      return false;
    }
    if ((!TextUtils.isEmpty(paramQBPluginItemInfo.mDownloadDir)) && (!TextUtils.isEmpty(paramQBPluginItemInfo.mDownloadFileName)))
    {
      Object localObject2 = new File(paramQBPluginItemInfo.mDownloadDir, paramQBPluginItemInfo.mDownloadFileName);
      localObject1 = null;
      if (((File)localObject2).exists())
      {
        ByteBuffer localByteBuffer = FileUtilsF.read(((File)localObject2).getAbsolutePath(), 0L, 256);
        localObject1 = ByteUtils.byteToHexString(Md5Utils.getMD5(localByteBuffer.array(), 0, localByteBuffer.position()));
        FileUtilsF.getInstance().releaseByteBuffer(localByteBuffer);
      }
      if (TextUtils.isEmpty((CharSequence)localObject1))
      {
        paramQBPluginItemInfo = new StringBuilder();
        paramQBPluginItemInfo.append("isNewVersionFileDownloaded pkgName=");
        paramQBPluginItemInfo.append(paramString);
        paramQBPluginItemInfo.append(",isFileDownloaded=false,locafileMd5 Empty");
        FLogger.i("QBPluginServiceImpl_TBS", paramQBPluginItemInfo.toString());
        return false;
      }
      if (!TextUtils.isEmpty(paramQBPluginItemInfo.mPackageSize))
      {
        long l = StringUtils.parseLong(paramQBPluginItemInfo.mPackageSize, -1L);
        if ((l >= 0L) && (l != ((File)localObject2).length()))
        {
          paramQBPluginItemInfo = new StringBuilder();
          paramQBPluginItemInfo.append("FSN");
          paramQBPluginItemInfo.append(Thread.currentThread().getId());
          PluginStatBehavior.addLogPath(paramString, 3, paramQBPluginItemInfo.toString());
          return false;
        }
      }
      if (paramQBPluginItemInfo.mMd5.equals(localObject1))
      {
        bool1 = true;
      }
      else
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("isNewVersionFileDownloaded pkgName=");
        ((StringBuilder)localObject2).append(paramString);
        ((StringBuilder)localObject2).append(",pluginItemInfo.mMd5=");
        ((StringBuilder)localObject2).append(paramQBPluginItemInfo.mMd5);
        ((StringBuilder)localObject2).append(",localFileMd5=");
        ((StringBuilder)localObject2).append((String)localObject1);
        FLogger.i("QBPluginServiceImpl_TBS", ((StringBuilder)localObject2).toString());
      }
      paramQBPluginItemInfo = new StringBuilder();
      paramQBPluginItemInfo.append("isNewVersionFileDownloaded pkgName=");
      paramQBPluginItemInfo.append(paramString);
      paramQBPluginItemInfo.append(",isFileDownloaded=");
      paramQBPluginItemInfo.append(bool1);
      FLogger.i("QBPluginServiceImpl_TBS", paramQBPluginItemInfo.toString());
      paramQBPluginItemInfo = new StringBuilder();
      paramQBPluginItemInfo.append("FS");
      paramQBPluginItemInfo.append(Thread.currentThread().getId());
      PluginStatBehavior.addLogPath(paramString, 3, paramQBPluginItemInfo.toString());
      return bool1;
    }
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("isNewVersionFileDownloaded pkgName=");
    ((StringBuilder)localObject1).append(paramString);
    ((StringBuilder)localObject1).append(",isFileDownloaded=false,pluginItemInfo.mDownloadDir=");
    ((StringBuilder)localObject1).append(paramQBPluginItemInfo.mDownloadDir);
    ((StringBuilder)localObject1).append(",");
    FLogger.i("QBPluginServiceImpl_TBS", ((StringBuilder)localObject1).toString());
    return false;
  }
  
  public void notifyTaskCanceled(String paramString)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("notifyTaskCanceled=");
    ((StringBuilder)localObject).append(paramString);
    FLogger.d("QBPluginServiceImpl_TBS", ((StringBuilder)localObject).toString());
    localObject = this.jdField_a_of_type_ComTencentCommonPluginPluginCallbackHandler.obtainMessage();
    ((Message)localObject).what = TYPE_PLUGIN_DOWNLOAD_CANCLED;
    ((Message)localObject).obj = paramString;
    this.jdField_a_of_type_ComTencentCommonPluginPluginCallbackHandler.sendMessage((Message)localObject);
  }
  
  public void notifyTaskDeleted(DownloadInfo paramDownloadInfo)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("notifyTaskDeleted=");
    localStringBuilder.append(paramDownloadInfo.url);
    FLogger.d("QBPluginServiceImpl_TBS", localStringBuilder.toString());
  }
  
  public void notifyTaskLength(DownloadTask paramDownloadTask, long paramLong1, long paramLong2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("notifyTaskDeleted=");
    localStringBuilder.append(paramDownloadTask.getTaskUrl());
    FLogger.d("QBPluginServiceImpl_TBS", localStringBuilder.toString());
  }
  
  public void notifyTaskPrepareStart(DownloadTask paramDownloadTask) {}
  
  public void onTaskCompleted(Task paramTask)
  {
    DownloadTask localDownloadTask;
    String str1;
    int k;
    Message localMessage;
    int i;
    int j;
    if ((paramTask instanceof DownloadTask))
    {
      localDownloadTask = (DownloadTask)paramTask;
      if (localDownloadTask != null)
      {
        if (!localDownloadTask.getExtFlagPlugin()) {
          return;
        }
        str1 = localDownloadTask.getAnnotation();
        k = StringUtils.parseInt(localDownloadTask.getAnnotationExt(), 0);
        if (k == 0) {
          return;
        }
        localMessage = this.jdField_a_of_type_ComTencentCommonPluginPluginCallbackHandler.obtainMessage();
        i = 1;
        j = 1;
      }
    }
    for (;;)
    {
      try
      {
        String str2 = localDownloadTask.getFileFolderPath();
        QBPluginItemInfo localQBPluginItemInfo = getPluginInfo(str1, k);
        if (localQBPluginItemInfo != null)
        {
          if (TextUtils.isEmpty(str2)) {
            break label326;
          }
          QBPluginDBHelper.getInstance(dbHelperContext()).updatePluginDownloadDir(str1, localQBPluginItemInfo.mDownloadDir, k);
          PluginStatBehavior.setDownloadDir(str1, 2, str2);
          i = j;
          if (!TextUtils.isEmpty(localDownloadTask.getFileName()))
          {
            QBPluginDBHelper.getInstance(dbHelperContext()).updatePluginDownloadFileName(str1, localDownloadTask.getFileName(), k);
            PluginStatBehavior.setFileSize(str1, 2, localDownloadTask.getTotalSize());
            PluginStatBehavior.setDownloadFileName(str1, 2, localDownloadTask.getFileName());
          }
          else
          {
            i = 0;
          }
        }
      }
      catch (Throwable localThrowable)
      {
        localThrowable.printStackTrace();
        i = 0;
      }
      if (i != 0) {
        localMessage.what = TYPE_PLUGIN_DOWNLOAD_SUCESSED;
      } else {
        localMessage.what = TYPE_PLUGIN_DOWNLOAD_FAILED;
      }
      localMessage.obj = paramTask;
      localMessage.arg1 = k;
      this.jdField_a_of_type_ComTencentCommonPluginPluginCallbackHandler.sendMessage(localMessage);
      if (i != 0)
      {
        paramTask = new StringBuilder();
        paramTask.append("40(");
        paramTask.append(System.currentTimeMillis());
        paramTask.append(")[");
        paramTask.append(localDownloadTask.mHijackInfo);
        paramTask.append("]");
        PluginStatBehavior.addLogPath(str1, 2, paramTask.toString());
        PluginStatBehavior.setFinCode(str1, 2, 0);
        return;
      }
      PluginStatBehavior.setFinCode(str1, 2, 430);
      return;
      return;
      return;
      label326:
      i = 0;
    }
  }
  
  public void onTaskCreated(Task paramTask)
  {
    if ((paramTask instanceof DownloadTask))
    {
      Object localObject1 = (DownloadTask)paramTask;
      if (localObject1 != null)
      {
        if (!((DownloadTask)localObject1).getExtFlagPlugin()) {
          return;
        }
        int i = StringUtils.parseInt(((DownloadTask)localObject1).getAnnotationExt(), 0);
        if (i == 0) {
          return;
        }
        Object localObject2 = this.jdField_a_of_type_ComTencentCommonPluginPluginCallbackHandler.obtainMessage();
        ((Message)localObject2).what = TYPE_PLUGIN_DOWNLOAD_CREATE;
        ((Message)localObject2).obj = paramTask;
        ((Message)localObject2).arg1 = i;
        this.jdField_a_of_type_ComTencentCommonPluginPluginCallbackHandler.sendMessage((Message)localObject2);
        paramTask = ((DownloadTask)localObject1).getAnnotation();
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("411(");
        ((StringBuilder)localObject2).append(System.currentTimeMillis());
        ((StringBuilder)localObject2).append(")");
        PluginStatBehavior.addLogPath(paramTask, 4, ((StringBuilder)localObject2).toString());
        paramTask = ((DownloadTask)localObject1).getAnnotation();
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("411(");
        ((StringBuilder)localObject1).append(System.currentTimeMillis());
        ((StringBuilder)localObject1).append(")");
        PluginStatBehavior.addLogPath(paramTask, 2, ((StringBuilder)localObject1).toString());
        return;
      }
      return;
    }
  }
  
  public void onTaskExtEvent(Task paramTask) {}
  
  public void onTaskFailed(Task paramTask)
  {
    if ((paramTask instanceof DownloadTask))
    {
      final DownloadTask localDownloadTask = (DownloadTask)paramTask;
      if (localDownloadTask != null)
      {
        if (!localDownloadTask.getExtFlagPlugin()) {
          return;
        }
        final String str = localDownloadTask.getAnnotation();
        final int i = StringUtils.parseInt(localDownloadTask.getAnnotationExt(), 0);
        if (i == 0) {
          return;
        }
        QBPluginDBHelper.getInstance(dbHelperContext()).updatePluginDownloadDir(str, "", i);
        Message localMessage = this.jdField_a_of_type_ComTencentCommonPluginPluginCallbackHandler.obtainMessage();
        localMessage.what = TYPE_PLUGIN_DOWNLOAD_FAILED;
        localMessage.obj = paramTask;
        localMessage.arg1 = i;
        this.jdField_a_of_type_ComTencentCommonPluginPluginCallbackHandler.sendMessage(localMessage);
        i = localDownloadTask.getErrorCode();
        BrowserExecutorSupplier.forTimeoutTasks().execute(new Runnable()
        {
          public void run()
          {
            int i;
            if (ConnectivityDetector.checkNetworkConnectivity()) {
              i = i;
            } else {
              i = 429;
            }
            String str = str;
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("414(");
            localStringBuilder.append(System.currentTimeMillis());
            localStringBuilder.append(",");
            localStringBuilder.append(localDownloadTask.getHttpResponseCode());
            localStringBuilder.append(")");
            PluginStatBehavior.addLogPath(str, 2, localStringBuilder.toString());
            if (i == 43)
            {
              str = str;
              localStringBuilder = new StringBuilder();
              localStringBuilder.append("414(");
              localStringBuilder.append(localDownloadTask.getTaskUrl());
              localStringBuilder.append(")");
              PluginStatBehavior.addLogPath(str, 2, localStringBuilder.toString());
            }
            PluginStatBehavior.setFinCode(str, 2, i);
          }
        });
        return;
      }
      return;
    }
  }
  
  public void onTaskProgress(Task paramTask)
  {
    if ((paramTask instanceof DownloadTask))
    {
      Object localObject1 = (DownloadTask)paramTask;
      if (localObject1 != null)
      {
        if (!((DownloadTask)localObject1).getExtFlagPlugin()) {
          return;
        }
        int i = StringUtils.parseInt(((DownloadTask)localObject1).getAnnotationExt(), 0);
        if (i == 0) {
          return;
        }
        Object localObject2 = this.jdField_a_of_type_ComTencentCommonPluginPluginCallbackHandler.obtainMessage();
        ((Message)localObject2).what = TYPE_PLUGIN_DOWNLOAD_PROGRESS;
        ((Message)localObject2).obj = paramTask;
        ((Message)localObject2).arg1 = i;
        this.jdField_a_of_type_ComTencentCommonPluginPluginCallbackHandler.sendMessage((Message)localObject2);
        paramTask = ((DownloadTask)localObject1).getAnnotation();
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("413(");
        ((StringBuilder)localObject2).append(System.currentTimeMillis());
        ((StringBuilder)localObject2).append(")");
        PluginStatBehavior.addLogPath(paramTask, 4, ((StringBuilder)localObject2).toString());
        paramTask = ((DownloadTask)localObject1).getAnnotation();
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("413(");
        ((StringBuilder)localObject1).append(System.currentTimeMillis());
        ((StringBuilder)localObject1).append(")");
        PluginStatBehavior.addLogPath(paramTask, 2, ((StringBuilder)localObject1).toString());
        return;
      }
      return;
    }
  }
  
  public void onTaskStarted(Task paramTask)
  {
    if ((paramTask instanceof DownloadTask))
    {
      Object localObject1 = (DownloadTask)paramTask;
      if (localObject1 != null)
      {
        if (!((DownloadTask)localObject1).getExtFlagPlugin()) {
          return;
        }
        if ((((DownloadTask)localObject1).mFlag & 0x10000000) == 268435456) {
          PluginStatBehavior.setOpType(((DownloadTask)localObject1).getAnnotation(), 2);
        }
        int i = StringUtils.parseInt(((DownloadTask)localObject1).getAnnotationExt(), 0);
        if (i == 0) {
          return;
        }
        Object localObject2 = this.jdField_a_of_type_ComTencentCommonPluginPluginCallbackHandler.obtainMessage();
        ((Message)localObject2).what = TYPE_PLUGIN_DOWNLOAD_START;
        ((Message)localObject2).obj = paramTask;
        ((Message)localObject2).arg1 = i;
        this.jdField_a_of_type_ComTencentCommonPluginPluginCallbackHandler.sendMessage((Message)localObject2);
        paramTask = ((DownloadTask)localObject1).getAnnotation();
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("412(");
        ((StringBuilder)localObject2).append(System.currentTimeMillis());
        ((StringBuilder)localObject2).append(")");
        PluginStatBehavior.addLogPath(paramTask, 4, ((StringBuilder)localObject2).toString());
        paramTask = ((DownloadTask)localObject1).getAnnotation();
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("412(");
        ((StringBuilder)localObject1).append(System.currentTimeMillis());
        ((StringBuilder)localObject1).append(")");
        PluginStatBehavior.addLogPath(paramTask, 2, ((StringBuilder)localObject1).toString());
        return;
      }
      return;
    }
  }
  
  public void onWUPTaskFail(WUPRequestBase paramWUPRequestBase)
  {
    int j = paramWUPRequestBase.getType();
    synchronized (this.synPluginListObject)
    {
      if (this.jdField_a_of_type_ComTencentCommonPluginPluginCallbackHandler == null)
      {
        setPluginRequestListStatus(3, j);
        return;
      }
      FLogger.d("QBPluginServiceImpl_TBS", "WUP failed");
      a(paramWUPRequestBase);
      setPluginRequestListStatus(3, j);
      Message localMessage = this.jdField_a_of_type_ComTencentCommonPluginPluginCallbackHandler.obtainMessage();
      localMessage.what = TYPE_PLUGIN_GETLIST_FAILED;
      localMessage.arg1 = paramWUPRequestBase.getType();
      this.jdField_a_of_type_ComTencentCommonPluginPluginCallbackHandler.sendMessage(localMessage);
      int i = REQ_ERROR_REQUEST_SEND_FAILED_NETWORK_OK;
      FLogger.d("QBPluginServiceImpl_TBS", "networkisAailable，start to detect network");
      if (Apn.isWifiMode(false))
      {
        if (ConnectivityDetector.detectWithCDNFile())
        {
          PluginStatBehavior.setFinCode("PluginList", PluginStatBehavior.getOpTyepPluginList(j), paramWUPRequestBase.getErrorCode());
        }
        else
        {
          PluginStatBehavior.setFinCode("PluginList", PluginStatBehavior.getOpTyepPluginList(j), 329);
          i = REQ_ERROR_REQUEST_SEND_FAILED_NETWORK_ERR;
        }
      }
      else if (ConnectivityDetector.detectWithTCPPing())
      {
        PluginStatBehavior.setFinCode("PluginList", PluginStatBehavior.getOpTyepPluginList(j), paramWUPRequestBase.getErrorCode());
      }
      else
      {
        PluginStatBehavior.setFinCode("PluginList", PluginStatBehavior.getOpTyepPluginList(j), 329);
        i = REQ_ERROR_REQUEST_SEND_FAILED_NETWORK_ERR;
      }
      a(i);
      return;
    }
  }
  
  public void onWUPTaskSuccess(WUPRequestBase paramWUPRequestBase, WUPResponseBase paramWUPResponseBase)
  {
    FLogger.startTiming("getPluginListRequest savePluginList");
    synchronized (this.synPluginListObject)
    {
      int j = paramWUPRequestBase.getType();
      if ((paramWUPResponseBase != null) && (this.jdField_a_of_type_ComTencentCommonPluginPluginCallbackHandler != null))
      {
        Integer localInteger = paramWUPResponseBase.getReturnCode();
        int i;
        if ((localInteger != null) && (localInteger.intValue() == 0))
        {
          paramWUPResponseBase = (UniPluginRsp)paramWUPResponseBase.get("stRsp");
          if (paramWUPResponseBase == null)
          {
            PluginStatBehavior.addLogPath("PluginList", PluginStatBehavior.getOpTyepPluginList(j), 317);
            PluginStatBehavior.setFinCode("PluginList", PluginStatBehavior.getOpTyepPluginList(j), 317);
            setPluginRequestListStatus(2, j);
            a(-5);
            FLogger.printCostTime("MultiWUPRequestTimeCost", "getPluginListRequest savePluginList", "getPluginListRequest savePluginList");
            return;
          }
          if (!QBPluginDBHelper.getInstance(dbHelperContext()).insertPluginList(paramWUPResponseBase, dbHelperContext(), j))
          {
            setPluginRequestListStatus(3, j);
            paramWUPResponseBase = this.jdField_a_of_type_ComTencentCommonPluginPluginCallbackHandler.obtainMessage();
            paramWUPResponseBase.what = TYPE_PLUGIN_GETLIST_FAILED;
            paramWUPResponseBase.arg1 = j;
            paramWUPResponseBase.obj = paramWUPRequestBase.getBindObject();
            this.jdField_a_of_type_ComTencentCommonPluginPluginCallbackHandler.sendMessage(paramWUPResponseBase);
            PluginStatBehavior.addLogPath("PluginList", PluginStatBehavior.getOpTyepPluginList(j), 320);
            PluginStatBehavior.setFinCode("PluginList", PluginStatBehavior.getOpTyepPluginList(j), 320);
            a(-6);
            FLogger.i("QBPluginServiceImpl_TBS", "getpluginlist: wup failed 4");
            FLogger.printCostTime("MultiWUPRequestTimeCost", "getPluginListRequest savePluginList", "getPluginListRequest savePluginList");
            return;
          }
          PluginSetting.getInstance(callerAppContext()).setPluginListRspMD5(j, paramWUPResponseBase.sMd5);
          PluginStatBehavior.setDownloadFileName("PluginList", PluginStatBehavior.getOpTyepPluginList(j), paramWUPResponseBase.sMd5);
          updatePluginConfigInfoToPluginList(j);
          if (PluginSetting.getInstance(callerAppContext()).pluginListLastTime(j) <= 0L) {
            PluginSetting.getInstance(callerAppContext()).setPluginListLastTime(j, System.currentTimeMillis());
          }
          PluginSetting.getInstance(callerAppContext()).setPluginListSyncSameToSvr(j, true);
          i = PluginStatBehavior.getOpTyepPluginList(j);
          paramWUPResponseBase = new StringBuilder();
          paramWUPResponseBase.append("InfoFrom_");
          paramWUPResponseBase.append(j);
          paramWUPResponseBase.append("PullList");
          PluginStatBehavior.addLogPath("PluginList", i, paramWUPResponseBase.toString());
          FLogger.i("QBPluginServiceImpl_TBS", "responsePluginList onWUPTaskSuccess  setPluginListRspSucc:true");
          paramWUPResponseBase = this.jdField_a_of_type_ComTencentCommonPluginPluginCallbackHandler.obtainMessage();
          paramWUPResponseBase.what = TYPE_PLUGIN_GETLIST_SUCC;
          paramWUPResponseBase.obj = paramWUPRequestBase.getBindObject();
          paramWUPResponseBase.arg1 = paramWUPRequestBase.getType();
          this.jdField_a_of_type_ComTencentCommonPluginPluginCallbackHandler.sendMessage(paramWUPResponseBase);
          PluginStatBehavior.addLogPath("PluginList", PluginStatBehavior.getOpTyepPluginList(j), 30);
          PluginStatBehavior.setFinCode("PluginList", PluginStatBehavior.getOpTyepPluginList(j), 0);
          setPluginRequestListStatus(2, j);
          FLogger.i("QBPluginServiceImpl_TBS", "getPluginList Success");
          a(0);
        }
        else
        {
          i = 64536;
          if (localInteger != null) {
            i = localInteger.intValue();
          }
          paramWUPResponseBase = new StringBuilder();
          paramWUPResponseBase.append("getPluginList failed errorcode=");
          paramWUPResponseBase.append(i);
          FLogger.i("QBPluginServiceImpl_TBS", paramWUPResponseBase.toString());
          paramWUPResponseBase = this.jdField_a_of_type_ComTencentCommonPluginPluginCallbackHandler.obtainMessage();
          paramWUPResponseBase.what = TYPE_PLUGIN_GETLIST_FAILED;
          paramWUPResponseBase.obj = paramWUPRequestBase.getBindObject();
          this.jdField_a_of_type_ComTencentCommonPluginPluginCallbackHandler.sendMessage(paramWUPResponseBase);
          int k = PluginStatBehavior.getOpTyepPluginList(j);
          paramWUPResponseBase = new StringBuilder();
          paramWUPResponseBase.append("321(");
          paramWUPResponseBase.append(i);
          paramWUPResponseBase.append(")");
          PluginStatBehavior.addLogPath("PluginList", k, paramWUPResponseBase.toString());
          PluginStatBehavior.setFinCode("PluginList", PluginStatBehavior.getOpTyepPluginList(j), i);
          a(-3);
          setPluginRequestListStatus(2, j);
          if ((i != -4) && (i != -1))
          {
            if (i == -6) {
              a();
            }
          }
          else {
            a(paramWUPRequestBase);
          }
        }
        FLogger.printCostTime("MultiWUPRequestTimeCost", "getPluginListRequest savePluginList", "getPluginListRequest savePluginList");
        return;
      }
      PluginStatBehavior.addLogPath("PluginList", PluginStatBehavior.getOpTyepPluginList(j), 315);
      PluginStatBehavior.setFinCode("PluginList", PluginStatBehavior.getOpTyepPluginList(j), 315);
      setPluginRequestListStatus(3, j);
      a(-4);
      FLogger.printCostTime("MultiWUPRequestTimeCost", "getPluginListRequest savePluginList", "getPluginListRequest savePluginList");
      return;
    }
  }
  
  public JSONObject pluginsToJson(int paramInt)
    throws Exception
  {
    JSONObject localJSONObject = new JSONObject();
    Iterator localIterator = QBPluginDBHelper.getInstance(dbHelperContext()).getAllPluginList(paramInt).iterator();
    paramInt = 0;
    while (localIterator.hasNext())
    {
      QBPluginItemInfo localQBPluginItemInfo = (QBPluginItemInfo)localIterator.next();
      JSONArray localJSONArray = new JSONArray();
      localJSONArray.put(localQBPluginItemInfo.mTitle);
      localJSONArray.put(localQBPluginItemInfo.mUrl);
      localJSONArray.put(localQBPluginItemInfo.mIconUrl);
      localJSONArray.put(localQBPluginItemInfo.mPackageName);
      localJSONArray.put(localQBPluginItemInfo.mPluginType);
      localJSONArray.put(localQBPluginItemInfo.mVersion);
      localJSONArray.put(localQBPluginItemInfo.mPackageSize);
      localJSONArray.put(localQBPluginItemInfo.mIsInstall);
      localJSONArray.put(localQBPluginItemInfo.mUpdateType);
      localJSONArray.put(localQBPluginItemInfo.mOrder);
      localJSONArray.put(localQBPluginItemInfo.mLocation);
      localJSONArray.put(localQBPluginItemInfo.mDetailSumary);
      localJSONArray.put(localQBPluginItemInfo.mExt);
      localJSONArray.put(localQBPluginItemInfo.mSignature);
      localJSONArray.put(localQBPluginItemInfo.mDownloadDir);
      localJSONArray.put(localQBPluginItemInfo.mInstallDir);
      localJSONArray.put(localQBPluginItemInfo.mUnzipDir);
      localJSONArray.put(localQBPluginItemInfo.mIsZipFileUpdate);
      localJSONArray.put(localQBPluginItemInfo.mPluginCompatiID);
      localJSONArray.put(localQBPluginItemInfo.mMd5);
      localJSONArray.put(localQBPluginItemInfo.mZipJarPluginType);
      localJSONArray.put(localQBPluginItemInfo.mDownloadFileName);
      localJSONArray.put(localQBPluginItemInfo.mInstallVersion);
      localJSONArray.put(localQBPluginItemInfo.mAntiHijackUrl);
      localJSONArray.put(localQBPluginItemInfo.mInfoFrom);
      localJSONArray.put(localQBPluginItemInfo.isNeedUpdate);
      localJSONObject.put(String.valueOf(paramInt), localJSONArray);
      paramInt += 1;
    }
    return localJSONObject;
  }
  
  public boolean refreshPluginListForced(String paramString, int paramInt, IGetPluginInfoCallback arg3)
    throws RemoteException
  {
    FLogger.e("QBPluginServiceImpl_TBS", new Exception("尽量别调用forceRequestPluginList，可能给后台造成预料之外的压力"));
    if (??? != null)
    {
      AsynGetPluginInfo localAsynGetPluginInfo = new AsynGetPluginInfo();
      localAsynGetPluginInfo.pkgName = paramString;
      localAsynGetPluginInfo.mCallBack = ???;
      localAsynGetPluginInfo.infoFrom = paramInt;
      synchronized (this.jdField_b_of_type_JavaUtilArrayList)
      {
        this.jdField_b_of_type_JavaUtilArrayList.add(localAsynGetPluginInfo);
      }
    }
    ??? = PluginSetting.getInstance(callerAppContext());
    boolean bool = false;
    ???.setPluginListSyncSameToSvr(paramInt, false);
    if (a(paramString, "Force", paramInt) == 0) {
      bool = true;
    }
    return bool;
  }
  
  public boolean refreshPluignListIfNeeded(int paramInt)
    throws RemoteException
  {
    return a(null, "UserOp", null, paramInt) == 0;
  }
  
  public boolean removePluginJarFileFromPluginInfoList(String paramString, int paramInt)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    if (getLocalPluginInfo(paramString, 2, paramInt) == null) {
      return false;
    }
    synchronized (this.mPackageName2PluginInfoMap)
    {
      QBPluginSystem.PluginPkgKey localPluginPkgKey = new QBPluginSystem.PluginPkgKey(paramString, paramInt);
      QBPluginInfo localQBPluginInfo = (QBPluginInfo)this.mPackageName2PluginInfoMap.get(localPluginPkgKey);
      if (localQBPluginInfo != null)
      {
        paramString = FileUtilsF.createDir(PluginFileUtils.getPluginDir(callerAppContext()), paramString);
        if (!TextUtils.isEmpty(localQBPluginInfo.mSoName))
        {
          paramString = new File(paramString, localQBPluginInfo.mSoName);
          boolean bool = paramString.exists();
          if (bool) {
            try
            {
              FileUtilsF.delete(paramString);
            }
            catch (Exception paramString)
            {
              paramString.printStackTrace();
            }
          }
        }
        this.mPackageName2PluginInfoMap.remove(localPluginPkgKey);
      }
      return false;
    }
  }
  
  public void requestPluginSystemInit(IQBPluginCallback paramIQBPluginCallback, int paramInt)
    throws RemoteException
  {
    if ((paramInt == 2) && (!this.d))
    {
      IPluginLoader localIPluginLoader = mIPluginLoader;
      if (localIPluginLoader != null) {
        this.d = localIPluginLoader.tbsPluginLoader();
      }
    }
    paramIQBPluginCallback.onPluginSystemInit(this.d, paramInt);
  }
  
  public void setDownloadManager(IDownloadManager paramIDownloadManager)
  {
    this.jdField_a_of_type_ComTencentTbsCommonDownloadIDownloadManager = paramIDownloadManager;
    this.jdField_a_of_type_ComTencentTbsCommonDownloadIDownloadManager.addTaskObserver(this);
    this.jdField_a_of_type_ComTencentTbsCommonDownloadIDownloadManager.addDownloadedTaskListener(this);
  }
  
  public void setIDownloadSpecicalWhiteList(IDownloadSpecicalWhiteList paramIDownloadSpecicalWhiteList)
  {
    this.jdField_a_of_type_ComTencentCommonPluginQBPluginServiceImpl$IDownloadSpecicalWhiteList = paramIDownloadSpecicalWhiteList;
  }
  
  public boolean setIsPluginInstall(String paramString, boolean paramBoolean, int paramInt)
  {
    return QBPluginDBHelper.getInstance(dbHelperContext()).a(paramString, paramBoolean, paramInt);
  }
  
  public void setPluginCallback(IQBPluginCallback paramIQBPluginCallback, int paramInt)
    throws RemoteException
  {
    if (!this.jdField_a_of_type_JavaUtilArrayList.contains(paramIQBPluginCallback)) {
      this.jdField_a_of_type_JavaUtilArrayList.add(paramIQBPluginCallback);
    }
  }
  
  public void setPluginJarZipType(String paramString, int paramInt1, int paramInt2)
    throws RemoteException
  {
    QBPluginDBHelper.getInstance(dbHelperContext()).updatePluginType(paramString, paramInt1, paramInt2);
  }
  
  public void setPluginRequestListStatus(int paramInt1, int paramInt2)
  {
    if (paramInt2 == 1) {
      this.mReqStatusUi = paramInt1;
    }
    if (paramInt2 == 2) {
      this.mReqStatusTbs = paramInt1;
    }
  }
  
  public boolean stopDownloadPlugin(String paramString, boolean paramBoolean, IQBPluginCallback paramIQBPluginCallback, int paramInt)
    throws RemoteException
  {
    paramIQBPluginCallback = new StringBuilder();
    paramIQBPluginCallback.append("StopDownloadPlugin,pluginName:");
    paramIQBPluginCallback.append(paramString);
    FLogger.i("QBPluginServiceImpl_TBS", paramIQBPluginCallback.toString());
    paramIQBPluginCallback = new StringBuilder();
    paramIQBPluginCallback.append("stopDownloadPluginTask ");
    paramIQBPluginCallback.append(paramString);
    paramIQBPluginCallback.append(",needDeleteTask=");
    paramIQBPluginCallback.append(paramBoolean);
    paramIQBPluginCallback.append(",needDeleteFile=");
    paramIQBPluginCallback.append(paramBoolean);
    FLogger.i("QBPluginServiceImpl_TBS", paramIQBPluginCallback.toString());
    if (this.jdField_a_of_type_ComTencentTbsCommonDownloadIDownloadManager == null) {
      return false;
    }
    paramIQBPluginCallback = null;
    try
    {
      QBPluginItemInfo localQBPluginItemInfo = getPluginInfo(paramString, paramInt);
      paramIQBPluginCallback = localQBPluginItemInfo;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    if (paramIQBPluginCallback == null) {
      return false;
    }
    if (!this.c)
    {
      setDownloadManager(getPluginRelateFunc(paramInt).getDownloadManager());
      this.c = true;
    }
    paramInt = this.jdField_a_of_type_ComTencentTbsCommonDownloadIDownloadManager.getTaskID(paramIQBPluginCallback.mUrl);
    if (paramInt != -1)
    {
      if (TbsMode.thirdTbsMode())
      {
        if (isSpecialPkg(paramString)) {
          paramIQBPluginCallback = callerAppContext();
        } else {
          paramIQBPluginCallback = tesProviderAppContext();
        }
      }
      else {
        paramIQBPluginCallback = callerAppContext();
      }
      deletePidFile(paramIQBPluginCallback, paramString);
      if (paramBoolean)
      {
        this.jdField_a_of_type_ComTencentTbsCommonDownloadIDownloadManager.deleteTask(paramInt, paramBoolean);
        return true;
      }
      this.jdField_a_of_type_ComTencentTbsCommonDownloadIDownloadManager.cancelTaskNoRet(paramInt);
      return true;
    }
    return false;
  }
  
  public Context tesProviderAppContext()
  {
    return mTesProviderAppContext;
  }
  
  public void updatePluginConfigInfoToPluginList(int paramInt)
  {
    HashMap localHashMap2 = new HashMap();
    synchronized (pluginConfigInfoHashMap)
    {
      Iterator localIterator = pluginConfigInfoHashMap.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        localHashMap2.put(localEntry.getKey(), ((PluginConfigInfo)localEntry.getValue()).compatableId);
      }
      QBPluginDBHelper.getInstance(dbHelperContext()).updatePluginCompatId(localHashMap2, paramInt);
      return;
    }
  }
  
  public void updatePluginIsZipFileUpdatel(String paramString, int paramInt1, int paramInt2)
  {
    QBPluginDBHelper.getInstance(dbHelperContext()).updatePluginIsZipFileUpdatel(paramString, paramInt1, paramInt2);
  }
  
  public void updatePluginNeesUpdateFlag(String paramString, int paramInt1, int paramInt2)
  {
    QBPluginDBHelper.getInstance(dbHelperContext()).updatePluginNeesUpdateFlag(paramString, paramInt1, paramInt2);
  }
  
  public static class AsynGetPluginInfo
  {
    public int infoFrom = 0;
    public IGetPluginInfoCallback mCallBack;
    public String pkgName;
  }
  
  public static abstract interface IDownloadSpecicalWhiteList
  {
    public abstract ArrayList<String> getDownloadSpecicalWhiteList();
  }
  
  public static abstract interface ILogPushUploadHelper
  {
    public abstract void uploadLogManully(List<File> paramList);
  }
  
  public static abstract interface IPluginDir
  {
    public abstract ArrayList<QBPluginItemInfo> getDefaultPluginList();
    
    public abstract File getQQBrowserDownloadDir();
    
    public abstract QBPluginServiceImpl.SdcardSizeInfo getSdcardSpace(String paramString);
    
    public abstract File getSpecialInstallDir(int paramInt);
    
    public abstract boolean hasSdcard();
  }
  
  public static abstract interface IPluginLoader
  {
    public abstract boolean tbsPluginLoader();
  }
  
  public static abstract interface IPluginRelateFunc
  {
    public abstract byte[] getByteGuid();
    
    public abstract IDownloadManager getDownloadManager();
    
    public abstract String getQUA();
    
    public abstract void upLoadToBeacon(String paramString, HashMap<String, String> paramHashMap);
    
    public abstract void userBehaviorStatistics(String paramString);
    
    public abstract void userBehaviorStatistics(String paramString, int paramInt);
  }
  
  public static class PluginConfigInfo
  {
    public String compatableId;
    public String installFileName;
    public String installPath;
    public int plugin_type_id;
    
    public PluginConfigInfo(int paramInt, String paramString1, String paramString2, String paramString3)
    {
      this.plugin_type_id = paramInt;
      this.compatableId = paramString1;
      this.installPath = paramString2;
      this.installFileName = paramString3;
    }
    
    @SuppressLint({"DefaultLocale"})
    public String toString()
    {
      return String.format("{typeId=%d,compatableId=%s,installPath=%s,installFileName=%s}", new Object[] { Integer.valueOf(this.plugin_type_id), this.compatableId, this.installPath, this.installFileName });
    }
  }
  
  public static class SdcardSizeInfo
  {
    public long rest = 0L;
    public long total = 0L;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\plugin\QBPluginServiceImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */