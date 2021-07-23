package org.tal.adapter.tests;

import org.webrtc.kite.tests.KiteBaseTest;

public abstract class TalTest extends KiteBaseTest {
    public static String apprtcURL = "https://dev-webrtc.weclassroom.com/engine/example/index.html";
    public static String roomId="23982308";
    public static String localUserId="239823081";
    public static String remoteUserId="239823082";
    public static String rtnUserId="1001";

    public static String localUrl=apprtcURL+"?h=8-140-113-148";
    public static String remoteUrl=apprtcURL+"?h=112-126-100-194";
    protected boolean getStats = true;
    private boolean finished=false;

    @Override
    protected void payloadHandling() {
        super.payloadHandling();
        if (this.payload != null) {
            apprtcURL = payload.getString("url", apprtcURL);
            localUrl = payload.getString("localUrl",localUrl);
            remoteUrl = payload.getString("remoteUrl",remoteUrl);
        }
    }
}
