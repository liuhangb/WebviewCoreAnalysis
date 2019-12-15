package org.chromium.tencent;

import android.graphics.Rect;
import org.chromium.content.browser.RenderCoordinates;

public abstract interface SelectionControllerClient
{
  public abstract void cloneRenderCoordinates(RenderCoordinates paramRenderCoordinates);
  
  public abstract void hideInsertionMode();
  
  public abstract void hideSelectionMode();
  
  public abstract void onSelectionEnd();
  
  public abstract void setEventType(int paramInt);
  
  public abstract void showInsertionMode(Rect paramRect, boolean paramBoolean);
  
  public abstract void showSelectionMode(Rect paramRect1, Rect paramRect2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\SelectionControllerClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */