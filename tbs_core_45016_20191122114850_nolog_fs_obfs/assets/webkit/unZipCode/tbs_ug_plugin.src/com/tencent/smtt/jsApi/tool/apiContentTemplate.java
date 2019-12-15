package com.tencent.smtt.jsApi.tool;

import android.text.TextUtils;
import com.tencent.common.utils.FileUtils;
import java.io.File;

public class apiContentTemplate
{
  private static String a;
  private static String b = "jsApiDexFile.java";
  private static String c = "../impl";
  private static String d = "package com.tencent.smtt.jsApi.tool;\r\npublic class jsApiDexFile { \r\nprivate static byte[] apiDexContents = ";
  private static String e = "}\r\n";
  
  public static void toLocal()
  {
    if (!TextUtils.isEmpty(a))
    {
      File localFile = new File(c, b);
      if (localFile.exists()) {
        localFile.delete();
      }
      try
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(d);
        localStringBuilder.append(a);
        localStringBuilder.append(e);
        FileUtils.save(localFile, localStringBuilder.toString().getBytes("UTF-8"));
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }
  
  public static void updateApiDexContents(String paramString)
  {
    a = paramString;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\tool\apiContentTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */