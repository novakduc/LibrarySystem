package model;

import java.util.List;

/**
 * Created by Novak on 9/6/2015.
 */
public class Member {

    private long mId;
    private String mName;
    private String mAddress;
    private String mPhone;
    private List mTransactions;
    private List mBooksOnHold;

    private Member(){}

    public Member(String mName, String mAddress, String mPhone){
        this.mName = mName;
        this.mAddress = mAddress;
        this.mPhone = mPhone;
    }

    public long getmId() {
        return mId;
    }

    public String getmName() {
        return mName;
    }

    public String getmAddress() {
        return mAddress;
    }

    public String getmPhone() {
        return mPhone;
    }

    public List getmTransactions() {
        return mTransactions;
    }

    public List getmBooksOnHold() {
        return mBooksOnHold;
    }
}
