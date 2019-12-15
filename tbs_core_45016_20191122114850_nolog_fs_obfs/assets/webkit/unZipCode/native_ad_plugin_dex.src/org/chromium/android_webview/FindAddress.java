package org.chromium.android_webview;

import java.util.Locale;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.chromium.base.VisibleForTesting;

@VisibleForTesting
public class FindAddress
{
  private static final Pattern jdField_a_of_type_JavaUtilRegexPattern = Pattern.compile("[^,*•\t                　\n\013\f\r  ]+(?=[,*•\t                　\n\013\f\r  ]|$)", 2);
  private static final ZipRange[] jdField_a_of_type_ArrayOfOrgChromiumAndroid_webviewFindAddress$ZipRange = { new ZipRange(99, 99, -1, -1), new ZipRange(35, 36, -1, -1), new ZipRange(71, 72, -1, -1), new ZipRange(96, 96, -1, -1), new ZipRange(85, 86, -1, -1), new ZipRange(90, 96, -1, -1), new ZipRange(80, 81, -1, -1), new ZipRange(6, 6, -1, -1), new ZipRange(20, 20, -1, -1), new ZipRange(19, 19, -1, -1), new ZipRange(32, 34, -1, -1), new ZipRange(96, 96, -1, -1), new ZipRange(30, 31, -1, -1), new ZipRange(96, 96, -1, -1), new ZipRange(96, 96, -1, -1), new ZipRange(50, 52, -1, -1), new ZipRange(83, 83, -1, -1), new ZipRange(60, 62, -1, -1), new ZipRange(46, 47, -1, -1), new ZipRange(66, 67, 73, -1), new ZipRange(40, 42, -1, -1), new ZipRange(70, 71, -1, -1), new ZipRange(1, 2, -1, -1), new ZipRange(20, 21, -1, -1), new ZipRange(3, 4, -1, -1), new ZipRange(96, 96, -1, -1), new ZipRange(48, 49, -1, -1), new ZipRange(55, 56, -1, -1), new ZipRange(63, 65, -1, -1), new ZipRange(96, 96, -1, -1), new ZipRange(38, 39, -1, -1), new ZipRange(55, 56, -1, -1), new ZipRange(27, 28, -1, -1), new ZipRange(58, 58, -1, -1), new ZipRange(68, 69, -1, -1), new ZipRange(3, 4, -1, -1), new ZipRange(7, 8, -1, -1), new ZipRange(87, 88, 86, -1), new ZipRange(88, 89, 96, -1), new ZipRange(10, 14, 0, 6), new ZipRange(43, 45, -1, -1), new ZipRange(73, 74, -1, -1), new ZipRange(97, 97, -1, -1), new ZipRange(15, 19, -1, -1), new ZipRange(6, 6, 0, 9), new ZipRange(96, 96, -1, -1), new ZipRange(2, 2, -1, -1), new ZipRange(29, 29, -1, -1), new ZipRange(57, 57, -1, -1), new ZipRange(37, 38, -1, -1), new ZipRange(75, 79, 87, 88), new ZipRange(84, 84, -1, -1), new ZipRange(22, 24, 20, -1), new ZipRange(6, 9, -1, -1), new ZipRange(5, 5, -1, -1), new ZipRange(98, 99, -1, -1), new ZipRange(53, 54, -1, -1), new ZipRange(24, 26, -1, -1), new ZipRange(82, 83, -1, -1) };
  private static final Pattern b = Pattern.compile("(?:one|\\d+([a-z](?=[^a-z]|$)|st|nd|rd|th)?)(?:-(?:one|\\d+([a-z](?=[^a-z]|$)|st|nd|rd|th)?))*(?=[,\"'\t                　\n\013\f\r  ]|$)", 2);
  private static final Pattern c = Pattern.compile("(?:(ak|alaska)|(al|alabama)|(ar|arkansas)|(as|american[\t                　]+samoa)|(az|arizona)|(ca|california)|(co|colorado)|(ct|connecticut)|(dc|district[\t                　]+of[\t                　]+columbia)|(de|delaware)|(fl|florida)|(fm|federated[\t                　]+states[\t                　]+of[\t                　]+micronesia)|(ga|georgia)|(gu|guam)|(hi|hawaii)|(ia|iowa)|(id|idaho)|(il|illinois)|(in|indiana)|(ks|kansas)|(ky|kentucky)|(la|louisiana)|(ma|massachusetts)|(md|maryland)|(me|maine)|(mh|marshall[\t                　]+islands)|(mi|michigan)|(mn|minnesota)|(mo|missouri)|(mp|northern[\t                　]+mariana[\t                　]+islands)|(ms|mississippi)|(mt|montana)|(nc|north[\t                　]+carolina)|(nd|north[\t                　]+dakota)|(ne|nebraska)|(nh|new[\t                　]+hampshire)|(nj|new[\t                　]+jersey)|(nm|new[\t                　]+mexico)|(nv|nevada)|(ny|new[\t                　]+york)|(oh|ohio)|(ok|oklahoma)|(or|oregon)|(pa|pennsylvania)|(pr|puerto[\t                　]+rico)|(pw|palau)|(ri|rhode[\t                　]+island)|(sc|south[\t                　]+carolina)|(sd|south[\t                　]+dakota)|(tn|tennessee)|(tx|texas)|(ut|utah)|(va|virginia)|(vi|virgin[\t                　]+islands)|(vt|vermont)|(wa|washington)|(wi|wisconsin)|(wv|west[\t                　]+virginia)|(wy|wyoming))(?=[,*•\t                　\n\013\f\r  ]|$)", 2);
  private static final Pattern d = Pattern.compile("(?:alley|annex|arcade|ave[.]?|avenue|alameda|bayou|beach|bend|bluffs?|bottom|boulevard|branch|bridge|brooks?|burgs?|bypass|broadway|camino|camp|canyon|cape|causeway|centers?|circles?|cliffs?|club|common|corners?|course|courts?|coves?|creek|crescent|crest|crossing|crossroad|curve|circulo|dale|dam|divide|drives?|estates?|expressway|extensions?|falls?|ferry|fields?|flats?|fords?|forest|forges?|forks?|fort|freeway|gardens?|gateway|glens?|greens?|groves?|harbors?|haven|heights|highway|hills?|hollow|inlet|islands?|isle|junctions?|keys?|knolls?|lakes?|land|landing|lane|lights?|loaf|locks?|lodge|loop|mall|manors?|meadows?|mews|mills?|mission|motorway|mount|mountains?|neck|orchard|oval|overpass|parks?|parkways?|pass|passage|path|pike|pines?|plains?|plaza|points?|ports?|prairie|privada|radial|ramp|ranch|rapids?|rd[.]?|rest|ridges?|river|roads?|route|row|rue|run|shoals?|shores?|skyway|springs?|spurs?|squares?|station|stravenue|stream|st[.]?|streets?|summit|speedway|terrace|throughway|trace|track|trafficway|trail|tunnel|turnpike|underpass|unions?|valleys?|viaduct|views?|villages?|ville|vista|walks?|wall|ways?|wells?|xing|xrd)(?=[,*•\t                　\n\013\f\r  ]|$)", 2);
  private static final Pattern e = Pattern.compile("(\\d+)(st|nd|rd|th)", 2);
  private static final Pattern f = Pattern.compile("(?:\\d{5}(?:-\\d{4})?)(?=[,*•\t                　\n\013\f\r  ]|$)", 2);
  
  private static int a(String paramString, MatchResult paramMatchResult)
  {
    int j = paramMatchResult.end();
    Matcher localMatcher = jdField_a_of_type_JavaUtilRegexPattern.matcher(paramString);
    paramMatchResult = "";
    int i2 = 1;
    int i3 = 1;
    int i1 = 1;
    int n = 0;
    int k = -1;
    int i;
    int i6;
    for (int m = -1;; m = i6)
    {
      i = j;
      if (j >= paramString.length()) {
        break;
      }
      if (!localMatcher.find(j)) {
        return -paramString.length();
      }
      if (localMatcher.end() - localMatcher.start() > 25) {
        return -localMatcher.end();
      }
      while (j < localMatcher.start())
      {
        i = i2;
        if ("\n\013\f\r  ".indexOf(paramString.charAt(j)) != -1) {
          i = i2 + 1;
        }
        j += 1;
        i2 = i;
      }
      if (i2 > 5)
      {
        i = j;
        break;
      }
      i3 += 1;
      if (i3 > 14)
      {
        i = j;
        break;
      }
      int i4;
      int i5;
      if (matchHouseNumber(paramString, j) != null)
      {
        if ((i1 != 0) && (i2 > 1)) {
          return -j;
        }
        i = i1;
        i4 = n;
        i5 = k;
        i6 = m;
        if (k == -1)
        {
          i = i1;
          i4 = n;
          i5 = j;
          i6 = m;
        }
      }
      else if (isValidLocationName(localMatcher.group(0)))
      {
        i = 0;
        i4 = 1;
        i5 = k;
        i6 = m;
      }
      else
      {
        if ((i3 == 5) && (n == 0))
        {
          i = localMatcher.end();
          break;
        }
        if ((n != 0) && (i3 > 4))
        {
          MatchResult localMatchResult = matchState(paramString, j);
          if (localMatchResult != null)
          {
            if ((paramMatchResult.equals("et")) && (localMatchResult.group(0).equals("al")))
            {
              i = localMatchResult.end();
              break;
            }
            paramMatchResult = jdField_a_of_type_JavaUtilRegexPattern.matcher(paramString);
            if ((paramMatchResult.find(localMatchResult.end())) && (a(paramMatchResult.group(0), localMatchResult))) {
              return paramMatchResult.end();
            }
            i6 = localMatchResult.end();
            i = 0;
            i4 = n;
            i5 = k;
            break label398;
          }
        }
        i = 0;
        i6 = m;
        i5 = k;
        i4 = n;
      }
      label398:
      paramMatchResult = localMatcher.group(0);
      j = localMatcher.end();
      i1 = i;
      n = i4;
      k = i5;
    }
    if (m > 0) {
      return m;
    }
    if (k > 0) {
      i = k;
    }
    return -i;
  }
  
  private static boolean a(String paramString)
  {
    int i = 0;
    int k;
    for (int j = 0; i < paramString.length(); j = k)
    {
      k = j;
      if (Character.isDigit(paramString.charAt(i))) {
        k = j + 1;
      }
      i += 1;
    }
    if (j > 5) {
      return false;
    }
    paramString = e.matcher(paramString);
    if (paramString.find())
    {
      i = Integer.parseInt(paramString.group(1));
      if (i == 0) {
        return false;
      }
      String str = paramString.group(2).toLowerCase(Locale.getDefault());
      switch (i % 10)
      {
      default: 
        return str.equals("th");
      case 3: 
        if (i % 100 == 13) {
          paramString = "th";
        } else {
          paramString = "rd";
        }
        return str.equals(paramString);
      case 2: 
        if (i % 100 == 12) {
          paramString = "th";
        } else {
          paramString = "nd";
        }
        return str.equals(paramString);
      }
      if (i % 100 == 11) {
        paramString = "th";
      } else {
        paramString = "st";
      }
      return str.equals(paramString);
    }
    return true;
  }
  
  private static boolean a(String paramString, MatchResult paramMatchResult)
  {
    boolean bool2 = false;
    if (paramMatchResult == null) {
      return false;
    }
    int j;
    for (int i = paramMatchResult.groupCount();; i = j)
    {
      j = i;
      if (i <= 0) {
        break;
      }
      j = i - 1;
      if (paramMatchResult.group(i) != null) {
        break;
      }
    }
    boolean bool1 = bool2;
    if (f.matcher(paramString).matches())
    {
      bool1 = bool2;
      if (jdField_a_of_type_ArrayOfOrgChromiumAndroid_webviewFindAddress$ZipRange[j].a(paramString)) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  @VisibleForTesting
  public static String findAddress(String paramString)
  {
    Matcher localMatcher = b.matcher(paramString);
    int i = 0;
    while (localMatcher.find(i)) {
      if (a(localMatcher.group(0)))
      {
        i = localMatcher.start();
        int j = a(paramString, localMatcher);
        if (j > 0) {
          return paramString.substring(i, j);
        }
        i = -j;
      }
      else
      {
        i = localMatcher.end();
      }
    }
    return null;
  }
  
  @VisibleForTesting
  public static boolean isValidLocationName(String paramString)
  {
    return d.matcher(paramString).matches();
  }
  
  @VisibleForTesting
  public static boolean isValidZipCode(String paramString)
  {
    return f.matcher(paramString).matches();
  }
  
  @VisibleForTesting
  public static boolean isValidZipCode(String paramString1, String paramString2)
  {
    return a(paramString1, matchState(paramString2, 0));
  }
  
  @VisibleForTesting
  public static MatchResult matchHouseNumber(String paramString, int paramInt)
  {
    if ((paramInt > 0) && (":,\"'\t                　\n\013\f\r  ".indexOf(paramString.charAt(paramInt - 1)) == -1)) {
      return null;
    }
    paramString = b.matcher(paramString).region(paramInt, paramString.length());
    if (paramString.lookingAt())
    {
      paramString = paramString.toMatchResult();
      if (a(paramString.group(0))) {
        return paramString;
      }
    }
    return null;
  }
  
  @VisibleForTesting
  public static MatchResult matchState(String paramString, int paramInt)
  {
    Object localObject = null;
    if ((paramInt > 0) && (",*•\t                　\n\013\f\r  ".indexOf(paramString.charAt(paramInt - 1)) == -1)) {
      return null;
    }
    Matcher localMatcher = c.matcher(paramString).region(paramInt, paramString.length());
    paramString = (String)localObject;
    if (localMatcher.lookingAt()) {
      paramString = localMatcher.toMatchResult();
    }
    return paramString;
  }
  
  static class ZipRange
  {
    int a;
    int b;
    int c;
    int d;
    
    ZipRange(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      this.a = paramInt1;
      this.b = paramInt2;
      this.c = paramInt3;
      this.d = paramInt3;
    }
    
    boolean a(String paramString)
    {
      boolean bool = false;
      int i = Integer.parseInt(paramString.substring(0, 2));
      if (((this.a <= i) && (i <= this.b)) || (i == this.c) || (i == this.d)) {
        bool = true;
      }
      return bool;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\FindAddress.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */