package org.chromium.base.process_launcher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.SparseArray;
import java.util.List;

public abstract interface ChildProcessServiceDelegate
{
  public abstract SparseArray<String> getFileDescriptorsIdsToKeys();
  
  public abstract boolean loadNativeLibrary(Context paramContext);
  
  public abstract void onBeforeMain();
  
  public abstract void onConnectionSetup(Bundle paramBundle, List<IBinder> paramList);
  
  public abstract void onDestroy();
  
  public abstract void onServiceBound(Intent paramIntent);
  
  public abstract void onServiceCreated();
  
  public abstract void preloadNativeLibrary(Context paramContext);
  
  public abstract void runMain();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\process_launcher\ChildProcessServiceDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */