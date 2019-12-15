package com.tencent.mtt.external.reader.internal;

import android.text.TextUtils;
import com.tencent.common.plugin.IQBPluginSystemCallback;
import com.tencent.common.plugin.QBPluginItemInfo;
import com.tencent.common.plugin.QBPluginSystem.ILoadPluginCallback;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.utils.StringUtils;
import com.tencent.mtt.external.reader.MttFileReaderWrapper;
import com.tencent.mtt.external.reader.utils.FileOpenUtils;
import java.io.File;

public class ReaderFiletypeDetector
  implements IQBPluginSystemCallback, QBPluginSystem.ILoadPluginCallback
{
  private final int jdField_a_of_type_Int = 0;
  private onFiletypeDetectorCallback jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector$onFiletypeDetectorCallback = null;
  private ReaderPluginSoSession jdField_a_of_type_ComTencentMttExternalReaderInternalReaderPluginSoSession = null;
  private boolean jdField_a_of_type_Boolean = false;
  private final int b = 3;
  private final int c = 4;
  private final int d = 5;
  private final int e = 6;
  private final int f = 7;
  private final int g = 8;
  private final int h = 9;
  private final int i = 10;
  private final int j = 11;
  private final int k = 12;
  private final int l = 13;
  private final int m = 14;
  public int mRequestID = 0;
  
  public ReaderFiletypeDetector(String paramString, onFiletypeDetectorCallback paramonFiletypeDetectorCallback)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector$onFiletypeDetectorCallback = paramonFiletypeDetectorCallback;
    this.mRequestID = getRequestID(paramString);
    if (this.mRequestID > 0) {
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderPluginSoSession = ReaderPluginSoSession.getInstance();
    }
  }
  
  private void a()
  {
    if (!this.jdField_a_of_type_Boolean) {
      return;
    }
    String str = getDexPath();
    ReaderInstaller.getInstance().chmod(str);
  }
  
  public void cancel()
  {
    ReaderPluginSoSession localReaderPluginSoSession = this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderPluginSoSession;
    if (localReaderPluginSoSession != null) {
      localReaderPluginSoSession.stopDownloadPlugin(this.mRequestID, this);
    }
  }
  
  public void downloadSo(boolean paramBoolean)
  {
    ReaderPluginSoSession localReaderPluginSoSession = this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderPluginSoSession;
    if (localReaderPluginSoSession != null) {
      localReaderPluginSoSession.downloadPluginOrLoadLocal(this.mRequestID, this, this, paramBoolean);
    }
  }
  
  public void finish()
  {
    ReaderPluginSoSession localReaderPluginSoSession = this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderPluginSoSession;
    if (localReaderPluginSoSession != null)
    {
      localReaderPluginSoSession.stopDownloadPlugin(this.mRequestID, this);
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderPluginSoSession = null;
    }
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector$onFiletypeDetectorCallback = null;
  }
  
  public String getDexClass()
  {
    String str = "";
    Object localObject = str;
    if (!MttFileReaderWrapper.USELOCAL)
    {
      localObject = this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderPluginSoSession;
      if (localObject != null) {
        str = ((ReaderPluginSoSession)localObject).getPluginExt(this.mRequestID);
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("class=");
      ((StringBuilder)localObject).append(str);
      LogUtils.d("ReaderFiletypeDetector", ((StringBuilder)localObject).toString());
      localObject = str;
      if (!TextUtils.isEmpty(str)) {
        return str.trim();
      }
    }
    int n = this.mRequestID;
    if (n == 3) {
      return "com.Reader.PDFReader";
    }
    if (n == 4) {
      return "com.PPTReader.PPTReader";
    }
    if (n == 5) {
      return "com.DOCXReader.DocxReader";
    }
    if (n == 6) {
      return "com.PPTXReader.PPTXReader";
    }
    if (n == 7) {
      return "com.XLSXReader.XLSXReader";
    }
    if (n == 8) {
      return "com.ExcelReader.ExcelReader";
    }
    if (n == 9) {
      return "com.tencent.docread.DocReader";
    }
    if (n == 11) {
      return "org.geometerplus.android.fbreader.FBReader";
    }
    if (n == 12) {
      return "com.ChmReader.ChmReader";
    }
    if (n == 13) {
      localObject = "com.tencent.mttreader.MTTReader";
    }
    return (String)localObject;
  }
  
  public String getDexPath()
  {
    String str = "";
    int n = this.mRequestID;
    if (n == 3) {
      return "PDFReader.jar";
    }
    if (n == 4) {
      return "PPTXReader.jar";
    }
    if (n == 5) {
      return "DOCXReader.jar";
    }
    if (n == 6) {
      return "PPTXReader.jar";
    }
    if (n == 7) {
      return "XLSXReader.jar";
    }
    if (n == 8) {
      return "XLSXReader.jar";
    }
    if (n == 9) {
      return "DOCReader.jar";
    }
    if (n == 11) {
      return "EPUBReader.jar";
    }
    if (n == 12) {
      return "ChmReader.jar";
    }
    if (n == 13) {
      str = "mttreader.jar";
    }
    return str;
  }
  
  public int getRequestID(String paramString)
  {
    boolean bool = TextUtils.isEmpty(paramString);
    int n = 0;
    int i1 = 0;
    if (!bool)
    {
      if (paramString.equalsIgnoreCase("pdf"))
      {
        n = 3;
      }
      else if (paramString.equalsIgnoreCase("ppt"))
      {
        n = 6;
      }
      else if (paramString.equalsIgnoreCase("docx"))
      {
        n = 5;
      }
      else if (paramString.equalsIgnoreCase("pptx"))
      {
        n = 6;
      }
      else if (paramString.equalsIgnoreCase("xlsx"))
      {
        n = 7;
      }
      else if (paramString.equalsIgnoreCase("xls"))
      {
        n = 7;
      }
      else if (paramString.equalsIgnoreCase("doc"))
      {
        n = 5;
      }
      else if (paramString.equalsIgnoreCase("epub"))
      {
        n = 11;
      }
      else
      {
        n = i1;
        if (paramString.equalsIgnoreCase("chm")) {
          n = 12;
        }
      }
      if (FileOpenUtils.isBasicReaderType(paramString)) {
        return 13;
      }
      if (paramString.equalsIgnoreCase("reader_menu")) {
        n = 14;
      }
    }
    return n;
  }
  
  public String getSoCachePath()
  {
    int n = this.mRequestID;
    if (n > 0) {
      return this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderPluginSoSession.getPluginPath(n);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(ReaderInstaller.getInstance().getReaderSharePath());
    localStringBuilder.append(File.separator);
    return localStringBuilder.toString();
  }
  
  public String getSoSize()
  {
    ReaderPluginSoSession localReaderPluginSoSession = this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderPluginSoSession;
    if (localReaderPluginSoSession != null)
    {
      int n = this.mRequestID;
      if (n > 0)
      {
        n = localReaderPluginSoSession.getPluginSize(n);
        if (n > 0) {
          return StringUtils.getSizeString(n);
        }
        return "";
      }
    }
    return "";
  }
  
  public boolean hasPlugin(String paramString)
  {
    ReaderPluginSoSession localReaderPluginSoSession = this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderPluginSoSession;
    if (localReaderPluginSoSession != null)
    {
      int n = this.mRequestID;
      if (n > 0) {
        return paramString.equalsIgnoreCase(localReaderPluginSoSession.getPluginVersion(n));
      }
    }
    return false;
  }
  
  public boolean isPluginInstalled()
  {
    ReaderPluginSoSession localReaderPluginSoSession = this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderPluginSoSession;
    if (localReaderPluginSoSession != null)
    {
      int n = this.mRequestID;
      if (n > 0) {
        return localReaderPluginSoSession.isPluginInstalled(n);
      }
    }
    return false;
  }
  
  public int isPluginNeedDownload()
  {
    return this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderPluginSoSession.isPluginNeedDownload(this.mRequestID);
  }
  
  public int load(String paramString, QBPluginItemInfo paramQBPluginItemInfo, int paramInt)
  {
    paramString = this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector$onFiletypeDetectorCallback;
    if ((paramString != null) && (!paramString.onSoILoad())) {
      return 1;
    }
    return 0;
  }
  
  public void onDownloadCreateed(String paramString1, String paramString2) {}
  
  public void onDownloadProgress(String paramString, int paramInt1, int paramInt2)
  {
    paramString = this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector$onFiletypeDetectorCallback;
    if (paramString != null) {
      paramString.onSoDownloadProgress(paramInt1);
    }
  }
  
  public void onDownloadStart(String paramString, int paramInt)
  {
    paramString = this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector$onFiletypeDetectorCallback;
    if (paramString != null) {
      paramString.onSoDownloadStart(paramInt);
    }
  }
  
  public void onDownloadSuccessed(String paramString1, String paramString2) {}
  
  public void onNeedDownloadNotify(String paramString, boolean paramBoolean) {}
  
  public void onPrepareFinished(String paramString, QBPluginItemInfo paramQBPluginItemInfo, int paramInt1, int paramInt2)
  {
    paramString = this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector$onFiletypeDetectorCallback;
    if (paramString != null)
    {
      if (paramInt1 == 0)
      {
        a();
        this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector$onFiletypeDetectorCallback.onSoDownloadSuccess(0);
        return;
      }
      if (paramInt2 != 3010) {
        paramString.onSoDownloadError(paramInt2);
      }
    }
  }
  
  public void onPrepareStart(String paramString)
  {
    paramString = this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector$onFiletypeDetectorCallback;
    if (paramString != null) {
      paramString.onSoDownloadWillStart();
    }
  }
  
  public void prepare(boolean paramBoolean)
  {
    ReaderPluginSoSession localReaderPluginSoSession = this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderPluginSoSession;
    if (localReaderPluginSoSession != null)
    {
      int n = this.mRequestID;
      if (n > 0) {
        localReaderPluginSoSession.prepareSoSessionIfNeed(n, this, this, false, paramBoolean);
      }
    }
  }
  
  public void setThrCall(boolean paramBoolean)
  {
    this.jdField_a_of_type_Boolean = paramBoolean;
  }
  
  public static abstract interface onFiletypeDetectorCallback
  {
    public abstract void onSoDownloadError(int paramInt);
    
    public abstract void onSoDownloadProgress(int paramInt);
    
    public abstract void onSoDownloadStart(int paramInt);
    
    public abstract void onSoDownloadSuccess(int paramInt);
    
    public abstract void onSoDownloadWillStart();
    
    public abstract boolean onSoILoad();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\internal\ReaderFiletypeDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */