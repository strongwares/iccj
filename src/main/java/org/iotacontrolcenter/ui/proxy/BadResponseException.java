package org.iotacontrolcenter.ui.proxy;

import org.iotacontrolcenter.dto.SimpleResponse;

public class BadResponseException extends Exception {

    public String errMsgkey;
    public SimpleResponse resp;

    public BadResponseException(String errMsgKey, SimpleResponse resp) {
        super(errMsgKey);
        this.errMsgkey = errMsgKey;
        this.resp = resp;
    }
}
