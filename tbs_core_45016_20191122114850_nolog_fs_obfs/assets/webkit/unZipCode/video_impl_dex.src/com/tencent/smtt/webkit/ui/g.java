package com.tencent.smtt.webkit.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.tencent.smtt.webkit.SmttResource;
import com.tencent.smtt.webkit.ui.zoomImg.OnDestroyCallback;
import com.tencent.smtt.webkit.ui.zoomImg.TileBitmapDrawable;
import com.tencent.smtt.webkit.ui.zoomImg.TouchImageView;
import java.io.File;

public class g
  extends Dialog
{
  private Context jdField_a_of_type_AndroidContentContext;
  private OnDestroyCallback jdField_a_of_type_ComTencentSmttWebkitUiZoomImgOnDestroyCallback;
  public final String a;
  private String b;
  
  public g(Context paramContext, String paramString)
  {
    super(paramContext, 16973835);
    this.jdField_a_of_type_JavaLangString = "TbsPicPreviewDialog";
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    this.b = paramString;
  }
  
  private void a(String paramString)
  {
    paramString = Uri.fromFile(new File(paramString));
    Intent localIntent = new Intent("android.intent.action.SEND");
    localIntent.setType("image/png");
    localIntent.putExtra("android.intent.extra.STREAM", paramString);
    localIntent.addFlags(268435456);
    localIntent.addFlags(67108864);
    localIntent.setPackage("com.tencent.mm");
    this.jdField_a_of_type_AndroidContentContext.startActivity(Intent.createChooser(localIntent, "分享截图"));
  }
  
  public View a(final Context paramContext)
  {
    int i = (int)(paramContext.getResources().getDisplayMetrics().density * 40.0F);
    int j = paramContext.getResources().getDisplayMetrics().widthPixels;
    int k = paramContext.getResources().getDisplayMetrics().heightPixels;
    RelativeLayout localRelativeLayout1 = new RelativeLayout(paramContext);
    localRelativeLayout1.setLayoutParams(new ViewGroup.LayoutParams(j, k));
    RelativeLayout localRelativeLayout2 = new RelativeLayout(paramContext);
    localRelativeLayout2.setBackgroundColor(-1895825408);
    Object localObject1 = new RelativeLayout.LayoutParams(j, i);
    ((RelativeLayout.LayoutParams)localObject1).addRule(10, -1);
    localRelativeLayout2.setLayoutParams((ViewGroup.LayoutParams)localObject1);
    localObject1 = new ImageView(paramContext);
    Object localObject2 = new RelativeLayout.LayoutParams(i, i);
    ((RelativeLayout.LayoutParams)localObject2).addRule(9, -1);
    ((RelativeLayout.LayoutParams)localObject2).addRule(15, -1);
    ((ImageView)localObject1).setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    ((ImageView)localObject1).setLayoutParams((ViewGroup.LayoutParams)localObject2);
    ((ImageView)localObject1).setImageDrawable(SmttResource.getDrawable("x5_camera_back"));
    ((ImageView)localObject1).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        g.this.dismiss();
      }
    });
    localObject2 = new TextView(paramContext);
    Object localObject3 = new RelativeLayout.LayoutParams(j, i);
    ((RelativeLayout.LayoutParams)localObject3).setMargins(i, 0, 0, 0);
    ((TextView)localObject2).setLayoutParams((ViewGroup.LayoutParams)localObject3);
    ((TextView)localObject2).setText(SmttResource.getString("x5_qq_snapshot_content_preview", "string"));
    ((TextView)localObject2).setTextSize(16.0F);
    ((TextView)localObject2).setTextColor(-1);
    ((TextView)localObject2).setGravity(16);
    localObject3 = new TouchImageView(paramContext);
    ((TouchImageView)localObject3).setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
    this.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgOnDestroyCallback = TileBitmapDrawable.a((ImageView)localObject3, this.b, null, null);
    RelativeLayout localRelativeLayout3 = new RelativeLayout(paramContext);
    Object localObject4 = new RelativeLayout.LayoutParams(j, i);
    ((RelativeLayout.LayoutParams)localObject4).addRule(12, -1);
    localRelativeLayout3.setLayoutParams((ViewGroup.LayoutParams)localObject4);
    localRelativeLayout3.setBackgroundColor(-1895825408);
    localObject4 = new ImageView(paramContext);
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(i, i);
    localLayoutParams.addRule(9, -1);
    localLayoutParams.addRule(15, -1);
    localLayoutParams.setMargins(32, 0, 0, 0);
    ((ImageView)localObject4).setLayoutParams(localLayoutParams);
    ((ImageView)localObject4).setImageDrawable(SmttResource.getDrawable("x5_snapshot_delete"));
    ((ImageView)localObject4).setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    ((ImageView)localObject4).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        try
        {
          new File(g.a(g.this)).delete();
          g.this.dismiss();
          Toast.makeText(paramContext, SmttResource.getString("x5_qq_snapshot_deleted", "string"), 0).show();
          return;
        }
        catch (Exception paramAnonymousView) {}
      }
    });
    paramContext = new ImageView(paramContext);
    localLayoutParams = new RelativeLayout.LayoutParams(i, i);
    localLayoutParams.addRule(11, -1);
    localLayoutParams.addRule(15, -1);
    localLayoutParams.setMargins(0, 0, 32, 0);
    paramContext.setLayoutParams(localLayoutParams);
    paramContext.setImageDrawable(SmttResource.getDrawable("x5_snapshot_share"));
    paramContext.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    paramContext.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        try
        {
          g.a(g.this, g.a(g.this));
          return;
        }
        catch (Exception paramAnonymousView) {}
      }
    });
    localRelativeLayout2.addView((View)localObject1);
    localRelativeLayout2.addView((View)localObject2);
    localRelativeLayout3.addView((View)localObject4);
    localRelativeLayout3.addView(paramContext);
    localRelativeLayout1.addView((View)localObject3);
    localRelativeLayout1.addView(localRelativeLayout2);
    localRelativeLayout1.addView(localRelativeLayout3);
    return localRelativeLayout1;
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    setContentView(a(this.jdField_a_of_type_AndroidContentContext));
    getWindow().setBackgroundDrawable(new ColorDrawable(0));
    getWindow().setLayout(-1, -1);
  }
  
  public void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    this.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgOnDestroyCallback.destroy();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\ui\g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */