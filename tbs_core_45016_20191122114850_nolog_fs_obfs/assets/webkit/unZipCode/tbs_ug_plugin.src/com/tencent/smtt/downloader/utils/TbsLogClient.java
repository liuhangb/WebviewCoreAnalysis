package com.tencent.smtt.downloader.utils;

import android.content.Context;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import android.widget.TextView;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class TbsLogClient
{
  static TbsLogClient jdField_a_of_type_ComTencentSmttDownloaderUtilsTbsLogClient;
  static File jdField_a_of_type_JavaIoFile;
  static String jdField_a_of_type_JavaLangString;
  private static boolean jdField_a_of_type_Boolean = true;
  static byte[] jdField_a_of_type_ArrayOfByte;
  private Context jdField_a_of_type_AndroidContentContext = null;
  TextView jdField_a_of_type_AndroidWidgetTextView;
  private StringBuffer jdField_a_of_type_JavaLangStringBuffer = new StringBuffer();
  private SimpleDateFormat jdField_a_of_type_JavaTextSimpleDateFormat = null;
  
  public TbsLogClient(Context paramContext)
  {
    try
    {
      this.jdField_a_of_type_AndroidContentContext = paramContext.getApplicationContext();
      this.jdField_a_of_type_JavaTextSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS", Locale.US);
      return;
    }
    catch (Exception paramContext)
    {
      for (;;) {}
    }
    this.jdField_a_of_type_JavaTextSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS");
  }
  
  public static void a(boolean paramBoolean)
  {
    jdField_a_of_type_Boolean = paramBoolean;
  }
  
  private void b()
  {
    try
    {
      if (jdField_a_of_type_JavaIoFile == null)
      {
        if (Environment.getExternalStorageState().equals("mounted"))
        {
          String str = FileUtil.a(this.jdField_a_of_type_AndroidContentContext, 6);
          if (str == null)
          {
            jdField_a_of_type_JavaIoFile = null;
            return;
          }
          jdField_a_of_type_JavaIoFile = new File(str, "tbslog.txt");
          jdField_a_of_type_JavaLangString = d.a();
          jdField_a_of_type_ArrayOfByte = d.c(jdField_a_of_type_JavaIoFile.getName(), jdField_a_of_type_JavaLangString);
          return;
        }
        jdField_a_of_type_JavaIoFile = null;
        return;
      }
    }
    catch (NullPointerException localNullPointerException)
    {
      localNullPointerException.printStackTrace();
      return;
    }
    catch (SecurityException localSecurityException)
    {
      localSecurityException.printStackTrace();
    }
  }
  
  public void a()
  {
    try
    {
      b();
      if (jdField_a_of_type_JavaIoFile != null)
      {
        d.a(jdField_a_of_type_JavaIoFile, jdField_a_of_type_JavaLangString, jdField_a_of_type_ArrayOfByte, this.jdField_a_of_type_JavaLangStringBuffer.toString(), true);
        this.jdField_a_of_type_JavaLangStringBuffer.delete(0, this.jdField_a_of_type_JavaLangStringBuffer.length());
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public void a(String paramString)
  {
    try
    {
      String str = this.jdField_a_of_type_JavaTextSimpleDateFormat.format(Long.valueOf(System.currentTimeMillis()));
      StringBuffer localStringBuffer = this.jdField_a_of_type_JavaLangStringBuffer;
      localStringBuffer.append(str);
      localStringBuffer.append(" pid=");
      localStringBuffer.append(Process.myPid());
      localStringBuffer.append(" tid=");
      localStringBuffer.append(Process.myTid());
      localStringBuffer.append(paramString);
      localStringBuffer.append("\n");
      if ((Thread.currentThread() != Looper.getMainLooper().getThread()) || (jdField_a_of_type_Boolean)) {
        a();
      }
      if (this.jdField_a_of_type_JavaLangStringBuffer.length() > 524288)
      {
        this.jdField_a_of_type_JavaLangStringBuffer.delete(0, this.jdField_a_of_type_JavaLangStringBuffer.length());
        return;
      }
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public void a(String paramString1, String paramString2) {}
  
  public void b(String paramString)
  {
    TextView localTextView = this.jdField_a_of_type_AndroidWidgetTextView;
    if (localTextView != null) {
      localTextView.post(new a(paramString));
    }
  }
  
  public void b(String paramString1, String paramString2) {}
  
  public void c(String paramString1, String paramString2) {}
  
  public void d(String paramString1, String paramString2) {}
  
  private class a
    implements Runnable
  {
    String jdField_a_of_type_JavaLangString = null;
    
    a(String paramString)
    {
      this.jdField_a_of_type_JavaLangString = paramString;
    }
    
    public void run()
    {
      if (TbsLogClient.this.a != null)
      {
        TextView localTextView = TbsLogClient.this.a;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(this.jdField_a_of_type_JavaLangString);
        localStringBuilder.append("\n");
        localTextView.append(localStringBuilder.toString());
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\downloader\utils\TbsLogClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */