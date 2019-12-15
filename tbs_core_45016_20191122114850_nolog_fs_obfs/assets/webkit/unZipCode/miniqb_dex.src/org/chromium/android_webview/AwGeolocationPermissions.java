package org.chromium.android_webview;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.chromium.base.Callback;
import org.chromium.base.ThreadUtils;
import org.chromium.net.GURLUtils;

public final class AwGeolocationPermissions
{
  private final SharedPreferences a;
  
  public AwGeolocationPermissions(SharedPreferences paramSharedPreferences)
  {
    this.a = paramSharedPreferences;
  }
  
  private String a(String paramString)
  {
    paramString = GURLUtils.getOrigin(paramString);
    if (paramString.isEmpty()) {
      return null;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("AwGeolocationPermissions%");
    localStringBuilder.append(paramString);
    return localStringBuilder.toString();
  }
  
  public void allow(String paramString)
  {
    paramString = a(paramString);
    if (paramString != null) {
      this.a.edit().putBoolean(paramString, true).apply();
    }
  }
  
  public void clear(String paramString)
  {
    paramString = a(paramString);
    if (paramString != null) {
      this.a.edit().remove(paramString).apply();
    }
  }
  
  public void clearAll()
  {
    Iterator localIterator = this.a.getAll().keySet().iterator();
    Object localObject1 = null;
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (str.startsWith("AwGeolocationPermissions%"))
      {
        Object localObject2 = localObject1;
        if (localObject1 == null) {
          localObject2 = this.a.edit();
        }
        ((SharedPreferences.Editor)localObject2).remove(str);
        localObject1 = localObject2;
      }
    }
    if (localObject1 != null) {
      ((SharedPreferences.Editor)localObject1).apply();
    }
  }
  
  public void deny(String paramString)
  {
    paramString = a(paramString);
    if (paramString != null) {
      this.a.edit().putBoolean(paramString, false).apply();
    }
  }
  
  public void getAllowed(String paramString, final Callback<Boolean> paramCallback)
  {
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        paramCallback.onResult(Boolean.valueOf(this.jdField_a_of_type_Boolean));
      }
    });
  }
  
  public void getOrigins(final Callback<Set<String>> paramCallback)
  {
    final HashSet localHashSet = new HashSet();
    Iterator localIterator = this.a.getAll().keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (str.startsWith("AwGeolocationPermissions%")) {
        localHashSet.add(str.substring(25));
      }
    }
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        paramCallback.onResult(localHashSet);
      }
    });
  }
  
  public boolean hasOrigin(String paramString)
  {
    return this.a.contains(a(paramString));
  }
  
  public boolean isOriginAllowed(String paramString)
  {
    return this.a.getBoolean(a(paramString), false);
  }
  
  public static abstract interface Callback
  {
    public abstract void invoke(String paramString, boolean paramBoolean1, boolean paramBoolean2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwGeolocationPermissions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */