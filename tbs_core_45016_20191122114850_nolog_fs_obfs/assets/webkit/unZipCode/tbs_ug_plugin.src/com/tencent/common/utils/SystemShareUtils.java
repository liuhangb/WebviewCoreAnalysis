package com.tencent.common.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import java.io.File;
import java.util.ArrayList;

public class SystemShareUtils
{
  private static String a(String paramString)
  {
    if ("chm".equals(paramString)) {
      return "text/plain";
    }
    if ("epub".equals(paramString)) {
      return "application/epub";
    }
    return "*/*";
  }
  
  private static String a(String[] paramArrayOfString)
  {
    if ((paramArrayOfString != null) && (paramArrayOfString.length > 0))
    {
      String[] arrayOfString1 = paramArrayOfString[0].split("/");
      if (arrayOfString1[0].equals("*"))
      {
        arrayOfString1[1] = "*";
      }
      else
      {
        int i = 1;
        while (i < paramArrayOfString.length)
        {
          String[] arrayOfString2 = paramArrayOfString[i].split("/");
          if (arrayOfString1[0].equals(arrayOfString2[0]))
          {
            if ((!arrayOfString1[1].equals("*")) && (!arrayOfString1[1].equals(arrayOfString2[1]))) {
              arrayOfString1[1] = "*";
            }
            i += 1;
          }
          else
          {
            arrayOfString1[0] = "*";
            arrayOfString1[1] = "*";
          }
        }
      }
      paramArrayOfString = new StringBuilder();
      paramArrayOfString.append(arrayOfString1[0]);
      paramArrayOfString.append("/");
      paramArrayOfString.append(arrayOfString1[1]);
      return paramArrayOfString.toString();
    }
    return "*/*";
  }
  
  public static Intent createFileSendIntent(String[] paramArrayOfString)
  {
    ReflectionUtils.invokeStatic(StrictMode.class.getName(), "disableDeathOnFileUriExposure");
    Intent localIntent = new Intent();
    int j = paramArrayOfString.length;
    int i = 0;
    String str;
    Object localObject2;
    Object localObject1;
    if (j == 1)
    {
      str = FileUtilsF.getFileExt(paramArrayOfString[0]);
      localObject2 = getMimeTypeFromExtension(str, "*/*");
      localObject1 = localObject2;
      if ("*/*".equals(localObject2)) {
        localObject1 = a(str);
      }
      localIntent.setAction("android.intent.action.SEND");
      localIntent.setType((String)localObject1);
      localIntent.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(paramArrayOfString[0])));
    }
    else
    {
      localObject1 = new ArrayList(paramArrayOfString.length);
      localObject2 = new String[paramArrayOfString.length];
      while (i < paramArrayOfString.length)
      {
        ((ArrayList)localObject1).add(Uri.fromFile(new File(paramArrayOfString[i])));
        str = FileUtilsF.getFileExt(paramArrayOfString[i]);
        localObject2[i] = getMimeTypeFromExtension(str, "*/*");
        if ("*/*".equals(localObject2[i])) {
          localObject2[i] = a(str);
        }
        i += 1;
      }
      localIntent.setAction("android.intent.action.SEND_MULTIPLE");
      localIntent.setType(a((String[])localObject2));
      localIntent.putParcelableArrayListExtra("android.intent.extra.STREAM", (ArrayList)localObject1);
    }
    localIntent.addFlags(268435456);
    localIntent.addFlags(67108864);
    return localIntent;
  }
  
  public static String getMimeTypeFromExtension(String paramString1, String paramString2)
  {
    if (!TextUtils.isEmpty(paramString1))
    {
      String str = MimeTypeMap.getSingleton().getMimeTypeFromExtension(paramString1);
      paramString1 = str;
      if (!TextUtils.isEmpty(str)) {}
    }
    else
    {
      paramString1 = paramString2;
    }
    return paramString1;
  }
  
  public static void sendFilesUsingLocalApps(Context paramContext, String[] paramArrayOfString)
  {
    paramContext.startActivity(createFileSendIntent(paramArrayOfString));
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\SystemShareUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */