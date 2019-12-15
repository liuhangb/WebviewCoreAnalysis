package org.chromium.policy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import org.chromium.base.ThreadUtils;
import org.chromium.base.VisibleForTesting;
import org.chromium.tencent.utils.X5ApiCompatibilityUtils;

public abstract class AbstractAppRestrictionsProvider
  extends PolicyProvider
{
  private static Bundle jdField_a_of_type_AndroidOsBundle;
  private final BroadcastReceiver jdField_a_of_type_AndroidContentBroadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      AbstractAppRestrictionsProvider.this.refresh();
    }
  };
  private final Context jdField_a_of_type_AndroidContentContext;
  
  public AbstractAppRestrictionsProvider(Context paramContext)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext;
  }
  
  @VisibleForTesting
  public static void setTestRestrictions(Bundle paramBundle)
  {
    jdField_a_of_type_AndroidOsBundle = paramBundle;
  }
  
  @VisibleForTesting
  protected void a(long paramLong) {}
  
  public void destroy()
  {
    stopListening();
    super.destroy();
  }
  
  protected abstract Bundle getApplicationRestrictions(String paramString);
  
  protected abstract String getRestrictionChangeIntentAction();
  
  public void refresh()
  {
    Object localObject = jdField_a_of_type_AndroidOsBundle;
    if (localObject != null)
    {
      notifySettingsAvailable((Bundle)localObject);
      return;
    }
    localObject = StrictMode.allowThreadDiskReads();
    long l = System.currentTimeMillis();
    Bundle localBundle = getApplicationRestrictions(this.jdField_a_of_type_AndroidContentContext.getPackageName());
    a(l);
    StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)localObject);
    notifySettingsAvailable(localBundle);
  }
  
  public void startListeningForPolicyChanges()
  {
    String str = getRestrictionChangeIntentAction();
    if (str == null) {
      return;
    }
    X5ApiCompatibilityUtils.x5RegisterReceiver(this.jdField_a_of_type_AndroidContentContext, this.jdField_a_of_type_AndroidContentBroadcastReceiver, new IntentFilter(str), null, new Handler(ThreadUtils.getUiThreadLooper()));
  }
  
  public void stopListening()
  {
    if (getRestrictionChangeIntentAction() != null) {
      X5ApiCompatibilityUtils.x5UnRegisterReceiver(this.jdField_a_of_type_AndroidContentContext, this.jdField_a_of_type_AndroidContentBroadcastReceiver);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\policy\AbstractAppRestrictionsProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */