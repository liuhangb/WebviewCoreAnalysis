package org.chromium.tencent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.widget.DatePicker;
import android.widget.TimePicker;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import org.chromium.content.browser.picker.DateTimeSuggestion;

public class X5InputDialogContainer
{
  private static int sTextInputTypeDate = 8;
  private static int sTextInputTypeDateTime = 9;
  private static int sTextInputTypeDateTimeLocal = 10;
  private static int sTextInputTypeMonth = 11;
  private static int sTextInputTypeTime = 12;
  private static int sTextInputTypeWeek = 13;
  private final Context mContext;
  private AlertDialog mDialog;
  private boolean mDialogAlreadyDismissed;
  private final InputActionDelegate mInputActionDelegate;
  
  public X5InputDialogContainer(Context paramContext, InputActionDelegate paramInputActionDelegate)
  {
    this.mContext = paramContext;
    this.mInputActionDelegate = paramInputActionDelegate;
  }
  
  public static boolean isDialogInputType(int paramInt)
  {
    return (paramInt == sTextInputTypeDate) || (paramInt == sTextInputTypeTime) || (paramInt == sTextInputTypeDateTime) || (paramInt == sTextInputTypeDateTimeLocal) || (paramInt == sTextInputTypeMonth) || (paramInt == sTextInputTypeWeek);
  }
  
  public void dismissDialog()
  {
    try
    {
      if ((isDialogShowing()) && (isActivityAlive()))
      {
        this.mDialog.dismiss();
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  boolean isActivityAlive()
  {
    Context localContext = this.mContext;
    if ((localContext != null) && ((localContext instanceof Activity))) {
      return ((Activity)localContext).isFinishing() ^ true;
    }
    return false;
  }
  
  boolean isDialogShowing()
  {
    AlertDialog localAlertDialog = this.mDialog;
    return (localAlertDialog != null) && (localAlertDialog.isShowing());
  }
  
  protected void setFieldDateTimeValue(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9)
  {
    if (this.mDialogAlreadyDismissed) {
      return;
    }
    this.mDialogAlreadyDismissed = true;
    if (paramInt1 == sTextInputTypeMonth)
    {
      this.mInputActionDelegate.replaceDateTime((paramInt2 - 1970) * 12 + paramInt3);
      return;
    }
    if (paramInt1 == sTextInputTypeTime)
    {
      this.mInputActionDelegate.replaceDateTime(TimeUnit.HOURS.toMillis(paramInt5) + TimeUnit.MINUTES.toMillis(paramInt6) + TimeUnit.SECONDS.toMillis(paramInt7) + paramInt8);
      return;
    }
    Calendar localCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    localCalendar.clear();
    localCalendar.set(1, paramInt2);
    localCalendar.set(2, paramInt3);
    localCalendar.set(5, paramInt4);
    localCalendar.set(11, paramInt5);
    localCalendar.set(12, paramInt6);
    localCalendar.set(13, paramInt7);
    localCalendar.set(14, paramInt8);
    this.mInputActionDelegate.replaceDateTime(localCalendar.getTimeInMillis());
  }
  
  void showDialog()
  {
    this.mDialog.setOnDismissListener(new DialogInterface.OnDismissListener()
    {
      public void onDismiss(DialogInterface paramAnonymousDialogInterface)
      {
        if (!X5InputDialogContainer.this.mDialogAlreadyDismissed)
        {
          X5InputDialogContainer.access$502(X5InputDialogContainer.this, true);
          X5InputDialogContainer.this.mInputActionDelegate.cancelDateTimeDialog();
        }
      }
    });
    this.mDialogAlreadyDismissed = false;
    try
    {
      this.mDialog.show();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public void showDialog(final int paramInt, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, final DateTimeSuggestion[] paramArrayOfDateTimeSuggestion)
  {
    dismissDialog();
    if (Double.isNaN(paramDouble1))
    {
      paramArrayOfDateTimeSuggestion = Calendar.getInstance();
      paramArrayOfDateTimeSuggestion.set(14, 0);
    }
    else if (paramInt == sTextInputTypeMonth)
    {
      int i = (int)Math.min(paramDouble1 / 12.0D + 1970.0D, 2.147483647E9D);
      int j = (int)(paramDouble1 % 12.0D);
      paramArrayOfDateTimeSuggestion = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
      paramArrayOfDateTimeSuggestion.clear();
      paramArrayOfDateTimeSuggestion.set(i, j, 1);
    }
    else if (paramInt == sTextInputTypeWeek)
    {
      paramArrayOfDateTimeSuggestion = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
      paramArrayOfDateTimeSuggestion.clear();
      paramArrayOfDateTimeSuggestion.setFirstDayOfWeek(2);
      paramArrayOfDateTimeSuggestion.setMinimalDaysInFirstWeek(4);
      paramArrayOfDateTimeSuggestion.setTimeInMillis(paramDouble1);
    }
    else
    {
      paramArrayOfDateTimeSuggestion = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
      paramArrayOfDateTimeSuggestion.setGregorianChange(new Date(Long.MIN_VALUE));
      paramArrayOfDateTimeSuggestion.setTimeInMillis(paramDouble1);
    }
    try
    {
      if (paramInt != sTextInputTypeTime) {
        this.mDialog = new DatePickerDialog(this.mContext, new DatePickerDialog.OnDateSetListener()
        {
          public void onDateSet(DatePicker paramAnonymousDatePicker, final int paramAnonymousInt1, final int paramAnonymousInt2, final int paramAnonymousInt3)
          {
            if ((paramInt != X5InputDialogContainer.sTextInputTypeTime) && (paramInt != X5InputDialogContainer.sTextInputTypeDateTime) && (paramInt != X5InputDialogContainer.sTextInputTypeDateTimeLocal))
            {
              X5InputDialogContainer.this.setFieldDateTimeValue(paramInt, paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, 0, 0, 0, 0, 0);
              return;
            }
            if ((X5InputDialogContainer.this.mDialog instanceof TimePickerDialog)) {
              return;
            }
            X5InputDialogContainer.this.mDialog.setOnDismissListener(null);
            paramAnonymousDatePicker = X5InputDialogContainer.this;
            X5InputDialogContainer.access$302(paramAnonymousDatePicker, new TimePickerDialog(paramAnonymousDatePicker.mContext, new TimePickerDialog.OnTimeSetListener()
            {
              public void onTimeSet(TimePicker paramAnonymous2TimePicker, int paramAnonymous2Int1, int paramAnonymous2Int2)
              {
                X5InputDialogContainer.this.setFieldDateTimeValue(X5InputDialogContainer.1.this.val$type, paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymous2Int1, paramAnonymous2Int2, 0, 0, 0);
              }
            }, paramArrayOfDateTimeSuggestion.get(11), paramArrayOfDateTimeSuggestion.get(12), true));
            X5InputDialogContainer.this.showDialog();
          }
        }, paramArrayOfDateTimeSuggestion.get(1), paramArrayOfDateTimeSuggestion.get(2), paramArrayOfDateTimeSuggestion.get(5));
      } else {
        this.mDialog = new TimePickerDialog(this.mContext, new TimePickerDialog.OnTimeSetListener()
        {
          public void onTimeSet(TimePicker paramAnonymousTimePicker, int paramAnonymousInt1, int paramAnonymousInt2)
          {
            X5InputDialogContainer.this.setFieldDateTimeValue(paramInt, 0, 0, 0, paramAnonymousInt1, paramAnonymousInt2, 0, 0, 0);
          }
        }, paramArrayOfDateTimeSuggestion.get(11), paramArrayOfDateTimeSuggestion.get(12), true);
      }
      showDialog();
      return;
    }
    catch (Exception paramArrayOfDateTimeSuggestion)
    {
      paramArrayOfDateTimeSuggestion.printStackTrace();
    }
  }
  
  public static abstract interface InputActionDelegate
  {
    public abstract void cancelDateTimeDialog();
    
    public abstract void replaceDateTime(double paramDouble);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\X5InputDialogContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */