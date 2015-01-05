package hecosire.com.hecosireapp.smartwatch;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.sonyericsson.extras.liveware.aef.control.Control;
import com.sonyericsson.extras.liveware.extension.util.control.ControlExtension;
import com.sonyericsson.extras.liveware.extension.util.control.ControlObjectClickEvent;
import com.sonyericsson.extras.liveware.extension.util.control.ControlTouchEvent;
import com.sonyericsson.extras.liveware.extension.util.control.ControlView;
import com.sonyericsson.extras.liveware.extension.util.control.ControlViewGroup;

import hecosire.com.hecosireapp.R;
import hecosire.com.hecosireapp.UserToken;
import hecosire.com.hecosireapp.smartwatch.state.ExtensionState;
import hecosire.com.hecosireapp.smartwatch.state.LoggedInState;
import hecosire.com.hecosireapp.smartwatch.state.NotLoggedInState;

/**
 * The sample control for SmartWatch handles the control on the accessory. This
 * class exists in one instance for every supported host application that we
 * have registered to
 */
public class SampleControlSmartWatch2 extends ControlExtension {


    private Handler mHandler;

    private ControlViewGroup mLayout = null;

    private Context _context;

    private ExtensionState extensionState;

    /**
     * Create sample control.
     *
     * @param hostAppPackageName Package name of host application.
     * @param context The context.
     * @param handler The handler to use
     */
    SampleControlSmartWatch2(final String hostAppPackageName, final Context context,
                             Handler handler) {
        super(context, hostAppPackageName);
        this._context = context;
        if (handler == null) {
            throw new IllegalArgumentException("handler == null");
        }
        mHandler = handler;
        figureOutState();
        setupClickables(context);
    }

    public void setWatchEvent(int viewLabelId, String event) {
        sendText(viewLabelId, event);
    }

    private void figureOutState() {
        UserToken token = UserToken.getUserToken(_context);
        if (token != null) {
            extensionState = new LoggedInState(this);
        } else {
            extensionState = new NotLoggedInState(this);
        }

    }

    /**
     * Get supported control width.
     *
     * @param context The context.
     * @return the width.
     */
    public static int getSupportedControlWidth(Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.smart_watch_2_control_width);
    }

    /**
     * Get supported control height.
     *
     * @param context The context.
     * @return the height.
     */
    public static int getSupportedControlHeight(Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.smart_watch_2_control_height);
    }

    @Override
    public void onDestroy() {
        Log.d(SampleExtensionService.LOG_TAG, "SampleControlSmartWatch onDestroy");
        //stopAnimation();
        mHandler = null;
    };

    @Override
    public void onStart() {
        // Nothing to do. Animation is handled in onResume.
    }

    @Override
    public void onStop() {
        // Nothing to do. Animation is handled in onPause.
    }

    @Override
    public void onResume() {
        figureOutState();
        extensionState.onResume();

        //fetchDataAboutTrams();
        //startAnimation();
    }

    @Override
    public void onPause() {
        Log.d(SampleExtensionService.LOG_TAG, "Stopping animation");
        // stopAnimation();
    }





    @Override
    public void onTouch(final ControlTouchEvent event) {
        Log.d(SampleExtensionService.LOG_TAG, "onTouch() " + event.getAction());
        if (event.getAction() == Control.Intents.TOUCH_ACTION_RELEASE) {
            Log.d(SampleExtensionService.LOG_TAG, "Toggling animation");
            //   toggleAnimation();
        }
    }

    @Override
    public void onObjectClick(final ControlObjectClickEvent event) {
        Log.d(SampleExtensionService.LOG_TAG, "onObjectClick() " + event.getClickType());
        if (event.getLayoutReference() != -1) {
            mLayout.onClick(event.getLayoutReference());
        }
    }

    @Override
    public void onKey(final int action, final int keyCode, final long timeStamp) {
        Log.d(SampleExtensionService.LOG_TAG, "onKey()");
        if (action == Control.Intents.KEY_ACTION_RELEASE
                && keyCode == Control.KeyCodes.KEYCODE_OPTIONS) {
            //
        }
        else if (action == Control.Intents.KEY_ACTION_RELEASE
                && keyCode == Control.KeyCodes.KEYCODE_BACK) {
            Log.d(SampleExtensionService.LOG_TAG, "onKey() - back button intercepted.");
        }
    }





    private void setupClickables(final Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.sample_control_2
                , null);
        mLayout = (ControlViewGroup) parseLayout(layout);
        if (mLayout != null) {
            ControlView bottomRight = mLayout.findViewById(R.id.logo_tram);
            bottomRight.setOnClickListener(new ControlView.OnClickListener() {
                @Override
                public void onClick() {
                    Intent goToNextActivity = new Intent(context, SamplePreferenceActivity.class);
                    goToNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(goToNextActivity);

//                    sendImage(R.id.sample_control_object_4, R.drawable.right_bottom_selected);
//                    mHandler.postDelayed(new SelectToggler(R.id.sample_control_object_4,
//                            R.drawable.right_bottom), SELECT_TOGGLER_MS);
                }
            });
        }
    }


}
