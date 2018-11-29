/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package android.support.v7.preference;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;

public class ListPreferenceDialogController extends PreferenceDialogController {

    private static final String SAVE_STATE_INDEX = "ListPreferenceDialogController.index";
    private static final String SAVE_STATE_ENTRIES = "ListPreferenceDialogController.entries";
    private static final String SAVE_STATE_ENTRY_VALUES =
            "ListPreferenceDialogController.entryValues";

    private int mClickedDialogEntryIndex;
    private CharSequence[] mEntries;
    private CharSequence[] mEntryValues;

    public static ListPreferenceDialogController newInstance(String key) {
        ListPreferenceDialogController controller =
                new ListPreferenceDialogController();
        controller.getArgs().putString(ARG_KEY, key);
        return controller;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            final ListPreference preference = getListPreference();

            if (preference.getEntries() == null || preference.getEntryValues() == null) {
                throw new IllegalStateException(
                        "ListPreference requires an entries array and an entryValues array.");
            }

            mClickedDialogEntryIndex = preference.findIndexOfValue(preference.getValue());
            mEntries = preference.getEntries();
            mEntryValues = preference.getEntryValues();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVE_STATE_INDEX, mClickedDialogEntryIndex);
        putCharSequenceArray(outState, SAVE_STATE_ENTRIES, mEntries);
        putCharSequenceArray(outState, SAVE_STATE_ENTRY_VALUES, mEntryValues);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mClickedDialogEntryIndex = savedInstanceState.getInt(SAVE_STATE_INDEX, 0);
        mEntries = getCharSequenceArray(savedInstanceState, SAVE_STATE_ENTRIES);
        mEntryValues = getCharSequenceArray(savedInstanceState, SAVE_STATE_ENTRY_VALUES);
    }

    private static void putCharSequenceArray(Bundle out, String key, CharSequence[] entries) {
        final ArrayList<String> stored = new ArrayList<>(entries.length);

        for (final CharSequence cs : entries) {
            stored.add(cs.toString());
        }

        out.putStringArrayList(key, stored);
    }

    private static CharSequence[] getCharSequenceArray(Bundle in, String key) {
        final ArrayList<String> stored = in.getStringArrayList(key);

        return stored == null ? null : stored.toArray(new CharSequence[0]);
    }

    private ListPreference getListPreference() {
        return (ListPreference) getPreference();
    }

    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        super.onPrepareDialogBuilder(builder);

        builder.setSingleChoiceItems(mEntries, mClickedDialogEntryIndex, (dialog, which) -> {
            mClickedDialogEntryIndex = which;

            /*
             * Clicking on an item simulates the positive button
             * click, and dismisses the dialog.
             */
            ListPreferenceDialogController.this.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
            dialog.dismiss();
        });

        /*
         * The typical interaction for list-based dialogs is to have
         * click-on-an-item dismiss the dialog instead of the user having to
         * press 'Ok'.
         */
        builder.setPositiveButton(null, null);
    }

    @Override
    public void onDialogClosed(boolean positiveResult) {
        final ListPreference preference = getListPreference();
        if (positiveResult && mClickedDialogEntryIndex >= 0) {
            String value = mEntryValues[mClickedDialogEntryIndex].toString();
            if (preference.callChangeListener(value)) {
                preference.setValue(value);
            }
        }
    }

}
