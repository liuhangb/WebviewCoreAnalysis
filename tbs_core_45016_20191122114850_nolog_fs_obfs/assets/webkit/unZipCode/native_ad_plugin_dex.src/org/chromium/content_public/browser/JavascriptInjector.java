package org.chromium.content_public.browser;

import android.util.Pair;
import java.lang.annotation.Annotation;
import java.util.Map;
import org.chromium.content.browser.JavascriptInjectorImpl;

public abstract class JavascriptInjector
{
  public static JavascriptInjector fromWebContents(WebContents paramWebContents)
  {
    return JavascriptInjectorImpl.fromWebContents(paramWebContents);
  }
  
  public abstract void addPossiblyUnsafeInterface(Object paramObject, String paramString, Class<? extends Annotation> paramClass);
  
  public abstract Map<String, Pair<Object, Class>> getInterfaces();
  
  public abstract void injectJavascript(String paramString);
  
  public abstract void removeInterface(String paramString);
  
  public abstract void setAllowInspection(boolean paramBoolean);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content_public\browser\JavascriptInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */