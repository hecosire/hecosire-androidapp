package hecosire.com.hecosireapp.smartwatch;

import android.content.ContentValues;
import android.content.Context;

import com.sonyericsson.extras.liveware.aef.registration.Registration;
import com.sonyericsson.extras.liveware.extension.util.ExtensionUtils;
import com.sonyericsson.extras.liveware.extension.util.registration.RegistrationInformation;

import hecosire.com.hecosireapp.R;

/**
 * Provides information needed during extension registration
 */
public class SampleRegistrationInformation extends RegistrationInformation {

    final Context mContext;

    /**
     * Create control registration object
     *
     * @param context The context
     */
    protected SampleRegistrationInformation(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context == null");
        }
        mContext = context;
    }

    @Override
    public int getRequiredControlApiVersion() {
        return 1;
    }

    /*
     * (non-Javadoc)
     * @see com.sonyericsson.extras.liveware.extension.util.registration.
     * RegistrationInformation#getTargetControlApiVersion()
     */
    @Override
    public int getTargetControlApiVersion() {
        return 2;
    }

    @Override
    public int getRequiredSensorApiVersion() {
        return 0;
    }

    @Override
    public int getRequiredNotificationApiVersion() {
        return 0;
    }

    @Override
    public int getRequiredWidgetApiVersion() {
        return 0;
    }

    /**
     * Get the extension registration information.
     *
     * @return The registration configuration.
     */
    @Override
    public ContentValues getExtensionRegistrationConfiguration() {
        String iconHostapp = ExtensionUtils.getUriString(mContext, R.drawable.ic_launcher);
        String iconExtension = ExtensionUtils
                .getUriString(mContext, R.drawable.ic_launcher);
        String iconExtension48 = ExtensionUtils
                .getUriString(mContext, R.drawable.ic_launcher);
        String iconExtensionBw = ExtensionUtils.getUriString(mContext,
                R.drawable.ic_launcher);

        ContentValues values = new ContentValues();

        values.put(Registration.ExtensionColumns.CONFIGURATION_ACTIVITY,
                SamplePreferenceActivity.class.getName());
        values.put(Registration.ExtensionColumns.CONFIGURATION_TEXT,
                mContext.getString(R.string.configuration_text));
        values.put(Registration.ExtensionColumns.NAME, mContext.getString(R.string.extension_name));
        values.put(Registration.ExtensionColumns.EXTENSION_KEY,
                SampleExtensionService.EXTENSION_KEY);
        values.put(Registration.ExtensionColumns.HOST_APP_ICON_URI, iconHostapp);
        values.put(Registration.ExtensionColumns.EXTENSION_ICON_URI, iconExtension);
        values.put(Registration.ExtensionColumns.EXTENSION_48PX_ICON_URI, iconExtension48);
        values.put(Registration.ExtensionColumns.EXTENSION_ICON_URI_BLACK_WHITE, iconExtensionBw);
        values.put(Registration.ExtensionColumns.NOTIFICATION_API_VERSION,
                getRequiredNotificationApiVersion());
        values.put(Registration.ExtensionColumns.PACKAGE_NAME, mContext.getPackageName());

        return values;
    }

    @Override
    public boolean isDisplaySizeSupported(int width, int height) {
        return ((width == SampleControlSmartWatch2.getSupportedControlWidth(mContext)
                && height == SampleControlSmartWatch2
                .getSupportedControlHeight(mContext) ));
    }

}
