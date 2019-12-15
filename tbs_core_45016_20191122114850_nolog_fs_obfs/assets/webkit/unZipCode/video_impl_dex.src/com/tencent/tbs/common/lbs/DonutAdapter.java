package com.tencent.tbs.common.lbs;

import android.annotation.TargetApi;
import android.content.SharedPreferences.Editor;

public class DonutAdapter
{
  @TargetApi(9)
  public static void editorApply(SharedPreferences.Editor paramEditor)
  {
    try
    {
      paramEditor.apply();
      return;
    }
    catch (Throwable paramEditor) {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\lbs\DonutAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */