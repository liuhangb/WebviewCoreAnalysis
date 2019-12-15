package com.tencent.tbs.common.ui.dialog;

import android.content.Context;
import android.net.Uri;
import android.webkit.ValueCallback;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.tbs.common.ui.dialog.interfaces.TBSDialogBuilder;

public class TBSFileChooser
{
  public static void OpenTBSFileChooser(Context paramContext, IX5WebViewBase paramIX5WebViewBase, ValueCallback<Uri[]> paramValueCallback, int paramInt)
  {
    new TBSDialogBuilder(paramContext).setFileChooserTag(true).setIX5WebViewBase(paramIX5WebViewBase).setFileChooserCallback(paramValueCallback).setFileChooserParamsMode(paramInt).show();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\ui\dialog\TBSFileChooser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */