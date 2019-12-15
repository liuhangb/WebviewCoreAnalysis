package com.tencent.smtt.c;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.tencent.smtt.util.MttLog;
import com.tencent.smtt.util.MttTimingLog;
import com.tencent.smtt.util.X5LogUploadManager;
import com.tencent.smtt.util.X5LogUploadManager.b;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

public class a
{
  private static Handler jdField_a_of_type_AndroidOsHandler;
  private static HandlerThread jdField_a_of_type_AndroidOsHandlerThread;
  private String jdField_a_of_type_JavaLangString = "md5";
  private boolean jdField_a_of_type_Boolean = false;
  
  public a()
  {
    c();
  }
  
  public static a a()
  {
    return a.a();
  }
  
  private void b(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.length() == 0) {
        return;
      }
      paramString = paramString.split(";");
      int j = paramString.length;
      int i = 0;
      while (i < j)
      {
        String str2 = paramString[i];
        int k = str2.indexOf("=");
        if ((k != -1) && (str2.length() >= k + 3))
        {
          String str1 = str2.substring(0, k);
          str2 = str2.substring(k + 1);
          if ((str1 != null) && ("cmd".equals(str1)) && (str2 != null) && (str2.length() > 0))
          {
            paramString = str2.split("\\|");
            i = paramString.length;
            if ((i <= 1) || (!"dump_upload".equals(paramString[0]))) {
              break;
            }
            paramString = paramString[(i - 1)];
            if ((paramString == null) || (this.jdField_a_of_type_JavaLangString.equals(paramString))) {
              break;
            }
            this.jdField_a_of_type_JavaLangString = paramString;
            b();
            return;
          }
        }
        i += 1;
      }
      return;
    }
  }
  
  private void c()
  {
    jdField_a_of_type_AndroidOsHandlerThread = new HandlerThread("stuckdump-handler");
    jdField_a_of_type_AndroidOsHandlerThread.start();
    jdField_a_of_type_AndroidOsHandler = new Handler(jdField_a_of_type_AndroidOsHandlerThread.getLooper())
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        switch (paramAnonymousMessage.what)
        {
        default: 
          return;
        case 2: 
          paramAnonymousMessage = (String)paramAnonymousMessage.obj;
          a.a(a.this, paramAnonymousMessage);
          return;
        }
        paramAnonymousMessage = (String)paramAnonymousMessage.obj;
        a.b(a.this, paramAnonymousMessage);
      }
    };
  }
  
  private void c(String paramString)
  {
    Object localObject = new JSONObject();
    try
    {
      ((JSONObject)localObject).put("psw", SmttServiceProxy.getInstance().getPasswordKey());
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    if (this.jdField_a_of_type_Boolean)
    {
      localObject = new X5LogUploadManager.b(paramString, "StuckDump_AISEE", "StuckDump_AISEE", (JSONObject)localObject);
      this.jdField_a_of_type_Boolean = false;
    }
    else
    {
      localObject = new X5LogUploadManager.b(paramString, "StuckDump", "StuckDump", (JSONObject)localObject);
    }
    X5LogUploadManager.a().a((X5LogUploadManager.b)localObject);
    MttTimingLog.uploadDump(paramString);
  }
  
  public void a()
  {
    this.jdField_a_of_type_Boolean = true;
    b();
  }
  
  public void a(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0))
    {
      paramString = jdField_a_of_type_AndroidOsHandler.obtainMessage(2, paramString);
      jdField_a_of_type_AndroidOsHandler.sendMessage(paramString);
    }
  }
  
  public void b()
  {
    Object localObject = MttLog.getLocalDumpDirTbs();
    if (localObject == null) {
      return;
    }
    if (((String)localObject).endsWith("/.dump")) {
      localObject = ((String)localObject).replaceFirst(".dump", "dump.zip");
    } else {
      localObject = ((String)localObject).concat("/dump.zip");
    }
    try
    {
      File localFile = new File((String)localObject);
      if ((localFile.exists()) && (localFile.length() != 0L))
      {
        boolean bool = localFile.isFile();
        if (!bool) {
          return;
        }
        localObject = jdField_a_of_type_AndroidOsHandler.obtainMessage(1, localObject);
        jdField_a_of_type_AndroidOsHandler.sendMessage((Message)localObject);
        return;
      }
      return;
    }
    catch (Exception localException) {}
  }
  
  private static class a
  {
    private static final a a = new a();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\c\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */