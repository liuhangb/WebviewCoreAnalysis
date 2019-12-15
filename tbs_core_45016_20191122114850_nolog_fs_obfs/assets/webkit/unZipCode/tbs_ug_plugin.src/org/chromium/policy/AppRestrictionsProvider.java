package org.chromium.policy;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.UserManager;

public class AppRestrictionsProvider
  extends AbstractAppRestrictionsProvider
{
  private final UserManager a;
  
  public AppRestrictionsProvider(Context paramContext)
  {
    super(paramContext);
    if (Build.VERSION.SDK_INT >= 18)
    {
      this.a = ((UserManager)paramContext.getSystemService("user"));
      return;
    }
    this.a = null;
  }
  
  @TargetApi(18)
  protected Bundle getApplicationRestrictions(String paramString)
  {
    UserManager localUserManager = this.a;
    if (localUserManager == null) {
      return new Bundle();
    }
    try
    {
      paramString = localUserManager.getApplicationRestrictions(paramString);
      return paramString;
    }
    catch (Throwable paramString)
    {
      paramString.printStackTrace();
    }
    return new Bundle();
  }
  
  @TargetApi(21)
  protected String getRestrictionChangeIntentAction()
  {
    if (Build.VERSION.SDK_INT < 21) {
      return null;
    }
    return "android.intent.action.APPLICATION_RESTRICTIONS_CHANGED";
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\policy\AppRestrictionsProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */