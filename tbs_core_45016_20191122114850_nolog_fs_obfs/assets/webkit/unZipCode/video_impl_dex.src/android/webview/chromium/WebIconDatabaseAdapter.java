package android.webview.chromium;

import android.content.ContentResolver;
import com.tencent.tbs.core.webkit.WebIconDatabase;
import com.tencent.tbs.core.webkit.WebIconDatabase.IconListener;
import org.chromium.android_webview.AwContents;
import org.chromium.base.annotations.UsedByReflection;

public class WebIconDatabaseAdapter
  extends WebIconDatabase
{
  public void bulkRequestIconForPageUrl(ContentResolver paramContentResolver, String paramString, WebIconDatabase.IconListener paramIconListener) {}
  
  public void close() {}
  
  public void open(String paramString) {}
  
  @UsedByReflection("WebCoreProxy.java")
  public void releaseIconForPageUrl(String paramString) {}
  
  public void removeAllIcons() {}
  
  @UsedByReflection("WebCoreProxy.java")
  public void requestIconForPageUrl(String paramString, WebIconDatabase.IconListener paramIconListener) {}
  
  @UsedByReflection("WebCoreProxy.java")
  public void retainIconForPageUrl(String paramString) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\WebIconDatabaseAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */