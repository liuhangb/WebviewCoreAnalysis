package org.chromium.content_public.browser;

import android.annotation.TargetApi;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.view.View;
import android.view.inputmethod.CursorAnchorInfo;
import android.view.inputmethod.ExtractedText;

public abstract interface InputMethodManagerWrapper
{
  public abstract boolean hideSoftInputFromWindow(IBinder paramIBinder, int paramInt, ResultReceiver paramResultReceiver);
  
  public abstract boolean isActive(View paramView);
  
  public abstract void notifyUserAction();
  
  public abstract void restartInput(View paramView);
  
  public abstract void showSoftInput(View paramView, int paramInt, ResultReceiver paramResultReceiver);
  
  @TargetApi(21)
  public abstract void updateCursorAnchorInfo(View paramView, CursorAnchorInfo paramCursorAnchorInfo);
  
  public abstract void updateExtractedText(View paramView, int paramInt, ExtractedText paramExtractedText);
  
  public abstract void updateSelection(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content_public\browser\InputMethodManagerWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */