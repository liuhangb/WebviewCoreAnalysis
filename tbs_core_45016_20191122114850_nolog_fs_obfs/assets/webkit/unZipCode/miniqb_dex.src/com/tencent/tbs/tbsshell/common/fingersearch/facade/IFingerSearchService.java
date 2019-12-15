package com.tencent.tbs.tbsshell.common.fingersearch.facade;

import com.tencent.smtt.export.internal.interfaces.IX5WebView;

public abstract interface IFingerSearchService
{
  public abstract void build(IX5WebView paramIX5WebView);
  
  public abstract void fingerSearchRequest(String paramString1, int paramInt1, String paramString2, int paramInt2, int[] paramArrayOfInt, String paramString3, String paramString4);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\fingersearch\facade\IFingerSearchService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */