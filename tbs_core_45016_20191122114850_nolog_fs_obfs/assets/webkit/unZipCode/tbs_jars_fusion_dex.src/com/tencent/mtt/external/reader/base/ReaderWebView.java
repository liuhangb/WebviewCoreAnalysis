package com.tencent.mtt.external.reader.base;

import android.content.Context;

public class ReaderWebView
  extends ReaderTypeView
{
  public static final String READER_URL_IDENTIFIER = "?from_file_reader=1";
  public Context mContext = null;
  
  public ReaderWebView(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  public void activityPause() {}
  
  public void addJSI(Object paramObject, String paramString) {}
  
  public void clearMatches() {}
  
  public void findAllAsync(String paramString) {}
  
  public void findNext(boolean paramBoolean) {}
  
  public void loadUrl(String paramString) {}
  
  public void setBackgroundColor(int paramInt) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\base\ReaderWebView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */