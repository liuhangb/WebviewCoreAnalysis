package com.tencent.tbs.core.partner.menu;

import android.content.Context;
import android.widget.TextView;

public class X5CopyButton
  extends TextView
{
  public static final int BTN_ID_COLLECT = 128;
  public static final int BTN_ID_COPY = 1;
  public static final int BTN_ID_CUT = 8;
  public static final int BTN_ID_EXPLORER = 4096;
  public static final int BTN_ID_OPEN_IN_BROWSER = 1024;
  public static final int BTN_ID_OPEN_URL = 512;
  public static final int BTN_ID_PASTE = 16;
  public static final int BTN_ID_RULE = 2048;
  public static final int BTN_ID_SEARCH = 2;
  public static final int BTN_ID_SELECT = 32;
  public static final int BTN_ID_SELECT_ALL = 64;
  public static final int BTN_ID_SHARE = 4;
  public static final int BTN_ID_TRANSLATE = 256;
  public static final int USER_SETTINGS_COLLECT = 4;
  public static final int USER_SETTINGS_SEARCH = 1;
  public static final int USER_SETTINGS_SHARE = 2;
  public static final int USER_SETTINGS_TRANSLATE = 8;
  public int mID;
  
  public X5CopyButton(Context paramContext, int paramInt)
  {
    super(paramContext);
    this.mID = paramInt;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\menu\X5CopyButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */