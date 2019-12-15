package com.tencent.tbs.common.baseinfo;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.provider.Settings.System;
import android.text.TextUtils;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.utils.ByteUtils;
import com.tencent.common.utils.DesUtils;
import com.tencent.common.utils.FileUtils;
import com.tencent.common.utils.LinuxToolsJni;
import com.tencent.common.utils.LogUtils;
import com.tencent.tbs.common.MTT.GuidRsp;
import com.tencent.tbs.common.push.PushDataParserUtils;
import com.tencent.tbs.common.settings.PublicSettingManager;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.concurrent.Executor;

public class GuidLoader
{
  static final String APP_DEMO = "com.tencent.tbs";
  static final String APP_QB = "com.tencent.mtt";
  static final String APP_QQ = "com.tencent.mobileqq";
  static final String APP_QZ = "com.qzone";
  static final String APP_WX = "com.tencent.mm";
  static final String GUID_FILE = "guid.txt";
  static final int GUID_HOLDER_COUNT = 7;
  private static final int GUID_LENGTH = 16;
  private static final byte[] GUID_VALIDATE_KEY = { 99, -41, -112, 99, 60, 14, 47, -61, 70, -17, -123, 55, 66, 31, -99, 74, 70, 61, 88, -13, -118, -107, -20, -124 };
  static final String[] HOST_APPS = { "com.tencent.mtt", "com.tencent.mm", "com.tencent.mobileqq", "com.qzone", "com.tencent.tbs" };
  static final byte HOST_APP_QB = 0;
  static final byte HOST_OTHER = 7;
  private static final byte LOAD_GUID_FROM_HOST_APP_RES_NONE = 0;
  private static final byte LOAD_GUID_FROM_HOST_APP_RES_OK = 1;
  static final String PREFERENCE_KEY_MERGE_QB_GUID = "PREFERENCE_KEY_MERGE_QB_GUID";
  static final byte QB_GUID_INDEX = 0;
  static final String QB_GUID_NAME = ".idx";
  static final String QB_GUID_NAME_BACKUP = ".idx.bk";
  static final String QB_GUID_NAME_BEFORE_4_5 = ".id";
  static final byte SD_GUID_INDEX = 6;
  static final byte SYS_GUID_INDEX = 5;
  static final String SYS_SETTING_TES_GUID = "sys_setting_tes_guid";
  static final String SYS_SETTING_TES_GUID_GTIME = "sys_setting_tes_gtime";
  static final String SYS_SETTING_TES_GUID_VCODE = "sys_setting_tes_vcode";
  private static final String TAG = "GuidLoader";
  static final String TBS_ENHANCE_QB_SETTING_GUID = "tbs_enhance_qb_guid";
  static final String TBS_ENHANCE_QB_SETTING_VALIDATION = "tbs_enhance_qb_validation";
  Context mContext = TbsBaseModuleShell.getCallerContext();
  GuidRsp mFinalGuid = null;
  GuidRsp[] mGuids = new GuidRsp[7];
  boolean mHasLoadGuidFromSysSettingAndSdcard;
  boolean mHasSavedCurrentAppGuid;
  LinuxToolsJni mLinuxTools;
  byte mWhoAmI = 7;
  
  public GuidLoader()
  {
    int k = 0;
    this.mHasLoadGuidFromSysSettingAndSdcard = false;
    this.mHasSavedCurrentAppGuid = false;
    this.mLinuxTools = null;
    if (this.mContext == null) {
      this.mContext = TbsBaseModuleShell.getCallerContext();
    }
    int j = k;
    if (this.mContext != null) {
      for (int i = 0;; i = (byte)(i + 1))
      {
        j = k;
        if (i >= HOST_APPS.length) {
          break;
        }
        if (this.mContext.getPackageName().equalsIgnoreCase(HOST_APPS[i]))
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("I am ");
          localStringBuilder.append(HOST_APPS[i]);
          LogUtils.d("GuidLoader", localStringBuilder.toString());
          this.mWhoAmI = i;
        }
      }
    }
    while (j < 7)
    {
      this.mGuids[j] = new GuidRsp();
      j = (byte)(j + 1);
    }
  }
  
  private void compareAllGuids()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("compare all guids begins");
    ((StringBuilder)localObject).append(printAllGuids());
    LogUtils.d("GuidLoader", ((StringBuilder)localObject).toString());
    int i = 1;
    int k;
    for (int j = 0; i < 7; j = k)
    {
      localObject = this.mGuids;
      k = j;
      if (!compareGuid(localObject[j], localObject[i])) {
        k = i;
      }
      i = (byte)(i + 1);
    }
    localObject = this.mGuids[j];
    if (isGUIDValidate(((GuidRsp)localObject).vGuid, ((GuidRsp)localObject).vValidation))
    {
      LogUtils.d("GuidLoader", "the newest guid is validate, check");
      i = this.mWhoAmI;
      if ((i != 7) && (isGuidEqual((GuidRsp)localObject, this.mGuids[i])))
      {
        LogUtils.d("GuidLoader", "app's guid is the newest, use this, check should update syssetting and sdcard");
        this.mFinalGuid = ((GuidRsp)localObject);
        updateSysSettingAndSdCard((GuidRsp)localObject, false);
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("");
        ((StringBuilder)localObject).append(printAllGuids());
        LogUtils.d("GuidLoader", ((StringBuilder)localObject).toString());
        return;
      }
      if (isGUIDValidate(((GuidRsp)localObject).vGuid, ((GuidRsp)localObject).vValidation))
      {
        LogUtils.d("GuidLoader", "app's guid is not the newst, but it is ok, use this guid");
        this.mFinalGuid = ((GuidRsp)localObject);
        saveCurrenAppGuid((GuidRsp)localObject);
        if ((this.mHasLoadGuidFromSysSettingAndSdcard) && (this.mWhoAmI != 7))
        {
          LogUtils.d("GuidLoader", "SECOND: we have load from Sys Settings and SDCard update current");
          updateSysSettingAndSdCard((GuidRsp)localObject, false);
        }
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("");
        ((StringBuilder)localObject).append(printAllGuids());
        LogUtils.d("GuidLoader", ((StringBuilder)localObject).toString());
      }
    }
    else
    {
      LogUtils.d("GuidLoader", "the newest guid is not validate, check has load sys or sdcard");
      if (this.mHasLoadGuidFromSysSettingAndSdcard)
      {
        LogUtils.d("GuidLoader", "all app and sys&&sdcard has no validate guid, wait for the init wup call");
        return;
      }
      LogUtils.d("GuidLoader", "all app has no validate guid, but has not read sys & sdcard, try it");
      loadGuidFromSysSetting();
      loadGuidFromSdcard();
      compareSysSdcardGuid();
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("finished: print All guids:");
      ((StringBuilder)localObject).append(printAllGuids());
      LogUtils.d("GuidLoader", ((StringBuilder)localObject).toString());
    }
  }
  
  private boolean compareGuid(GuidRsp paramGuidRsp1, GuidRsp paramGuidRsp2)
  {
    boolean bool2 = ByteUtils.isAllZeroBytes(paramGuidRsp1.vGuid);
    boolean bool1 = false;
    if (!bool2)
    {
      if (paramGuidRsp1.vGuid.length <= 0) {
        return false;
      }
      if (!ByteUtils.isAllZeroBytes(paramGuidRsp2.vGuid))
      {
        if (paramGuidRsp2.vGuid.length <= 0) {
          return true;
        }
        if (paramGuidRsp1.lGenerateTime >= paramGuidRsp2.lGenerateTime) {
          bool1 = true;
        }
        return bool1;
      }
      return true;
    }
    return false;
  }
  
  private void compareSysSdcardGuid()
  {
    LogUtils.d("GuidLoader", "compare sys sdcard guid");
    Object localObject = this.mGuids;
    int i = 5;
    if (!compareGuid(localObject[5], localObject[6])) {
      i = 6;
    }
    localObject = this.mGuids[i];
    if (isGUIDValidate(((GuidRsp)localObject).vGuid, ((GuidRsp)localObject).vValidation))
    {
      LogUtils.d("GuidLoader", "find the newest guid in syssetting or sdcard");
      this.mFinalGuid = ((GuidRsp)localObject);
      saveCurrenAppGuid((GuidRsp)localObject);
      updateSysSettingAndSdCard((GuidRsp)localObject, false);
      return;
    }
    LogUtils.d("GuidLoader", "has no guid in sys and sdcard at all , wait for the net call");
  }
  
  private Context createAppContext(Context paramContext, String paramString)
  {
    try
    {
      paramContext = paramContext.createPackageContext(paramString, 2);
      return paramContext;
    }
    catch (Throwable paramContext)
    {
      paramContext.printStackTrace();
    }
    return null;
  }
  
  private void enhanceQBGuid()
  {
    for (;;)
    {
      try
      {
        LogUtils.d("GuidLoader", "enhanceQBGuid: begins");
        if (this.mWhoAmI == 0)
        {
          LogUtils.d("GuidLoader", "enhanceQBGuid: I am qb ,ignore");
          return;
        }
        Object localObject3 = loadGuidFromQBSDCard();
        if (localObject3 == null)
        {
          LogUtils.d("GuidLoader", "enhanceQBGuid: load qb guid empty");
          return;
        }
        Object localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("enhanceQBGuid: load from qb, guid=");
        ((StringBuilder)localObject1).append(ByteUtils.byteToHexString(((GuidRsp)localObject3).vGuid));
        ((StringBuilder)localObject1).append(", checksum=");
        ((StringBuilder)localObject1).append(ByteUtils.byteToHexString(((GuidRsp)localObject3).vValidation));
        LogUtils.d("GuidLoader", ((StringBuilder)localObject1).toString());
        try
        {
          localObject1 = this.mContext.getContentResolver();
          Object localObject5 = Settings.System.getString((ContentResolver)localObject1, "tbs_enhance_qb_guid");
          Object localObject4 = Settings.System.getString((ContentResolver)localObject1, "tbs_enhance_qb_validation");
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("enhanceQBGuid: load from seting, guid=");
          localStringBuilder.append((String)localObject5);
          localStringBuilder.append(", validation=");
          localStringBuilder.append((String)localObject4);
          LogUtils.d("GuidLoader", localStringBuilder.toString());
          boolean bool2 = TextUtils.isEmpty((CharSequence)localObject5);
          bool1 = true;
          if ((!bool2) && (!TextUtils.isEmpty((CharSequence)localObject4)))
          {
            localObject5 = ByteUtils.hexStringToByte((String)localObject5);
            localObject4 = ByteUtils.hexStringToByte((String)localObject4);
            if (!isGUIDValidate((byte[])localObject5, (byte[])localObject4))
            {
              LogUtils.d("GuidLoader", "enhanceQBGuid: guid or validation is valid, need rewrite!!!");
            }
            else
            {
              if (ByteUtils.isEqual((byte[])localObject5, ((GuidRsp)localObject3).vGuid)) {
                if (ByteUtils.isEqual((byte[])localObject4, ((GuidRsp)localObject3).vValidation)) {
                  break label477;
                }
              }
              LogUtils.d("GuidLoader", "enhanceQBGuid: guid or validation is not the same, need rewrite!!!");
            }
          }
          else
          {
            LogUtils.d("GuidLoader", "enhanceQBGuid: guid or validation is empty, need rewrite!!!");
          }
          localObject4 = new StringBuilder();
          ((StringBuilder)localObject4).append("enhanceQBGuid:need rewrite=");
          ((StringBuilder)localObject4).append(bool1);
          LogUtils.d("GuidLoader", ((StringBuilder)localObject4).toString());
          if (bool1)
          {
            localObject4 = ByteUtils.byteToHexString(((GuidRsp)localObject3).vGuid);
            localObject3 = ByteUtils.byteToHexString(((GuidRsp)localObject3).vValidation);
            localObject5 = new StringBuilder();
            ((StringBuilder)localObject5).append("enhanceQBGuid: begin write guid: tobePutGuid=");
            ((StringBuilder)localObject5).append((String)localObject4);
            ((StringBuilder)localObject5).append(", tobePutValidate=");
            ((StringBuilder)localObject5).append((String)localObject3);
            LogUtils.d("GuidLoader", ((StringBuilder)localObject5).toString());
            Settings.System.putString((ContentResolver)localObject1, "tbs_enhance_qb_guid", (String)localObject4);
            Settings.System.putString((ContentResolver)localObject1, "tbs_enhance_qb_validation", (String)localObject3);
          }
        }
        catch (Throwable localThrowable)
        {
          localObject3 = new StringBuilder();
          ((StringBuilder)localObject3).append("enhanceQBGuid: exception=");
          ((StringBuilder)localObject3).append(localThrowable.getMessage());
          LogUtils.d("GuidLoader", ((StringBuilder)localObject3).toString());
          localThrowable.printStackTrace();
        }
        return;
      }
      finally {}
      label477:
      boolean bool1 = false;
    }
  }
  
  private File getExtSDcardDir()
  {
    try
    {
      File localFile = Environment.getExternalStorageDirectory();
      if (localFile != null) {
        return localFile;
      }
      localFile = new File("/mnt/sdcard");
      if (localFile.exists()) {
        return localFile;
      }
      localFile = new File("/storage/emulated/0");
      if (localFile.exists()) {
        return localFile;
      }
      localFile = new File("/storage/sdcard0");
      boolean bool = localFile.exists();
      if (bool) {
        return localFile;
      }
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
    return null;
  }
  
  private File getQBSdcardGuidDir()
  {
    Object localObject1;
    try
    {
      localObject1 = getExtSDcardDir();
      if (localObject1 == null) {
        return null;
      }
      Object localObject2 = ((File)localObject1).getAbsolutePath();
      if (TextUtils.isEmpty((CharSequence)localObject2)) {
        return null;
      }
      localObject1 = localObject2;
      if (!((String)localObject2).endsWith(File.separator))
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append((String)localObject2);
        ((StringBuilder)localObject1).append(File.separator);
        localObject1 = ((StringBuilder)localObject1).toString();
      }
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("QQBrowser/.Application");
      localObject1 = new File(((StringBuilder)localObject2).toString());
      localObject2 = localObject1;
      try
      {
        if (((File)localObject1).exists()) {
          return ???;
        }
        ((File)localObject1).mkdirs();
        return (File)localObject1;
      }
      catch (Exception localException1) {}
      localException2.printStackTrace();
    }
    catch (Exception localException2)
    {
      localObject1 = null;
    }
    Object localObject3 = localObject1;
    return (File)localObject3;
  }
  
  private boolean isAppInstalled(Context paramContext, String paramString)
  {
    try
    {
      paramContext = paramContext.getPackageManager().getPackageInfo(paramString, 0);
    }
    catch (Exception paramContext)
    {
      for (;;) {}
    }
    paramContext = null;
    return paramContext != null;
  }
  
  private boolean isGUIDValidate(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    if ((!ByteUtils.isAllZeroBytes(paramArrayOfByte1)) && (!ByteUtils.isAllZeroBytes(paramArrayOfByte2)) && (paramArrayOfByte1.length == 16))
    {
      StringBuilder localStringBuilder = null;
      try
      {
        paramArrayOfByte2 = DesUtils.Des3Encrypt(GUID_VALIDATE_KEY, paramArrayOfByte2, 2);
      }
      catch (Exception paramArrayOfByte2)
      {
        paramArrayOfByte2.printStackTrace();
        paramArrayOfByte2 = localStringBuilder;
      }
      if ((paramArrayOfByte2 != null) && (paramArrayOfByte2.length != 0))
      {
        int i = 0;
        while (i < 16)
        {
          if (paramArrayOfByte1[i] != paramArrayOfByte2[i])
          {
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("guid  validation is not ok. guid[i]=");
            localStringBuilder.append(paramArrayOfByte1[i]);
            localStringBuilder.append(" validation[i]=");
            localStringBuilder.append(paramArrayOfByte2[i]);
            LogUtils.d("GuidLoader", localStringBuilder.toString());
            return false;
          }
          i += 1;
        }
        return true;
      }
      LogUtils.d("GuidLoader", " checksum atfter decrypt is not validate, ignore");
      return false;
    }
    LogUtils.d("GuidLoader", "isGUIDValidate, guid or checksum is not validate, ignore");
    return false;
  }
  
  private boolean isGuidEqual(GuidRsp paramGuidRsp1, GuidRsp paramGuidRsp2)
  {
    return ByteUtils.isEqual(paramGuidRsp1.vGuid, paramGuidRsp2.vGuid);
  }
  
  private boolean linuxChmod(File paramFile, String paramString)
  {
    if (paramFile != null)
    {
      if (TextUtils.isEmpty(paramString)) {
        return false;
      }
      if (this.mLinuxTools == null) {
        this.mLinuxTools = new LinuxToolsJni();
      }
      try
      {
        if (LinuxToolsJni.gJniloaded)
        {
          this.mLinuxTools.Chmod(paramFile.getAbsolutePath(), "644");
          paramString = new StringBuilder();
          paramString.append("Chmod for ");
          paramString.append(paramFile);
          paramString.append(", compelet");
          LogUtils.d("GuidLoader", paramString.toString());
          return true;
        }
      }
      catch (Throwable paramString)
      {
        paramString.printStackTrace();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Chmod for ");
        localStringBuilder.append(paramFile);
        localStringBuilder.append(" error: ");
        localStringBuilder.append(paramString.getMessage());
        LogUtils.d("GuidLoader", localStringBuilder.toString());
      }
      return false;
    }
    return false;
  }
  
  private byte loadGuidFromHostApp()
  {
    LogUtils.d("GuidLoader", "load guid from host app");
    String[] arrayOfString = HOST_APPS;
    int i = 0;
    byte b = 0;
    Object localObject1;
    Object localObject3;
    if (i < arrayOfString.length)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("load guid from ");
      ((StringBuilder)localObject1).append(arrayOfString[i]);
      LogUtils.d("GuidLoader", ((StringBuilder)localObject1).toString());
      if ((this.mWhoAmI != i) && (!isAppInstalled(this.mContext, arrayOfString[i])))
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append(arrayOfString[i]);
        ((StringBuilder)localObject1).append(" is not installed, continue load from other host");
        LogUtils.d("GuidLoader", ((StringBuilder)localObject1).toString());
      }
      else
      {
        if (this.mWhoAmI == i) {
          localObject3 = this.mContext;
        } else {
          localObject3 = createAppContext(this.mContext, arrayOfString[i]);
        }
        if (localObject3 == null)
        {
          LogUtils.d("GuidLoader", "the host context is null, exception happen, record, continue load from other app");
        }
        else
        {
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append(arrayOfString[i]);
          ((StringBuilder)localObject1).append(" has installed, continue read file");
          LogUtils.d("GuidLoader", ((StringBuilder)localObject1).toString());
        }
      }
    }
    label586:
    label596:
    label604:
    for (;;)
    {
      try
      {
        LogUtils.d("GuidLoader", "guidShareDir from getTbsDataShareDirWithoutChmod");
        localObject1 = FileUtils.getTbsDataShareDirWithoutChmod((Context)localObject3);
        if (localObject1 == null) {
          break label596;
        }
        localObject1 = new File((File)localObject1, "guid.txt");
        if ((!((File)localObject1).exists()) || (!((File)localObject1).canRead())) {
          break label596;
        }
        LogUtils.d("GuidLoader", "the host has primary GUID file, and can read, use it");
        j = 1;
        if (j != 0) {
          break label604;
        }
        localObject3 = FileUtils.getCommonDataShareDirWithoutChmod((Context)localObject3);
        if (localObject3 == null) {
          break label604;
        }
        localObject3 = new File((File)localObject3, "guid.txt");
        if ((!((File)localObject3).exists()) || (!((File)localObject3).canRead())) {
          break label604;
        }
        LogUtils.d("GuidLoader", "the host has secondary GUID file, and can read, use it");
        localObject1 = localObject3;
        if (localObject1 == null)
        {
          LogUtils.d("GuidLoader", "the host did not has guid yet, ignore this app, load from other");
        }
        else
        {
          localObject3 = new StringBuilder();
          ((StringBuilder)localObject3).append("begin read from file ");
          ((StringBuilder)localObject3).append(((File)localObject1).getAbsolutePath());
          LogUtils.d("GuidLoader", ((StringBuilder)localObject3).toString());
          localObject3 = FileUtils.read((File)localObject1);
          if ((localObject3 != null) && (((ByteBuffer)localObject3).position() > 0))
          {
            localObject1 = (GuidRsp)PushDataParserUtils.parseRawData(GuidRsp.class, (ByteBuffer)localObject3);
            FileUtils.getInstance().releaseByteBuffer((ByteBuffer)localObject3);
            if ((localObject1 != null) && (isGUIDValidate(((GuidRsp)localObject1).vGuid, ((GuidRsp)localObject1).vValidation)))
            {
              localObject3 = new StringBuilder();
              ((StringBuilder)localObject3).append("load guid from this app OK, guid=");
              ((StringBuilder)localObject3).append(ByteUtils.byteToHexString(((GuidRsp)localObject1).vGuid));
              ((StringBuilder)localObject3).append(", checksum=");
              ((StringBuilder)localObject3).append(ByteUtils.byteToHexString(((GuidRsp)localObject1).vValidation));
              LogUtils.d("GuidLoader", ((StringBuilder)localObject3).toString());
              try
              {
                this.mGuids[i] = localObject1;
                b = 1;
              }
              catch (Throwable localThrowable1)
              {
                b = 1;
                continue;
              }
            }
            else
            {
              LogUtils.d("GuidLoader", "load guid from this app is not in guid type, ignore, record");
              break label586;
            }
          }
          else
          {
            LogUtils.d("GuidLoader", "the host has guid file ,but the file is empty, exception happen, record");
            localThrowable1.delete();
            FileUtils.getInstance().releaseByteBuffer((ByteBuffer)localObject3);
          }
        }
      }
      catch (Throwable localThrowable2)
      {
        localThrowable2.printStackTrace();
      }
      i = (byte)(i + 1);
      break;
      return b;
      Object localObject2 = null;
      int j = 0;
    }
  }
  
  private GuidRsp loadGuidFromQBSDCard()
  {
    LogUtils.d("GuidLoader", "load from qb's sd card");
    if (!FileUtils.hasSDcard())
    {
      LogUtils.d("GuidLoader", "this device has no sd card, ignore");
      return null;
    }
    File localFile = getQBSdcardGuidDir();
    if (localFile == null)
    {
      LogUtils.d("GuidLoader", "this device has sd card, but do not have guid dir, ignore");
      return null;
    }
    try
    {
      GuidRsp localGuidRsp1 = loadQBGuidFromSDCard(localFile, "com.tencent.mtt", ".idx");
      if ((localGuidRsp1 != null) && (isGUIDValidate(localGuidRsp1.vGuid, localGuidRsp1.vValidation)))
      {
        LogUtils.d("GuidLoader", "load guid from qb sdcard ok");
        return localGuidRsp1;
      }
    }
    catch (Throwable localThrowable1)
    {
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("load guid from qb sdcard exception, e=");
      ((StringBuilder)localObject2).append(localThrowable1.getMessage());
      LogUtils.d("GuidLoader", ((StringBuilder)localObject2).toString());
      localThrowable1.printStackTrace();
      try
      {
        Object localObject1 = getExtSDcardDir();
        if (localObject1 != null)
        {
          localObject2 = ((File)localObject1).getAbsolutePath();
          localObject1 = localObject2;
          if (!((String)localObject2).endsWith(File.separator))
          {
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append((String)localObject2);
            ((StringBuilder)localObject1).append(File.separator);
            localObject1 = ((StringBuilder)localObject1).toString();
          }
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append((String)localObject1);
          ((StringBuilder)localObject2).append(".Application");
          localObject1 = loadQBGuidFromSDCard(new File(((StringBuilder)localObject2).toString()), "", ".idx.bk");
          if ((localObject1 != null) && (isGUIDValidate(((GuidRsp)localObject1).vGuid, ((GuidRsp)localObject1).vValidation)))
          {
            LogUtils.d("GuidLoader", "load guid from qb 7.4 sdcard backup ok, new guid");
            return (GuidRsp)localObject1;
          }
        }
      }
      catch (Throwable localThrowable2)
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("load guid from qb 7.4 sdcard backup exception, e=");
        ((StringBuilder)localObject2).append(localThrowable2.getMessage());
        LogUtils.d("GuidLoader", ((StringBuilder)localObject2).toString());
        localThrowable2.printStackTrace();
        try
        {
          GuidRsp localGuidRsp2 = loadQBGuidFromSDCard(localFile, "com.tencent.mtt", ".id");
          if ((localGuidRsp2 != null) && (isGUIDValidate(localGuidRsp2.vGuid, localGuidRsp2.vValidation)))
          {
            LogUtils.d("GuidLoader", "load guid from qb sdcard ok, old guid");
            return localGuidRsp2;
          }
        }
        catch (Throwable localThrowable3)
        {
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("load guid from old qb sdcard exception, e=");
          ((StringBuilder)localObject2).append(localThrowable3.getMessage());
          LogUtils.d("GuidLoader", ((StringBuilder)localObject2).toString());
          localThrowable3.printStackTrace();
        }
      }
    }
    return null;
  }
  
  /* Error */
  private void loadGuidFromSdcard()
  {
    // Byte code:
    //   0: ldc 71
    //   2: ldc_w 546
    //   5: invokestatic 173	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   8: aload_0
    //   9: iconst_1
    //   10: putfield 141	com/tencent/tbs/common/baseinfo/GuidLoader:mHasLoadGuidFromSysSettingAndSdcard	Z
    //   13: invokestatic 520	com/tencent/common/utils/FileUtils:hasSDcard	()Z
    //   16: ifne +12 -> 28
    //   19: ldc 71
    //   21: ldc_w 522
    //   24: invokestatic 173	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   27: return
    //   28: aconst_null
    //   29: astore 4
    //   31: aconst_null
    //   32: astore_2
    //   33: aload_2
    //   34: astore_1
    //   35: invokestatic 549	com/tencent/common/utils/FileUtils:getTesSdcardShareDir	()Ljava/io/File;
    //   38: astore 5
    //   40: aload_2
    //   41: astore_1
    //   42: new 356	java/io/File
    //   45: dup
    //   46: aload 5
    //   48: ldc 27
    //   50: invokespecial 468	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   53: astore_3
    //   54: aload_2
    //   55: astore_1
    //   56: aload_3
    //   57: invokevirtual 365	java/io/File:exists	()Z
    //   60: ifne +14 -> 74
    //   63: aload_2
    //   64: astore_1
    //   65: ldc 71
    //   67: ldc_w 551
    //   70: invokestatic 173	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   73: return
    //   74: aload_2
    //   75: astore_1
    //   76: new 553	com/tencent/tbs/common/utils/TBSFileLock
    //   79: dup
    //   80: aload 5
    //   82: ldc 27
    //   84: invokespecial 554	com/tencent/tbs/common/utils/TBSFileLock:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   87: astore_2
    //   88: aload_2
    //   89: invokevirtual 557	com/tencent/tbs/common/utils/TBSFileLock:lock	()V
    //   92: aload_3
    //   93: invokestatic 486	com/tencent/common/utils/FileUtils:read	(Ljava/io/File;)Ljava/nio/ByteBuffer;
    //   96: astore_3
    //   97: aload_3
    //   98: ifnull +179 -> 277
    //   101: aload_3
    //   102: invokevirtual 492	java/nio/ByteBuffer:position	()I
    //   105: ifgt +6 -> 111
    //   108: goto +169 -> 277
    //   111: ldc 125
    //   113: aload_3
    //   114: invokestatic 498	com/tencent/tbs/common/push/PushDataParserUtils:parseRawData	(Ljava/lang/Class;Ljava/nio/ByteBuffer;)Lcom/taf/JceStruct;
    //   117: checkcast 125	com/tencent/tbs/common/MTT/GuidRsp
    //   120: astore_1
    //   121: invokestatic 502	com/tencent/common/utils/FileUtils:getInstance	()Lcom/tencent/common/utils/FileUtils;
    //   124: aload_3
    //   125: invokevirtual 506	com/tencent/common/utils/FileUtils:releaseByteBuffer	(Ljava/nio/ByteBuffer;)Z
    //   128: pop
    //   129: aload_1
    //   130: ifnonnull +16 -> 146
    //   133: ldc 71
    //   135: ldc_w 559
    //   138: invokestatic 173	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   141: aload_2
    //   142: invokevirtual 562	com/tencent/tbs/common/utils/TBSFileLock:releaseLock	()V
    //   145: return
    //   146: aload_0
    //   147: aload_1
    //   148: getfield 206	com/tencent/tbs/common/MTT/GuidRsp:vGuid	[B
    //   151: aload_1
    //   152: getfield 209	com/tencent/tbs/common/MTT/GuidRsp:vValidation	[B
    //   155: invokespecial 213	com/tencent/tbs/common/baseinfo/GuidLoader:isGUIDValidate	([B[B)Z
    //   158: ifne +24 -> 182
    //   161: ldc 71
    //   163: ldc_w 564
    //   166: invokestatic 173	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   169: ldc 71
    //   171: ldc_w 559
    //   174: invokestatic 173	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   177: aload_2
    //   178: invokevirtual 562	com/tencent/tbs/common/utils/TBSFileLock:releaseLock	()V
    //   181: return
    //   182: new 157	java/lang/StringBuilder
    //   185: dup
    //   186: invokespecial 158	java/lang/StringBuilder:<init>	()V
    //   189: astore_3
    //   190: aload_3
    //   191: ldc_w 566
    //   194: invokevirtual 164	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   197: pop
    //   198: aload_3
    //   199: aload_1
    //   200: getfield 206	com/tencent/tbs/common/MTT/GuidRsp:vGuid	[B
    //   203: invokestatic 294	com/tencent/common/utils/ByteUtils:byteToHexString	([B)Ljava/lang/String;
    //   206: invokevirtual 164	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   209: pop
    //   210: aload_3
    //   211: ldc_w 568
    //   214: invokevirtual 164	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   217: pop
    //   218: aload_3
    //   219: aload_1
    //   220: getfield 209	com/tencent/tbs/common/MTT/GuidRsp:vValidation	[B
    //   223: invokestatic 294	com/tencent/common/utils/ByteUtils:byteToHexString	([B)Ljava/lang/String;
    //   226: invokevirtual 164	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   229: pop
    //   230: aload_3
    //   231: ldc_w 570
    //   234: invokevirtual 164	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   237: pop
    //   238: aload_3
    //   239: aload_1
    //   240: getfield 261	com/tencent/tbs/common/MTT/GuidRsp:lGenerateTime	J
    //   243: invokevirtual 573	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   246: pop
    //   247: ldc 71
    //   249: aload_3
    //   250: invokevirtual 167	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   253: invokestatic 173	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   256: aload_0
    //   257: getfield 127	com/tencent/tbs/common/baseinfo/GuidLoader:mGuids	[Lcom/tencent/tbs/common/MTT/GuidRsp;
    //   260: bipush 6
    //   262: aload_1
    //   263: aastore
    //   264: ldc 71
    //   266: ldc_w 559
    //   269: invokestatic 173	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   272: aload_2
    //   273: invokevirtual 562	com/tencent/tbs/common/utils/TBSFileLock:releaseLock	()V
    //   276: return
    //   277: invokestatic 502	com/tencent/common/utils/FileUtils:getInstance	()Lcom/tencent/common/utils/FileUtils;
    //   280: aload_3
    //   281: invokevirtual 506	com/tencent/common/utils/FileUtils:releaseByteBuffer	(Ljava/nio/ByteBuffer;)Z
    //   284: pop
    //   285: ldc 71
    //   287: ldc_w 575
    //   290: invokestatic 173	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   293: ldc 71
    //   295: ldc_w 559
    //   298: invokestatic 173	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   301: aload_2
    //   302: invokevirtual 562	com/tencent/tbs/common/utils/TBSFileLock:releaseLock	()V
    //   305: return
    //   306: astore_1
    //   307: goto +42 -> 349
    //   310: astore_3
    //   311: goto +15 -> 326
    //   314: astore_3
    //   315: aload_1
    //   316: astore_2
    //   317: aload_3
    //   318: astore_1
    //   319: goto +30 -> 349
    //   322: astore_3
    //   323: aload 4
    //   325: astore_2
    //   326: aload_2
    //   327: astore_1
    //   328: aload_3
    //   329: invokevirtual 278	java/lang/Throwable:printStackTrace	()V
    //   332: aload_2
    //   333: ifnull +15 -> 348
    //   336: ldc 71
    //   338: ldc_w 559
    //   341: invokestatic 173	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   344: aload_2
    //   345: invokevirtual 562	com/tencent/tbs/common/utils/TBSFileLock:releaseLock	()V
    //   348: return
    //   349: aload_2
    //   350: ifnull +15 -> 365
    //   353: ldc 71
    //   355: ldc_w 559
    //   358: invokestatic 173	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   361: aload_2
    //   362: invokevirtual 562	com/tencent/tbs/common/utils/TBSFileLock:releaseLock	()V
    //   365: aload_1
    //   366: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	367	0	this	GuidLoader
    //   34	229	1	localObject1	Object
    //   306	10	1	localObject2	Object
    //   318	48	1	localObject3	Object
    //   32	330	2	localObject4	Object
    //   53	228	3	localObject5	Object
    //   310	1	3	localThrowable1	Throwable
    //   314	4	3	localObject6	Object
    //   322	7	3	localThrowable2	Throwable
    //   29	295	4	localObject7	Object
    //   38	43	5	localFile	File
    // Exception table:
    //   from	to	target	type
    //   88	97	306	finally
    //   101	108	306	finally
    //   111	129	306	finally
    //   146	169	306	finally
    //   182	264	306	finally
    //   277	293	306	finally
    //   88	97	310	java/lang/Throwable
    //   101	108	310	java/lang/Throwable
    //   111	129	310	java/lang/Throwable
    //   146	169	310	java/lang/Throwable
    //   182	264	310	java/lang/Throwable
    //   277	293	310	java/lang/Throwable
    //   35	40	314	finally
    //   42	54	314	finally
    //   56	63	314	finally
    //   65	73	314	finally
    //   76	88	314	finally
    //   328	332	314	finally
    //   35	40	322	java/lang/Throwable
    //   42	54	322	java/lang/Throwable
    //   56	63	322	java/lang/Throwable
    //   65	73	322	java/lang/Throwable
    //   76	88	322	java/lang/Throwable
  }
  
  private void loadGuidFromSysSetting()
  {
    try
    {
      LogUtils.d("GuidLoader", "load guid from sys setting begin");
      this.mHasLoadGuidFromSysSettingAndSdcard = true;
      Object localObject = this.mContext.getContentResolver();
      String str1 = Settings.System.getString((ContentResolver)localObject, "sys_setting_tes_guid");
      if (TextUtils.isEmpty(str1))
      {
        LogUtils.d("GuidLoader", "can not find guid in system setting");
        return;
      }
      String str2 = Settings.System.getString((ContentResolver)localObject, "sys_setting_tes_vcode");
      if (TextUtils.isEmpty(str2))
      {
        LogUtils.d("GuidLoader", "can not find guid checksum, in system setting");
        return;
      }
      long l2 = Settings.System.getLong((ContentResolver)localObject, "sys_setting_tes_gtime", -1L);
      long l1 = l2;
      if (l2 <= 0L)
      {
        LogUtils.d("GuidLoader", "g time smaller than 0, continue use it anyway");
        l1 = 0L;
      }
      localObject = ByteUtils.hexStringToByte(str1);
      byte[] arrayOfByte = ByteUtils.hexStringToByte(str2);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("guid in system setting, guid=");
      localStringBuilder.append(str1);
      localStringBuilder.append(", checksum=");
      localStringBuilder.append(str2);
      localStringBuilder.append(", gtime=");
      localStringBuilder.append(l1);
      LogUtils.d("GuidLoader", localStringBuilder.toString());
      if (isGUIDValidate((byte[])localObject, arrayOfByte))
      {
        this.mGuids[5].vGuid = ((byte[])localObject);
        this.mGuids[5].vValidation = arrayOfByte;
        this.mGuids[5].lGenerateTime = l1;
      }
      LogUtils.d("GuidLoader", "load guid from sys setting end");
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  private GuidRsp loadQBGuidFromSDCard(File paramFile, String paramString1, String paramString2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("load qb guid from sdcard, filetail=");
    localStringBuilder.append(paramString2);
    LogUtils.d("GuidLoader", localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString1);
    localStringBuilder.append(paramString2);
    paramFile = new File(paramFile, localStringBuilder.toString());
    if (!paramFile.exists())
    {
      paramString1 = new StringBuilder();
      paramString1.append("the sdcard did not has qb's guid yet, ignore, file=");
      paramString1.append(paramFile.getAbsolutePath());
      LogUtils.d("GuidLoader", paramString1.toString());
      return null;
    }
    paramString1 = new StringBuilder();
    paramString1.append("the sdcard HAS qb's guid!!!, file=");
    paramString1.append(paramFile.getAbsolutePath());
    LogUtils.d("GuidLoader", paramString1.toString());
    paramFile = FileUtils.read(paramFile);
    paramString1 = new byte[paramFile.position()];
    paramFile.position(0);
    paramFile.get(paramString1);
    FileUtils.getInstance().releaseByteBuffer(paramFile);
    return splitBytes(paramString1, paramString2);
  }
  
  private void mergeQBGuid()
  {
    if (PublicSettingManager.getInstance().getCloudSwitch("PREFERENCE_KEY_MERGE_QB_GUID", 1) == 0)
    {
      LogUtils.d("GuidLoader", "mergeQBGuid: load guid ok, server did not request us to merger qb guid, ignore");
      return;
    }
    int i = this.mWhoAmI;
    if ((i != 7) && (i != 0))
    {
      LogUtils.d("GuidLoader", "mergeQBGuid: load guid ok, server request us to merger qb guid, now do it");
      GuidRsp localGuidRsp = this.mGuids[0];
      if ((localGuidRsp != null) && (isGUIDValidate(localGuidRsp.vGuid, localGuidRsp.vValidation)))
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("mergeQBGuid: load qb's guid from tbs succ, qb tbs guid=");
        localStringBuilder.append(ByteUtils.byteToHexString(localGuidRsp.vGuid));
        localStringBuilder.append(", validation=");
        localStringBuilder.append(ByteUtils.byteToHexString(localGuidRsp.vValidation));
        LogUtils.d("GuidLoader", localStringBuilder.toString());
      }
      else
      {
        localGuidRsp = loadGuidFromQBSDCard();
        if ((localGuidRsp == null) || (!isGUIDValidate(localGuidRsp.vGuid, localGuidRsp.vValidation))) {
          break label331;
        }
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("mergeQBGuid: load qb's guid from sdcard succ, qb sdcard guid=");
        localStringBuilder.append(ByteUtils.byteToHexString(localGuidRsp.vGuid));
        localStringBuilder.append(", validation=");
        localStringBuilder.append(ByteUtils.byteToHexString(localGuidRsp.vGuid));
        LogUtils.d("GuidLoader", localStringBuilder.toString());
      }
      if (ByteUtils.isEqual(this.mFinalGuid.vGuid, localGuidRsp.vGuid))
      {
        LogUtils.d("GuidLoader", "mergeQBGuid: load qb's guid ok, but is the same as tbs's");
        updateSysSettingAndSdCard(this.mFinalGuid, true);
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("mergeQBGuid: my guid is different from qb's: mine=");
      localStringBuilder.append(ByteUtils.byteToHexString(this.mFinalGuid.vGuid));
      localStringBuilder.append(", qb's=");
      localStringBuilder.append(ByteUtils.byteToHexString(localGuidRsp.vGuid));
      LogUtils.d("GuidLoader", localStringBuilder.toString());
      this.mFinalGuid = localGuidRsp;
      BrowserExecutorSupplier.forIoTasks().execute(new Runnable()
      {
        public void run()
        {
          LogUtils.d("GuidLoader", "mergeQBGuid: qb's guid is diffrent from tbs's, update files");
          if (GuidLoader.this.mFinalGuid.lGenerateTime <= 0L) {
            GuidLoader.this.mFinalGuid.lGenerateTime = (System.currentTimeMillis() / 1000L);
          }
          GuidLoader localGuidLoader = GuidLoader.this;
          localGuidLoader.saveCurrenAppGuid(localGuidLoader.mFinalGuid);
          localGuidLoader = GuidLoader.this;
          localGuidLoader.saveGuidToSysSetting(localGuidLoader.mFinalGuid);
          localGuidLoader = GuidLoader.this;
          localGuidLoader.saveGuidToSdcard(localGuidLoader.mFinalGuid);
          LogUtils.d("GuidLoader", "mergeQBGuid: save qb's guid succ");
        }
      });
      return;
      label331:
      LogUtils.d("GuidLoader", "mergeQBGuid: load qb's guid failed, ignore");
      return;
    }
    LogUtils.d("GuidLoader", "mergeQBGuid: this is not the 3 main host app, ignore merge qb guid");
  }
  
  private String printAllGuids()
  {
    LogUtils.d("GuidLoader", "");
    LogUtils.d("GuidLoader", "-------------------ALL Guids-------------------");
    for (int i = 0; i < 7; i = (byte)(i + 1))
    {
      String str1 = ByteUtils.byteToHexString(this.mGuids[i].vGuid);
      String str2 = ByteUtils.byteToHexString(this.mGuids[i].vValidation);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("guid ");
      localStringBuilder.append(i);
      localStringBuilder.append(": guid=");
      localStringBuilder.append(str1);
      localStringBuilder.append(", validate=");
      localStringBuilder.append(str2);
      localStringBuilder.append(", time=");
      localStringBuilder.append(this.mGuids[i].lGenerateTime);
      LogUtils.d("GuidLoader", localStringBuilder.toString());
    }
    LogUtils.d("GuidLoader", "-------------------ALL Guids-------------------");
    LogUtils.d("GuidLoader", "");
    return "";
  }
  
  private boolean readAndValidate(File paramFile)
  {
    if ((paramFile != null) && (paramFile.exists()))
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("readAndValidate: begin validate from file ");
      ((StringBuilder)localObject).append(paramFile.getAbsolutePath());
      LogUtils.d("GuidLoader", ((StringBuilder)localObject).toString());
      try
      {
        paramFile = FileUtils.read(paramFile);
        if ((paramFile != null) && (paramFile.position() > 0))
        {
          localObject = (GuidRsp)PushDataParserUtils.parseRawData(GuidRsp.class, paramFile);
          FileUtils.getInstance().releaseByteBuffer(paramFile);
          if ((localObject != null) && (isGUIDValidate(((GuidRsp)localObject).vGuid, ((GuidRsp)localObject).vValidation)))
          {
            LogUtils.d("GuidLoader", "readAndValidate: guid is ok");
            return true;
          }
          LogUtils.d("GuidLoader", "readAndValidate: guid is empty or not valid");
          return false;
        }
        LogUtils.d("GuidLoader", "readAndValidate: the host has guid file ,but the file is empty");
        FileUtils.getInstance().releaseByteBuffer(paramFile);
        return false;
      }
      catch (Throwable paramFile)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("readAndValidate: exception=");
        ((StringBuilder)localObject).append(paramFile.getMessage());
        LogUtils.d("GuidLoader", ((StringBuilder)localObject).toString());
        paramFile.printStackTrace();
        return false;
      }
    }
    LogUtils.d("GuidLoader", "readAndValidate: guid file is Null or non-exist");
    return false;
  }
  
  /* Error */
  private void saveGuidToSdcard(GuidRsp paramGuidRsp)
  {
    // Byte code:
    //   0: ldc 71
    //   2: ldc_w 680
    //   5: invokestatic 173	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   8: invokestatic 520	com/tencent/common/utils/FileUtils:hasSDcard	()Z
    //   11: ifne +12 -> 23
    //   14: ldc 71
    //   16: ldc_w 522
    //   19: invokestatic 173	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   22: return
    //   23: aload_1
    //   24: ifnull +176 -> 200
    //   27: aload_0
    //   28: aload_1
    //   29: getfield 206	com/tencent/tbs/common/MTT/GuidRsp:vGuid	[B
    //   32: aload_1
    //   33: getfield 209	com/tencent/tbs/common/MTT/GuidRsp:vValidation	[B
    //   36: invokespecial 213	com/tencent/tbs/common/baseinfo/GuidLoader:isGUIDValidate	([B[B)Z
    //   39: ifne +6 -> 45
    //   42: goto +158 -> 200
    //   45: aconst_null
    //   46: astore 4
    //   48: aconst_null
    //   49: astore_3
    //   50: aload_3
    //   51: astore_2
    //   52: invokestatic 549	com/tencent/common/utils/FileUtils:getTesSdcardShareDir	()Ljava/io/File;
    //   55: astore 6
    //   57: aload_3
    //   58: astore_2
    //   59: new 356	java/io/File
    //   62: dup
    //   63: aload 6
    //   65: ldc 27
    //   67: invokespecial 468	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   70: astore 5
    //   72: aload_3
    //   73: astore_2
    //   74: new 553	com/tencent/tbs/common/utils/TBSFileLock
    //   77: dup
    //   78: aload 6
    //   80: ldc 27
    //   82: invokespecial 554	com/tencent/tbs/common/utils/TBSFileLock:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   85: astore_3
    //   86: aload_3
    //   87: invokevirtual 557	com/tencent/tbs/common/utils/TBSFileLock:lock	()V
    //   90: aload 5
    //   92: invokevirtual 365	java/io/File:exists	()Z
    //   95: ifne +17 -> 112
    //   98: ldc 71
    //   100: ldc_w 682
    //   103: invokestatic 173	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   106: aload 5
    //   108: invokevirtual 685	java/io/File:createNewFile	()Z
    //   111: pop
    //   112: aload 5
    //   114: aload_1
    //   115: invokestatic 689	com/tencent/tbs/common/push/PushDataParserUtils:jce2Bytes	(Lcom/taf/JceStruct;)[B
    //   118: invokestatic 693	com/tencent/common/utils/FileUtils:save	(Ljava/io/File;[B)Z
    //   121: pop
    //   122: aload_0
    //   123: getfield 127	com/tencent/tbs/common/baseinfo/GuidLoader:mGuids	[Lcom/tencent/tbs/common/MTT/GuidRsp;
    //   126: bipush 6
    //   128: aload_1
    //   129: aastore
    //   130: aload_3
    //   131: invokevirtual 562	com/tencent/tbs/common/utils/TBSFileLock:releaseLock	()V
    //   134: goto +39 -> 173
    //   137: astore_1
    //   138: goto +44 -> 182
    //   141: astore_2
    //   142: aload_3
    //   143: astore_1
    //   144: aload_2
    //   145: astore_3
    //   146: goto +13 -> 159
    //   149: astore_1
    //   150: aload_2
    //   151: astore_3
    //   152: goto +30 -> 182
    //   155: astore_3
    //   156: aload 4
    //   158: astore_1
    //   159: aload_1
    //   160: astore_2
    //   161: aload_3
    //   162: invokevirtual 389	java/lang/Exception:printStackTrace	()V
    //   165: aload_1
    //   166: ifnull +15 -> 181
    //   169: aload_1
    //   170: invokevirtual 562	com/tencent/tbs/common/utils/TBSFileLock:releaseLock	()V
    //   173: ldc 71
    //   175: ldc_w 695
    //   178: invokestatic 173	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   181: return
    //   182: aload_3
    //   183: ifnull +15 -> 198
    //   186: aload_3
    //   187: invokevirtual 562	com/tencent/tbs/common/utils/TBSFileLock:releaseLock	()V
    //   190: ldc 71
    //   192: ldc_w 695
    //   195: invokestatic 173	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   198: aload_1
    //   199: athrow
    //   200: ldc 71
    //   202: ldc_w 697
    //   205: invokestatic 173	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   208: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	209	0	this	GuidLoader
    //   0	209	1	paramGuidRsp	GuidRsp
    //   51	23	2	localObject1	Object
    //   141	10	2	localException1	Exception
    //   160	1	2	localGuidRsp	GuidRsp
    //   49	103	3	localObject2	Object
    //   155	32	3	localException2	Exception
    //   46	111	4	localObject3	Object
    //   70	43	5	localFile1	File
    //   55	24	6	localFile2	File
    // Exception table:
    //   from	to	target	type
    //   86	112	137	finally
    //   112	130	137	finally
    //   86	112	141	java/lang/Exception
    //   112	130	141	java/lang/Exception
    //   52	57	149	finally
    //   59	72	149	finally
    //   74	86	149	finally
    //   161	165	149	finally
    //   52	57	155	java/lang/Exception
    //   59	72	155	java/lang/Exception
    //   74	86	155	java/lang/Exception
  }
  
  private void saveGuidToSysSetting(GuidRsp paramGuidRsp)
  {
    LogUtils.d("GuidLoader", "save guid to sys setting");
    if (paramGuidRsp != null) {}
    try
    {
      if (isGUIDValidate(paramGuidRsp.vGuid, paramGuidRsp.vValidation))
      {
        localObject = ByteUtils.byteToHexString(paramGuidRsp.vGuid);
        String str = ByteUtils.byteToHexString(paramGuidRsp.vValidation);
        ContentResolver localContentResolver = this.mContext.getContentResolver();
        Settings.System.putString(localContentResolver, "sys_setting_tes_guid", (String)localObject);
        Settings.System.putString(localContentResolver, "sys_setting_tes_vcode", str);
        Settings.System.putLong(localContentResolver, "sys_setting_tes_gtime", paramGuidRsp.lGenerateTime);
        LogUtils.d("GuidLoader", "save guid to sys setting success");
        this.mGuids[5] = paramGuidRsp;
        return;
      }
      LogUtils.d("GuidLoader", "someone tries to write an empty guid to system settings, ignore!!! ");
      return;
    }
    catch (Exception paramGuidRsp)
    {
      paramGuidRsp.printStackTrace();
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("save guid to sys setting error: ");
      ((StringBuilder)localObject).append(paramGuidRsp.getMessage());
      LogUtils.d("GuidLoader", ((StringBuilder)localObject).toString());
    }
  }
  
  private GuidRsp splitBytes(byte[] paramArrayOfByte, String paramString)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("split bytes, filetail=");
    ((StringBuilder)localObject).append(paramString);
    LogUtils.d("GuidLoader", ((StringBuilder)localObject).toString());
    if ((paramArrayOfByte != null) && (paramArrayOfByte.length > 0)) {
      paramArrayOfByte = DesUtils.DesEncrypt(DesUtils.MTT_KEY, paramArrayOfByte, 0);
    } else {
      paramArrayOfByte = null;
    }
    if ((paramArrayOfByte != null) && (paramArrayOfByte.length > 0))
    {
      localObject = new GuidRsp();
      if (".id".equals(paramString))
      {
        LogUtils.d("GuidLoader", "split bytes, decry data is ok, data from old guid");
        paramString = new GuidRsp();
        paramString.vGuid = ByteUtils.subByte(paramArrayOfByte, 0, 16);
        return paramString;
      }
      LogUtils.d("GuidLoader", "split bytes, decry data is ok, data from new guid");
      ((GuidRsp)localObject).vGuid = ByteUtils.subByte(paramArrayOfByte, 0, 16);
      ((GuidRsp)localObject).vValidation = ByteUtils.subByte(paramArrayOfByte, 16, -1);
      return (GuidRsp)localObject;
    }
    LogUtils.d("GuidLoader", "split bytes, decry data is empty, ignore");
    return null;
  }
  
  private void validateCurrentAppGuid()
  {
    LogUtils.d("GuidLoader", "------------ validateCurrentAppGuid begins ------------");
    if ((this.mFinalGuid != null) && (this.mWhoAmI != 7))
    {
      if (this.mHasSavedCurrentAppGuid)
      {
        LogUtils.d("GuidLoader", "validateCurrentAppGuid: we have already perform saving before, no need to validate");
        return;
      }
      Object localObject = FileUtils.getTbsDataShareDirWithoutChmod(this.mContext);
      boolean bool3 = false;
      if (localObject != null)
      {
        localObject = new File((File)localObject, "guid.txt");
        if ((((File)localObject).exists()) && (((File)localObject).canRead()))
        {
          LogUtils.d("GuidLoader", "validateCurrentAppGuid: the host has primary GUID file, and can read");
          bool1 = readAndValidate((File)localObject);
          break label108;
        }
      }
      boolean bool1 = false;
      label108:
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("validateCurrentAppGuid: after validate primary, isOK=");
      ((StringBuilder)localObject).append(bool1);
      LogUtils.d("GuidLoader", ((StringBuilder)localObject).toString());
      localObject = FileUtils.getCommonDataShareDirWithoutChmod(this.mContext);
      boolean bool2 = bool3;
      if (localObject != null)
      {
        localObject = new File((File)localObject, "guid.txt");
        bool2 = bool3;
        if (((File)localObject).exists())
        {
          bool2 = bool3;
          if (((File)localObject).canRead())
          {
            LogUtils.d("GuidLoader", "validateCurrentAppGuid: the host has secondary GUID file, and can read");
            bool2 = readAndValidate((File)localObject);
          }
        }
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("validateCurrentAppGuid: after validate secondary, isOK=");
      ((StringBuilder)localObject).append(bool2);
      LogUtils.d("GuidLoader", ((StringBuilder)localObject).toString());
      if ((bool1) && (bool2))
      {
        LogUtils.d("GuidLoader", "validateCurrentAppGuid: all guid are ok, no need to rewrite");
      }
      else
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("validateCurrentAppGuid: primaryGuidOk=");
        ((StringBuilder)localObject).append(bool1);
        ((StringBuilder)localObject).append(", secondaryGuidOk=");
        ((StringBuilder)localObject).append(bool2);
        LogUtils.d("GuidLoader", ((StringBuilder)localObject).toString());
        saveCurrenAppGuid(this.mFinalGuid);
      }
      LogUtils.d("GuidLoader", "validateCurrentAppGuid: compelete");
      return;
    }
    LogUtils.d("GuidLoader", "validateCurrentAppGuid: final guid is not OK, or I am not 3 main host App, ignore");
  }
  
  public GuidRsp getGuid()
  {
    return this.mFinalGuid;
  }
  
  public boolean isThirdPartApp()
  {
    return this.mWhoAmI == 7;
  }
  
  public void loadGuid()
  {
    LogUtils.d("GuidLoader", "load guid begin");
    switch (loadGuidFromHostApp())
    {
    default: 
      break;
    case 1: 
      LogUtils.d("GuidLoader", "load guid from host app -> ok, go compare the guids, find the newest one");
      compareAllGuids();
      break;
    case 0: 
      LogUtils.d("GuidLoader", "load guid from host app -> none, go load guid from SysSetting and sdcard");
      loadGuidFromSysSetting();
      loadGuidFromSdcard();
      compareAllGuids();
    }
    if ((this.mFinalGuid == null) && (this.mWhoAmI != 0))
    {
      LogUtils.d("GuidLoader", "all guid is empty, now check if we can load guid from qb~~");
      this.mFinalGuid = loadGuidFromQBSDCard();
      GuidRsp localGuidRsp = this.mFinalGuid;
      if ((localGuidRsp != null) && (localGuidRsp.lGenerateTime <= 0L))
      {
        LogUtils.d("GuidLoader", "finally, we load guid from QB, but its time not ok, set it as current");
        this.mFinalGuid.lGenerateTime = (System.currentTimeMillis() / 1000L);
      }
    }
    else
    {
      mergeQBGuid();
    }
    if (this.mFinalGuid != null) {
      BrowserExecutorSupplier.forIoTasks().execute(new Runnable()
      {
        public void run()
        {
          LogUtils.d("GuidLoader", "!-------------------- Post GUID Task Begins --------------------!");
          LogUtils.d("GuidLoader", "-------------------- Post GUID Task 1: validate My Guid Start --------------------");
          GuidLoader.this.validateCurrentAppGuid();
          LogUtils.d("GuidLoader", "-------------------- Post GUID Task 1: validate My Guids End --------------------");
          LogUtils.d("GuidLoader", "-------------------- Post GUID Task 2: enhance QB's Guid Start --------------------");
          GuidLoader.this.enhanceQBGuid();
          LogUtils.d("GuidLoader", "-------------------- Post GUID Task 2: enhance QB's Guid End --------------------");
          LogUtils.d("GuidLoader", "!-------------------- Post GUID Task Compelete --------------------!");
        }
      });
    }
  }
  
  /* Error */
  public boolean saveCurrenAppGuid(GuidRsp paramGuidRsp)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: ldc 71
    //   4: ldc_w 786
    //   7: invokestatic 173	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   10: aload_0
    //   11: getfield 129	com/tencent/tbs/common/baseinfo/GuidLoader:mWhoAmI	B
    //   14: bipush 7
    //   16: if_icmpne +15 -> 31
    //   19: ldc 71
    //   21: ldc_w 788
    //   24: invokestatic 173	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   27: aload_0
    //   28: monitorexit
    //   29: iconst_0
    //   30: ireturn
    //   31: aload_0
    //   32: aload_1
    //   33: getfield 206	com/tencent/tbs/common/MTT/GuidRsp:vGuid	[B
    //   36: aload_1
    //   37: getfield 209	com/tencent/tbs/common/MTT/GuidRsp:vValidation	[B
    //   40: invokespecial 213	com/tencent/tbs/common/baseinfo/GuidLoader:isGUIDValidate	([B[B)Z
    //   43: ifne +15 -> 58
    //   46: ldc 71
    //   48: ldc_w 790
    //   51: invokestatic 173	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   54: aload_0
    //   55: monitorexit
    //   56: iconst_0
    //   57: ireturn
    //   58: aconst_null
    //   59: astore 6
    //   61: aconst_null
    //   62: astore 5
    //   64: aload 5
    //   66: astore 4
    //   68: aload_0
    //   69: getfield 137	com/tencent/tbs/common/baseinfo/GuidLoader:mContext	Landroid/content/Context;
    //   72: invokestatic 793	com/tencent/common/utils/FileUtils:getTesDataShareDir	(Landroid/content/Context;)Ljava/io/File;
    //   75: astore 8
    //   77: aload 5
    //   79: astore 4
    //   81: new 356	java/io/File
    //   84: dup
    //   85: aload 8
    //   87: ldc 27
    //   89: invokespecial 468	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   92: astore 7
    //   94: aload 5
    //   96: astore 4
    //   98: new 553	com/tencent/tbs/common/utils/TBSFileLock
    //   101: dup
    //   102: aload 8
    //   104: ldc 27
    //   106: invokespecial 554	com/tencent/tbs/common/utils/TBSFileLock:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   109: astore 5
    //   111: aload 5
    //   113: invokevirtual 557	com/tencent/tbs/common/utils/TBSFileLock:lock	()V
    //   116: aload 7
    //   118: invokevirtual 365	java/io/File:exists	()Z
    //   121: ifne +17 -> 138
    //   124: ldc 71
    //   126: ldc_w 795
    //   129: invokestatic 173	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   132: aload 7
    //   134: invokevirtual 685	java/io/File:createNewFile	()Z
    //   137: pop
    //   138: aload_0
    //   139: aload 7
    //   141: ldc_w 428
    //   144: invokespecial 797	com/tencent/tbs/common/baseinfo/GuidLoader:linuxChmod	(Ljava/io/File;Ljava/lang/String;)Z
    //   147: pop
    //   148: aload_1
    //   149: invokestatic 689	com/tencent/tbs/common/push/PushDataParserUtils:jce2Bytes	(Lcom/taf/JceStruct;)[B
    //   152: astore_1
    //   153: aload 7
    //   155: aload_1
    //   156: invokestatic 693	com/tencent/common/utils/FileUtils:save	(Ljava/io/File;[B)Z
    //   159: istore_3
    //   160: aload_0
    //   161: getfield 137	com/tencent/tbs/common/baseinfo/GuidLoader:mContext	Landroid/content/Context;
    //   164: invokestatic 800	com/tencent/common/utils/FileUtils:getCommonDataShareDir	(Landroid/content/Context;)Ljava/io/File;
    //   167: astore 4
    //   169: iload_3
    //   170: istore_2
    //   171: aload 4
    //   173: ifnull +99 -> 272
    //   176: new 157	java/lang/StringBuilder
    //   179: dup
    //   180: invokespecial 158	java/lang/StringBuilder:<init>	()V
    //   183: astore 6
    //   185: aload 6
    //   187: ldc_w 802
    //   190: invokevirtual 164	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   193: pop
    //   194: aload 6
    //   196: iload_3
    //   197: invokevirtual 334	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   200: pop
    //   201: ldc 71
    //   203: aload 6
    //   205: invokevirtual 167	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   208: invokestatic 173	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   211: new 356	java/io/File
    //   214: dup
    //   215: aload 4
    //   217: ldc 27
    //   219: invokespecial 468	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   222: astore 4
    //   224: aload 4
    //   226: invokevirtual 365	java/io/File:exists	()Z
    //   229: ifne +17 -> 246
    //   232: ldc 71
    //   234: ldc_w 804
    //   237: invokestatic 173	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   240: aload 4
    //   242: invokevirtual 685	java/io/File:createNewFile	()Z
    //   245: pop
    //   246: aload_0
    //   247: aload 4
    //   249: ldc_w 428
    //   252: invokespecial 797	com/tencent/tbs/common/baseinfo/GuidLoader:linuxChmod	(Ljava/io/File;Ljava/lang/String;)Z
    //   255: pop
    //   256: aload 4
    //   258: aload_1
    //   259: invokestatic 693	com/tencent/common/utils/FileUtils:save	(Ljava/io/File;[B)Z
    //   262: ifne +150 -> 412
    //   265: iload_3
    //   266: ifeq +141 -> 407
    //   269: goto +143 -> 412
    //   272: aload_0
    //   273: iload_2
    //   274: putfield 143	com/tencent/tbs/common/baseinfo/GuidLoader:mHasSavedCurrentAppGuid	Z
    //   277: new 157	java/lang/StringBuilder
    //   280: dup
    //   281: invokespecial 158	java/lang/StringBuilder:<init>	()V
    //   284: astore_1
    //   285: aload_1
    //   286: ldc_w 806
    //   289: invokevirtual 164	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   292: pop
    //   293: aload_1
    //   294: iload_2
    //   295: invokevirtual 334	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   298: pop
    //   299: ldc 71
    //   301: aload_1
    //   302: invokevirtual 167	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   305: invokestatic 173	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   308: aload 5
    //   310: invokevirtual 562	com/tencent/tbs/common/utils/TBSFileLock:releaseLock	()V
    //   313: ldc 71
    //   315: ldc_w 808
    //   318: invokestatic 173	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   321: aload_0
    //   322: monitorexit
    //   323: iload_2
    //   324: ireturn
    //   325: astore_1
    //   326: goto +56 -> 382
    //   329: astore 4
    //   331: aload 5
    //   333: astore_1
    //   334: aload 4
    //   336: astore 5
    //   338: goto +16 -> 354
    //   341: astore_1
    //   342: aload 4
    //   344: astore 5
    //   346: goto +36 -> 382
    //   349: astore 5
    //   351: aload 6
    //   353: astore_1
    //   354: aload_1
    //   355: astore 4
    //   357: aload 5
    //   359: invokevirtual 389	java/lang/Exception:printStackTrace	()V
    //   362: aload_1
    //   363: ifnull +15 -> 378
    //   366: aload_1
    //   367: invokevirtual 562	com/tencent/tbs/common/utils/TBSFileLock:releaseLock	()V
    //   370: ldc 71
    //   372: ldc_w 808
    //   375: invokestatic 173	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   378: aload_0
    //   379: monitorexit
    //   380: iconst_0
    //   381: ireturn
    //   382: aload 5
    //   384: ifnull +16 -> 400
    //   387: aload 5
    //   389: invokevirtual 562	com/tencent/tbs/common/utils/TBSFileLock:releaseLock	()V
    //   392: ldc 71
    //   394: ldc_w 808
    //   397: invokestatic 173	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   400: aload_1
    //   401: athrow
    //   402: astore_1
    //   403: aload_0
    //   404: monitorexit
    //   405: aload_1
    //   406: athrow
    //   407: iconst_0
    //   408: istore_2
    //   409: goto -137 -> 272
    //   412: iconst_1
    //   413: istore_2
    //   414: goto -142 -> 272
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	417	0	this	GuidLoader
    //   0	417	1	paramGuidRsp	GuidRsp
    //   170	244	2	bool1	boolean
    //   159	107	3	bool2	boolean
    //   66	191	4	localObject1	Object
    //   329	14	4	localException1	Exception
    //   355	1	4	localGuidRsp	GuidRsp
    //   62	283	5	localObject2	Object
    //   349	39	5	localException2	Exception
    //   59	293	6	localStringBuilder	StringBuilder
    //   92	62	7	localFile1	File
    //   75	28	8	localFile2	File
    // Exception table:
    //   from	to	target	type
    //   111	138	325	finally
    //   138	169	325	finally
    //   176	246	325	finally
    //   246	265	325	finally
    //   272	308	325	finally
    //   111	138	329	java/lang/Exception
    //   138	169	329	java/lang/Exception
    //   176	246	329	java/lang/Exception
    //   246	265	329	java/lang/Exception
    //   272	308	329	java/lang/Exception
    //   68	77	341	finally
    //   81	94	341	finally
    //   98	111	341	finally
    //   357	362	341	finally
    //   68	77	349	java/lang/Exception
    //   81	94	349	java/lang/Exception
    //   98	111	349	java/lang/Exception
    //   2	27	402	finally
    //   31	54	402	finally
    //   308	321	402	finally
    //   366	378	402	finally
    //   387	400	402	finally
    //   400	402	402	finally
  }
  
  public void updateSysSettingAndSdCard(GuidRsp paramGuidRsp, boolean paramBoolean)
  {
    LogUtils.d("GuidLoader", "update guid in sys setting and sdcard");
    if (this.mWhoAmI == 7)
    {
      LogUtils.d("GuidLoader", "this is not the 3 main host app, ignore this request");
      return;
    }
    if (!isGUIDValidate(paramGuidRsp.vGuid, paramGuidRsp.vValidation))
    {
      LogUtils.d("GuidLoader", "updateSysSettingAndSdCard, but guid is not validate, ignore this request");
      return;
    }
    if (!this.mHasLoadGuidFromSysSettingAndSdcard)
    {
      loadGuidFromSysSetting();
      loadGuidFromSdcard();
    }
    int i;
    if ((paramBoolean) && ((!ByteUtils.isEqual(paramGuidRsp.vGuid, this.mGuids[5].vGuid)) || (!ByteUtils.isEqual(paramGuidRsp.vGuid, this.mGuids[6].vGuid)))) {
      i = 1;
    } else {
      i = 0;
    }
    if ((i != 0) || (!compareGuid(this.mGuids[5], paramGuidRsp)))
    {
      LogUtils.d("GuidLoader", ", sys setting's guid is not the newest, update guid in sys setting");
      saveGuidToSysSetting(paramGuidRsp);
    }
    if ((i != 0) || (!compareGuid(this.mGuids[6], paramGuidRsp)))
    {
      LogUtils.d("GuidLoader", ", sdcard's guid is not the newest, update guid in sdcard");
      saveGuidToSdcard(paramGuidRsp);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\GuidLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */