package org.chromium.tencent;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.webkit.ValueCallback;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.chromium.base.ThreadUtils;
import org.chromium.net.GURLUtils;

@TargetApi(9)
public class AwMediaAccessPermissions
{
  public static final int AR_VIDEO_CAPTURE_TYPE = 3;
  public static final int AUDIO_CAPTURE_TYPE = 2;
  private static final String PREF_PREFIX = "AwMediaAccessPermissions";
  private static final String PREF_PREFIX_AR_VIDEO = "AwMediaAccessPermissions_AR_Video%";
  private static final String PREF_PREFIX_AUDIO = "AwMediaAccessPermissions_Audio%";
  private static final String PREF_PREFIX_VIDEO = "AwMediaAccessPermissions_Video%";
  public static final int VIDEO_CAPTURE_TYPE = 1;
  private final SharedPreferences mSharedPreferences;
  
  public AwMediaAccessPermissions(SharedPreferences paramSharedPreferences)
  {
    this.mSharedPreferences = paramSharedPreferences;
  }
  
  private String getOriginKey(String paramString, int paramInt)
  {
    paramString = GURLUtils.getOrigin(paramString);
    if (paramString.isEmpty()) {
      return null;
    }
    StringBuilder localStringBuilder;
    if (paramInt == 1)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("AwMediaAccessPermissions_Video%");
      localStringBuilder.append(paramString);
      return localStringBuilder.toString();
    }
    if (paramInt == 2)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("AwMediaAccessPermissions_Audio%");
      localStringBuilder.append(paramString);
      return localStringBuilder.toString();
    }
    if (paramInt == 3)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("AwMediaAccessPermissions_AR_Video%");
      localStringBuilder.append(paramString);
      return localStringBuilder.toString();
    }
    return null;
  }
  
  @TargetApi(9)
  public void allow(String paramString, int paramInt)
  {
    paramString = getOriginKey(paramString, paramInt);
    if (paramString != null) {
      this.mSharedPreferences.edit().putBoolean(paramString, true).apply();
    }
  }
  
  @TargetApi(9)
  public void clear(String paramString)
  {
    String str = getOriginKey(paramString, 1);
    if (str != null) {
      this.mSharedPreferences.edit().remove(str).apply();
    }
    str = getOriginKey(paramString, 2);
    if (str != null) {
      this.mSharedPreferences.edit().remove(str).apply();
    }
    paramString = getOriginKey(paramString, 3);
    if (paramString != null) {
      this.mSharedPreferences.edit().remove(paramString).apply();
    }
  }
  
  @SuppressLint({"NewApi"})
  public void clearAll()
  {
    Iterator localIterator = this.mSharedPreferences.getAll().keySet().iterator();
    Object localObject1 = null;
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (str.startsWith("AwMediaAccessPermissions"))
      {
        Object localObject2 = localObject1;
        if (localObject1 == null) {
          localObject2 = this.mSharedPreferences.edit();
        }
        ((SharedPreferences.Editor)localObject2).remove(str);
        localObject1 = localObject2;
      }
    }
    if (localObject1 != null) {
      ((SharedPreferences.Editor)localObject1).apply();
    }
  }
  
  @SuppressLint({"NewApi"})
  public void deny(String paramString, int paramInt)
  {
    paramString = getOriginKey(paramString, paramInt);
    if (paramString != null) {
      this.mSharedPreferences.edit().putBoolean(paramString, false).apply();
    }
  }
  
  public void getAllowed(String paramString, int paramInt, final ValueCallback<Boolean> paramValueCallback)
  {
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        paramValueCallback.onReceiveValue(Boolean.valueOf(this.val$finalAllowed));
      }
    });
  }
  
  public void getOrigins(final ValueCallback<Set<String>> paramValueCallback)
  {
    final HashSet localHashSet = new HashSet();
    Iterator localIterator = this.mSharedPreferences.getAll().keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (str.startsWith("AwMediaAccessPermissions")) {
        localHashSet.add(str.substring(24));
      }
    }
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        paramValueCallback.onReceiveValue(localHashSet);
      }
    });
  }
  
  public boolean hasOrigin(String paramString, int paramInt)
  {
    return this.mSharedPreferences.contains(getOriginKey(paramString, paramInt));
  }
  
  public boolean isOriginAllowed(String paramString, int paramInt)
  {
    return this.mSharedPreferences.getBoolean(getOriginKey(paramString, paramInt), false);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\AwMediaAccessPermissions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */