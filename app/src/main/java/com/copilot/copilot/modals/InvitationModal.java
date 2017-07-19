package com.copilot.copilot.modals;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
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

    public static InvitationModal newInstance(String sender, String recipientID, String recipientName) {
        InvitationModal f = new InvitationModal();

        Bundle args = new Bundle();
        args.putString("sender", sender);
        args.putString("recipientName", recipientName);
        args.putString("recipientID", recipientID);
        f.setArguments(args);

        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String sender = getArguments().getString("sender", "");
        String recipientID = getArguments().getString("recipientID", "");
        String recipientName = getArguments().getString("recipientName", "");

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View modalBody = inflater.inflate(R.layout.modal_invite, null);
        TextView inviteModalText = (TextView) modalBody.findViewById(R.id.invite_modal_text);
        inviteModalText.setText("Send invitation to " + recipientName + "?");

        // get buttons from the modalBody view
        Button cancelButton = (Button) modalBody.findViewById(R.id.cancel_invite);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InvitationModal.this.getDialog().cancel();
            }
        });

        builder.setView(modalBody);
        return builder.create();
    }
}
