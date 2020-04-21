package com.example.ncov_api;

import java.util.Comparator;

public class Sort_by_deaths implements Comparator<nCov> {
    @Override
    public int compare(nCov o1, nCov o2) {
        int x = o1.getConfirmed() - o2.getConfirmed();
        if(x > 0) return -1;
        else if(x == 0) return 0;
        return 1;
    }
}
