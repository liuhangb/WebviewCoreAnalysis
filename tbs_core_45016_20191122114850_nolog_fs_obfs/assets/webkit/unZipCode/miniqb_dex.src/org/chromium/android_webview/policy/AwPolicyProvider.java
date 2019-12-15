package org.chromium.android_webview.policy;

import android.content.Context;
import android.os.Bundle;
import java.util.Iterator;
import java.util.Set;
import org.chromium.base.VisibleForTesting;
import org.chromium.policy.AppRestrictionsProvider;

@VisibleForTesting
public class AwPolicyProvider
  extends AppRestrictionsProvider
{
  public static final String POLICY_PREFIX = "com.android.browser:";
  
  public AwPolicyProvider(Context paramContext)
  {
    super(paramContext);
  }
  
  public void notifySettingsAvailable(Bundle paramBundle)
  {
    if (paramBundle != null)
    {
      Bundle localBundle = new Bundle();
      Iterator localIterator = paramBundle.keySet().iterator();
      for (;;)
      {
        localObject = localBundle;
        if (!localIterator.hasNext()) {
          break;
        }
        localObject = (String)localIterator.next();
        if (((String)localObject).startsWith("com.android.browser:")) {
          localBundle.putSerializable(((String)localObject).substring(20), paramBundle.getSerializable((String)localObject));
        }
      }
    }
    Object localObject = null;
    super.notifySettingsAvailable((Bundle)localObject);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\policy\AwPolicyProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */