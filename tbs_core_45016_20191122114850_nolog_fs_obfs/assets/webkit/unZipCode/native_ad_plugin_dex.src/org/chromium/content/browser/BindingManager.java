package org.chromium.content.browser;

import android.content.Context;
import org.chromium.base.process_launcher.ChildProcessConnection;

public abstract interface BindingManager
{
  public abstract void addNewConnection(int paramInt, ChildProcessConnection paramChildProcessConnection);
  
  public abstract void onBroughtToForeground();
  
  public abstract void onSentToBackground();
  
  public abstract void releaseAllModerateBindings();
  
  public abstract void removeConnection(int paramInt);
  
  public abstract void setPriority(int paramInt, boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract void startModerateBindingManagement(Context paramContext, int paramInt);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\BindingManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */