package Model;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Created by Novak on 9/6/2015.
 */
public class MemberList {
    private List mMembers;
    private MemberList mMemberList;

    private MemberList() {
    }

    public MemberList getInstance() {
        if (mMemberList == null)
            return new MemberList();
        else return mMemberList;
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
