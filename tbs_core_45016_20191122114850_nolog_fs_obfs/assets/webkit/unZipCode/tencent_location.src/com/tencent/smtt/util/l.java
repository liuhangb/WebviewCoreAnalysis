package com.tencent.smtt.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.text.TextUtils.TruncateAt;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.tencent.smtt.listbox.b;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.smtt.webkit.SmttResource;

public class l
{
  private static float jdField_a_of_type_Float = 0.0F;
  private static int jdField_a_of_type_Int = 351;
  private static l jdField_a_of_type_ComTencentSmttUtilL;
  private static int b = 176;
  private Context jdField_a_of_type_AndroidContentContext = null;
  private final String jdField_a_of_type_JavaLangString = "searchvideo-";
  
  private l()
  {
    jdField_a_of_type_Float = SmttResource.getResources().getDisplayMetrics().density;
    this.jdField_a_of_type_AndroidContentContext = ContextHolder.getInstance().getApplicationContext();
    Object localObject = this.jdField_a_of_type_AndroidContentContext;
    if (localObject != null)
    {
      localObject = (WindowManager)((Context)localObject).getSystemService("window");
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      ((WindowManager)localObject).getDefaultDisplay().getMetrics(localDisplayMetrics);
      if (localDisplayMetrics.widthPixels < localDisplayMetrics.heightPixels) {
        i = localDisplayMetrics.widthPixels;
      } else {
        i = localDisplayMetrics.heightPixels;
      }
      int i = 375 - (int)(i / jdField_a_of_type_Float);
      if (i > 0)
      {
        b -= i;
        jdField_a_of_type_Int -= i;
      }
    }
  }
  
  private int a(int paramInt)
  {
    return (int)(paramInt * jdField_a_of_type_Float);
  }
  
  /* Error */
  private Bitmap a(String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 5
    //   3: aconst_null
    //   4: astore 6
    //   6: aconst_null
    //   7: astore 4
    //   9: new 92	java/net/URL
    //   12: dup
    //   13: aload_1
    //   14: invokespecial 95	java/net/URL:<init>	(Ljava/lang/String;)V
    //   17: invokevirtual 99	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   20: checkcast 101	java/net/HttpURLConnection
    //   23: astore_2
    //   24: aload_2
    //   25: astore_3
    //   26: aload_2
    //   27: ldc 103
    //   29: invokevirtual 106	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
    //   32: aload_2
    //   33: astore_3
    //   34: aload_2
    //   35: sipush 1000
    //   38: invokevirtual 110	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   41: aload_2
    //   42: astore_3
    //   43: aload_2
    //   44: sipush 1000
    //   47: invokevirtual 113	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   50: aload_2
    //   51: astore_3
    //   52: aload_2
    //   53: invokevirtual 116	java/net/HttpURLConnection:connect	()V
    //   56: aload 4
    //   58: astore_1
    //   59: aload_2
    //   60: astore_3
    //   61: aload_2
    //   62: invokevirtual 120	java/net/HttpURLConnection:getResponseCode	()I
    //   65: sipush 200
    //   68: if_icmpne +13 -> 81
    //   71: aload_2
    //   72: astore_3
    //   73: aload_2
    //   74: invokevirtual 124	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   77: invokestatic 130	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;)Landroid/graphics/Bitmap;
    //   80: astore_1
    //   81: aload_1
    //   82: astore_3
    //   83: aload_2
    //   84: ifnull +52 -> 136
    //   87: aload_2
    //   88: astore_3
    //   89: aload_1
    //   90: astore_2
    //   91: aload_3
    //   92: invokevirtual 133	java/net/HttpURLConnection:disconnect	()V
    //   95: aload_2
    //   96: areturn
    //   97: astore 4
    //   99: aload_2
    //   100: astore_1
    //   101: goto +13 -> 114
    //   104: astore_1
    //   105: aconst_null
    //   106: astore_3
    //   107: goto +32 -> 139
    //   110: astore 4
    //   112: aconst_null
    //   113: astore_1
    //   114: aload_1
    //   115: astore_3
    //   116: aload 4
    //   118: invokevirtual 136	java/lang/Exception:printStackTrace	()V
    //   121: aload 6
    //   123: astore_3
    //   124: aload_1
    //   125: ifnull +11 -> 136
    //   128: aload 5
    //   130: astore_2
    //   131: aload_1
    //   132: astore_3
    //   133: goto -42 -> 91
    //   136: aload_3
    //   137: areturn
    //   138: astore_1
    //   139: aload_3
    //   140: ifnull +7 -> 147
    //   143: aload_3
    //   144: invokevirtual 133	java/net/HttpURLConnection:disconnect	()V
    //   147: aload_1
    //   148: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	149	0	this	l
    //   0	149	1	paramString	String
    //   23	108	2	localObject1	Object
    //   25	119	3	localObject2	Object
    //   7	50	4	localObject3	Object
    //   97	1	4	localException1	Exception
    //   110	7	4	localException2	Exception
    //   1	128	5	localObject4	Object
    //   4	118	6	localObject5	Object
    // Exception table:
    //   from	to	target	type
    //   26	32	97	java/lang/Exception
    //   34	41	97	java/lang/Exception
    //   43	50	97	java/lang/Exception
    //   52	56	97	java/lang/Exception
    //   61	71	97	java/lang/Exception
    //   73	81	97	java/lang/Exception
    //   9	24	104	finally
    //   9	24	110	java/lang/Exception
    //   26	32	138	finally
    //   34	41	138	finally
    //   43	50	138	finally
    //   52	56	138	finally
    //   61	71	138	finally
    //   73	81	138	finally
    //   116	121	138	finally
  }
  
  private Bitmap a(String paramString1, String paramString2)
  {
    paramString1 = a(paramString1, paramString2);
    int i = paramString1.getWidth();
    int j = paramString1.getHeight();
    paramString2 = Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(paramString2);
    paramString1.layout(0, 0, i, j);
    paramString1.draw(localCanvas);
    return b.a(paramString2, a(2), a(2));
  }
  
  private Bitmap a(String paramString1, String paramString2, String paramString3)
  {
    paramString1 = a(paramString1);
    if (paramString1 == null) {
      return null;
    }
    paramString1 = a(paramString1, paramString2, paramString3);
    int i = paramString1.getWidth();
    int j = paramString1.getHeight();
    paramString2 = Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_8888);
    paramString3 = new Canvas(paramString2);
    paramString1.layout(0, 0, i, j);
    paramString1.draw(paramString3);
    return b.a(paramString2, a(2), a(2));
  }
  
  private View a(Bitmap paramBitmap, String paramString1, String paramString2)
  {
    LinearLayout localLinearLayout = new LinearLayout(this.jdField_a_of_type_AndroidContentContext);
    localLinearLayout.setOrientation(0);
    localLinearLayout.setBackgroundColor(-367980271);
    Object localObject = new ImageView(this.jdField_a_of_type_AndroidContentContext);
    ((ImageView)localObject).setImageBitmap(paramBitmap);
    ((ImageView)localObject).setLayoutParams(new LinearLayout.LayoutParams(a(80), -1));
    ((ImageView)localObject).setScaleType(ImageView.ScaleType.CENTER_CROP);
    localLinearLayout.addView((View)localObject);
    paramBitmap = new LinearLayout(this.jdField_a_of_type_AndroidContentContext);
    paramBitmap.setOrientation(1);
    paramBitmap.setLayoutParams(new LinearLayout.LayoutParams(a(b), -1));
    localLinearLayout.addView(paramBitmap);
    localObject = new TextView(this.jdField_a_of_type_AndroidContentContext);
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, -2);
    localLayoutParams.setMargins(a(8), a(8), 0, 0);
    ((TextView)localObject).setLayoutParams(localLayoutParams);
    ((TextView)localObject).setTextColor(-1);
    ((TextView)localObject).setTextSize(12.0F);
    ((TextView)localObject).setSingleLine(true);
    ((TextView)localObject).setText(paramString1);
    ((TextView)localObject).setEllipsize(TextUtils.TruncateAt.END);
    ((TextView)localObject).setTypeface(Typeface.DEFAULT_BOLD);
    paramBitmap.addView((View)localObject);
    paramString1 = new TextView(this.jdField_a_of_type_AndroidContentContext);
    localObject = new LinearLayout.LayoutParams(-1, -2);
    ((LinearLayout.LayoutParams)localObject).setMargins(a(8), a(2), 0, 0);
    paramString1.setLayoutParams((ViewGroup.LayoutParams)localObject);
    paramString1.setTextColor(-1694498817);
    paramString1.setTextSize(11.0F);
    paramString1.setSingleLine(true);
    paramString1.setEllipsize(TextUtils.TruncateAt.END);
    paramString1.setText(paramString2);
    paramBitmap.addView(paramString1);
    paramBitmap = new ImageView(this.jdField_a_of_type_AndroidContentContext);
    if (Build.VERSION.SDK_INT > 15) {
      paramBitmap.setBackground(SmttResource.getDrawable("x5_search_video_play_button"));
    } else {
      paramBitmap.setBackgroundDrawable(SmttResource.getDrawable("x5_search_video_play_button"));
    }
    paramString1 = new LinearLayout.LayoutParams(a(13), a(15));
    paramString1.setMargins(a(15), a(17), 0, 0);
    paramBitmap.setLayoutParams(paramString1);
    localLinearLayout.addView(paramBitmap);
    paramBitmap = new ImageView(this.jdField_a_of_type_AndroidContentContext);
    if (Build.VERSION.SDK_INT > 15) {
      paramBitmap.setBackground(SmttResource.getDrawable("x5_search_video_close_button"));
    } else {
      paramBitmap.setBackgroundDrawable(SmttResource.getDrawable("x5_search_video_close_button"));
    }
    paramString1 = new LinearLayout.LayoutParams(a(16), a(16));
    paramString1.setMargins(a(30), a(17), 0, 0);
    paramBitmap.setLayoutParams(paramString1);
    localLinearLayout.addView(paramBitmap);
    localLinearLayout.measure(View.MeasureSpec.makeMeasureSpec(a(jdField_a_of_type_Int), 1073741824), View.MeasureSpec.makeMeasureSpec(a(50), 1073741824));
    localLinearLayout.layout(0, 0, localLinearLayout.getMeasuredWidth(), localLinearLayout.getMeasuredHeight());
    return localLinearLayout;
  }
  
  private View a(String paramString1, String paramString2)
  {
    LinearLayout localLinearLayout = new LinearLayout(this.jdField_a_of_type_AndroidContentContext);
    localLinearLayout.setOrientation(0);
    localLinearLayout.setBackgroundColor(-654311424);
    Object localObject1 = new ImageView(this.jdField_a_of_type_AndroidContentContext);
    if (Build.VERSION.SDK_INT > 15) {
      ((ImageView)localObject1).setBackground(SmttResource.getDrawable("x5_search_video_play_button"));
    } else {
      ((ImageView)localObject1).setBackgroundDrawable(SmttResource.getDrawable("x5_search_video_play_button"));
    }
    Object localObject2 = new LinearLayout.LayoutParams(a(8), a(10));
    ((LinearLayout.LayoutParams)localObject2).setMargins(a(9), a(7), 0, 0);
    ((ImageView)localObject1).setLayoutParams((ViewGroup.LayoutParams)localObject2);
    localLinearLayout.addView((View)localObject1);
    localObject1 = new TextView(this.jdField_a_of_type_AndroidContentContext);
    localObject2 = new LinearLayout.LayoutParams(-2, -2);
    ((LinearLayout.LayoutParams)localObject2).setMargins(a(6), a(3), 0, 0);
    ((TextView)localObject1).setLayoutParams((ViewGroup.LayoutParams)localObject2);
    ((TextView)localObject1).setTextColor(-1);
    ((TextView)localObject1).setTextSize(12.0F);
    ((TextView)localObject1).setSingleLine(true);
    paramString1 = a(paramString1);
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append(paramString1);
    ((StringBuilder)localObject2).append(" ");
    ((StringBuilder)localObject2).append(paramString2);
    ((TextView)localObject1).setText(((StringBuilder)localObject2).toString());
    int i = paramString1.length();
    ((TextView)localObject1).setEllipsize(TextUtils.TruncateAt.END);
    ((TextView)localObject1).setTypeface(Typeface.DEFAULT_BOLD);
    localLinearLayout.addView((View)localObject1);
    localLinearLayout.measure(View.MeasureSpec.makeMeasureSpec(a(i * 12 + 72), 1073741824), View.MeasureSpec.makeMeasureSpec(a(24), 1073741824));
    localLinearLayout.layout(0, 0, localLinearLayout.getMeasuredWidth(), localLinearLayout.getMeasuredHeight());
    return localLinearLayout;
  }
  
  public static l a()
  {
    if (jdField_a_of_type_ComTencentSmttUtilL == null) {
      jdField_a_of_type_ComTencentSmttUtilL = new l();
    }
    return jdField_a_of_type_ComTencentSmttUtilL;
  }
  
  private String a(String paramString)
  {
    if (paramString == null) {
      return "";
    }
    if (paramString.length() <= 8) {
      return paramString;
    }
    return paramString.substring(0, 8);
  }
  
  /* Error */
  public byte[] a(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: aconst_null
    //   4: astore 5
    //   6: aload_0
    //   7: aload_1
    //   8: aload_2
    //   9: invokespecial 349	com/tencent/smtt/util/l:a	(Ljava/lang/String;Ljava/lang/String;)Landroid/graphics/Bitmap;
    //   12: astore_3
    //   13: goto +10 -> 23
    //   16: astore_1
    //   17: aload_1
    //   18: invokevirtual 350	java/lang/Throwable:printStackTrace	()V
    //   21: aconst_null
    //   22: astore_3
    //   23: aload 5
    //   25: astore_1
    //   26: new 352	java/io/ByteArrayOutputStream
    //   29: dup
    //   30: invokespecial 353	java/io/ByteArrayOutputStream:<init>	()V
    //   33: astore_2
    //   34: aload_3
    //   35: getstatic 359	android/graphics/Bitmap$CompressFormat:PNG	Landroid/graphics/Bitmap$CompressFormat;
    //   38: bipush 90
    //   40: aload_2
    //   41: invokevirtual 363	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   44: pop
    //   45: aload_2
    //   46: invokevirtual 367	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   49: astore_1
    //   50: aload_2
    //   51: invokevirtual 370	java/io/ByteArrayOutputStream:close	()V
    //   54: aload_1
    //   55: areturn
    //   56: astore_1
    //   57: goto +37 -> 94
    //   60: astore_3
    //   61: goto +15 -> 76
    //   64: astore_3
    //   65: aload_1
    //   66: astore_2
    //   67: aload_3
    //   68: astore_1
    //   69: goto +25 -> 94
    //   72: astore_3
    //   73: aload 4
    //   75: astore_2
    //   76: aload_2
    //   77: astore_1
    //   78: aload_3
    //   79: invokevirtual 350	java/lang/Throwable:printStackTrace	()V
    //   82: aload_2
    //   83: ifnull +7 -> 90
    //   86: aload_2
    //   87: invokevirtual 370	java/io/ByteArrayOutputStream:close	()V
    //   90: iconst_0
    //   91: newarray <illegal type>
    //   93: areturn
    //   94: aload_2
    //   95: ifnull +7 -> 102
    //   98: aload_2
    //   99: invokevirtual 370	java/io/ByteArrayOutputStream:close	()V
    //   102: aload_1
    //   103: athrow
    //   104: astore_2
    //   105: aload_1
    //   106: areturn
    //   107: astore_1
    //   108: goto -18 -> 90
    //   111: astore_2
    //   112: goto -10 -> 102
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	115	0	this	l
    //   0	115	1	paramString1	String
    //   0	115	2	paramString2	String
    //   12	23	3	localBitmap	Bitmap
    //   60	1	3	localThrowable1	Throwable
    //   64	4	3	localObject1	Object
    //   72	7	3	localThrowable2	Throwable
    //   1	73	4	localObject2	Object
    //   4	20	5	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   6	13	16	java/lang/Throwable
    //   34	50	56	finally
    //   34	50	60	java/lang/Throwable
    //   26	34	64	finally
    //   78	82	64	finally
    //   26	34	72	java/lang/Throwable
    //   50	54	104	java/io/IOException
    //   86	90	107	java/io/IOException
    //   98	102	111	java/io/IOException
  }
  
  /* Error */
  public byte[] a(String paramString1, String paramString2, String paramString3)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 5
    //   3: aconst_null
    //   4: astore 4
    //   6: aload_0
    //   7: aload_1
    //   8: aload_2
    //   9: aload_3
    //   10: invokespecial 373	com/tencent/smtt/util/l:a	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/graphics/Bitmap;
    //   13: astore_3
    //   14: goto +10 -> 24
    //   17: astore_1
    //   18: aload_1
    //   19: invokevirtual 350	java/lang/Throwable:printStackTrace	()V
    //   22: aconst_null
    //   23: astore_3
    //   24: aload_3
    //   25: ifnonnull +7 -> 32
    //   28: iconst_0
    //   29: newarray <illegal type>
    //   31: areturn
    //   32: new 352	java/io/ByteArrayOutputStream
    //   35: dup
    //   36: invokespecial 353	java/io/ByteArrayOutputStream:<init>	()V
    //   39: astore_1
    //   40: aload_1
    //   41: astore_2
    //   42: aload_3
    //   43: getstatic 359	android/graphics/Bitmap$CompressFormat:PNG	Landroid/graphics/Bitmap$CompressFormat;
    //   46: bipush 90
    //   48: aload_1
    //   49: invokevirtual 363	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   52: pop
    //   53: aload_1
    //   54: astore_2
    //   55: aload_1
    //   56: invokevirtual 367	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   59: astore_3
    //   60: aload_3
    //   61: astore_2
    //   62: aload_1
    //   63: invokevirtual 370	java/io/ByteArrayOutputStream:close	()V
    //   66: goto +35 -> 101
    //   69: astore_3
    //   70: goto +12 -> 82
    //   73: astore_1
    //   74: aconst_null
    //   75: astore_2
    //   76: goto +36 -> 112
    //   79: astore_3
    //   80: aconst_null
    //   81: astore_1
    //   82: aload_1
    //   83: astore_2
    //   84: aload_3
    //   85: invokevirtual 350	java/lang/Throwable:printStackTrace	()V
    //   88: aload 5
    //   90: astore_2
    //   91: aload_1
    //   92: ifnull +9 -> 101
    //   95: aload 4
    //   97: astore_2
    //   98: goto -36 -> 62
    //   101: aload_2
    //   102: ifnonnull +7 -> 109
    //   105: iconst_0
    //   106: newarray <illegal type>
    //   108: areturn
    //   109: aload_2
    //   110: areturn
    //   111: astore_1
    //   112: aload_2
    //   113: ifnull +7 -> 120
    //   116: aload_2
    //   117: invokevirtual 370	java/io/ByteArrayOutputStream:close	()V
    //   120: aload_1
    //   121: athrow
    //   122: astore_1
    //   123: goto -22 -> 101
    //   126: astore_2
    //   127: goto -7 -> 120
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	130	0	this	l
    //   0	130	1	paramString1	String
    //   0	130	2	paramString2	String
    //   0	130	3	paramString3	String
    //   4	92	4	localObject1	Object
    //   1	88	5	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   6	14	17	java/lang/Throwable
    //   42	53	69	java/lang/Throwable
    //   55	60	69	java/lang/Throwable
    //   32	40	73	finally
    //   32	40	79	java/lang/Throwable
    //   42	53	111	finally
    //   55	60	111	finally
    //   84	88	111	finally
    //   62	66	122	java/io/IOException
    //   116	120	126	java/io/IOException
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\util\l.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */