package org.chromium.components.web_contents_delegate_android;

import android.view.KeyEvent;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.content.browser.ContentVideoViewEmbedder;
import org.chromium.content_public.browser.WebContents;
import org.chromium.content_public.common.ResourceRequestBody;

@JNINamespace("web_contents_delegate_android")
public class WebContentsDelegateAndroid
{
  public static final int LOG_LEVEL_ERROR = 3;
  public static final int LOG_LEVEL_LOG = 1;
  public static final int LOG_LEVEL_TIP = 0;
  public static final int LOG_LEVEL_WARNING = 2;
  private int mMostRecentProgress = 100;
  
  @CalledByNative
  private final void notifyLoadProgressChanged(double paramDouble)
  {
    this.mMostRecentProgress = ((int)(paramDouble * 100.0D));
    onLoadProgressChanged(this.mMostRecentProgress);
  }
  
  @CalledByNative
  public void activateContents() {}
  
  @CalledByNative
  public boolean addMessageToConsole(int paramInt1, String paramString1, int paramInt2, String paramString2)
  {
    return false;
  }
  
  @CalledByNative
  public void closeContents() {}
  
  @CalledByNative
  public boolean controlsResizeView()
  {
    return false;
  }
  
  @CalledByNative
  public int getBottomControlsHeight()
  {
    return 0;
  }
  
  @CalledByNative
  public ContentVideoViewEmbedder getContentVideoViewEmbedder()
  {
    return null;
  }
  
  public int getMostRecentProgress()
  {
    return this.mMostRecentProgress;
  }
  
  @CalledByNative
  public int getTopControlsHeight()
  {
    return 0;
  }
  
  @CalledByNative
  public void handleKeyboardEvent(KeyEvent paramKeyEvent) {}
  
  @CalledByNative
  public boolean isFullscreenForTabOrPending()
  {
    return false;
  }
  
  @CalledByNative
  public void loadingStateChanged(boolean paramBoolean) {}
  
  @CalledByNative
  public void navigationStateChanged(int paramInt) {}
  
  public void onLoadProgressChanged(int paramInt) {}
  
  @CalledByNative
  public void onUpdateUrl(String paramString) {}
  
  @CalledByNative
  public void openNewTab(String paramString1, String paramString2, ResourceRequestBody paramResourceRequestBody, int paramInt, boolean paramBoolean) {}
  
  @CalledByNative
  public void rendererResponsive() {}
  
  @CalledByNative
  public void rendererUnresponsive() {}
  
  @CalledByNative
  public boolean shouldBlockMediaRequest(String paramString)
  {
    return false;
  }
  
  @CalledByNative
  public boolean shouldCreateWebContents(String paramString)
  {
    return true;
  }
  
  @CalledByNative
  public void showRepostFormWarningDialog() {}
  
  @CalledByNative
  public boolean takeFocus(boolean paramBoolean)
  {
    return false;
  }
  
  @CalledByNative
  public void toggleFullscreenModeForTab(boolean paramBoolean) {}
  
  @CalledByNative
  public void visibleSSLStateChanged() {}
  
  @CalledByNative
  public void webContentsCreated(WebContents paramWebContents1, long paramLong1, long paramLong2, String paramString1, String paramString2, WebContents paramWebContents2) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\components\web_contents_delegate_android\WebContentsDelegateAndroid.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */