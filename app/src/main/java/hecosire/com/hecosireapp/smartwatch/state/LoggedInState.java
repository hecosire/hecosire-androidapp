package hecosire.com.hecosireapp.smartwatch.state;

import android.os.Bundle;

import hecosire.com.hecosireapp.R;
import hecosire.com.hecosireapp.smartwatch.SampleControlSmartWatch2;

public class LoggedInState implements ExtensionState {

    private SampleControlSmartWatch2 control;

    public LoggedInState(SampleControlSmartWatch2 control) {

        this.control = control;
    }


    @Override
    public void onResume() {
        Bundle[] data = new Bundle[4];
        control.showLayout(R.layout.smart_new_report, data);
    }
}
