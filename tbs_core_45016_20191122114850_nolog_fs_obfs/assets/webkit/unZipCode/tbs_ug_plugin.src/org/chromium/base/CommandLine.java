package org.chromium.base;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;
import org.chromium.base.annotations.MainDex;

@MainDex
public abstract class CommandLine
{
  private static final AtomicReference<CommandLine> jdField_a_of_type_JavaUtilConcurrentAtomicAtomicReference = new AtomicReference();
  
  private static void a(CommandLine paramCommandLine)
  {
    paramCommandLine = (CommandLine)jdField_a_of_type_JavaUtilConcurrentAtomicAtomicReference.getAndSet(paramCommandLine);
    if (paramCommandLine != null) {
      paramCommandLine.a();
    }
  }
  
  /* Error */
  private static char[] a(String paramString)
  {
    // Byte code:
    //   0: new 72	java/io/File
    //   3: dup
    //   4: aload_0
    //   5: invokespecial 74	java/io/File:<init>	(Ljava/lang/String;)V
    //   8: astore_0
    //   9: new 76	java/io/FileReader
    //   12: dup
    //   13: aload_0
    //   14: invokespecial 79	java/io/FileReader:<init>	(Ljava/io/File;)V
    //   17: astore_2
    //   18: aload_0
    //   19: invokevirtual 83	java/io/File:length	()J
    //   22: l2i
    //   23: newarray <illegal type>
    //   25: astore_0
    //   26: aload_0
    //   27: iconst_0
    //   28: aload_2
    //   29: aload_0
    //   30: invokevirtual 87	java/io/FileReader:read	([C)I
    //   33: invokestatic 93	java/util/Arrays:copyOfRange	([CII)[C
    //   36: astore_0
    //   37: aload_2
    //   38: invokevirtual 96	java/io/FileReader:close	()V
    //   41: aload_0
    //   42: areturn
    //   43: astore_0
    //   44: aconst_null
    //   45: astore_1
    //   46: goto +7 -> 53
    //   49: astore_1
    //   50: aload_1
    //   51: athrow
    //   52: astore_0
    //   53: aload_1
    //   54: ifnull +19 -> 73
    //   57: aload_2
    //   58: invokevirtual 96	java/io/FileReader:close	()V
    //   61: goto +16 -> 77
    //   64: astore_2
    //   65: aload_1
    //   66: aload_2
    //   67: invokevirtual 100	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   70: goto +7 -> 77
    //   73: aload_2
    //   74: invokevirtual 96	java/io/FileReader:close	()V
    //   77: aload_0
    //   78: athrow
    //   79: astore_0
    //   80: aconst_null
    //   81: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	82	0	paramString	String
    //   45	1	1	localObject	Object
    //   49	17	1	localThrowable1	Throwable
    //   17	41	2	localFileReader	java.io.FileReader
    //   64	10	2	localThrowable2	Throwable
    // Exception table:
    //   from	to	target	type
    //   18	37	43	finally
    //   18	37	49	java/lang/Throwable
    //   50	52	52	finally
    //   57	61	64	java/lang/Throwable
    //   9	18	79	java/io/IOException
    //   37	41	79	java/io/IOException
    //   57	61	79	java/io/IOException
    //   65	70	79	java/io/IOException
    //   73	77	79	java/io/IOException
    //   77	79	79	java/io/IOException
  }
  
  @VisibleForTesting
  static String[] a(char[] paramArrayOfChar)
  {
    if (paramArrayOfChar.length <= 65536)
    {
      ArrayList localArrayList = new ArrayList();
      int m = paramArrayOfChar.length;
      localObject1 = null;
      int i = 0;
      int k;
      for (int j = 0; i < m; j = k)
      {
        char c = paramArrayOfChar[i];
        Object localObject2;
        if (((j == 0) && ((c == '\'') || (c == '"'))) || (c == j))
        {
          if ((localObject1 != null) && (((StringBuilder)localObject1).length() > 0) && (((StringBuilder)localObject1).charAt(((StringBuilder)localObject1).length() - 1) == '\\'))
          {
            ((StringBuilder)localObject1).setCharAt(((StringBuilder)localObject1).length() - 1, c);
            localObject2 = localObject1;
            k = j;
          }
          else
          {
            if (j == 0) {
              j = c;
            } else {
              j = 0;
            }
            localObject2 = localObject1;
            k = j;
          }
        }
        else if ((j == 0) && (Character.isWhitespace(c)))
        {
          localObject2 = localObject1;
          k = j;
          if (localObject1 != null)
          {
            localArrayList.add(((StringBuilder)localObject1).toString());
            localObject2 = null;
            k = j;
          }
        }
        else
        {
          localObject2 = localObject1;
          if (localObject1 == null) {
            localObject2 = new StringBuilder();
          }
          ((StringBuilder)localObject2).append(c);
          k = j;
        }
        i += 1;
        localObject1 = localObject2;
      }
      if (localObject1 != null) {
        localArrayList.add(((StringBuilder)localObject1).toString());
      }
      return (String[])localArrayList.toArray(new String[localArrayList.size()]);
    }
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("Flags file too big: ");
    ((StringBuilder)localObject1).append(paramArrayOfChar.length);
    throw new RuntimeException(((StringBuilder)localObject1).toString());
  }
  
  public static void enableNativeProxy()
  {
    jdField_a_of_type_JavaUtilConcurrentAtomicAtomicReference.set(new NativeCommandLine(getJavaSwitchesOrNull()));
  }
  
  @VisibleForTesting
  public static CommandLine getInstance()
  {
    CommandLine localCommandLine = (CommandLine)jdField_a_of_type_JavaUtilConcurrentAtomicAtomicReference.get();
    if (!jdField_a_of_type_Boolean)
    {
      if (localCommandLine != null) {
        return localCommandLine;
      }
      throw new AssertionError();
    }
    return localCommandLine;
  }
  
  @Nullable
  public static String[] getJavaSwitchesOrNull()
  {
    CommandLine localCommandLine = (CommandLine)jdField_a_of_type_JavaUtilConcurrentAtomicAtomicReference.get();
    if (localCommandLine != null) {
      return localCommandLine.getCommandLineArguments();
    }
    return null;
  }
  
  public static void init(@Nullable String[] paramArrayOfString)
  {
    a(new JavaCommandLine(paramArrayOfString));
  }
  
  public static void initFromFile(String paramString)
  {
    paramString = a(paramString);
    if (paramString == null) {
      paramString = null;
    } else {
      paramString = a(paramString);
    }
    init(paramString);
  }
  
  public static boolean isInitialized()
  {
    return jdField_a_of_type_JavaUtilConcurrentAtomicAtomicReference.get() != null;
  }
  
  private static native void nativeAppendSwitch(String paramString);
  
  private static native void nativeAppendSwitchWithValue(String paramString1, String paramString2);
  
  private static native void nativeAppendSwitchesAndArguments(String[] paramArrayOfString);
  
  private static native String nativeGetSwitchValue(String paramString);
  
  private static native boolean nativeHasSwitch(String paramString);
  
  private static native void nativeInit(String[] paramArrayOfString);
  
  @VisibleForTesting
  public static void reset()
  {
    a(null);
  }
  
  protected void a() {}
  
  @VisibleForTesting
  public abstract void appendSwitch(String paramString);
  
  public abstract void appendSwitchWithValue(String paramString1, String paramString2);
  
  public abstract void appendSwitchesAndArguments(String[] paramArrayOfString);
  
  protected abstract String[] getCommandLineArguments();
  
  public abstract String getSwitchValue(String paramString);
  
  public String getSwitchValue(String paramString1, String paramString2)
  {
    String str = getSwitchValue(paramString1);
    paramString1 = str;
    if (TextUtils.isEmpty(str)) {
      paramString1 = paramString2;
    }
    return paramString1;
  }
  
  @VisibleForTesting
  public abstract boolean hasSwitch(String paramString);
  
  public boolean isNativeImplementation()
  {
    return false;
  }
  
  private static class JavaCommandLine
    extends CommandLine
  {
    private int jdField_a_of_type_Int = 1;
    private ArrayList<String> jdField_a_of_type_JavaUtilArrayList = new ArrayList();
    private HashMap<String, String> jdField_a_of_type_JavaUtilHashMap = new HashMap();
    
    JavaCommandLine(@Nullable String[] paramArrayOfString)
    {
      super();
      if ((paramArrayOfString != null) && (paramArrayOfString.length != 0) && (paramArrayOfString[0] != null))
      {
        this.jdField_a_of_type_JavaUtilArrayList.add(paramArrayOfString[0]);
        a(paramArrayOfString, 1);
      }
      else
      {
        this.jdField_a_of_type_JavaUtilArrayList.add("");
      }
      if (!b)
      {
        if (this.jdField_a_of_type_JavaUtilArrayList.size() > 0) {
          return;
        }
        throw new AssertionError();
      }
    }
    
    private void a(String[] paramArrayOfString, int paramInt)
    {
      int k = paramArrayOfString.length;
      int j = paramInt;
      int i = 0;
      paramInt = 1;
      while (i < k)
      {
        String str = paramArrayOfString[i];
        if (j > 0)
        {
          j -= 1;
        }
        else
        {
          if (str.equals("--")) {
            paramInt = 0;
          }
          if ((paramInt != 0) && (str.startsWith("--")))
          {
            String[] arrayOfString = str.split("=", 2);
            if (arrayOfString.length > 1) {
              str = arrayOfString[1];
            } else {
              str = null;
            }
            appendSwitchWithValue(arrayOfString[0].substring(2), str);
          }
          else
          {
            this.jdField_a_of_type_JavaUtilArrayList.add(str);
          }
        }
        i += 1;
      }
    }
    
    public void appendSwitch(String paramString)
    {
      appendSwitchWithValue(paramString, null);
    }
    
    public void appendSwitchWithValue(String paramString1, String paramString2)
    {
      HashMap localHashMap = this.jdField_a_of_type_JavaUtilHashMap;
      if (paramString2 == null) {
        localObject = "";
      } else {
        localObject = paramString2;
      }
      localHashMap.put(paramString1, localObject);
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("--");
      ((StringBuilder)localObject).append(paramString1);
      localObject = ((StringBuilder)localObject).toString();
      paramString1 = (String)localObject;
      if (paramString2 != null)
      {
        paramString1 = (String)localObject;
        if (!paramString2.isEmpty())
        {
          paramString1 = new StringBuilder();
          paramString1.append((String)localObject);
          paramString1.append("=");
          paramString1.append(paramString2);
          paramString1 = paramString1.toString();
        }
      }
      paramString2 = this.jdField_a_of_type_JavaUtilArrayList;
      int i = this.jdField_a_of_type_Int;
      this.jdField_a_of_type_Int = (i + 1);
      paramString2.add(i, paramString1);
    }
    
    public void appendSwitchesAndArguments(String[] paramArrayOfString)
    {
      a(paramArrayOfString, 0);
    }
    
    protected String[] getCommandLineArguments()
    {
      ArrayList localArrayList = this.jdField_a_of_type_JavaUtilArrayList;
      return (String[])localArrayList.toArray(new String[localArrayList.size()]);
    }
    
    public String getSwitchValue(String paramString)
    {
      String str = (String)this.jdField_a_of_type_JavaUtilHashMap.get(paramString);
      if (str != null)
      {
        paramString = str;
        if (!str.isEmpty()) {}
      }
      else
      {
        paramString = null;
      }
      return paramString;
    }
    
    public boolean hasSwitch(String paramString)
    {
      return this.jdField_a_of_type_JavaUtilHashMap.containsKey(paramString);
    }
  }
  
  private static class NativeCommandLine
    extends CommandLine
  {
    public NativeCommandLine(@Nullable String[] paramArrayOfString)
    {
      super();
      CommandLine.a(paramArrayOfString);
    }
    
    protected void a()
    {
      throw new IllegalStateException("Can't destroy native command line after startup");
    }
    
    public void appendSwitch(String paramString)
    {
      CommandLine.a(paramString);
    }
    
    public void appendSwitchWithValue(String paramString1, String paramString2)
    {
      CommandLine.a(paramString1, paramString2);
    }
    
    public void appendSwitchesAndArguments(String[] paramArrayOfString)
    {
      CommandLine.b(paramArrayOfString);
    }
    
    protected String[] getCommandLineArguments()
    {
      if (b) {
        return null;
      }
      throw new AssertionError();
    }
    
    public String getSwitchValue(String paramString)
    {
      return CommandLine.a(paramString);
    }
    
    public boolean hasSwitch(String paramString)
    {
      return CommandLine.a(paramString);
    }
    
    public boolean isNativeImplementation()
    {
      return true;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\CommandLine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */