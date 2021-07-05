package org.tal.adapter.tests;

import org.webrtc.kite.tests.KiteBaseTest;

public abstract class TalTest extends KiteBaseTest {
    public static String apprtcURL = "https://dev-webrtc.weclassroom.com/engine/example/index.html";
    public static String roomId="23982308";
    public static String localUserId="239823081";
    public static String remoteUserId="239823082";
    public static String rtnUserId="1001";

    protected boolean getStats = true;
    private boolean finished=false;

    @Override
    protected void payloadHandling() {
        super.payloadHandling();
        if (this.payload != null) {
            apprtcURL = payload.getString("url", apprtcURL);
        }
    }
}
