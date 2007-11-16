package com.itmill.toolkit.terminal.gwt.client.ui;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTML;
import com.itmill.toolkit.terminal.gwt.client.ApplicationConnection;
import com.itmill.toolkit.terminal.gwt.client.Paintable;
import com.itmill.toolkit.terminal.gwt.client.UIDL;

public class IEmbedded extends HTML implements Paintable {

    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
        if (client.updateComponent(this, uidl, true)) {
            return;
        }
        String w = uidl.hasAttribute("width") ? uidl
                .getStringAttribute("width") : "100%";
        String h = uidl.hasAttribute("height") ? uidl
                .getStringAttribute("height") : "100%";
        DOM.setStyleAttribute(getElement(), "width", w);
        DOM.setStyleAttribute(getElement(), "height", h);

        if (uidl.hasAttribute("type")) {
            String type = uidl.getStringAttribute("type");
            if (type.equals("image")) {
                setHTML("<img src=\"" + getSrc(uidl, client) + "\"/>");
            } else if (type.equals("browser")) {
                setHTML("<iframe width=\"100%\" height=\"100%\" frameborder=\"0\" src=\""
                        + getSrc(uidl, client) + "\"></iframe>");
            } else {
                ApplicationConnection.getConsole().log(
                        "Unknown Embedded type '" + type + "'");
            }
        } else if (uidl.hasAttribute("mimetype")) {
            String mime = uidl.getStringAttribute("mimetype");
            if (mime.equals("application/x-shockwave-flash")) {
                setHTML("<object width=\"" + w + "\" height=\"" + h
                        + "\"><param name=\"movie\" value=\""
                        + getSrc(uidl, client) + "\"><embed src=\""
                        + getSrc(uidl, client) + "\" width=\"" + w
                        + "\" height=\"" + h + "\"></embed></object>");
            } else {
                ApplicationConnection.getConsole().log(
                        "Unknown Embedded mimetype '" + mime + "'");
            }
        } else {
            ApplicationConnection.getConsole().log(
                    "Unknown Embedded; no type or mimetype attribute");
        }

    }

    /**
     * Helper to return translated src attribute from embeddeds uidl
     * 
     * @param uidl
     * @param client
     * @return
     */
    private String getSrc(UIDL uidl, ApplicationConnection client) {
        return client.translateToolkitUri(uidl.getStringAttribute("src"));
    }
}
