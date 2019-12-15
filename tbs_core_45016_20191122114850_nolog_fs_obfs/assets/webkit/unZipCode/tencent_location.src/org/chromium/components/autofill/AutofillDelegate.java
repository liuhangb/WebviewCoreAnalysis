package org.chromium.components.autofill;

public abstract interface AutofillDelegate
{
  public abstract void accessibilityFocusCleared();
  
  public abstract void deleteSuggestion(int paramInt);
  
  public abstract void dismissed();
  
  public abstract void suggestionSelected(int paramInt);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\components\autofill\AutofillDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */