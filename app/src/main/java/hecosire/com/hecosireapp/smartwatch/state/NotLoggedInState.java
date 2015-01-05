package hecosire.com.hecosireapp.smartwatch.state;

import android.os.Bundle;
import android.util.Log;

import com.sonyericsson.extras.liveware.aef.control.Control;

import hecosire.com.hecosireapp.R;
import hecosire.com.hecosireapp.smartwatch.SampleControlSmartWatch2;
import hecosire.com.hecosireapp.smartwatch.SampleExtensionService;

public class NotLoggedInState implements ExtensionState {

    private SampleControlSmartWatch2 control;

    public NotLoggedInState(SampleControlSmartWatch2 control) {

        this.control = control;
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
