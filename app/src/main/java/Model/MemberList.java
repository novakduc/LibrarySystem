package Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Created by Novak on 9/6/2015.
 */
public class MemberList {

    private volatile static MemberList sUniqueInstance;
    private List mMembers;

    private MemberList() {
        mMembers = new ArrayList();
    }

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

    public boolean insert(Member member) {
        return mMembers.add(member);
    }

    public Member search(UUID memberId) {
        Iterator iterator = mMembers.iterator();
        Object o;
        while (iterator.hasNext()) {
            o = iterator.next();
            if (o instanceof Member) {
                Member member = (Member) o;
                if (member.getId().equals(memberId))
                    return member;
            }
        }
        return null;
    }
}
