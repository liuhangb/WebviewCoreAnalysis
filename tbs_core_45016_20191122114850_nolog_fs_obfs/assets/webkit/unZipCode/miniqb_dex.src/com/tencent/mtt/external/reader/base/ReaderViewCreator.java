package com.tencent.mtt.external.reader.base;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.mtt.external.reader.export.IReaderWebViewProxy;
import java.util.Locale;

public class ReaderViewCreator
{
  public static final int READER_TYPE_VIEW_CHM_WEBVIEW = 8;
  public static final int READER_TYPE_VIEW_CONTENT = 1;
  public static final int READER_TYPE_VIEW_FAILED = 3;
  public static final int READER_TYPE_VIEW_LOADING = 2;
  public static final int READER_TYPE_VIEW_PDF = 4;
  public static final int READER_TYPE_VIEW_SELECT = 7;
  public static final int READER_TYPE_VIEW_TXT = 6;
  public static final int READER_TYPE_VIEW_WEBVIEW = 5;
  public static final int READER_VIEW_QB = 1;
  public static final int READER_VIEW_SYSTEM = 2;
  static ReaderViewCreator jdField_a_of_type_ComTencentMttExternalReaderBaseReaderViewCreator;
  private static final String[] jdField_a_of_type_ArrayOfJavaLangString = { "ini", "log", "bat", "php", "js", "lrc", "txt" };
  private static final String[] b = { "pdf", "epub", "doc", "docx", "xls", "xlsx", "ppt", "pptx" };
  private IReaderWebViewProxy jdField_a_of_type_ComTencentMttExternalReaderExportIReaderWebViewProxy = null;
  
  public static void destroy()
  {
    ReaderViewCreator localReaderViewCreator = jdField_a_of_type_ComTencentMttExternalReaderBaseReaderViewCreator;
    if (localReaderViewCreator != null)
    {
      if (localReaderViewCreator.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderWebViewProxy != null) {
        localReaderViewCreator.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderWebViewProxy = null;
      }
      jdField_a_of_type_ComTencentMttExternalReaderBaseReaderViewCreator = null;
    }
  }
  
  public static ReaderViewCreator getInstance()
  {
    if (jdField_a_of_type_ComTencentMttExternalReaderBaseReaderViewCreator == null) {
      jdField_a_of_type_ComTencentMttExternalReaderBaseReaderViewCreator = new ReaderViewCreator();
    }
    return jdField_a_of_type_ComTencentMttExternalReaderBaseReaderViewCreator;
  }
  
  public static boolean isDefault(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      paramString = paramString.toLowerCase(Locale.US);
      String[] arrayOfString = b;
      int j = arrayOfString.length;
      int i = 0;
      while (i < j)
      {
        if (paramString.equals(arrayOfString[i])) {
          return true;
        }
        i += 1;
      }
    }
    return false;
  }
  
  public static boolean isSupportExt(String paramString)
  {
    return (isDefault(paramString)) || (isTxt(paramString));
  }
  
  public static boolean isTxt(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      paramString = paramString.toLowerCase(Locale.US);
      String[] arrayOfString = jdField_a_of_type_ArrayOfJavaLangString;
      int j = arrayOfString.length;
      int i = 0;
      while (i < j)
      {
        if (paramString.equals(arrayOfString[i])) {
          return true;
        }
        i += 1;
      }
    }
    return false;
  }
  
  public static void setViewCreator(ReaderViewCreator paramReaderViewCreator)
  {
    jdField_a_of_type_ComTencentMttExternalReaderBaseReaderViewCreator = paramReaderViewCreator;
  }
  
  public ReaderBaseView create(Context paramContext, int paramInt)
  {
    if (paramInt == 1) {
      return new ReaderContentView(paramContext);
    }
    return null;
  }
  
  public ReaderBaseView create(Context paramContext, String paramString)
  {
    if (isTxt(paramString)) {
      return new ReaderTxtView(paramContext);
    }
    if (paramString.equalsIgnoreCase("pdf")) {
      return new ReaderPdfView(paramContext);
    }
    if (paramString.equalsIgnoreCase("ppt")) {
      return new ReaderPPTView(paramContext);
    }
    if (paramString.equalsIgnoreCase("pptx")) {
      return new ReaderPPTView(paramContext);
    }
    if (paramString.equalsIgnoreCase("chm")) {
      return new ReaderChmView(paramContext);
    }
    if (isDefault(paramString)) {
      return new ReaderDefaultView(paramContext);
    }
    return null;
  }
  
  public IReaderWebViewProxy getWebViewProxy()
  {
    return this.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderWebViewProxy;
  }
  
  public void setWebViewProxy(IReaderWebViewProxy paramIReaderWebViewProxy)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderWebViewProxy = paramIReaderWebViewProxy;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\base\ReaderViewCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */