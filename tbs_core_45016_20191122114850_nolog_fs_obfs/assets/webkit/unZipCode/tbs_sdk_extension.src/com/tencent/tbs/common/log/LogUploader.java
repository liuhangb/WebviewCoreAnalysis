package com.tencent.tbs.common.log;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.webkit.ValueCallback;
import com.tencent.common.http.HttpRequesterBase;
import com.tencent.common.http.MttRequestBase;
import com.tencent.common.http.Requester;
import com.tencent.common.serverconfig.IPListUtils;
import com.tencent.common.utils.FileUtils;
import com.tencent.common.utils.ZipUtils;
import com.tencent.mtt.ContextHolder;
import com.tencent.tbs.common.baseinfo.GUIDFactory;
import com.tencent.tbs.common.baseinfo.RoutineDaemon;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.http.MttRequest;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.tbs.common.task.Task;
import com.tencent.tbs.common.task.TaskManager;
import com.tencent.tbs.common.task.TaskObserverBase;
import com.tencent.tbs.common.utils.DeviceUtils;
import com.tencent.tbs.common.utils.NetworkUtils;
import com.tencent.tbs.common.utils.TbsInfoUtils;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.json.JSONException;
import org.json.JSONObject;

public class LogUploader
{
  private static final String BUG_PROPERTIES = "bug.properties";
  public static final String CMD_START_DEFAULT = "c=1&t=0&lv=0";
  public static final String CMD_START_SAMPLE = "c=1&t=1&lv=0&core=1";
  public static final String CMD_UPLOAD_DEFAULT = "c=3&t=0&lv=0&s=1&eh=168";
  public static final String CMD_UPLOAD_SAMPLE = "c=3&t=1&lv=0";
  private static final String FILE_TAG_SEP = ";";
  public static final String LOG_FILE_SUFFIX = ".qlog";
  public static final String LOG_NAME_SEPERATOR = "_";
  public static final String LOG_ZIP_FILE_SUFFIX = "_log.zip";
  public static final String OS_ANDROID = "android";
  public static final int PROMPT_TIME_SEC_LIMIT = 1000;
  public static final String PUSH_LIST_ALL_TAG = "ALL";
  public static final String QB_TOKEN = "ae98540d1e25e2c9d605edad4873b81d21c03ae8";
  public static final String QB_USER_NAME = "qqbrowser";
  private static final String SDK_TASK_TITLE = "tbs";
  private static final String TAG = "LogUploader";
  public static final String TBS_APP_ID = "1469089481";
  public static final String TBS_TAPD_WORK_SPACE_ID = "10098241";
  public static final String TESLY_UA = "tesly_sdk";
  private static final SimpleDateFormat sDateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
  
  private static boolean checkCommand(PushCommand paramPushCommand)
  {
    return (paramPushCommand != null) && (paramPushCommand.mCmdType != 0);
  }
  
  public static void executeCmd(String paramString1, final String paramString2, final List<File> paramList, final ValueCallback<String> paramValueCallback)
  {
    if (paramString1 != null)
    {
      if (paramString2 == null) {
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("command:");
      localStringBuilder.append(paramString1);
      localStringBuilder.toString();
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("logPath:");
      localStringBuilder.append(paramString2);
      localStringBuilder.toString();
      RoutineDaemon.getInstance().post(new Runnable()
      {
        public void run()
        {
          try
          {
            LogUploader.handleCommand(LogUploader.PushCommandParser.parsePushCommand(this.val$cmdString), paramString2, paramList, paramValueCallback);
            return;
          }
          catch (Throwable localThrowable)
          {
            localThrowable.printStackTrace();
            localThrowable.getMessage();
          }
        }
      });
      return;
    }
  }
  
  private static File generateBirdBugPropertiesFile(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, ArrayList<File> paramArrayList)
  {
    paramString6 = getGeneratePropFile(paramString6);
    StringBuffer localStringBuffer1 = new StringBuffer();
    StringBuffer localStringBuffer2 = new StringBuffer();
    if ((paramArrayList != null) && (paramArrayList.size() > 0))
    {
      int i = 0;
      while (i < paramArrayList.size())
      {
        if (paramArrayList.get(i) != null)
        {
          StringBuilder localStringBuilder;
          if (((File)paramArrayList.get(i)).getName().endsWith(".jpg"))
          {
            localStringBuilder = new StringBuilder();
            localStringBuilder.append(((File)paramArrayList.get(i)).getName());
            localStringBuilder.append(";");
            localStringBuffer1.append(localStringBuilder.toString());
          }
          else
          {
            localStringBuilder = new StringBuilder();
            localStringBuilder.append(((File)paramArrayList.get(i)).getName());
            localStringBuilder.append(";");
            localStringBuffer2.append(localStringBuilder.toString());
          }
        }
        i += 1;
      }
    }
    paramString1 = generateBuglyProperties(paramString1, paramString2, paramString3, paramString4, paramString5, localStringBuffer1.toString(), localStringBuffer2.toString());
    try
    {
      storePropertiesToFile(paramString6.getAbsolutePath(), paramString1);
      return paramString6;
    }
    catch (IOException paramString1)
    {
      paramString1.printStackTrace();
    }
    return null;
  }
  
  @SuppressLint({"NewApi"})
  private static Properties generateBuglyProperties(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    paramString4 = ContextHolder.getAppContext();
    Properties localProperties = new Properties();
    localProperties.setProperty("bug_creator", "tbs");
    localProperties.setProperty("bugly_qq", paramString5);
    localProperties.setProperty("bug_guid", paramString3);
    localProperties.setProperty("bug_file_image", paramString6);
    localProperties.setProperty("bug_file_extra_log", paramString7);
    localProperties.setProperty("bug_title", paramString1);
    paramString3 = new StringBuilder();
    paramString3.append(paramString1);
    paramString3.append("=");
    paramString3.append(paramString2);
    localProperties.setProperty("bug_desc", paramString3.toString());
    localProperties.setProperty("build_fingerprint", Build.FINGERPRINT);
    paramString1 = new StringBuilder();
    paramString1.append(FileUtils.getSdcardFreeSpace() / 1024L / 1024L);
    paramString1.append("MB");
    localProperties.setProperty("hardware_sd_card_avail", paramString1.toString());
    paramString1 = new StringBuilder();
    paramString1.append(FileUtils.getDataFreeSpace(ContextHolder.getAppContext()) / 1024L / 1024L);
    paramString1.append("MB");
    localProperties.setProperty("hardware_rom_avail", paramString1.toString());
    localProperties.setProperty("build_version_release", Build.VERSION.RELEASE);
    paramString1 = new StringBuilder();
    paramString1.append(DeviceUtils.getWidth(paramString4));
    paramString1.append("*");
    paramString1.append(DeviceUtils.getHeight(paramString4));
    localProperties.setProperty("hardware_display_resolution", paramString1.toString());
    localProperties.setProperty("build_display", Build.DISPLAY);
    localProperties.setProperty("build_serial", Build.SERIAL);
    int i = DeviceUtils.getTelephonyManager().getPhoneType();
    localProperties.setProperty("telephony_phone_type", new String[] { "NONE", "GSM", "CDMA", "SIP" }[i]);
    localProperties.setProperty("bug_create_time", new SimpleDateFormat("yyyy-MM-dd HH\\:mm\\:ss").format(new Date()));
    localProperties.setProperty("build_model", getDeviceModel());
    localProperties.setProperty("build_brand", getDeviceBrand());
    localProperties.setProperty("telephony_device_id", getImei());
    paramString1 = new StringBuilder();
    paramString1.append("");
    paramString1.append(DeviceUtils.getAvailRAM());
    paramString1.append("MB");
    localProperties.setProperty("hardware_ram_avail", paramString1.toString());
    paramString1 = new StringBuilder();
    paramString1.append("");
    paramString1.append(DeviceUtils.getTotalRAM());
    paramString1.append("MB");
    localProperties.setProperty("hardware_ram_total", paramString1.toString());
    localProperties.setProperty("network_type", getNetworkType());
    localProperties.setProperty("build_manufacturer", getDeviceManufacturer());
    if (IPListUtils.getWifiBSSID(paramString4) == null) {
      paramString1 = "NoWifi";
    } else {
      paramString1 = IPListUtils.getWifiBSSID(ContextHolder.getAppContext());
    }
    localProperties.setProperty("wifi_ssid", paramString1);
    paramString1 = new StringBuilder();
    paramString1.append(DeviceUtils.getDensity(paramString4));
    paramString1.append("DM");
    localProperties.setProperty("hardware_display_density", paramString1.toString());
    localProperties.setProperty("hardware_cpu", DeviceUtils.getDeviceCpuabi());
    localProperties.setProperty("rom_type", Build.MODEL);
    paramString1 = new StringBuilder();
    paramString1.append(Build.VERSION.SDK_INT);
    paramString1.append("");
    localProperties.setProperty("build_version_sdk_int", paramString1.toString());
    localProperties.setProperty("app_version_name", getVersionName(paramString4));
    paramString1 = new StringBuilder();
    paramString1.append(getROMMemery());
    paramString1.append("MB");
    localProperties.setProperty("hardware_rom_total", paramString1.toString());
    paramString1 = new StringBuilder();
    paramString1.append(getVersionCode(paramString4));
    paramString1.append("");
    localProperties.setProperty("app_version_code", paramString1.toString());
    localProperties.setProperty("wifi_ip_address", NetworkUtils.getIpAddress(paramString4));
    return localProperties;
  }
  
  private static ArrayList<File> getAllUploadFiles(PushCommand paramPushCommand, String paramString)
  {
    File localFile = new File(paramString);
    paramString = new ArrayList();
    GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    localGregorianCalendar.setTime(paramPushCommand.mDateOfLogToUpload);
    float f = 60;
    localGregorianCalendar.add(12, -(int)(paramPushCommand.mHoursBeforeGivenDate * f));
    final Date localDate = localGregorianCalendar.getTime();
    localGregorianCalendar.setTime(paramPushCommand.mDateOfLogToUpload);
    localGregorianCalendar.add(12, (int)(f * paramPushCommand.mHoursAfterGivenDate));
    paramString.addAll(Arrays.asList(localFile.listFiles(new FilenameFilter()
    {
      public boolean accept(File paramAnonymousFile, String paramAnonymousString)
      {
        if (paramAnonymousString.endsWith(".qlog"))
        {
          paramAnonymousFile = paramAnonymousString.replace(".qlog", "").split("_");
          if (paramAnonymousFile.length < 5) {
            return false;
          }
        }
        try
        {
          int i = Integer.parseInt(paramAnonymousFile[3]);
          if ((1 << i - 1 & this.val$command.mLogLevelMask) != 0) {
            try
            {
              paramAnonymousFile = LogUploader.sDateFormatter.parse(paramAnonymousFile[4]);
              if (paramAnonymousFile.after(localDate))
              {
                boolean bool = paramAnonymousFile.before(this.val$endDateTime);
                if (bool) {
                  return true;
                }
              }
            }
            catch (ParseException paramAnonymousFile)
            {
              paramAnonymousFile.printStackTrace();
              return false;
            }
          }
          return false;
        }
        catch (Exception paramAnonymousFile) {}
        return false;
      }
    })));
    paramPushCommand = paramPushCommand.mAttachedFileList.iterator();
    while (paramPushCommand.hasNext())
    {
      localFile = new File((String)paramPushCommand.next());
      if (localFile.exists()) {
        paramString.add(localFile);
      }
    }
    return paramString;
  }
  
  public static String getDeviceBrand()
  {
    String str = Build.BRAND;
    if (str == null) {
      return "";
    }
    return str.toLowerCase();
  }
  
  public static String getDeviceManufacturer()
  {
    String str = Build.MANUFACTURER;
    if (str == null) {
      return "";
    }
    return str.toLowerCase();
  }
  
  public static String getDeviceModel()
  {
    return Build.MODEL.replaceAll("[ |\\/|\\_|\\&|\\|]", "");
  }
  
  private static File getGeneratePropFile(String paramString)
  {
    paramString = new File(paramString);
    if (paramString.exists())
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString.getAbsolutePath());
      localStringBuilder.append(File.separator);
      localStringBuilder.append("bug.properties");
      paramString = new File(localStringBuilder.toString());
      if (!paramString.exists()) {
        try
        {
          paramString.createNewFile();
          return paramString;
        }
        catch (IOException localIOException)
        {
          localIOException.printStackTrace();
        }
      }
      return paramString;
    }
    return null;
  }
  
  private static String getImei()
  {
    return ((TelephonyManager)ContextHolder.getAppContext().getSystemService("phone")).getDeviceId();
  }
  
  public static String getNetworkType()
  {
    String str = "UNKNOWN";
    int i = getNetworkTypeInt();
    if (i == 1) {
      return "GPRS";
    }
    if (i == 2) {
      return "EDGE";
    }
    if (i == 3) {
      return "UMTS";
    }
    if (i == 8) {
      return "HSDPA";
    }
    if (i == 9) {
      return "HSUPA";
    }
    if (i == 10) {
      return "HSPA";
    }
    if (i == 4) {
      return "CDMA";
    }
    if (i == 5) {
      return "CDMAEVDO0";
    }
    if (i == 6) {
      return "CDMAEVDOA";
    }
    if (i == 7) {
      return "CDMA1xRTT";
    }
    if (i == 11) {
      return "IDEN";
    }
    if (i == 12) {
      return "CDMAEVDOB";
    }
    if (i == 13) {
      return "LTE";
    }
    if (i == 14) {
      return "EHRPD";
    }
    if (i == 15) {
      str = "HSPAP";
    }
    return str;
  }
  
  public static int getNetworkTypeInt()
  {
    TelephonyManager localTelephonyManager = getTelephonyManager(ContextHolder.getAppContext());
    if (localTelephonyManager != null) {
      return localTelephonyManager.getNetworkType();
    }
    return 0;
  }
  
  public static PackageInfo getPackageInfo(Context paramContext)
  {
    try
    {
      paramContext = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 16384);
      return paramContext;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return null;
  }
  
  public static float getROMMemery()
  {
    Object localObject = new StatFs(Environment.getDataDirectory().getPath());
    long l = ((StatFs)localObject).getAvailableBlocks();
    float f1 = (float)(((StatFs)localObject).getBlockSize() * l / 1024L / 1024L);
    l = ((StatFs)localObject).getBlockCount();
    float f2 = (float)(((StatFs)localObject).getBlockSize() * l / 1024L / 1024L);
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("av: ");
    ((StringBuilder)localObject).append(f1);
    ((StringBuilder)localObject).append(", total: ");
    ((StringBuilder)localObject).append(f2);
    ((StringBuilder)localObject).toString();
    return f2;
  }
  
  public static TelephonyManager getTelephonyManager(Context paramContext)
  {
    if (paramContext != null) {
      try
      {
        paramContext = (TelephonyManager)paramContext.getSystemService("phone");
        return paramContext;
      }
      catch (Error paramContext)
      {
        paramContext.printStackTrace();
      }
    }
    return null;
  }
  
  public static int getVersionCode(Context paramContext)
  {
    return getPackageInfo(paramContext).versionCode;
  }
  
  public static String getVersionName(Context paramContext)
  {
    return getPackageInfo(paramContext).versionName;
  }
  
  private static void handleCommand(PushCommand paramPushCommand, final String paramString, List<File> paramList, final ValueCallback<String> paramValueCallback)
  {
    ArrayList localArrayList;
    if ((checkCommand(paramPushCommand)) && (paramPushCommand.mCmdType == 3))
    {
      int i;
      if (IPListUtils.getConnectType(ContextHolder.getAppContext()) == 1) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        localArrayList = getAllUploadFiles(paramPushCommand, paramString);
        localArrayList.addAll(paramList);
        localObject = "";
        str2 = "";
        paramPushCommand = (PushCommand)localObject;
        paramList = (List<File>)localObject;
      }
    }
    try
    {
      if (TbsBaseModuleShell.getCallerContext() != null)
      {
        paramList = (List<File>)localObject;
        paramPushCommand = TbsBaseModuleShell.getCallerContext().getPackageName();
      }
      str1 = str2;
      localObject = paramPushCommand;
      paramList = paramPushCommand;
      if (TbsBaseModuleShell.getX5CoreHostAppContext() != null)
      {
        paramList = paramPushCommand;
        str1 = TbsBaseModuleShell.getX5CoreHostAppContext().getPackageName();
        localObject = paramPushCommand;
      }
    }
    catch (Exception paramPushCommand)
    {
      for (;;)
      {
        String str1 = str2;
        localObject = paramList;
      }
    }
    paramPushCommand = getImei();
    paramList = GUIDFactory.getInstance().getStrGuid();
    paramPushCommand = generateBirdBugPropertiesFile("tbs", String.format("{sdk:%d;tbs:%s;miniqb:%s;caller:%s;x5host:%s}", new Object[] { Integer.valueOf(TbsBaseModuleShell.getTesVersionCode()), TbsBaseModuleShell.getX5CoreVersion(), TbsBaseModuleShell.getMiniQBVersion(), localObject, str1 }), paramList, paramPushCommand, "", paramString, localArrayList);
    if (paramPushCommand == null) {
      return;
    }
    localArrayList.add(paramPushCommand);
    try
    {
      paramString = zipFiles(paramString, localArrayList);
      paramPushCommand = new UploadTaskObserver(new ValueCallback()
      {
        public void onReceiveValue(LogUploader.LogUploadResult paramAnonymousLogUploadResult)
        {
          try
          {
            this.val$buglyPropertyFile.delete();
            new File(paramString).delete();
            paramValueCallback.onReceiveValue(paramAnonymousLogUploadResult.toJsonString());
            return;
          }
          catch (Exception localException)
          {
            for (;;) {}
          }
        }
      });
      uploadLogFiles(new File(paramString), paramPushCommand);
      return;
    }
    catch (Exception paramPushCommand)
    {
      paramPushCommand.printStackTrace();
      paramPushCommand.getMessage();
      if (paramValueCallback == null) {
        break label288;
      }
    }
    paramValueCallback.onReceiveValue(new LogUploadResult(1, "文件压缩失败").toJsonString());
    return;
    if (paramValueCallback != null) {
      paramValueCallback.onReceiveValue(new LogUploadResult(1, "当前网络不是wifi").toJsonString());
    }
    label288:
  }
  
  @SuppressLint({"NewApi"})
  public static void storePropertiesToFile(String paramString, Properties paramProperties)
    throws IOException
  {
    if (Build.VERSION.SDK_INT < 9)
    {
      paramString = new FileOutputStream(paramString);
      paramProperties.store(paramString, "");
      paramString.close();
      return;
    }
    paramString = new FileWriter(paramString);
    paramProperties.store(paramString, "");
    paramString.close();
  }
  
  public static void uploadLogFiles(File paramFile, UploadTaskObserver paramUploadTaskObserver)
  {
    if ((paramFile != null) && (paramFile.exists()))
    {
      HttpPostDataHelper.init(null);
      HttpPostDataHelper.part("os", "android");
      HttpPostDataHelper.part("ua", "tesly_sdk");
      HttpPostDataHelper.part("username", "qqbrowser");
      HttpPostDataHelper.part("token", "ae98540d1e25e2c9d605edad4873b81d21c03ae8");
      HttpPostDataHelper.part("tapd", "10098241");
      HttpPostDataHelper.part("Filedata", paramFile.getName(), "multipart/form-data", paramFile);
      HttpPostDataHelper.part("appid", "1469089481");
      paramFile = HttpPostDataHelper.getOutputByteArray();
      paramFile = new HttpUploadTask(String.format("http://%s/tesly/bug/sdk/upload", new Object[] { "tesly.qq.com" }), (byte)0, paramFile, true);
      paramFile.getMttRequest().addHeaders(HttpPostDataHelper.getHeaders());
      paramFile.addObserver(paramUploadTaskObserver);
      TaskManager.getInstance().addTask(paramFile);
    }
  }
  
  private static String zipFiles(String paramString, ArrayList<File> paramArrayList)
    throws Exception
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append(File.separatorChar);
    localStringBuilder.append(sDateFormatter.format(new Date()));
    localStringBuilder.append("_log.zip");
    paramString = localStringBuilder.toString();
    ZipUtils.compress((File[])paramArrayList.toArray(new File[paramArrayList.size()]), paramString);
    return paramString;
  }
  
  public static class HttpPostDataHelper
  {
    private static final String BOUNDARY = "00content0boundary00";
    public static final String CHARSET_UTF8 = "UTF-8";
    private static final String CONTENT_TYPE_MULTIPART = "multipart/form-data; boundary=00content0boundary00";
    private static final String CRLF = "\r\n";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String PARAM_CHARSET = "charset";
    private static int bufferSize = 1024;
    private static CharsetEncoder encoder;
    private static Map<String, String> headers = new HashMap();
    private static boolean multipart = false;
    private static ByteArrayOutputStream output = new ByteArrayOutputStream();
    private static int totalSize = -1;
    private boolean ignoreCloseExceptions = true;
    
    public static void contentType(String paramString1, String paramString2)
    {
      if ((paramString2 != null) && (paramString2.length() > 0))
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(paramString1);
        localStringBuilder.append("; charset=");
        localStringBuilder.append(paramString2);
        header("Content-Type", localStringBuilder.toString());
        return;
      }
      header("Content-Type", paramString1);
    }
    
    protected static void copy(InputStream paramInputStream)
      throws IOException
    {
      byte[] arrayOfByte = new byte[bufferSize];
      for (;;)
      {
        int i = paramInputStream.read(arrayOfByte);
        if (i == -1) {
          break;
        }
        output.write(arrayOfByte, 0, i);
      }
    }
    
    public static Map<String, String> getHeaders()
    {
      return headers;
    }
    
    public static byte[] getOutputByteArray()
    {
      return output.toByteArray();
    }
    
    private static String getValidCharset(String paramString)
    {
      if ((paramString != null) && (paramString.length() > 0)) {
        return paramString;
      }
      return "UTF-8";
    }
    
    public static void header(String paramString1, String paramString2)
    {
      headers.put(paramString1, paramString2);
    }
    
    private static void incrementTotalSize(long paramLong)
    {
      if (totalSize == -1) {
        totalSize = 0;
      }
      totalSize = (int)(totalSize + paramLong);
    }
    
    public static void init(String paramString)
    {
      multipart = false;
      encoder = Charset.forName(getValidCharset(paramString)).newEncoder();
    }
    
    public static void part(String paramString1, String paramString2)
    {
      part(paramString1, null, paramString2);
    }
    
    public static void part(String paramString1, String paramString2, String paramString3)
    {
      part(paramString1, paramString2, null, paramString3);
    }
    
    public static void part(String paramString1, String paramString2, String paramString3, File paramFile)
    {
      InputStream localInputStream;
      try
      {
        BufferedInputStream localBufferedInputStream = new BufferedInputStream(new FileInputStream(paramFile));
      }
      catch (FileNotFoundException localFileNotFoundException)
      {
        localFileNotFoundException.printStackTrace();
        localInputStream = null;
      }
      incrementTotalSize(paramFile.length());
      part(paramString1, paramString2, paramString3, localInputStream);
    }
    
    public static void part(String paramString1, String paramString2, String paramString3, InputStream paramInputStream)
    {
      try
      {
        startPart();
        writePartHeader(paramString1, paramString2, paramString3);
        copy(paramInputStream);
        return;
      }
      catch (IOException paramString1)
      {
        paramString1.printStackTrace();
      }
    }
    
    public static void part(String paramString1, String paramString2, String paramString3, String paramString4)
    {
      try
      {
        startPart();
        writePartHeader(paramString1, paramString2, paramString3);
        write(paramString4);
        return;
      }
      catch (IOException paramString1)
      {
        paramString1.printStackTrace();
      }
    }
    
    public static void partHeader(String paramString1, String paramString2)
    {
      send(paramString1);
      send(": ");
      send(paramString2);
      send("\r\n");
    }
    
    public static void send(CharSequence paramCharSequence)
    {
      try
      {
        write(paramCharSequence);
        return;
      }
      catch (IOException paramCharSequence)
      {
        paramCharSequence.printStackTrace();
        return;
      }
      catch (CharacterCodingException paramCharSequence)
      {
        paramCharSequence.printStackTrace();
      }
    }
    
    protected static void startPart()
      throws IOException
    {
      if (!multipart)
      {
        multipart = true;
        contentType("multipart/form-data; boundary=00content0boundary00", null);
        write("--00content0boundary00\r\n");
        return;
      }
      write("\r\n--00content0boundary00\r\n");
    }
    
    private static void write(CharSequence paramCharSequence)
      throws IOException, CharacterCodingException
    {
      paramCharSequence = encoder.encode(CharBuffer.wrap(paramCharSequence));
      output.write(paramCharSequence.array(), 0, paramCharSequence.limit());
    }
    
    protected static void writePartHeader(String paramString1, String paramString2, String paramString3)
      throws IOException
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("form-data; name=\"");
      localStringBuilder.append(paramString1);
      if (paramString2 != null)
      {
        localStringBuilder.append("\"; filename=\"");
        localStringBuilder.append(paramString2);
      }
      localStringBuilder.append('"');
      partHeader("Content-Disposition", localStringBuilder.toString());
      if (paramString3 != null) {
        partHeader("Content-Type", paramString3);
      }
      send("\r\n");
    }
    
    protected void closeOutput()
      throws IOException
    {
      if (output == null) {
        return;
      }
      if (multipart) {
        write("\r\n--00content0boundary00--\r\n");
      }
      if (this.ignoreCloseExceptions) {}
      try
      {
        output.close();
      }
      catch (IOException localIOException)
      {
        for (;;) {}
      }
      output.close();
      output = null;
    }
  }
  
  public static class HttpRequester
    extends HttpRequesterBase
  {
    protected void fillUserAgent()
    {
      if ((this.mMttRequest.getUserAgent() != null) && (!this.mIsRemoveHeader)) {
        this.mMttRequest.addHeader("User-Agent", this.mMttRequest.getUserAgent());
      }
    }
    
    protected void setCookie() {}
  }
  
  public static class HttpUploadTask
    extends Task
  {
    private static final String CONTENT_TYPE = "application/multipart-formdata";
    private static final int DEFAULT_READ_TIME_OUT = 20000;
    private static final int DEFAULT_TIME_OUT = 20000;
    private static final int HTTP_STATUS_SC_OK = 200;
    private static final int NETWORK_RETRY_TIMES = 3;
    static final String TAG = "HttpUploadTask";
    private int mRetryTimes = 3;
    public Object referenceObject;
    private byte[] rspData;
    
    public HttpUploadTask(String paramString1, byte paramByte, byte[] paramArrayOfByte, String paramString2, String paramString3, Object paramObject, boolean paramBoolean)
    {
      this.mTaskType = paramByte;
      this.referenceObject = paramObject;
      this.mMttRequest = new MttRequest();
      this.mMttRequest.setUrl(paramString1);
      this.mMttRequest.addHeader("Content-Type", "application/multipart-formdata");
      paramString1 = PublicSettingManager.getInstance();
      if (paramString1.getAndroidEnableQua1() != 0) {
        this.mMttRequest.addHeader("Q-UA", TbsInfoUtils.getQUA());
      }
      if (paramString1.getAndroidEnableQua2_v3() != 0) {
        this.mMttRequest.addHeader("Q-UA2", TbsInfoUtils.getQUA2());
      }
      this.mMttRequest.addHeader("Q-GUID", GUIDFactory.getInstance().getStrGuid());
      if ((!TextUtils.isEmpty(paramString2)) && (!TextUtils.isEmpty(paramString3))) {
        this.mMttRequest.addHeader(paramString2, paramString3);
      }
      this.mMttRequest.setMethod((byte)1);
      this.mMttRequest.setPostData(paramArrayOfByte);
      if (!paramBoolean) {
        this.mRetryTimes = 1;
      }
    }
    
    public HttpUploadTask(String paramString, byte paramByte, byte[] paramArrayOfByte, boolean paramBoolean)
    {
      this(paramString, paramByte, paramArrayOfByte, null, null, null, paramBoolean);
    }
    
    public void cancel()
    {
      setMttResponse(null);
      if (this.mRequester != null) {
        this.mRequester.close();
      }
      this.mCanceled = true;
      this.mStatus = 6;
    }
    
    public byte[] getResponseData()
    {
      return this.rspData;
    }
    
    /* Error */
    public void run()
    {
      // Byte code:
      //   0: aload_0
      //   1: iconst_5
      //   2: putfield 131	com/tencent/tbs/common/log/LogUploader$HttpUploadTask:mStatus	B
      //   5: ldc 21
      //   7: ldc -110
      //   9: invokestatic 151	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
      //   12: iconst_0
      //   13: istore_1
      //   14: aload_0
      //   15: getfield 128	com/tencent/tbs/common/log/LogUploader$HttpUploadTask:mCanceled	Z
      //   18: ifne +348 -> 366
      //   21: new 153	java/lang/StringBuilder
      //   24: dup
      //   25: invokespecial 154	java/lang/StringBuilder:<init>	()V
      //   28: astore_3
      //   29: aload_3
      //   30: ldc -100
      //   32: invokevirtual 160	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   35: pop
      //   36: aload_3
      //   37: iload_1
      //   38: invokevirtual 163	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   41: pop
      //   42: ldc 21
      //   44: aload_3
      //   45: invokevirtual 166	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   48: invokestatic 151	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
      //   51: iload_1
      //   52: aload_0
      //   53: getfield 33	com/tencent/tbs/common/log/LogUploader$HttpUploadTask:mRetryTimes	I
      //   56: if_icmplt +6 -> 62
      //   59: goto +307 -> 366
      //   62: aload_0
      //   63: new 168	com/tencent/tbs/common/log/LogUploader$HttpRequester
      //   66: dup
      //   67: invokespecial 169	com/tencent/tbs/common/log/LogUploader$HttpRequester:<init>	()V
      //   70: putfield 119	com/tencent/tbs/common/log/LogUploader$HttpUploadTask:mRequester	Lcom/tencent/common/http/Requester;
      //   73: aload_0
      //   74: getfield 119	com/tencent/tbs/common/log/LogUploader$HttpUploadTask:mRequester	Lcom/tencent/common/http/Requester;
      //   77: sipush 20000
      //   80: invokevirtual 173	com/tencent/common/http/Requester:setConnectTimeout	(I)V
      //   83: aload_0
      //   84: getfield 119	com/tencent/tbs/common/log/LogUploader$HttpUploadTask:mRequester	Lcom/tencent/common/http/Requester;
      //   87: sipush 20000
      //   90: invokevirtual 176	com/tencent/common/http/Requester:setReadTimeout	(I)V
      //   93: ldc 21
      //   95: ldc -78
      //   97: invokestatic 151	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
      //   100: aload_0
      //   101: getfield 119	com/tencent/tbs/common/log/LogUploader$HttpUploadTask:mRequester	Lcom/tencent/common/http/Requester;
      //   104: aload_0
      //   105: getfield 46	com/tencent/tbs/common/log/LogUploader$HttpUploadTask:mMttRequest	Lcom/tencent/tbs/common/http/MttRequest;
      //   108: invokevirtual 182	com/tencent/common/http/Requester:execute	(Lcom/tencent/common/http/MttRequestBase;)Lcom/tencent/common/http/MttResponse;
      //   111: astore_3
      //   112: ldc 21
      //   114: ldc -72
      //   116: invokestatic 151	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
      //   119: aload_0
      //   120: aload_3
      //   121: invokevirtual 115	com/tencent/tbs/common/log/LogUploader$HttpUploadTask:setMttResponse	(Lcom/tencent/common/http/MttResponse;)V
      //   124: aload_3
      //   125: invokevirtual 190	com/tencent/common/http/MttResponse:getStatusCode	()Ljava/lang/Integer;
      //   128: invokevirtual 195	java/lang/Integer:intValue	()I
      //   131: istore_2
      //   132: new 153	java/lang/StringBuilder
      //   135: dup
      //   136: invokespecial 154	java/lang/StringBuilder:<init>	()V
      //   139: astore 4
      //   141: aload 4
      //   143: ldc -59
      //   145: invokevirtual 160	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   148: pop
      //   149: aload 4
      //   151: iload_2
      //   152: invokevirtual 163	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   155: pop
      //   156: ldc 21
      //   158: aload 4
      //   160: invokevirtual 166	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   163: invokestatic 151	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
      //   166: iload_2
      //   167: sipush 200
      //   170: if_icmpne +107 -> 277
      //   173: aload_3
      //   174: invokevirtual 201	com/tencent/common/http/MttResponse:getInputStream	()Lcom/tencent/common/http/MttInputStream;
      //   177: astore_3
      //   178: aload_3
      //   179: ifnull +90 -> 269
      //   182: new 203	java/io/ByteArrayOutputStream
      //   185: dup
      //   186: invokespecial 204	java/io/ByteArrayOutputStream:<init>	()V
      //   189: astore 4
      //   191: sipush 4096
      //   194: newarray <illegal type>
      //   196: astore 5
      //   198: aload_3
      //   199: aload 5
      //   201: iconst_0
      //   202: aload 5
      //   204: arraylength
      //   205: invokevirtual 210	com/tencent/common/http/MttInputStream:read	([BII)I
      //   208: istore_2
      //   209: iload_2
      //   210: ifle +15 -> 225
      //   213: aload 4
      //   215: aload 5
      //   217: iconst_0
      //   218: iload_2
      //   219: invokevirtual 214	java/io/ByteArrayOutputStream:write	([BII)V
      //   222: goto -24 -> 198
      //   225: aload 4
      //   227: invokevirtual 217	java/io/ByteArrayOutputStream:flush	()V
      //   230: aload_0
      //   231: aload 4
      //   233: invokevirtual 220	java/io/ByteArrayOutputStream:toByteArray	()[B
      //   236: putfield 135	com/tencent/tbs/common/log/LogUploader$HttpUploadTask:rspData	[B
      //   239: aload 4
      //   241: invokevirtual 221	java/io/ByteArrayOutputStream:close	()V
      //   244: aload_0
      //   245: invokevirtual 224	com/tencent/tbs/common/log/LogUploader$HttpUploadTask:closeQuietly	()V
      //   248: goto +21 -> 269
      //   251: astore_3
      //   252: goto +11 -> 263
      //   255: astore_3
      //   256: aload_3
      //   257: invokevirtual 227	java/lang/Exception:printStackTrace	()V
      //   260: goto -16 -> 244
      //   263: aload_0
      //   264: invokevirtual 224	com/tencent/tbs/common/log/LogUploader$HttpUploadTask:closeQuietly	()V
      //   267: aload_3
      //   268: athrow
      //   269: aload_0
      //   270: iconst_3
      //   271: putfield 131	com/tencent/tbs/common/log/LogUploader$HttpUploadTask:mStatus	B
      //   274: goto +92 -> 366
      //   277: aload_0
      //   278: iconst_5
      //   279: putfield 131	com/tencent/tbs/common/log/LogUploader$HttpUploadTask:mStatus	B
      //   282: goto +46 -> 328
      //   285: astore_3
      //   286: aload_3
      //   287: invokevirtual 228	java/lang/Throwable:printStackTrace	()V
      //   290: aload_0
      //   291: iconst_5
      //   292: putfield 131	com/tencent/tbs/common/log/LogUploader$HttpUploadTask:mStatus	B
      //   295: new 153	java/lang/StringBuilder
      //   298: dup
      //   299: invokespecial 154	java/lang/StringBuilder:<init>	()V
      //   302: astore_3
      //   303: aload_3
      //   304: ldc -26
      //   306: invokevirtual 160	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   309: pop
      //   310: aload_3
      //   311: aload_0
      //   312: getfield 37	com/tencent/tbs/common/log/LogUploader$HttpUploadTask:mTaskType	B
      //   315: invokevirtual 163	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   318: pop
      //   319: ldc 21
      //   321: aload_3
      //   322: invokevirtual 166	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   325: invokestatic 151	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
      //   328: aload_0
      //   329: getfield 128	com/tencent/tbs/common/log/LogUploader$HttpUploadTask:mCanceled	Z
      //   332: ifeq +13 -> 345
      //   335: ldc 21
      //   337: ldc -24
      //   339: invokestatic 151	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
      //   342: goto +24 -> 366
      //   345: iload_1
      //   346: iconst_1
      //   347: iadd
      //   348: istore_1
      //   349: ldc2_w 233
      //   352: invokestatic 240	java/lang/Thread:sleep	(J)V
      //   355: goto -341 -> 14
      //   358: astore_3
      //   359: aload_3
      //   360: invokevirtual 241	java/lang/InterruptedException:printStackTrace	()V
      //   363: goto -349 -> 14
      //   366: aload_0
      //   367: getfield 128	com/tencent/tbs/common/log/LogUploader$HttpUploadTask:mCanceled	Z
      //   370: ifeq +9 -> 379
      //   373: aload_0
      //   374: bipush 6
      //   376: putfield 131	com/tencent/tbs/common/log/LogUploader$HttpUploadTask:mStatus	B
      //   379: aload_0
      //   380: invokevirtual 244	com/tencent/tbs/common/log/LogUploader$HttpUploadTask:fireObserverEvent	()V
      //   383: return
      //   384: astore_3
      //   385: goto -141 -> 244
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	388	0	this	HttpUploadTask
      //   13	336	1	i	int
      //   131	88	2	j	int
      //   28	171	3	localObject1	Object
      //   251	1	3	localObject2	Object
      //   255	13	3	localException	Exception
      //   285	2	3	localThrowable	Throwable
      //   302	20	3	localStringBuilder	StringBuilder
      //   358	2	3	localInterruptedException	InterruptedException
      //   384	1	3	localOutOfMemoryError	OutOfMemoryError
      //   139	101	4	localObject3	Object
      //   196	20	5	arrayOfByte	byte[]
      // Exception table:
      //   from	to	target	type
      //   191	198	251	finally
      //   198	209	251	finally
      //   213	222	251	finally
      //   225	244	251	finally
      //   256	260	251	finally
      //   191	198	255	java/lang/Exception
      //   198	209	255	java/lang/Exception
      //   213	222	255	java/lang/Exception
      //   225	244	255	java/lang/Exception
      //   93	166	285	java/lang/Throwable
      //   173	178	285	java/lang/Throwable
      //   182	191	285	java/lang/Throwable
      //   244	248	285	java/lang/Throwable
      //   263	269	285	java/lang/Throwable
      //   269	274	285	java/lang/Throwable
      //   277	282	285	java/lang/Throwable
      //   349	355	358	java/lang/InterruptedException
      //   191	198	384	java/lang/OutOfMemoryError
      //   198	209	384	java/lang/OutOfMemoryError
      //   213	222	384	java/lang/OutOfMemoryError
      //   225	244	384	java/lang/OutOfMemoryError
    }
  }
  
  public static class LogUploadResult
  {
    public static final int UPLOAD_FAIL = 1;
    public static final int UPLOAD_SUCESS = 0;
    public String mReason;
    public int mRet;
    
    public LogUploadResult()
    {
      this.mRet = 0;
      this.mReason = "";
    }
    
    public LogUploadResult(int paramInt, String paramString)
    {
      this.mRet = paramInt;
      this.mReason = paramString;
    }
    
    public String toJsonString()
    {
      Object localObject = new JSONObject();
      try
      {
        ((JSONObject)localObject).put("ret", this.mRet);
        ((JSONObject)localObject).put("reason", this.mReason);
        localObject = ((JSONObject)localObject).toString();
        return (String)localObject;
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
      return "";
    }
  }
  
  public static class PushCommand
  {
    public static final int CMD_TYPE_DELETE_LOG = 4;
    public static final int CMD_TYPE_NONE = 0;
    public static final int CMD_TYPE_START_LOG = 1;
    public static final int CMD_TYPE_STOP_LOG = 2;
    public static final int CMD_TYPE_UPDATE_CONFIG = 6;
    public static final int CMD_TYPE_UPLOAD_LOG = 3;
    public static final int CMD_TYPE_ZIP_LOG = 5;
    public ArrayList<String> mAttachedFileList = new ArrayList();
    public int mCmdType = 0;
    public Date mDateOfLogToUpload = new Date();
    public ArrayList<String> mDevLogTagList = new ArrayList();
    public String mDevLogTagPartten = "";
    public int mExpireHours = 0;
    public String mExtraInfo = "";
    public ArrayList<String> mGuidList = new ArrayList();
    public float mHoursAfterGivenDate = 0.5F;
    public float mHoursBeforeGivenDate = 0.5F;
    public int mLogLevelMask = 0;
    public ArrayList<String> mLogcatTagList = new ArrayList();
    public boolean mNeedCoreLog = false;
    public boolean mNeedRecordScreen = false;
    public boolean mNeedSaveLogcat = false;
    public boolean mSaveToPreferrence = false;
    public boolean mShowToast = false;
  }
  
  public static class PushCommandParser
  {
    public static final String CMD_PARAM_SEPERATOR = "=";
    public static final String CMD_SEPERATOR = "&";
    public static final String FILE_TAG_SEP = ";";
    
    public static LogUploader.PushCommand parsePushCommand(String paramString)
    {
      LogUploader.PushCommand localPushCommand = new LogUploader.PushCommand();
      if ((paramString != null) && (paramString.length() > 0))
      {
        paramString = paramString.split("&");
        if ((paramString != null) && (paramString.length > 0))
        {
          int i = 0;
          while (i < paramString.length)
          {
            Object localObject1 = paramString[i];
            if ((localObject1 != null) && (((String)localObject1).length() > 0))
            {
              Object localObject2 = ((String)localObject1).split("=");
              if ((localObject2 != null) && (localObject2.length > 1))
              {
                localObject1 = localObject2[0].trim();
                localObject2 = localObject2[1].trim();
                if ((localObject1 != "") && (localObject2 != "")) {
                  if ("c".equalsIgnoreCase((String)localObject1))
                  {
                    localPushCommand.mCmdType = Integer.parseInt((String)localObject2);
                  }
                  else if ("t".equalsIgnoreCase((String)localObject1))
                  {
                    localPushCommand.mShowToast = ((String)localObject2).equals("1");
                  }
                  else if ("core".equalsIgnoreCase((String)localObject1))
                  {
                    localPushCommand.mNeedCoreLog = ((String)localObject2).equals("1");
                  }
                  else if ("lc".equalsIgnoreCase((String)localObject1))
                  {
                    localPushCommand.mNeedSaveLogcat = ((String)localObject2).equals("1");
                  }
                  else if ("v".equalsIgnoreCase((String)localObject1))
                  {
                    localPushCommand.mNeedRecordScreen = ((String)localObject2).equals("1");
                  }
                  else if ("lv".equalsIgnoreCase((String)localObject1))
                  {
                    localPushCommand.mLogLevelMask = Integer.valueOf((String)localObject2).intValue();
                  }
                  else if ("s".equalsIgnoreCase((String)localObject1))
                  {
                    localPushCommand.mSaveToPreferrence = ((String)localObject2).equals("1");
                  }
                  else if ("date".equalsIgnoreCase((String)localObject1))
                  {
                    localObject1 = new SimpleDateFormat("yyyyMMddHHmmss");
                    try
                    {
                      localObject1 = ((SimpleDateFormat)localObject1).parse((String)localObject2);
                      if (localObject1 == null) {
                        break label505;
                      }
                      localPushCommand.mDateOfLogToUpload = ((Date)localObject1);
                    }
                    catch (ParseException localParseException)
                    {
                      localParseException.printStackTrace();
                    }
                  }
                  else if ("bdate".equalsIgnoreCase(localParseException))
                  {
                    localPushCommand.mHoursBeforeGivenDate = Float.parseFloat((String)localObject2);
                  }
                  else if ("adate".equalsIgnoreCase(localParseException))
                  {
                    localPushCommand.mHoursAfterGivenDate = Float.parseFloat((String)localObject2);
                  }
                  else if ("info".equalsIgnoreCase(localParseException))
                  {
                    localPushCommand.mExtraInfo = ((String)localObject2);
                  }
                  else if ("eh".equalsIgnoreCase(localParseException))
                  {
                    localPushCommand.mExpireHours = Integer.parseInt((String)localObject2);
                  }
                  else if ("f".equalsIgnoreCase(localParseException))
                  {
                    localPushCommand.mAttachedFileList.add(localObject2);
                  }
                  else if ("dir".equalsIgnoreCase(localParseException))
                  {
                    localPushCommand.mAttachedFileList.add(localObject2);
                  }
                  else if ("lt".equalsIgnoreCase(localParseException))
                  {
                    localPushCommand.mDevLogTagList.add(localObject2);
                  }
                  else if ("lct".equalsIgnoreCase(localParseException))
                  {
                    localPushCommand.mLogcatTagList.add(localObject2);
                  }
                  else if ("g".equalsIgnoreCase(localParseException))
                  {
                    localPushCommand.mGuidList.add(localObject2);
                  }
                }
              }
            }
            label505:
            i += 1;
          }
        }
      }
      return localPushCommand;
    }
  }
  
  private static class UploadTaskObserver
    extends TaskObserverBase
  {
    public ValueCallback<LogUploader.LogUploadResult> mCallback;
    
    public UploadTaskObserver(ValueCallback<LogUploader.LogUploadResult> paramValueCallback)
    {
      this.mCallback = paramValueCallback;
    }
    
    public void onTaskCompleted(Task paramTask)
    {
      paramTask.removeObserver(this);
      paramTask = new LogUploader.LogUploadResult(0, "");
      this.mCallback.onReceiveValue(paramTask);
    }
    
    public void onTaskFailed(Task paramTask)
    {
      paramTask.removeObserver(this);
      paramTask = new LogUploader.LogUploadResult(1, "");
      this.mCallback.onReceiveValue(paramTask);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\log\LogUploader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */