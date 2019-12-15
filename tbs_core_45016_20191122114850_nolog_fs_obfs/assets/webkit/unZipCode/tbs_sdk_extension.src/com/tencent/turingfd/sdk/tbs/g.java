package com.tencent.turingfd.sdk.tbs;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Proxy;
import java.net.HttpURLConnection;

public class g
{
  public static String a = "10.0.0.172";
  public a a;
  public HttpURLConnection a;
  public boolean a;
  
  public g(Context paramContext)
  {
    Object localObject1 = null;
    this.jdField_a_of_type_JavaNetHttpURLConnection = null;
    this.jdField_a_of_type_Boolean = false;
    this.jdField_a_of_type_ComTencentTuringfdSdkTbsG$a = a.d;
    Object localObject2 = (ConnectivityManager)paramContext.getSystemService("connectivity");
    try
    {
      localObject2 = ((ConnectivityManager)localObject2).getActiveNetworkInfo();
      localObject1 = localObject2;
    }
    catch (Throwable localThrowable)
    {
      for (;;) {}
    }
    if ((localObject1 != null) && ((((NetworkInfo)localObject1).getState() == NetworkInfo.State.CONNECTING) || (((NetworkInfo)localObject1).getState() == NetworkInfo.State.CONNECTED)))
    {
      if (((NetworkInfo)localObject1).getType() == 1) {
        paramContext = a.jdField_a_of_type_ComTencentTuringfdSdkTbsG$a;
      } else if (((NetworkInfo)localObject1).getType() == 0)
      {
        if ((Proxy.getDefaultHost() == null) && (Proxy.getHost(paramContext) == null))
        {
          paramContext = a.c;
        }
        else
        {
          jdField_a_of_type_JavaLangString = Proxy.getDefaultHost();
          paramContext = a.b;
        }
      }
      else {
        paramContext = a.d;
      }
    }
    else {
      paramContext = a.d;
    }
    this.jdField_a_of_type_ComTencentTuringfdSdkTbsG$a = paramContext;
  }
  
  public static enum a
  {
    static
    {
      a locala = a;
      locala = b;
      locala = c;
      locala = d;
    }
    
    public a() {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\turingfd\sdk\tbs\g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */