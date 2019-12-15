package com.tencent.tbs.core.partner.filechooser;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.webview.chromium.tencent.TencentWebViewContentsClientAdapter;
import com.tencent.tbs.core.webkit.ValueCallback;

public class FirstScreenViewBackGround
  extends View
{
  private TencentWebViewContentsClientAdapter mTencentWebViewContentsClientAdapter;
  private ValueCallback<Uri[]> mUploadFileCallback;
  
  public FirstScreenViewBackGround(Context paramContext, ValueCallback<Uri[]> paramValueCallback, TencentWebViewContentsClientAdapter paramTencentWebViewContentsClientAdapter)
  {
    super(paramContext);
    this.mUploadFileCallback = paramValueCallback;
    this.mTencentWebViewContentsClientAdapter = paramTencentWebViewContentsClientAdapter;
    setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (FirstScreenViewBackGround.this.mUploadFileCallback != null) {
          FirstScreenViewBackGround.this.mUploadFileCallback.onReceiveValue(null);
        }
        FirstScreenViewBackGround.this.mTencentWebViewContentsClientAdapter.onClickFirstScreenViewBackGround();
      }
    });
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\filechooser\FirstScreenViewBackGround.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */