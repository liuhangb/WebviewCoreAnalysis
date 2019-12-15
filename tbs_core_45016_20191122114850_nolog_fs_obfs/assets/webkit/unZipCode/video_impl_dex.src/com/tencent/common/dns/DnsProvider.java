package com.tencent.common.dns;

import android.text.TextUtils;
import com.tencent.common.manifest.AppManifest;
import java.net.Inet6Address;
import java.net.InetAddress;

public class DnsProvider
{
  private static IDnsResolver a;
  
  public static String resolveIPAddress(String paramString)
  {
    localObject2 = (IDnsResolver)AppManifest.getInstance().queryService(IDnsResolver.class);
    Object localObject1 = localObject2;
    if (localObject2 == null) {
      localObject1 = a;
    }
    if (localObject1 != null) {
      return ((IDnsResolver)localObject1).resolveDomain(paramString);
    }
    localObject1 = null;
    localObject2 = null;
    try
    {
      InetAddress[] arrayOfInetAddress = InetAddress.getAllByName(paramString);
      int i = 0;
      for (paramString = (String)localObject2;; paramString = (String)localObject1)
      {
        localObject1 = paramString;
        localObject2 = paramString;
        if (i >= arrayOfInetAddress.length) {
          break;
        }
        localObject2 = arrayOfInetAddress[i];
        localObject1 = paramString;
        if (localObject2 != null)
        {
          localObject1 = paramString;
          if ((localObject2 instanceof Inet6Address))
          {
            localObject1 = paramString;
          }
          else
          {
            localObject1 = paramString;
            paramString = ((InetAddress)localObject2).getHostAddress();
            localObject1 = paramString;
            boolean bool = TextUtils.isEmpty(paramString);
            localObject1 = paramString;
            if (!bool) {
              return paramString;
            }
          }
        }
        i += 1;
      }
      return (String)localObject2;
    }
    catch (Throwable paramString)
    {
      paramString.printStackTrace();
      localObject2 = localObject1;
    }
  }
  
  public static void setDnsResolver(IDnsResolver paramIDnsResolver)
  {
    a = paramIDnsResolver;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\dns\DnsProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */