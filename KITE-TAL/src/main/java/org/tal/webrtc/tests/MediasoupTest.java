package org.tal.webrtc.tests;

import org.webrtc.kite.tests.KiteBaseTest;

public abstract class MediasoupTest extends KiteBaseTest {
    public static String apprtcURL = "https://112-126-100-194.eaydu.com/index";
    protected boolean getStats = true;


    @Override
    protected void payloadHandling() {
        super.payloadHandling();
        if (this.payload != null) {
            apprtcURL = payload.getString("url", apprtcURL);
        }
    }
}
