package org.chromium.content_public.browser;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View.OnClickListener;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextClassifier;
import android.view.textclassifier.TextSelection;
import org.chromium.content.browser.selection.SmartSelectionClient;
import org.chromium.tencent.utils.ClassAdapter;

public abstract class SelectionClient
{
  public static SelectionClient createSmartSelectionClient(WebContents paramWebContents)
  {
    return SmartSelectionClient.create(ClassAdapter.fromWebContentsSelectionPopupController(paramWebContents).getResultCallback(), paramWebContents);
  }
  
  public abstract void cancelAllRequests();
  
  public TextClassifier getCustomTextClassifier()
  {
    return null;
  }
  
  public SelectionMetricsLogger getSelectionMetricsLogger()
  {
    return null;
  }
  
  public TextClassifier getTextClassifier()
  {
    return null;
  }
  
  public abstract void onSelectionChanged(String paramString);
  
  public abstract void onSelectionEvent(int paramInt, float paramFloat1, float paramFloat2);
  
  public abstract boolean requestSelectionPopupUpdates(boolean paramBoolean);
  
  public abstract void selectWordAroundCaretAck(boolean paramBoolean, int paramInt1, int paramInt2);
  
  public void setTextClassifier(TextClassifier paramTextClassifier) {}
  
  public abstract void showUnhandledTapUIIfNeeded(int paramInt1, int paramInt2);
  
  public static class Result
  {
    public int a;
    public Intent a;
    public Drawable a;
    public View.OnClickListener a;
    public TextClassification a;
    public TextSelection a;
    public CharSequence a;
    public int b;
    
    public boolean a()
    {
      return ((this.jdField_a_of_type_JavaLangCharSequence != null) || (this.jdField_a_of_type_AndroidGraphicsDrawableDrawable != null)) && ((this.jdField_a_of_type_AndroidContentIntent != null) || (this.jdField_a_of_type_AndroidViewView$OnClickListener != null));
    }
  }
  
  public static abstract interface ResultCallback
  {
    public abstract void onClassified(SelectionClient.Result paramResult);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content_public\browser\SelectionClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */