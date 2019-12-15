package org.chromium.android_webview;

import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.view.LayoutInflater;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

public class ResourcesContextWrapperFactory
{
  private static final Object jdField_a_of_type_JavaLangObject = new Object();
  private static final WeakHashMap<Context, WeakReference<WebViewContextWrapper>> jdField_a_of_type_JavaUtilWeakHashMap = new WeakHashMap();
  
  public static Context get(Context paramContext)
  {
    if ((paramContext instanceof WebViewContextWrapper)) {
      return paramContext;
    }
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      Object localObject1 = (WeakReference)jdField_a_of_type_JavaUtilWeakHashMap.get(paramContext);
      if (localObject1 == null) {
        localObject1 = null;
      } else {
        localObject1 = (WebViewContextWrapper)((WeakReference)localObject1).get();
      }
      Object localObject2 = localObject1;
      if (localObject1 == null)
      {
        localObject2 = new WebViewContextWrapper(paramContext);
        jdField_a_of_type_JavaUtilWeakHashMap.put(paramContext, new WeakReference(localObject2));
      }
      return (Context)localObject2;
    }
  }
  
  private static class WebViewContextWrapper
    extends ContextWrapper
  {
    private Context a;
    
    public WebViewContextWrapper(Context paramContext)
    {
      super();
    }
    
    public Context getApplicationContext()
    {
      if (this.a == null)
      {
        Context localContext = getBaseContext().getApplicationContext();
        if (localContext == getBaseContext()) {
          this.a = this;
        } else {
          this.a = ResourcesContextWrapperFactory.get(localContext);
        }
      }
      return this.a;
    }
    
    public ClassLoader getClassLoader()
    {
      final ClassLoader localClassLoader = getBaseContext().getClassLoader();
      new ClassLoader()
      {
        protected Class<?> findClass(String paramAnonymousString)
          throws ClassNotFoundException
        {
          try
          {
            Class localClass = this.jdField_a_of_type_JavaLangClassLoader.loadClass(paramAnonymousString);
            return localClass;
          }
          catch (ClassNotFoundException localClassNotFoundException)
          {
            for (;;) {}
          }
          return localClassLoader.loadClass(paramAnonymousString);
        }
      };
    }
    
    public Object getSystemService(String paramString)
    {
      if ("layout_inflater".equals(paramString)) {
        return ((LayoutInflater)getBaseContext().getSystemService(paramString)).cloneInContext(this);
      }
      return getBaseContext().getSystemService(paramString);
    }
    
    public void registerComponentCallbacks(ComponentCallbacks paramComponentCallbacks)
    {
      getBaseContext().registerComponentCallbacks(paramComponentCallbacks);
    }
    
    public void startActivity(Intent paramIntent)
    {
      if (AwContents.activityFromContext(this) == null) {
        paramIntent.setFlags(268435456);
      }
      super.startActivity(paramIntent);
    }
    
    public void unregisterComponentCallbacks(ComponentCallbacks paramComponentCallbacks)
    {
      getBaseContext().unregisterComponentCallbacks(paramComponentCallbacks);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\ResourcesContextWrapperFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */