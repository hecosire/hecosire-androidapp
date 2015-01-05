package hecosire.com.hecosireapp.smartwatch.state;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.sonyericsson.extras.liveware.aef.control.Control;
import com.sonyericsson.extras.liveware.extension.util.control.ControlView;
import com.sonyericsson.extras.liveware.extension.util.control.ControlViewGroup;

import hecosire.com.hecosireapp.R;
import hecosire.com.hecosireapp.smartwatch.SampleControlSmartWatch2;
import hecosire.com.hecosireapp.smartwatch.SampleExtensionService;
import hecosire.com.hecosireapp.smartwatch.SamplePreferenceActivity;

public class NotLoggedInState extends ExtensionState {

    public NotLoggedInState(SampleControlSmartWatch2 control, Context context) {
        super(control, context);
    }

    @Override
    public void onClick(int layoutReference) {
        mLayout.onClick(layoutReference);
    }

    public void setupClickables(final Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.sample_control_2
                , null);
        mLayout = (ControlViewGroup) control.parseLayout(layout);
        if (mLayout != null) {
            ControlView bottomRight = mLayout.findViewById(R.id.logo_tram);
            bottomRight.setOnClickListener(new ControlView.OnClickListener() {
                @Override
                public void onClick() {
                    Intent goToNextActivity = new Intent(context, SamplePreferenceActivity.class);
                    goToNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(goToNextActivity);
                }
            });
        }
    }


    public void onResume() {
        Log.d(SampleExtensionService.LOG_TAG, "Starting animation");

        Bundle b1 = new Bundle();
        b1.putInt(Control.Intents.EXTRA_LAYOUT_REFERENCE, R.id.tram_information_1);
        b1.putString(Control.Intents.EXTRA_TEXT, "1");

        control.sendText(R.id.tram_information_1, "testing");


        Bundle[] data = new Bundle[4];

        data[0] = b1;

        control.showLayout(R.layout.sample_control_2, data);

    }

}
