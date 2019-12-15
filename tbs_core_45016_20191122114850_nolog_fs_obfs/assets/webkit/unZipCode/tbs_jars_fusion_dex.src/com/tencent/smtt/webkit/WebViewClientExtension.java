package com.tencent.smtt.webkit;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.ValueCallback;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import java.util.HashMap;
import java.util.Set;
import org.chromium.base.annotations.UsedByReflection;

@UsedByReflection("WebCoreProxy.java")
public class WebViewClientExtension
  implements IX5WebViewClientExtension
{
  public boolean allowJavaScriptOpenWindowAutomatically(String paramString1, String paramString2)
  {
    return false;
  }
  
  public void computeScroll(View paramView) {}
  
  public void didFailLoad(String paramString, int paramInt) {}
  
  public void didFirstVisuallyNonEmptyPaint() {}
  
  public void didNavigateWithinPage(IX5WebViewBase paramIX5WebViewBase, String paramString) {}
  
  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent, View paramView)
  {
    return false;
  }
  
  public void documentAvailableInMainFrame() {}
  
  public void handlePluginTag(String paramString1, String paramString2, boolean paramBoolean, String paramString3) {}
  
  public void hasDiscardCurrentPage(boolean paramBoolean) {}
  
  public void hasRestoreCurrentPage(boolean paramBoolean) {}
  
  public void hideAddressBar() {}
  
  public void invalidate() {}
  
  public boolean notifyAutoAudioPlay(String paramString, JsResult paramJsResult)
  {
    return false;
  }
  
  public boolean notifyJavaScriptOpenWindowsBlocked(String paramString, String[] paramArrayOfString, ValueCallback<Boolean> paramValueCallback, boolean paramBoolean)
  {
    return false;
  }
  
  public void onDoubleTapStart() {}
  
  public void onFakeLoginRecognised(Bundle paramBundle) {}
  
  public void onFlingScrollBegin(int paramInt1, int paramInt2, int paramInt3) {}
  
  public void onFlingScrollEnd() {}
  
  public HashMap<String, String> onGetExtraHeadersForPreloading(String paramString)
  {
    return null;
  }
  
  public void onGetTtsText(String paramString, int paramInt) {}
  
  public void onHideAdSuccess() {}
  
  public void onHideListBox() {}
  
  public void onHistoryItemChanged() {}
  
  public void onInputBoxTextChanged(IX5WebViewExtension paramIX5WebViewExtension, String paramString) {}
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent, View paramView)
  {
    return false;
  }
  
  public void onMetricsSavedCountReceived(String paramString1, boolean paramBoolean, long paramLong, String paramString2, int paramInt) {}
  
  public Object onMiscCallBack(String paramString, Bundle paramBundle)
  {
    return null;
  }
  
  public Object onMiscCallBack(String paramString, Bundle paramBundle, Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4)
  {
    return null;
  }
  
  public void onMissingPluginClicked(String paramString1, String paramString2, String paramString3, int paramInt) {}
  
  public void onNativeCrashReport(int paramInt, String paramString) {}
  
  public void onOverScrolled(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2, View paramView) {}
  
  public void onPinchToZoomStart() {}
  
  public void onPreReadFinished() {}
  
  public void onPrefetchResourceHit(boolean paramBoolean) {}
  
  public void onPreloadCallback(int paramInt, String paramString) {}
  
  public void onPromptScaleSaved() {}
  
  public void onReceivedQNovel(String paramString1, String paramString2) {}
  
  public void onReceivedSslErrorCancel() {}
  
  public void onReceivedViewSource(String paramString) {}
  
  public void onReportAdFilterInfo(int paramInt1, int paramInt2, String paramString, boolean paramBoolean) {}
  
  public void onReportHtmlInfo(int paramInt, String paramString) {}
  
  public void onResponseReceived(WebResourceRequest paramWebResourceRequest, WebResourceResponse paramWebResourceResponse, int paramInt) {}
  
  public void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
  
  public void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4, View paramView) {}
  
  public void onSetButtonStatus(boolean paramBoolean1, boolean paramBoolean2) {}
  
  public void onShowListBox(String[] paramArrayOfString, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt) {}
  
  public boolean onShowLongClickPopupMenu()
  {
    return false;
  }
  
  public void onShowMutilListBox(String[] paramArrayOfString, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3) {}
  
  public void onShowTtsBar() {}
  
  public void onSlidingTitleOffScreen(int paramInt1, int paramInt2) {}
  
  public void onSoftKeyBoardHide(int paramInt) {}
  
  public void onSoftKeyBoardShow() {}
  
  public void onSpecialSiteDetectedResult(Set<String> paramSet1, Set<String> paramSet2) {}
  
  public void onSupportReadMode() {}
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent, View paramView)
  {
    return false;
  }
  
  public void onTransitionToCommitted() {}
  
  public void onUploadProgressChange(int paramInt1, int paramInt2, String paramString) {}
  
  public void onUploadProgressStart(int paramInt) {}
  
  public void onUrlChange(String paramString1, String paramString2) {}
  
  public boolean overScrollBy(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean, View paramView)
  {
    return false;
  }
  
  public boolean preShouldOverrideUrlLoading(IX5WebViewExtension paramIX5WebViewExtension, String paramString)
  {
    return false;
  }
  
  public boolean reportFingerSearchAdjustInfo(String paramString)
  {
    return false;
  }
  
  public boolean reportFingerSearchRequestInfo(String paramString)
  {
    return false;
  }
  
  public boolean requestVibration(String paramString, JsResult paramJsResult)
  {
    return false;
  }
  
  public boolean shouldDiscardCurrentPage()
  {
    return false;
  }
  
  public boolean shouldRestoreCurrentPage()
  {
    return false;
  }
  
  public void showTranslateBubble(int paramInt1, String paramString1, String paramString2, int paramInt2) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\WebViewClientExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */