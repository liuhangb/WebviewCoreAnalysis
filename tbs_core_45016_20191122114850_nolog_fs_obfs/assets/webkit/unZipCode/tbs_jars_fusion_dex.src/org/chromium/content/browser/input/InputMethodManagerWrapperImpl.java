package org.chromium.content.browser.input;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.view.View;
import android.view.inputmethod.CursorAnchorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.InputMethodManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.chromium.content_public.browser.InputMethodManagerWrapper;

public class InputMethodManagerWrapperImpl
  implements InputMethodManagerWrapper
{
  private final Context a;
  
  public InputMethodManagerWrapperImpl(Context paramContext)
  {
    this.a = paramContext;
  }
  
  private InputMethodManager a()
  {
    return (InputMethodManager)this.a.getSystemService("input_method");
  }
  
  public boolean hideSoftInputFromWindow(IBinder paramIBinder, int paramInt, ResultReceiver paramResultReceiver)
  {
    StrictMode.ThreadPolicy localThreadPolicy = StrictMode.allowThreadDiskWrites();
    try
    {
      boolean bool = a().hideSoftInputFromWindow(paramIBinder, paramInt, paramResultReceiver);
      return bool;
    }
    finally
    {
      StrictMode.setThreadPolicy(localThreadPolicy);
    }
  }
  
  public boolean isActive(View paramView)
  {
    return a().isActive(paramView);
  }
  
  public void notifyUserAction()
  {
    if (Build.VERSION.SDK_INT > 23) {
      return;
    }
    InputMethodManager localInputMethodManager = a();
    try
    {
      InputMethodManager.class.getMethod("notifyUserAction", new Class[0]).invoke(localInputMethodManager, new Object[0]);
      return;
    }
    catch (NoSuchMethodException|IllegalAccessException|IllegalArgumentException|InvocationTargetException localNoSuchMethodException) {}
  }
  
  public void restartInput(View paramView)
  {
    a().restartInput(paramView);
  }
  
  public void showSoftInput(View paramView, int paramInt, ResultReceiver paramResultReceiver)
  {
    StrictMode.ThreadPolicy localThreadPolicy = StrictMode.allowThreadDiskWrites();
    try
    {
      a().showSoftInput(paramView, paramInt, paramResultReceiver);
      return;
    }
    finally
    {
      StrictMode.setThreadPolicy(localThreadPolicy);
    }
  }
  
  public void updateCursorAnchorInfo(View paramView, CursorAnchorInfo paramCursorAnchorInfo)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      a().updateCursorAnchorInfo(paramView, paramCursorAnchorInfo);
    }
  }
  
  public void updateExtractedText(View paramView, int paramInt, ExtractedText paramExtractedText)
  {
    a().updateExtractedText(paramView, paramInt, paramExtractedText);
  }
  
  public void updateSelection(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    a().updateSelection(paramView, paramInt1, paramInt2, paramInt3, paramInt4);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\input\InputMethodManagerWrapperImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */