package org.chromium.content_public.browser;

import android.content.Intent;
import android.view.ActionMode.Callback;
import android.view.textclassifier.TextClassifier;

public abstract interface SelectionPopupController
{
  public abstract void clearSelection();
  
  public abstract void destroySelectActionMode();
  
  public abstract ActionModeCallbackHelper getActionModeCallbackHelper();
  
  public abstract TextClassifier getCustomTextClassifier();
  
  public abstract SelectionClient.ResultCallback getResultCallback();
  
  public abstract String getSelectedText();
  
  public abstract TextClassifier getTextClassifier();
  
  public abstract boolean hasSelection();
  
  public abstract boolean isFocusedNodeEditable();
  
  public abstract boolean isSelectActionBarShowing();
  
  public abstract void onReceivedProcessTextResult(int paramInt, Intent paramIntent);
  
  public abstract void setActionModeCallback(ActionMode.Callback paramCallback);
  
  public abstract void setNonSelectionActionModeCallback(ActionMode.Callback paramCallback);
  
  public abstract void setSelectionClient(SelectionClient paramSelectionClient);
  
  public abstract void setTextClassifier(TextClassifier paramTextClassifier);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content_public\browser\SelectionPopupController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */