package com.git.amarradi.palatschinkencounter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ExampleDialog extends AppCompatDialogFragment {
    private ExampleDialogListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle(getResources().getString(R.string.attention))
                .setMessage(getResources().getString(R.string.resetMessage))
                .setNegativeButton(getResources().getString(R.string.Cancel), (dialog, which) -> {

                })
                .setPositiveButton(getResources().getString(R.string.resetOK), (dialog, which) -> listener.onYesClicked());
        return builder.create();
    }

    public interface ExampleDialogListener {
        void onYesClicked();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }
}
