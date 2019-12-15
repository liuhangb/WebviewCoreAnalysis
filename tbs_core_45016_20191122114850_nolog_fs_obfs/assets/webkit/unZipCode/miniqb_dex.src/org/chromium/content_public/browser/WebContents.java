package org.chromium.content_public.browser;

import android.graphics.Rect;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import org.chromium.base.VisibleForTesting;
import org.chromium.ui.OverscrollRefreshHandler;
import org.chromium.ui.base.EventForwarder;
import org.chromium.ui.base.WindowAndroid;

public abstract interface WebContents
  extends Parcelable
{
  public abstract void addMessageToDevToolsConsole(int paramInt, String paramString);
  
  public abstract void addObserver(WebContentsObserver paramWebContentsObserver);
  
  public abstract void adjustSelectionByCharacterOffset(int paramInt1, int paramInt2, boolean paramBoolean);
  
  public abstract void collapseSelection();
  
  public abstract void copy();
  
  public abstract MessagePort[] createMessageChannel();
  
  public abstract void cut();
  
  public abstract void destroy();
  
  public abstract void dismissTextHandles();
  
  public abstract int downloadImage(String paramString, boolean paramBoolean1, int paramInt, boolean paramBoolean2, ImageDownloadCallback paramImageDownloadCallback);
  
  public abstract void evaluateJavaScript(String paramString, JavaScriptCallback paramJavaScriptCallback);
  
  @VisibleForTesting
  public abstract void evaluateJavaScriptForTests(String paramString, JavaScriptCallback paramJavaScriptCallback);
  
  public abstract void exitFullscreen();
  
  public abstract boolean focusLocationBarByDefault();
  
  public abstract int getBackgroundColor();
  
  public abstract void getContentBitmapAsync(int paramInt1, int paramInt2, ContentBitmapCallback paramContentBitmapCallback);
  
  public abstract String getEncoding();
  
  public abstract EventForwarder getEventForwarder();
  
  @Nullable
  public abstract Rect getFullscreenVideoSize();
  
  public abstract int getHeight();
  
  public abstract String getLastCommittedUrl();
  
  public abstract RenderFrameHost getMainFrame();
  
  public abstract NavigationController getNavigationController();
  
  public abstract int getThemeColor();
  
  public abstract String getTitle();
  
  public abstract WindowAndroid getTopLevelNativeWindow();
  
  public abstract String getVisibleUrl();
  
  public abstract int getWidth();
  
  public abstract boolean hasAccessedInitialDocument();
  
  public abstract boolean hasActiveEffectivelyFullscreenVideo();
  
  public abstract boolean isDestroyed();
  
  public abstract boolean isIncognito();
  
  public abstract boolean isLoading();
  
  public abstract boolean isLoadingToDifferentDocument();
  
  public abstract boolean isPictureInPictureAllowedForFullscreenVideo();
  
  public abstract boolean isReady();
  
  public abstract boolean isShowingInterstitialPage();
  
  public abstract void onHide();
  
  public abstract void onShow();
  
  public abstract void paste();
  
  public abstract void pasteAsPlainText();
  
  public abstract void postMessageToFrame(String paramString1, String paramString2, String paramString3, String paramString4, MessagePort[] paramArrayOfMessagePort);
  
  public abstract void reloadLoFiImages();
  
  public abstract void removeObserver(WebContentsObserver paramWebContentsObserver);
  
  public abstract void replace(String paramString);
  
  public abstract void requestAccessibilitySnapshot(AccessibilitySnapshotCallback paramAccessibilitySnapshotCallback);
  
  public abstract void requestSmartClipExtract(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract void resumeLoadingCreatedWebContents();
  
  public abstract void scrollFocusedEditableNodeIntoView();
  
  public abstract void selectAll();
  
  public abstract void selectWordAroundCaret();
  
  public abstract void setAudioMuted(boolean paramBoolean);
  
  public abstract void setDisplayCutoutSafeArea(Rect paramRect);
  
  public abstract void setHasPersistentVideo(boolean paramBoolean);
  
  public abstract void setImportance(int paramInt);
  
  public abstract void setInternalsHolder(InternalsHolder paramInternalsHolder);
  
  public abstract void setOverscrollRefreshHandler(OverscrollRefreshHandler paramOverscrollRefreshHandler);
  
  public abstract void setSize(int paramInt1, int paramInt2);
  
  public abstract void setSmartClipResultHandler(Handler paramHandler);
  
  public abstract void showContextMenuAtTouchHandle(int paramInt1, int paramInt2);
  
  @VisibleForTesting
  public abstract void showInterstitialPage(String paramString, long paramLong);
  
  public abstract void simulateRendererKilledForTesting(boolean paramBoolean);
  
  public abstract void stop();
  
  public abstract void suspendAllMediaPlayers();
  
  public abstract void updateBrowserControlsState(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4);
  
  public static abstract interface InternalsHolder
  {
    public abstract WebContentsInternals get();
    
    public abstract void set(WebContentsInternals paramWebContentsInternals);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content_public\browser\WebContents.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */