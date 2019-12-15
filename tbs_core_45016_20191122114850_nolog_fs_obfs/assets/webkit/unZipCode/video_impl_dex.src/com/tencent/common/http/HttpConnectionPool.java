package com.tencent.common.http;

import android.os.Build.VERSION;
import com.tencent.basesupport.FLogger;
import com.tencent.common.utils.ReflectionUtils;
import java.lang.reflect.Constructor;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HttpsURLConnection;

public class HttpConnectionPool
{
  public static final long CONNECTION_KEEPALIVE_SECONDS = 45L;
  public static final int CONNECTION_POOL_SIZE = 4;
  private Object a = null;
  
  public HttpConnectionPool()
  {
    this.a = a(4, 45L, TimeUnit.SECONDS);
  }
  
  public HttpConnectionPool(int paramInt, long paramLong, TimeUnit paramTimeUnit)
  {
    this.a = a(paramInt, paramLong, paramTimeUnit);
  }
  
  private Object a(int paramInt, long paramLong, TimeUnit paramTimeUnit)
  {
    if (Build.VERSION.SDK_INT < 19)
    {
      FLogger.d("HttpConnectionPool", "it is below 4.4, do not enable");
      return null;
    }
    try
    {
      Class localClass = Class.forName("com.android.okhttp.ConnectionPool");
      Constructor[] arrayOfConstructor = localClass.getConstructors();
      if ((arrayOfConstructor != null) && (arrayOfConstructor.length != 0))
      {
        int k = arrayOfConstructor.length;
        int i = 0;
        while (i < k)
        {
          Object localObject1 = arrayOfConstructor[i];
          if (localObject1 != null)
          {
            Object localObject3 = ((Constructor)localObject1).getParameterTypes();
            if (localObject3 != null)
            {
              if (localObject3.length == 0) {
                try
                {
                  localObject1 = localClass.newInstance();
                  if ((ReflectionUtils.setInstanceField(localObject1, "maxIdleConnections", Integer.valueOf(paramInt))) && (ReflectionUtils.setInstanceField(localObject1, "keepAliveDurationNs", Long.valueOf(paramTimeUnit.toNanos(paramLong))))) {
                    j = 1;
                  } else {
                    j = 0;
                  }
                  if (j == 0) {
                    break label335;
                  }
                  FLogger.d("HttpConnectionPool", "create connection pool succ!!!");
                  return localObject1;
                }
                catch (Throwable localThrowable1)
                {
                  localThrowable1.printStackTrace();
                }
              }
              ArrayList localArrayList = new ArrayList();
              int m = localObject3.length;
              int j = 0;
              while (j < m)
              {
                Object localObject4 = localObject3[j];
                if (localObject4 == Integer.TYPE) {
                  localArrayList.add(Integer.valueOf(paramInt));
                } else if (localObject4 == Long.TYPE)
                {
                  if (localObject3.length == 2) {
                    localArrayList.add(Long.valueOf(paramTimeUnit.toMillis(paramLong)));
                  } else {
                    localArrayList.add(Long.valueOf(paramLong));
                  }
                }
                else if (localObject4 == TimeUnit.class) {
                  localArrayList.add(paramTimeUnit);
                }
                j += 1;
              }
              localObject3 = localArrayList.toArray();
              try
              {
                Object localObject2 = localThrowable1.newInstance((Object[])localObject3);
                FLogger.d("HttpConnectionPool", "create connection pool succ!!!");
                return localObject2;
              }
              catch (Throwable localThrowable2)
              {
                localThrowable2.printStackTrace();
              }
            }
          }
          label335:
          i += 1;
        }
        return null;
      }
      FLogger.d("HttpConnectionPool", "No constructor valid for ConnectionPool");
      return null;
    }
    catch (Throwable paramTimeUnit)
    {
      paramTimeUnit.printStackTrace();
      FLogger.d("HttpConnectionPool", "Fail to find com.android.okhttp.ConnectionPool");
    }
    return null;
  }
  
  public boolean attachConnection(HttpURLConnection paramHttpURLConnection)
  {
    if ((this.a != null) && (paramHttpURLConnection != null))
    {
      Object localObject2;
      if ((paramHttpURLConnection instanceof HttpsURLConnection))
      {
        localObject2 = ReflectionUtils.getInstanceField(paramHttpURLConnection, "delegate");
        localObject1 = localObject2;
        if (localObject2 == null)
        {
          FLogger.d("HttpConnectionPool", "Fail to get https deletgate");
          return false;
        }
      }
      else
      {
        localObject1 = paramHttpURLConnection;
      }
      Object localObject1 = ReflectionUtils.getInstanceField(localObject1, "client");
      if (localObject1 == null)
      {
        FLogger.d("HttpConnectionPool", "Fail to get okhttp client");
        return false;
      }
      try
      {
        localObject2 = Class.forName("com.android.okhttp.ConnectionPool");
        Object localObject3 = this.a;
        localObject1 = ReflectionUtils.invokeInstance(localObject1, "setConnectionPool", new Class[] { localObject2 }, new Object[] { localObject3 });
        if (localObject1 == null)
        {
          FLogger.d("HttpConnectionPool", "Fail to invoke Builder.connectionPool");
          return false;
        }
        boolean bool = ReflectionUtils.setInstanceField(paramHttpURLConnection, "client", localObject1);
        paramHttpURLConnection = new StringBuilder();
        paramHttpURLConnection.append("ATTATCH CONNECTION COMPLETE: result=");
        paramHttpURLConnection.append(bool);
        paramHttpURLConnection.append(", current pool size=");
        paramHttpURLConnection.append(getConnectionCount());
        FLogger.d("HttpConnectionPool", paramHttpURLConnection.toString());
        return bool;
      }
      catch (Throwable paramHttpURLConnection)
      {
        paramHttpURLConnection.printStackTrace();
        FLogger.d("HttpConnectionPool", "Fail to find com.android.okhttp.ConnectionPool");
        return false;
      }
    }
    FLogger.d("HttpConnectionPool", "Fail to create ConnectionPool");
    return false;
  }
  
  public void evictAll()
  {
    Object localObject = this.a;
    if (localObject == null)
    {
      FLogger.d("HttpConnectionPool", "evictAll: connectionPool is null");
      return;
    }
    ReflectionUtils.invokeInstance(localObject, " evictAll", null, new Object[0]);
  }
  
  public int getConnectionCount()
  {
    Object localObject = this.a;
    if (localObject == null) {
      return 0;
    }
    localObject = ReflectionUtils.invokeInstance(localObject, "getConnectionCount");
    if ((localObject instanceof Integer)) {
      return ((Integer)localObject).intValue();
    }
    return 0;
  }
  
  public boolean isValid()
  {
    return this.a != null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\http\HttpConnectionPool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */