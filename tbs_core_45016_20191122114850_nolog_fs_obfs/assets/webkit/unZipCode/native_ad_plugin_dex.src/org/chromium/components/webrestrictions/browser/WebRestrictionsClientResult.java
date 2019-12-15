package org.chromium.components.webrestrictions.browser;

import android.database.Cursor;
import org.chromium.base.annotations.CalledByNative;

public class WebRestrictionsClientResult
{
  private final Cursor a;
  
  public WebRestrictionsClientResult(Cursor paramCursor)
  {
    this.a = paramCursor;
    if (paramCursor == null) {
      return;
    }
    paramCursor.moveToFirst();
  }
  
  @CalledByNative
  public int getColumnCount()
  {
    Cursor localCursor = this.a;
    if (localCursor == null) {
      return 0;
    }
    return localCursor.getColumnCount();
  }
  
  @CalledByNative
  public String getColumnName(int paramInt)
  {
    Cursor localCursor = this.a;
    if (localCursor == null) {
      return null;
    }
    return localCursor.getColumnName(paramInt);
  }
  
  @CalledByNative
  public int getInt(int paramInt)
  {
    Cursor localCursor = this.a;
    if (localCursor == null) {
      return 0;
    }
    return localCursor.getInt(paramInt);
  }
  
  @CalledByNative
  public String getString(int paramInt)
  {
    Cursor localCursor = this.a;
    if (localCursor == null) {
      return null;
    }
    return localCursor.getString(paramInt);
  }
  
  @CalledByNative
  public boolean isString(int paramInt)
  {
    Cursor localCursor = this.a;
    boolean bool = false;
    if (localCursor == null) {
      return false;
    }
    if (localCursor.getType(paramInt) == 3) {
      bool = true;
    }
    return bool;
  }
  
  @CalledByNative
  public boolean shouldProceed()
  {
    Cursor localCursor = this.a;
    boolean bool = false;
    if (localCursor == null) {
      return false;
    }
    if (localCursor.getInt(0) > 0) {
      bool = true;
    }
    return bool;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\components\webrestrictions\browser\WebRestrictionsClientResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */