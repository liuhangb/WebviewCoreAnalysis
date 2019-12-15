package com.tencent.tbs.common.utils;

import android.os.Bundle;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;

public class MiniProgramUtil
{
  public static boolean isInMiniProgram(IX5WebViewBase paramIX5WebViewBase)
  {
    if (paramIX5WebViewBase != null) {}
    int i;
    label115:
    do
    {
      try
      {
        if (paramIX5WebViewBase.getX5WebViewExtension() == null) {
          break label115;
        }
        Object localObject = paramIX5WebViewBase.getX5WebViewExtension().invokeMiscMethod("getMiniProgramFlag", new Bundle());
        if (!(localObject instanceof Integer)) {
          break label115;
        }
        i = ((Integer)localObject).intValue();
      }
      catch (Exception paramIX5WebViewBase)
      {
        boolean bool;
        for (;;) {}
      }
      if ((i == -1) && (paramIX5WebViewBase != null) && (paramIX5WebViewBase.getSettings() != null) && (paramIX5WebViewBase.getSettings().getUserAgentString() != null))
      {
        bool = paramIX5WebViewBase.getSettings().getUserAgentString().contains("miniProgram");
        if (bool) {
          return true;
        }
      }
      return false;
      do
      {
        return true;
        i = -1;
      } while (i == 1);
    } while (i != 2);
    return true;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\utils\MiniProgramUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */