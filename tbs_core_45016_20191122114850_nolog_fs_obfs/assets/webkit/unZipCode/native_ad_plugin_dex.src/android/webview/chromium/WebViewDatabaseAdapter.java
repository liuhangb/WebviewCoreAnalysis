package android.webview.chromium;

import com.tencent.tbs.core.webkit.WebViewDatabase;
import java.util.concurrent.Callable;
import org.chromium.android_webview.AwFormDatabase;
import org.chromium.android_webview.HttpAuthDatabase;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.UsedByReflection;

public class WebViewDatabaseAdapter
  extends WebViewDatabase
{
  private final WebViewChromiumFactoryProvider mFactory;
  private final HttpAuthDatabase mHttpAuthDatabase;
  
  public WebViewDatabaseAdapter(WebViewChromiumFactoryProvider paramWebViewChromiumFactoryProvider, HttpAuthDatabase paramHttpAuthDatabase)
  {
    this.mFactory = paramWebViewChromiumFactoryProvider;
    this.mHttpAuthDatabase = paramHttpAuthDatabase;
  }
  
  private static boolean checkNeedsPost()
  {
    return ThreadUtils.runningOnUiThread() ^ true;
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void clearFormData()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run() {}
      });
      return;
    }
    AwFormDatabase.clearFormData();
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void clearHttpAuthUsernamePassword()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewDatabaseAdapter.this.mHttpAuthDatabase.clearHttpAuthUsernamePassword();
        }
      });
      return;
    }
    this.mHttpAuthDatabase.clearHttpAuthUsernamePassword();
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void clearUsernamePassword() {}
  
  public String[] getHttpAuthUsernamePassword(final String paramString1, final String paramString2)
  {
    if (checkNeedsPost()) {
      (String[])this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public String[] call()
        {
          return WebViewDatabaseAdapter.this.mHttpAuthDatabase.getHttpAuthUsernamePassword(paramString1, paramString2);
        }
      });
    }
    return this.mHttpAuthDatabase.getHttpAuthUsernamePassword(paramString1, paramString2);
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public boolean hasFormData()
  {
    if (checkNeedsPost()) {
      ((Boolean)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Boolean call()
        {
          return Boolean.valueOf(AwFormDatabase.hasFormData());
        }
      })).booleanValue();
    }
    return AwFormDatabase.hasFormData();
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public boolean hasHttpAuthUsernamePassword()
  {
    if (checkNeedsPost()) {
      ((Boolean)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Boolean call()
        {
          return Boolean.valueOf(WebViewDatabaseAdapter.this.mHttpAuthDatabase.hasHttpAuthUsernamePassword());
        }
      })).booleanValue();
    }
    return this.mHttpAuthDatabase.hasHttpAuthUsernamePassword();
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public boolean hasUsernamePassword()
  {
    return false;
  }
  
  public void setHttpAuthUsernamePassword(final String paramString1, final String paramString2, final String paramString3, final String paramString4)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewDatabaseAdapter.this.mHttpAuthDatabase.setHttpAuthUsernamePassword(paramString1, paramString2, paramString3, paramString4);
        }
      });
      return;
    }
    this.mHttpAuthDatabase.setHttpAuthUsernamePassword(paramString1, paramString2, paramString3, paramString4);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\WebViewDatabaseAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */