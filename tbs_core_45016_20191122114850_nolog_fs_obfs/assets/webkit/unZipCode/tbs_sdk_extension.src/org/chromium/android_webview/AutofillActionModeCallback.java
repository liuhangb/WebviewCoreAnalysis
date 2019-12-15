package org.chromium.android_webview;

import android.content.Context;
import android.content.res.Resources;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuItem;
import org.chromium.components.autofill.AutofillProvider;

public class AutofillActionModeCallback
  implements ActionMode.Callback
{
  private final int jdField_a_of_type_Int;
  private final Context jdField_a_of_type_AndroidContentContext;
  private final AutofillProvider jdField_a_of_type_OrgChromiumComponentsAutofillAutofillProvider;
  private final int b;
  
  public AutofillActionModeCallback(Context paramContext, AutofillProvider paramAutofillProvider)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    this.jdField_a_of_type_OrgChromiumComponentsAutofillAutofillProvider = paramAutofillProvider;
    this.jdField_a_of_type_Int = this.jdField_a_of_type_AndroidContentContext.getResources().getIdentifier("autofill", "string", "android");
    this.b = this.jdField_a_of_type_AndroidContentContext.getResources().getIdentifier("autofill", "id", "android");
  }
  
  public boolean onActionItemClicked(ActionMode paramActionMode, MenuItem paramMenuItem)
  {
    if (paramMenuItem.getItemId() == this.b)
    {
      this.jdField_a_of_type_OrgChromiumComponentsAutofillAutofillProvider.queryAutofillSuggestion();
      paramActionMode.finish();
      return true;
    }
    return false;
  }
  
  public boolean onCreateActionMode(ActionMode paramActionMode, Menu paramMenu)
  {
    return (this.jdField_a_of_type_Int != 0) && (this.b != 0);
  }
  
  public void onDestroyActionMode(ActionMode paramActionMode) {}
  
  public boolean onPrepareActionMode(ActionMode paramActionMode, Menu paramMenu)
  {
    if ((this.jdField_a_of_type_Int != 0) && (this.jdField_a_of_type_OrgChromiumComponentsAutofillAutofillProvider.shouldQueryAutofillSuggestion())) {
      paramMenu.add(0, this.b, 196608, this.jdField_a_of_type_Int).setShowAsActionFlags(5);
    }
    return true;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AutofillActionModeCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */