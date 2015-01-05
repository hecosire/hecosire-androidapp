package hecosire.com.hecosireapp.smartwatch.state;

import android.content.Context;
import android.util.Log;

import com.sonyericsson.extras.liveware.extension.util.control.ControlViewGroup;

import hecosire.com.hecosireapp.UserToken;
import hecosire.com.hecosireapp.smartwatch.SampleControlSmartWatch2;
import hecosire.com.hecosireapp.smartwatch.SampleExtensionService;

public abstract class ExtensionState {

    protected SampleControlSmartWatch2 control;

    protected ControlViewGroup mLayout = null;

    public ExtensionState(SampleControlSmartWatch2 control, Context context) {

        this.control = control;
        setupClickables(context);
    }


    public abstract void onResume();

    public abstract void setupClickables(final Context context);

    public void onClick(int layoutReference) {
        mLayout.onClick(layoutReference);
    }

}
