package hecosire.com.hecosireapp.smartwatch.state;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.sonyericsson.extras.liveware.aef.control.Control;
import com.sonyericsson.extras.liveware.aef.registration.Registration;
import com.sonyericsson.extras.liveware.extension.util.control.ControlView;
import com.sonyericsson.extras.liveware.extension.util.control.ControlViewGroup;

import hecosire.com.hecosireapp.NewReportTask;
import hecosire.com.hecosireapp.R;
import hecosire.com.hecosireapp.UserToken;
import hecosire.com.hecosireapp.smartwatch.HealthyState;
import hecosire.com.hecosireapp.smartwatch.SampleControlSmartWatch2;
import hecosire.com.hecosireapp.smartwatch.SampleExtensionService;

public class LoggedInState extends ExtensionState {

    private UserToken token;

    public LoggedInState(SampleControlSmartWatch2 control, Context context) {
        super(control, context);
    }

    public void setupClickables(final Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.smart_new_report
                , null);
        mLayout = (ControlViewGroup) control.parseLayout(layout);
        if (mLayout != null) {

            setClickHandlerFor(context, R.id.button_healthy, HealthyState.HEALTHY);
            setClickHandlerFor(context, R.id.button_coming_down, HealthyState.COMING_DOWN);
            setClickHandlerFor(context, R.id.button_sick, HealthyState.SICK);
            setClickHandlerFor(context, R.id.button_recovering, HealthyState.RECOVERING);
        }
    }

    private void setClickHandlerFor(final Context context, int button_id, final int health_state_id) {
        ControlView button = mLayout.findViewById(button_id);
        button.setOnClickListener(new ControlView.OnClickListener() {
            @Override
            public void onClick() {

                new NewReportTask(context, token).execute(health_state_id);
                control.sendToHostApp(new Intent(Control.Intents.CONTROL_STOP_REQUEST_INTENT));

            }
        });
    }

    @Override
    public void onResume() {
        Bundle[] data = new Bundle[4];
        control.showLayout(R.layout.smart_new_report, data);
    }

    public void setToken(UserToken token) {
        this.token = token;
    }
}
