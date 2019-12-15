package android.webview.chromium.tencent;

import android.webkit.ValueCallback;
import org.chromium.base.Callback;

public final class TencentCallbackConverter
{
  public static <T> Callback<T> fromValueCallback(ValueCallback<T> paramValueCallback)
  {
    if (paramValueCallback == null) {
      return null;
    }
    new Callback()
    {
      public void onResult(T paramAnonymousT)
      {
        this.val$valueCallback.onReceiveValue(paramAnonymousT);
      }
    };
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\tencent\TencentCallbackConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */