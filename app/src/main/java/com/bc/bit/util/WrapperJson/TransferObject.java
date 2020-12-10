package com.bc.bit.util.WrapperJson;

public class TransferObject {
    public static final int ERROR_NETWORK_INVALID = 251658241;
    public static final int ERROR_SERVER_RESPONSE = 251658242;
    public static final int ERROR_DATA_CONVERSION = 251658243;
    public static final int ERROR_SOCKET_TIME_OUT = 251658244;
    public static final int DATA_RESPONSE_OK = 251658245;
    public static final int DATA_FILTER = 251658246;
    public static final int DATA_ERROR = 251658247;
    public int what;
    public int arg1;
    public int arg2;
    public Object obj;

    public TransferObject() {
    }

    public TransferObject(int w) {
        this.what = w;
    }

    public boolean isOk() {
        return this.what == 251658245;
    }

    public static TransferObject create(int what) {
        return create(what, "");
    }

    public static TransferObject create(int what, String error) {
        TransferObject o = new TransferObject(what);
        o.obj = error;
        return o;
    }

    public static TransferObject create(int what, Object obj) {
        TransferObject o = new TransferObject(what);
        o.obj = obj;
        return o;
    }

    public static TransferObject errorNetwork() {
        return create(251658241);
    }

    public static TransferObject success(Object obj) {
        return create(251658245, obj);
    }

    public static TransferObject error(Object obj) {
        return create(251658247, obj);
    }
}
