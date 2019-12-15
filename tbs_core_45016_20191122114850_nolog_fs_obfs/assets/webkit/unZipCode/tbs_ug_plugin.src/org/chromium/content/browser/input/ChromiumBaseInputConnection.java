package org.chromium.content.browser.input;

import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import org.chromium.base.VisibleForTesting;

public abstract interface ChromiumBaseInputConnection
  extends InputConnection
{
  @VisibleForTesting
  public abstract Handler getHandler();
  
  public abstract void onRestartInputOnUiThread();
  
  public abstract boolean sendKeyEventOnUiThread(KeyEvent paramKeyEvent);
  
  public abstract void unblockOnUiThread();
  
  public abstract void updateStateOnUiThread(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, boolean paramBoolean2);
  
  public static abstract interface Factory
  {
    @VisibleForTesting
    public abstract Handler getHandler();
    
    public abstract ChromiumBaseInputConnection initializeAndGet(View paramView, ImeAdapterImpl paramImeAdapterImpl, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, EditorInfo paramEditorInfo);
    
    public abstract void onViewAttachedToWindow();
    
    public abstract void onViewDetachedFromWindow();
    
    public abstract void onViewFocusChanged(boolean paramBoolean);
    
    public abstract void onWindowFocusChanged(boolean paramBoolean);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\input\ChromiumBaseInputConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */