package cc.atspace.cloudy.cloudy.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.qiushui.blurredview.BlurredView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.lang.ref.WeakReference;

import cc.atspace.cloudy.cloudy.R;
import cc.atspace.cloudy.cloudy.utils.BlurImage;
import cc.atspace.cloudy.cloudy.utils.SpecialProgressBarView;
import it.michelelacorte.elasticprogressbar.ElasticDownloadView;
import jp.wasabeef.blurry.Blurry;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ImageDisplayFullScreen extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private ImageView fgImageView, bgImageView;
    Context context;

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    //    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
//            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    /*  private static final int WHAT = 1;
      int num = 20;
      SpecialProgressBarView ls;
      MyHandler handler = new MyHandler(this);

      class MyHandler extends Handler {
          WeakReference<Activity> weakReference;

          public MyHandler(Activity activity) {
              weakReference = new WeakReference<Activity>(activity);
          }

          @Override
          public void handleMessage(Message msg) {
              if (weakReference.get() != null) {
                  switch (msg.what) {
                      case WHAT:
                          num++;
                          if (num <= ls.getMax()) {
                              ls.setProgress(num);
                              handler.sendEmptyMessageDelayed(WHAT, 50);
                          }
                          break;
                  }
              }
          }
      }

    */  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_display_full_screen);
        context = ImageDisplayFullScreen.this;
        mVisible = true;
//        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        fgImageView = findViewById(R.id.fgImage_IV_FS);
        bgImageView = findViewById(R.id.bgImage_IV_FS);

        String currentLink = getIntent().getStringExtra("currentStoryLink");
        Picasso.with(context).load(currentLink).into(fgImageView);

        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                bgImageView.setImageBitmap(BlurImage.fastblur(bitmap, 1f, 20));
            }


            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                bgImageView.setImageResource(R.mipmap.ic_launcher);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        bgImageView.setTag(target);
        Picasso.with(this)
                .load(currentLink)
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .into(target);

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
//        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);


        //Progress Bar

    /*    ls = (SpecialProgressBarView) findViewById(R.id.ls);
        ls.setEndSuccessBackgroundColor(Color.parseColor("#66A269"))//设置进度完成时背景颜色
                .setEndSuccessDrawable(R.drawable.ic_done_white_36dp, null)//设置进度完成时背景图片
                .setCanEndSuccessClickable(false)//设置进度完成后是否可以再次点击开始
                .setProgressBarColor(Color.WHITE)//进度条颜色
                .setCanDragChangeProgress(false)//是否进度条是否可以拖拽
                .setCanReBack(true)//是否在进度成功后返回初始状态
                .setProgressBarBgColor(Color.parseColor("#491C14"))//进度条背景颜色
                .setProgressBarHeight(ls.dip2px(this, 4))//进度条宽度
                .setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()))//设置字体大小
                .setStartDrawable(R.drawable.ic_file_upload_white_36dp, null)//设置开始时背景图片
                .setTextColorSuccess(Color.parseColor("#66A269"))//设置成功时字体颜色
                .setTextColorNormal(Color.parseColor("#491C14"))//设置默认字体颜色
                .setTextColorError(Color.parseColor("#BC5246"));//设置错误时字体颜色

        ls.beginStarting();//启动开始开始动画
        ls.setError();//进度失败 发生错误

        ls.setOnAnimationEndListener(new SpecialProgressBarView.AnimationEndListener() {
            @Override
            public void onAnimationEnd() {
                ls.setMax(187);
                ls.setProgress(num);//初次进入在动画结束时设置进度
            }
        });
        ls.setOntextChangeListener(new SpecialProgressBarView.OntextChangeListener() {
            @Override
            public String onProgressTextChange(SpecialProgressBarView specialProgressBarView, int max, int progress) {
                return progress * 100 / max + "%";
            }

            @Override
            public String onErrorTextChange(SpecialProgressBarView specialProgressBarView, int max, int progress) {
                return "error";
            }

            @Override
            public String onSuccessTextChange(SpecialProgressBarView specialProgressBarView, int max, int progress) {
                return "done";
            }
        });


        //straight bar

        final ElasticDownloadView mElasticDownloadView;
        mElasticDownloadView = (ElasticDownloadView) findViewById(R.id.elastic_download_view);

//Call startIntro() to start animation
        mElasticDownloadView.startIntro();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int progressStatus = 0;
                while (progressStatus < 100) {
                    progressStatus = downloadFile();
                    final int finalProgressStatus = progressStatus;
                    handler.post(new Runnable() {
                        public void run() {
                            //Set progress dinamically
                            mElasticDownloadView.setProgress(finalProgressStatus);
                            Log.d("Progress:", "" + mElasticDownloadView.getProgress());
                        }
                    });
                }
                if (progressStatus >= 100) {
                    mElasticDownloadView.success();
                }
            }
        }, 1000);

//You can set progress
        mElasticDownloadView.setProgress(10);

//If is finish animation
        mElasticDownloadView.success();

//Or fail
//mElasticDownloadView.fail();
*/
    }

    private int downloadFile() {
        int fileSize = 0;
        while (fileSize <= 1000000) {

            fileSize++;

            if (fileSize == 100000) {
                return 10;

            } else if (fileSize == 200000) {
                return 20;

            } else if (fileSize == 300000) {
                return 30;

            } else if (fileSize == 400000) {
                return 40;

            } else if (fileSize == 500000) {

                return 50;
            } else if (fileSize == 700000) {

                return 70;
            } else if (fileSize == 800000) {

                return 80;
            } else if (fileSize == 900000) {

                return 90;
            }
        }

        return 100;

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
//        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
