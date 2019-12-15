package com.tencent.common.download;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.tencent.basesupport.FLogger;
import com.tencent.common.utils.IntentUtilsF;
import com.tencent.common.utils.ThreadUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

public class DownloadServiceProxy
{
  public static final String ACTION_DOWNLOAD = "com.tencent.mtt.ACTION_DOWNLOAD";
  private static DownloadServiceProxy jdField_a_of_type_ComTencentCommonDownloadDownloadServiceProxy;
  private Context jdField_a_of_type_AndroidContentContext = null;
  private a jdField_a_of_type_ComTencentCommonDownloadDownloadServiceProxy$a = null;
  private DownloadTaskObserver jdField_a_of_type_ComTencentCommonDownloadDownloadTaskObserver;
  IDownloadService jdField_a_of_type_ComTencentCommonDownloadIDownloadService = null;
  private Object jdField_a_of_type_JavaLangObject = new byte[0];
  private ArrayList<b> jdField_a_of_type_JavaUtilArrayList = new ArrayList();
  ReentrantLock jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock = new ReentrantLock();
  protected final Object mBindServiceLock = new Object();
  
  private DownloadServiceProxy(Context paramContext)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext.getApplicationContext();
  }
  
  private void a(Context paramContext)
  {
    if (this.jdField_a_of_type_ComTencentCommonDownloadIDownloadService != null) {
      return;
    }
    this.jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock.lock();
    try
    {
      paramContext = new Intent("com.tencent.mtt.ACTION_DOWNLOAD");
      this.jdField_a_of_type_ComTencentCommonDownloadDownloadServiceProxy$a = new a();
      IntentUtilsF.fillPackageName(paramContext);
      this.jdField_a_of_type_AndroidContentContext.startService(paramContext);
      this.jdField_a_of_type_AndroidContentContext.bindService(paramContext, this.jdField_a_of_type_ComTencentCommonDownloadDownloadServiceProxy$a, 1);
      FLogger.d("tester", "bind done");
    }
    catch (SecurityException paramContext)
    {
      paramContext.printStackTrace();
    }
    this.jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock.unlock();
  }
  
  public static DownloadServiceProxy getInstance(Context paramContext)
  {
    if (jdField_a_of_type_ComTencentCommonDownloadDownloadServiceProxy == null) {
      jdField_a_of_type_ComTencentCommonDownloadDownloadServiceProxy = new DownloadServiceProxy(paramContext);
    }
    return jdField_a_of_type_ComTencentCommonDownloadDownloadServiceProxy;
  }
  
  void a()
  {
    new Thread()
    {
      public void run()
      {
        try
        {
          synchronized (DownloadServiceProxy.a(DownloadServiceProxy.this))
          {
            if (DownloadServiceProxy.a(DownloadServiceProxy.this) != null)
            {
              DownloadServiceProxy.this.a.addTaskObserver(DownloadServiceProxy.a(DownloadServiceProxy.this));
              DownloadServiceProxy.a(DownloadServiceProxy.this, null);
            }
            Iterator localIterator = DownloadServiceProxy.a(DownloadServiceProxy.this).iterator();
            while (localIterator.hasNext())
            {
              DownloadServiceProxy.b localb = (DownloadServiceProxy.b)localIterator.next();
              DownloadServiceProxy.this.a.startDownloadTaskWithExtFlag(localb.jdField_a_of_type_JavaLangString, localb.jdField_b_of_type_JavaLangString, localb.c, localb.d, localb.e, localb.jdField_a_of_type_Int, localb.jdField_b_of_type_Long, localb.jdField_a_of_type_Boolean, localb.jdField_a_of_type_Long);
            }
            DownloadServiceProxy.a(DownloadServiceProxy.this).clear();
          }
        }
        catch (NullPointerException localNullPointerException)
        {
          localNullPointerException.printStackTrace();
        }
        catch (RemoteException localRemoteException)
        {
          localRemoteException.printStackTrace();
        }
      }
    }.start();
  }
  
  public void addTaskObserver(DownloadTaskObserver paramDownloadTaskObserver)
  {
    try
    {
      synchronized (this.mBindServiceLock)
      {
        if (this.jdField_a_of_type_ComTencentCommonDownloadIDownloadService == null)
        {
          a(this.jdField_a_of_type_AndroidContentContext);
          this.mBindServiceLock.wait(1000L);
        }
      }
    }
    catch (Throwable localThrowable)
    {
      for (;;) {}
    }
    ??? = this.jdField_a_of_type_ComTencentCommonDownloadIDownloadService;
    if (??? == null)
    {
      this.jdField_a_of_type_ComTencentCommonDownloadDownloadTaskObserver = paramDownloadTaskObserver;
      return;
    }
    if (??? != null) {
      try
      {
        ((IDownloadService)???).addTaskObserver(paramDownloadTaskObserver);
        return;
      }
      catch (RemoteException paramDownloadTaskObserver)
      {
        paramDownloadTaskObserver.printStackTrace();
      }
    }
  }
  
  public void excuteFlowCtrlTasks(byte paramByte)
  {
    FLogger.d("ZHUPGRADE", "DownloadServiceProxy excuteDelayTasks begin.");
    try
    {
      synchronized (this.mBindServiceLock)
      {
        FLogger.d("ZHUPGRADE", "DownloadServiceProxy excuteDelayTasks bind service.");
        if (this.jdField_a_of_type_ComTencentCommonDownloadIDownloadService == null)
        {
          FLogger.d("ZHUPGRADE", "DownloadServiceProxy excuteDelayTasks service is null.");
          a(this.jdField_a_of_type_AndroidContentContext);
          this.mBindServiceLock.wait(1000L);
        }
      }
    }
    catch (Throwable localThrowable)
    {
      for (;;) {}
    }
    FLogger.d("ZHUPGRADE", "DownloadServiceProxy excuteDelayTasks exception.");
    if (this.jdField_a_of_type_ComTencentCommonDownloadIDownloadService != null) {}
    try
    {
      FLogger.d("ZHUPGRADE", "DownloadServiceProxy begin remote call.");
      this.jdField_a_of_type_ComTencentCommonDownloadIDownloadService.excuteFlowCtrlTasks(paramByte);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      for (;;) {}
    }
    FLogger.d("ZHUPGRADE", "DownloadServiceProxy begin remote call service exception.");
    return;
    FLogger.d("ZHUPGRADE", "DownloadServiceProxy begin remote call service is null.");
  }
  
  public String getDownloadTaskFileName(String paramString)
  {
    try
    {
      synchronized (this.mBindServiceLock)
      {
        if (this.jdField_a_of_type_ComTencentCommonDownloadIDownloadService == null)
        {
          a(this.jdField_a_of_type_AndroidContentContext);
          this.mBindServiceLock.wait(1000L);
        }
      }
    }
    catch (Throwable localThrowable)
    {
      for (;;) {}
    }
    ??? = this.jdField_a_of_type_ComTencentCommonDownloadIDownloadService;
    if (??? == null) {
      return null;
    }
    try
    {
      paramString = ((IDownloadService)???).getDownloadTaskFileName(paramString);
      return paramString;
    }
    catch (RemoteException paramString)
    {
      paramString.printStackTrace();
      return null;
    }
  }
  
  public String getDownloadTaskPath(String paramString)
  {
    try
    {
      synchronized (this.mBindServiceLock)
      {
        if (this.jdField_a_of_type_ComTencentCommonDownloadIDownloadService == null)
        {
          a(this.jdField_a_of_type_AndroidContentContext);
          this.mBindServiceLock.wait(1000L);
        }
      }
    }
    catch (Throwable localThrowable)
    {
      for (;;) {}
    }
    ??? = this.jdField_a_of_type_ComTencentCommonDownloadIDownloadService;
    if (??? == null) {
      return null;
    }
    try
    {
      paramString = ((IDownloadService)???).getDownloadTaskPath(paramString);
      return paramString;
    }
    catch (RemoteException paramString)
    {
      paramString.printStackTrace();
      return null;
    }
  }
  
  public DownloadTaskInfo getTaskFromDatabase(int paramInt)
  {
    try
    {
      synchronized (this.mBindServiceLock)
      {
        if (this.jdField_a_of_type_ComTencentCommonDownloadIDownloadService == null)
        {
          a(this.jdField_a_of_type_AndroidContentContext);
          this.mBindServiceLock.wait(1000L);
        }
      }
    }
    catch (Throwable localThrowable)
    {
      for (;;) {}
    }
    ??? = this.jdField_a_of_type_ComTencentCommonDownloadIDownloadService;
    if (??? == null) {
      return null;
    }
    if (??? != null) {
      try
      {
        ??? = ((IDownloadService)???).getTaskFromDatabaseById(paramInt);
        return (DownloadTaskInfo)???;
      }
      catch (RemoteException localRemoteException)
      {
        localRemoteException.printStackTrace();
      }
    }
    return null;
  }
  
  public DownloadTaskInfo getTaskFromDatabase(String paramString)
  {
    try
    {
      synchronized (this.mBindServiceLock)
      {
        if (this.jdField_a_of_type_ComTencentCommonDownloadIDownloadService == null)
        {
          a(this.jdField_a_of_type_AndroidContentContext);
          this.mBindServiceLock.wait(1000L);
        }
      }
    }
    catch (Throwable localThrowable)
    {
      for (;;) {}
    }
    ??? = this.jdField_a_of_type_ComTencentCommonDownloadIDownloadService;
    if (??? == null) {
      return null;
    }
    if (??? != null) {
      try
      {
        paramString = ((IDownloadService)???).getTaskFromDatabaseByUrl(paramString);
        return paramString;
      }
      catch (RemoteException paramString)
      {
        paramString.printStackTrace();
      }
    }
    return null;
  }
  
  public boolean isCompletedTask(String paramString)
  {
    try
    {
      synchronized (this.mBindServiceLock)
      {
        if (this.jdField_a_of_type_ComTencentCommonDownloadIDownloadService == null)
        {
          a(this.jdField_a_of_type_AndroidContentContext);
          this.mBindServiceLock.wait(1000L);
        }
      }
    }
    catch (Throwable localThrowable)
    {
      for (;;) {}
    }
    ??? = this.jdField_a_of_type_ComTencentCommonDownloadIDownloadService;
    if (??? == null) {
      return false;
    }
    try
    {
      boolean bool = ((IDownloadService)???).isCompletedTask(paramString);
      return bool;
    }
    catch (RemoteException paramString)
    {
      paramString.printStackTrace();
      return false;
    }
  }
  
  public boolean isOnGoing(String paramString)
  {
    try
    {
      synchronized (this.mBindServiceLock)
      {
        if (this.jdField_a_of_type_ComTencentCommonDownloadIDownloadService == null)
        {
          a(this.jdField_a_of_type_AndroidContentContext);
          this.mBindServiceLock.wait(1000L);
        }
      }
    }
    catch (Throwable localThrowable)
    {
      for (;;) {}
    }
    ??? = this.jdField_a_of_type_ComTencentCommonDownloadIDownloadService;
    if (??? == null) {
      return false;
    }
    try
    {
      boolean bool = ((IDownloadService)???).isOnGoing(paramString);
      return bool;
    }
    catch (RemoteException paramString)
    {
      paramString.printStackTrace();
      return false;
    }
  }
  
  public void removeTaskObserver(DownloadTaskObserver paramDownloadTaskObserver)
  {
    try
    {
      synchronized (this.mBindServiceLock)
      {
        if (this.jdField_a_of_type_ComTencentCommonDownloadIDownloadService == null)
        {
          a(this.jdField_a_of_type_AndroidContentContext);
          this.mBindServiceLock.wait(1000L);
        }
      }
    }
    catch (Throwable localThrowable)
    {
      for (;;) {}
    }
    ??? = this.jdField_a_of_type_ComTencentCommonDownloadIDownloadService;
    if (??? == null) {
      return;
    }
    if (??? != null) {
      try
      {
        ((IDownloadService)???).removeTaskObserver(paramDownloadTaskObserver);
        return;
      }
      catch (RemoteException paramDownloadTaskObserver)
      {
        paramDownloadTaskObserver.printStackTrace();
      }
    }
  }
  
  public void setDownloadService(IDownloadService arg1)
  {
    this.jdField_a_of_type_ComTencentCommonDownloadIDownloadService = ???;
    synchronized (this.mBindServiceLock)
    {
      this.mBindServiceLock.notifyAll();
      return;
    }
  }
  
  public boolean startDownloadTaskWithExtFlag(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt, long paramLong1, boolean paramBoolean, long paramLong2)
  {
    ??? = this.jdField_a_of_type_ComTencentCommonDownloadIDownloadService;
    if (??? == null) {
      synchronized (this.jdField_a_of_type_JavaLangObject)
      {
        b localb = new b(null);
        localb.jdField_a_of_type_JavaLangString = paramString1;
        localb.jdField_b_of_type_JavaLangString = paramString2;
        localb.c = paramString3;
        localb.d = paramString4;
        localb.e = paramString5;
        localb.jdField_a_of_type_Int = paramInt;
        localb.jdField_b_of_type_Long = paramLong1;
        localb.jdField_a_of_type_Boolean = paramBoolean;
        localb.jdField_a_of_type_Long = paramLong2;
        if (!this.jdField_a_of_type_JavaUtilArrayList.contains(localb)) {
          this.jdField_a_of_type_JavaUtilArrayList.add(localb);
        }
        if (!ThreadUtils.isMainProcess(this.jdField_a_of_type_AndroidContentContext)) {
          a(this.jdField_a_of_type_AndroidContentContext);
        }
        return true;
      }
    }
    try
    {
      paramBoolean = ((IDownloadService)???).startDownloadTaskWithExtFlag(paramString1, paramString2, paramString3, paramString4, paramString5, paramInt, paramLong1, paramBoolean, paramLong2);
      return paramBoolean;
    }
    catch (RemoteException paramString1)
    {
      paramString1.printStackTrace();
    }
    return false;
  }
  
  public void startService()
  {
    Intent localIntent = new Intent("com.tencent.mtt.ACTION_DOWNLOAD");
    IntentUtilsF.fillPackageName(localIntent);
    this.jdField_a_of_type_ComTencentCommonDownloadDownloadServiceProxy$a = new a();
    this.jdField_a_of_type_AndroidContentContext.startService(localIntent);
    this.jdField_a_of_type_AndroidContentContext.bindService(localIntent, this.jdField_a_of_type_ComTencentCommonDownloadDownloadServiceProxy$a, 1);
  }
  
  public boolean startServiceSync(long paramLong)
  {
    try
    {
      synchronized (this.mBindServiceLock)
      {
        if (this.jdField_a_of_type_ComTencentCommonDownloadIDownloadService == null)
        {
          a(this.jdField_a_of_type_AndroidContentContext);
          this.mBindServiceLock.wait(paramLong);
        }
      }
    }
    catch (Throwable localThrowable)
    {
      for (;;) {}
    }
    return this.jdField_a_of_type_ComTencentCommonDownloadIDownloadService != null;
  }
  
  class a
    implements ServiceConnection
  {
    a() {}
    
    public void onServiceConnected(ComponentName arg1, IBinder paramIBinder)
    {
      DownloadServiceProxy.this.jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock.lock();
      synchronized (DownloadServiceProxy.this.mBindServiceLock)
      {
        DownloadServiceProxy.this.mBindServiceLock.notifyAll();
        DownloadServiceProxy.this.jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock.unlock();
        DownloadServiceProxy.this.jdField_a_of_type_ComTencentCommonDownloadIDownloadService = IDownloadService.Stub.asInterface(paramIBinder);
        if (DownloadServiceProxy.this.jdField_a_of_type_ComTencentCommonDownloadIDownloadService != null) {
          DownloadServiceProxy.this.a();
        }
        return;
      }
    }
    
    public void onServiceDisconnected(ComponentName arg1)
    {
      ??? = DownloadServiceProxy.this;
      ???.jdField_a_of_type_ComTencentCommonDownloadIDownloadService = null;
      ???.jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock.lock();
      synchronized (DownloadServiceProxy.this.mBindServiceLock)
      {
        DownloadServiceProxy.this.mBindServiceLock.notifyAll();
        DownloadServiceProxy.this.jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock.unlock();
        return;
      }
    }
  }
  
  private class b
  {
    public int a;
    public long a;
    public String a;
    public boolean a;
    public long b;
    public String b;
    public String c;
    public String d;
    public String e;
    
    private b() {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\download\DownloadServiceProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */