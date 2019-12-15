package com.tencent.tbs.common.baseinfo;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.common.utils.FileUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.utils.ThreadUtils;
import com.tencent.tbs.common.logupload.TbsLogManager;
import com.tencent.tbs.common.settings.PublicSettingManager;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class GodCmdWupManager
{
  private static final String CK_TAG = "CK";
  private static final int CMD_KEYS_LENGTH = 5;
  private static final String CMD_LIVELOG_UPDATE = "upload";
  private static final String CMD_LOG_TYPE_LIVELOG = "livelog";
  private static final String CMD_OPTIONS_CLEAR_DOMAIN = "clearDomain";
  private static final String CMD_OPTION_ADD_DOMAIN = "addDomain";
  private static final String CMD_OPTION_DELETE_DOMAIN = "deleteDomain";
  private static final String CMD_OPTION_UPDATE_TBSLOG = "update_tbslog";
  private static final String CMD_SP_UPDATE = "update";
  private static final String CMD_TAG_CACHE = "C";
  private static final String CMD_TAG_CLEAR = "CLEAR";
  private static final String CMD_TAG_DNS = "DNS";
  private static final String CMD_TAG_DOMAIN = "D";
  private static final String CMD_TAG_LOG = "L";
  private static final String CMD_TAG_SP_KEY = "P";
  private static final String CMD_TAG_TBS_BASIC = "T";
  private static final String CMD_TAG_TBS_LOG = "TBSLOG";
  private static final int KEY_DOMAIN_LENGTH = 5;
  private static final String TAG = "GodCmdWupManager";
  private static final String TBS_CFG_FILE = "tbs_pv_config";
  private static final String TBS_CFG_FILE_HIS = "core_godcmd_history";
  static final String TBS_FILS_NAME = "files";
  static final String TBS_FOLDER_NAME = "tbs";
  static final String TBS_PRIVATE_FOLDER_NAME = "core_private";
  static final String TBS_SHARE_FOLDER_NAME = "core_share";
  private boolean canClearDNSCache = false;
  private boolean initailed = false;
  private String logGodCmd = "";
  private Context mAppContext;
  private HashMap<String, String> mCmdHistoryMap;
  private HashMap<String, String> mSyncCmdMap;
  
  private static File getCmdHistoryFile(Context paramContext, String paramString)
  {
    paramContext = getFilesDir(paramContext);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("inti tbsPrivateDir = ");
    localStringBuilder.append(paramContext);
    LogUtils.d("GodCmdWupManager", localStringBuilder.toString());
    if (paramContext == null)
    {
      LogUtils.d("GodCmdWupManager", "inti tbsPrivateDir null error");
      return null;
    }
    paramContext = new File(paramContext, paramString);
    paramString = new StringBuilder();
    paramString.append("inti tbsFile = ");
    paramString.append(paramContext.getName());
    LogUtils.d("GodCmdWupManager", paramString.toString());
    if (paramContext.exists()) {
      return paramContext;
    }
    try
    {
      paramContext.createNewFile();
      return paramContext;
    }
    catch (IOException paramContext)
    {
      paramContext.printStackTrace();
    }
    return null;
  }
  
  static File getFilesDir(Context paramContext)
  {
    paramContext = FileUtils.getFilesDir(paramContext);
    if (paramContext != null)
    {
      if (!paramContext.isDirectory())
      {
        LogUtils.d("GodCmdWupManager", "inti filesDir create");
        if (!paramContext.mkdir()) {
          return null;
        }
      }
      return paramContext;
    }
    return null;
  }
  
  public static GodCmdWupManager getInstance()
  {
    return InstanceHolder.INSTANCE;
  }
  
  static File getTbsCorePrivateDir(Context paramContext)
  {
    paramContext = new File(paramContext.getDir("tbs", 0), "core_private");
    if (!paramContext.isDirectory())
    {
      LogUtils.d("GodCmdWupManager", "inti tbsPrivateDir create");
      if (!paramContext.mkdir()) {
        return null;
      }
    }
    return paramContext;
  }
  
  private static File getTbsFile(Context paramContext, String paramString)
  {
    paramContext = getTbsCorePrivateDir(paramContext);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("inti tbsPrivateDir = ");
    localStringBuilder.append(paramContext);
    LogUtils.d("GodCmdWupManager", localStringBuilder.toString());
    if (paramContext == null)
    {
      LogUtils.d("GodCmdWupManager", "inti tbsPrivateDir null error");
      return null;
    }
    paramContext = new File(paramContext, paramString);
    paramString = new StringBuilder();
    paramString.append("inti tbsFile = ");
    paramString.append(paramContext.getName());
    LogUtils.d("GodCmdWupManager", paramString.toString());
    if (paramContext.exists()) {
      return paramContext;
    }
    try
    {
      paramContext.createNewFile();
      return paramContext;
    }
    catch (IOException paramContext)
    {
      paramContext.printStackTrace();
    }
    return null;
  }
  
  private void preferencesCommit(String paramString1, String paramString2)
  {
    Object localObject = PublicSettingManager.getInstance();
    TbsWupManager.getInstance().setPsm(paramString1, paramString2, (PublicSettingManager)localObject);
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("handleSPGodCmd onPreferencesCommit preference info =");
    ((StringBuilder)localObject).append(paramString1);
    ((StringBuilder)localObject).append("   ");
    ((StringBuilder)localObject).append(paramString2);
    LogUtils.d("GodCmdWupManager", ((StringBuilder)localObject).toString());
  }
  
  private void printCmdHisMap()
  {
    LogUtils.d("GodCmdWupManager", this.mCmdHistoryMap.toString());
  }
  
  private void printSyncMap()
  {
    LogUtils.d("GodCmdWupManager", this.mSyncCmdMap.toString());
  }
  
  public boolean canClearDNSCacheGodCmd()
  {
    return this.canClearDNSCache;
  }
  
  public void commit()
  {
    try
    {
      writeCmdHistoryInfo();
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void excuteAddDomainGodCmd(int paramInt, ArrayList paramArrayList)
  {
    TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).addDomainsGodCmd(paramInt, paramArrayList);
    TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).saveDomainList();
  }
  
  public void excuteClearDomainGodCmd(int paramInt)
  {
    TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).clearDomainsGodCmd(paramInt);
    TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).saveDomainList();
  }
  
  public void excuteDeleteDomainGodCmd(int paramInt, ArrayList paramArrayList)
  {
    TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).deleteDomainsGodCmd(paramInt, paramArrayList);
    TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).saveDomainList();
  }
  
  public void excuteGodCmd()
  {
    if (!this.initailed)
    {
      this.initailed = true;
      init();
    }
    LogUtils.d("GodCmdWupManager", "excuteGodCmd ");
    Object localObject = this.mSyncCmdMap;
    if ((localObject != null) && (((HashMap)localObject).size() > 0))
    {
      if (!TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).hasDomainListData())
      {
        LogUtils.d("GodCmdWupManager", "handleGodCmd WARNING DomainList null");
        TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).loadJsonObject());
      }
      localObject = this.mSyncCmdMap.entrySet().iterator();
      while (((Iterator)localObject).hasNext())
      {
        Map.Entry localEntry = (Map.Entry)((Iterator)localObject).next();
        handleGodCmd((String)localEntry.getKey(), (String)localEntry.getValue());
      }
    }
  }
  
  public String getCmdHisFileName()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(ThreadUtils.getCurrentProcessNameIngoreColon(this.mAppContext));
    localStringBuilder.append("_");
    localStringBuilder.append("core_godcmd_history");
    return localStringBuilder.toString();
  }
  
  public HashMap getCmdHistoryMap()
  {
    return this.mCmdHistoryMap;
  }
  
  public String getCmdTime(String paramString)
  {
    if (this.mCmdHistoryMap.size() <= 0) {
      return "0";
    }
    if (!TextUtils.isEmpty(paramString))
    {
      paramString = (String)this.mCmdHistoryMap.get(paramString);
      if (!TextUtils.isEmpty(paramString)) {
        return paramString;
      }
    }
    return "0";
  }
  
  public String getConfigFileName()
  {
    return "tbs_pv_config";
  }
  
  public String getLiveLogGodCmd()
  {
    return this.logGodCmd;
  }
  
  public HashMap getSyncCmdMap()
  {
    return this.mSyncCmdMap;
  }
  
  public boolean handleCacheGodCmd(String paramString1, String paramString2, String paramString3)
  {
    try
    {
      if (paramString1.equals("DNS"))
      {
        paramString1 = new StringBuilder();
        paramString1.append("handleCacheGodCmd DNS = ");
        paramString1.append(paramString2);
        paramString1.append("");
        LogUtils.d("GodCmdWupManager", paramString1.toString());
        if (paramString2.equals("CLEAR"))
        {
          this.canClearDNSCache = true;
          return true;
        }
        paramString1 = new StringBuilder();
        paramString1.append("ERROR=CANT_HANDLE_CACHE_OPTIONS cmdOptions=");
        paramString1.append(paramString2);
        LogUtils.d("GodCmdWupManager", paramString1.toString());
        return false;
      }
      paramString2 = new StringBuilder();
      paramString2.append("ERROR=CANT_HANDLE_CACHE_TYPE cmdSubType=");
      paramString2.append(paramString1);
      LogUtils.d("GodCmdWupManager", paramString2.toString());
      return false;
    }
    catch (Exception paramString1)
    {
      paramString1.printStackTrace();
    }
    return false;
  }
  
  public boolean handleDomainGodCmd(String paramString1, String paramString2, String paramString3)
  {
    for (;;)
    {
      int i;
      try
      {
        if (TextUtils.isEmpty(paramString3))
        {
          LogUtils.d("GodCmdWupManager", "handleDomainGodCmd ERROR=DOMAIN_VALUE_NULL");
          return false;
        }
        Object localObject = paramString3.split(";");
        ArrayList localArrayList = new ArrayList();
        if (localObject != null)
        {
          LogUtils.d("GodCmdWupManager", "handleDomainGodCmd handle more than one Domain");
          i = 0;
          if (i < localObject.length)
          {
            CharSequence localCharSequence = localObject[i];
            if ((localCharSequence == null) || (TextUtils.isEmpty(localCharSequence))) {
              break label332;
            }
            localArrayList.add(localCharSequence);
            break label332;
          }
        }
        else
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("handleDomainGodCmd handle one Domain :");
          ((StringBuilder)localObject).append(paramString3);
          LogUtils.d("GodCmdWupManager", ((StringBuilder)localObject).toString());
          localArrayList.add(paramString3);
        }
        i = Integer.parseInt(paramString1);
        if (paramString2.equals("addDomain"))
        {
          paramString1 = new StringBuilder();
          paramString1.append("---> excuteAddDomainGodCmd = ");
          paramString1.append(paramString3);
          LogUtils.d("GodCmdWupManager", paramString1.toString());
          excuteAddDomainGodCmd(i, localArrayList);
          return true;
        }
        if (paramString2.equals("deleteDomain"))
        {
          excuteDeleteDomainGodCmd(i, localArrayList);
          paramString1 = new StringBuilder();
          paramString1.append("---> excuteDeleteDomainGodCmd = ");
          paramString1.append(paramString3);
          LogUtils.d("GodCmdWupManager", paramString1.toString());
          return true;
        }
        if (paramString2.equals("clearDomain"))
        {
          excuteClearDomainGodCmd(i);
          paramString1 = new StringBuilder();
          paramString1.append("---> excuteClearDomainGodCmd  type= ");
          paramString1.append(i);
          LogUtils.d("GodCmdWupManager", paramString1.toString());
          return true;
        }
        paramString1 = new StringBuilder();
        paramString1.append("ERROR=CANT_HANDLE_DOMAIN_OPTIONS cmdOptions=");
        paramString1.append(paramString2);
        LogUtils.d("GodCmdWupManager", paramString1.toString());
        return false;
      }
      catch (Exception paramString1)
      {
        LogUtils.d("GodCmdWupManager", Log.getStackTraceString(paramString1));
        return false;
      }
      label332:
      i += 1;
    }
  }
  
  public boolean handleGodCmd(String paramString1, String paramString2)
  {
    Object localObject2;
    Object localObject1;
    CharSequence localCharSequence1;
    CharSequence localCharSequence2;
    CharSequence localCharSequence3;
    if ((!TextUtils.isEmpty(paramString1)) && (!TextUtils.isEmpty(paramString2)))
    {
      localObject2 = paramString1.split("#");
      if (localObject2 == null)
      {
        paramString2 = new StringBuilder();
        paramString2.append("handleGodCmd ERROR=CMD_KEYS_PASE_ERROR; cmdKey=");
        paramString2.append(paramString1);
        LogUtils.d("GodCmdWupManager", paramString2.toString());
        return false;
      }
      if (localObject2.length != 5)
      {
        paramString2 = new StringBuilder();
        paramString2.append("handleGodCmd ERROR=CMD_KEYS_LENGTH; cmdKey=");
        paramString2.append(paramString1);
        LogUtils.d("GodCmdWupManager", paramString2.toString());
        return false;
      }
      localObject1 = localObject2[0];
      localCharSequence1 = localObject2[1];
      localCharSequence2 = localObject2[2];
      localCharSequence3 = localObject2[3];
      localObject2 = localObject2[4];
    }
    for (;;)
    {
      try
      {
        if ((!TextUtils.isEmpty((CharSequence)localObject1)) && (!TextUtils.isEmpty(localCharSequence1)) && (!TextUtils.isEmpty(localCharSequence2)) && (!TextUtils.isEmpty(localCharSequence3)) && (!TextUtils.isEmpty((CharSequence)localObject2)))
        {
          if (!((String)localObject1).equals("CK"))
          {
            paramString2 = new StringBuilder();
            paramString2.append("handleGodCmd ERROR=CK_TAG; cmdKey=");
            paramString2.append(paramString1);
            LogUtils.d("GodCmdWupManager", paramString2.toString());
            return false;
          }
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append((String)localObject1);
          localStringBuilder.append("#");
          localStringBuilder.append(localCharSequence1);
          localStringBuilder.append("#");
          localStringBuilder.append(localCharSequence2);
          localStringBuilder.append("#");
          localStringBuilder.append(localCharSequence3);
          if (Long.parseLong(getCmdTime(localStringBuilder.toString())) >= Long.parseLong((String)localObject2))
          {
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("handleDomainGodCmd WARNING=CMD_duplicate_Ingore; ");
            ((StringBuilder)localObject1).append(paramString1);
            ((StringBuilder)localObject1).append("=");
            ((StringBuilder)localObject1).append(paramString2);
            LogUtils.d("GodCmdWupManager", ((StringBuilder)localObject1).toString());
            return false;
          }
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("handleGodCmd cmdkey ");
          localStringBuilder.append(paramString1);
          localStringBuilder.append(" cmdValue=");
          localStringBuilder.append(paramString2);
          LogUtils.d("GodCmdWupManager", localStringBuilder.toString());
          if (localCharSequence1.equals("D"))
          {
            bool = handleDomainGodCmd(localCharSequence2, localCharSequence3, paramString2);
          }
          else if (localCharSequence1.equals("P"))
          {
            bool = handleSPGodCmd(localCharSequence2, localCharSequence3, paramString2);
          }
          else if (localCharSequence1.equals("L"))
          {
            bool = handleLogGodCmd(localCharSequence2, localCharSequence3, paramString2);
          }
          else if (localCharSequence1.equals("C"))
          {
            bool = handleCacheGodCmd(localCharSequence2, localCharSequence3, paramString2);
          }
          else
          {
            if (!localCharSequence1.equals("T")) {
              break label674;
            }
            bool = handleTbsBaseGodCmd(localCharSequence2, localCharSequence3, paramString2);
          }
          if (!bool) {
            break;
          }
          paramString1 = new StringBuilder();
          paramString1.append((String)localObject1);
          paramString1.append("#");
          paramString1.append(localCharSequence1);
          paramString1.append("#");
          paramString1.append(localCharSequence2);
          paramString1.append("#");
          paramString1.append(localCharSequence3);
          putCmdHisProAndCommit(paramString1.toString(), (String)localObject2);
          return true;
        }
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("handleGodCmd ERROR=CMD_KEYS_FORMAT_ERROR; cmdKey=");
        ((StringBuilder)localObject1).append(paramString1);
        ((StringBuilder)localObject1).append("; cmdValue=");
        ((StringBuilder)localObject1).append(paramString2);
        LogUtils.d("GodCmdWupManager", ((StringBuilder)localObject1).toString());
        return false;
      }
      catch (Exception paramString1)
      {
        paramString1.printStackTrace();
        return false;
      }
      LogUtils.d("GodCmdWupManager", "handleGodCmd ERROR=KV_NULL");
      return false;
      label674:
      boolean bool = false;
    }
    return false;
  }
  
  public boolean handleLogGodCmd(String paramString1, String paramString2, String paramString3)
  {
    try
    {
      if (TextUtils.isEmpty(paramString3))
      {
        LogUtils.d("GodCmdWupManager", "handleLogGodCmd ERROR=SP_VALUE_NULL");
        return false;
      }
      if (!paramString1.equals("livelog"))
      {
        LogUtils.d("GodCmdWupManager", "handleLogGodCmd ERROR=LOG_TYPE_ERROR");
        return false;
      }
      if (paramString2.equals("upload"))
      {
        paramString1 = new StringBuilder();
        paramString1.append(paramString2);
        paramString1.append("=");
        paramString1.append(paramString3);
        this.logGodCmd = paramString1.toString();
        return true;
      }
      paramString1 = new StringBuilder();
      paramString1.append("ERROR=CANT_HANDLE_LOG_OPTIONS cmdOptions=");
      paramString1.append(paramString2);
      LogUtils.d("GodCmdWupManager", paramString1.toString());
      return false;
    }
    catch (Exception paramString1)
    {
      LogUtils.d("GodCmdWupManager", Log.getStackTraceString(paramString1));
    }
    return false;
  }
  
  public boolean handleSPGodCmd(String paramString1, String paramString2, String paramString3)
  {
    try
    {
      if (TextUtils.isEmpty(paramString3))
      {
        LogUtils.d("GodCmdWupManager", "handleSPGodCmd ERROR=SP_VALUE_NULL");
        return false;
      }
      if (paramString2.equals("update"))
      {
        preferencesCommit(paramString1, paramString3);
        return true;
      }
      paramString1 = new StringBuilder();
      paramString1.append("ERROR=CANT_HANDLE_SP_OPTIONS cmdOptions=");
      paramString1.append(paramString2);
      LogUtils.d("GodCmdWupManager", paramString1.toString());
      return false;
    }
    catch (Exception paramString1)
    {
      LogUtils.d("GodCmdWupManager", Log.getStackTraceString(paramString1));
    }
    return false;
  }
  
  public boolean handleTbsBaseGodCmd(String paramString1, String paramString2, final String paramString3)
  {
    if ((paramString1.equals("TBSLOG")) && (paramString2.equals("update_tbslog"))) {
      BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
      {
        public void doRun()
        {
          try
          {
            File localFile = Environment.getExternalStorageDirectory();
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("Android/data/");
            localStringBuilder.append(paramString3);
            localStringBuilder.append("/files/tbslog/tbslog.txt");
            localFile = new File(localFile, localStringBuilder.toString());
            TbsLogManager.uploadTbsLog(GodCmdWupManager.this.mAppContext, new String[] { localFile.getAbsolutePath() }, "tbslog_auto_submit");
            return;
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
          }
        }
      });
    }
    return true;
  }
  
  public void init()
  {
    if (!this.initailed)
    {
      this.initailed = true;
      this.mSyncCmdMap = new HashMap();
      this.mCmdHistoryMap = new HashMap();
      Object localObject = TbsBaseModuleShell.getContext();
      this.mAppContext = ((Context)localObject).getApplicationContext();
      if (this.mAppContext == null) {
        this.mAppContext = ((Context)localObject);
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("init() Start Thread-name");
      ((StringBuilder)localObject).append(Thread.currentThread().getName());
      ((StringBuilder)localObject).append(" Process=");
      ((StringBuilder)localObject).append(ThreadUtils.getCurrentProcessNameIngoreColon(this.mAppContext));
      LogUtils.d("GodCmdWupManager", ((StringBuilder)localObject).toString());
      refreshCmdHisMap();
      refreshSyncCmdMap();
      LogUtils.d("GodCmdWupManager", "init() end");
      return;
    }
    LogUtils.d("GodCmdWupManager", " initailed, not need init()");
  }
  
  public void putCmdHisProAndCommit(String paramString1, String paramString2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("putCmdHisProAndCommit key is ");
    localStringBuilder.append(paramString1);
    localStringBuilder.append(" value is ");
    localStringBuilder.append(paramString2);
    LogUtils.d("GodCmdWupManager", localStringBuilder.toString());
    this.mCmdHistoryMap.put(paramString1, paramString2);
    commit();
  }
  
  /* Error */
  public void refreshCmdHisMap()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 110	com/tencent/tbs/common/baseinfo/GodCmdWupManager:mAppContext	Landroid/content/Context;
    //   6: aload_0
    //   7: invokevirtual 510	com/tencent/tbs/common/baseinfo/GodCmdWupManager:getCmdHisFileName	()Ljava/lang/String;
    //   10: invokestatic 512	com/tencent/tbs/common/baseinfo/GodCmdWupManager:getCmdHistoryFile	(Landroid/content/Context;Ljava/lang/String;)Ljava/io/File;
    //   13: astore_1
    //   14: new 120	java/lang/StringBuilder
    //   17: dup
    //   18: invokespecial 121	java/lang/StringBuilder:<init>	()V
    //   21: astore_2
    //   22: aload_2
    //   23: ldc_w 514
    //   26: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   29: pop
    //   30: aload_2
    //   31: aload_1
    //   32: invokevirtual 152	java/io/File:getName	()Ljava/lang/String;
    //   35: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   38: pop
    //   39: ldc 65
    //   41: aload_2
    //   42: invokevirtual 134	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   45: invokestatic 140	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   48: aload_1
    //   49: ifnonnull +6 -> 55
    //   52: aload_0
    //   53: monitorexit
    //   54: return
    //   55: aload_0
    //   56: getfield 212	com/tencent/tbs/common/baseinfo/GodCmdWupManager:mCmdHistoryMap	Ljava/util/HashMap;
    //   59: invokevirtual 517	java/util/HashMap:clear	()V
    //   62: new 519	java/io/BufferedInputStream
    //   65: dup
    //   66: new 521	java/io/FileInputStream
    //   69: dup
    //   70: aload_1
    //   71: invokespecial 524	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   74: invokespecial 527	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   77: astore_2
    //   78: aload_2
    //   79: astore_1
    //   80: new 529	java/util/Properties
    //   83: dup
    //   84: invokespecial 530	java/util/Properties:<init>	()V
    //   87: astore_3
    //   88: aload_2
    //   89: astore_1
    //   90: aload_3
    //   91: aload_2
    //   92: invokevirtual 533	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   95: aload_2
    //   96: astore_1
    //   97: aload_3
    //   98: invokevirtual 536	java/util/Properties:stringPropertyNames	()Ljava/util/Set;
    //   101: invokeinterface 286 1 0
    //   106: astore 4
    //   108: aload_2
    //   109: astore_1
    //   110: aload 4
    //   112: invokeinterface 291 1 0
    //   117: ifeq +144 -> 261
    //   120: aload_2
    //   121: astore_1
    //   122: aload 4
    //   124: invokeinterface 295 1 0
    //   129: checkcast 302	java/lang/String
    //   132: astore 5
    //   134: aload_2
    //   135: astore_1
    //   136: aload 5
    //   138: ldc 13
    //   140: invokevirtual 540	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   143: ifeq +105 -> 248
    //   146: aload_2
    //   147: astore_1
    //   148: aload_3
    //   149: aload 5
    //   151: invokevirtual 543	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   154: astore 6
    //   156: aload_2
    //   157: astore_1
    //   158: aload 6
    //   160: invokestatic 330	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   163: ifne -55 -> 108
    //   166: aload_2
    //   167: astore_1
    //   168: new 120	java/lang/StringBuilder
    //   171: dup
    //   172: invokespecial 121	java/lang/StringBuilder:<init>	()V
    //   175: astore 7
    //   177: aload_2
    //   178: astore_1
    //   179: aload 7
    //   181: ldc_w 545
    //   184: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   187: pop
    //   188: aload_2
    //   189: astore_1
    //   190: aload 7
    //   192: aload 5
    //   194: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   197: pop
    //   198: aload_2
    //   199: astore_1
    //   200: aload 7
    //   202: ldc_w 547
    //   205: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   208: pop
    //   209: aload_2
    //   210: astore_1
    //   211: aload 7
    //   213: aload 6
    //   215: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   218: pop
    //   219: aload_2
    //   220: astore_1
    //   221: ldc 65
    //   223: aload 7
    //   225: invokevirtual 134	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   228: invokestatic 140	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   231: aload_2
    //   232: astore_1
    //   233: aload_0
    //   234: getfield 212	com/tencent/tbs/common/baseinfo/GodCmdWupManager:mCmdHistoryMap	Ljava/util/HashMap;
    //   237: aload 5
    //   239: aload 6
    //   241: invokevirtual 504	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   244: pop
    //   245: goto -137 -> 108
    //   248: aload_2
    //   249: astore_1
    //   250: ldc 65
    //   252: ldc_w 549
    //   255: invokestatic 140	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   258: goto -150 -> 108
    //   261: aload_2
    //   262: astore_1
    //   263: aload_0
    //   264: invokespecial 551	com/tencent/tbs/common/baseinfo/GodCmdWupManager:printCmdHisMap	()V
    //   267: aload_2
    //   268: invokevirtual 554	java/io/BufferedInputStream:close	()V
    //   271: goto +71 -> 342
    //   274: astore_1
    //   275: ldc 65
    //   277: ldc_w 556
    //   280: invokestatic 140	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   283: aload_1
    //   284: invokevirtual 352	java/lang/Exception:printStackTrace	()V
    //   287: goto +55 -> 342
    //   290: astore_3
    //   291: goto +12 -> 303
    //   294: astore_1
    //   295: aconst_null
    //   296: astore_2
    //   297: goto +53 -> 350
    //   300: astore_3
    //   301: aconst_null
    //   302: astore_2
    //   303: aload_2
    //   304: astore_1
    //   305: ldc 65
    //   307: ldc_w 558
    //   310: invokestatic 140	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   313: aload_2
    //   314: astore_1
    //   315: aload_3
    //   316: invokevirtual 559	java/lang/Throwable:printStackTrace	()V
    //   319: aload_2
    //   320: ifnull +22 -> 342
    //   323: aload_2
    //   324: invokevirtual 554	java/io/BufferedInputStream:close	()V
    //   327: goto +15 -> 342
    //   330: astore_1
    //   331: ldc 65
    //   333: ldc_w 556
    //   336: invokestatic 140	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   339: goto -56 -> 283
    //   342: aload_0
    //   343: monitorexit
    //   344: return
    //   345: astore_3
    //   346: aload_1
    //   347: astore_2
    //   348: aload_3
    //   349: astore_1
    //   350: aload_2
    //   351: ifnull +27 -> 378
    //   354: aload_2
    //   355: invokevirtual 554	java/io/BufferedInputStream:close	()V
    //   358: goto +20 -> 378
    //   361: astore_1
    //   362: goto +18 -> 380
    //   365: astore_2
    //   366: ldc 65
    //   368: ldc_w 556
    //   371: invokestatic 140	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   374: aload_2
    //   375: invokevirtual 352	java/lang/Exception:printStackTrace	()V
    //   378: aload_1
    //   379: athrow
    //   380: aload_0
    //   381: monitorexit
    //   382: aload_1
    //   383: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	384	0	this	GodCmdWupManager
    //   13	250	1	localObject1	Object
    //   274	10	1	localException1	Exception
    //   294	1	1	localObject2	Object
    //   304	11	1	localObject3	Object
    //   330	17	1	localException2	Exception
    //   349	1	1	localObject4	Object
    //   361	22	1	localObject5	Object
    //   21	334	2	localObject6	Object
    //   365	10	2	localException3	Exception
    //   87	62	3	localProperties	java.util.Properties
    //   290	1	3	localThrowable1	Throwable
    //   300	16	3	localThrowable2	Throwable
    //   345	4	3	localObject7	Object
    //   106	17	4	localIterator	Iterator
    //   132	106	5	str1	String
    //   154	86	6	str2	String
    //   175	49	7	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   267	271	274	java/lang/Exception
    //   80	88	290	java/lang/Throwable
    //   90	95	290	java/lang/Throwable
    //   97	108	290	java/lang/Throwable
    //   110	120	290	java/lang/Throwable
    //   122	134	290	java/lang/Throwable
    //   136	146	290	java/lang/Throwable
    //   148	156	290	java/lang/Throwable
    //   158	166	290	java/lang/Throwable
    //   168	177	290	java/lang/Throwable
    //   179	188	290	java/lang/Throwable
    //   190	198	290	java/lang/Throwable
    //   200	209	290	java/lang/Throwable
    //   211	219	290	java/lang/Throwable
    //   221	231	290	java/lang/Throwable
    //   233	245	290	java/lang/Throwable
    //   250	258	290	java/lang/Throwable
    //   263	267	290	java/lang/Throwable
    //   2	48	294	finally
    //   55	78	294	finally
    //   2	48	300	java/lang/Throwable
    //   55	78	300	java/lang/Throwable
    //   323	327	330	java/lang/Exception
    //   80	88	345	finally
    //   90	95	345	finally
    //   97	108	345	finally
    //   110	120	345	finally
    //   122	134	345	finally
    //   136	146	345	finally
    //   148	156	345	finally
    //   158	166	345	finally
    //   168	177	345	finally
    //   179	188	345	finally
    //   190	198	345	finally
    //   200	209	345	finally
    //   211	219	345	finally
    //   221	231	345	finally
    //   233	245	345	finally
    //   250	258	345	finally
    //   263	267	345	finally
    //   305	313	345	finally
    //   315	319	345	finally
    //   267	271	361	finally
    //   275	283	361	finally
    //   283	287	361	finally
    //   323	327	361	finally
    //   331	339	361	finally
    //   354	358	361	finally
    //   366	378	361	finally
    //   378	380	361	finally
    //   354	358	365	java/lang/Exception
  }
  
  /* Error */
  public void refreshSyncCmdMap()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 110	com/tencent/tbs/common/baseinfo/GodCmdWupManager:mAppContext	Landroid/content/Context;
    //   4: aload_0
    //   5: invokevirtual 561	com/tencent/tbs/common/baseinfo/GodCmdWupManager:getConfigFileName	()Ljava/lang/String;
    //   8: invokestatic 563	com/tencent/tbs/common/baseinfo/GodCmdWupManager:getTbsFile	(Landroid/content/Context;Ljava/lang/String;)Ljava/io/File;
    //   11: astore_1
    //   12: new 120	java/lang/StringBuilder
    //   15: dup
    //   16: invokespecial 121	java/lang/StringBuilder:<init>	()V
    //   19: astore_2
    //   20: aload_2
    //   21: ldc_w 565
    //   24: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   27: pop
    //   28: aload_2
    //   29: aload_1
    //   30: invokevirtual 152	java/io/File:getName	()Ljava/lang/String;
    //   33: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   36: pop
    //   37: ldc 65
    //   39: aload_2
    //   40: invokevirtual 134	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   43: invokestatic 140	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   46: aload_1
    //   47: ifnonnull +4 -> 51
    //   50: return
    //   51: aload_0
    //   52: getfield 218	com/tencent/tbs/common/baseinfo/GodCmdWupManager:mSyncCmdMap	Ljava/util/HashMap;
    //   55: invokevirtual 517	java/util/HashMap:clear	()V
    //   58: new 519	java/io/BufferedInputStream
    //   61: dup
    //   62: new 521	java/io/FileInputStream
    //   65: dup
    //   66: aload_1
    //   67: invokespecial 524	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   70: invokespecial 527	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   73: astore_2
    //   74: aload_2
    //   75: astore_1
    //   76: new 529	java/util/Properties
    //   79: dup
    //   80: invokespecial 530	java/util/Properties:<init>	()V
    //   83: astore_3
    //   84: aload_2
    //   85: astore_1
    //   86: aload_3
    //   87: aload_2
    //   88: invokevirtual 533	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   91: aload_2
    //   92: astore_1
    //   93: aload_3
    //   94: invokevirtual 536	java/util/Properties:stringPropertyNames	()Ljava/util/Set;
    //   97: invokeinterface 286 1 0
    //   102: astore 4
    //   104: aload_2
    //   105: astore_1
    //   106: aload 4
    //   108: invokeinterface 291 1 0
    //   113: ifeq +79 -> 192
    //   116: aload_2
    //   117: astore_1
    //   118: aload 4
    //   120: invokeinterface 295 1 0
    //   125: checkcast 302	java/lang/String
    //   128: astore 5
    //   130: aload_2
    //   131: astore_1
    //   132: aload 5
    //   134: ldc 13
    //   136: invokevirtual 540	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   139: ifeq +40 -> 179
    //   142: aload_2
    //   143: astore_1
    //   144: aload_3
    //   145: aload 5
    //   147: invokevirtual 543	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   150: astore 6
    //   152: aload_2
    //   153: astore_1
    //   154: aload 6
    //   156: invokestatic 330	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   159: ifne -55 -> 104
    //   162: aload_2
    //   163: astore_1
    //   164: aload_0
    //   165: getfield 218	com/tencent/tbs/common/baseinfo/GodCmdWupManager:mSyncCmdMap	Ljava/util/HashMap;
    //   168: aload 5
    //   170: aload 6
    //   172: invokevirtual 504	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   175: pop
    //   176: goto -72 -> 104
    //   179: aload_2
    //   180: astore_1
    //   181: ldc 65
    //   183: ldc_w 567
    //   186: invokestatic 140	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   189: goto -85 -> 104
    //   192: aload_2
    //   193: astore_1
    //   194: aload_0
    //   195: invokespecial 569	com/tencent/tbs/common/baseinfo/GodCmdWupManager:printSyncMap	()V
    //   198: aload_2
    //   199: invokevirtual 554	java/io/BufferedInputStream:close	()V
    //   202: return
    //   203: astore_3
    //   204: goto +12 -> 216
    //   207: astore_1
    //   208: aconst_null
    //   209: astore_2
    //   210: goto +50 -> 260
    //   213: astore_3
    //   214: aconst_null
    //   215: astore_2
    //   216: aload_2
    //   217: astore_1
    //   218: ldc 65
    //   220: ldc_w 571
    //   223: invokestatic 140	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   226: aload_2
    //   227: astore_1
    //   228: aload_3
    //   229: invokevirtual 559	java/lang/Throwable:printStackTrace	()V
    //   232: aload_2
    //   233: ifnull +21 -> 254
    //   236: aload_2
    //   237: invokevirtual 554	java/io/BufferedInputStream:close	()V
    //   240: return
    //   241: astore_1
    //   242: ldc 65
    //   244: ldc_w 573
    //   247: invokestatic 140	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   250: aload_1
    //   251: invokevirtual 352	java/lang/Exception:printStackTrace	()V
    //   254: return
    //   255: astore_3
    //   256: aload_1
    //   257: astore_2
    //   258: aload_3
    //   259: astore_1
    //   260: aload_2
    //   261: ifnull +23 -> 284
    //   264: aload_2
    //   265: invokevirtual 554	java/io/BufferedInputStream:close	()V
    //   268: goto +16 -> 284
    //   271: astore_2
    //   272: ldc 65
    //   274: ldc_w 573
    //   277: invokestatic 140	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   280: aload_2
    //   281: invokevirtual 352	java/lang/Exception:printStackTrace	()V
    //   284: aload_1
    //   285: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	286	0	this	GodCmdWupManager
    //   11	183	1	localObject1	Object
    //   207	1	1	localObject2	Object
    //   217	11	1	localObject3	Object
    //   241	16	1	localException1	Exception
    //   259	26	1	localObject4	Object
    //   19	246	2	localObject5	Object
    //   271	10	2	localException2	Exception
    //   83	62	3	localProperties	java.util.Properties
    //   203	1	3	localThrowable1	Throwable
    //   213	16	3	localThrowable2	Throwable
    //   255	4	3	localObject6	Object
    //   102	17	4	localIterator	Iterator
    //   128	41	5	str1	String
    //   150	21	6	str2	String
    // Exception table:
    //   from	to	target	type
    //   76	84	203	java/lang/Throwable
    //   86	91	203	java/lang/Throwable
    //   93	104	203	java/lang/Throwable
    //   106	116	203	java/lang/Throwable
    //   118	130	203	java/lang/Throwable
    //   132	142	203	java/lang/Throwable
    //   144	152	203	java/lang/Throwable
    //   154	162	203	java/lang/Throwable
    //   164	176	203	java/lang/Throwable
    //   181	189	203	java/lang/Throwable
    //   194	198	203	java/lang/Throwable
    //   0	46	207	finally
    //   51	74	207	finally
    //   0	46	213	java/lang/Throwable
    //   51	74	213	java/lang/Throwable
    //   198	202	241	java/lang/Exception
    //   236	240	241	java/lang/Exception
    //   76	84	255	finally
    //   86	91	255	finally
    //   93	104	255	finally
    //   106	116	255	finally
    //   118	130	255	finally
    //   132	142	255	finally
    //   144	152	255	finally
    //   154	162	255	finally
    //   164	176	255	finally
    //   181	189	255	finally
    //   194	198	255	finally
    //   218	226	255	finally
    //   228	232	255	finally
    //   264	268	271	java/lang/Exception
  }
  
  /* Error */
  public void writeCmdHistoryInfo()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: ldc 65
    //   4: ldc_w 574
    //   7: invokestatic 140	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   10: aconst_null
    //   11: astore_2
    //   12: aconst_null
    //   13: astore 4
    //   15: aload_0
    //   16: getfield 110	com/tencent/tbs/common/baseinfo/GodCmdWupManager:mAppContext	Landroid/content/Context;
    //   19: aload_0
    //   20: invokevirtual 510	com/tencent/tbs/common/baseinfo/GodCmdWupManager:getCmdHisFileName	()Ljava/lang/String;
    //   23: invokestatic 512	com/tencent/tbs/common/baseinfo/GodCmdWupManager:getCmdHistoryFile	(Landroid/content/Context;Ljava/lang/String;)Ljava/io/File;
    //   26: astore_1
    //   27: aload_1
    //   28: ifnonnull +6 -> 34
    //   31: aload_0
    //   32: monitorexit
    //   33: return
    //   34: new 519	java/io/BufferedInputStream
    //   37: dup
    //   38: new 521	java/io/FileInputStream
    //   41: dup
    //   42: aload_1
    //   43: invokespecial 524	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   46: invokespecial 527	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   49: astore_3
    //   50: new 529	java/util/Properties
    //   53: dup
    //   54: invokespecial 530	java/util/Properties:<init>	()V
    //   57: astore 4
    //   59: aload 4
    //   61: aload_3
    //   62: invokevirtual 533	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   65: aload_0
    //   66: getfield 212	com/tencent/tbs/common/baseinfo/GodCmdWupManager:mCmdHistoryMap	Ljava/util/HashMap;
    //   69: invokevirtual 280	java/util/HashMap:entrySet	()Ljava/util/Set;
    //   72: invokeinterface 286 1 0
    //   77: astore 5
    //   79: aload 5
    //   81: invokeinterface 291 1 0
    //   86: ifeq +145 -> 231
    //   89: aload 5
    //   91: invokeinterface 295 1 0
    //   96: checkcast 297	java/util/Map$Entry
    //   99: astore 6
    //   101: aload 6
    //   103: invokeinterface 300 1 0
    //   108: checkcast 302	java/lang/String
    //   111: astore 7
    //   113: new 120	java/lang/StringBuilder
    //   116: dup
    //   117: invokespecial 121	java/lang/StringBuilder:<init>	()V
    //   120: astore 8
    //   122: aload 8
    //   124: ldc 101
    //   126: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   129: pop
    //   130: aload 8
    //   132: aload 6
    //   134: invokeinterface 305 1 0
    //   139: checkcast 302	java/lang/String
    //   142: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   145: pop
    //   146: aload 4
    //   148: aload 7
    //   150: aload 8
    //   152: invokevirtual 134	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   155: invokevirtual 578	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
    //   158: pop
    //   159: new 120	java/lang/StringBuilder
    //   162: dup
    //   163: invokespecial 121	java/lang/StringBuilder:<init>	()V
    //   166: astore 7
    //   168: aload 7
    //   170: ldc_w 580
    //   173: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   176: pop
    //   177: aload 7
    //   179: aload 6
    //   181: invokeinterface 300 1 0
    //   186: checkcast 302	java/lang/String
    //   189: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   192: pop
    //   193: aload 7
    //   195: ldc_w 500
    //   198: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   201: pop
    //   202: aload 7
    //   204: aload 6
    //   206: invokeinterface 305 1 0
    //   211: checkcast 302	java/lang/String
    //   214: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   217: pop
    //   218: ldc 65
    //   220: aload 7
    //   222: invokevirtual 134	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   225: invokestatic 140	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   228: goto -149 -> 79
    //   231: aload_0
    //   232: getfield 212	com/tencent/tbs/common/baseinfo/GodCmdWupManager:mCmdHistoryMap	Ljava/util/HashMap;
    //   235: invokevirtual 517	java/util/HashMap:clear	()V
    //   238: new 582	java/io/BufferedOutputStream
    //   241: dup
    //   242: new 584	java/io/FileOutputStream
    //   245: dup
    //   246: aload_1
    //   247: invokespecial 585	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   250: invokespecial 588	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   253: astore_1
    //   254: aload 4
    //   256: aload_1
    //   257: aconst_null
    //   258: invokevirtual 592	java/util/Properties:store	(Ljava/io/OutputStream;Ljava/lang/String;)V
    //   261: aload_3
    //   262: invokevirtual 554	java/io/BufferedInputStream:close	()V
    //   265: goto +8 -> 273
    //   268: astore_2
    //   269: aload_2
    //   270: invokevirtual 352	java/lang/Exception:printStackTrace	()V
    //   273: aload_1
    //   274: invokevirtual 593	java/io/BufferedOutputStream:close	()V
    //   277: goto +82 -> 359
    //   280: astore_1
    //   281: aload_1
    //   282: invokevirtual 352	java/lang/Exception:printStackTrace	()V
    //   285: goto +74 -> 359
    //   288: astore 4
    //   290: aload_1
    //   291: astore_2
    //   292: aload 4
    //   294: astore_1
    //   295: goto +74 -> 369
    //   298: astore_2
    //   299: goto +10 -> 309
    //   302: astore_1
    //   303: goto +66 -> 369
    //   306: astore_2
    //   307: aconst_null
    //   308: astore_1
    //   309: goto +15 -> 324
    //   312: astore_1
    //   313: aconst_null
    //   314: astore_3
    //   315: goto +54 -> 369
    //   318: astore_2
    //   319: aconst_null
    //   320: astore_1
    //   321: aload 4
    //   323: astore_3
    //   324: aload_2
    //   325: invokevirtual 559	java/lang/Throwable:printStackTrace	()V
    //   328: aload_3
    //   329: ifnull +15 -> 344
    //   332: aload_3
    //   333: invokevirtual 554	java/io/BufferedInputStream:close	()V
    //   336: goto +8 -> 344
    //   339: astore_2
    //   340: aload_2
    //   341: invokevirtual 352	java/lang/Exception:printStackTrace	()V
    //   344: aload_1
    //   345: ifnull +14 -> 359
    //   348: aload_1
    //   349: invokevirtual 593	java/io/BufferedOutputStream:close	()V
    //   352: goto +7 -> 359
    //   355: astore_1
    //   356: goto -75 -> 281
    //   359: aload_0
    //   360: monitorexit
    //   361: return
    //   362: astore 4
    //   364: aload_1
    //   365: astore_2
    //   366: aload 4
    //   368: astore_1
    //   369: aload_3
    //   370: ifnull +15 -> 385
    //   373: aload_3
    //   374: invokevirtual 554	java/io/BufferedInputStream:close	()V
    //   377: goto +8 -> 385
    //   380: astore_3
    //   381: aload_3
    //   382: invokevirtual 352	java/lang/Exception:printStackTrace	()V
    //   385: aload_2
    //   386: ifnull +15 -> 401
    //   389: aload_2
    //   390: invokevirtual 593	java/io/BufferedOutputStream:close	()V
    //   393: goto +8 -> 401
    //   396: astore_2
    //   397: aload_2
    //   398: invokevirtual 352	java/lang/Exception:printStackTrace	()V
    //   401: aload_1
    //   402: athrow
    //   403: astore_1
    //   404: aload_0
    //   405: monitorexit
    //   406: aload_1
    //   407: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	408	0	this	GodCmdWupManager
    //   26	248	1	localObject1	Object
    //   280	11	1	localException1	Exception
    //   294	1	1	localObject2	Object
    //   302	1	1	localObject3	Object
    //   308	1	1	localObject4	Object
    //   312	1	1	localObject5	Object
    //   320	29	1	localObject6	Object
    //   355	10	1	localException2	Exception
    //   368	34	1	localObject7	Object
    //   403	4	1	localObject8	Object
    //   11	1	2	localObject9	Object
    //   268	2	2	localException3	Exception
    //   291	1	2	localObject10	Object
    //   298	1	2	localThrowable1	Throwable
    //   306	1	2	localThrowable2	Throwable
    //   318	7	2	localThrowable3	Throwable
    //   339	2	2	localException4	Exception
    //   365	25	2	localObject11	Object
    //   396	2	2	localException5	Exception
    //   49	325	3	localObject12	Object
    //   380	2	3	localException6	Exception
    //   13	242	4	localProperties	java.util.Properties
    //   288	34	4	localObject13	Object
    //   362	5	4	localObject14	Object
    //   77	13	5	localIterator	Iterator
    //   99	106	6	localEntry	Map.Entry
    //   111	110	7	localObject15	Object
    //   120	31	8	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   261	265	268	java/lang/Exception
    //   273	277	280	java/lang/Exception
    //   254	261	288	finally
    //   254	261	298	java/lang/Throwable
    //   50	79	302	finally
    //   79	228	302	finally
    //   231	254	302	finally
    //   50	79	306	java/lang/Throwable
    //   79	228	306	java/lang/Throwable
    //   231	254	306	java/lang/Throwable
    //   15	27	312	finally
    //   34	50	312	finally
    //   15	27	318	java/lang/Throwable
    //   34	50	318	java/lang/Throwable
    //   332	336	339	java/lang/Exception
    //   348	352	355	java/lang/Exception
    //   324	328	362	finally
    //   373	377	380	java/lang/Exception
    //   389	393	396	java/lang/Exception
    //   2	10	403	finally
    //   261	265	403	finally
    //   269	273	403	finally
    //   273	277	403	finally
    //   281	285	403	finally
    //   332	336	403	finally
    //   340	344	403	finally
    //   348	352	403	finally
    //   373	377	403	finally
    //   381	385	403	finally
    //   389	393	403	finally
    //   397	401	403	finally
    //   401	403	403	finally
  }
  
  private static class InstanceHolder
  {
    private static final GodCmdWupManager INSTANCE = new GodCmdWupManager();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\GodCmdWupManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */