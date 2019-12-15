package org.chromium.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.Window.Callback;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.MainDex;

@JNINamespace("base::android")
@MainDex
public class ApplicationStatus
{
  @SuppressLint({"StaticFieldLeak"})
  private static Activity jdField_a_of_type_AndroidAppActivity;
  @SuppressLint({"SupportAnnotationUsage"})
  private static Integer jdField_a_of_type_JavaLangInteger;
  private static final Object jdField_a_of_type_JavaLangObject = new Object();
  private static final Map<Activity, ActivityInfo> jdField_a_of_type_JavaUtilMap = new ConcurrentHashMap();
  private static ApplicationStateListener jdField_a_of_type_OrgChromiumBaseApplicationStatus$ApplicationStateListener;
  private static final ObserverList<ActivityStateListener> jdField_a_of_type_OrgChromiumBaseObserverList = new ObserverList();
  private static final ObserverList<ApplicationStateListener> jdField_b_of_type_OrgChromiumBaseObserverList = new ObserverList();
  private static boolean jdField_b_of_type_Boolean;
  private static final ObserverList<WindowFocusChangedListener> c = new ObserverList();
  
  private static int a()
  {
    Iterator localIterator = jdField_a_of_type_JavaUtilMap.values().iterator();
    int j = 0;
    int i = 0;
    while (localIterator.hasNext())
    {
      int k = ((ActivityInfo)localIterator.next()).getStatus();
      if ((k != 4) && (k != 5) && (k != 6)) {
        return 1;
      }
      if (k == 4) {
        j = 1;
      } else if (k == 5) {
        i = 1;
      }
    }
    if (j != 0) {
      return 2;
    }
    if (i != 0) {
      return 3;
    }
    return 4;
  }
  
  private static void a()
  {
    if (jdField_b_of_type_Boolean) {
      return;
    }
    throw new IllegalStateException("ApplicationStatus has not been initialized yet.");
  }
  
  private static void b(Activity paramActivity, int paramInt)
  {
    if (paramActivity != null)
    {
      if ((jdField_a_of_type_AndroidAppActivity == null) || (paramInt == 1) || (paramInt == 3) || (paramInt == 2)) {
        jdField_a_of_type_AndroidAppActivity = paramActivity;
      }
      int i = getStateForApplication();
      if (paramInt == 1)
      {
        if ((!jdField_a_of_type_Boolean) && (jdField_a_of_type_JavaUtilMap.containsKey(paramActivity))) {
          throw new AssertionError();
        }
        jdField_a_of_type_JavaUtilMap.put(paramActivity, new ActivityInfo(null));
      }
      synchronized (jdField_a_of_type_JavaLangObject)
      {
        jdField_a_of_type_JavaLangInteger = null;
        ??? = (ActivityInfo)jdField_a_of_type_JavaUtilMap.get(paramActivity);
        ((ActivityInfo)???).setStatus(paramInt);
        if (paramInt == 6)
        {
          jdField_a_of_type_JavaUtilMap.remove(paramActivity);
          if (paramActivity == jdField_a_of_type_AndroidAppActivity) {
            jdField_a_of_type_AndroidAppActivity = null;
          }
        }
        ??? = ((ActivityInfo)???).getListeners().iterator();
        while (((Iterator)???).hasNext()) {
          ((ActivityStateListener)((Iterator)???).next()).onActivityStateChange(paramActivity, paramInt);
        }
        ??? = jdField_a_of_type_OrgChromiumBaseObserverList.iterator();
        while (((Iterator)???).hasNext()) {
          ((ActivityStateListener)((Iterator)???).next()).onActivityStateChange(paramActivity, paramInt);
        }
        paramInt = getStateForApplication();
        if (paramInt != i)
        {
          paramActivity = jdField_b_of_type_OrgChromiumBaseObserverList.iterator();
          while (paramActivity.hasNext()) {
            ((ApplicationStateListener)paramActivity.next()).onApplicationStateChange(paramInt);
          }
        }
        return;
      }
    }
    throw new IllegalArgumentException("null activity is not supported");
  }
  
  public static void destroyForJUnitTests()
  {
    jdField_b_of_type_OrgChromiumBaseObserverList.clear();
    jdField_a_of_type_OrgChromiumBaseObserverList.clear();
    jdField_a_of_type_JavaUtilMap.clear();
    c.clear();
    jdField_b_of_type_Boolean = false;
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      jdField_a_of_type_JavaLangInteger = null;
      jdField_a_of_type_AndroidAppActivity = null;
      jdField_a_of_type_OrgChromiumBaseApplicationStatus$ApplicationStateListener = null;
      return;
    }
  }
  
  public static Activity getLastTrackedFocusedActivity()
  {
    return jdField_a_of_type_AndroidAppActivity;
  }
  
  public static List<WeakReference<Activity>> getRunningActivities()
  {
    a();
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = jdField_a_of_type_JavaUtilMap.keySet().iterator();
    while (localIterator.hasNext()) {
      localArrayList.add(new WeakReference((Activity)localIterator.next()));
    }
    return localArrayList;
  }
  
  public static int getStateForActivity(@Nullable Activity paramActivity)
  {
    a();
    int i = 6;
    if (paramActivity == null) {
      return 6;
    }
    paramActivity = (ActivityInfo)jdField_a_of_type_JavaUtilMap.get(paramActivity);
    if (paramActivity != null) {
      i = paramActivity.getStatus();
    }
    return i;
  }
  
  @CalledByNative
  public static int getStateForApplication()
  {
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      if (jdField_a_of_type_JavaLangInteger == null) {
        jdField_a_of_type_JavaLangInteger = Integer.valueOf(a());
      }
      int i = jdField_a_of_type_JavaLangInteger.intValue();
      return i;
    }
  }
  
  public static boolean hasVisibleActivities()
  {
    int i = getStateForApplication();
    boolean bool = true;
    if (i != 1)
    {
      if (i == 2) {
        return true;
      }
      bool = false;
    }
    return bool;
  }
  
  public static void initialize(Application paramApplication)
  {
    if (jdField_b_of_type_Boolean) {
      return;
    }
    jdField_b_of_type_Boolean = true;
    registerWindowFocusChangedListener(new WindowFocusChangedListener()
    {
      public void onWindowFocusChanged(Activity paramAnonymousActivity, boolean paramAnonymousBoolean)
      {
        if (paramAnonymousBoolean)
        {
          if (paramAnonymousActivity == ApplicationStatus.a()) {
            return;
          }
          int i = ApplicationStatus.getStateForActivity(paramAnonymousActivity);
          if ((i != 6) && (i != 5)) {
            ApplicationStatus.a(paramAnonymousActivity);
          }
          return;
        }
      }
    });
    paramApplication.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks()
    {
      private void a(Activity paramAnonymousActivity)
      {
        if (BuildConfig.DCHECK_IS_ON)
        {
          paramAnonymousActivity = paramAnonymousActivity.getWindow().getCallback().getClass();
          if ((!a) && (!Proxy.isProxyClass(paramAnonymousActivity)) && (!paramAnonymousActivity.getName().equals("android.support.v7.app.ToolbarActionBar$ToolbarCallbackWrapper")) && (!paramAnonymousActivity.getName().equals("android.support.v7.internal.app.ToolbarActionBar$ToolbarCallbackWrapper")))
          {
            if (paramAnonymousActivity.getName().equals("com.android.tools.profiler.support.event.WindowProfilerCallback")) {
              return;
            }
            throw new AssertionError();
          }
        }
      }
      
      public void onActivityCreated(Activity paramAnonymousActivity, Bundle paramAnonymousBundle)
      {
        ApplicationStatus.a(paramAnonymousActivity, 1);
        Window.Callback localCallback = paramAnonymousActivity.getWindow().getCallback();
        paramAnonymousBundle = paramAnonymousActivity.getWindow();
        ClassLoader localClassLoader = Window.Callback.class.getClassLoader();
        paramAnonymousActivity = new ApplicationStatus.WindowCallbackProxy(paramAnonymousActivity, localCallback);
        paramAnonymousBundle.setCallback((Window.Callback)Proxy.newProxyInstance(localClassLoader, new Class[] { Window.Callback.class }, paramAnonymousActivity));
      }
      
      public void onActivityDestroyed(Activity paramAnonymousActivity)
      {
        ApplicationStatus.a(paramAnonymousActivity, 6);
        a(paramAnonymousActivity);
      }
      
      public void onActivityPaused(Activity paramAnonymousActivity)
      {
        ApplicationStatus.a(paramAnonymousActivity, 4);
        a(paramAnonymousActivity);
      }
      
      public void onActivityResumed(Activity paramAnonymousActivity)
      {
        ApplicationStatus.a(paramAnonymousActivity, 3);
        a(paramAnonymousActivity);
      }
      
      public void onActivitySaveInstanceState(Activity paramAnonymousActivity, Bundle paramAnonymousBundle)
      {
        a(paramAnonymousActivity);
      }
      
      public void onActivityStarted(Activity paramAnonymousActivity)
      {
        ApplicationStatus.a(paramAnonymousActivity, 2);
        a(paramAnonymousActivity);
      }
      
      public void onActivityStopped(Activity paramAnonymousActivity)
      {
        ApplicationStatus.a(paramAnonymousActivity, 5);
        a(paramAnonymousActivity);
      }
    });
  }
  
  public static boolean isEveryActivityDestroyed()
  {
    return jdField_a_of_type_JavaUtilMap.isEmpty();
  }
  
  private static native void nativeOnApplicationStateChange(int paramInt);
  
  @VisibleForTesting
  public static void onStateChangeForTesting(Activity paramActivity, int paramInt)
  {
    b(paramActivity, paramInt);
  }
  
  public static void registerApplicationStateListener(ApplicationStateListener paramApplicationStateListener)
  {
    jdField_b_of_type_OrgChromiumBaseObserverList.addObserver(paramApplicationStateListener);
  }
  
  @SuppressLint({"NewApi"})
  public static void registerStateListenerForActivity(ActivityStateListener paramActivityStateListener, Activity paramActivity)
  {
    if (paramActivity != null)
    {
      a();
      paramActivity = (ActivityInfo)jdField_a_of_type_JavaUtilMap.get(paramActivity);
      if (paramActivity != null)
      {
        if ((!jdField_a_of_type_Boolean) && (paramActivity.getStatus() == 6)) {
          throw new AssertionError();
        }
        paramActivity.getListeners().addObserver(paramActivityStateListener);
        return;
      }
      throw new IllegalStateException("Attempting to register listener on an untracked activity.");
    }
    throw new IllegalStateException("Attempting to register listener on a null activity.");
  }
  
  public static void registerStateListenerForAllActivities(ActivityStateListener paramActivityStateListener)
  {
    jdField_a_of_type_OrgChromiumBaseObserverList.addObserver(paramActivityStateListener);
  }
  
  @CalledByNative
  private static void registerThreadSafeNativeApplicationStateListener()
  {
    ThreadUtils.runOnUiThread(new Runnable()
    {
      public void run()
      {
        if (ApplicationStatus.a() != null) {
          return;
        }
        ApplicationStatus.a(new ApplicationStatus.ApplicationStateListener()
        {
          public void onApplicationStateChange(int paramAnonymous2Int)
          {
            ApplicationStatus.a(paramAnonymous2Int);
          }
        });
        ApplicationStatus.registerApplicationStateListener(ApplicationStatus.a());
      }
    });
  }
  
  public static void registerWindowFocusChangedListener(WindowFocusChangedListener paramWindowFocusChangedListener)
  {
    c.addObserver(paramWindowFocusChangedListener);
  }
  
  public static void unregisterActivityStateListener(ActivityStateListener paramActivityStateListener)
  {
    jdField_a_of_type_OrgChromiumBaseObserverList.removeObserver(paramActivityStateListener);
    Iterator localIterator = jdField_a_of_type_JavaUtilMap.values().iterator();
    while (localIterator.hasNext()) {
      ((ActivityInfo)localIterator.next()).getListeners().removeObserver(paramActivityStateListener);
    }
  }
  
  public static void unregisterApplicationStateListener(ApplicationStateListener paramApplicationStateListener)
  {
    jdField_b_of_type_OrgChromiumBaseObserverList.removeObserver(paramApplicationStateListener);
  }
  
  public static void unregisterWindowFocusChangedListener(WindowFocusChangedListener paramWindowFocusChangedListener)
  {
    c.removeObserver(paramWindowFocusChangedListener);
  }
  
  private static class ActivityInfo
  {
    private int jdField_a_of_type_Int = 6;
    private ObserverList<ApplicationStatus.ActivityStateListener> jdField_a_of_type_OrgChromiumBaseObserverList = new ObserverList();
    
    public ObserverList<ApplicationStatus.ActivityStateListener> getListeners()
    {
      return this.jdField_a_of_type_OrgChromiumBaseObserverList;
    }
    
    public int getStatus()
    {
      return this.jdField_a_of_type_Int;
    }
    
    public void setStatus(int paramInt)
    {
      this.jdField_a_of_type_Int = paramInt;
    }
  }
  
  public static abstract interface ActivityStateListener
  {
    public abstract void onActivityStateChange(Activity paramActivity, int paramInt);
  }
  
  public static abstract interface ApplicationStateListener
  {
    public abstract void onApplicationStateChange(int paramInt);
  }
  
  private static class WindowCallbackProxy
    implements InvocationHandler
  {
    private final Activity jdField_a_of_type_AndroidAppActivity;
    private final Window.Callback jdField_a_of_type_AndroidViewWindow$Callback;
    
    public WindowCallbackProxy(Activity paramActivity, Window.Callback paramCallback)
    {
      this.jdField_a_of_type_AndroidViewWindow$Callback = paramCallback;
      this.jdField_a_of_type_AndroidAppActivity = paramActivity;
    }
    
    public Object invoke(Object paramObject, Method paramMethod, Object[] paramArrayOfObject)
      throws Throwable
    {
      if ((paramMethod.getName().equals("onWindowFocusChanged")) && (paramArrayOfObject.length == 1) && ((paramArrayOfObject[0] instanceof Boolean)))
      {
        onWindowFocusChanged(((Boolean)paramArrayOfObject[0]).booleanValue());
        return null;
      }
      try
      {
        paramObject = paramMethod.invoke(this.jdField_a_of_type_AndroidViewWindow$Callback, paramArrayOfObject);
        return paramObject;
      }
      catch (InvocationTargetException paramObject)
      {
        if ((((InvocationTargetException)paramObject).getCause() instanceof AbstractMethodError)) {
          throw ((InvocationTargetException)paramObject).getCause();
        }
        throw ((Throwable)paramObject);
      }
    }
    
    public void onWindowFocusChanged(boolean paramBoolean)
    {
      this.jdField_a_of_type_AndroidViewWindow$Callback.onWindowFocusChanged(paramBoolean);
      Iterator localIterator = ApplicationStatus.a().iterator();
      while (localIterator.hasNext()) {
        ((ApplicationStatus.WindowFocusChangedListener)localIterator.next()).onWindowFocusChanged(this.jdField_a_of_type_AndroidAppActivity, paramBoolean);
      }
    }
  }
  
  public static abstract interface WindowFocusChangedListener
  {
    public abstract void onWindowFocusChanged(Activity paramActivity, boolean paramBoolean);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\ApplicationStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */