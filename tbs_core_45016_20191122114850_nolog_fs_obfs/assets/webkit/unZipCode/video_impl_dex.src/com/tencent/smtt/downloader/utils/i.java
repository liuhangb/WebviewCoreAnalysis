package com.tencent.smtt.downloader.utils;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.tencent.smtt.downloader.TbsListener;
import com.tencent.smtt.downloader.a;

public class i
{
  private static i jdField_a_of_type_ComTencentSmttDownloaderUtilsI;
  private Context jdField_a_of_type_AndroidContentContext;
  private Handler jdField_a_of_type_AndroidOsHandler = null;
  private boolean jdField_a_of_type_Boolean = false;
  
  private i(Context paramContext)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext.getApplicationContext();
    paramContext = new HandlerThread("TbsLogReportThread");
    paramContext.start();
    this.jdField_a_of_type_AndroidOsHandler = new Handler(paramContext.getLooper())
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        if (paramAnonymousMessage.what == 600)
        {
          if (!(paramAnonymousMessage.obj instanceof i.b)) {
            return;
          }
          try
          {
            i.b localb = (i.b)paramAnonymousMessage.obj;
            int i = paramAnonymousMessage.arg1;
            i.a(i.this, i, localb);
            return;
          }
          catch (Exception paramAnonymousMessage)
          {
            paramAnonymousMessage.printStackTrace();
            return;
          }
        }
        if (paramAnonymousMessage.what == 601) {
          i.a(i.this);
        }
      }
    };
  }
  
  public static i a(Context paramContext)
  {
    if (jdField_a_of_type_ComTencentSmttDownloaderUtilsI == null) {
      try
      {
        if (jdField_a_of_type_ComTencentSmttDownloaderUtilsI == null) {
          jdField_a_of_type_ComTencentSmttDownloaderUtilsI = new i(paramContext);
        }
      }
      finally {}
    }
    return jdField_a_of_type_ComTencentSmttDownloaderUtilsI;
  }
  
  private void a(int paramInt, b paramb) {}
  
  private void a(int paramInt, b paramb, a parama)
  {
    paramb.h(paramInt);
    paramb.a(System.currentTimeMillis());
    a.a.onInstallFinish(paramInt);
    a(parama, paramb);
  }
  
  private void b() {}
  
  public b a()
  {
    return new b(null);
  }
  
  public void a()
  {
    this.jdField_a_of_type_AndroidOsHandler.sendEmptyMessage(601);
  }
  
  public void a(int paramInt, String paramString)
  {
    a(paramInt, paramString, a.b);
  }
  
  public void a(int paramInt, String paramString, a parama)
  {
    if ((paramInt != 200) && (paramInt != 220) && (paramInt != 221))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("error occured in installation, errorCode:");
      ((StringBuilder)localObject).append(paramInt);
      h.a("TbsDownload", ((StringBuilder)localObject).toString(), true);
    }
    Object localObject = a();
    ((b)localObject).e(paramString);
    a(paramInt, (b)localObject, parama);
  }
  
  public void a(int paramInt, Throwable paramThrowable)
  {
    b localb = a();
    localb.a(paramThrowable);
    a(paramInt, localb, a.b);
  }
  
  public void a(a parama, b paramb)
  {
    try
    {
      paramb = (b)paramb.clone();
      Message localMessage = this.jdField_a_of_type_AndroidOsHandler.obtainMessage();
      localMessage.what = 600;
      localMessage.arg1 = parama.a;
      localMessage.obj = paramb;
      this.jdField_a_of_type_AndroidOsHandler.sendMessage(localMessage);
      return;
    }
    catch (Throwable parama)
    {
      paramb = new StringBuilder();
      paramb.append("[TbsLogReport.eventReport] error, message=");
      paramb.append(parama.getMessage());
      h.c("upload", paramb.toString());
    }
  }
  
  public static enum a
  {
    int jdField_a_of_type_Int;
    
    static
    {
      jdField_a_of_type_ComTencentSmttDownloaderUtilsI$a = new a("TYPE_DOWNLOAD", 0, 0);
      b = new a("TYPE_INSTALL", 1, 1);
      c = new a("TYPE_LOAD", 2, 2);
      d = new a("TYPE_DOWNLOAD_DECOUPLE", 3, 3);
      e = new a("TYPE_INSTALL_DECOUPLE", 4, 4);
      f = new a("TYPE_COOKIE_DB_SWITCH", 5, 5);
      g = new a("TYPE_SDK_REPORT_INFO", 6, 6);
    }
    
    private a(int paramInt)
    {
      this.jdField_a_of_type_Int = paramInt;
    }
  }
  
  public static class b
    implements Cloneable
  {
    public int a;
    private long jdField_a_of_type_Long;
    private String jdField_a_of_type_JavaLangString;
    private int jdField_b_of_type_Int;
    private long jdField_b_of_type_Long;
    private String jdField_b_of_type_JavaLangString;
    private int jdField_c_of_type_Int;
    private long jdField_c_of_type_Long;
    private String jdField_c_of_type_JavaLangString;
    private int jdField_d_of_type_Int;
    private long jdField_d_of_type_Long;
    private String jdField_d_of_type_JavaLangString;
    private int jdField_e_of_type_Int;
    private String jdField_e_of_type_JavaLangString;
    private int f;
    private int g;
    private int h;
    
    private b()
    {
      a();
    }
    
    public int a()
    {
      return this.g;
    }
    
    public void a()
    {
      this.jdField_a_of_type_Long = 0L;
      this.jdField_a_of_type_JavaLangString = null;
      this.jdField_b_of_type_JavaLangString = null;
      this.jdField_b_of_type_Int = 0;
      this.jdField_c_of_type_Int = 0;
      this.jdField_d_of_type_Int = 0;
      this.jdField_e_of_type_Int = 2;
      this.jdField_c_of_type_JavaLangString = "unknown";
      this.f = 0;
      this.g = 2;
      this.jdField_b_of_type_Long = 0L;
      this.jdField_c_of_type_Long = 0L;
      this.h = 1;
      this.jdField_a_of_type_Int = 0;
      this.jdField_d_of_type_JavaLangString = null;
      this.jdField_e_of_type_JavaLangString = null;
      this.jdField_d_of_type_Long = 0L;
    }
    
    public void a(int paramInt)
    {
      this.jdField_b_of_type_Int = paramInt;
    }
    
    public void a(long paramLong)
    {
      this.jdField_a_of_type_Long = paramLong;
    }
    
    public void a(String paramString)
    {
      if (this.jdField_a_of_type_JavaLangString == null)
      {
        this.jdField_a_of_type_JavaLangString = paramString;
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(this.jdField_a_of_type_JavaLangString);
      localStringBuilder.append(";");
      localStringBuilder.append(paramString);
      this.jdField_a_of_type_JavaLangString = localStringBuilder.toString();
    }
    
    public void a(Throwable paramThrowable)
    {
      if (paramThrowable == null)
      {
        this.jdField_e_of_type_JavaLangString = "";
        return;
      }
      String str = Log.getStackTraceString(paramThrowable);
      paramThrowable = str;
      if (str.length() > 1024) {
        paramThrowable = str.substring(0, 1024);
      }
      this.jdField_e_of_type_JavaLangString = paramThrowable;
    }
    
    public void b(int paramInt)
    {
      this.jdField_c_of_type_Int = paramInt;
    }
    
    public void b(long paramLong)
    {
      this.jdField_b_of_type_Long = paramLong;
    }
    
    public void b(String paramString)
    {
      this.jdField_b_of_type_JavaLangString = paramString;
    }
    
    public void c(int paramInt)
    {
      this.jdField_d_of_type_Int = paramInt;
    }
    
    public void c(long paramLong)
    {
      this.jdField_c_of_type_Long += paramLong;
    }
    
    public void c(String paramString)
    {
      this.jdField_c_of_type_JavaLangString = paramString;
    }
    
    protected Object clone()
    {
      try
      {
        Object localObject = super.clone();
        return localObject;
      }
      catch (CloneNotSupportedException localCloneNotSupportedException) {}
      return this;
    }
    
    public void d(int paramInt)
    {
      this.jdField_e_of_type_Int = paramInt;
    }
    
    public void d(long paramLong)
    {
      this.jdField_d_of_type_Long += paramLong;
    }
    
    public void d(String paramString)
    {
      h(108);
      this.jdField_d_of_type_JavaLangString = paramString;
    }
    
    public void e(int paramInt)
    {
      this.f = paramInt;
    }
    
    public void e(String paramString)
    {
      if (paramString == null) {
        return;
      }
      String str = paramString;
      if (paramString.length() > 1024) {
        str = paramString.substring(0, 1024);
      }
      this.jdField_e_of_type_JavaLangString = str;
    }
    
    public void f(int paramInt)
    {
      this.g = paramInt;
    }
    
    public void g(int paramInt)
    {
      this.h = paramInt;
    }
    
    public void h(int paramInt)
    {
      if ((paramInt != 100) && (paramInt != 110) && (paramInt != 120) && (paramInt != 111) && (paramInt < 400))
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("error occured, errorCode:");
        localStringBuilder.append(paramInt);
        h.a("TbsDownload", localStringBuilder.toString(), true);
      }
      if (paramInt == 111) {
        h.a("TbsDownload", "you are not in wifi, downloading stoped", true);
      }
      this.jdField_a_of_type_Int = paramInt;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\downloader\utils\i.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */