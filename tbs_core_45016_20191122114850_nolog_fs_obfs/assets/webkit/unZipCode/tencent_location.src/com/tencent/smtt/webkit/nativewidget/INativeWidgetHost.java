package com.tencent.smtt.webkit.nativewidget;

import android.location.Location;
import android.webkit.ValueCallback;

public abstract interface INativeWidgetHost
{
  public abstract void evaluateJavascript(String paramString1, String paramString2);
  
  public abstract void setLocationChangedCallback(ValueCallback<Location> paramValueCallback);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\nativewidget\INativeWidgetHost.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */