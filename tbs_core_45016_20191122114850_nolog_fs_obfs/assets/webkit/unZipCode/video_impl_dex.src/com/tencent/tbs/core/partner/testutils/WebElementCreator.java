package com.tencent.tbs.core.partner.testutils;

import android.os.SystemClock;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

class WebElementCreator
{
  private boolean isFinished = false;
  private List<WebElement> webElements = new CopyOnWriteArrayList();
  
  private WebElement createWebElementAndSetLocation(String paramString, TencentWebViewProxy paramTencentWebViewProxy)
  {
    String[] arrayOfString1 = paramString.split(";,");
    Hashtable localHashtable = new Hashtable();
    for (;;)
    {
      int i;
      int n;
      int m;
      try
      {
        i = Math.round(Float.valueOf(arrayOfString1[5]).floatValue());
      }
      catch (Exception paramString)
      {
        int j;
        int k;
        continue;
      }
      try
      {
        j = Math.round(Float.valueOf(arrayOfString1[6]).floatValue());
      }
      catch (Exception paramString)
      {
        continue;
      }
      try
      {
        k = Math.round(Float.valueOf(arrayOfString1[7]).floatValue());
      }
      catch (Exception paramString)
      {
        continue;
      }
      try
      {
        n = Math.round(Float.valueOf(arrayOfString1[8]).floatValue());
      }
      catch (Exception paramString)
      {
        m = i;
        continue;
      }
      try
      {
        paramString = arrayOfString1[9].split("\\#\\$");
        m = i;
        i = j;
        j = k;
        k = n;
      }
      catch (Exception paramString)
      {
        m = i;
        i = n;
      }
    }
    i = 0;
    j = 0;
    k = 0;
    m = i;
    i = 0;
    n = j;
    j = k;
    paramString = null;
    k = i;
    i = n;
    if (paramString != null)
    {
      n = 0;
      while (n < paramString.length)
      {
        String[] arrayOfString2 = paramString[n].split("::");
        if (arrayOfString2.length > 1) {
          localHashtable.put(arrayOfString2[0], arrayOfString2[1]);
        } else {
          localHashtable.put(arrayOfString2[0], arrayOfString2[0]);
        }
        n += 1;
      }
    }
    try
    {
      paramString = new WebElement(arrayOfString1[0], arrayOfString1[1], arrayOfString1[2], arrayOfString1[3], arrayOfString1[4], localHashtable);
    }
    catch (Exception paramString)
    {
      for (;;) {}
    }
    try
    {
      setLocation(paramString, paramTencentWebViewProxy, m, i, j, k);
      return paramString;
    }
    catch (Exception paramTencentWebViewProxy) {}
    return null;
    return paramString;
  }
  
  private void setLocation(WebElement paramWebElement, TencentWebViewProxy paramTencentWebViewProxy, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    float f = paramTencentWebViewProxy.getRealWebView().getScale();
    int[] arrayOfInt = new int[2];
    paramTencentWebViewProxy.getRealWebView().getLocationOnScreen(arrayOfInt);
    double d2 = arrayOfInt[0];
    double d3 = paramInt1;
    double d4 = Math.floor(paramInt3 / 2);
    Double.isNaN(d3);
    double d1 = f;
    Double.isNaN(d1);
    Double.isNaN(d2);
    paramInt1 = (int)(d2 + (d3 + d4) * d1);
    d2 = arrayOfInt[1];
    d3 = paramInt2;
    d4 = Math.floor(paramInt4 / 2);
    Double.isNaN(d3);
    Double.isNaN(d1);
    Double.isNaN(d2);
    paramInt2 = (int)(d2 + (d3 + d4) * d1);
    paramWebElement.setLocationX(paramInt1);
    paramWebElement.setLocationY(paramInt2);
  }
  
  private boolean waitForWebElementsToBeCreated()
  {
    long l = SystemClock.uptimeMillis();
    while (SystemClock.uptimeMillis() < l + 5000L)
    {
      if (this.isFinished) {
        return true;
      }
      try
      {
        Thread.sleep(1000L);
      }
      catch (InterruptedException localInterruptedException)
      {
        localInterruptedException.printStackTrace();
      }
    }
    return false;
  }
  
  public void createWebElementAndAddInList(String paramString, TencentWebViewProxy paramTencentWebViewProxy)
  {
    paramString = createWebElementAndSetLocation(paramString, paramTencentWebViewProxy);
    if (paramString != null) {
      this.webElements.add(paramString);
    }
  }
  
  public ArrayList<WebElement> getWebElementsFromWebViews()
  {
    waitForWebElementsToBeCreated();
    return new ArrayList(this.webElements);
  }
  
  public boolean isFinished()
  {
    return this.isFinished;
  }
  
  public void prepareForStart()
  {
    setFinished(false);
    this.webElements.clear();
  }
  
  public void setFinished(boolean paramBoolean)
  {
    this.isFinished = paramBoolean;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\testutils\WebElementCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */