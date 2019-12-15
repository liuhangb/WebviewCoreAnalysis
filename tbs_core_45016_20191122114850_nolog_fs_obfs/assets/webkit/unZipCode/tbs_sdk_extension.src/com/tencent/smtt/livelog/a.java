package com.tencent.smtt.livelog;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.tencent.common.utils.FileUtils;
import com.tencent.smtt.util.MttTimingLog.c;
import com.tencent.smtt.util.X5LogUploadManager;
import com.tencent.smtt.util.X5LogUploadManager.OnUploadListener;
import com.tencent.smtt.util.X5LogUploadManager.b;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

public class a
{
  private static Handler jdField_a_of_type_AndroidOsHandler;
  private static HandlerThread jdField_a_of_type_AndroidOsHandlerThread;
  private X5LogUploadManager.OnUploadListener jdField_a_of_type_ComTencentSmttUtilX5LogUploadManager$OnUploadListener = null;
  private String jdField_a_of_type_JavaLangString = "md5";
  private boolean jdField_a_of_type_Boolean = false;
  private String jdField_b_of_type_JavaLangString = "";
  private boolean jdField_b_of_type_Boolean = false;
  
  public a()
  {
    c();
  }
  
  public static a a()
  {
    return a.a();
  }
  
  private void c()
  {
    jdField_a_of_type_AndroidOsHandlerThread = new HandlerThread("livelog-handler");
    jdField_a_of_type_AndroidOsHandlerThread.start();
    jdField_a_of_type_AndroidOsHandler = new Handler(jdField_a_of_type_AndroidOsHandlerThread.getLooper())
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        int i = paramAnonymousMessage.what;
        if (i != 5)
        {
          switch (i)
          {
          default: 
            return;
          case 3: 
            paramAnonymousMessage = (String)paramAnonymousMessage.obj;
            a.c(a.this, paramAnonymousMessage);
            return;
          case 2: 
            paramAnonymousMessage = (String)paramAnonymousMessage.obj;
            a.a(a.this, paramAnonymousMessage);
            return;
          }
          paramAnonymousMessage = (String)paramAnonymousMessage.obj;
          a.b(a.this, paramAnonymousMessage);
          return;
        }
        paramAnonymousMessage = (String)paramAnonymousMessage.obj;
        a.d(a.this, paramAnonymousMessage);
      }
    };
  }
  
  private void d(String paramString)
  {
    String str1 = LiveLog.getInstance().getLogDir();
    LiveLog.getInstance().flushSync();
    Object localObject = new File(str1);
    if (!((File)localObject).exists()) {
      return;
    }
    String[] arrayOfString = ((File)localObject).list();
    if (arrayOfString != null)
    {
      if (arrayOfString.length == 0) {
        return;
      }
      localObject = new ArrayList();
      int j = arrayOfString.length;
      int i = 0;
      while (i < j)
      {
        String str2 = arrayOfString[i];
        if (str2.contains(paramString))
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append(str1);
          localStringBuilder.append("/");
          localStringBuilder.append(str2);
          ((ArrayList)localObject).add(localStringBuilder.toString());
        }
        i += 1;
      }
      if (((ArrayList)localObject).size() == 0) {
        return;
      }
      paramString = new StringBuilder();
      paramString.append(str1);
      paramString.append("/");
      paramString.append("livelog.zip");
      paramString = paramString.toString();
      new MttTimingLog.c((String[])((ArrayList)localObject).toArray(new String[((ArrayList)localObject).size()]), paramString).a();
      paramString = jdField_a_of_type_AndroidOsHandler.obtainMessage(1, paramString);
      jdField_a_of_type_AndroidOsHandler.sendMessage(paramString);
      return;
    }
  }
  
  private void e(String paramString)
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
        String str = paramString[i];
        int k = str.indexOf("=");
        if ((k != -1) && (str.length() >= k + 3))
        {
          Object localObject = str.substring(0, k);
          str = str.substring(k + 1);
          if ((localObject != null) && ("cmd".equals(localObject)) && (str != null) && (str.length() > 0))
          {
            localObject = str.split("\\|");
            i = localObject.length;
            if ((i <= 2) || (!"upload".equals(localObject[0]))) {
              break;
            }
            paramString = localObject[1];
            localObject = localObject[(i - 1)];
            if ((paramString == null) || (paramString.length() <= 0) || (localObject == null) || (this.jdField_a_of_type_JavaLangString.equals(localObject))) {
              break;
            }
            this.jdField_a_of_type_JavaLangString = ((String)localObject);
            a(paramString);
            return;
          }
        }
        i += 1;
      }
      return;
    }
  }
  
  private void f(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.length() == 0) {
        return;
      }
      Object localObject = paramString.split("=");
      if (localObject.length != 2) {
        return;
      }
      int i = 0;
      paramString = localObject[0];
      localObject = localObject[1];
      if ((paramString.hashCode() != -838595071) || (!paramString.equals("upload"))) {
        i = -1;
      }
      if (i != 0) {
        return;
      }
      a((String)localObject);
      return;
    }
  }
  
  private void g(String paramString)
  {
    JSONObject localJSONObject = new JSONObject();
    if (!TextUtils.isEmpty(this.jdField_b_of_type_JavaLangString)) {
      try
      {
        localJSONObject.put("id", this.jdField_b_of_type_JavaLangString);
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
    }
    if (this.jdField_a_of_type_Boolean)
    {
      paramString = new X5LogUploadManager.b(paramString, "LiveLog_AISEE", "LiveLog_AISEE", localJSONObject);
      this.jdField_a_of_type_Boolean = false;
    }
    else
    {
      paramString = new X5LogUploadManager.b(paramString, "LiveLog", "LiveLog", localJSONObject);
    }
    if ((this.jdField_b_of_type_Boolean) && (this.jdField_a_of_type_ComTencentSmttUtilX5LogUploadManager$OnUploadListener != null))
    {
      X5LogUploadManager.a().a(paramString, this.jdField_a_of_type_ComTencentSmttUtilX5LogUploadManager$OnUploadListener);
      this.jdField_b_of_type_Boolean = false;
      return;
    }
    X5LogUploadManager.a().a(paramString);
  }
  
  public void a()
  {
    this.jdField_a_of_type_Boolean = true;
    b();
  }
  
  public void a(String paramString)
  {
    paramString = jdField_a_of_type_AndroidOsHandler.obtainMessage(3, paramString);
    jdField_a_of_type_AndroidOsHandler.sendMessage(paramString);
  }
  
  public void a(String paramString1, String paramString2, X5LogUploadManager.OnUploadListener paramOnUploadListener)
  {
    this.jdField_b_of_type_JavaLangString = paramString2;
    this.jdField_a_of_type_ComTencentSmttUtilX5LogUploadManager$OnUploadListener = paramOnUploadListener;
    this.jdField_b_of_type_Boolean = true;
    paramString1 = jdField_a_of_type_AndroidOsHandler.obtainMessage(3, paramString1);
    jdField_a_of_type_AndroidOsHandler.sendMessage(paramString1);
  }
  
  public void a(String paramString, byte[] paramArrayOfByte)
  {
    if (ContextHolder.getInstance().getContext() == null) {
      return;
    }
    Object localObject = new File(paramString);
    if (!((File)localObject).exists()) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(((File)localObject).getParent());
    localStringBuilder.append("/localPluin.zip");
    localObject = localStringBuilder.toString();
    new MttTimingLog.c(new String[] { paramString }, (String)localObject).a(paramString, paramArrayOfByte);
    paramString = new JSONObject();
    try
    {
      paramString.put("psw", SmttServiceProxy.getInstance().getPasswordKey());
    }
    catch (JSONException paramArrayOfByte)
    {
      paramArrayOfByte.printStackTrace();
    }
    paramString = new X5LogUploadManager.b((String)localObject, "localPluginFiles", "localPluginFiles", paramString);
    X5LogUploadManager.a().a(paramString);
  }
  
  public void a(ArrayList<String> paramArrayList)
  {
    if (paramArrayList.size() == 0) {
      return;
    }
    Object localObject1 = ContextHolder.getInstance().getContext();
    if (localObject1 == null) {
      return;
    }
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append(FileUtils.getSDcardDir((Context)localObject1));
    ((StringBuilder)localObject2).append("/Tencent/tbs_config_files/");
    ((StringBuilder)localObject2).append(((Context)localObject1).getPackageName());
    localObject1 = ((StringBuilder)localObject2).toString();
    localObject2 = new File((String)localObject1);
    if (!((File)localObject2).exists()) {
      ((File)localObject2).mkdirs();
    }
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append("/");
    ((StringBuilder)localObject2).append("localConfigFiles.zip");
    localObject1 = ((StringBuilder)localObject2).toString();
    new MttTimingLog.c((String[])paramArrayList.toArray(new String[paramArrayList.size()]), (String)localObject1).a();
    paramArrayList = new JSONObject();
    try
    {
      paramArrayList.put("psw", SmttServiceProxy.getInstance().getPasswordKey());
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    paramArrayList = new X5LogUploadManager.b((String)localObject1, "localConfigFiles", "localConfigFiles", paramArrayList);
    X5LogUploadManager.a().a(paramArrayList);
  }
  
  public void b()
  {
    a(new SimpleDateFormat("yyyyMMdd").format(new Date()));
  }
  
  public void b(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0))
    {
      paramString = jdField_a_of_type_AndroidOsHandler.obtainMessage(2, paramString);
      jdField_a_of_type_AndroidOsHandler.sendMessage(paramString);
    }
  }
  
  public void c(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0))
    {
      paramString = jdField_a_of_type_AndroidOsHandler.obtainMessage(5, paramString);
      jdField_a_of_type_AndroidOsHandler.sendMessage(paramString);
    }
  }
  
  private static class a
  {
    private static final a a = new a();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\livelog\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */