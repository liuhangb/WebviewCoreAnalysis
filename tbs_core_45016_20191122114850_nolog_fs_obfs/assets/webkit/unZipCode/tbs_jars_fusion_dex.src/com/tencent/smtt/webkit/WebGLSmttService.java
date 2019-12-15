package com.tencent.smtt.webkit;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import java.util.HashMap;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("tencent")
public class WebGLSmttService
{
  private static int jdField_a_of_type_Int = 0;
  private static Handler jdField_a_of_type_AndroidOsHandler;
  private static String jdField_a_of_type_JavaLangString;
  private static boolean jdField_a_of_type_Boolean = false;
  private static String jdField_b_of_type_JavaLangString;
  private static boolean jdField_b_of_type_Boolean = false;
  private static String c;
  private static String d;
  private static String e;
  private static String f = "";
  private static String g = "";
  
  public static void a(String paramString1, String paramString2, String paramString3)
  {
    for (;;)
    {
      int i;
      int j;
      String str;
      int m;
      int k;
      try
      {
        if (((jdField_a_of_type_Boolean) || ("WebGLCreationError".equalsIgnoreCase(paramString2))) && (paramString1 != null) && (paramString2 != null) && (paramString3 != null) && (d != null))
        {
          if (d.isEmpty()) {
            return;
          }
          if (paramString1.isEmpty()) {
            c = jdField_b_of_type_JavaLangString;
          } else {
            c = paramString1;
          }
          if ((jdField_b_of_type_JavaLangString != null) && (!jdField_b_of_type_JavaLangString.isEmpty()))
          {
            if (jdField_b_of_type_JavaLangString.equalsIgnoreCase("blank")) {
              return;
            }
            String[] arrayOfString1 = paramString3.split("\\@");
            if (arrayOfString1 == null) {
              break label447;
            }
            paramString1 = "";
            i = 0;
            j = 0;
            str = paramString1;
            if (i < arrayOfString1.length)
            {
              String[] arrayOfString2 = arrayOfString1[i].split("\\$");
              m = 0;
              str = paramString1;
              k = j;
              if (m >= arrayOfString2.length) {
                break label425;
              }
              if (!"functionName".equals(arrayOfString2[m])) {
                break label416;
              }
              str = arrayOfString2[(m + 1)];
              k = 1;
              break label425;
            }
            if ((!paramString2.equals("HasWebGLContext")) && ((jdField_a_of_type_Int > 5) || (f.equals(str))) && ((!"WebGLCreationError".equalsIgnoreCase(paramString2)) || (g.equalsIgnoreCase(jdField_b_of_type_JavaLangString)))) {
              break label415;
            }
            if (paramString2.equals("HasWebGLContext")) {
              jdField_a_of_type_Int = 0;
            }
            paramString1 = new HashMap();
            paramString1.put("webglurl", c);
            paramString1.put("gpu", jdField_a_of_type_JavaLangString);
            paramString1.put("type", paramString2);
            if (paramString3.contains("webGLEnabled"))
            {
              paramString2 = new StringBuilder();
              paramString2.append(paramString3.replace("]", ""));
              paramString2.append(" content::GpuProcessHost::gpu_enabled is ");
              paramString2.append(jdField_b_of_type_Boolean);
              paramString2.append("]");
              paramString1.put("error", paramString2.toString());
            }
            else
            {
              paramString1.put("error", paramString3);
            }
            paramString1.put("state", d);
            paramString1.put("pageurl", jdField_b_of_type_JavaLangString);
            paramString1.put("qua2", e);
            SmttServiceProxy.getInstance().upLoadToBeacon("MTT_CORE_WEBGL", paramString1);
            f = str;
            g = jdField_b_of_type_JavaLangString;
            jdField_a_of_type_Int += 1;
          }
        }
        else
        {
          return;
        }
      }
      catch (Exception paramString1)
      {
        paramString1.printStackTrace();
      }
      label415:
      return;
      label416:
      m += 1;
      continue;
      label425:
      if (k == 0)
      {
        i += 1;
        paramString1 = str;
        j = k;
        continue;
        label447:
        str = "";
      }
    }
  }
  
  @CalledByNative
  public static void initGpu(String paramString)
  {
    jdField_a_of_type_AndroidOsHandler = new Handler(ThreadUtils.getUiThreadLooper())
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        switch (paramAnonymousMessage.what)
        {
        default: 
          return;
        case 6: 
          paramAnonymousMessage = paramAnonymousMessage.getData().getStringArray("paraStringArray");
          WebGLSmttService.a(paramAnonymousMessage[0], paramAnonymousMessage[1], paramAnonymousMessage[2]);
          return;
        case 5: 
          WebGLSmttService.b(((Boolean)paramAnonymousMessage.obj).booleanValue());
          return;
        case 4: 
          WebGLSmttService.a(((Boolean)paramAnonymousMessage.obj).booleanValue());
          return;
        case 3: 
          WebGLSmttService.c((String)paramAnonymousMessage.obj);
          return;
        case 2: 
          WebGLSmttService.d((String)paramAnonymousMessage.obj);
          return;
        }
        WebGLSmttService.a((String)paramAnonymousMessage.obj);
        WebGLSmttService.b(SmttServiceProxy.getInstance().getQUA2());
        WebGLSmttService.c("");
        WebGLSmttService.a(false);
        WebGLSmttService.b(true);
      }
    };
    paramString = jdField_a_of_type_AndroidOsHandler.obtainMessage(1, paramString);
    jdField_a_of_type_AndroidOsHandler.sendMessage(paramString);
  }
  
  @CalledByNative
  public static void reportWebGL(String paramString1, String paramString2, String paramString3)
  {
    Message localMessage = jdField_a_of_type_AndroidOsHandler.obtainMessage(6);
    Bundle localBundle = new Bundle();
    localBundle.putStringArray("paraStringArray", new String[] { paramString1, paramString2, paramString3 });
    localMessage.setData(localBundle);
    jdField_a_of_type_AndroidOsHandler.sendMessage(localMessage);
  }
  
  @CalledByNative
  public static void updateEnable(boolean paramBoolean)
  {
    Message localMessage = jdField_a_of_type_AndroidOsHandler.obtainMessage(4, Boolean.valueOf(paramBoolean));
    jdField_a_of_type_AndroidOsHandler.sendMessage(localMessage);
  }
  
  @CalledByNative
  public static void updateGpuEnabled(boolean paramBoolean)
  {
    Message localMessage = jdField_a_of_type_AndroidOsHandler.obtainMessage(5, Boolean.valueOf(paramBoolean));
    jdField_a_of_type_AndroidOsHandler.sendMessage(localMessage);
  }
  
  @CalledByNative
  public static void updateState(String paramString)
  {
    paramString = jdField_a_of_type_AndroidOsHandler.obtainMessage(3, paramString);
    jdField_a_of_type_AndroidOsHandler.sendMessage(paramString);
  }
  
  @CalledByNative
  public static void updateUrl(String paramString)
  {
    paramString = jdField_a_of_type_AndroidOsHandler.obtainMessage(2, paramString);
    jdField_a_of_type_AndroidOsHandler.sendMessage(paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\WebGLSmttService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */