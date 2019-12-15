package org.chromium.android_webview;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ZoomButtonsController;
import android.widget.ZoomButtonsController.OnZoomListener;
import org.chromium.content_public.browser.ContentViewCore;

class AwZoomControls
{
  private ZoomButtonsController jdField_a_of_type_AndroidWidgetZoomButtonsController;
  private AwContents jdField_a_of_type_OrgChromiumAndroid_webviewAwContents;
  
  AwZoomControls(AwContents paramAwContents)
  {
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContents = paramAwContents;
  }
  
  @SuppressLint({"RtlHardcoded"})
  private ZoomButtonsController a()
  {
    if ((this.jdField_a_of_type_AndroidWidgetZoomButtonsController == null) && (this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContents.getSettings().shouldDisplayZoomControls()))
    {
      this.jdField_a_of_type_AndroidWidgetZoomButtonsController = new ZoomButtonsController(this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContents.getContentViewCore().getContainerView());
      this.jdField_a_of_type_AndroidWidgetZoomButtonsController.setOnZoomListener(new ZoomListener(null));
      ViewGroup.LayoutParams localLayoutParams = this.jdField_a_of_type_AndroidWidgetZoomButtonsController.getZoomControls().getLayoutParams();
      if ((localLayoutParams instanceof FrameLayout.LayoutParams)) {
        ((FrameLayout.LayoutParams)localLayoutParams).gravity = 5;
      }
    }
    return this.jdField_a_of_type_AndroidWidgetZoomButtonsController;
  }
  
  View a()
  {
    ZoomButtonsController localZoomButtonsController = this.jdField_a_of_type_AndroidWidgetZoomButtonsController;
    if (localZoomButtonsController != null) {
      return localZoomButtonsController.getZoomControls();
    }
    return null;
  }
  
  public void dismissZoomPicker()
  {
    ZoomButtonsController localZoomButtonsController = a();
    if (localZoomButtonsController != null) {
      localZoomButtonsController.setVisible(false);
    }
  }
  
  public void invokeZoomPicker()
  {
    ZoomButtonsController localZoomButtonsController = a();
    if (localZoomButtonsController != null) {
      localZoomButtonsController.setVisible(true);
    }
  }
  
  public void updateZoomControls()
  {
    ZoomButtonsController localZoomButtonsController = a();
    if (localZoomButtonsController == null) {
      return;
    }
    boolean bool1 = this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContents.canZoomIn();
    boolean bool2 = this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContents.canZoomOut();
    if ((!bool1) && (!bool2))
    {
      localZoomButtonsController.getZoomControls().setVisibility(8);
      return;
    }
    localZoomButtonsController.setZoomInEnabled(bool1);
    localZoomButtonsController.setZoomOutEnabled(bool2);
  }
  
  private class ZoomListener
    implements ZoomButtonsController.OnZoomListener
  {
    private ZoomListener() {}
    
    public void onVisibilityChanged(boolean paramBoolean)
    {
      if (paramBoolean)
      {
        AwZoomControls.a(AwZoomControls.this).getZoomControls().setVisibility(0);
        AwZoomControls.this.updateZoomControls();
      }
    }
    
    public void onZoom(boolean paramBoolean)
    {
      if (paramBoolean)
      {
        AwZoomControls.a(AwZoomControls.this).zoomIn();
        return;
      }
      AwZoomControls.a(AwZoomControls.this).zoomOut();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwZoomControls.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */