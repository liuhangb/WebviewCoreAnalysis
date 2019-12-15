package com.tencent.smtt.net;

import android.content.Context;
import android.webkit.JavascriptInterface;

public class SWTools
{
  Context jdField_a_of_type_AndroidContentContext = null;
  private final String jdField_a_of_type_JavaLangString = "SERVICEWORKER";
  
  public SWTools(Context paramContext)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext;
  }
  
  @JavascriptInterface
  public void clearAllCache() {}
  
  @JavascriptInterface
  public void clearCacheExceptSW() {}
  
  @JavascriptInterface
  public void clearCacheOnlySW() {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\net\SWTools.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */