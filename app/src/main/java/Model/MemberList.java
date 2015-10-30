package Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Novak on 9/6/2015.
 */
public class MemberList {

    private volatile static MemberList sUniqueInstance;
    //private static long sNumberOfMembers;
    private List mMembers;

    private MemberList() {
        //sNumberOfMembers = 0;
        mMembers = new ArrayList();
    }

    /*
    public static long getNumberOfMembers() {
        return sNumberOfMembers;
    }
    */

    public static MemberList getInstance() {
        if (sUniqueInstance == null) {
            synchronized (MemberList.class) {
                if (sUniqueInstance == null) {
                    sUniqueInstance = new MemberList();
                }
            }
        }
        return sUniqueInstance;
    }

    public Iterator getMembers() {
        return mMembers.iterator();
    }

    public boolean insert(Member member) throws Exception {
        Iterator iterator = mMembers.iterator();
        Member tempMember;
        while (iterator.hasNext()) {
            tempMember = (Member) iterator.next();
            if (tempMember.getName().equals(member.getName())) {
                if (tempMember.getAddress().equals(member.getAddress())) {
                    throw new Exception("Member already existed");
                }
            }
        }
        //sNumberOfMembers++;
        return mMembers.add(member);
    }

    private Member search(String memberName) {
        Iterator iterator = mMembers.iterator();
        Object o;
        while (iterator.hasNext()) {
            o = iterator.next();
            if (o instanceof Member) {
                Member member = (Member) o;
                if (member.getName().equals(memberName))
                    return member;
            }
        }
        return null;
    }

    public Member search(long memberId) {
        Iterator iterator = mMembers.iterator();
        Object o;
        while (iterator.hasNext()) {
            o = iterator.next();
            if (o instanceof Member) {
                Member member = (Member) o;
                if (member.getId() == memberId)
                    return member;
            }
        }
        return null;
    }
}
