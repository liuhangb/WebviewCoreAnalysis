package com.tencent.tbs.core.partner.clipboard;

import android.content.Context;
import android.os.Build.VERSION;
import org.chromium.tencent.ui.base.PartnerClipboardProxy;
import org.chromium.tencent.ui.base.PartnerClipboardProxy.IPartnerClipboardListener;

public class PartnerClipboard
{
  private static PartnerClipboard instance;
  private static boolean isTbsClipBoardEnabled = true;
  private static boolean isThirdPartyApp = false;
  private static boolean mIsSystemClipBoardNewest = true;
  private static String mX5ClipboardText;
  
  public static boolean canPaste(Context paramContext)
  {
    try
    {
      if (Build.VERSION.SDK_INT >= 11) {
        return ((android.content.ClipboardManager)paramContext.getSystemService("clipboard")).hasPrimaryClip();
      }
      boolean bool = ((android.text.ClipboardManager)paramContext.getSystemService("clipboard")).hasText();
      return bool;
    }
    catch (Throwable paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public static void clear(Context paramContext)
  {
    setText(paramContext, "");
  }
  
  public static String getText(Context paramContext)
  {
    try
    {
      if ((!isThirdPartyApp) && (isTbsClipBoardEnabled))
      {
        if (mX5ClipboardText == null) {
          getTextFromSysClipBoard(paramContext);
        }
        return mX5ClipboardText;
      }
      if (Build.VERSION.SDK_INT >= 11)
      {
        paramContext = (android.content.ClipboardManager)paramContext.getSystemService("clipboard");
        if (paramContext.getText() == null) {
          return "";
        }
        return paramContext.getText().toString();
      }
      paramContext = (android.text.ClipboardManager)paramContext.getSystemService("clipboard");
      if (paramContext.getText() == null) {
        return "";
      }
      paramContext = paramContext.getText().toString();
      return paramContext;
    }
    catch (Throwable paramContext)
    {
      paramContext.printStackTrace();
    }
    return "";
  }
  
  public static void getTextFromSysClipBoard(Context paramContext)
  {
    try
    {
      boolean bool = isThirdPartyApp;
      if (bool) {
        return;
      }
      bool = isTbsClipBoardEnabled;
      if (!bool) {
        return;
      }
      bool = mIsSystemClipBoardNewest;
      if (!bool) {
        return;
      }
      try
      {
        if (Build.VERSION.SDK_INT >= 11)
        {
          paramContext = (android.content.ClipboardManager)paramContext.getSystemService("clipboard");
          if (paramContext.getText() == null) {
            paramContext = "";
          } else {
            paramContext = paramContext.getText().toString();
          }
          mX5ClipboardText = paramContext;
        }
        else
        {
          paramContext = (android.text.ClipboardManager)paramContext.getSystemService("clipboard");
          if (paramContext.getText() == null) {
            paramContext = "";
          } else {
            paramContext = paramContext.getText().toString();
          }
          mX5ClipboardText = paramContext;
        }
      }
      catch (Throwable paramContext)
      {
        paramContext.printStackTrace();
      }
      return;
    }
    finally {}
  }
  
  public static void initClipboardListener()
  {
    PartnerClipboardProxy.getInstance().setIPartnerClipboardListener(new PartnerClipboardProxy.IPartnerClipboardListener()
    {
      public void onClear(Context paramAnonymousContext)
      {
        PartnerClipboard.clear(paramAnonymousContext);
      }
      
      public String onGetCoercedText(Context paramAnonymousContext)
      {
        return PartnerClipboard.getText(paramAnonymousContext);
      }
      
      public void onSetText(Context paramAnonymousContext, String paramAnonymousString)
      {
        PartnerClipboard.setText(paramAnonymousContext, paramAnonymousString);
      }
    });
  }
  
  public static void setIsSystemClipBoardNewest(boolean paramBoolean)
  {
    try
    {
      mIsSystemClipBoardNewest = paramBoolean;
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static void setIsTbsClipBoardEnabled(boolean paramBoolean)
  {
    isTbsClipBoardEnabled = paramBoolean;
  }
  
  public static void setIsThirdPartyApp(boolean paramBoolean)
  {
    isThirdPartyApp = paramBoolean;
  }
  
  public static void setText(Context paramContext, String paramString)
  {
    try
    {
      if ((!isThirdPartyApp) && (isTbsClipBoardEnabled))
      {
        setIsSystemClipBoardNewest(false);
        mX5ClipboardText = paramString;
        return;
      }
      if (Build.VERSION.SDK_INT >= 11)
      {
        ((android.content.ClipboardManager)paramContext.getSystemService("clipboard")).setText(paramString);
        return;
      }
      ((android.text.ClipboardManager)paramContext.getSystemService("clipboard")).setText(paramString);
      return;
    }
    catch (Throwable paramContext)
    {
      paramContext.printStackTrace();
    }
  }
  
  public static void setTextToSysClipBoard(Context paramContext)
  {
    try
    {
      boolean bool = isThirdPartyApp;
      if (bool) {
        return;
      }
      bool = isTbsClipBoardEnabled;
      if (!bool) {
        return;
      }
      Object localObject = mX5ClipboardText;
      if (localObject == null) {
        return;
      }
      bool = mIsSystemClipBoardNewest;
      if (bool) {
        return;
      }
      try
      {
        if (Build.VERSION.SDK_INT >= 11)
        {
          localObject = (android.content.ClipboardManager)paramContext.getSystemService("clipboard");
          if (((android.content.ClipboardManager)localObject).getText() == null) {
            paramContext = "";
          } else {
            paramContext = ((android.content.ClipboardManager)localObject).getText().toString();
          }
          if (!mX5ClipboardText.equals(paramContext)) {
            ((android.content.ClipboardManager)localObject).setText(mX5ClipboardText);
          }
        }
        else
        {
          localObject = (android.text.ClipboardManager)paramContext.getSystemService("clipboard");
          if (((android.text.ClipboardManager)localObject).getText() == null) {
            paramContext = "";
          } else {
            paramContext = ((android.text.ClipboardManager)localObject).getText().toString();
          }
          if (!mX5ClipboardText.equals(paramContext)) {
            ((android.text.ClipboardManager)localObject).setText(mX5ClipboardText);
          }
        }
        mX5ClipboardText = null;
      }
      catch (Throwable paramContext)
      {
        paramContext.printStackTrace();
      }
      catch (IllegalStateException paramContext)
      {
        paramContext.printStackTrace();
      }
      return;
    }
    finally {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\clipboard\PartnerClipboard.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */