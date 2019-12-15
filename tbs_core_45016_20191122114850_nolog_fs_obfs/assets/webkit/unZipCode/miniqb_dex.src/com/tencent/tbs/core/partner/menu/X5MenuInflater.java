package com.tencent.tbs.core.partner.menu;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ActionProvider;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import com.android.internal.R.styleable;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class X5MenuInflater
  extends MenuInflater
{
  private static final String XML_GROUP = "group";
  private static final String XML_ITEM = "item";
  private static final String XML_MENU = "menu";
  private Context mContext;
  private ArrayList<X5MenuItem> mX5MenuItem = null;
  
  public X5MenuInflater(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
    this.mX5MenuItem = new ArrayList();
  }
  
  private void parseMenu(XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Menu paramMenu)
    throws XmlPullParserException, IOException
  {
    MenuState localMenuState = new MenuState(paramMenu);
    int i = paramXmlPullParser.getEventType();
    do
    {
      if (i == 2)
      {
        paramMenu = paramXmlPullParser.getName();
        if (paramMenu.equals("menu"))
        {
          i = paramXmlPullParser.next();
          break;
        }
        paramXmlPullParser = new StringBuilder();
        paramXmlPullParser.append("Expecting menu, got ");
        paramXmlPullParser.append(paramMenu);
        throw new RuntimeException(paramXmlPullParser.toString());
      }
      j = paramXmlPullParser.next();
      i = j;
    } while (j != 1);
    i = j;
    Object localObject = null;
    int j = 0;
    int k = 0;
    int n = i;
    while (j == 0)
    {
      int m;
      switch (n)
      {
      default: 
        i = k;
        paramMenu = (Menu)localObject;
        m = j;
        break;
      case 3: 
        String str = paramXmlPullParser.getName();
        if ((k != 0) && (str.equals(localObject)))
        {
          paramMenu = null;
          i = 0;
          m = j;
        }
        else if (str.equals("group"))
        {
          localMenuState.resetGroup();
          i = k;
          paramMenu = (Menu)localObject;
          m = j;
        }
        else if (str.equals("item"))
        {
          i = k;
          paramMenu = (Menu)localObject;
          m = j;
          if (!localMenuState.hasAddedItem())
          {
            i = k;
            paramMenu = (Menu)localObject;
            m = j;
            if (localMenuState.itemActionProvider != null)
            {
              localMenuState.itemActionProvider.hasSubMenu();
              i = k;
              paramMenu = (Menu)localObject;
              m = j;
            }
          }
        }
        else
        {
          i = k;
          paramMenu = (Menu)localObject;
          m = j;
          if (str.equals("menu"))
          {
            m = 1;
            i = k;
            paramMenu = (Menu)localObject;
          }
        }
        break;
      case 2: 
        if (k != 0)
        {
          i = k;
          paramMenu = (Menu)localObject;
          m = j;
        }
        else
        {
          paramMenu = paramXmlPullParser.getName();
          if (paramMenu.equals("group"))
          {
            localMenuState.readGroup(paramAttributeSet);
            i = k;
            paramMenu = (Menu)localObject;
            m = j;
          }
          else if (paramMenu.equals("item"))
          {
            localMenuState.readItem(paramAttributeSet);
            i = k;
            paramMenu = (Menu)localObject;
            m = j;
          }
          else if (paramMenu.equals("menu"))
          {
            parseMenu(paramXmlPullParser, paramAttributeSet, localMenuState.addSubMenuItem());
            i = k;
            paramMenu = (Menu)localObject;
            m = j;
          }
          else
          {
            i = 1;
            m = j;
          }
        }
        break;
      case 1: 
        throw new RuntimeException("Unexpected end of document");
      }
      n = paramXmlPullParser.next();
      k = i;
      localObject = paramMenu;
      j = m;
    }
  }
  
  public ArrayList<X5MenuItem> getX5MenuItem()
  {
    return this.mX5MenuItem;
  }
  
  /* Error */
  public void inflate(int paramInt, Menu paramMenu)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 28	com/tencent/tbs/core/partner/menu/X5MenuInflater:mX5MenuItem	Ljava/util/ArrayList;
    //   4: invokevirtual 126	java/util/ArrayList:clear	()V
    //   7: aconst_null
    //   8: astore 4
    //   10: aconst_null
    //   11: astore 5
    //   13: aconst_null
    //   14: astore_3
    //   15: aload_0
    //   16: getfield 30	com/tencent/tbs/core/partner/menu/X5MenuInflater:mContext	Landroid/content/Context;
    //   19: invokevirtual 132	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   22: iload_1
    //   23: invokevirtual 138	android/content/res/Resources:getLayout	(I)Landroid/content/res/XmlResourceParser;
    //   26: astore 6
    //   28: aload 6
    //   30: astore_3
    //   31: aload 6
    //   33: astore 4
    //   35: aload 6
    //   37: astore 5
    //   39: aload_0
    //   40: aload 6
    //   42: aload 6
    //   44: invokestatic 144	android/util/Xml:asAttributeSet	(Lorg/xmlpull/v1/XmlPullParser;)Landroid/util/AttributeSet;
    //   47: aload_2
    //   48: invokespecial 114	com/tencent/tbs/core/partner/menu/X5MenuInflater:parseMenu	(Lorg/xmlpull/v1/XmlPullParser;Landroid/util/AttributeSet;Landroid/view/Menu;)V
    //   51: aload 6
    //   53: ifnull +10 -> 63
    //   56: aload 6
    //   58: invokeinterface 149 1 0
    //   63: return
    //   64: astore_2
    //   65: goto +33 -> 98
    //   68: astore_2
    //   69: aload 4
    //   71: astore_3
    //   72: new 151	android/view/InflateException
    //   75: dup
    //   76: ldc -103
    //   78: aload_2
    //   79: invokespecial 156	android/view/InflateException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   82: athrow
    //   83: astore_2
    //   84: aload 5
    //   86: astore_3
    //   87: new 151	android/view/InflateException
    //   90: dup
    //   91: ldc -103
    //   93: aload_2
    //   94: invokespecial 156	android/view/InflateException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   97: athrow
    //   98: aload_3
    //   99: ifnull +9 -> 108
    //   102: aload_3
    //   103: invokeinterface 149 1 0
    //   108: aload_2
    //   109: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	110	0	this	X5MenuInflater
    //   0	110	1	paramInt	int
    //   0	110	2	paramMenu	Menu
    //   14	89	3	localObject1	Object
    //   8	62	4	localObject2	Object
    //   11	74	5	localObject3	Object
    //   26	31	6	localXmlResourceParser	android.content.res.XmlResourceParser
    // Exception table:
    //   from	to	target	type
    //   15	28	64	finally
    //   39	51	64	finally
    //   72	83	64	finally
    //   87	98	64	finally
    //   15	28	68	java/io/IOException
    //   39	51	68	java/io/IOException
    //   15	28	83	org/xmlpull/v1/XmlPullParserException
    //   39	51	83	org/xmlpull/v1/XmlPullParserException
  }
  
  private class MenuState
  {
    private static final int defaultItemCategory = 0;
    private static final int defaultItemCheckable = 0;
    private static final boolean defaultItemChecked = false;
    private static final boolean defaultItemEnabled = true;
    private static final int defaultItemOrder = 0;
    private static final boolean defaultItemVisible = true;
    private int groupCategory;
    private int groupCheckable;
    private boolean groupEnabled;
    private int groupId;
    private int groupOrder;
    private boolean groupVisible;
    private ActionProvider itemActionProvider;
    private String itemActionProviderClassName;
    private String itemActionViewClassName;
    private int itemActionViewLayout;
    private boolean itemAdded;
    private char itemAlphabeticShortcut;
    private int itemCategoryOrder;
    private int itemCheckable;
    private boolean itemChecked;
    private boolean itemEnabled;
    private int itemIconResId;
    private int itemId;
    private String itemListenerMethodName;
    private char itemNumericShortcut;
    private int itemShowAsAction;
    private CharSequence itemTitle;
    private CharSequence itemTitleCondensed;
    private boolean itemVisible;
    private Menu menu;
    
    public MenuState(Menu paramMenu)
    {
      this.menu = paramMenu;
      resetGroup();
    }
    
    private char getShortcut(String paramString)
    {
      if (paramString == null) {
        return '\000';
      }
      return paramString.charAt(0);
    }
    
    private <T> T newInstance(String paramString, Class<?>[] paramArrayOfClass, Object[] paramArrayOfObject)
    {
      try
      {
        paramString = X5MenuInflater.this.mContext.getClassLoader().loadClass(paramString).getConstructor(paramArrayOfClass).newInstance(paramArrayOfObject);
        return paramString;
      }
      catch (Exception paramString)
      {
        for (;;) {}
      }
      return null;
    }
    
    private void setItem(MenuItem paramMenuItem) {}
    
    public MenuItem addItem()
    {
      this.itemAdded = true;
      MenuItem localMenuItem = this.menu.add(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle);
      setItem(localMenuItem);
      return localMenuItem;
    }
    
    public SubMenu addSubMenuItem()
    {
      this.itemAdded = true;
      SubMenu localSubMenu = this.menu.addSubMenu(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle);
      setItem(localSubMenu.getItem());
      return localSubMenu;
    }
    
    public boolean hasAddedItem()
    {
      return this.itemAdded;
    }
    
    public void readGroup(AttributeSet paramAttributeSet)
    {
      paramAttributeSet = X5MenuInflater.this.mContext.obtainStyledAttributes(paramAttributeSet, R.styleable.MenuGroup);
      this.groupCategory = paramAttributeSet.getInt(3, 0);
      this.groupOrder = paramAttributeSet.getInt(4, 0);
      this.groupCheckable = paramAttributeSet.getInt(5, 0);
      this.groupVisible = paramAttributeSet.getBoolean(2, true);
      this.groupEnabled = paramAttributeSet.getBoolean(0, true);
      paramAttributeSet.recycle();
    }
    
    public void readItem(AttributeSet paramAttributeSet)
    {
      throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
    }
    
    public void resetGroup()
    {
      this.groupCategory = 0;
      this.groupOrder = 0;
      this.groupCheckable = 0;
      this.groupVisible = true;
      this.groupEnabled = true;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\menu\X5MenuInflater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */