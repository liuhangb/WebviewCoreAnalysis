package org.chromium.base.process_launcher;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.chromium.base.Log;
import org.chromium.base.ObserverList;
import org.chromium.base.VisibleForTesting;

public class ChildConnectionAllocator
{
  private final Handler jdField_a_of_type_AndroidOsHandler;
  private final String jdField_a_of_type_JavaLangString;
  private final ArrayList<Integer> jdField_a_of_type_JavaUtilArrayList;
  private final ObserverList<Listener> jdField_a_of_type_OrgChromiumBaseObserverList = new ObserverList();
  private ConnectionFactory jdField_a_of_type_OrgChromiumBaseProcess_launcherChildConnectionAllocator$ConnectionFactory = new ConnectionFactoryImpl(null);
  private final ChildProcessConnection[] jdField_a_of_type_ArrayOfOrgChromiumBaseProcess_launcherChildProcessConnection;
  private final String jdField_b_of_type_JavaLangString;
  private final boolean jdField_b_of_type_Boolean;
  private final boolean c;
  private final boolean d;
  
  private ChildConnectionAllocator(Handler paramHandler, String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt)
  {
    this.jdField_a_of_type_AndroidOsHandler = paramHandler;
    if ((!jdField_a_of_type_Boolean) && (!a())) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_JavaLangString = paramString1;
    this.jdField_b_of_type_JavaLangString = paramString2;
    this.jdField_b_of_type_Boolean = paramBoolean1;
    this.c = paramBoolean2;
    this.d = paramBoolean3;
    this.jdField_a_of_type_ArrayOfOrgChromiumBaseProcess_launcherChildProcessConnection = new ChildProcessConnection[paramInt];
    this.jdField_a_of_type_JavaUtilArrayList = new ArrayList(paramInt);
    int i = 0;
    while (i < paramInt)
    {
      this.jdField_a_of_type_JavaUtilArrayList.add(Integer.valueOf(i));
      i += 1;
    }
  }
  
  private void a(ChildProcessConnection paramChildProcessConnection)
  {
    if ((!jdField_a_of_type_Boolean) && (!a())) {
      throw new AssertionError();
    }
    int i = Arrays.asList(this.jdField_a_of_type_ArrayOfOrgChromiumBaseProcess_launcherChildProcessConnection).indexOf(paramChildProcessConnection);
    if (i == -1)
    {
      Log.e("ChildConnAllocator", "Unable to find connection to free.", new Object[0]);
      if (!jdField_a_of_type_Boolean) {
        throw new AssertionError();
      }
    }
    else
    {
      this.jdField_a_of_type_ArrayOfOrgChromiumBaseProcess_launcherChildProcessConnection[i] = null;
      if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_JavaUtilArrayList.contains(Integer.valueOf(i)))) {
        throw new AssertionError();
      }
      this.jdField_a_of_type_JavaUtilArrayList.add(Integer.valueOf(i));
    }
    Iterator localIterator = this.jdField_a_of_type_OrgChromiumBaseObserverList.iterator();
    while (localIterator.hasNext()) {
      ((Listener)localIterator.next()).onConnectionFreed(this, paramChildProcessConnection);
    }
  }
  
  private boolean a()
  {
    return this.jdField_a_of_type_AndroidOsHandler.getLooper() == Looper.myLooper();
  }
  
  public static ChildConnectionAllocator create(Context paramContext, Handler paramHandler, String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    paramContext = paramContext.getPackageManager();
    try
    {
      ApplicationInfo localApplicationInfo = paramContext.getApplicationInfo(paramString1, 128);
      if (localApplicationInfo.metaData != null) {
        i = localApplicationInfo.metaData.getInt(paramString3, -1);
      } else {
        i = -1;
      }
      if (i < 0) {}
    }
    catch (PackageManager.NameNotFoundException paramContext)
    {
      int i;
      label109:
      for (;;) {}
    }
    try
    {
      paramString3 = new StringBuilder();
      paramString3.append(paramString2);
      paramString3.append("0");
      paramContext.getServiceInfo(new ComponentName(paramString1, paramString3.toString()), 0);
      return new ChildConnectionAllocator(paramHandler, paramString1, paramString2, paramBoolean1, paramBoolean2, paramBoolean3, i);
    }
    catch (PackageManager.NameNotFoundException paramContext)
    {
      break label109;
    }
    throw new RuntimeException("Illegal meta data value: the child service doesn't exist");
    throw new RuntimeException("Illegal meta data value for number of child services");
    throw new RuntimeException("Could not get application info.");
  }
  
  @VisibleForTesting
  public static ChildConnectionAllocator createForTest(String paramString1, String paramString2, int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    return new ChildConnectionAllocator(new Handler(), paramString1, paramString2, paramBoolean1, paramBoolean2, paramBoolean3, paramInt);
  }
  
  public static int getNumberOfServices(Context paramContext, String paramString1, String paramString2)
  {
    try
    {
      paramContext = paramContext.getPackageManager().getApplicationInfo(paramString1, 128);
      paramString1 = paramContext.metaData;
      int i = -1;
      if (paramString1 != null) {
        i = paramContext.metaData.getInt(paramString2, -1);
      }
      if (i >= 0) {
        return i;
      }
      throw new RuntimeException("Illegal meta data value for number of child services");
    }
    catch (PackageManager.NameNotFoundException paramContext)
    {
      throw new RuntimeException("Could not get application info", paramContext);
    }
  }
  
  public void addListener(Listener paramListener)
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_OrgChromiumBaseObserverList.hasObserver(paramListener))) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_OrgChromiumBaseObserverList.addObserver(paramListener);
  }
  
  public ChildProcessConnection allocate(Context paramContext, Bundle paramBundle, final ChildProcessConnection.ServiceCallback paramServiceCallback)
  {
    if ((!jdField_a_of_type_Boolean) && (!a())) {
      throw new AssertionError();
    }
    if (this.jdField_a_of_type_JavaUtilArrayList.isEmpty()) {
      return null;
    }
    int i = ((Integer)this.jdField_a_of_type_JavaUtilArrayList.remove(0)).intValue();
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_ArrayOfOrgChromiumBaseProcess_launcherChildProcessConnection[i] != null)) {
      throw new AssertionError();
    }
    Object localObject = this.jdField_a_of_type_JavaLangString;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.jdField_b_of_type_JavaLangString);
    localStringBuilder.append(i);
    localObject = new ComponentName((String)localObject, localStringBuilder.toString());
    paramServiceCallback = new ChildProcessConnection.ServiceCallback()
    {
      private void a(final ChildProcessConnection paramAnonymousChildProcessConnection)
      {
        ChildConnectionAllocator.a(ChildConnectionAllocator.this).postDelayed(new Runnable()
        {
          public void run()
          {
            ChildConnectionAllocator.a(ChildConnectionAllocator.this, paramAnonymousChildProcessConnection);
          }
        }, 1L);
      }
      
      public void onChildProcessDied(final ChildProcessConnection paramAnonymousChildProcessConnection)
      {
        if ((!jdField_a_of_type_Boolean) && (!ChildConnectionAllocator.a(ChildConnectionAllocator.this))) {
          throw new AssertionError();
        }
        if (paramServiceCallback != null) {
          ChildConnectionAllocator.a(ChildConnectionAllocator.this).post(new Runnable()
          {
            public void run()
            {
              ChildConnectionAllocator.1.this.a.onChildProcessDied(paramAnonymousChildProcessConnection);
            }
          });
        }
        a(paramAnonymousChildProcessConnection);
      }
      
      public void onChildStartFailed(final ChildProcessConnection paramAnonymousChildProcessConnection)
      {
        if ((!jdField_a_of_type_Boolean) && (!ChildConnectionAllocator.a(ChildConnectionAllocator.this))) {
          throw new AssertionError();
        }
        if (paramServiceCallback != null) {
          ChildConnectionAllocator.a(ChildConnectionAllocator.this).post(new Runnable()
          {
            public void run()
            {
              ChildConnectionAllocator.1.this.a.onChildStartFailed(paramAnonymousChildProcessConnection);
            }
          });
        }
        a(paramAnonymousChildProcessConnection);
      }
      
      public void onChildStarted()
      {
        if ((!jdField_a_of_type_Boolean) && (!ChildConnectionAllocator.a(ChildConnectionAllocator.this))) {
          throw new AssertionError();
        }
        if (paramServiceCallback != null) {
          ChildConnectionAllocator.a(ChildConnectionAllocator.this).post(new Runnable()
          {
            public void run()
            {
              ChildConnectionAllocator.1.this.a.onChildStarted();
            }
          });
        }
      }
    };
    paramContext = this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildConnectionAllocator$ConnectionFactory.createConnection(paramContext, (ComponentName)localObject, this.jdField_b_of_type_Boolean, this.c, paramBundle);
    this.jdField_a_of_type_ArrayOfOrgChromiumBaseProcess_launcherChildProcessConnection[i] = paramContext;
    paramBundle = this.jdField_a_of_type_OrgChromiumBaseObserverList.iterator();
    while (paramBundle.hasNext()) {
      ((Listener)paramBundle.next()).onConnectionAllocated(this, paramContext);
    }
    paramContext.start(this.d, paramServiceCallback);
    return paramContext;
  }
  
  @VisibleForTesting
  public int allocatedConnectionsCountForTesting()
  {
    if ((!jdField_a_of_type_Boolean) && (!a())) {
      throw new AssertionError();
    }
    return this.jdField_a_of_type_ArrayOfOrgChromiumBaseProcess_launcherChildProcessConnection.length - this.jdField_a_of_type_JavaUtilArrayList.size();
  }
  
  public boolean anyConnectionAllocated()
  {
    return this.jdField_a_of_type_JavaUtilArrayList.size() < this.jdField_a_of_type_ArrayOfOrgChromiumBaseProcess_launcherChildProcessConnection.length;
  }
  
  @VisibleForTesting
  public ChildProcessConnection getChildProcessConnectionAtSlotForTesting(int paramInt)
  {
    return this.jdField_a_of_type_ArrayOfOrgChromiumBaseProcess_launcherChildProcessConnection[paramInt];
  }
  
  public int getNumberOfServices()
  {
    return this.jdField_a_of_type_ArrayOfOrgChromiumBaseProcess_launcherChildProcessConnection.length;
  }
  
  public String getPackageName()
  {
    return this.jdField_a_of_type_JavaLangString;
  }
  
  public boolean isConnectionFromAllocator(ChildProcessConnection paramChildProcessConnection)
  {
    ChildProcessConnection[] arrayOfChildProcessConnection = this.jdField_a_of_type_ArrayOfOrgChromiumBaseProcess_launcherChildProcessConnection;
    int j = arrayOfChildProcessConnection.length;
    int i = 0;
    while (i < j)
    {
      if (arrayOfChildProcessConnection[i] == paramChildProcessConnection) {
        return true;
      }
      i += 1;
    }
    return false;
  }
  
  public boolean isFreeConnectionAvailable()
  {
    if ((!jdField_a_of_type_Boolean) && (!a())) {
      throw new AssertionError();
    }
    return this.jdField_a_of_type_JavaUtilArrayList.isEmpty() ^ true;
  }
  
  public void removeListener(Listener paramListener)
  {
    boolean bool = this.jdField_a_of_type_OrgChromiumBaseObserverList.removeObserver(paramListener);
    if (!jdField_a_of_type_Boolean)
    {
      if (bool) {
        return;
      }
      throw new AssertionError();
    }
  }
  
  @VisibleForTesting
  public void setConnectionFactoryForTesting(ConnectionFactory paramConnectionFactory)
  {
    this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildConnectionAllocator$ConnectionFactory = paramConnectionFactory;
  }
  
  @VisibleForTesting
  public static abstract interface ConnectionFactory
  {
    public abstract ChildProcessConnection createConnection(Context paramContext, ComponentName paramComponentName, boolean paramBoolean1, boolean paramBoolean2, Bundle paramBundle);
  }
  
  private static class ConnectionFactoryImpl
    implements ChildConnectionAllocator.ConnectionFactory
  {
    public ChildProcessConnection createConnection(Context paramContext, ComponentName paramComponentName, boolean paramBoolean1, boolean paramBoolean2, Bundle paramBundle)
    {
      return new ChildProcessConnection(paramContext, paramComponentName, paramBoolean1, paramBoolean2, paramBundle);
    }
  }
  
  public static abstract class Listener
  {
    public void onConnectionAllocated(ChildConnectionAllocator paramChildConnectionAllocator, ChildProcessConnection paramChildProcessConnection) {}
    
    public void onConnectionFreed(ChildConnectionAllocator paramChildConnectionAllocator, ChildProcessConnection paramChildProcessConnection) {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\process_launcher\ChildConnectionAllocator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */