package com.tencent.tbs.common.utils;

import android.text.TextUtils;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.baseinfo.TbsDomainListDataProvider;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class URLUtil
{
  public static boolean isHttpUrl(String paramString)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (paramString != null)
    {
      bool1 = bool2;
      if (paramString.length() > 6)
      {
        bool1 = bool2;
        if (paramString.substring(0, 7).equalsIgnoreCase("http://")) {
          bool1 = true;
        }
      }
    }
    return bool1;
  }
  
  public static boolean isHttpsUrl(String paramString)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (paramString != null)
    {
      bool1 = bool2;
      if (paramString.length() > 7)
      {
        bool1 = bool2;
        if (paramString.substring(0, 8).equalsIgnoreCase("https://")) {
          bool1 = true;
        }
      }
    }
    return bool1;
  }
  
  public static boolean isUrlMatchDomainList(String paramString, int paramInt, boolean paramBoolean)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    for (;;)
    {
      Object localObject1;
      try
      {
        paramString = new URL(paramString);
        String str = paramString.getHost();
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append(paramString.getHost());
        ((StringBuilder)localObject1).append(paramString.getPath());
        localObject1 = ((StringBuilder)localObject1).toString();
        if (((String)localObject1).lastIndexOf('/') != -1)
        {
          paramString = ((String)localObject1).substring(0, ((String)localObject1).lastIndexOf('/'));
          Object localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("startDownload fullPathName:");
          ((StringBuilder)localObject2).append((String)localObject1);
          ((StringBuilder)localObject2).append(" fullPath:");
          ((StringBuilder)localObject2).append(paramString);
          ((StringBuilder)localObject2).toString();
          if (TextUtils.isEmpty(paramString)) {
            return false;
          }
          localObject2 = localObject1;
          if (!paramString.equals(str)) {
            if (paramBoolean) {
              localObject2 = localObject1;
            } else {
              localObject2 = paramString;
            }
          }
          paramString = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(paramInt);
          if ((paramString != null) && (paramString.size() > 0))
          {
            paramString = paramString.iterator();
            if (paramString.hasNext())
            {
              if (!((String)localObject2).startsWith((String)paramString.next())) {
                continue;
              }
              paramString = new StringBuilder();
              paramString.append("startDownload march while domain list:");
              paramString.append((String)localObject2);
              paramString.toString();
              return true;
            }
          }
          return false;
        }
      }
      catch (Exception paramString)
      {
        paramString.printStackTrace();
        return false;
      }
      paramString = (String)localObject1;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\utils\URLUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */