package com.tencent.tbs.common.utils;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DomainMatcher
{
  public Hashtable<String, String> domainTable = new Hashtable();
  private ArrayList<String> fastRegCheckList = new ArrayList();
  private ArrayList<Pattern> patternList = new ArrayList();
  
  private String convertToRegexDomain(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int k = paramString.length();
    int i = 0;
    while (i < k)
    {
      char c = paramString.charAt(i);
      int j = i + 3;
      if ((j < k) && (c == '('))
      {
        int m = i + 1;
        if (paramString.charAt(m) == '.')
        {
          int n = i + 2;
          if ((paramString.charAt(n) == '*') && (paramString.charAt(j) == ')'))
          {
            localStringBuffer.append(c);
            localStringBuffer.append(paramString.charAt(m));
            localStringBuffer.append(paramString.charAt(n));
            localStringBuffer.append(paramString.charAt(j));
            i = j;
            break label170;
          }
        }
      }
      if (c == '*')
      {
        localStringBuffer.append('.');
        localStringBuffer.append(c);
      }
      else
      {
        localStringBuffer.append(c);
      }
      label170:
      i += 1;
    }
    return localStringBuffer.toString();
  }
  
  private boolean isFastCheckDomain(String paramString)
  {
    return (paramString.length() > 3) && (paramString.startsWith("*")) && (!paramString.substring(1).contains("*"));
  }
  
  private boolean isRegexDomain(String paramString)
  {
    int i = 0;
    while (i < paramString.length())
    {
      int j = paramString.charAt(i);
      if (((j < 97) || (j > 122)) && (j != 46) && (j != 45) && ((j < 48) || (j > 57)) && ((j < 65) || (j > 90)) && ((j < 19968) || (j > 40959))) {
        return true;
      }
      i += 1;
    }
    return false;
  }
  
  public void addDomain(String paramString)
  {
    if (isFastCheckDomain(paramString))
    {
      this.fastRegCheckList.add(paramString.substring(1));
      return;
    }
    if (isRegexDomain(paramString))
    {
      paramString = convertToRegexDomain(paramString);
      this.patternList.add(Pattern.compile(paramString));
      return;
    }
    this.domainTable.put(paramString, "");
  }
  
  public void addDomainList(ArrayList<String> paramArrayList)
  {
    if (paramArrayList != null)
    {
      if (paramArrayList.size() == 0) {
        return;
      }
      int i = 0;
      while (i < paramArrayList.size())
      {
        addDomain(((String)paramArrayList.get(i)).toLowerCase());
        i += 1;
      }
      return;
    }
  }
  
  public void clear()
  {
    this.domainTable.clear();
    this.patternList.clear();
    this.fastRegCheckList.clear();
  }
  
  public boolean isContainsDomain(String paramString)
  {
    if (paramString == null) {
      return false;
    }
    paramString = paramString.toLowerCase();
    if (this.domainTable.containsKey(paramString)) {
      return true;
    }
    if (!this.fastRegCheckList.isEmpty())
    {
      i = 0;
      while (i < this.fastRegCheckList.size())
      {
        if (paramString.endsWith((String)this.fastRegCheckList.get(i))) {
          return true;
        }
        i += 1;
      }
    }
    int i = 0;
    while (i < this.patternList.size())
    {
      if (((Pattern)this.patternList.get(i)).matcher(paramString).find()) {
        return true;
      }
      i += 1;
    }
    return false;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder1 = new StringBuilder();
    localStringBuilder1.append("\r\n1> DomainTable:\r\n");
    Iterator localIterator = this.domainTable.keySet().iterator();
    Object localObject1;
    Object localObject2;
    while (localIterator.hasNext())
    {
      localObject1 = (String)localIterator.next();
      localObject2 = (String)this.domainTable.get(localObject1);
      StringBuilder localStringBuilder2 = new StringBuilder();
      localStringBuilder2.append("<");
      localStringBuilder2.append((String)localObject1);
      localStringBuilder2.append(",");
      localStringBuilder2.append((String)localObject2);
      localStringBuilder2.append(">\t");
      localStringBuilder1.append(localStringBuilder2.toString());
    }
    localStringBuilder1.append("\r\n2> PatternList:\r\n");
    localIterator = this.patternList.iterator();
    while (localIterator.hasNext())
    {
      localObject1 = (Pattern)localIterator.next();
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append(((Pattern)localObject1).toString());
      ((StringBuilder)localObject2).append("\t");
      localStringBuilder1.append(((StringBuilder)localObject2).toString());
    }
    localStringBuilder1.append("\r\n3> RegressionList:\r\n");
    localIterator = this.fastRegCheckList.iterator();
    while (localIterator.hasNext())
    {
      localObject1 = (String)localIterator.next();
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("\t");
      localStringBuilder1.append(((StringBuilder)localObject2).toString());
    }
    return localStringBuilder1.toString();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\utils\DomainMatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */