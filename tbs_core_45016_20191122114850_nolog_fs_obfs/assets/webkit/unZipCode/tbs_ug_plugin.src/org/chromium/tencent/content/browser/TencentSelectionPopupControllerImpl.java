package org.chromium.tencent.content.browser;

import android.graphics.Rect;
import org.chromium.content.browser.RenderCoordinates;
import org.chromium.content.browser.selection.SelectionPopupControllerImpl;
import org.chromium.content_public.browser.WebContents;
import org.chromium.tencent.SelectionControllerClient;

public class TencentSelectionPopupControllerImpl
  extends SelectionPopupControllerImpl
{
  private static final String TAG = "TencentSelectionPopupControllerImpl";
  private RenderCoordinates mRenderCoordinates;
  private SelectionControllerClient mSelectionControllerClient;
  
  public TencentSelectionPopupControllerImpl(WebContents paramWebContents)
  {
    super(paramWebContents);
  }
  
  public void cloneRenderCoordinates(RenderCoordinates paramRenderCoordinates)
  {
    if (paramRenderCoordinates == null) {
      return;
    }
    this.mRenderCoordinates = paramRenderCoordinates;
    paramRenderCoordinates = this.mSelectionControllerClient;
    if (paramRenderCoordinates != null) {
      paramRenderCoordinates.cloneRenderCoordinates(this.mRenderCoordinates);
    }
  }
  
  protected void onDragUpdate(float paramFloat1, float paramFloat2) {}
  
  protected void onSelectionBoundsEvent(int paramInt, Rect paramRect1, Rect paramRect2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    if (!paramBoolean3)
    {
      paramBoolean1 = true;
      paramBoolean2 = true;
    }
    SelectionControllerClient localSelectionControllerClient = this.mSelectionControllerClient;
    if (localSelectionControllerClient != null) {
      localSelectionControllerClient.setEventType(paramInt);
    }
    switch (paramInt)
    {
    default: 
      
    case 10: 
      paramRect1 = this.mSelectionControllerClient;
      if (paramRect1 != null)
      {
        paramRect1.showInsertionMode(paramRect2, paramBoolean1);
        return;
      }
      break;
    case 9: 
      paramRect1 = this.mSelectionControllerClient;
      if (paramRect1 != null)
      {
        paramRect1.showInsertionMode(paramRect2, paramBoolean1);
        return;
      }
      break;
    case 8: 
      this.mIsInsertionForTesting = false;
      paramRect1 = this.mSelectionControllerClient;
      if (paramRect1 != null)
      {
        paramRect1.hideInsertionMode();
        return;
      }
      break;
    case 7: 
      paramRect1 = this.mSelectionControllerClient;
      if (paramRect1 != null)
      {
        paramRect1.showInsertionMode(paramRect2, paramBoolean1);
        return;
      }
      break;
    case 6: 
      paramRect1 = this.mSelectionControllerClient;
      if (paramRect1 != null)
      {
        paramRect1.showInsertionMode(paramRect2, paramBoolean1);
        return;
      }
      break;
    case 5: 
      this.mIsInsertionForTesting = true;
      paramRect1 = this.mSelectionControllerClient;
      if (paramRect1 != null)
      {
        paramRect1.showInsertionMode(paramRect2, paramBoolean1);
        return;
      }
      break;
    case 4: 
      localSelectionControllerClient = this.mSelectionControllerClient;
      if (localSelectionControllerClient != null)
      {
        localSelectionControllerClient.showSelectionMode(paramRect1, paramRect2, paramBoolean1, paramBoolean2, paramBoolean4);
        return;
      }
      break;
    case 3: 
      localSelectionControllerClient = this.mSelectionControllerClient;
      if (localSelectionControllerClient != null)
      {
        localSelectionControllerClient.showSelectionMode(paramRect1, paramRect2, paramBoolean1, paramBoolean2, paramBoolean4);
        return;
      }
      break;
    case 2: 
      this.mHasSelection = false;
      paramRect1 = this.mSelectionControllerClient;
      if (paramRect1 != null)
      {
        paramRect1.hideSelectionMode();
        this.mSelectionControllerClient.onSelectionEnd();
        return;
      }
      break;
    case 1: 
      localSelectionControllerClient = this.mSelectionControllerClient;
      if (localSelectionControllerClient != null)
      {
        localSelectionControllerClient.showSelectionMode(paramRect1, paramRect2, paramBoolean1, paramBoolean2, paramBoolean4);
        return;
      }
      break;
    case 0: 
      this.mHasSelection = true;
      localSelectionControllerClient = this.mSelectionControllerClient;
      if (localSelectionControllerClient != null) {
        localSelectionControllerClient.showSelectionMode(paramRect1, paramRect2, paramBoolean1, paramBoolean2, paramBoolean4);
      }
      break;
    }
  }
  
  protected void onSelectionEvent(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {}
  
  public void resetSelection()
  {
    this.mHasSelection = false;
  }
  
  public void restoreSelectionPopupsIfNecessary()
  {
    if (this.mHasSelection) {
      clearSelection();
    }
  }
  
  public void setSelectionControllerClient(SelectionControllerClient paramSelectionControllerClient)
  {
    if (paramSelectionControllerClient == null) {
      return;
    }
    this.mSelectionControllerClient = paramSelectionControllerClient;
    paramSelectionControllerClient = this.mRenderCoordinates;
    if (paramSelectionControllerClient != null) {
      this.mSelectionControllerClient.cloneRenderCoordinates(paramSelectionControllerClient);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\content\browser\TencentSelectionPopupControllerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */