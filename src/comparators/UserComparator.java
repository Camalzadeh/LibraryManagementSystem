package comparators;

import models.User;

import java.util.Comparator;

public class UserComparator implements Comparator<User> {
    @Override
    public int compare(User o1, User o2) {
        return o1.getUserId().compareTo(o2.getUserId());
    }

    public int compareByName(User o1, User o2) {
        return o1.getName().compareTo(o2.getName());
    }

    public int compareByAddress(User o1, User o2) {
        return o1.getAddress().compareTo(o2.getAddress());
    }

    public int compareByUserRole(User o1, User o2) {
        return o1.getUserRole().compareTo(o2.getUserRole());
    }
}
