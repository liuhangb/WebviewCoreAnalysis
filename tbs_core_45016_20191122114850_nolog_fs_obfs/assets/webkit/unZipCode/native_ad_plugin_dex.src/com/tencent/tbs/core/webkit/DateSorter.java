package com.tencent.tbs.core.webkit;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import java.util.Calendar;
import java.util.Locale;
import libcore.icu.LocaleData;

public class DateSorter
{
  public static final int DAY_COUNT = 5;
  private static final String LOGTAG = "webkit";
  public static final int NUM_DAYS_AGO = 7;
  private long[] mBins = new long[4];
  public String[] mLabels = new String[5];
  
  public DateSorter(Context paramContext)
  {
    Resources localResources = paramContext.getResources();
    Object localObject = Calendar.getInstance();
    beginningOfDay((Calendar)localObject);
    this.mBins[0] = ((Calendar)localObject).getTimeInMillis();
    ((Calendar)localObject).add(6, -1);
    this.mBins[1] = ((Calendar)localObject).getTimeInMillis();
    ((Calendar)localObject).add(6, -6);
    this.mBins[2] = ((Calendar)localObject).getTimeInMillis();
    ((Calendar)localObject).add(6, 7);
    ((Calendar)localObject).add(2, -1);
    this.mBins[3] = ((Calendar)localObject).getTimeInMillis();
    Locale localLocale = localResources.getConfiguration().locale;
    localObject = localLocale;
    if (localLocale == null) {
      localObject = Locale.getDefault();
    }
    localObject = LocaleData.get((Locale)localObject);
    this.mLabels[0] = ((LocaleData)localObject).today;
    this.mLabels[1] = ((LocaleData)localObject).yesterday;
    localObject = localResources.getQuantityString(18153490, 7);
    this.mLabels[2] = String.format((String)localObject, new Object[] { Integer.valueOf(7) });
    this.mLabels[3] = paramContext.getString(17040074);
    this.mLabels[4] = paramContext.getString(17040336);
  }
  
  private void beginningOfDay(Calendar paramCalendar)
  {
    paramCalendar.set(11, 0);
    paramCalendar.set(12, 0);
    paramCalendar.set(13, 0);
    paramCalendar.set(14, 0);
  }
  
  public long getBoundary(int paramInt)
  {
    int i;
    if (paramInt >= 0)
    {
      i = paramInt;
      if (paramInt <= 4) {}
    }
    else
    {
      i = 0;
    }
    if (i == 4) {
      return Long.MIN_VALUE;
    }
    return this.mBins[i];
  }
  
  public int getIndex(long paramLong)
  {
    int i = 0;
    while (i < 4)
    {
      if (paramLong > this.mBins[i]) {
        return i;
      }
      i += 1;
    }
    return 4;
  }
  
  public String getLabel(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < 5)) {
      return this.mLabels[paramInt];
    }
    return "";
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\DateSorter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */