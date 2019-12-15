package com.tencent.mtt.external.reader.utils;

import android.content.Context;
import android.os.Build.VERSION;

public class ClipboardMangerAdapter
{
  public static void setText(Context paramContext, String paramString)
  {
    if (Build.VERSION.SDK_INT >= 11)
    {
      ((android.content.ClipboardManager)paramContext.getSystemService("clipboard")).setText(paramString);
      return;
    }
    ((android.text.ClipboardManager)paramContext.getSystemService("clipboard")).setText(paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\utils\ClipboardMangerAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */