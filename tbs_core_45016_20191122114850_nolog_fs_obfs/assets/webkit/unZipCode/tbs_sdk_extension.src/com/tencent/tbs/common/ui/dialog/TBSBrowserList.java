package com.tencent.tbs.common.ui.dialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import com.tencent.tbs.common.ui.dialog.interfaces.TBSDialog;
import com.tencent.tbs.common.ui.dialog.interfaces.TBSDialogBuilder;
import com.tencent.tbs.common.ui.dialog.interfaces.TBSDialogBuilder.IntentFilterCallback;
import java.util.Map;

public class TBSBrowserList
{
  public static final String STYLE_HW = "hw";
  public static final String STYLE_NORMAL = "normal";
  
  public static void OpenBrowserList(Context paramContext, Intent paramIntent, String paramString, Map<String, Drawable> paramMap, TBSDialogBuilder.IntentFilterCallback paramIntentFilterCallback)
  {
    new TBSDialogBuilder(paramContext).setTitle(paramString).setDrawables(paramMap).setIntentToSend(paramIntent, paramIntentFilterCallback).setFileChooserTag(false).show();
  }
  
  public static void OpenHWBrowserList(Context paramContext, Intent paramIntent, Map<String, Drawable> paramMap, TBSDialogBuilder.IntentFilterCallback paramIntentFilterCallback)
  {
    new TBSHwDialogBuilder(paramContext).setDrawables(paramMap).setIntentToSend(paramIntent, paramIntentFilterCallback).create().show();
  }
  
  public static void OpenHWSearchBrowserList(Context paramContext, Intent paramIntent, Map<String, Drawable> paramMap, TBSDialogBuilder.IntentFilterCallback paramIntentFilterCallback)
  {
    new TBSHwDialogBuilder(paramContext).setDialogForSearch(true).setDrawables(paramMap).setIntentToSend(paramIntent, paramIntentFilterCallback).create().show();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\ui\dialog\TBSBrowserList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */