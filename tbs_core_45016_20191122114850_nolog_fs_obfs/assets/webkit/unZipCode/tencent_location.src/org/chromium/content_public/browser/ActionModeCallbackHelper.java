package org.chromium.content_public.browser;

import android.content.Intent;
import android.graphics.Rect;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import org.chromium.content.browser.selection.SelectionPopupControllerImpl;

public abstract class ActionModeCallbackHelper
{
  public static final EmptyActionCallback EMPTY_CALLBACK = new EmptyActionCallback(null);
  public static final int MAX_SEARCH_QUERY_LENGTH = 1000;
  public static final int MENU_ITEM_PROCESS_TEXT = 4;
  public static final int MENU_ITEM_SHARE = 1;
  public static final int MENU_ITEM_WEB_SEARCH = 2;
  private static final String TAG = "ActionModeHelper";
  
  public static String sanitizeQuery(String paramString, int paramInt)
  {
    return SelectionPopupControllerImpl.sanitizeQuery(paramString, paramInt);
  }
  
  public abstract void finishActionMode();
  
  public abstract String getSelectedText();
  
  public abstract boolean isActionModeValid();
  
  public abstract boolean onActionItemClicked(ActionMode paramActionMode, MenuItem paramMenuItem);
  
  public abstract void onCreateActionMode(ActionMode paramActionMode, Menu paramMenu);
  
  public abstract void onDestroyActionMode();
  
  public abstract void onGetContentRect(ActionMode paramActionMode, View paramView, Rect paramRect);
  
  public abstract boolean onPrepareActionMode(ActionMode paramActionMode, Menu paramMenu);
  
  public abstract void onReceivedProcessTextResult(int paramInt, Intent paramIntent);
  
  public abstract void setAllowedMenuItems(int paramInt);
  
  public abstract boolean supportsFloatingActionMode();
  
  private static class EmptyActionCallback
    implements ActionMode.Callback
  {
    public boolean onActionItemClicked(ActionMode paramActionMode, MenuItem paramMenuItem)
    {
      return false;
    }
    
    public boolean onCreateActionMode(ActionMode paramActionMode, Menu paramMenu)
    {
      return false;
    }
    
    public void onDestroyActionMode(ActionMode paramActionMode) {}
    
    public boolean onPrepareActionMode(ActionMode paramActionMode, Menu paramMenu)
    {
      return false;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content_public\browser\ActionModeCallbackHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */