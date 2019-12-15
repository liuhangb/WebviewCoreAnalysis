package com.tencent.tbs.core.partner.clipboard;

import android.content.Context;
import org.chromium.base.annotations.UsedByReflection;

public class ClipboardMangerAdapter
{
  @UsedByReflection("MiniQB")
  public static String getText(Context paramContext)
  {
    return PartnerClipboard.getText(paramContext);
  }
  
  @UsedByReflection("MiniQB")
  public static void getTextFromSysClipBoard(Context paramContext)
  {
    try
    {
      PartnerClipboard.getTextFromSysClipBoard(paramContext);
      return;
    }
    finally
    {
      paramContext = finally;
      throw paramContext;
    }
  }
  
  public static void setIsSystemClipBoardNewest(boolean paramBoolean)
  {
    try
    {
      PartnerClipboard.setIsSystemClipBoardNewest(paramBoolean);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  @UsedByReflection("MiniQB")
  public static void setText(Context paramContext, String paramString)
  {
    PartnerClipboard.setText(paramContext, paramString);
  }
  
  @UsedByReflection("MiniQB")
  public static void setTextToSysClipBoard(Context paramContext)
  {
    try
    {
      PartnerClipboard.setTextToSysClipBoard(paramContext);
      return;
    }
    finally
    {
      paramContext = finally;
      throw paramContext;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\clipboard\ClipboardMangerAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */