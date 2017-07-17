package com.copilot.copilot.invitationlist;

/**
 * Created by xiaozhuoyu on 2017-07-16.
 */

public class InvitationListItem {
    private String name;
    private String pickup;
    private String destination;
    private String time;
    private String inviterOrRequestId;

    public InvitationListItem(String name, String pickup, String destination, String time, String id) {
        this.name = name;
        this.pickup = pickup;
        this.destination = destination;
        this.time = time;
        this.inviterOrRequestId = id;
    }

    public String getName() {
        return this.name;
    }

    public String getStartingLocation() {
        return this.pickup;
    }

    public String getDestination() {
        return this.destination;
    }

    public String getTime() {
        return time;
    }

    public String getId() {
        return inviterOrRequestId;
    }
}
