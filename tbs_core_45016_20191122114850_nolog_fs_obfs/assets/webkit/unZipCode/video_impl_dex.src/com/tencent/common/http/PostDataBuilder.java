package com.tencent.common.http;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import com.tencent.common.utils.ContentTypeUtils;

public class PostDataBuilder
{
  public static IPostDataBuf create()
  {
    return new a();
  }
  
  public static String getUriFileName(Context paramContext, String paramString)
  {
    if ((paramContext != null) && (paramString != null))
    {
      String str = Uri.decode(paramString);
      if (str.toLowerCase().startsWith("content:"))
      {
        paramString = null;
        try
        {
          paramContext = paramContext.getContentResolver().getType(Uri.parse(str));
        }
        catch (Exception paramContext)
        {
          paramContext.printStackTrace();
          paramContext = paramString;
        }
        if (paramContext == null) {
          return "";
        }
        paramString = new StringBuilder();
        paramString.append(str.substring(str.lastIndexOf("/") + 1));
        paramString.append(".");
        paramString.append(paramContext.substring(paramContext.lastIndexOf("/") + 1));
        return paramString.toString();
      }
      if (str.toLowerCase().startsWith("file:")) {
        return str.substring(str.lastIndexOf("/") + 1);
      }
    }
    return "";
  }
  
  public static String getUriType(Context paramContext, String paramString)
  {
    if ((paramContext != null) && (paramString != null)) {
      if (paramString.toLowerCase().startsWith("content:"))
      {
        try
        {
          paramContext = paramContext.getContentResolver().getType(Uri.parse(paramString));
          return paramContext;
        }
        catch (Exception paramContext)
        {
          paramContext.printStackTrace();
        }
      }
      else if (paramString.toLowerCase().startsWith("file:"))
      {
        paramContext = ContentTypeUtils.getContentType(paramString);
        paramString = new StringBuilder();
        paramString.append(paramContext.mType);
        paramString.append("/");
        paramString.append(paramContext.mTypeValue);
        return paramString.toString();
      }
    }
    return "application/octet-stream";
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\http\PostDataBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */