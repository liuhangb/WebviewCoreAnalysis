package org.chromium.content.browser.webcontents;

import org.chromium.base.ObserverList;
import org.chromium.base.ObserverList.RewindableIterator;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.content_public.browser.WebContentsObserver;

@JNINamespace("content")
class WebContentsObserverProxy
  extends WebContentsObserver
{
  private long jdField_a_of_type_Long;
  private final ObserverList.RewindableIterator<WebContentsObserver> jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator;
  private final ObserverList<WebContentsObserver> jdField_a_of_type_OrgChromiumBaseObserverList;
  
  public WebContentsObserverProxy(WebContentsImpl paramWebContentsImpl)
  {
    ThreadUtils.assertOnUiThread();
    this.jdField_a_of_type_Long = nativeInit(paramWebContentsImpl);
    this.jdField_a_of_type_OrgChromiumBaseObserverList = new ObserverList();
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator = this.jdField_a_of_type_OrgChromiumBaseObserverList.rewindableIterator();
  }
  
  private native void nativeDestroy(long paramLong);
  
  private native long nativeInit(WebContentsImpl paramWebContentsImpl);
  
  void a(WebContentsObserver paramWebContentsObserver)
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_Long == 0L)) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_OrgChromiumBaseObserverList.addObserver(paramWebContentsObserver);
  }
  
  void b(WebContentsObserver paramWebContentsObserver)
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList.removeObserver(paramWebContentsObserver);
  }
  
  @CalledByNative
  public void destroy()
  {
    ThreadUtils.assertOnUiThread();
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((WebContentsObserver)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).destroy();
    }
    if ((!jdField_a_of_type_Boolean) && (!this.jdField_a_of_type_OrgChromiumBaseObserverList.isEmpty())) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_OrgChromiumBaseObserverList.clear();
    long l = this.jdField_a_of_type_Long;
    if (l != 0L)
    {
      nativeDestroy(l);
      this.jdField_a_of_type_Long = 0L;
    }
  }
  
  @CalledByNative
  public void didAttachInterstitialPage()
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((WebContentsObserver)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).didAttachInterstitialPage();
    }
  }
  
  @CalledByNative
  public void didChangeThemeColor(int paramInt)
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((WebContentsObserver)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).didChangeThemeColor(paramInt);
    }
  }
  
  @CalledByNative
  public void didDetachInterstitialPage()
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((WebContentsObserver)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).didDetachInterstitialPage();
    }
  }
  
  @CalledByNative
  public void didFailLoad(boolean paramBoolean, int paramInt, String paramString1, String paramString2)
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((WebContentsObserver)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).didFailLoad(paramBoolean, paramInt, paramString1, paramString2);
    }
  }
  
  @CalledByNative
  public void didFinishLoad(long paramLong, String paramString, boolean paramBoolean)
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((WebContentsObserver)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).didFinishLoad(paramLong, paramString, paramBoolean);
    }
  }
  
  @CalledByNative
  public void didFinishNavigation(String paramString1, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, int paramInt1, int paramInt2, String paramString2, int paramInt3)
  {
    Integer localInteger;
    if (paramInt1 == -1) {
      localInteger = null;
    } else {
      localInteger = Integer.valueOf(paramInt1);
    }
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((WebContentsObserver)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).didFinishNavigation(paramString1, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, paramBoolean5, localInteger, paramInt2, paramString2, paramInt3);
    }
  }
  
  @CalledByNative
  public void didFirstVisuallyNonEmptyPaint()
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((WebContentsObserver)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).didFirstVisuallyNonEmptyPaint();
    }
  }
  
  @CalledByNative
  public void didStartLoading(String paramString)
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((WebContentsObserver)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).didStartLoading(paramString);
    }
  }
  
  @CalledByNative
  public void didStartNavigation(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((WebContentsObserver)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).didStartNavigation(paramString, paramBoolean1, paramBoolean2, paramBoolean3);
    }
  }
  
  @CalledByNative
  public void didStopLoading(String paramString)
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((WebContentsObserver)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).didStopLoading(paramString);
    }
  }
  
  @CalledByNative
  public void documentAvailableInMainFrame()
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((WebContentsObserver)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).documentAvailableInMainFrame();
    }
  }
  
  @CalledByNative
  public void documentLoadedInFrame(long paramLong, boolean paramBoolean)
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((WebContentsObserver)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).documentLoadedInFrame(paramLong, paramBoolean);
    }
  }
  
  @CalledByNative
  public void hasEffectivelyFullscreenVideoChange(boolean paramBoolean)
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((WebContentsObserver)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).hasEffectivelyFullscreenVideoChange(paramBoolean);
    }
  }
  
  @CalledByNative
  public void navigationEntriesDeleted()
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((WebContentsObserver)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).navigationEntriesDeleted();
    }
  }
  
  @CalledByNative
  public void navigationEntryCommitted()
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((WebContentsObserver)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).navigationEntryCommitted();
    }
  }
  
  @CalledByNative
  public void renderProcessGone(boolean paramBoolean)
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((WebContentsObserver)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).renderProcessGone(paramBoolean);
    }
  }
  
  @CalledByNative
  public void renderViewReady()
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((WebContentsObserver)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).renderViewReady();
    }
  }
  
  @CalledByNative
  public void titleWasSet(String paramString)
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((WebContentsObserver)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).titleWasSet(paramString);
    }
  }
  
  @CalledByNative
  public void viewportFitChanged(int paramInt)
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((WebContentsObserver)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).viewportFitChanged(paramInt);
    }
  }
  
  @CalledByNative
  public void wasHidden()
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((WebContentsObserver)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).wasHidden();
    }
  }
  
  @CalledByNative
  public void wasShown()
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.rewind();
    while (this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.hasNext()) {
      ((WebContentsObserver)this.jdField_a_of_type_OrgChromiumBaseObserverList$RewindableIterator.next()).wasShown();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\webcontents\WebContentsObserverProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */