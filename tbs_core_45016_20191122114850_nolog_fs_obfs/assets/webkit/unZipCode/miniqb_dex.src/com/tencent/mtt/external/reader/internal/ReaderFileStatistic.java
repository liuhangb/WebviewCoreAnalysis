package com.tencent.mtt.external.reader.internal;

import android.text.TextUtils;
import com.tencent.common.http.Apn;
import com.tencent.common.utils.FileUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.mtt.external.reader.utils.AppEngine;
import com.tencent.tbs.common.MTT.CommStatData;
import com.tencent.tbs.common.beacon.X5CoreBeaconUploader;
import com.tencent.tbs.common.stat.TBSStatManager;
import java.io.File;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class ReaderFileStatistic
{
  public static final int FILE_CORE_RETRY_BEGIN = 1000000;
  public static final int FILE_DEFAULT_ERR = -1;
  public static final int FILE_DEX_INFO = 900;
  public static final int FILE_DOC_COMMAN_ERR = 2000;
  public static final int FILE_DOWNLOAD_BEGIN = 500;
  public static final int FILE_DOWNLOAD_ERR = 5;
  public static final int FILE_DOWNLOAD_SO_WITHOUT_NETWORK = 7;
  public static final int FILE_EXCEPTION = 1006;
  public static final int FILE_FORMAT_UNEXPECTED = 10;
  public static final int FILE_LOADING = 0;
  public static final int FILE_LOAD_TIME_OUT = 6;
  public static final int FILE_LOSE_ERR = 1005;
  public static final int FILE_OPEN_ERR = 4;
  public static final int FILE_OPEN_TIMEOUT = 1007;
  public static final int FILE_PREDOWNLOAD_ERR = 800;
  public static final int FILE_REQUEST_PASSWORD = 9;
  public static final int FILE_RESULT_INIT = -1;
  public static final int FILE_SO_DOWNLOAD_ERR = 2;
  public static final int FILE_SO_LOAD_ERR = 3;
  public static final int FILE_SUCCESS = 1;
  public static final int FILE_TOBROWSER_ERR = 1004;
  public static final int FILE_USER_NORMAL_CANCEL = 8;
  public static String LOGTAG = "ReaderFileStatistic";
  public static final String READER_STATISTICS_COUNT_CANCEL_LOADED_BTN = "AHNG802";
  public static final String READER_STATISTICS_COUNT_CLICK_LOADED_BTN = "AHNG801";
  public static final String READER_STATISTICS_COUNT_RETRY_BTN = "AHNG803";
  public static final String READER_STATISTICS_LOAD_FILE_FAILED = "AHNG821";
  public static final String READER_STATISTICS_LOAD_FILE_SUCCESS = "AHNG820";
  public static final String READER_STATISTICS_LOAD_SO_FAILED = "AHNG819";
  public static final String READER_STATISTICS_LOAD_SO_SUCCESS = "AHNG818";
  public static final int SO_DOWNLOADED = 1;
  public static final int SO_NEED_DOWNLOAD = 0;
  public static final int SO_TYPE_CHM = 11;
  public static final int SO_TYPE_DOC = 12;
  public static final int SO_TYPE_DOCX = 2;
  public static final int SO_TYPE_EPUB = 7;
  public static final int SO_TYPE_MTTREADER = 10;
  public static final int SO_TYPE_MUSIC = 5;
  public static final int SO_TYPE_PDF = 9;
  public static final int SO_TYPE_PPT = 0;
  public static final int SO_TYPE_PPTX = 3;
  public static final int SO_TYPE_SYSTEM_MUSIC = 6;
  public static final int SO_TYPE_XLS = 13;
  public static final int SO_TYPE_XLSX = 4;
  public static final int SO_TYPE_XLS_DOC = 1;
  public static final int SO_TYPE_ZIP = 8;
  public int SoState = 1;
  private long jdField_a_of_type_Long = -1L;
  private String jdField_a_of_type_JavaLangString = "unknown_file";
  private TreeMap<E_REPORT_KEYS, String> jdField_a_of_type_JavaUtilTreeMap = null;
  private boolean jdField_a_of_type_Boolean = false;
  private long jdField_b_of_type_Long = System.currentTimeMillis();
  private String jdField_b_of_type_JavaLangString = "unknown_file_url";
  private boolean jdField_b_of_type_Boolean = false;
  private long jdField_c_of_type_Long = this.jdField_b_of_type_Long;
  private boolean jdField_c_of_type_Boolean = false;
  public int errCode = -1;
  public String fileExt = "unknown_ext";
  public String from = "tbs";
  public int openResult = -1;
  public int soType = -1;
  public String soVersion = "unknown_so";
  
  private void a()
  {
    if (this.jdField_a_of_type_JavaUtilTreeMap == null)
    {
      this.jdField_a_of_type_JavaUtilTreeMap = new TreeMap();
      E_REPORT_KEYS[] arrayOfE_REPORT_KEYS = E_REPORT_KEYS.values();
      int j = arrayOfE_REPORT_KEYS.length;
      int i = 0;
      while (i < j)
      {
        E_REPORT_KEYS localE_REPORT_KEYS = arrayOfE_REPORT_KEYS[i];
        this.jdField_a_of_type_JavaUtilTreeMap.put(localE_REPORT_KEYS, "");
        i += 1;
      }
    }
  }
  
  public void addToStatManager(boolean paramBoolean)
  {
    if (paramBoolean) {
      this.jdField_b_of_type_Boolean = true;
    }
    if ((this.jdField_b_of_type_Boolean) && (this.jdField_a_of_type_Boolean)) {
      return;
    }
    int i;
    if (!Apn.isNetworkAvailable()) {
      i = 1;
    } else if (Apn.isWifiMode()) {
      i = 2;
    } else {
      i = 3;
    }
    long l2 = System.currentTimeMillis();
    long l1 = this.jdField_b_of_type_Long;
    l2 -= l1;
    long l3 = this.jdField_c_of_type_Long;
    this.jdField_a_of_type_Boolean = true;
    if (this.SoState != -1)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append(this.SoState);
      ((StringBuilder)localObject1).append("");
      ((StringBuilder)localObject1).toString();
    }
    if (this.soType != -1)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append(this.soType);
      ((StringBuilder)localObject1).append("");
      ((StringBuilder)localObject1).toString();
    }
    Object localObject1 = this.soVersion;
    localObject1 = this.fileExt;
    if (localObject1 == null) {
      localObject2 = "";
    } else {
      localObject2 = ((String)localObject1).toLowerCase();
    }
    if (this.jdField_a_of_type_Long != -1L)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append(this.jdField_a_of_type_Long);
      ((StringBuilder)localObject1).append("");
      ((StringBuilder)localObject1).toString();
    }
    Object localObject3 = String.valueOf(this.openResult);
    int j = this.openResult;
    if (2 == j)
    {
      localObject1 = localObject3;
      if (1 == i) {
        localObject1 = String.valueOf(7);
      }
    }
    else
    {
      localObject1 = localObject3;
      if (j == 0)
      {
        localObject1 = localObject3;
        if (paramBoolean)
        {
          localObject1 = localObject3;
          if (l2 > 10000L) {
            localObject1 = String.valueOf(6);
          }
        }
      }
    }
    j = this.errCode;
    if ((j != -1) && (j != 0))
    {
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append(this.errCode);
      ((StringBuilder)localObject3).append("");
      localObject3 = ((StringBuilder)localObject3).toString();
    }
    else
    {
      localObject3 = "";
    }
    HashMap localHashMap = new HashMap();
    if (this.from == null)
    {
      localObject4 = "";
    }
    else
    {
      localObject4 = new StringBuilder();
      ((StringBuilder)localObject4).append(this.from);
      ((StringBuilder)localObject4).append("");
      localObject4 = ((StringBuilder)localObject4).toString();
    }
    localHashMap.put("fileFrom", localObject4);
    if (this.SoState == -1)
    {
      localObject4 = "";
    }
    else
    {
      localObject4 = new StringBuilder();
      ((StringBuilder)localObject4).append(this.SoState);
      ((StringBuilder)localObject4).append("");
      localObject4 = ((StringBuilder)localObject4).toString();
    }
    localHashMap.put("isNew", localObject4);
    if (this.soType == -1)
    {
      localObject4 = "";
    }
    else
    {
      localObject4 = new StringBuilder();
      ((StringBuilder)localObject4).append(this.soType);
      ((StringBuilder)localObject4).append("");
      localObject4 = ((StringBuilder)localObject4).toString();
    }
    localHashMap.put("soName", localObject4);
    String str = this.soVersion;
    Object localObject4 = str;
    if (str == null) {
      localObject4 = "";
    }
    localHashMap.put("version", localObject4);
    localHashMap.put("fileType", localObject2);
    if (this.jdField_a_of_type_Long == -1L)
    {
      localObject2 = "";
    }
    else
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append(this.jdField_a_of_type_Long);
      ((StringBuilder)localObject2).append("");
      localObject2 = ((StringBuilder)localObject2).toString();
    }
    localHashMap.put("fileSize", localObject2);
    localHashMap.put("result", localObject1);
    localHashMap.put("errcode", localObject3);
    localHashMap.put("elapsedTime", String.valueOf(l2));
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append(i);
    ((StringBuilder)localObject1).append("");
    localHashMap.put("netStat", ((StringBuilder)localObject1).toString());
    localHashMap.put("openCost", String.valueOf(l3 - l1));
    localHashMap.put("fileName", this.jdField_a_of_type_JavaLangString);
    localObject1 = LOGTAG;
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("addToStatManager: ");
    ((StringBuilder)localObject2).append(localHashMap);
    LogUtils.d((String)localObject1, ((StringBuilder)localObject2).toString());
    X5CoreBeaconUploader.getInstance(AppEngine.getInstance().getApplicationContext()).upLoadToBeacon("file", localHashMap);
  }
  
  public void doReport(String paramString1, int paramInt, String paramString2)
  {
    for (;;)
    {
      try
      {
        Object localObject1 = LOGTAG;
        Object localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("doReport:");
        ((StringBuilder)localObject2).append(paramString1);
        ((StringBuilder)localObject2).append(" errCode:");
        ((StringBuilder)localObject2).append(paramInt);
        ((StringBuilder)localObject2).append(" errMsg:");
        ((StringBuilder)localObject2).append(paramString2);
        ((StringBuilder)localObject2).append(" |trace done");
        LogUtils.d((String)localObject1, ((StringBuilder)localObject2).toString());
        localObject1 = new CommStatData();
        ((CommStatData)localObject1).sAppKey = "file_err";
        setReportData(E_REPORT_KEYS.KEY_FROM, this.from);
        setReportData(E_REPORT_KEYS.KEY_ERR_CODE, String.valueOf(paramInt));
        setReportData(E_REPORT_KEYS.KEY_ERR_MSG, String.valueOf(paramString2));
        setReportData(E_REPORT_KEYS.KEY_FILE_SIZE, String.valueOf(this.jdField_a_of_type_Long));
        setReportData(E_REPORT_KEYS.KEY_SO_NAME, String.valueOf(this.soType));
        setReportData(E_REPORT_KEYS.KEY_SO_VER, this.soVersion);
        paramString2 = this.jdField_a_of_type_JavaLangString;
        if (!TextUtils.isEmpty(this.jdField_b_of_type_JavaLangString))
        {
          paramString2 = new StringBuilder();
          paramString2.append(this.jdField_a_of_type_JavaLangString);
          paramString2.append("(url:");
          paramString2.append(this.jdField_b_of_type_JavaLangString);
          paramString2.append(")");
          paramString2 = paramString2.toString();
        }
        setReportData(E_REPORT_KEYS.KEY_FILE_NAME, paramString2);
        setReportData(E_REPORT_KEYS.KEY_TRACK_INFO, paramString1);
        paramString2 = this.jdField_a_of_type_JavaUtilTreeMap.entrySet().iterator();
        if (paramString2.hasNext())
        {
          localObject2 = (Map.Entry)paramString2.next();
          paramString1 = (String)((Map.Entry)localObject2).getValue();
          if (paramString1 != null)
          {
            paramString1.replace("|", "&brvbar;");
            ((CommStatData)localObject1).put(((E_REPORT_KEYS)((Map.Entry)localObject2).getKey()).toString(), paramString1);
            String str = LOGTAG;
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append(((E_REPORT_KEYS)((Map.Entry)localObject2).getKey()).toString());
            localStringBuilder.append("=");
            localStringBuilder.append(paramString1);
            LogUtils.d(str, localStringBuilder.toString());
          }
        }
        else
        {
          TBSStatManager.getInstance().statSTCommonData((CommStatData)localObject1);
          return;
        }
      }
      catch (Throwable paramString1)
      {
        paramString1.printStackTrace();
        return;
      }
      paramString1 = "null";
    }
  }
  
  public void doReportException(String paramString, Throwable paramThrowable)
  {
    for (;;)
    {
      StackTraceElement[] arrayOfStackTraceElement;
      int j;
      try
      {
        arrayOfStackTraceElement = paramThrowable.getStackTrace();
        localFormatter = new Formatter();
        localFormatter.format("%s", new Object[] { paramThrowable.getMessage() });
        if (arrayOfStackTraceElement.length > 3) {
          i = 3;
        } else {
          i = arrayOfStackTraceElement.length;
        }
      }
      catch (Throwable paramString)
      {
        Formatter localFormatter;
        int i;
        paramString.printStackTrace();
        return;
      }
      if (j < i)
      {
        localFormatter.format("[%d]%s.%s(%s:%d)", new Object[] { Integer.valueOf(j), arrayOfStackTraceElement[j].getClassName(), arrayOfStackTraceElement[j].getMethodName(), arrayOfStackTraceElement[j].getFileName(), Integer.valueOf(arrayOfStackTraceElement[j].getLineNumber()) });
        j += 1;
      }
      else
      {
        doReport(paramString, 1006, localFormatter.toString());
        return;
        if (arrayOfStackTraceElement != null) {
          j = 0;
        }
      }
    }
  }
  
  public void initFileOpenTM()
  {
    this.jdField_b_of_type_Long = System.currentTimeMillis();
  }
  
  public void setErrCode(int paramInt)
  {
    this.errCode = paramInt;
  }
  
  public void setFileSizeFromPath(String paramString)
  {
    this.jdField_a_of_type_JavaLangString = paramString;
    if (!TextUtils.isEmpty(paramString)) {
      this.jdField_a_of_type_Long = FileUtils.getFileOrDirectorySize(new File(paramString));
    }
  }
  
  public void setFileUrl(String paramString)
  {
    this.jdField_b_of_type_JavaLangString = paramString;
  }
  
  public void setNeedPassword(boolean paramBoolean)
  {
    this.jdField_c_of_type_Boolean = paramBoolean;
  }
  
  public void setOpenFinished()
  {
    this.jdField_c_of_type_Long = System.currentTimeMillis();
  }
  
  public void setOpenResult(int paramInt)
  {
    this.openResult = paramInt;
  }
  
  public void setReportData(E_REPORT_KEYS paramE_REPORT_KEYS, String paramString)
  {
    a();
    this.jdField_a_of_type_JavaUtilTreeMap.put(paramE_REPORT_KEYS, paramString);
  }
  
  public final void setSoName(String paramString)
  {
    if (paramString == null) {
      return;
    }
    if (paramString.equalsIgnoreCase("PPTReader"))
    {
      this.soType = 0;
      return;
    }
    if (paramString.equalsIgnoreCase("XLSDOCReader"))
    {
      this.soType = 1;
      return;
    }
    if (paramString.equalsIgnoreCase("DOCXReader"))
    {
      this.soType = 2;
      return;
    }
    if (paramString.equalsIgnoreCase("PPTXReader"))
    {
      this.soType = 3;
      return;
    }
    if (paramString.equalsIgnoreCase("XLSXReader"))
    {
      this.soType = 4;
      return;
    }
    if (paramString.equalsIgnoreCase("EPUBReader"))
    {
      this.soType = 7;
      return;
    }
    if (paramString.equalsIgnoreCase("ZIPReader"))
    {
      this.soType = 8;
      return;
    }
    if (paramString.equalsIgnoreCase("MTTReader"))
    {
      this.soType = 10;
      return;
    }
    if (paramString.equalsIgnoreCase("PDFReader"))
    {
      this.soType = 9;
      return;
    }
    if (paramString.equalsIgnoreCase("ChmReader"))
    {
      this.soType = 11;
      return;
    }
    if (paramString.equalsIgnoreCase("ExcelReader"))
    {
      this.soType = 13;
      return;
    }
    if (paramString.equalsIgnoreCase("DOCReader")) {
      this.soType = 12;
    }
  }
  
  public static enum E_REPORT_KEYS
  {
    static
    {
      KEY_FILE_NAME = new E_REPORT_KEYS("KEY_FILE_NAME", 1);
      KEY_FILE_SIZE = new E_REPORT_KEYS("KEY_FILE_SIZE", 2);
      KEY_SO_NAME = new E_REPORT_KEYS("KEY_SO_NAME", 3);
      KEY_SO_VER = new E_REPORT_KEYS("KEY_SO_VER", 4);
      KEY_TRACK_INFO = new E_REPORT_KEYS("KEY_TRACK_INFO", 5);
    }
    
    private E_REPORT_KEYS() {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\internal\ReaderFileStatistic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */