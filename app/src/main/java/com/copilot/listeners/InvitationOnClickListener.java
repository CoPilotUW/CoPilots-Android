package com.copilot.listeners;

import android.app.Activity;
import android.view.View;

import com.copilot.copilot.modals.InvitationModal;

public class InvitationOnClickListener implements View.OnClickListener {
    String inviterID;
    String recipientID;
    String recipientName;
    Activity parent;

    public InvitationOnClickListener(Activity parent, String inviterID, String recipientID, String recipientName) {
        this.inviterID = inviterID;
        this.recipientID = recipientID;
        this.recipientName = recipientName;
        this.parent = parent;
    }

    @Override
    public void onClick(View v) {
        InvitationModal modal = InvitationModal.newInstance(inviterID, recipientID, recipientName);
        // TODO: API call here for success
        modal.show(parent.getFragmentManager(), "invite_modal");
    }
}