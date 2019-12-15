package com.tencent.tbs.common.stat;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.tencent.common.http.Apn;
import com.tencent.common.utils.FileUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.tbs.common.MTT.CommStatData;
import com.tencent.tbs.common.MTT.PerformanceStat;
import com.tencent.tbs.common.MTT.ReqRecord;
import com.tencent.tbs.common.MTT.STPV;
import com.tencent.tbs.common.MTT.STTotal;
import com.tencent.tbs.common.MTT.UserBehaviorPV;
import com.tencent.tbs.common.baseinfo.ICoreInfoFetcher;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.baseinfo.TbsUserInfo;
import com.tencent.tbs.common.baseinfo.TbsWupManager;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.tbs.common.utils.DeviceUtils;
import com.tencent.tbs.common.utils.QBUrlUtils;
import com.tencent.tbs.common.utils.TbsFileUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class TbsStatDataManager
{
  private static final int DEFAULT_ENTRY_TYPE = 0;
  private static final int DEFAULT_LOGIN_TYPE = 0;
  public static final int MAX_CACHED_DATA_SIZE = 10;
  public static final int MAX_NEED_UPLOAD_DATA_SIZE = 5;
  private static final int MAX_STAT_TIMES = 40;
  private static final String TAG = "TbsStatDataManager";
  public static final String WUP_STAT_DATA_VERSION = "000000";
  private static TbsStatDataManager mSimpleQBInstance;
  private static TbsStatDataManager mTBSInstance;
  Object loadLock = new Object();
  private boolean mCurrentDataAllNew = true;
  private WUPStatData mCurrentStat = null;
  private Object mCurrentStatLock = new byte[0];
  private boolean mForSimpleQB = false;
  private boolean mForceUploadFlag = false;
  private boolean mHasDataChangedSinceLastSave = false;
  private boolean mIsCollectStat = true;
  boolean mIsLoading = false;
  private ArrayList<WUPStatData> mPreStatList = null;
  private Object mPreStatLock = new byte[0];
  private int mStatTimes = 0;
  
  private TbsStatDataManager(boolean paramBoolean)
  {
    this.mForSimpleQB = paramBoolean;
    synchronized (this.mPreStatLock)
    {
      this.mPreStatList = new ArrayList();
      synchronized (this.mPreStatLock)
      {
        this.mPreStatList = new ArrayList();
        initData();
        return;
      }
    }
  }
  
  private PerformanceStat createOrGetPerformanceStat(String paramString1, byte paramByte, String paramString2, String paramString3)
  {
    String str1 = paramString3;
    if (paramString3.startsWith("Wlan")) {
      str1 = "Wlan";
    }
    Object localObject = TbsBaseModuleShell.getCoreInfoFetcher();
    paramString3 = paramString2;
    if (localObject != null)
    {
      paramString3 = paramString2;
      if (!((ICoreInfoFetcher)localObject).shouldUseQProxyAccordingToFlag(paramByte)) {
        paramString3 = "";
      }
    }
    String str2 = WUPStatData.getPerformanceStatKey(str1, paramByte, paramString3, paramString1);
    localObject = (PerformanceStat)getCurrentStatData().mPerformanceStat.get(str2);
    paramString2 = (String)localObject;
    if (localObject == null)
    {
      paramString2 = new PerformanceStat();
      paramString2.sUrl = paramString1;
      paramString2.sAPN = str1;
      paramString2.iProxyType = paramByte;
      paramString2.sProxyIP = paramString3;
      getCurrentStatData().mPerformanceStat.put(str2, paramString2);
    }
    paramString2.sDevice = DeviceUtils.getDeviceName();
    return paramString2;
  }
  
  private String getPerformanceStatKey(String paramString1, byte paramByte, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(paramString1);
    localStringBuffer.append('#');
    localStringBuffer.append(paramByte);
    localStringBuffer.append('#');
    localStringBuffer.append(paramString2);
    localStringBuffer.append(paramString3);
    return localStringBuffer.toString();
  }
  
  public static TbsStatDataManager getSimpleQBStatManager()
  {
    if (mSimpleQBInstance == null) {
      mSimpleQBInstance = new TbsStatDataManager(true);
    }
    return mSimpleQBInstance;
  }
  
  public static TbsStatDataManager getTBSStatManager()
  {
    if (mTBSInstance == null) {
      mTBSInstance = new TbsStatDataManager(false);
    }
    return mTBSInstance;
  }
  
  private void initData()
  {
    synchronized (this.mCurrentStatLock)
    {
      this.mCurrentStat = new WUPStatData();
      this.mCurrentStat.mTime = System.currentTimeMillis();
      this.mCurrentStat.mId = PublicSettingManager.getInstance().getWUPStatDataId(this.mForSimpleQB);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("init stat data, id=");
      localStringBuilder.append(this.mCurrentStat.mId);
      localStringBuilder.append("; isSimpleQB=");
      localStringBuilder.append(this.mForSimpleQB);
      LogUtils.d("TbsStatDataManager", localStringBuilder.toString());
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("init stat data, time=");
      localStringBuilder.append(this.mCurrentStat.mTime);
      localStringBuilder.append("; isSimpleQB=");
      localStringBuilder.append(this.mForSimpleQB);
      LogUtils.d("TbsStatDataManager", localStringBuilder.toString());
      return;
    }
  }
  
  private void setForceUpload(boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("setForceUpload called, force=");
    localStringBuilder.append(paramBoolean);
    localStringBuilder.append("; isSimpleQB=");
    localStringBuilder.append(this.mForSimpleQB);
    LogUtils.d("TbsStatDataManager", localStringBuilder.toString());
    this.mForceUploadFlag = paramBoolean;
  }
  
  private void stat(StatEntry paramStatEntry, boolean paramBoolean, int paramInt)
  {
    if (paramStatEntry != null)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("stat url:");
      localStringBuilder.append(paramStatEntry.url);
      localStringBuilder.append(",isRes:");
      localStringBuilder.append(paramStatEntry.isRes);
      localStringBuilder.append(",isWap:");
      localStringBuilder.append(paramStatEntry.isWap);
      localStringBuilder.append(",flow:");
      localStringBuilder.append(paramStatEntry.flow);
      localStringBuilder.append("; isSimpleQB=");
      localStringBuilder.append(this.mForSimpleQB);
      LogUtils.d("TbsStatDataManager", localStringBuilder.toString());
    }
    if (getCurrentStatData() != null)
    {
      if (paramStatEntry == null) {
        return;
      }
      if (PublicSettingManager.getInstance().getIsUploadPvinWup())
      {
        if ((!TextUtils.isEmpty(paramStatEntry.url)) || (!TextUtils.isEmpty(paramStatEntry.sUrl))) {
          statDomain(paramStatEntry, false);
        }
        LogUtils.d("TbsStatDataManager", "WUP Upload pv open");
      }
      else
      {
        LogUtils.d("TbsStatDataManager", "WUP Upload pv closed");
      }
      paramInt = this.mStatTimes;
      if (paramInt >= 40)
      {
        LogUtils.d("TbsStatDataManager", "mStatTime HAS REACHED 40, BEGIN SAVE!!!!");
        save();
        this.mStatTimes = 0;
        return;
      }
      this.mStatTimes = (paramInt + 1);
      return;
    }
  }
  
  private void statCommDataAddPv(CommStatData paramCommStatData)
  {
    if (paramCommStatData != null)
    {
      if (TextUtils.isEmpty(paramCommStatData.sStatKey)) {
        return;
      }
      CommStatData localCommStatData = (CommStatData)getCurrentStatData().mCommStatDatas.get(paramCommStatData.sStatKey);
      if (localCommStatData != null)
      {
        localCommStatData.iPv += paramCommStatData.iPv;
        return;
      }
      getCurrentStatData().mCommStatDatas.put(paramCommStatData.sStatKey, paramCommStatData);
      return;
    }
  }
  
  private void statDomain(StatEntry paramStatEntry, boolean paramBoolean)
  {
    if (paramStatEntry != null)
    {
      if (getCurrentStatData() == null) {
        return;
      }
      if (TextUtils.isEmpty(paramStatEntry.url)) {
        localObject1 = paramStatEntry.sUrl;
      } else {
        localObject1 = paramStatEntry.url;
      }
      String str = QBUrlUtils.getStatDomain((String)localObject1);
      Object localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("stat outer: topdomain=");
      ((StringBuilder)localObject1).append(str);
      ((StringBuilder)localObject1).append(", loginType=");
      ((StringBuilder)localObject1).append(0);
      ((StringBuilder)localObject1).append("; isSimpleQB=");
      ((StringBuilder)localObject1).append(this.mForSimpleQB);
      LogUtils.d("TbsStatDataManager", ((StringBuilder)localObject1).toString());
      if ((!paramStatEntry.isWup) && (!TextUtils.isEmpty(str)) && (getCurrentStatData() != null) && (!paramStatEntry.isError))
      {
        if (getCurrentStatData().mOuterPV == null) {
          getCurrentStatData().mOuterPV = new HashMap();
        }
        localObject1 = getCurrentStatData().mOuterPV;
        Object localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append(str);
        ((StringBuilder)localObject2).append("&");
        ((StringBuilder)localObject2).append(0);
        ((StringBuilder)localObject2).append("&");
        ((StringBuilder)localObject2).append(0);
        localObject2 = (STPV)((HashMap)localObject1).get(((StringBuilder)localObject2).toString());
        localObject1 = localObject2;
        if (localObject2 == null)
        {
          localObject1 = new STPV();
          ((STPV)localObject1).sDomain = str;
          ((STPV)localObject1).eEntryType = 0;
          ((STPV)localObject1).eLoginType = 0;
          localObject2 = getCurrentStatData().mOuterPV;
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append(str);
          localStringBuilder.append("&");
          localStringBuilder.append(0);
          localStringBuilder.append("&");
          localStringBuilder.append(0);
          ((HashMap)localObject2).put(localStringBuilder.toString(), localObject1);
        }
        if (!paramBoolean) {
          if (paramStatEntry.isRes)
          {
            ((STPV)localObject1).iResPv += 1;
          }
          else
          {
            ((STPV)localObject1).iWapPV += 1;
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("domainPV=");
            ((StringBuilder)localObject2).append(((STPV)localObject1).iWapPV);
            ((StringBuilder)localObject2).append("; isSimpleQB=");
            ((StringBuilder)localObject2).append(this.mForSimpleQB);
            LogUtils.d("TbsStatDataManager", ((StringBuilder)localObject2).toString());
          }
        }
        ((STPV)localObject1).iFlow += paramStatEntry.flow;
        return;
      }
      if (TextUtils.isEmpty(str))
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("stat domain top domain = null,sUrl:");
        ((StringBuilder)localObject1).append(paramStatEntry.sUrl);
        ((StringBuilder)localObject1).append(",url:");
        ((StringBuilder)localObject1).append(paramStatEntry.url);
        ((StringBuilder)localObject1).append("; isSimpleQB=");
        ((StringBuilder)localObject1).append(this.mForSimpleQB);
        LogUtils.d("TbsStatDataManager", ((StringBuilder)localObject1).toString());
      }
      if ((paramStatEntry != null) && (paramStatEntry.isError))
      {
        paramStatEntry = new StringBuilder();
        paramStatEntry.append("stat domain error pv; isSimpleQB=");
        paramStatEntry.append(this.mForSimpleQB);
        LogUtils.d("TbsStatDataManager", paramStatEntry.toString());
      }
      return;
    }
  }
  
  private void statTotal(StatEntry paramStatEntry, boolean paramBoolean)
  {
    if (paramStatEntry == null) {
      return;
    }
    String str = Apn.getApnName(paramStatEntry.apn);
    if (TextUtils.isEmpty(paramStatEntry.url)) {
      localObject1 = paramStatEntry.sUrl;
    } else {
      localObject1 = paramStatEntry.url;
    }
    Object localObject1 = QBUrlUtils.getStatDomain((String)localObject1);
    if ((!paramStatEntry.isWup) && (str != null) && (getCurrentStatData() != null) && (!TextUtils.isEmpty((CharSequence)localObject1)))
    {
      if (getCurrentStatData().mTotal == null) {
        getCurrentStatData().mTotal = new HashMap();
      }
      Object localObject2 = (STTotal)getCurrentStatData().mTotal.get(str);
      localObject1 = localObject2;
      if (localObject2 == null)
      {
        localObject1 = new STTotal();
        ((STTotal)localObject1).sAPN = str;
        getCurrentStatData().mTotal.put(str, localObject1);
      }
      if (paramStatEntry.isError)
      {
        ((STTotal)localObject1).iErrorPV += 1;
        return;
      }
      if (paramStatEntry.isRes)
      {
        if (!paramBoolean) {
          ((STTotal)localObject1).iResPv += 1;
        }
        ((STTotal)localObject1).iResFlow += paramStatEntry.flow;
        return;
      }
      if (!paramBoolean)
      {
        ((STTotal)localObject1).iWapPV += 1;
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("totalPv=");
        ((StringBuilder)localObject2).append(((STTotal)localObject1).iWapPV);
        ((StringBuilder)localObject2).append("; isSimpleQB=");
        ((StringBuilder)localObject2).append(this.mForSimpleQB);
        LogUtils.d("TbsStatDataManager", ((StringBuilder)localObject2).toString());
      }
      ((STTotal)localObject1).iWapFlow += paramStatEntry.flow;
      if (paramStatEntry.isProxy) {
        ((STTotal)localObject1).iProxyPV += 1;
      }
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("totalPv=");
      ((StringBuilder)localObject2).append(((STTotal)localObject1).iWapPV);
      ((StringBuilder)localObject2).append(", iProxyPV=");
      ((StringBuilder)localObject2).append(((STTotal)localObject1).iProxyPV);
      ((StringBuilder)localObject2).append(", url=");
      ((StringBuilder)localObject2).append(paramStatEntry.url);
      ((StringBuilder)localObject2).append("; isSimpleQB=");
      ((StringBuilder)localObject2).append(this.mForSimpleQB);
      LogUtils.d("TbsStatDataManagerpv-Report", ((StringBuilder)localObject2).toString());
    }
  }
  
  private void statUniqueCommData(CommStatData paramCommStatData)
  {
    getCurrentStatData().mCommStatDatas.put(paramCommStatData.sAppKey, paramCommStatData);
  }
  
  public void SaveCurrentAndMakeNewStatData()
  {
    if (!save())
    {
      ??? = new StringBuilder();
      ((StringBuilder)???).append("save data fail, put this stat data in memory; isSimpleQB=");
      ((StringBuilder)???).append(this.mForSimpleQB);
      LogUtils.d("TbsStatDataManager", ((StringBuilder)???).toString());
      return;
    }
    ??? = new StringBuilder();
    ((StringBuilder)???).append("save data succ, remve this stat data from memory; isSimpleQB=");
    ((StringBuilder)???).append(this.mForSimpleQB);
    LogUtils.d("TbsStatDataManager", ((StringBuilder)???).toString());
    synchronized (this.mPreStatLock)
    {
      this.mPreStatList.add(this.mCurrentStat.copy());
      initData();
      return;
    }
  }
  
  protected boolean checkShouldUpload()
  {
    synchronized (this.mPreStatLock)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("notify load stat data end; isSimpleQB=");
      localStringBuilder.append(this.mForSimpleQB);
      LogUtils.d("checkTesData", localStringBuilder.toString());
      if (TbsWupManager.getInstance().isNewDay())
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("this is a new day, check weather has any stat data; isSimpleQB=");
        localStringBuilder.append(this.mForSimpleQB);
        LogUtils.d("checkTesData", localStringBuilder.toString());
        if (((this.mPreStatList != null) && (this.mPreStatList.size() > 0)) || (!this.mCurrentDataAllNew))
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("this is a new day, and we have stat data, upload; isSimpleQB=");
          localStringBuilder.append(this.mForSimpleQB);
          LogUtils.d("checkTesData", localStringBuilder.toString());
          return true;
        }
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("this is a new day, but we do not have any stat data, return, do nothing; isSimpleQB=");
        localStringBuilder.append(this.mForSimpleQB);
        LogUtils.d("checkTesData", localStringBuilder.toString());
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("this is not a new day since last boot, check if we have got 5 stat files; isSimpleQB=");
      localStringBuilder.append(this.mForSimpleQB);
      LogUtils.d("checkTesData", localStringBuilder.toString());
      if ((this.mPreStatList != null) && (this.mPreStatList.size() >= 5))
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("this is not a new day since last boot, Oh, yes, we have got ");
        localStringBuilder.append(this.mPreStatList.size());
        localStringBuilder.append(" stat files, upload; isSimpleQB=");
        localStringBuilder.append(this.mForSimpleQB);
        LogUtils.d("checkTesData", localStringBuilder.toString());
        return true;
      }
      int i;
      if (this.mPreStatList == null) {
        i = 0;
      } else {
        i = this.mPreStatList.size();
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("this is not a new day since last boot, but we just have got ");
      localStringBuilder.append(i);
      localStringBuilder.append(" stat files, return, do nothing; isSimpleQB=");
      localStringBuilder.append(this.mForSimpleQB);
      LogUtils.d("checkTesData", localStringBuilder.toString());
      return false;
    }
  }
  
  public void deleteFileByID(int paramInt)
  {
    synchronized (this.mPreStatLock)
    {
      Object localObject2 = this.mPreStatList.iterator();
      while (((Iterator)localObject2).hasNext())
      {
        WUPStatData localWUPStatData = (WUPStatData)((Iterator)localObject2).next();
        if ((localWUPStatData != null) && (localWUPStatData.mId == paramInt)) {
          ((Iterator)localObject2).remove();
        }
      }
      ??? = TbsFileUtils.getStatFile(paramInt % 10, this.mForSimpleQB);
      if ((??? != null) && (((File)???).exists()))
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("delete file ");
        ((StringBuilder)localObject2).append(((File)???).getAbsolutePath());
        LogUtils.d("TbsStatDataManager", ((StringBuilder)localObject2).toString());
        FileUtils.deleteQuietly((File)???);
      }
      return;
    }
  }
  
  public WUPStatData getCurrentStatData()
  {
    synchronized (this.mCurrentStatLock)
    {
      if (this.mCurrentStat == null) {
        initData();
      }
      this.mCurrentDataAllNew = false;
      this.mHasDataChangedSinceLastSave = true;
      return this.mCurrentStat;
    }
  }
  
  public int getCurrentStatId()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("get current id; isSimpleQB=");
    ((StringBuilder)localObject).append(this.mForSimpleQB);
    LogUtils.d("TbsStatDataManager", ((StringBuilder)localObject).toString());
    localObject = this.mCurrentStat;
    if (localObject != null) {
      return ((WUPStatData)localObject).mId;
    }
    return 0;
  }
  
  public ArrayList<WUPStatData> getPreData()
  {
    synchronized (this.mPreStatLock)
    {
      ArrayList localArrayList = new ArrayList(this.mPreStatList);
      return localArrayList;
    }
  }
  
  public boolean isStatPerformance()
  {
    return (TbsUserInfo.getInstance().getStatState() & 0x100) == 256;
  }
  
  public void load()
  {
    ??? = new StringBuilder();
    ((StringBuilder)???).append("load file; isSimpleQB=");
    ((StringBuilder)???).append(this.mForSimpleQB);
    LogUtils.d("TbsStatDataManager", ((StringBuilder)???).toString());
    synchronized (this.loadLock)
    {
      if (this.mIsLoading)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("we have load data already, ignore this request; isSimpleQB=");
        localStringBuilder.append(this.mForSimpleQB);
        LogUtils.d("loadDatTag", localStringBuilder.toString());
        return;
      }
      this.mIsLoading = true;
      StatWorkerThread.getInstance().post(new Runnable()
      {
        public void run()
        {
          TbsStatDataManager.this.loadDataFromFile();
          TbsStatDataManager.this.notifyLoadEnd();
        }
      });
      return;
    }
  }
  
  /* Error */
  protected int loadDataFromFile()
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore 5
    //   3: iconst_0
    //   4: istore_1
    //   5: iload 5
    //   7: bipush 10
    //   9: if_icmpge +2459 -> 2468
    //   12: iload 5
    //   14: aload_0
    //   15: getfield 81	com/tencent/tbs/common/stat/TbsStatDataManager:mForSimpleQB	Z
    //   18: invokestatic 482	com/tencent/tbs/common/utils/TbsFileUtils:getStatFile	(IZ)Ljava/io/File;
    //   21: astore 8
    //   23: aload 8
    //   25: ifnull +2379 -> 2404
    //   28: aload 8
    //   30: invokevirtual 487	java/io/File:exists	()Z
    //   33: ifne +6 -> 39
    //   36: goto +2368 -> 2404
    //   39: new 545	java/io/DataInputStream
    //   42: dup
    //   43: new 547	java/io/BufferedInputStream
    //   46: dup
    //   47: aload 8
    //   49: invokestatic 551	com/tencent/common/utils/FileUtils:openInputStream	(Ljava/io/File;)Ljava/io/FileInputStream;
    //   52: invokespecial 554	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   55: invokespecial 555	java/io/DataInputStream:<init>	(Ljava/io/InputStream;)V
    //   58: astore 9
    //   60: iload_1
    //   61: istore_3
    //   62: iload_1
    //   63: istore_2
    //   64: iload_1
    //   65: istore 4
    //   67: aload 9
    //   69: astore 8
    //   71: new 130	com/tencent/tbs/common/stat/WUPStatData
    //   74: dup
    //   75: invokespecial 203	com/tencent/tbs/common/stat/WUPStatData:<init>	()V
    //   78: astore 11
    //   80: iload_1
    //   81: istore_3
    //   82: iload_1
    //   83: istore_2
    //   84: iload_1
    //   85: istore 4
    //   87: aload 9
    //   89: astore 8
    //   91: aload 11
    //   93: aload 9
    //   95: invokevirtual 558	java/io/DataInputStream:readUTF	()Ljava/lang/String;
    //   98: putfield 561	com/tencent/tbs/common/stat/WUPStatData:version	Ljava/lang/String;
    //   101: iload_1
    //   102: istore_3
    //   103: iload_1
    //   104: istore_2
    //   105: iload_1
    //   106: istore 4
    //   108: aload 9
    //   110: astore 8
    //   112: ldc 32
    //   114: aload 11
    //   116: getfield 561	com/tencent/tbs/common/stat/WUPStatData:version	Ljava/lang/String;
    //   119: invokevirtual 564	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   122: ifne +200 -> 322
    //   125: iload_1
    //   126: istore_3
    //   127: iload_1
    //   128: istore_2
    //   129: iload_1
    //   130: istore 4
    //   132: aload 9
    //   134: astore 8
    //   136: new 228	java/lang/StringBuilder
    //   139: dup
    //   140: invokespecial 229	java/lang/StringBuilder:<init>	()V
    //   143: astore 10
    //   145: iload_1
    //   146: istore_3
    //   147: iload_1
    //   148: istore_2
    //   149: iload_1
    //   150: istore 4
    //   152: aload 9
    //   154: astore 8
    //   156: aload 10
    //   158: ldc_w 566
    //   161: invokevirtual 234	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   164: pop
    //   165: iload_1
    //   166: istore_3
    //   167: iload_1
    //   168: istore_2
    //   169: iload_1
    //   170: istore 4
    //   172: aload 9
    //   174: astore 8
    //   176: aload 10
    //   178: iload 5
    //   180: invokevirtual 237	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   183: pop
    //   184: iload_1
    //   185: istore_3
    //   186: iload_1
    //   187: istore_2
    //   188: iload_1
    //   189: istore 4
    //   191: aload 9
    //   193: astore 8
    //   195: aload 10
    //   197: ldc_w 568
    //   200: invokevirtual 234	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   203: pop
    //   204: iload_1
    //   205: istore_3
    //   206: iload_1
    //   207: istore_2
    //   208: iload_1
    //   209: istore 4
    //   211: aload 9
    //   213: astore 8
    //   215: aload 10
    //   217: aload 11
    //   219: getfield 561	com/tencent/tbs/common/stat/WUPStatData:version	Ljava/lang/String;
    //   222: invokevirtual 234	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   225: pop
    //   226: iload_1
    //   227: istore_3
    //   228: iload_1
    //   229: istore_2
    //   230: iload_1
    //   231: istore 4
    //   233: aload 9
    //   235: astore 8
    //   237: aload 10
    //   239: ldc -17
    //   241: invokevirtual 234	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   244: pop
    //   245: iload_1
    //   246: istore_3
    //   247: iload_1
    //   248: istore_2
    //   249: iload_1
    //   250: istore 4
    //   252: aload 9
    //   254: astore 8
    //   256: aload 10
    //   258: aload_0
    //   259: getfield 81	com/tencent/tbs/common/stat/TbsStatDataManager:mForSimpleQB	Z
    //   262: invokevirtual 242	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   265: pop
    //   266: iload_1
    //   267: istore_3
    //   268: iload_1
    //   269: istore_2
    //   270: iload_1
    //   271: istore 4
    //   273: aload 9
    //   275: astore 8
    //   277: ldc 29
    //   279: aload 10
    //   281: invokevirtual 243	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   284: invokestatic 249	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   287: iload_1
    //   288: istore_3
    //   289: iload_1
    //   290: istore_2
    //   291: iload_1
    //   292: istore 4
    //   294: aload 9
    //   296: astore 8
    //   298: aload_0
    //   299: iload 5
    //   301: invokevirtual 570	com/tencent/tbs/common/stat/TbsStatDataManager:deleteFileByID	(I)V
    //   304: aload 9
    //   306: invokevirtual 573	java/io/DataInputStream:close	()V
    //   309: goto +2150 -> 2459
    //   312: astore 8
    //   314: aload 8
    //   316: invokevirtual 576	java/io/IOException:printStackTrace	()V
    //   319: goto +2140 -> 2459
    //   322: iload_1
    //   323: istore_3
    //   324: iload_1
    //   325: istore_2
    //   326: iload_1
    //   327: istore 4
    //   329: aload 9
    //   331: astore 8
    //   333: aload 11
    //   335: aload 9
    //   337: invokevirtual 579	java/io/DataInputStream:readInt	()I
    //   340: putfield 226	com/tencent/tbs/common/stat/WUPStatData:mId	I
    //   343: iload_1
    //   344: istore_3
    //   345: iload_1
    //   346: istore_2
    //   347: iload_1
    //   348: istore 4
    //   350: aload 9
    //   352: astore 8
    //   354: aload 11
    //   356: aload 9
    //   358: invokevirtual 582	java/io/DataInputStream:readLong	()J
    //   361: putfield 213	com/tencent/tbs/common/stat/WUPStatData:mTime	J
    //   364: iload_1
    //   365: istore_3
    //   366: iload_1
    //   367: istore_2
    //   368: iload_1
    //   369: istore 4
    //   371: aload 9
    //   373: astore 8
    //   375: aload 9
    //   377: invokevirtual 586	java/io/DataInputStream:readShort	()S
    //   380: istore 7
    //   382: iconst_0
    //   383: istore 6
    //   385: iload 6
    //   387: iload 7
    //   389: if_icmpge +142 -> 531
    //   392: iload_1
    //   393: istore_3
    //   394: iload_1
    //   395: istore_2
    //   396: iload_1
    //   397: istore 4
    //   399: aload 9
    //   401: astore 8
    //   403: aload 9
    //   405: aload 9
    //   407: invokevirtual 586	java/io/DataInputStream:readShort	()S
    //   410: invokestatic 590	com/tencent/common/utils/FileUtils:read	(Ljava/io/InputStream;I)Ljava/nio/ByteBuffer;
    //   413: astore 10
    //   415: iload_1
    //   416: istore_3
    //   417: iload_1
    //   418: istore_2
    //   419: iload_1
    //   420: istore 4
    //   422: aload 9
    //   424: astore 8
    //   426: new 592	com/taf/JceInputStream
    //   429: dup
    //   430: aload 10
    //   432: invokespecial 595	com/taf/JceInputStream:<init>	(Ljava/nio/ByteBuffer;)V
    //   435: astore 12
    //   437: iload_1
    //   438: istore_3
    //   439: iload_1
    //   440: istore_2
    //   441: iload_1
    //   442: istore 4
    //   444: aload 9
    //   446: astore 8
    //   448: new 382	com/tencent/tbs/common/MTT/STTotal
    //   451: dup
    //   452: invokespecial 383	com/tencent/tbs/common/MTT/STTotal:<init>	()V
    //   455: astore 13
    //   457: iload_1
    //   458: istore_3
    //   459: iload_1
    //   460: istore_2
    //   461: iload_1
    //   462: istore 4
    //   464: aload 9
    //   466: astore 8
    //   468: aload 13
    //   470: aload 12
    //   472: invokevirtual 599	com/tencent/tbs/common/MTT/STTotal:readFrom	(Lcom/taf/JceInputStream;)V
    //   475: iload_1
    //   476: istore_3
    //   477: iload_1
    //   478: istore_2
    //   479: iload_1
    //   480: istore 4
    //   482: aload 9
    //   484: astore 8
    //   486: aload 11
    //   488: getfield 380	com/tencent/tbs/common/stat/WUPStatData:mTotal	Ljava/util/HashMap;
    //   491: aload 13
    //   493: getfield 384	com/tencent/tbs/common/MTT/STTotal:sAPN	Ljava/lang/String;
    //   496: aload 13
    //   498: invokevirtual 168	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   501: pop
    //   502: iload_1
    //   503: istore_3
    //   504: iload_1
    //   505: istore_2
    //   506: iload_1
    //   507: istore 4
    //   509: aload 9
    //   511: astore 8
    //   513: invokestatic 602	com/tencent/common/utils/FileUtils:getInstance	()Lcom/tencent/common/utils/FileUtils;
    //   516: aload 10
    //   518: invokevirtual 606	com/tencent/common/utils/FileUtils:releaseByteBuffer	(Ljava/nio/ByteBuffer;)Z
    //   521: pop
    //   522: iload 6
    //   524: iconst_1
    //   525: iadd
    //   526: istore 6
    //   528: goto -143 -> 385
    //   531: iload_1
    //   532: istore_3
    //   533: iload_1
    //   534: istore_2
    //   535: iload_1
    //   536: istore 4
    //   538: aload 9
    //   540: astore 8
    //   542: aload 9
    //   544: invokevirtual 586	java/io/DataInputStream:readShort	()S
    //   547: istore 7
    //   549: iconst_0
    //   550: istore 6
    //   552: iload 6
    //   554: iload 7
    //   556: if_icmpge +283 -> 839
    //   559: iload_1
    //   560: istore_3
    //   561: iload_1
    //   562: istore_2
    //   563: iload_1
    //   564: istore 4
    //   566: aload 9
    //   568: astore 8
    //   570: aload 9
    //   572: aload 9
    //   574: invokevirtual 586	java/io/DataInputStream:readShort	()S
    //   577: invokestatic 590	com/tencent/common/utils/FileUtils:read	(Ljava/io/InputStream;I)Ljava/nio/ByteBuffer;
    //   580: astore 10
    //   582: iload_1
    //   583: istore_3
    //   584: iload_1
    //   585: istore_2
    //   586: iload_1
    //   587: istore 4
    //   589: aload 9
    //   591: astore 8
    //   593: new 592	com/taf/JceInputStream
    //   596: dup
    //   597: aload 10
    //   599: invokespecial 595	com/taf/JceInputStream:<init>	(Ljava/nio/ByteBuffer;)V
    //   602: astore 13
    //   604: iload_1
    //   605: istore_3
    //   606: iload_1
    //   607: istore_2
    //   608: iload_1
    //   609: istore 4
    //   611: aload 9
    //   613: astore 8
    //   615: new 340	com/tencent/tbs/common/MTT/STPV
    //   618: dup
    //   619: invokespecial 341	com/tencent/tbs/common/MTT/STPV:<init>	()V
    //   622: astore 12
    //   624: iload_1
    //   625: istore_3
    //   626: iload_1
    //   627: istore_2
    //   628: iload_1
    //   629: istore 4
    //   631: aload 9
    //   633: astore 8
    //   635: aload 12
    //   637: aload 13
    //   639: invokevirtual 607	com/tencent/tbs/common/MTT/STPV:readFrom	(Lcom/taf/JceInputStream;)V
    //   642: iload_1
    //   643: istore_3
    //   644: iload_1
    //   645: istore_2
    //   646: iload_1
    //   647: istore 4
    //   649: aload 9
    //   651: astore 8
    //   653: aload 11
    //   655: getfield 335	com/tencent/tbs/common/stat/WUPStatData:mOuterPV	Ljava/util/HashMap;
    //   658: astore 13
    //   660: iload_1
    //   661: istore_3
    //   662: iload_1
    //   663: istore_2
    //   664: iload_1
    //   665: istore 4
    //   667: aload 9
    //   669: astore 8
    //   671: new 228	java/lang/StringBuilder
    //   674: dup
    //   675: invokespecial 229	java/lang/StringBuilder:<init>	()V
    //   678: astore 14
    //   680: iload_1
    //   681: istore_3
    //   682: iload_1
    //   683: istore_2
    //   684: iload_1
    //   685: istore 4
    //   687: aload 9
    //   689: astore 8
    //   691: aload 14
    //   693: aload 12
    //   695: getfield 344	com/tencent/tbs/common/MTT/STPV:sDomain	Ljava/lang/String;
    //   698: invokevirtual 234	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   701: pop
    //   702: iload_1
    //   703: istore_3
    //   704: iload_1
    //   705: istore_2
    //   706: iload_1
    //   707: istore 4
    //   709: aload 9
    //   711: astore 8
    //   713: aload 14
    //   715: ldc_w 338
    //   718: invokevirtual 234	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   721: pop
    //   722: iload_1
    //   723: istore_3
    //   724: iload_1
    //   725: istore_2
    //   726: iload_1
    //   727: istore 4
    //   729: aload 9
    //   731: astore 8
    //   733: aload 14
    //   735: aload 12
    //   737: getfield 347	com/tencent/tbs/common/MTT/STPV:eEntryType	I
    //   740: invokevirtual 237	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   743: pop
    //   744: iload_1
    //   745: istore_3
    //   746: iload_1
    //   747: istore_2
    //   748: iload_1
    //   749: istore 4
    //   751: aload 9
    //   753: astore 8
    //   755: aload 14
    //   757: ldc_w 338
    //   760: invokevirtual 234	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   763: pop
    //   764: iload_1
    //   765: istore_3
    //   766: iload_1
    //   767: istore_2
    //   768: iload_1
    //   769: istore 4
    //   771: aload 9
    //   773: astore 8
    //   775: aload 14
    //   777: aload 12
    //   779: getfield 350	com/tencent/tbs/common/MTT/STPV:eLoginType	I
    //   782: invokevirtual 237	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   785: pop
    //   786: iload_1
    //   787: istore_3
    //   788: iload_1
    //   789: istore_2
    //   790: iload_1
    //   791: istore 4
    //   793: aload 9
    //   795: astore 8
    //   797: aload 13
    //   799: aload 14
    //   801: invokevirtual 243	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   804: aload 12
    //   806: invokevirtual 168	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   809: pop
    //   810: iload_1
    //   811: istore_3
    //   812: iload_1
    //   813: istore_2
    //   814: iload_1
    //   815: istore 4
    //   817: aload 9
    //   819: astore 8
    //   821: invokestatic 602	com/tencent/common/utils/FileUtils:getInstance	()Lcom/tencent/common/utils/FileUtils;
    //   824: aload 10
    //   826: invokevirtual 606	com/tencent/common/utils/FileUtils:releaseByteBuffer	(Ljava/nio/ByteBuffer;)Z
    //   829: pop
    //   830: iload 6
    //   832: iconst_1
    //   833: iadd
    //   834: istore 6
    //   836: goto -284 -> 552
    //   839: iload_1
    //   840: istore_3
    //   841: iload_1
    //   842: istore_2
    //   843: iload_1
    //   844: istore 4
    //   846: aload 9
    //   848: astore 8
    //   850: aload 9
    //   852: invokevirtual 586	java/io/DataInputStream:readShort	()S
    //   855: istore 7
    //   857: iconst_0
    //   858: istore 6
    //   860: iload 6
    //   862: iload 7
    //   864: if_icmpge +198 -> 1062
    //   867: iload_1
    //   868: istore_3
    //   869: iload_1
    //   870: istore_2
    //   871: iload_1
    //   872: istore 4
    //   874: aload 9
    //   876: astore 8
    //   878: aload 9
    //   880: aload 9
    //   882: invokevirtual 586	java/io/DataInputStream:readShort	()S
    //   885: invokestatic 590	com/tencent/common/utils/FileUtils:read	(Ljava/io/InputStream;I)Ljava/nio/ByteBuffer;
    //   888: astore 12
    //   890: iload_1
    //   891: istore_3
    //   892: iload_1
    //   893: istore_2
    //   894: iload_1
    //   895: istore 4
    //   897: aload 9
    //   899: astore 8
    //   901: new 592	com/taf/JceInputStream
    //   904: dup
    //   905: aload 12
    //   907: invokespecial 595	com/taf/JceInputStream:<init>	(Ljava/nio/ByteBuffer;)V
    //   910: astore 10
    //   912: iload_1
    //   913: istore_3
    //   914: iload_1
    //   915: istore_2
    //   916: iload_1
    //   917: istore 4
    //   919: aload 9
    //   921: astore 8
    //   923: new 609	com/tencent/tbs/common/MTT/UserBehaviorPV
    //   926: dup
    //   927: invokespecial 610	com/tencent/tbs/common/MTT/UserBehaviorPV:<init>	()V
    //   930: astore 13
    //   932: iload_1
    //   933: istore_3
    //   934: iload_1
    //   935: istore_2
    //   936: iload_1
    //   937: istore 4
    //   939: aload 9
    //   941: astore 8
    //   943: aload 13
    //   945: aload 10
    //   947: invokevirtual 611	com/tencent/tbs/common/MTT/UserBehaviorPV:readFrom	(Lcom/taf/JceInputStream;)V
    //   950: iload_1
    //   951: istore_3
    //   952: iload_1
    //   953: istore_2
    //   954: iload_1
    //   955: istore 4
    //   957: aload 9
    //   959: astore 8
    //   961: aload 13
    //   963: getfield 614	com/tencent/tbs/common/MTT/UserBehaviorPV:mBehaviorType	I
    //   966: invokestatic 618	java/lang/Integer:toString	(I)Ljava/lang/String;
    //   969: astore 10
    //   971: iload_1
    //   972: istore_3
    //   973: iload_1
    //   974: istore_2
    //   975: iload_1
    //   976: istore 4
    //   978: aload 9
    //   980: astore 8
    //   982: iconst_m1
    //   983: aload 13
    //   985: getfield 614	com/tencent/tbs/common/MTT/UserBehaviorPV:mBehaviorType	I
    //   988: if_icmpne +21 -> 1009
    //   991: iload_1
    //   992: istore_3
    //   993: iload_1
    //   994: istore_2
    //   995: iload_1
    //   996: istore 4
    //   998: aload 9
    //   1000: astore 8
    //   1002: aload 13
    //   1004: getfield 621	com/tencent/tbs/common/MTT/UserBehaviorPV:mBehaviorAction	Ljava/lang/String;
    //   1007: astore 10
    //   1009: iload_1
    //   1010: istore_3
    //   1011: iload_1
    //   1012: istore_2
    //   1013: iload_1
    //   1014: istore 4
    //   1016: aload 9
    //   1018: astore 8
    //   1020: aload 11
    //   1022: getfield 624	com/tencent/tbs/common/stat/WUPStatData:mUserBehaviorPV	Ljava/util/HashMap;
    //   1025: aload 10
    //   1027: aload 13
    //   1029: invokevirtual 168	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1032: pop
    //   1033: iload_1
    //   1034: istore_3
    //   1035: iload_1
    //   1036: istore_2
    //   1037: iload_1
    //   1038: istore 4
    //   1040: aload 9
    //   1042: astore 8
    //   1044: invokestatic 602	com/tencent/common/utils/FileUtils:getInstance	()Lcom/tencent/common/utils/FileUtils;
    //   1047: aload 12
    //   1049: invokevirtual 606	com/tencent/common/utils/FileUtils:releaseByteBuffer	(Ljava/nio/ByteBuffer;)Z
    //   1052: pop
    //   1053: iload 6
    //   1055: iconst_1
    //   1056: iadd
    //   1057: istore 6
    //   1059: goto -199 -> 860
    //   1062: iload_1
    //   1063: istore_3
    //   1064: iload_1
    //   1065: istore_2
    //   1066: iload_1
    //   1067: istore 4
    //   1069: aload 9
    //   1071: astore 8
    //   1073: aload 9
    //   1075: invokevirtual 586	java/io/DataInputStream:readShort	()S
    //   1078: istore 7
    //   1080: iload_1
    //   1081: istore_3
    //   1082: iload_1
    //   1083: istore_2
    //   1084: iload_1
    //   1085: istore 4
    //   1087: aload 9
    //   1089: astore 8
    //   1091: new 228	java/lang/StringBuilder
    //   1094: dup
    //   1095: invokespecial 229	java/lang/StringBuilder:<init>	()V
    //   1098: astore 10
    //   1100: iload_1
    //   1101: istore_3
    //   1102: iload_1
    //   1103: istore_2
    //   1104: iload_1
    //   1105: istore 4
    //   1107: aload 9
    //   1109: astore 8
    //   1111: aload 10
    //   1113: ldc_w 626
    //   1116: invokevirtual 234	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1119: pop
    //   1120: iload_1
    //   1121: istore_3
    //   1122: iload_1
    //   1123: istore_2
    //   1124: iload_1
    //   1125: istore 4
    //   1127: aload 9
    //   1129: astore 8
    //   1131: aload 10
    //   1133: iload 7
    //   1135: invokevirtual 237	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1138: pop
    //   1139: iload_1
    //   1140: istore_3
    //   1141: iload_1
    //   1142: istore_2
    //   1143: iload_1
    //   1144: istore 4
    //   1146: aload 9
    //   1148: astore 8
    //   1150: aload 10
    //   1152: ldc -17
    //   1154: invokevirtual 234	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1157: pop
    //   1158: iload_1
    //   1159: istore_3
    //   1160: iload_1
    //   1161: istore_2
    //   1162: iload_1
    //   1163: istore 4
    //   1165: aload 9
    //   1167: astore 8
    //   1169: aload 10
    //   1171: aload_0
    //   1172: getfield 81	com/tencent/tbs/common/stat/TbsStatDataManager:mForSimpleQB	Z
    //   1175: invokevirtual 242	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   1178: pop
    //   1179: iload_1
    //   1180: istore_3
    //   1181: iload_1
    //   1182: istore_2
    //   1183: iload_1
    //   1184: istore 4
    //   1186: aload 9
    //   1188: astore 8
    //   1190: ldc_w 628
    //   1193: aload 10
    //   1195: invokevirtual 243	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1198: invokestatic 249	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   1201: iconst_0
    //   1202: istore 6
    //   1204: iload 6
    //   1206: iload 7
    //   1208: if_icmpge +284 -> 1492
    //   1211: iload_1
    //   1212: istore_3
    //   1213: iload_1
    //   1214: istore_2
    //   1215: iload_1
    //   1216: istore 4
    //   1218: aload 9
    //   1220: astore 8
    //   1222: aload 9
    //   1224: aload 9
    //   1226: invokevirtual 586	java/io/DataInputStream:readShort	()S
    //   1229: invokestatic 590	com/tencent/common/utils/FileUtils:read	(Ljava/io/InputStream;I)Ljava/nio/ByteBuffer;
    //   1232: astore 10
    //   1234: iload_1
    //   1235: istore_3
    //   1236: iload_1
    //   1237: istore_2
    //   1238: iload_1
    //   1239: istore 4
    //   1241: aload 9
    //   1243: astore 8
    //   1245: new 150	com/tencent/tbs/common/MTT/PerformanceStat
    //   1248: dup
    //   1249: invokespecial 151	com/tencent/tbs/common/MTT/PerformanceStat:<init>	()V
    //   1252: astore 12
    //   1254: iload_1
    //   1255: istore_3
    //   1256: iload_1
    //   1257: istore_2
    //   1258: iload_1
    //   1259: istore 4
    //   1261: aload 9
    //   1263: astore 8
    //   1265: aload 12
    //   1267: aload 10
    //   1269: invokevirtual 634	java/nio/ByteBuffer:array	()[B
    //   1272: iconst_0
    //   1273: aload 10
    //   1275: invokevirtual 637	java/nio/ByteBuffer:position	()I
    //   1278: invokevirtual 641	com/tencent/tbs/common/MTT/PerformanceStat:deserialize	([BII)V
    //   1281: iload_1
    //   1282: istore_3
    //   1283: iload_1
    //   1284: istore_2
    //   1285: iload_1
    //   1286: istore 4
    //   1288: aload 9
    //   1290: astore 8
    //   1292: new 228	java/lang/StringBuilder
    //   1295: dup
    //   1296: invokespecial 229	java/lang/StringBuilder:<init>	()V
    //   1299: astore 13
    //   1301: iload_1
    //   1302: istore_3
    //   1303: iload_1
    //   1304: istore_2
    //   1305: iload_1
    //   1306: istore 4
    //   1308: aload 9
    //   1310: astore 8
    //   1312: aload 13
    //   1314: ldc_w 643
    //   1317: invokevirtual 234	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1320: pop
    //   1321: iload_1
    //   1322: istore_3
    //   1323: iload_1
    //   1324: istore_2
    //   1325: iload_1
    //   1326: istore 4
    //   1328: aload 9
    //   1330: astore 8
    //   1332: aload 13
    //   1334: aload 12
    //   1336: invokevirtual 646	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1339: pop
    //   1340: iload_1
    //   1341: istore_3
    //   1342: iload_1
    //   1343: istore_2
    //   1344: iload_1
    //   1345: istore 4
    //   1347: aload 9
    //   1349: astore 8
    //   1351: aload 13
    //   1353: ldc -17
    //   1355: invokevirtual 234	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1358: pop
    //   1359: iload_1
    //   1360: istore_3
    //   1361: iload_1
    //   1362: istore_2
    //   1363: iload_1
    //   1364: istore 4
    //   1366: aload 9
    //   1368: astore 8
    //   1370: aload 13
    //   1372: aload_0
    //   1373: getfield 81	com/tencent/tbs/common/stat/TbsStatDataManager:mForSimpleQB	Z
    //   1376: invokevirtual 242	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   1379: pop
    //   1380: iload_1
    //   1381: istore_3
    //   1382: iload_1
    //   1383: istore_2
    //   1384: iload_1
    //   1385: istore 4
    //   1387: aload 9
    //   1389: astore 8
    //   1391: ldc_w 628
    //   1394: aload 13
    //   1396: invokevirtual 243	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1399: invokestatic 249	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   1402: iload_1
    //   1403: istore_3
    //   1404: iload_1
    //   1405: istore_2
    //   1406: iload_1
    //   1407: istore 4
    //   1409: aload 9
    //   1411: astore 8
    //   1413: aload_0
    //   1414: aload 12
    //   1416: getfield 157	com/tencent/tbs/common/MTT/PerformanceStat:sAPN	Ljava/lang/String;
    //   1419: aload 12
    //   1421: getfield 161	com/tencent/tbs/common/MTT/PerformanceStat:iProxyType	B
    //   1424: aload 12
    //   1426: getfield 164	com/tencent/tbs/common/MTT/PerformanceStat:sProxyIP	Ljava/lang/String;
    //   1429: aload 12
    //   1431: getfield 154	com/tencent/tbs/common/MTT/PerformanceStat:sUrl	Ljava/lang/String;
    //   1434: invokespecial 647	com/tencent/tbs/common/stat/TbsStatDataManager:getPerformanceStatKey	(Ljava/lang/String;BLjava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   1437: astore 13
    //   1439: iload_1
    //   1440: istore_3
    //   1441: iload_1
    //   1442: istore_2
    //   1443: iload_1
    //   1444: istore 4
    //   1446: aload 9
    //   1448: astore 8
    //   1450: aload 11
    //   1452: getfield 142	com/tencent/tbs/common/stat/WUPStatData:mPerformanceStat	Ljava/util/HashMap;
    //   1455: aload 13
    //   1457: aload 12
    //   1459: invokevirtual 168	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1462: pop
    //   1463: iload_1
    //   1464: istore_3
    //   1465: iload_1
    //   1466: istore_2
    //   1467: iload_1
    //   1468: istore 4
    //   1470: aload 9
    //   1472: astore 8
    //   1474: invokestatic 602	com/tencent/common/utils/FileUtils:getInstance	()Lcom/tencent/common/utils/FileUtils;
    //   1477: aload 10
    //   1479: invokevirtual 606	com/tencent/common/utils/FileUtils:releaseByteBuffer	(Ljava/nio/ByteBuffer;)Z
    //   1482: pop
    //   1483: iload 6
    //   1485: iconst_1
    //   1486: iadd
    //   1487: istore 6
    //   1489: goto -285 -> 1204
    //   1492: iload_1
    //   1493: istore_3
    //   1494: iload_1
    //   1495: istore_2
    //   1496: iload_1
    //   1497: istore 4
    //   1499: aload 9
    //   1501: astore 8
    //   1503: aload 9
    //   1505: invokevirtual 586	java/io/DataInputStream:readShort	()S
    //   1508: istore 7
    //   1510: iconst_0
    //   1511: istore 6
    //   1513: iload 6
    //   1515: iload 7
    //   1517: if_icmpge +137 -> 1654
    //   1520: iload_1
    //   1521: istore_3
    //   1522: iload_1
    //   1523: istore_2
    //   1524: iload_1
    //   1525: istore 4
    //   1527: aload 9
    //   1529: astore 8
    //   1531: aload 9
    //   1533: aload 9
    //   1535: invokevirtual 586	java/io/DataInputStream:readShort	()S
    //   1538: invokestatic 590	com/tencent/common/utils/FileUtils:read	(Ljava/io/InputStream;I)Ljava/nio/ByteBuffer;
    //   1541: astore 10
    //   1543: iload_1
    //   1544: istore_3
    //   1545: iload_1
    //   1546: istore_2
    //   1547: iload_1
    //   1548: istore 4
    //   1550: aload 9
    //   1552: astore 8
    //   1554: new 592	com/taf/JceInputStream
    //   1557: dup
    //   1558: aload 10
    //   1560: invokespecial 595	com/taf/JceInputStream:<init>	(Ljava/nio/ByteBuffer;)V
    //   1563: astore 12
    //   1565: iload_1
    //   1566: istore_3
    //   1567: iload_1
    //   1568: istore_2
    //   1569: iload_1
    //   1570: istore 4
    //   1572: aload 9
    //   1574: astore 8
    //   1576: new 649	com/tencent/tbs/common/MTT/STCommonAppInfo
    //   1579: dup
    //   1580: invokespecial 650	com/tencent/tbs/common/MTT/STCommonAppInfo:<init>	()V
    //   1583: astore 13
    //   1585: iload_1
    //   1586: istore_3
    //   1587: iload_1
    //   1588: istore_2
    //   1589: iload_1
    //   1590: istore 4
    //   1592: aload 9
    //   1594: astore 8
    //   1596: aload 13
    //   1598: aload 12
    //   1600: invokevirtual 651	com/tencent/tbs/common/MTT/STCommonAppInfo:readFrom	(Lcom/taf/JceInputStream;)V
    //   1603: iload_1
    //   1604: istore_3
    //   1605: iload_1
    //   1606: istore_2
    //   1607: iload_1
    //   1608: istore 4
    //   1610: aload 9
    //   1612: astore 8
    //   1614: aload 11
    //   1616: getfield 654	com/tencent/tbs/common/stat/WUPStatData:mCommonAppInfos	Ljava/util/ArrayList;
    //   1619: aload 13
    //   1621: invokevirtual 425	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   1624: pop
    //   1625: iload_1
    //   1626: istore_3
    //   1627: iload_1
    //   1628: istore_2
    //   1629: iload_1
    //   1630: istore 4
    //   1632: aload 9
    //   1634: astore 8
    //   1636: invokestatic 602	com/tencent/common/utils/FileUtils:getInstance	()Lcom/tencent/common/utils/FileUtils;
    //   1639: aload 10
    //   1641: invokevirtual 606	com/tencent/common/utils/FileUtils:releaseByteBuffer	(Ljava/nio/ByteBuffer;)Z
    //   1644: pop
    //   1645: iload 6
    //   1647: iconst_1
    //   1648: iadd
    //   1649: istore 6
    //   1651: goto -138 -> 1513
    //   1654: iload_1
    //   1655: istore_3
    //   1656: iload_1
    //   1657: istore_2
    //   1658: iload_1
    //   1659: istore 4
    //   1661: aload 9
    //   1663: astore 8
    //   1665: aload 9
    //   1667: invokevirtual 586	java/io/DataInputStream:readShort	()S
    //   1670: istore 7
    //   1672: iconst_0
    //   1673: istore 6
    //   1675: iload 6
    //   1677: iload 7
    //   1679: if_icmpge +191 -> 1870
    //   1682: iload_1
    //   1683: istore_3
    //   1684: iload_1
    //   1685: istore_2
    //   1686: iload_1
    //   1687: istore 4
    //   1689: aload 9
    //   1691: astore 8
    //   1693: aload 9
    //   1695: aload 9
    //   1697: invokevirtual 586	java/io/DataInputStream:readShort	()S
    //   1700: invokestatic 590	com/tencent/common/utils/FileUtils:read	(Ljava/io/InputStream;I)Ljava/nio/ByteBuffer;
    //   1703: astore 10
    //   1705: iload_1
    //   1706: istore_3
    //   1707: iload_1
    //   1708: istore_2
    //   1709: iload_1
    //   1710: istore 4
    //   1712: aload 9
    //   1714: astore 8
    //   1716: new 592	com/taf/JceInputStream
    //   1719: dup
    //   1720: aload 10
    //   1722: invokespecial 595	com/taf/JceInputStream:<init>	(Ljava/nio/ByteBuffer;)V
    //   1725: astore 12
    //   1727: iload_1
    //   1728: istore_3
    //   1729: iload_1
    //   1730: istore_2
    //   1731: iload_1
    //   1732: istore 4
    //   1734: aload 9
    //   1736: astore 8
    //   1738: new 307	com/tencent/tbs/common/MTT/CommStatData
    //   1741: dup
    //   1742: invokespecial 655	com/tencent/tbs/common/MTT/CommStatData:<init>	()V
    //   1745: astore 13
    //   1747: iload_1
    //   1748: istore_3
    //   1749: iload_1
    //   1750: istore_2
    //   1751: iload_1
    //   1752: istore 4
    //   1754: aload 9
    //   1756: astore 8
    //   1758: aload 13
    //   1760: aload 12
    //   1762: invokevirtual 656	com/tencent/tbs/common/MTT/CommStatData:readFrom	(Lcom/taf/JceInputStream;)V
    //   1765: iload_1
    //   1766: istore_3
    //   1767: iload_1
    //   1768: istore_2
    //   1769: iload_1
    //   1770: istore 4
    //   1772: aload 9
    //   1774: astore 8
    //   1776: aload 13
    //   1778: getfield 659	com/tencent/tbs/common/MTT/CommStatData:mDataOp	B
    //   1781: ifne +33 -> 1814
    //   1784: iload_1
    //   1785: istore_3
    //   1786: iload_1
    //   1787: istore_2
    //   1788: iload_1
    //   1789: istore 4
    //   1791: aload 9
    //   1793: astore 8
    //   1795: aload 11
    //   1797: getfield 313	com/tencent/tbs/common/stat/WUPStatData:mCommStatDatas	Ljava/util/HashMap;
    //   1800: aload 13
    //   1802: getfield 310	com/tencent/tbs/common/MTT/CommStatData:sStatKey	Ljava/lang/String;
    //   1805: aload 13
    //   1807: invokevirtual 168	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1810: pop
    //   1811: goto +30 -> 1841
    //   1814: iload_1
    //   1815: istore_3
    //   1816: iload_1
    //   1817: istore_2
    //   1818: iload_1
    //   1819: istore 4
    //   1821: aload 9
    //   1823: astore 8
    //   1825: aload 11
    //   1827: getfield 313	com/tencent/tbs/common/stat/WUPStatData:mCommStatDatas	Ljava/util/HashMap;
    //   1830: aload 13
    //   1832: getfield 413	com/tencent/tbs/common/MTT/CommStatData:sAppKey	Ljava/lang/String;
    //   1835: aload 13
    //   1837: invokevirtual 168	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1840: pop
    //   1841: iload_1
    //   1842: istore_3
    //   1843: iload_1
    //   1844: istore_2
    //   1845: iload_1
    //   1846: istore 4
    //   1848: aload 9
    //   1850: astore 8
    //   1852: invokestatic 602	com/tencent/common/utils/FileUtils:getInstance	()Lcom/tencent/common/utils/FileUtils;
    //   1855: aload 10
    //   1857: invokevirtual 606	com/tencent/common/utils/FileUtils:releaseByteBuffer	(Ljava/nio/ByteBuffer;)Z
    //   1860: pop
    //   1861: iload 6
    //   1863: iconst_1
    //   1864: iadd
    //   1865: istore 6
    //   1867: goto -192 -> 1675
    //   1870: iload_1
    //   1871: istore_3
    //   1872: iload_1
    //   1873: istore_2
    //   1874: iload_1
    //   1875: istore 4
    //   1877: aload 9
    //   1879: astore 8
    //   1881: aload_0
    //   1882: getfield 67	com/tencent/tbs/common/stat/TbsStatDataManager:mPreStatLock	Ljava/lang/Object;
    //   1885: astore 10
    //   1887: iload_1
    //   1888: istore_3
    //   1889: iload_1
    //   1890: istore_2
    //   1891: iload_1
    //   1892: istore 4
    //   1894: aload 9
    //   1896: astore 8
    //   1898: aload 10
    //   1900: monitorenter
    //   1901: aload_0
    //   1902: getfield 65	com/tencent/tbs/common/stat/TbsStatDataManager:mPreStatList	Ljava/util/ArrayList;
    //   1905: aload 11
    //   1907: invokevirtual 425	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   1910: pop
    //   1911: aload 10
    //   1913: monitorexit
    //   1914: iload_1
    //   1915: iconst_1
    //   1916: iadd
    //   1917: istore_1
    //   1918: iload_1
    //   1919: istore_3
    //   1920: iload_1
    //   1921: istore_2
    //   1922: iload_1
    //   1923: istore 4
    //   1925: aload 9
    //   1927: astore 8
    //   1929: new 228	java/lang/StringBuilder
    //   1932: dup
    //   1933: invokespecial 229	java/lang/StringBuilder:<init>	()V
    //   1936: astore 10
    //   1938: iload_1
    //   1939: istore_3
    //   1940: iload_1
    //   1941: istore_2
    //   1942: iload_1
    //   1943: istore 4
    //   1945: aload 9
    //   1947: astore 8
    //   1949: aload 10
    //   1951: ldc_w 661
    //   1954: invokevirtual 234	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1957: pop
    //   1958: iload_1
    //   1959: istore_3
    //   1960: iload_1
    //   1961: istore_2
    //   1962: iload_1
    //   1963: istore 4
    //   1965: aload 9
    //   1967: astore 8
    //   1969: aload 10
    //   1971: iload 5
    //   1973: invokevirtual 237	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1976: pop
    //   1977: iload_1
    //   1978: istore_3
    //   1979: iload_1
    //   1980: istore_2
    //   1981: iload_1
    //   1982: istore 4
    //   1984: aload 9
    //   1986: astore 8
    //   1988: aload 10
    //   1990: ldc_w 663
    //   1993: invokevirtual 234	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1996: pop
    //   1997: iload_1
    //   1998: istore_3
    //   1999: iload_1
    //   2000: istore_2
    //   2001: iload_1
    //   2002: istore 4
    //   2004: aload 9
    //   2006: astore 8
    //   2008: aload 10
    //   2010: aload_0
    //   2011: getfield 81	com/tencent/tbs/common/stat/TbsStatDataManager:mForSimpleQB	Z
    //   2014: invokevirtual 242	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   2017: pop
    //   2018: iload_1
    //   2019: istore_3
    //   2020: iload_1
    //   2021: istore_2
    //   2022: iload_1
    //   2023: istore 4
    //   2025: aload 9
    //   2027: astore 8
    //   2029: ldc 29
    //   2031: aload 10
    //   2033: invokevirtual 243	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2036: invokestatic 249	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   2039: iload_1
    //   2040: istore_2
    //   2041: aload 9
    //   2043: invokevirtual 573	java/io/DataInputStream:close	()V
    //   2046: goto +413 -> 2459
    //   2049: astore 8
    //   2051: aload 8
    //   2053: invokevirtual 576	java/io/IOException:printStackTrace	()V
    //   2056: iload_2
    //   2057: istore_1
    //   2058: goto +401 -> 2459
    //   2061: astore 11
    //   2063: aload 10
    //   2065: monitorexit
    //   2066: iload_1
    //   2067: istore_3
    //   2068: iload_1
    //   2069: istore_2
    //   2070: iload_1
    //   2071: istore 4
    //   2073: aload 9
    //   2075: astore 8
    //   2077: aload 11
    //   2079: athrow
    //   2080: astore 10
    //   2082: goto +33 -> 2115
    //   2085: astore 10
    //   2087: iload_2
    //   2088: istore_3
    //   2089: goto +61 -> 2150
    //   2092: astore 10
    //   2094: iload 4
    //   2096: istore_3
    //   2097: goto +167 -> 2264
    //   2100: astore 8
    //   2102: aconst_null
    //   2103: astore 9
    //   2105: goto +276 -> 2381
    //   2108: astore 10
    //   2110: aconst_null
    //   2111: astore 9
    //   2113: iload_1
    //   2114: istore_3
    //   2115: aload 9
    //   2117: astore 8
    //   2119: aload 10
    //   2121: invokevirtual 664	java/lang/NoClassDefFoundError:printStackTrace	()V
    //   2124: iload_3
    //   2125: istore_1
    //   2126: aload 9
    //   2128: ifnull +331 -> 2459
    //   2131: iload_3
    //   2132: istore_2
    //   2133: aload 9
    //   2135: invokevirtual 573	java/io/DataInputStream:close	()V
    //   2138: iload_3
    //   2139: istore_1
    //   2140: goto +319 -> 2459
    //   2143: astore 10
    //   2145: aconst_null
    //   2146: astore 9
    //   2148: iload_1
    //   2149: istore_3
    //   2150: aload 9
    //   2152: astore 8
    //   2154: new 228	java/lang/StringBuilder
    //   2157: dup
    //   2158: invokespecial 229	java/lang/StringBuilder:<init>	()V
    //   2161: astore 11
    //   2163: aload 9
    //   2165: astore 8
    //   2167: aload 11
    //   2169: ldc_w 661
    //   2172: invokevirtual 234	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2175: pop
    //   2176: aload 9
    //   2178: astore 8
    //   2180: aload 11
    //   2182: iload 5
    //   2184: invokevirtual 237	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   2187: pop
    //   2188: aload 9
    //   2190: astore 8
    //   2192: aload 11
    //   2194: ldc_w 666
    //   2197: invokevirtual 234	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2200: pop
    //   2201: aload 9
    //   2203: astore 8
    //   2205: aload 11
    //   2207: aload_0
    //   2208: getfield 81	com/tencent/tbs/common/stat/TbsStatDataManager:mForSimpleQB	Z
    //   2211: invokevirtual 242	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   2214: pop
    //   2215: aload 9
    //   2217: astore 8
    //   2219: ldc 29
    //   2221: aload 11
    //   2223: invokevirtual 243	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2226: invokestatic 249	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   2229: aload 9
    //   2231: astore 8
    //   2233: aload 10
    //   2235: invokevirtual 667	java/lang/OutOfMemoryError:printStackTrace	()V
    //   2238: iload_3
    //   2239: istore_1
    //   2240: aload 9
    //   2242: ifnull +217 -> 2459
    //   2245: iload_3
    //   2246: istore_2
    //   2247: aload 9
    //   2249: invokevirtual 573	java/io/DataInputStream:close	()V
    //   2252: iload_3
    //   2253: istore_1
    //   2254: goto +205 -> 2459
    //   2257: astore 10
    //   2259: aconst_null
    //   2260: astore 9
    //   2262: iload_1
    //   2263: istore_3
    //   2264: aload 9
    //   2266: astore 8
    //   2268: new 228	java/lang/StringBuilder
    //   2271: dup
    //   2272: invokespecial 229	java/lang/StringBuilder:<init>	()V
    //   2275: astore 11
    //   2277: aload 9
    //   2279: astore 8
    //   2281: aload 11
    //   2283: ldc_w 661
    //   2286: invokevirtual 234	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2289: pop
    //   2290: aload 9
    //   2292: astore 8
    //   2294: aload 11
    //   2296: iload 5
    //   2298: invokevirtual 237	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   2301: pop
    //   2302: aload 9
    //   2304: astore 8
    //   2306: aload 11
    //   2308: ldc_w 669
    //   2311: invokevirtual 234	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2314: pop
    //   2315: aload 9
    //   2317: astore 8
    //   2319: aload 11
    //   2321: aload_0
    //   2322: getfield 81	com/tencent/tbs/common/stat/TbsStatDataManager:mForSimpleQB	Z
    //   2325: invokevirtual 242	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   2328: pop
    //   2329: aload 9
    //   2331: astore 8
    //   2333: ldc 29
    //   2335: aload 11
    //   2337: invokevirtual 243	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2340: invokestatic 249	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   2343: aload 9
    //   2345: astore 8
    //   2347: aload 10
    //   2349: invokevirtual 670	java/lang/Exception:printStackTrace	()V
    //   2352: iload_3
    //   2353: istore_1
    //   2354: aload 9
    //   2356: ifnull +103 -> 2459
    //   2359: iload_3
    //   2360: istore_2
    //   2361: aload 9
    //   2363: invokevirtual 573	java/io/DataInputStream:close	()V
    //   2366: iload_3
    //   2367: istore_1
    //   2368: goto +91 -> 2459
    //   2371: astore 10
    //   2373: aload 8
    //   2375: astore 9
    //   2377: aload 10
    //   2379: astore 8
    //   2381: aload 9
    //   2383: ifnull +18 -> 2401
    //   2386: aload 9
    //   2388: invokevirtual 573	java/io/DataInputStream:close	()V
    //   2391: goto +10 -> 2401
    //   2394: astore 9
    //   2396: aload 9
    //   2398: invokevirtual 576	java/io/IOException:printStackTrace	()V
    //   2401: aload 8
    //   2403: athrow
    //   2404: new 228	java/lang/StringBuilder
    //   2407: dup
    //   2408: invokespecial 229	java/lang/StringBuilder:<init>	()V
    //   2411: astore 8
    //   2413: aload 8
    //   2415: ldc_w 566
    //   2418: invokevirtual 234	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2421: pop
    //   2422: aload 8
    //   2424: iload 5
    //   2426: invokevirtual 237	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   2429: pop
    //   2430: aload 8
    //   2432: ldc_w 672
    //   2435: invokevirtual 234	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2438: pop
    //   2439: aload 8
    //   2441: aload_0
    //   2442: getfield 81	com/tencent/tbs/common/stat/TbsStatDataManager:mForSimpleQB	Z
    //   2445: invokevirtual 242	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   2448: pop
    //   2449: ldc 29
    //   2451: aload 8
    //   2453: invokevirtual 243	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2456: invokestatic 249	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   2459: iload 5
    //   2461: iconst_1
    //   2462: iadd
    //   2463: istore 5
    //   2465: goto -2460 -> 5
    //   2468: iload_1
    //   2469: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	2470	0	this	TbsStatDataManager
    //   4	2465	1	i	int
    //   63	2298	2	j	int
    //   61	2306	3	k	int
    //   65	2030	4	m	int
    //   1	2463	5	n	int
    //   383	1483	6	i1	int
    //   380	1300	7	i2	int
    //   21	276	8	localObject1	Object
    //   312	3	8	localIOException1	java.io.IOException
    //   331	1697	8	localObject2	Object
    //   2049	3	8	localIOException2	java.io.IOException
    //   2075	1	8	localObject3	Object
    //   2100	1	8	localObject4	Object
    //   2117	335	8	localObject5	Object
    //   58	2329	9	localObject6	Object
    //   2394	3	9	localIOException3	java.io.IOException
    //   143	1921	10	localObject7	Object
    //   2080	1	10	localNoClassDefFoundError1	NoClassDefFoundError
    //   2085	1	10	localOutOfMemoryError1	OutOfMemoryError
    //   2092	1	10	localException1	Exception
    //   2108	12	10	localNoClassDefFoundError2	NoClassDefFoundError
    //   2143	91	10	localOutOfMemoryError2	OutOfMemoryError
    //   2257	91	10	localException2	Exception
    //   2371	7	10	localObject8	Object
    //   78	1828	11	localWUPStatData	WUPStatData
    //   2061	17	11	localObject9	Object
    //   2161	175	11	localStringBuilder1	StringBuilder
    //   435	1326	12	localObject10	Object
    //   455	1381	13	localObject11	Object
    //   678	122	14	localStringBuilder2	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   304	309	312	java/io/IOException
    //   2041	2046	2049	java/io/IOException
    //   2133	2138	2049	java/io/IOException
    //   2247	2252	2049	java/io/IOException
    //   2361	2366	2049	java/io/IOException
    //   1901	1914	2061	finally
    //   2063	2066	2061	finally
    //   71	80	2080	java/lang/NoClassDefFoundError
    //   91	101	2080	java/lang/NoClassDefFoundError
    //   112	125	2080	java/lang/NoClassDefFoundError
    //   136	145	2080	java/lang/NoClassDefFoundError
    //   156	165	2080	java/lang/NoClassDefFoundError
    //   176	184	2080	java/lang/NoClassDefFoundError
    //   195	204	2080	java/lang/NoClassDefFoundError
    //   215	226	2080	java/lang/NoClassDefFoundError
    //   237	245	2080	java/lang/NoClassDefFoundError
    //   256	266	2080	java/lang/NoClassDefFoundError
    //   277	287	2080	java/lang/NoClassDefFoundError
    //   298	304	2080	java/lang/NoClassDefFoundError
    //   333	343	2080	java/lang/NoClassDefFoundError
    //   354	364	2080	java/lang/NoClassDefFoundError
    //   375	382	2080	java/lang/NoClassDefFoundError
    //   403	415	2080	java/lang/NoClassDefFoundError
    //   426	437	2080	java/lang/NoClassDefFoundError
    //   448	457	2080	java/lang/NoClassDefFoundError
    //   468	475	2080	java/lang/NoClassDefFoundError
    //   486	502	2080	java/lang/NoClassDefFoundError
    //   513	522	2080	java/lang/NoClassDefFoundError
    //   542	549	2080	java/lang/NoClassDefFoundError
    //   570	582	2080	java/lang/NoClassDefFoundError
    //   593	604	2080	java/lang/NoClassDefFoundError
    //   615	624	2080	java/lang/NoClassDefFoundError
    //   635	642	2080	java/lang/NoClassDefFoundError
    //   653	660	2080	java/lang/NoClassDefFoundError
    //   671	680	2080	java/lang/NoClassDefFoundError
    //   691	702	2080	java/lang/NoClassDefFoundError
    //   713	722	2080	java/lang/NoClassDefFoundError
    //   733	744	2080	java/lang/NoClassDefFoundError
    //   755	764	2080	java/lang/NoClassDefFoundError
    //   775	786	2080	java/lang/NoClassDefFoundError
    //   797	810	2080	java/lang/NoClassDefFoundError
    //   821	830	2080	java/lang/NoClassDefFoundError
    //   850	857	2080	java/lang/NoClassDefFoundError
    //   878	890	2080	java/lang/NoClassDefFoundError
    //   901	912	2080	java/lang/NoClassDefFoundError
    //   923	932	2080	java/lang/NoClassDefFoundError
    //   943	950	2080	java/lang/NoClassDefFoundError
    //   961	971	2080	java/lang/NoClassDefFoundError
    //   982	991	2080	java/lang/NoClassDefFoundError
    //   1002	1009	2080	java/lang/NoClassDefFoundError
    //   1020	1033	2080	java/lang/NoClassDefFoundError
    //   1044	1053	2080	java/lang/NoClassDefFoundError
    //   1073	1080	2080	java/lang/NoClassDefFoundError
    //   1091	1100	2080	java/lang/NoClassDefFoundError
    //   1111	1120	2080	java/lang/NoClassDefFoundError
    //   1131	1139	2080	java/lang/NoClassDefFoundError
    //   1150	1158	2080	java/lang/NoClassDefFoundError
    //   1169	1179	2080	java/lang/NoClassDefFoundError
    //   1190	1201	2080	java/lang/NoClassDefFoundError
    //   1222	1234	2080	java/lang/NoClassDefFoundError
    //   1245	1254	2080	java/lang/NoClassDefFoundError
    //   1265	1281	2080	java/lang/NoClassDefFoundError
    //   1292	1301	2080	java/lang/NoClassDefFoundError
    //   1312	1321	2080	java/lang/NoClassDefFoundError
    //   1332	1340	2080	java/lang/NoClassDefFoundError
    //   1351	1359	2080	java/lang/NoClassDefFoundError
    //   1370	1380	2080	java/lang/NoClassDefFoundError
    //   1391	1402	2080	java/lang/NoClassDefFoundError
    //   1413	1439	2080	java/lang/NoClassDefFoundError
    //   1450	1463	2080	java/lang/NoClassDefFoundError
    //   1474	1483	2080	java/lang/NoClassDefFoundError
    //   1503	1510	2080	java/lang/NoClassDefFoundError
    //   1531	1543	2080	java/lang/NoClassDefFoundError
    //   1554	1565	2080	java/lang/NoClassDefFoundError
    //   1576	1585	2080	java/lang/NoClassDefFoundError
    //   1596	1603	2080	java/lang/NoClassDefFoundError
    //   1614	1625	2080	java/lang/NoClassDefFoundError
    //   1636	1645	2080	java/lang/NoClassDefFoundError
    //   1665	1672	2080	java/lang/NoClassDefFoundError
    //   1693	1705	2080	java/lang/NoClassDefFoundError
    //   1716	1727	2080	java/lang/NoClassDefFoundError
    //   1738	1747	2080	java/lang/NoClassDefFoundError
    //   1758	1765	2080	java/lang/NoClassDefFoundError
    //   1776	1784	2080	java/lang/NoClassDefFoundError
    //   1795	1811	2080	java/lang/NoClassDefFoundError
    //   1825	1841	2080	java/lang/NoClassDefFoundError
    //   1852	1861	2080	java/lang/NoClassDefFoundError
    //   1881	1887	2080	java/lang/NoClassDefFoundError
    //   1898	1901	2080	java/lang/NoClassDefFoundError
    //   1929	1938	2080	java/lang/NoClassDefFoundError
    //   1949	1958	2080	java/lang/NoClassDefFoundError
    //   1969	1977	2080	java/lang/NoClassDefFoundError
    //   1988	1997	2080	java/lang/NoClassDefFoundError
    //   2008	2018	2080	java/lang/NoClassDefFoundError
    //   2029	2039	2080	java/lang/NoClassDefFoundError
    //   2077	2080	2080	java/lang/NoClassDefFoundError
    //   71	80	2085	java/lang/OutOfMemoryError
    //   91	101	2085	java/lang/OutOfMemoryError
    //   112	125	2085	java/lang/OutOfMemoryError
    //   136	145	2085	java/lang/OutOfMemoryError
    //   156	165	2085	java/lang/OutOfMemoryError
    //   176	184	2085	java/lang/OutOfMemoryError
    //   195	204	2085	java/lang/OutOfMemoryError
    //   215	226	2085	java/lang/OutOfMemoryError
    //   237	245	2085	java/lang/OutOfMemoryError
    //   256	266	2085	java/lang/OutOfMemoryError
    //   277	287	2085	java/lang/OutOfMemoryError
    //   298	304	2085	java/lang/OutOfMemoryError
    //   333	343	2085	java/lang/OutOfMemoryError
    //   354	364	2085	java/lang/OutOfMemoryError
    //   375	382	2085	java/lang/OutOfMemoryError
    //   403	415	2085	java/lang/OutOfMemoryError
    //   426	437	2085	java/lang/OutOfMemoryError
    //   448	457	2085	java/lang/OutOfMemoryError
    //   468	475	2085	java/lang/OutOfMemoryError
    //   486	502	2085	java/lang/OutOfMemoryError
    //   513	522	2085	java/lang/OutOfMemoryError
    //   542	549	2085	java/lang/OutOfMemoryError
    //   570	582	2085	java/lang/OutOfMemoryError
    //   593	604	2085	java/lang/OutOfMemoryError
    //   615	624	2085	java/lang/OutOfMemoryError
    //   635	642	2085	java/lang/OutOfMemoryError
    //   653	660	2085	java/lang/OutOfMemoryError
    //   671	680	2085	java/lang/OutOfMemoryError
    //   691	702	2085	java/lang/OutOfMemoryError
    //   713	722	2085	java/lang/OutOfMemoryError
    //   733	744	2085	java/lang/OutOfMemoryError
    //   755	764	2085	java/lang/OutOfMemoryError
    //   775	786	2085	java/lang/OutOfMemoryError
    //   797	810	2085	java/lang/OutOfMemoryError
    //   821	830	2085	java/lang/OutOfMemoryError
    //   850	857	2085	java/lang/OutOfMemoryError
    //   878	890	2085	java/lang/OutOfMemoryError
    //   901	912	2085	java/lang/OutOfMemoryError
    //   923	932	2085	java/lang/OutOfMemoryError
    //   943	950	2085	java/lang/OutOfMemoryError
    //   961	971	2085	java/lang/OutOfMemoryError
    //   982	991	2085	java/lang/OutOfMemoryError
    //   1002	1009	2085	java/lang/OutOfMemoryError
    //   1020	1033	2085	java/lang/OutOfMemoryError
    //   1044	1053	2085	java/lang/OutOfMemoryError
    //   1073	1080	2085	java/lang/OutOfMemoryError
    //   1091	1100	2085	java/lang/OutOfMemoryError
    //   1111	1120	2085	java/lang/OutOfMemoryError
    //   1131	1139	2085	java/lang/OutOfMemoryError
    //   1150	1158	2085	java/lang/OutOfMemoryError
    //   1169	1179	2085	java/lang/OutOfMemoryError
    //   1190	1201	2085	java/lang/OutOfMemoryError
    //   1222	1234	2085	java/lang/OutOfMemoryError
    //   1245	1254	2085	java/lang/OutOfMemoryError
    //   1265	1281	2085	java/lang/OutOfMemoryError
    //   1292	1301	2085	java/lang/OutOfMemoryError
    //   1312	1321	2085	java/lang/OutOfMemoryError
    //   1332	1340	2085	java/lang/OutOfMemoryError
    //   1351	1359	2085	java/lang/OutOfMemoryError
    //   1370	1380	2085	java/lang/OutOfMemoryError
    //   1391	1402	2085	java/lang/OutOfMemoryError
    //   1413	1439	2085	java/lang/OutOfMemoryError
    //   1450	1463	2085	java/lang/OutOfMemoryError
    //   1474	1483	2085	java/lang/OutOfMemoryError
    //   1503	1510	2085	java/lang/OutOfMemoryError
    //   1531	1543	2085	java/lang/OutOfMemoryError
    //   1554	1565	2085	java/lang/OutOfMemoryError
    //   1576	1585	2085	java/lang/OutOfMemoryError
    //   1596	1603	2085	java/lang/OutOfMemoryError
    //   1614	1625	2085	java/lang/OutOfMemoryError
    //   1636	1645	2085	java/lang/OutOfMemoryError
    //   1665	1672	2085	java/lang/OutOfMemoryError
    //   1693	1705	2085	java/lang/OutOfMemoryError
    //   1716	1727	2085	java/lang/OutOfMemoryError
    //   1738	1747	2085	java/lang/OutOfMemoryError
    //   1758	1765	2085	java/lang/OutOfMemoryError
    //   1776	1784	2085	java/lang/OutOfMemoryError
    //   1795	1811	2085	java/lang/OutOfMemoryError
    //   1825	1841	2085	java/lang/OutOfMemoryError
    //   1852	1861	2085	java/lang/OutOfMemoryError
    //   1881	1887	2085	java/lang/OutOfMemoryError
    //   1898	1901	2085	java/lang/OutOfMemoryError
    //   1929	1938	2085	java/lang/OutOfMemoryError
    //   1949	1958	2085	java/lang/OutOfMemoryError
    //   1969	1977	2085	java/lang/OutOfMemoryError
    //   1988	1997	2085	java/lang/OutOfMemoryError
    //   2008	2018	2085	java/lang/OutOfMemoryError
    //   2029	2039	2085	java/lang/OutOfMemoryError
    //   2077	2080	2085	java/lang/OutOfMemoryError
    //   71	80	2092	java/lang/Exception
    //   91	101	2092	java/lang/Exception
    //   112	125	2092	java/lang/Exception
    //   136	145	2092	java/lang/Exception
    //   156	165	2092	java/lang/Exception
    //   176	184	2092	java/lang/Exception
    //   195	204	2092	java/lang/Exception
    //   215	226	2092	java/lang/Exception
    //   237	245	2092	java/lang/Exception
    //   256	266	2092	java/lang/Exception
    //   277	287	2092	java/lang/Exception
    //   298	304	2092	java/lang/Exception
    //   333	343	2092	java/lang/Exception
    //   354	364	2092	java/lang/Exception
    //   375	382	2092	java/lang/Exception
    //   403	415	2092	java/lang/Exception
    //   426	437	2092	java/lang/Exception
    //   448	457	2092	java/lang/Exception
    //   468	475	2092	java/lang/Exception
    //   486	502	2092	java/lang/Exception
    //   513	522	2092	java/lang/Exception
    //   542	549	2092	java/lang/Exception
    //   570	582	2092	java/lang/Exception
    //   593	604	2092	java/lang/Exception
    //   615	624	2092	java/lang/Exception
    //   635	642	2092	java/lang/Exception
    //   653	660	2092	java/lang/Exception
    //   671	680	2092	java/lang/Exception
    //   691	702	2092	java/lang/Exception
    //   713	722	2092	java/lang/Exception
    //   733	744	2092	java/lang/Exception
    //   755	764	2092	java/lang/Exception
    //   775	786	2092	java/lang/Exception
    //   797	810	2092	java/lang/Exception
    //   821	830	2092	java/lang/Exception
    //   850	857	2092	java/lang/Exception
    //   878	890	2092	java/lang/Exception
    //   901	912	2092	java/lang/Exception
    //   923	932	2092	java/lang/Exception
    //   943	950	2092	java/lang/Exception
    //   961	971	2092	java/lang/Exception
    //   982	991	2092	java/lang/Exception
    //   1002	1009	2092	java/lang/Exception
    //   1020	1033	2092	java/lang/Exception
    //   1044	1053	2092	java/lang/Exception
    //   1073	1080	2092	java/lang/Exception
    //   1091	1100	2092	java/lang/Exception
    //   1111	1120	2092	java/lang/Exception
    //   1131	1139	2092	java/lang/Exception
    //   1150	1158	2092	java/lang/Exception
    //   1169	1179	2092	java/lang/Exception
    //   1190	1201	2092	java/lang/Exception
    //   1222	1234	2092	java/lang/Exception
    //   1245	1254	2092	java/lang/Exception
    //   1265	1281	2092	java/lang/Exception
    //   1292	1301	2092	java/lang/Exception
    //   1312	1321	2092	java/lang/Exception
    //   1332	1340	2092	java/lang/Exception
    //   1351	1359	2092	java/lang/Exception
    //   1370	1380	2092	java/lang/Exception
    //   1391	1402	2092	java/lang/Exception
    //   1413	1439	2092	java/lang/Exception
    //   1450	1463	2092	java/lang/Exception
    //   1474	1483	2092	java/lang/Exception
    //   1503	1510	2092	java/lang/Exception
    //   1531	1543	2092	java/lang/Exception
    //   1554	1565	2092	java/lang/Exception
    //   1576	1585	2092	java/lang/Exception
    //   1596	1603	2092	java/lang/Exception
    //   1614	1625	2092	java/lang/Exception
    //   1636	1645	2092	java/lang/Exception
    //   1665	1672	2092	java/lang/Exception
    //   1693	1705	2092	java/lang/Exception
    //   1716	1727	2092	java/lang/Exception
    //   1738	1747	2092	java/lang/Exception
    //   1758	1765	2092	java/lang/Exception
    //   1776	1784	2092	java/lang/Exception
    //   1795	1811	2092	java/lang/Exception
    //   1825	1841	2092	java/lang/Exception
    //   1852	1861	2092	java/lang/Exception
    //   1881	1887	2092	java/lang/Exception
    //   1898	1901	2092	java/lang/Exception
    //   1929	1938	2092	java/lang/Exception
    //   1949	1958	2092	java/lang/Exception
    //   1969	1977	2092	java/lang/Exception
    //   1988	1997	2092	java/lang/Exception
    //   2008	2018	2092	java/lang/Exception
    //   2029	2039	2092	java/lang/Exception
    //   2077	2080	2092	java/lang/Exception
    //   39	60	2100	finally
    //   39	60	2108	java/lang/NoClassDefFoundError
    //   39	60	2143	java/lang/OutOfMemoryError
    //   39	60	2257	java/lang/Exception
    //   71	80	2371	finally
    //   91	101	2371	finally
    //   112	125	2371	finally
    //   136	145	2371	finally
    //   156	165	2371	finally
    //   176	184	2371	finally
    //   195	204	2371	finally
    //   215	226	2371	finally
    //   237	245	2371	finally
    //   256	266	2371	finally
    //   277	287	2371	finally
    //   298	304	2371	finally
    //   333	343	2371	finally
    //   354	364	2371	finally
    //   375	382	2371	finally
    //   403	415	2371	finally
    //   426	437	2371	finally
    //   448	457	2371	finally
    //   468	475	2371	finally
    //   486	502	2371	finally
    //   513	522	2371	finally
    //   542	549	2371	finally
    //   570	582	2371	finally
    //   593	604	2371	finally
    //   615	624	2371	finally
    //   635	642	2371	finally
    //   653	660	2371	finally
    //   671	680	2371	finally
    //   691	702	2371	finally
    //   713	722	2371	finally
    //   733	744	2371	finally
    //   755	764	2371	finally
    //   775	786	2371	finally
    //   797	810	2371	finally
    //   821	830	2371	finally
    //   850	857	2371	finally
    //   878	890	2371	finally
    //   901	912	2371	finally
    //   923	932	2371	finally
    //   943	950	2371	finally
    //   961	971	2371	finally
    //   982	991	2371	finally
    //   1002	1009	2371	finally
    //   1020	1033	2371	finally
    //   1044	1053	2371	finally
    //   1073	1080	2371	finally
    //   1091	1100	2371	finally
    //   1111	1120	2371	finally
    //   1131	1139	2371	finally
    //   1150	1158	2371	finally
    //   1169	1179	2371	finally
    //   1190	1201	2371	finally
    //   1222	1234	2371	finally
    //   1245	1254	2371	finally
    //   1265	1281	2371	finally
    //   1292	1301	2371	finally
    //   1312	1321	2371	finally
    //   1332	1340	2371	finally
    //   1351	1359	2371	finally
    //   1370	1380	2371	finally
    //   1391	1402	2371	finally
    //   1413	1439	2371	finally
    //   1450	1463	2371	finally
    //   1474	1483	2371	finally
    //   1503	1510	2371	finally
    //   1531	1543	2371	finally
    //   1554	1565	2371	finally
    //   1576	1585	2371	finally
    //   1596	1603	2371	finally
    //   1614	1625	2371	finally
    //   1636	1645	2371	finally
    //   1665	1672	2371	finally
    //   1693	1705	2371	finally
    //   1716	1727	2371	finally
    //   1738	1747	2371	finally
    //   1758	1765	2371	finally
    //   1776	1784	2371	finally
    //   1795	1811	2371	finally
    //   1825	1841	2371	finally
    //   1852	1861	2371	finally
    //   1881	1887	2371	finally
    //   1898	1901	2371	finally
    //   1929	1938	2371	finally
    //   1949	1958	2371	finally
    //   1969	1977	2371	finally
    //   1988	1997	2371	finally
    //   2008	2018	2371	finally
    //   2029	2039	2371	finally
    //   2077	2080	2371	finally
    //   2119	2124	2371	finally
    //   2154	2163	2371	finally
    //   2167	2176	2371	finally
    //   2180	2188	2371	finally
    //   2192	2201	2371	finally
    //   2205	2215	2371	finally
    //   2219	2229	2371	finally
    //   2233	2238	2371	finally
    //   2268	2277	2371	finally
    //   2281	2290	2371	finally
    //   2294	2302	2371	finally
    //   2306	2315	2371	finally
    //   2319	2329	2371	finally
    //   2333	2343	2371	finally
    //   2347	2352	2371	finally
    //   2386	2391	2394	java/io/IOException
  }
  
  protected void notifyLoadEnd()
  {
    synchronized (this.mPreStatLock)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("notify load stat data end; isSimpleQB=");
      localStringBuilder.append(this.mForSimpleQB);
      LogUtils.d("loadDatTag", localStringBuilder.toString());
      if (TbsWupManager.getInstance().isNewDay())
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("this is a new day, check weather has any stat data; isSimpleQB=");
        localStringBuilder.append(this.mForSimpleQB);
        LogUtils.d("loadDatTag", localStringBuilder.toString());
        if (((this.mPreStatList != null) && (this.mPreStatList.size() > 0)) || (!this.mCurrentDataAllNew))
        {
          LogUtils.d("loadDatTag", "this is a new day, and we have stat data, upload");
          TbsStatDataUploader.getInstance().upload(this.mForSimpleQB);
        }
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("this is a new day, but we do not have any stat data, return, do nothing; isSimpleQB=");
        localStringBuilder.append(this.mForSimpleQB);
        LogUtils.d("loadDatTag", localStringBuilder.toString());
        return;
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("this is not a new day since last boot, check if we have got 5 stat files; isSimpleQB=");
      localStringBuilder.append(this.mForSimpleQB);
      LogUtils.d("loadDatTag", localStringBuilder.toString());
      if ((this.mPreStatList != null) && (this.mPreStatList.size() >= 5))
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("this is not a new day since last boot, Oh, yes, we have got ");
        localStringBuilder.append(this.mPreStatList.size());
        localStringBuilder.append(" stat files, upload; isSimpleQB=");
        localStringBuilder.append(this.mForSimpleQB);
        LogUtils.d("loadDatTag", localStringBuilder.toString());
        TbsStatDataUploader.getInstance().upload(this.mForSimpleQB);
        if (!this.mForSimpleQB) {
          getSimpleQBStatManager().setForceUpload(true);
        }
        return;
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("this is not a new day and less than 5 files, check whether we are forced to upload, mmForceUploadFlag=");
      localStringBuilder.append(this.mForceUploadFlag);
      localStringBuilder.append("; isSimpleQB=");
      localStringBuilder.append(this.mForSimpleQB);
      LogUtils.d("loadDatTag", localStringBuilder.toString());
      boolean bool = this.mForceUploadFlag;
      int i = 0;
      if ((bool) && (this.mPreStatList != null) && (this.mPreStatList.size() > 0))
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("this is not a new day and less than 5 files, but we are forced to upload, we have got ");
        localStringBuilder.append(this.mPreStatList.size());
        localStringBuilder.append(" stat files, upload; isSimpleQB=");
        localStringBuilder.append(this.mForSimpleQB);
        LogUtils.d("loadDatTag", localStringBuilder.toString());
        this.mForceUploadFlag = false;
        TbsStatDataUploader.getInstance().upload(this.mForSimpleQB);
        return;
      }
      if (this.mPreStatList != null) {
        i = this.mPreStatList.size();
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("this is not a new day since last boot, but we just have got ");
      localStringBuilder.append(i);
      localStringBuilder.append(" stat files, return, do nothing; isSimpleQB=");
      localStringBuilder.append(this.mForSimpleQB);
      LogUtils.d("loadDatTag", localStringBuilder.toString());
      return;
    }
  }
  
  public void onReportPageTotalTimeV2(final String paramString1, final String paramString2, final byte paramByte, final long paramLong1, long paramLong2, final long paramLong3, long paramLong4, final String paramString3)
  {
    StatWorkerThread.getInstance().post(new Runnable()
    {
      public void run()
      {
        if (!TbsStatDataManager.this.mIsCollectStat) {
          return;
        }
        PerformanceStat localPerformanceStat = TbsStatDataManager.this.createOrGetPerformanceStat(paramString1, paramByte, paramString2, paramString3);
        localPerformanceStat.iTotalTime += (int)paramLong3;
        localPerformanceStat.iNetLoadTime += (int)paramLong1;
        if (localPerformanceStat.vReqRecord != null)
        {
          int i = localPerformanceStat.vReqRecord.size();
          if (i > 0)
          {
            ReqRecord localReqRecord = (ReqRecord)localPerformanceStat.vReqRecord.get(i - 1);
            localReqRecord.iFirstWordTime = ((int)this.val$firstWordTime);
            localReqRecord.iFirstScreenTime = ((int)this.val$firstScreenTime);
            Object localObject = TbsBaseModuleShell.getCoreInfoFetcher();
            if ((localObject != null) && (TbsStatDataManager.this.getCurrentStatData().trimPerformanceStat() >= ((ICoreInfoFetcher)localObject).getMaxReportNumber()))
            {
              localObject = new StringBuilder();
              ((StringBuilder)localObject).append("max-report-num reached, stop loging; isSimpleQB=");
              ((StringBuilder)localObject).append(TbsStatDataManager.this.mForSimpleQB);
              LogUtils.d("StatManager", ((StringBuilder)localObject).toString());
              TbsStatDataManager.access$102(TbsStatDataManager.this, false);
            }
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append("first-word, first-screen, etc. statics are added to : ");
            ((StringBuilder)localObject).append(WUPStatData.getPerformanceStatKey(localPerformanceStat));
            ((StringBuilder)localObject).append(", and the record's status-code: ");
            ((StringBuilder)localObject).append(localReqRecord.iRet);
            ((StringBuilder)localObject).append("; isSimpleQB=");
            ((StringBuilder)localObject).append(TbsStatDataManager.this.mForSimpleQB);
            LogUtils.d("StatManager", ((StringBuilder)localObject).toString());
          }
        }
      }
    });
  }
  
  public void save(WUPStatData paramWUPStatData)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("TesStatDataManager-do save(); isSimpleQB=");
    localStringBuilder.append(this.mForSimpleQB);
    LogUtils.d("TbsStatDataManager", localStringBuilder.toString());
    paramWUPStatData = new SaveWupDataRunnable(paramWUPStatData.copy(), this.mForSimpleQB);
    StatWorkerThread.getInstance().post(paramWUPStatData);
  }
  
  public boolean save()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("TesStatDataManager-save begin(); isSimpleQB=");
    localStringBuilder.append(this.mForSimpleQB);
    LogUtils.d("TbsStatDataManager", localStringBuilder.toString());
    if ((!this.mCurrentDataAllNew) && (this.mHasDataChangedSinceLastSave))
    {
      this.mHasDataChangedSinceLastSave = false;
      save(getCurrentStatData());
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("TesStatDataManager-save(); isSimpleQB=");
      localStringBuilder.append(this.mForSimpleQB);
      LogUtils.d("TbsStatDataManagerpv-Report", localStringBuilder.toString());
      return true;
    }
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("TesStatDataManager-save data empty, ignore; isSimpleQB=");
    localStringBuilder.append(this.mForSimpleQB);
    LogUtils.d("TbsStatDataManager", localStringBuilder.toString());
    return false;
  }
  
  public void stat(final StatEntry paramStatEntry, final boolean paramBoolean)
  {
    StatWorkerThread.getInstance().post(new Runnable()
    {
      public void run()
      {
        TbsStatDataManager.this.stat(paramStatEntry, paramBoolean, 255);
      }
    });
  }
  
  public void statCommonData(CommStatData paramCommStatData)
  {
    if (paramCommStatData == null) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramCommStatData.toString());
    localStringBuilder.append("; isSimpleQB=");
    localStringBuilder.append(this.mForSimpleQB);
    LogUtils.d("TbsStatDataManager", localStringBuilder.toString());
    if (paramCommStatData.mDataOp == 1)
    {
      statUniqueCommData(paramCommStatData);
      return;
    }
    if (paramCommStatData.mDataOp == 0)
    {
      statCommDataAddPv(paramCommStatData);
      return;
    }
    getCurrentStatData().mCommonAppInfos.add(paramCommStatData.toCommonAppInfo());
  }
  
  public void userBehaviorStatistics(final UserBehaviorPV paramUserBehaviorPV, final boolean paramBoolean)
  {
    StatWorkerThread.getInstance().post(new Runnable()
    {
      public void run()
      {
        String str;
        if (paramUserBehaviorPV.mBehaviorType == -1) {
          str = paramUserBehaviorPV.mBehaviorAction;
        } else {
          str = Integer.toString(paramUserBehaviorPV.mBehaviorType);
        }
        if (TbsStatDataManager.this.getCurrentStatData().mUserBehaviorPV == null) {
          TbsStatDataManager.this.getCurrentStatData().mUserBehaviorPV = new HashMap();
        }
        Object localObject = (UserBehaviorPV)TbsStatDataManager.this.getCurrentStatData().mUserBehaviorPV.get(str);
        if (localObject == null)
        {
          TbsStatDataManager.this.getCurrentStatData().mUserBehaviorPV.put(str, paramUserBehaviorPV);
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("UB : key=");
          ((StringBuilder)localObject).append(str);
          ((StringBuilder)localObject).append(" pv=");
          ((StringBuilder)localObject).append(paramUserBehaviorPV.mPV);
          ((StringBuilder)localObject).append("; isSimpleQB=");
          ((StringBuilder)localObject).append(TbsStatDataManager.this.mForSimpleQB);
          LogUtils.d("TbsStatDataManager", ((StringBuilder)localObject).toString());
          return;
        }
        int i;
        if (paramBoolean) {
          i = ((UserBehaviorPV)localObject).mPV + paramUserBehaviorPV.mPV;
        } else {
          i = paramUserBehaviorPV.mPV;
        }
        ((UserBehaviorPV)localObject).mPV = i;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("UB : key=");
        localStringBuilder.append(str);
        localStringBuilder.append(" pv=");
        localStringBuilder.append(((UserBehaviorPV)localObject).mPV);
        localStringBuilder.append("; isSimpleQB=");
        localStringBuilder.append(TbsStatDataManager.this.mForSimpleQB);
        LogUtils.d("TbsStatDataManager", localStringBuilder.toString());
      }
    });
  }
  
  protected static class StatWorkerThread
  {
    private static StatWorkerThread sInstance;
    private static byte[] sInstanceLock = new byte[0];
    private Handler mHandler = null;
    private Looper mLooper = null;
    
    public static StatWorkerThread getInstance()
    {
      if (sInstance == null) {
        synchronized (sInstanceLock)
        {
          if (sInstance == null) {
            sInstance = new StatWorkerThread();
          }
        }
      }
      return sInstance;
    }
    
    public final boolean post(Runnable paramRunnable)
    {
      Handler localHandler = this.mHandler;
      if (localHandler != null) {
        return localHandler.post(paramRunnable);
      }
      return false;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\stat\TbsStatDataManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */