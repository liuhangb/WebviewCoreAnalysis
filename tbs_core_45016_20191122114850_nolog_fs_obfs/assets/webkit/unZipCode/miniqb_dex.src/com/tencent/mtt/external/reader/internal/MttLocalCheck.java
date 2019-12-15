package com.tencent.mtt.external.reader.internal;

import android.content.Context;
import android.os.Message;
import android.text.TextUtils;
import com.tencent.common.utils.FileUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.mtt.external.reader.base.ReaderCheck;
import com.tencent.mtt.external.reader.base.ReaderCheck.CheckCallback;
import com.tencent.mtt.external.reader.base.ReaderLoadingView;
import com.tencent.mtt.external.reader.base.ReaderMessage;
import com.tencent.mtt.external.reader.base.ReaderMessage.MessageEvent;
import com.tencent.mtt.external.reader.utils.AppEngine;
import com.tencent.mtt.external.reader.utils.FileOpenUtils;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class MttLocalCheck
  extends ReaderCheck
{
  public static final int HIDE_PROGRESSTIP = 1;
  public static final int READER_OPEN_ERROR = 7;
  public static final int READER_SO_FAILE = 4;
  public static final int READER_SO_INTO_START = 9;
  public static final int READER_SO_PROGRESS = 5;
  public static final int READER_SO_START = 6;
  public static final int READER_SO_SUCCESS = 2;
  public static final int READER_SO_WILL_START = 8;
  public static final int READER_WAIT_IN_QUEUE = 3;
  private static Map<String, Class<?>> jdField_a_of_type_JavaUtilMap = new HashMap();
  ReaderMessage.MessageEvent jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent = null;
  ReaderMessage jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage = new ReaderMessage();
  ReaderFiletypeDetector jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector = null;
  DexClassLoader jdField_a_of_type_DalvikSystemDexClassLoader;
  String b = "";
  private String c = "";
  
  public MttLocalCheck(String paramString, DexClassLoader paramDexClassLoader)
  {
    super(paramString);
    this.b = paramString;
    this.jdField_a_of_type_DalvikSystemDexClassLoader = paramDexClassLoader;
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector = new ReaderFiletypeDetector(paramString, null);
    a();
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.setEvent(this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent);
  }
  
  private DexClassLoader a(Context paramContext, String paramString1, String paramString2)
  {
    Object localObject = paramContext.getApplicationContext();
    File localFile = ((Context)localObject).getDir("dynamic_jar_output", 0);
    if (localFile == null) {
      return null;
    }
    if (TextUtils.isEmpty(paramString1)) {
      paramContext = new File(localFile, paramString2);
    } else {
      paramContext = new File(paramString1, paramString2);
    }
    paramString1 = new StringBuilder();
    paramString1.append("[getDexClassLoader] dexFile path:");
    paramString1.append(paramContext.getAbsolutePath());
    LogUtils.d("mttlocalcheck", paramString1.toString());
    try
    {
      paramString1 = FileUtils.getNativeLibraryDir((Context)localObject);
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("libPath:");
      ((StringBuilder)localObject).append(paramString1);
      LogUtils.d("mttlocalcheck", ((StringBuilder)localObject).toString());
      paramContext = new DexClassLoader(paramContext.getAbsolutePath(), localFile.getAbsolutePath(), paramString1, this.jdField_a_of_type_DalvikSystemDexClassLoader);
      return paramContext;
    }
    catch (Exception paramContext)
    {
      paramString1 = new StringBuilder();
      paramString1.append("[getDexClassLoader] ");
      paramString1.append(paramString2);
      paramString1.append(" failed");
      LogUtils.d("mttlocalcheck", paramString1.toString());
      paramContext.printStackTrace();
    }
    return null;
  }
  
  Object a(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    Object localObject1 = a(AppEngine.getInstance().getApplicationContext(), paramString1, paramString2);
    if (paramBoolean) {
      paramString1 = (Class)jdField_a_of_type_JavaUtilMap.get(paramString3);
    } else {
      paramString1 = null;
    }
    paramString2 = paramString1;
    Object localObject2;
    if (paramString1 == null)
    {
      paramString2 = paramString1;
      if (localObject1 != null)
      {
        paramString2 = paramString1;
        try
        {
          localObject2 = new StringBuilder();
          paramString2 = paramString1;
          ((StringBuilder)localObject2).append("getDexClassInstance classLoader:");
          paramString2 = paramString1;
          ((StringBuilder)localObject2).append(((DexClassLoader)localObject1).toString());
          paramString2 = paramString1;
          ((StringBuilder)localObject2).append(" classPath:");
          paramString2 = paramString1;
          ((StringBuilder)localObject2).append(paramString3);
          paramString2 = paramString1;
          LogUtils.d("mttlocalcheck", ((StringBuilder)localObject2).toString());
          paramString2 = paramString1;
          paramString1 = ((DexClassLoader)localObject1).loadClass(paramString3);
          if ((paramBoolean) && (paramString1 != null))
          {
            paramString2 = paramString1;
            jdField_a_of_type_JavaUtilMap.put(paramString3, paramString1);
          }
          paramString2 = paramString1;
          localObject1 = new StringBuilder();
          paramString2 = paramString1;
          ((StringBuilder)localObject1).append("getDexClassInstance  dexClass:");
          paramString2 = paramString1;
          ((StringBuilder)localObject1).append(paramString3);
          paramString2 = paramString1;
          LogUtils.d("mttlocalcheck", ((StringBuilder)localObject1).toString());
          paramString2 = paramString1;
        }
        catch (Exception paramString1)
        {
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("[getDexClass] ");
          ((StringBuilder)localObject1).append(paramString3);
          ((StringBuilder)localObject1).append(" failed");
          LogUtils.d("mttlocalcheck", ((StringBuilder)localObject1).toString());
          paramString1.printStackTrace();
        }
      }
    }
    localObject1 = new Object[0];
    int i;
    label371:
    label382:
    int j;
    if (paramString2 != null)
    {
      if (localObject1.length >= 0)
      {
        paramString3 = new Class[localObject1.length];
        i = 0;
        for (;;)
        {
          paramString1 = paramString3;
          if (i >= localObject1.length) {
            break;
          }
          paramString3[i] = localObject1[i].getClass();
          i += 1;
        }
      }
      paramString1 = null;
      try
      {
        paramString3 = paramString2.getDeclaredConstructors();
        paramString2 = new StringBuilder();
        paramString2.append("[constructors] ");
        paramString2.append(paramString3);
        LogUtils.d("mttlocalcheck", paramString2.toString());
        if (paramString3 == null) {
          break label496;
        }
        i = 0;
        if (i >= paramString3.length) {
          break label496;
        }
        paramString2 = paramString3[i];
        localObject2 = paramString2.getParameterTypes();
        if (localObject2 != null) {
          break label452;
        }
        if (paramString1 == null) {
          break label459;
        }
      }
      catch (InvocationTargetException paramString1)
      {
        paramString1.printStackTrace();
        return null;
      }
      catch (IllegalAccessException paramString1)
      {
        paramString1.printStackTrace();
        return null;
      }
      catch (InstantiationException paramString1)
      {
        paramString1.printStackTrace();
        return null;
      }
      catch (IllegalArgumentException paramString1)
      {
        paramString1.printStackTrace();
      }
      if (localObject2.length == paramString1.length) {
        break label459;
      }
      break label487;
      if (j >= localObject2.length) {
        break label474;
      }
      if (localObject2[j].isAssignableFrom(paramString1[j])) {
        break label465;
      }
      j = 0;
      break label477;
    }
    for (;;)
    {
      if (paramString1 != null)
      {
        paramString1 = paramString1.newInstance((Object[])localObject1);
        return paramString1;
      }
      return null;
      label452:
      if (paramString1 != null) {
        break label371;
      }
      break label487;
      label459:
      j = 0;
      break label382;
      label465:
      j += 1;
      break label382;
      label474:
      j = 1;
      label477:
      if (j != 0)
      {
        paramString1 = paramString2;
      }
      else
      {
        label487:
        i += 1;
        break;
        label496:
        paramString1 = null;
      }
    }
  }
  
  void a()
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage$MessageEvent = new ReaderMessage.MessageEvent()
    {
      public void onMessage(Message paramAnonymousMessage)
      {
        switch (paramAnonymousMessage.what)
        {
        case 3: 
        case 7: 
        default: 
          
        case 9: 
          MttLocalCheck.this.a.downloadSo(false);
          return;
        case 6: 
          if (MttLocalCheck.this.mLoadingView != null)
          {
            MttLocalCheck.this.mLoadingView.setTotalSize(paramAnonymousMessage.arg1);
            return;
          }
          break;
        case 5: 
          int i = paramAnonymousMessage.arg1;
          if (MttLocalCheck.this.mLoadingView != null)
          {
            MttLocalCheck.this.mLoadingView.setDetailProgress(i);
            return;
          }
          break;
        case 4: 
          MttLocalCheck.this.a(1, paramAnonymousMessage.arg1);
          return;
        case 2: 
          MttLocalCheck.this.a(0, 0);
        }
      }
    };
  }
  
  void a(int paramInt1, int paramInt2)
  {
    if (paramInt1 == 0)
    {
      this.mCallback.onCheckEvent(paramInt1, paramInt2, a(getReaderPath(), this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector.getDexPath(), this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector.getDexClass(), true));
      return;
    }
    this.mCallback.onCheckEvent(paramInt1, paramInt2, null);
  }
  
  public void cancel() {}
  
  public void check()
  {
    Object localObject1 = getReaderPath();
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("cachePath:");
    ((StringBuilder)localObject2).append((String)localObject1);
    LogUtils.d("MttLocalCheck", ((StringBuilder)localObject2).toString());
    localObject2 = this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFiletypeDetector.getDexPath();
    Object localObject3 = new File((String)localObject1, (String)localObject2);
    Object localObject4 = new StringBuilder();
    ((StringBuilder)localObject4).append("dexFile:");
    ((StringBuilder)localObject4).append(((File)localObject3).getAbsolutePath());
    LogUtils.d("MttLocalCheck", ((StringBuilder)localObject4).toString());
    try
    {
      if (this.b.equalsIgnoreCase("pdf"))
      {
        if (!this.c.isEmpty())
        {
          localObject3 = new StringBuilder();
          ((StringBuilder)localObject3).append(FileUtils.getSDcardDir().getAbsolutePath());
          ((StringBuilder)localObject3).append("/");
          ((StringBuilder)localObject3).append(this.c);
          ((StringBuilder)localObject3).append("/reader/PDFReader.jar");
          localObject3 = ((StringBuilder)localObject3).toString();
          localObject4 = new StringBuilder();
          ((StringBuilder)localObject4).append(FileUtils.getSDcardDir().getAbsolutePath());
          ((StringBuilder)localObject4).append("/");
          ((StringBuilder)localObject4).append(this.c);
          ((StringBuilder)localObject4).append("/reader/libmupdf.so");
          localObject4 = ((StringBuilder)localObject4).toString();
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append((String)localObject1);
          localStringBuilder.append((String)localObject2);
          localObject2 = localStringBuilder.toString();
          localStringBuilder = new StringBuilder();
          localStringBuilder.append((String)localObject1);
          localStringBuilder.append("libmupdf.so");
          localObject1 = localStringBuilder.toString();
          FileUtils.copyFile((String)localObject3, (String)localObject2);
          FileUtils.copyFile((String)localObject4, (String)localObject1);
        }
        else
        {
          FileOpenUtils.copyAssetsFileToEx(AppEngine.getInstance().getApplicationContext(), "reader/PDFReader.jar", (File)localObject3, true);
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append((String)localObject1);
          ((StringBuilder)localObject2).append("libmupdf.so");
          localObject1 = new File(((StringBuilder)localObject2).toString());
          FileOpenUtils.copyAssetsFileToEx(AppEngine.getInstance().getApplicationContext(), "reader/libmupdf.so", (File)localObject1, true);
        }
      }
      else if (this.b.equalsIgnoreCase("chm"))
      {
        FileOpenUtils.copyAssetsFileToEx(AppEngine.getInstance().getApplicationContext(), "reader/ChmReader.jar", (File)localObject3, true);
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append((String)localObject1);
        ((StringBuilder)localObject2).append("libchmlib.so");
        localObject1 = new File(((StringBuilder)localObject2).toString());
        FileOpenUtils.copyAssetsFileToEx(AppEngine.getInstance().getApplicationContext(), "reader/libchmlib.so", (File)localObject1, true);
      }
      else if (this.b.equalsIgnoreCase("docx"))
      {
        FileOpenUtils.copyAssetsFileToEx(AppEngine.getInstance().getApplicationContext(), "reader/DOCXReader.jar", (File)localObject3, true);
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append((String)localObject1);
        ((StringBuilder)localObject2).append("docxJsLibrarys.zip");
        localObject1 = new File(((StringBuilder)localObject2).toString());
        FileOpenUtils.copyAssetsFileToEx(AppEngine.getInstance().getApplicationContext(), "reader/docxJsLibrarys.zip", (File)localObject1, true);
      }
      else if (this.b.equalsIgnoreCase("pptx"))
      {
        FileOpenUtils.copyAssetsFileToEx(AppEngine.getInstance().getApplicationContext(), "reader/PPTXReader.jar", (File)localObject3, true);
      }
      else if (this.b.equalsIgnoreCase("xlsx"))
      {
        FileOpenUtils.copyAssetsFileToEx(AppEngine.getInstance().getApplicationContext(), "reader/XLSXReader.jar", (File)localObject3, true);
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append((String)localObject1);
        ((StringBuilder)localObject2).append("xlsxJsLibrarys.zip");
        localObject1 = new File(((StringBuilder)localObject2).toString());
        FileOpenUtils.copyAssetsFileToEx(AppEngine.getInstance().getApplicationContext(), "reader/xlsxJsLibrarys.zip", (File)localObject1, true);
      }
      else if (this.b.equalsIgnoreCase("doc"))
      {
        FileOpenUtils.copyAssetsFileToEx(AppEngine.getInstance().getApplicationContext(), "reader/DOCReader.jar", (File)localObject3, true);
      }
      else if (this.b.equalsIgnoreCase("xls"))
      {
        FileOpenUtils.copyAssetsFileToEx(AppEngine.getInstance().getApplicationContext(), "reader/EXCELReader.jar", (File)localObject3, true);
      }
      else if (this.b.equalsIgnoreCase("ppt"))
      {
        FileOpenUtils.copyAssetsFileToEx(AppEngine.getInstance().getApplicationContext(), "reader/PPTReader.jar", (File)localObject3, true);
      }
      else if (this.b.equalsIgnoreCase("txt"))
      {
        FileOpenUtils.copyAssetsFileToEx(AppEngine.getInstance().getApplicationContext(), "reader/mttreader.jar", (File)localObject3, true);
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append((String)localObject1);
        ((StringBuilder)localObject2).append("libLineBreak.so");
        localObject1 = new File(((StringBuilder)localObject2).toString());
        FileOpenUtils.copyAssetsFileToEx(AppEngine.getInstance().getApplicationContext(), "reader/libLineBreak.so", (File)localObject1, true);
      }
      else if (this.b.equalsIgnoreCase("epub"))
      {
        FileOpenUtils.copyAssetsFileToEx(AppEngine.getInstance().getApplicationContext(), "reader/EPUBReader.jar", (File)localObject3, true);
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append((String)localObject1);
        ((StringBuilder)localObject2).append("libNativeFormats.so");
        localObject2 = new File(((StringBuilder)localObject2).toString());
        FileOpenUtils.copyAssetsFileToEx(AppEngine.getInstance().getApplicationContext(), "reader/libNativeFormats.so", (File)localObject2, true);
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append((String)localObject1);
        ((StringBuilder)localObject2).append("xhtml-lat1.ent");
        localObject2 = new File(((StringBuilder)localObject2).toString());
        FileOpenUtils.copyAssetsFileToEx(AppEngine.getInstance().getApplicationContext(), "reader/xhtml-lat1.ent", (File)localObject2, true);
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append((String)localObject1);
        ((StringBuilder)localObject2).append("xhtml-special.ent");
        localObject2 = new File(((StringBuilder)localObject2).toString());
        FileOpenUtils.copyAssetsFileToEx(AppEngine.getInstance().getApplicationContext(), "reader/xhtml-special.ent", (File)localObject2, true);
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append((String)localObject1);
        ((StringBuilder)localObject2).append("xhtml-symbol.ent");
        localObject2 = new File(((StringBuilder)localObject2).toString());
        FileOpenUtils.copyAssetsFileToEx(AppEngine.getInstance().getApplicationContext(), "reader/xhtml-symbol.ent", (File)localObject2, true);
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append((String)localObject1);
        ((StringBuilder)localObject2).append("styles.xml");
        localObject1 = new File(((StringBuilder)localObject2).toString());
        FileOpenUtils.copyAssetsFileToEx(AppEngine.getInstance().getApplicationContext(), "reader/styles.xml", (File)localObject1, true);
      }
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMessage.send(2);
  }
  
  public void close() {}
  
  public String getReaderPath()
  {
    Context localContext = AppEngine.getInstance().getApplicationContext();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(ReaderPluginSoSession.getInstance().getCachePath(localContext));
    localStringBuilder.append(File.separator);
    return localStringBuilder.toString();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\internal\MttLocalCheck.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */