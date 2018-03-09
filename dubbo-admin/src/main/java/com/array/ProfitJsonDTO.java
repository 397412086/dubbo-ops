package com.array;


public class ProfitJsonDTO extends BaseObject {
    private static final long serialVersionUID = 1L;

    private Wfsy wfsy;
    
    private Qrnhsy qrnhsy;

    public Wfsy getWfsy() {
        return wfsy;
    }

    public void setWfsy(Wfsy wfsy) {
        this.wfsy = wfsy;
    }

    public Qrnhsy getQrnhsy() {
        return qrnhsy;
    }

    public void setQrnhsy(Qrnhsy qrnhsy) {
        this.qrnhsy = qrnhsy;
    }
}
