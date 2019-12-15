package org.chromium.android_webview;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.content.browser.ContentVideoViewEmbedder;

@JNINamespace("android_webview")
public class AwContentVideoViewEmbedder
  implements ContentVideoViewEmbedder
{
  private final Context jdField_a_of_type_AndroidContentContext;
  private View jdField_a_of_type_AndroidViewView;
  private FrameLayout jdField_a_of_type_AndroidWidgetFrameLayout;
  private final AwContentsClient jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsClient;
  
  public AwContentVideoViewEmbedder(Context paramContext, AwContentsClient paramAwContentsClient, FrameLayout paramFrameLayout)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsClient = paramAwContentsClient;
    this.jdField_a_of_type_AndroidWidgetFrameLayout = paramFrameLayout;
  }
  
  public void enterFullscreenVideo(View paramView, boolean paramBoolean)
  {
    FrameLayout localFrameLayout = this.jdField_a_of_type_AndroidWidgetFrameLayout;
    if (localFrameLayout == null) {
      return;
    }
    localFrameLayout.addView(paramView, 0);
    if (paramBoolean) {
      return;
    }
    this.jdField_a_of_type_AndroidViewView = this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsClient.getVideoLoadingProgressView();
    if (this.jdField_a_of_type_AndroidViewView == null) {
      this.jdField_a_of_type_AndroidViewView = new ProgressView(this.jdField_a_of_type_AndroidContentContext);
    }
    this.jdField_a_of_type_AndroidWidgetFrameLayout.addView(this.jdField_a_of_type_AndroidViewView, new FrameLayout.LayoutParams(-2, -2, 17));
  }
  
  public void exitFullscreenVideo() {}
  
  public void fullscreenVideoLoaded()
  {
    FrameLayout localFrameLayout = this.jdField_a_of_type_AndroidWidgetFrameLayout;
    if (localFrameLayout == null) {
      return;
    }
    View localView = this.jdField_a_of_type_AndroidViewView;
    if (localView != null)
    {
      localFrameLayout.removeView(localView);
      this.jdField_a_of_type_AndroidViewView = null;
    }
  }
  
  public void setCustomView(FrameLayout paramFrameLayout)
  {
    this.jdField_a_of_type_AndroidWidgetFrameLayout = paramFrameLayout;
  }
  
  public void setSystemUiVisibility(boolean paramBoolean) {}
  
  private static class ProgressView
    extends LinearLayout
  {
    private final ProgressBar jdField_a_of_type_AndroidWidgetProgressBar;
    private final TextView jdField_a_of_type_AndroidWidgetTextView;
    
    public ProgressView(Context paramContext)
    {
      super();
      setOrientation(1);
      setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
      this.jdField_a_of_type_AndroidWidgetProgressBar = new ProgressBar(paramContext, null, 16842874);
      this.jdField_a_of_type_AndroidWidgetTextView = new TextView(paramContext);
      this.jdField_a_of_type_AndroidWidgetTextView.setText("");
      addView(this.jdField_a_of_type_AndroidWidgetProgressBar);
      addView(this.jdField_a_of_type_AndroidWidgetTextView);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwContentVideoViewEmbedder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */