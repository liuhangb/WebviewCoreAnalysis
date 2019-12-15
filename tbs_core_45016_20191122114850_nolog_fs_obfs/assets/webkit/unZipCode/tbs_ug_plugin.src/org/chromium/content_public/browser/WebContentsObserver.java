package org.chromium.content_public.browser;

import android.support.annotation.Nullable;
import java.lang.ref.WeakReference;

public abstract class WebContentsObserver
{
  protected WeakReference<WebContents> a;
  
  protected WebContentsObserver() {}
  
  public WebContentsObserver(WebContents paramWebContents)
  {
    this.a = new WeakReference(paramWebContents);
    paramWebContents.addObserver(this);
  }
  
  public void destroy()
  {
    Object localObject = this.a;
    if (localObject == null) {
      return;
    }
    localObject = (WebContents)((WeakReference)localObject).get();
    this.a = null;
    if (localObject == null) {
      return;
    }
    ((WebContents)localObject).removeObserver(this);
  }
  
  public void didAttachInterstitialPage() {}
  
  public void didChangeThemeColor(int paramInt) {}
  
  public void didDetachInterstitialPage() {}
  
  public void didFailLoad(boolean paramBoolean, int paramInt, String paramString1, String paramString2) {}
  
  public void didFinishLoad(long paramLong, String paramString, boolean paramBoolean) {}
  
  public void didFinishNavigation(String paramString1, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, @Nullable Integer paramInteger, int paramInt1, String paramString2, int paramInt2) {}
  
  public void didFirstVisuallyNonEmptyPaint() {}
  
  public void didStartLoading(String paramString) {}
  
  public void didStartNavigation(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {}
  
  public void didStopLoading(String paramString) {}
  
  public void documentAvailableInMainFrame() {}
  
  public void documentLoadedInFrame(long paramLong, boolean paramBoolean) {}
  
  public void hasEffectivelyFullscreenVideoChange(boolean paramBoolean) {}
  
  public void navigationEntriesDeleted() {}
  
  public void navigationEntryCommitted() {}
  
  public void renderProcessGone(boolean paramBoolean) {}
  
  public void renderViewReady() {}
  
  public void titleWasSet(String paramString) {}
  
  public void viewportFitChanged(int paramInt) {}
  
  public void wasHidden() {}
  
  public void wasShown() {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content_public\browser\WebContentsObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */