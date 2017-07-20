package com.copilot.copilot.modals;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.copilot.com.copilot.global.GlobalConstants;
import com.copilot.com.copilot.global.InviteType;
import com.copilot.copilot.BookingActivity;
import com.copilot.copilot.R;
import com.copilot.helper.HTTPRequestWrapper;
import com.copilot.helper.VolleyCallback;

import java.util.HashMap;
import java.util.Map;

// Note: we're going to
public class InvitationModal extends DialogFragment {
//    private InviteType inviteType = InviteType.DRIVER_INVITATION;

//    private InvitationModal()

    // /rider/group/join will join group with id cpgroupid (POST)
    // /driver/invite will invite user with cpuserid for group cpgroupid


    final VolleyCallback dummyCallback = new VolleyCallback() {
        @Override
        public void onSuccessResponse(String response) {
            Log.d("Invitationmodal", "successfully sent invite " + response);
//            finish();
        }
    };

    final VolleyCallback failure = new VolleyCallback() {
        @Override
        public void onSuccessResponse(String response) {
            Log.d("Invitationmodal", "send invite failed " + response);
//            finish();
        }
    };

    public static InvitationModal newInstance(String tripID, String recipientID, String recipientName, String endpoint) {
        InvitationModal f = new InvitationModal();

        Bundle args = new Bundle();
        args.putString("endpoint", endpoint);
        args.putString("tripID", tripID);
        args.putString("recipientName", recipientName);
        args.putString("recipientID", recipientID);
        f.setArguments(args);

        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String endpoint = getArguments().getString("endpoint", "");

        final String tripID = getArguments().getString("tripID", "");
        final String recipientID = getArguments().getString("recipientID", "");
        final String recipientName = getArguments().getString("recipientName", "");

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View modalBody = inflater.inflate(R.layout.modal_invite, null);
        TextView inviteModalText = (TextView) modalBody.findViewById(R.id.invite_modal_text);

        // assume sender driver if recipientID (id of rider to invite) is not empty
        boolean isSenderDriver = !getArguments().getString("recipientID", "").equals("");

        String inviteText = (isSenderDriver ? "Send invitation to " : "Send ride request to ") + recipientName + "?";
        inviteModalText.setText(inviteText);

        // get buttons from the modalBody view
        Button cancelButton = (Button) modalBody.findViewById(R.id.cancel_invite);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InvitationModal.this.getDialog().cancel();
            }
        });

        Button sendButton = (Button) modalBody.findViewById(R.id.submit_invite);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // quick, make the API call!
//                InvitationModal.this.getDialog().cancel();

                // request.makePostRequest(GlobalConstants.CREATE_TRIP_SEARCH, params, dummyCallback, failure, headers);

                Map<String, String> params = new HashMap<String, String>();
                Map<String, String> headers = new HashMap<String, String>();

                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                String accessToken = sharedPref.getString(GlobalConstants.ACCESS_TOKEN, "");

                headers.put("x-access-token", accessToken);

                params.put("cpgroupid", tripID);
                if (!recipientID.equals("")) {
                    params.put("cpuserid", recipientID);
                }

                HTTPRequestWrapper request = new HTTPRequestWrapper(GlobalConstants.GLOBAL_URL + GlobalConstants.V1_FEATURES, getActivity());
                request.makePostRequest(endpoint, params, dummyCallback, failure, headers);

                InvitationModal.this.getDialog().cancel();
            }
        });

        builder.setView(modalBody);
        return builder.create();
    }
}
