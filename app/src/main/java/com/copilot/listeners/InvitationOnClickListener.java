package com.copilot.listeners;

import android.app.Activity;
import android.view.View;

import com.copilot.copilot.modals.InvitationModal;

public class InvitationOnClickListener implements View.OnClickListener {
    private String tripID;
    private String recipientID;
    private String recipientName;
    private Activity parentActivity;
    private String endpoint;

    // TODO: add a success, failure callback into this thing
    // and an endpoint

    public InvitationOnClickListener(
        Activity parentActivity,
        String tripID,
        String recipientID,
        String recipientName,
        String endpoint
    ) {
        this.tripID = tripID;
        this.recipientID = recipientID;
        this.recipientName = recipientName;
        this.parentActivity = parentActivity;
        this.endpoint = endpoint;
    }

    @Override
    public void onClick(View v) {
        InvitationModal modal = InvitationModal.newInstance(tripID, recipientID, recipientName, endpoint);
        // TODO: API call here for success
        modal.show(parentActivity.getFragmentManager(), "invite_modal");
    }
}