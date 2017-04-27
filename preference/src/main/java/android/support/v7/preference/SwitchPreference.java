package android.support.v7.preference;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Convenience class to instantiate a switch compat from XML.
 */
public class SwitchPreference extends SwitchPreferenceCompat {

    public SwitchPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public SwitchPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SwitchPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwitchPreference(Context context) {
        super(context);
    }
}
