package org.chromium.tencent;

import android.content.Context;
import android.view.View;
import org.chromium.android_webview.AwContents;
import org.chromium.android_webview.AwContentsClient;
import org.chromium.android_webview.AwSettings;
import org.chromium.android_webview.AwWebContentsDelegateAdapter;

public class TencentAwWebContentsDelegateAdapter
  extends AwWebContentsDelegateAdapter
{
  private AwContentsClientExtension mContentsClientExtension;
  
  public TencentAwWebContentsDelegateAdapter(AwContents paramAwContents, AwContentsClient paramAwContentsClient, AwSettings paramAwSettings, Context paramContext, View paramView)
  {
    super(paramAwContents, paramAwContentsClient, paramAwSettings, paramContext, paramView);
  }
  
  public boolean controlsResizeView()
  {
    AwContentsClientExtension localAwContentsClientExtension = this.mContentsClientExtension;
    if (localAwContentsClientExtension != null) {
      return localAwContentsClientExtension.controlsResizeView();
    }
    return false;
  }
  
  public int getTopControlsHeight()
  {
    AwContentsClientExtension localAwContentsClientExtension = this.mContentsClientExtension;
    if (localAwContentsClientExtension != null) {
      return localAwContentsClientExtension.getBrowserControlsHeight();
    }
    return 0;
  }
  
  public void setAwContentsClientExtension(AwContentsClientExtension paramAwContentsClientExtension)
  {
    this.mContentsClientExtension = paramAwContentsClientExtension;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\TencentAwWebContentsDelegateAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */