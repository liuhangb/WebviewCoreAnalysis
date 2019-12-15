package android.webview.chromium.tencent;

import android.os.Build.VERSION;
import android.view.View;
import android.webview.chromium.DrawGLFunctor;
import android.webview.chromium.WebViewDelegateFactory.WebViewDelegate;

public class TencentDrawGLFunctor
  extends DrawGLFunctor
{
  public TencentDrawGLFunctor(long paramLong, WebViewDelegateFactory.WebViewDelegate paramWebViewDelegate)
  {
    super(paramLong, paramWebViewDelegate);
  }
  
  public boolean requestInvokeGL(View paramView, boolean paramBoolean)
  {
    if ((Build.VERSION.SDK_INT <= 19) && (paramBoolean)) {
      return false;
    }
    return super.requestInvokeGL(paramView, paramBoolean);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\tencent\TencentDrawGLFunctor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */