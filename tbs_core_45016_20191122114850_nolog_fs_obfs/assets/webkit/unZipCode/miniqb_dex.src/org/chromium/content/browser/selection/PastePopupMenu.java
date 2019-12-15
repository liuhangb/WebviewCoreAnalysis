package org.chromium.content.browser.selection;

import android.graphics.Rect;

public abstract interface PastePopupMenu
{
  public abstract void hide();
  
  public abstract void show(Rect paramRect);
  
  public static abstract interface PastePopupMenuDelegate
  {
    public abstract boolean canPaste();
    
    public abstract boolean canPasteAsPlainText();
    
    public abstract boolean canSelectAll();
    
    public abstract void paste();
    
    public abstract void pasteAsPlainText();
    
    public abstract void selectAll();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\selection\PastePopupMenu.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */