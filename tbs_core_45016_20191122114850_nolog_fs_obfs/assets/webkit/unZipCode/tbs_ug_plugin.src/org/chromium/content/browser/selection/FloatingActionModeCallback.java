package org.chromium.content.browser.selection;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.ActionMode.Callback2;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import org.chromium.content_public.browser.ActionModeCallbackHelper;

@TargetApi(23)
class FloatingActionModeCallback
  extends ActionMode.Callback2
{
  private final ActionMode.Callback jdField_a_of_type_AndroidViewActionMode$Callback;
  private final ActionModeCallbackHelper jdField_a_of_type_OrgChromiumContent_publicBrowserActionModeCallbackHelper;
  
  public FloatingActionModeCallback(ActionModeCallbackHelper paramActionModeCallbackHelper, ActionMode.Callback paramCallback)
  {
    this.jdField_a_of_type_OrgChromiumContent_publicBrowserActionModeCallbackHelper = paramActionModeCallbackHelper;
    this.jdField_a_of_type_AndroidViewActionMode$Callback = paramCallback;
  }
  
  public boolean onActionItemClicked(ActionMode paramActionMode, MenuItem paramMenuItem)
  {
    return this.jdField_a_of_type_AndroidViewActionMode$Callback.onActionItemClicked(paramActionMode, paramMenuItem);
  }
  
  public boolean onCreateActionMode(ActionMode paramActionMode, Menu paramMenu)
  {
    if (paramActionMode.getType() != 1) {
      return false;
    }
    return this.jdField_a_of_type_AndroidViewActionMode$Callback.onCreateActionMode(paramActionMode, paramMenu);
  }
  
  public void onDestroyActionMode(ActionMode paramActionMode)
  {
    this.jdField_a_of_type_AndroidViewActionMode$Callback.onDestroyActionMode(paramActionMode);
  }
  
  public void onGetContentRect(ActionMode paramActionMode, View paramView, Rect paramRect)
  {
    this.jdField_a_of_type_OrgChromiumContent_publicBrowserActionModeCallbackHelper.onGetContentRect(paramActionMode, paramView, paramRect);
  }
  
  public boolean onPrepareActionMode(ActionMode paramActionMode, Menu paramMenu)
  {
    return this.jdField_a_of_type_AndroidViewActionMode$Callback.onPrepareActionMode(paramActionMode, paramMenu);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\selection\FloatingActionModeCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */