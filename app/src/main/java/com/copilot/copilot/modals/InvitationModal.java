package com.copilot.copilot.modals;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.copilot.com.copilot.global.InviteType;
import com.copilot.copilot.R;

// Note: we're going to
public class InvitationModal extends DialogFragment {
//    private InviteType inviteType = InviteType.DRIVER_INVITATION;

//    private InvitationModal()

    public static InvitationModal newInstance(String sender, String recipient) {
        InvitationModal f = new InvitationModal();

        Bundle args = new Bundle();
        args.putString("sender", sender);
        args.putString("recipient", recipient);
        f.setArguments(args);

        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String sender = getArguments().getString("sender", "");
        String recipient = getArguments().getString("recipient", "");
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View modalBody = inflater.inflate(R.layout.modal_invite, null);
        TextView inviteModalText = (TextView) modalBody.findViewById(R.id.invite_modal_text);
        inviteModalText.setText("Send invitation to " + recipient + "?");

        builder.setView(modalBody);

        // get buttons from the modalBody view

        /*
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_signin, null))
        // Add action buttons
               .setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int id) {
                       // sign in the user ...
                   }
               })
               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       LoginDialogFragment.this.getDialog().cancel();
                   }
               });
        return builder.create();
        */
        return builder.create();
    }
}
