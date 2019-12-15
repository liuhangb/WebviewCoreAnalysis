package android.webview.chromium;

import android.net.Uri;
import com.tencent.tbs.core.webkit.TokenBindingService;
import com.tencent.tbs.core.webkit.TokenBindingService.TokenBindingKey;
import com.tencent.tbs.core.webkit.ValueCallback;
import java.security.KeyPair;
import org.chromium.android_webview.AwTokenBindingManager;
import org.chromium.base.Callback;

public class TokenBindingManagerAdapter
  extends TokenBindingService
{
  private boolean mEnabled;
  private WebViewChromiumFactoryProvider mProvider;
  private AwTokenBindingManager mTokenBindingManager = new AwTokenBindingManager();
  
  TokenBindingManagerAdapter(WebViewChromiumFactoryProvider paramWebViewChromiumFactoryProvider)
  {
    this.mProvider = paramWebViewChromiumFactoryProvider;
  }
  
  private void startChromiumEngine()
  {
    if (this.mEnabled)
    {
      this.mProvider.startYourEngines(false);
      return;
    }
    throw new IllegalStateException("Token binding is not enabled");
  }
  
  public void deleteAllKeys(ValueCallback<Boolean> paramValueCallback)
  {
    startChromiumEngine();
    this.mTokenBindingManager.deleteAllKeys(CallbackConverter.fromValueCallback(paramValueCallback));
  }
  
  public void deleteKey(Uri paramUri, ValueCallback<Boolean> paramValueCallback)
  {
    startChromiumEngine();
    this.mTokenBindingManager.deleteKey(paramUri, CallbackConverter.fromValueCallback(paramValueCallback));
  }
  
  public void enableTokenBinding()
  {
    if (!this.mProvider.hasStarted())
    {
      this.mEnabled = true;
      this.mTokenBindingManager.enableTokenBinding();
      return;
    }
    throw new IllegalStateException("Token binding cannot be enabled after webview creation");
  }
  
  public void getKey(Uri paramUri, String[] paramArrayOfString, final ValueCallback<TokenBindingService.TokenBindingKey> paramValueCallback)
  {
    startChromiumEngine();
    if ((paramArrayOfString != null) && (paramArrayOfString.length == 0)) {
      throw new IllegalArgumentException("algorithms cannot be empty");
    }
    if (paramArrayOfString != null)
    {
      int m = paramArrayOfString.length;
      int k = 0;
      int i = 0;
      int j;
      for (;;)
      {
        j = k;
        if (i >= m) {
          break;
        }
        if (paramArrayOfString[i].equals("ECDSAP256"))
        {
          j = 1;
          break;
        }
        i += 1;
      }
      if (j == 0) {
        throw new IllegalArgumentException("no supported algorithm found");
      }
    }
    paramArrayOfString = new Callback()
    {
      public void onResult(final KeyPair paramAnonymousKeyPair)
      {
        paramAnonymousKeyPair = new TokenBindingService.TokenBindingKey()
        {
          public String getAlgorithm()
          {
            return "ECDSAP256";
          }
          
          public KeyPair getKeyPair()
          {
            return paramAnonymousKeyPair;
          }
        };
        paramValueCallback.onReceiveValue(paramAnonymousKeyPair);
      }
    };
    this.mTokenBindingManager.getKey(paramUri, null, paramArrayOfString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\TokenBindingManagerAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */