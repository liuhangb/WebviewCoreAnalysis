package org.chromium.content.browser.selection;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.ActionMode.Callback2;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

@TargetApi(23)
public class FloatingPastePopupMenu
  implements PastePopupMenu
{
  private final Context jdField_a_of_type_AndroidContentContext;
  private Rect jdField_a_of_type_AndroidGraphicsRect;
  private ActionMode.Callback jdField_a_of_type_AndroidViewActionMode$Callback;
  private ActionMode jdField_a_of_type_AndroidViewActionMode;
  private final View jdField_a_of_type_AndroidViewView;
  private final PastePopupMenu.PastePopupMenuDelegate jdField_a_of_type_OrgChromiumContentBrowserSelectionPastePopupMenu$PastePopupMenuDelegate;
  
  public FloatingPastePopupMenu(Context paramContext, View paramView, PastePopupMenu.PastePopupMenuDelegate paramPastePopupMenuDelegate, ActionMode.Callback paramCallback)
  {
    if ((!jdField_a_of_type_Boolean) && (Build.VERSION.SDK_INT < 23)) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_AndroidViewView = paramView;
    this.jdField_a_of_type_OrgChromiumContentBrowserSelectionPastePopupMenu$PastePopupMenuDelegate = paramPastePopupMenuDelegate;
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    this.jdField_a_of_type_AndroidViewActionMode$Callback = paramCallback;
  }
  
  private void a()
  {
    if (this.jdField_a_of_type_AndroidViewActionMode != null) {
      return;
    }
    ActionMode localActionMode = this.jdField_a_of_type_AndroidViewView.startActionMode(new ActionModeCallback(null), 1);
    if (localActionMode != null)
    {
      LGEmailActionModeWorkaround.runIfNecessary(this.jdField_a_of_type_AndroidContentContext, localActionMode);
      if ((!jdField_a_of_type_Boolean) && (localActionMode.getType() != 1)) {
        throw new AssertionError();
      }
      this.jdField_a_of_type_AndroidViewActionMode = localActionMode;
    }
  }
  
  public void hide()
  {
    ActionMode localActionMode = this.jdField_a_of_type_AndroidViewActionMode;
    if (localActionMode != null)
    {
      localActionMode.finish();
      this.jdField_a_of_type_AndroidViewActionMode = null;
    }
  }
  
  public void show(Rect paramRect)
  {
    this.jdField_a_of_type_AndroidGraphicsRect = paramRect;
    paramRect = this.jdField_a_of_type_AndroidViewActionMode;
    if (paramRect != null)
    {
      paramRect.invalidateContentRect();
      return;
    }
    a();
  }
  
  private class ActionModeCallback
    extends ActionMode.Callback2
  {
    private ActionModeCallback() {}
    
    private void a(ActionMode paramActionMode, Menu paramMenu)
    {
      paramActionMode.setSubtitle(null);
      SelectionPopupControllerImpl.initializeMenu(FloatingPastePopupMenu.a(FloatingPastePopupMenu.this), paramActionMode, paramMenu);
      SelectionPopupControllerImpl.setPasteAsPlainTextMenuItemTitle(paramMenu);
    }
    
    public boolean onActionItemClicked(ActionMode paramActionMode, MenuItem paramMenuItem)
    {
      paramMenuItem.getItemId();
      return true;
    }
    
    public boolean onCreateActionMode(ActionMode paramActionMode, Menu paramMenu)
    {
      a(paramActionMode, paramMenu);
      if (FloatingPastePopupMenu.a(FloatingPastePopupMenu.this) != null) {
        FloatingPastePopupMenu.a(FloatingPastePopupMenu.this).onCreateActionMode(paramActionMode, paramMenu);
      }
      return true;
    }
    
    public void onDestroyActionMode(ActionMode paramActionMode)
    {
      if (FloatingPastePopupMenu.a(FloatingPastePopupMenu.this) != null) {
        FloatingPastePopupMenu.a(FloatingPastePopupMenu.this).onDestroyActionMode(paramActionMode);
      }
      FloatingPastePopupMenu.a(FloatingPastePopupMenu.this, null);
    }
    
    public void onGetContentRect(ActionMode paramActionMode, View paramView, Rect paramRect)
    {
      paramRect.set(FloatingPastePopupMenu.a(FloatingPastePopupMenu.this));
    }
    
    public boolean onPrepareActionMode(ActionMode paramActionMode, Menu paramMenu)
    {
      if (FloatingPastePopupMenu.a(FloatingPastePopupMenu.this) != null) {
        return FloatingPastePopupMenu.a(FloatingPastePopupMenu.this).onPrepareActionMode(paramActionMode, paramMenu);
      }
      return false;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\selection\FloatingPastePopupMenu.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */