package org.chromium.android_webview;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuItem;
import java.util.List;
import org.chromium.content_public.browser.ActionModeCallbackHelper;

public class AwActionModeCallback
  implements ActionMode.Callback
{
  private int jdField_a_of_type_Int;
  private final Context jdField_a_of_type_AndroidContentContext;
  private final AwContents jdField_a_of_type_OrgChromiumAndroid_webviewAwContents;
  private final ActionModeCallbackHelper jdField_a_of_type_OrgChromiumContent_publicBrowserActionModeCallbackHelper;
  
  public AwActionModeCallback(Context paramContext, AwContents paramAwContents, ActionModeCallbackHelper paramActionModeCallbackHelper)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContents = paramAwContents;
    this.jdField_a_of_type_OrgChromiumContent_publicBrowserActionModeCallbackHelper = paramActionModeCallbackHelper;
    this.jdField_a_of_type_OrgChromiumContent_publicBrowserActionModeCallbackHelper.setAllowedMenuItems(0);
  }
  
  private int a(int paramInt)
  {
    boolean bool;
    if (paramInt == 2) {
      bool = a();
    } else {
      bool = true;
    }
    if ((bool) && (this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContents.isSelectActionModeAllowed(paramInt))) {
      return paramInt;
    }
    return 0;
  }
  
  private boolean a()
  {
    Intent localIntent = new Intent("android.intent.action.WEB_SEARCH");
    localIntent.putExtra("new_search", true);
    return this.jdField_a_of_type_AndroidContentContext.getPackageManager().queryIntentActivities(localIntent, 65536).size() > 0;
  }
  
  public boolean onActionItemClicked(ActionMode paramActionMode, MenuItem paramMenuItem)
  {
    return !this.jdField_a_of_type_OrgChromiumContent_publicBrowserActionModeCallbackHelper.isActionModeValid();
  }
  
  public boolean onCreateActionMode(ActionMode paramActionMode, Menu paramMenu)
  {
    int i = a(1) | a(2) | a(4);
    if (i != this.jdField_a_of_type_Int)
    {
      this.jdField_a_of_type_OrgChromiumContent_publicBrowserActionModeCallbackHelper.setAllowedMenuItems(i);
      this.jdField_a_of_type_Int = i;
    }
    this.jdField_a_of_type_OrgChromiumContent_publicBrowserActionModeCallbackHelper.onCreateActionMode(paramActionMode, paramMenu);
    return true;
  }
  
  public void onDestroyActionMode(ActionMode paramActionMode)
  {
    this.jdField_a_of_type_OrgChromiumContent_publicBrowserActionModeCallbackHelper.onDestroyActionMode();
  }
  
  public boolean onPrepareActionMode(ActionMode paramActionMode, Menu paramMenu)
  {
    return this.jdField_a_of_type_OrgChromiumContent_publicBrowserActionModeCallbackHelper.onPrepareActionMode(paramActionMode, paramMenu);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwActionModeCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */