package org.chromium.content.browser.input;

import android.annotation.TargetApi;
import android.graphics.Matrix;
import android.view.View;
import android.view.inputmethod.CursorAnchorInfo;
import android.view.inputmethod.CursorAnchorInfo.Builder;
import java.util.Arrays;
import org.chromium.base.VisibleForTesting;
import org.chromium.content_public.browser.InputMethodManagerWrapper;

@TargetApi(21)
final class CursorAnchorInfoController
{
  private float jdField_a_of_type_Float;
  private final Matrix jdField_a_of_type_AndroidGraphicsMatrix = new Matrix();
  private final CursorAnchorInfo.Builder jdField_a_of_type_AndroidViewInputmethodCursorAnchorInfo$Builder = new CursorAnchorInfo.Builder();
  private CursorAnchorInfo jdField_a_of_type_AndroidViewInputmethodCursorAnchorInfo;
  private final ComposingTextDelegate jdField_a_of_type_OrgChromiumContentBrowserInputCursorAnchorInfoController$ComposingTextDelegate;
  private final ViewDelegate jdField_a_of_type_OrgChromiumContentBrowserInputCursorAnchorInfoController$ViewDelegate;
  private InputMethodManagerWrapper jdField_a_of_type_OrgChromiumContent_publicBrowserInputMethodManagerWrapper;
  private boolean jdField_a_of_type_Boolean;
  private float[] jdField_a_of_type_ArrayOfFloat;
  private final int[] jdField_a_of_type_ArrayOfInt = new int[2];
  private float jdField_b_of_type_Float;
  private boolean jdField_b_of_type_Boolean;
  private float jdField_c_of_type_Float;
  private boolean jdField_c_of_type_Boolean;
  private float jdField_d_of_type_Float;
  private boolean jdField_d_of_type_Boolean;
  private float jdField_e_of_type_Float;
  private boolean jdField_e_of_type_Boolean;
  private float jdField_f_of_type_Float;
  private boolean jdField_f_of_type_Boolean;
  
  private CursorAnchorInfoController(InputMethodManagerWrapper paramInputMethodManagerWrapper, ComposingTextDelegate paramComposingTextDelegate, ViewDelegate paramViewDelegate)
  {
    this.jdField_a_of_type_OrgChromiumContent_publicBrowserInputMethodManagerWrapper = paramInputMethodManagerWrapper;
    this.jdField_a_of_type_OrgChromiumContentBrowserInputCursorAnchorInfoController$ComposingTextDelegate = paramComposingTextDelegate;
    this.jdField_a_of_type_OrgChromiumContentBrowserInputCursorAnchorInfoController$ViewDelegate = paramViewDelegate;
  }
  
  private void a(View paramView)
  {
    if (!this.jdField_d_of_type_Boolean) {
      return;
    }
    if (this.jdField_a_of_type_AndroidViewInputmethodCursorAnchorInfo == null)
    {
      this.jdField_a_of_type_AndroidViewInputmethodCursorAnchorInfo$Builder.reset();
      localObject = this.jdField_a_of_type_OrgChromiumContentBrowserInputCursorAnchorInfoController$ComposingTextDelegate.getText();
      int j = this.jdField_a_of_type_OrgChromiumContentBrowserInputCursorAnchorInfoController$ComposingTextDelegate.getSelectionStart();
      int k = this.jdField_a_of_type_OrgChromiumContentBrowserInputCursorAnchorInfoController$ComposingTextDelegate.getSelectionEnd();
      int m = this.jdField_a_of_type_OrgChromiumContentBrowserInputCursorAnchorInfoController$ComposingTextDelegate.getComposingTextStart();
      int i = this.jdField_a_of_type_OrgChromiumContentBrowserInputCursorAnchorInfoController$ComposingTextDelegate.getComposingTextEnd();
      float f2;
      float f3;
      if ((localObject != null) && (m >= 0) && (i <= ((CharSequence)localObject).length()))
      {
        this.jdField_a_of_type_AndroidViewInputmethodCursorAnchorInfo$Builder.setComposingText(m, ((CharSequence)localObject).subSequence(m, i));
        localObject = this.jdField_a_of_type_ArrayOfFloat;
        if (localObject != null)
        {
          int n = localObject.length / 4;
          i = 0;
          while (i < n)
          {
            int i1 = i * 4;
            f1 = localObject[i1];
            f2 = localObject[(i1 + 1)];
            f3 = localObject[(i1 + 2)];
            float f4 = localObject[(i1 + 3)];
            this.jdField_a_of_type_AndroidViewInputmethodCursorAnchorInfo$Builder.addCharacterBounds(m + i, f1, f2, f3, f4, 1);
            i += 1;
          }
        }
      }
      this.jdField_a_of_type_AndroidViewInputmethodCursorAnchorInfo$Builder.setSelectionRange(j, k);
      localObject = this.jdField_a_of_type_AndroidGraphicsMatrix;
      float f1 = this.jdField_a_of_type_Float;
      ((Matrix)localObject).setScale(f1, f1);
      this.jdField_a_of_type_AndroidGraphicsMatrix.postTranslate(this.jdField_b_of_type_Float, this.jdField_c_of_type_Float);
      this.jdField_a_of_type_AndroidViewInputmethodCursorAnchorInfo$Builder.setMatrix(this.jdField_a_of_type_AndroidGraphicsMatrix);
      if (this.jdField_e_of_type_Boolean)
      {
        localObject = this.jdField_a_of_type_AndroidViewInputmethodCursorAnchorInfo$Builder;
        f1 = this.jdField_d_of_type_Float;
        f2 = this.jdField_e_of_type_Float;
        f3 = this.jdField_f_of_type_Float;
        if (this.jdField_f_of_type_Boolean) {
          i = 1;
        } else {
          i = 2;
        }
        ((CursorAnchorInfo.Builder)localObject).setInsertionMarkerLocation(f1, f2, f3, f3, i);
      }
      this.jdField_a_of_type_AndroidViewInputmethodCursorAnchorInfo = this.jdField_a_of_type_AndroidViewInputmethodCursorAnchorInfo$Builder.build();
    }
    Object localObject = this.jdField_a_of_type_OrgChromiumContent_publicBrowserInputMethodManagerWrapper;
    if (localObject != null) {
      ((InputMethodManagerWrapper)localObject).updateCursorAnchorInfo(paramView, this.jdField_a_of_type_AndroidViewInputmethodCursorAnchorInfo);
    }
    this.jdField_b_of_type_Boolean = false;
  }
  
  public static CursorAnchorInfoController create(InputMethodManagerWrapper paramInputMethodManagerWrapper, ComposingTextDelegate paramComposingTextDelegate)
  {
    new CursorAnchorInfoController(paramInputMethodManagerWrapper, paramComposingTextDelegate, new ViewDelegate()
    {
      public void getLocationOnScreen(View paramAnonymousView, int[] paramAnonymousArrayOfInt)
      {
        paramAnonymousView.getLocationOnScreen(paramAnonymousArrayOfInt);
      }
    });
  }
  
  @VisibleForTesting
  public static CursorAnchorInfoController createForTest(InputMethodManagerWrapper paramInputMethodManagerWrapper, ComposingTextDelegate paramComposingTextDelegate, ViewDelegate paramViewDelegate)
  {
    return new CursorAnchorInfoController(paramInputMethodManagerWrapper, paramComposingTextDelegate, paramViewDelegate);
  }
  
  public void focusedNodeChanged(boolean paramBoolean)
  {
    this.jdField_a_of_type_Boolean = paramBoolean;
    this.jdField_a_of_type_ArrayOfFloat = null;
    this.jdField_d_of_type_Boolean = false;
    this.jdField_a_of_type_AndroidViewInputmethodCursorAnchorInfo = null;
  }
  
  public void invalidateLastCursorAnchorInfo()
  {
    if (!this.jdField_a_of_type_Boolean) {
      return;
    }
    this.jdField_a_of_type_AndroidViewInputmethodCursorAnchorInfo = null;
  }
  
  public boolean onRequestCursorUpdates(boolean paramBoolean1, boolean paramBoolean2, View paramView)
  {
    if (!this.jdField_a_of_type_Boolean) {
      return false;
    }
    if ((this.jdField_c_of_type_Boolean) && (!paramBoolean2)) {
      invalidateLastCursorAnchorInfo();
    }
    this.jdField_c_of_type_Boolean = paramBoolean2;
    if (paramBoolean1)
    {
      this.jdField_b_of_type_Boolean = true;
      a(paramView);
    }
    return true;
  }
  
  public void onUpdateFrameInfo(float paramFloat1, float paramFloat2, boolean paramBoolean1, boolean paramBoolean2, float paramFloat3, float paramFloat4, float paramFloat5, View paramView)
  {
    if (!this.jdField_a_of_type_Boolean) {
      return;
    }
    this.jdField_a_of_type_OrgChromiumContentBrowserInputCursorAnchorInfoController$ViewDelegate.getLocationOnScreen(paramView, this.jdField_a_of_type_ArrayOfInt);
    int[] arrayOfInt = this.jdField_a_of_type_ArrayOfInt;
    float f1 = arrayOfInt[0];
    paramFloat2 = arrayOfInt[1] + paramFloat2;
    if ((!this.jdField_d_of_type_Boolean) || (paramFloat1 != this.jdField_a_of_type_Float) || (f1 != this.jdField_b_of_type_Float) || (paramFloat2 != this.jdField_c_of_type_Float) || (paramBoolean1 != this.jdField_e_of_type_Boolean) || (paramBoolean2 != this.jdField_f_of_type_Boolean) || (paramFloat3 != this.jdField_d_of_type_Float) || (paramFloat4 != this.jdField_e_of_type_Float) || (paramFloat5 != this.jdField_f_of_type_Float))
    {
      this.jdField_a_of_type_AndroidViewInputmethodCursorAnchorInfo = null;
      this.jdField_d_of_type_Boolean = true;
      this.jdField_a_of_type_Float = paramFloat1;
      this.jdField_b_of_type_Float = f1;
      this.jdField_c_of_type_Float = paramFloat2;
      this.jdField_e_of_type_Boolean = paramBoolean1;
      this.jdField_f_of_type_Boolean = paramBoolean2;
      this.jdField_d_of_type_Float = paramFloat3;
      this.jdField_e_of_type_Float = paramFloat4;
      this.jdField_f_of_type_Float = paramFloat5;
    }
    if ((this.jdField_b_of_type_Boolean) || ((this.jdField_c_of_type_Boolean) && (this.jdField_a_of_type_AndroidViewInputmethodCursorAnchorInfo == null))) {
      a(paramView);
    }
  }
  
  public void setCompositionCharacterBounds(float[] paramArrayOfFloat, View paramView)
  {
    if (!this.jdField_a_of_type_Boolean) {
      return;
    }
    if (!Arrays.equals(paramArrayOfFloat, this.jdField_a_of_type_ArrayOfFloat))
    {
      this.jdField_a_of_type_AndroidViewInputmethodCursorAnchorInfo = null;
      this.jdField_a_of_type_ArrayOfFloat = paramArrayOfFloat;
      if (this.jdField_d_of_type_Boolean) {
        a(paramView);
      }
    }
  }
  
  @VisibleForTesting
  public void setInputMethodManagerWrapper(InputMethodManagerWrapper paramInputMethodManagerWrapper)
  {
    this.jdField_a_of_type_OrgChromiumContent_publicBrowserInputMethodManagerWrapper = paramInputMethodManagerWrapper;
  }
  
  public static abstract interface ComposingTextDelegate
  {
    public abstract int getComposingTextEnd();
    
    public abstract int getComposingTextStart();
    
    public abstract int getSelectionEnd();
    
    public abstract int getSelectionStart();
    
    public abstract CharSequence getText();
  }
  
  public static abstract interface ViewDelegate
  {
    public abstract void getLocationOnScreen(View paramView, int[] paramArrayOfInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\input\CursorAnchorInfoController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */