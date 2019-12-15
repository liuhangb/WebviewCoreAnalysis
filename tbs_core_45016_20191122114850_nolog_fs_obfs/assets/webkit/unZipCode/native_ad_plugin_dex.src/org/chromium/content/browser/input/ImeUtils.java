package org.chromium.content.browser.input;

import android.view.inputmethod.EditorInfo;
import org.chromium.base.ThreadUtils;

public class ImeUtils
{
  private static int a(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    if ((paramInt3 == 0) && (paramInt1 == 3)) {
      return 3;
    }
    if (paramBoolean) {
      return 1;
    }
    if ((paramInt2 & 0x400) != 0) {
      return 5;
    }
    return 2;
  }
  
  static void a()
  {
    a("Should be on UI thread.", ThreadUtils.runningOnUiThread());
  }
  
  static void a(String paramString, boolean paramBoolean)
  {
    if (paramBoolean) {
      return;
    }
    throw new AssertionError(paramString);
  }
  
  static void a(boolean paramBoolean)
  {
    if (paramBoolean) {
      return;
    }
    throw new AssertionError();
  }
  
  public static void computeEditorInfo(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, EditorInfo paramEditorInfo)
  {
    paramEditorInfo.inputType = 161;
    if ((paramInt2 & 0x2) != 0) {
      paramEditorInfo.inputType |= 0x80000;
    }
    boolean bool = true;
    if (paramInt3 == 0)
    {
      if (paramInt1 == 1)
      {
        if ((paramInt2 & 0x8) == 0) {
          paramEditorInfo.inputType |= 0x8000;
        }
      }
      else if ((paramInt1 != 14) && (paramInt1 != 15))
      {
        if (paramInt1 == 2) {
          paramEditorInfo.inputType = 225;
        } else if (paramInt1 == 7) {
          paramEditorInfo.inputType = 17;
        } else if (paramInt1 == 4) {
          paramEditorInfo.inputType = 209;
        } else if (paramInt1 == 6) {
          paramEditorInfo.inputType = 3;
        } else if (paramInt1 == 5) {
          paramEditorInfo.inputType = 8194;
        }
      }
      else
      {
        paramEditorInfo.inputType |= 0x20000;
        if ((paramInt2 & 0x8) == 0) {
          paramEditorInfo.inputType |= 0x8000;
        }
      }
    }
    else {
      switch (paramInt3)
      {
      default: 
        paramEditorInfo.inputType |= 0x20000;
        if ((paramInt2 & 0x8) == 0) {
          paramEditorInfo.inputType |= 0x8000;
        }
        break;
      case 7: 
        paramEditorInfo.inputType = 8194;
        break;
      case 6: 
        paramEditorInfo.inputType = 2;
        break;
      case 5: 
        paramEditorInfo.inputType = 209;
        break;
      case 4: 
        paramEditorInfo.inputType = 17;
        break;
      case 3: 
        paramEditorInfo.inputType = 3;
      }
    }
    int i = paramEditorInfo.imeOptions;
    if ((paramEditorInfo.inputType & 0x20000) == 0) {
      bool = false;
    }
    paramEditorInfo.imeOptions = (a(paramInt1, paramInt2, paramInt3, bool) | i);
    if ((paramInt2 & 0x80) != 0) {
      paramEditorInfo.inputType |= 0x1000;
    } else if ((paramInt2 & 0x100) != 0) {
      paramEditorInfo.inputType |= 0x2000;
    } else if ((paramInt2 & 0x200) != 0) {
      paramEditorInfo.inputType |= 0x4000;
    }
    if ((paramInt2 & 0x1000) != 0) {
      paramEditorInfo.inputType = 225;
    }
    paramEditorInfo.initialSelStart = paramInt4;
    paramEditorInfo.initialSelEnd = paramInt5;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\input\ImeUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */