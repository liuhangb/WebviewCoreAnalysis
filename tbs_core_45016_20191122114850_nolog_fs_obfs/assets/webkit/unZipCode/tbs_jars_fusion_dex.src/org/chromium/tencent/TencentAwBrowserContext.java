package org.chromium.tencent;

import android.content.Context;
import android.content.SharedPreferences;

public class TencentAwBrowserContext
{
  private Context mApplicationContext;
  private AwMediaAccessPermissions mMediaAccessPermissions;
  private final SharedPreferences mSharedPreferences;
  
  public TencentAwBrowserContext(SharedPreferences paramSharedPreferences, Context paramContext)
  {
    this.mSharedPreferences = paramSharedPreferences;
    this.mApplicationContext = paramContext;
  }
  
  public AwMediaAccessPermissions getMediaAccessPermissions()
  {
    if (this.mMediaAccessPermissions == null)
    {
      this.mMediaAccessPermissions = new AwMediaAccessPermissions(this.mSharedPreferences);
      this.mMediaAccessPermissions.clearAll();
    }
    return this.mMediaAccessPermissions;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\TencentAwBrowserContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */