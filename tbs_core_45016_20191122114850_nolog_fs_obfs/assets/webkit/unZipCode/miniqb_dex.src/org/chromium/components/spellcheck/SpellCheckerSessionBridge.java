package org.chromium.components.spellcheck;

import android.content.Context;
import android.os.SystemClock;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SpellCheckerSession;
import android.view.textservice.SpellCheckerSession.SpellCheckerSessionListener;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextInfo;
import android.view.textservice.TextServicesManager;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.chromium.base.ContextUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.metrics.RecordHistogram;

public class SpellCheckerSessionBridge
  implements SpellCheckerSession.SpellCheckerSessionListener
{
  private long jdField_a_of_type_Long;
  private final SpellCheckerSession jdField_a_of_type_AndroidViewTextserviceSpellCheckerSession;
  private long b;
  private long c;
  
  private SpellCheckerSessionBridge(long paramLong)
  {
    this.jdField_a_of_type_Long = paramLong;
    this.jdField_a_of_type_AndroidViewTextserviceSpellCheckerSession = ((TextServicesManager)ContextUtils.getApplicationContext().getSystemService("textservices")).newSpellCheckerSession(null, null, this, true);
  }
  
  private int[] a(ArrayList<Integer> paramArrayList)
  {
    int[] arrayOfInt = new int[paramArrayList.size()];
    int i = 0;
    while (i < arrayOfInt.length)
    {
      arrayOfInt[i] = ((Integer)paramArrayList.get(i)).intValue();
      i += 1;
    }
    return arrayOfInt;
  }
  
  @CalledByNative
  private static SpellCheckerSessionBridge create(long paramLong)
  {
    SpellCheckerSessionBridge localSpellCheckerSessionBridge = new SpellCheckerSessionBridge(paramLong);
    if (localSpellCheckerSessionBridge.jdField_a_of_type_AndroidViewTextserviceSpellCheckerSession == null) {
      return null;
    }
    return localSpellCheckerSessionBridge;
  }
  
  @CalledByNative
  private void disconnect()
  {
    this.jdField_a_of_type_Long = 0L;
    this.jdField_a_of_type_AndroidViewTextserviceSpellCheckerSession.cancel();
    this.jdField_a_of_type_AndroidViewTextserviceSpellCheckerSession.close();
  }
  
  private native void nativeProcessSpellCheckResults(long paramLong, int[] paramArrayOfInt1, int[] paramArrayOfInt2, String[][] paramArrayOfString);
  
  @CalledByNative
  private void requestTextCheck(String paramString)
  {
    String str = paramString;
    if (paramString.endsWith(".")) {
      str = paramString.substring(0, paramString.length() - 1);
    }
    this.b = SystemClock.elapsedRealtime();
    this.jdField_a_of_type_AndroidViewTextserviceSpellCheckerSession.getSentenceSuggestions(new TextInfo[] { new TextInfo(str) }, 0);
  }
  
  public void onGetSentenceSuggestions(SentenceSuggestionsInfo[] paramArrayOfSentenceSuggestionsInfo)
  {
    this.c = SystemClock.elapsedRealtime();
    if (this.jdField_a_of_type_Long == 0L) {
      return;
    }
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    ArrayList localArrayList3 = new ArrayList();
    int m = paramArrayOfSentenceSuggestionsInfo.length;
    int i = 0;
    while (i < m)
    {
      SentenceSuggestionsInfo localSentenceSuggestionsInfo = paramArrayOfSentenceSuggestionsInfo[i];
      if (localSentenceSuggestionsInfo != null)
      {
        int j = 0;
        while (j < localSentenceSuggestionsInfo.getSuggestionsCount())
        {
          if ((localSentenceSuggestionsInfo.getSuggestionsInfoAt(j).getSuggestionsAttributes() & 0x2) == 2)
          {
            localArrayList1.add(Integer.valueOf(localSentenceSuggestionsInfo.getOffsetAt(j)));
            localArrayList2.add(Integer.valueOf(localSentenceSuggestionsInfo.getLengthAt(j)));
            SuggestionsInfo localSuggestionsInfo = localSentenceSuggestionsInfo.getSuggestionsInfoAt(j);
            ArrayList localArrayList4 = new ArrayList();
            int k = 0;
            while (k < localSuggestionsInfo.getSuggestionsCount())
            {
              String str2 = localSuggestionsInfo.getSuggestionAt(k);
              String str1 = str2;
              if (str2.charAt(str2.length() - 1) == '​') {
                str1 = str2.substring(0, str2.length() - 1);
              }
              localArrayList4.add(str1);
              k += 1;
            }
            localArrayList3.add(localArrayList4.toArray(new String[localArrayList4.size()]));
          }
          j += 1;
        }
      }
      i += 1;
    }
    nativeProcessSpellCheckResults(this.jdField_a_of_type_Long, a(localArrayList1), a(localArrayList2), (String[][])localArrayList3.toArray(new String[localArrayList3.size()][]));
    RecordHistogram.recordTimesHistogram("SpellCheck.Android.Latency", this.c - this.b, TimeUnit.MILLISECONDS);
  }
  
  public void onGetSuggestions(SuggestionsInfo[] paramArrayOfSuggestionsInfo) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\components\spellcheck\SpellCheckerSessionBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */