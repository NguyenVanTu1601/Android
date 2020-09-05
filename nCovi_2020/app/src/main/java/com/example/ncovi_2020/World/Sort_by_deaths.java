package com.example.ncovi_2020.World;

import com.example.ncovi_2020.World.nCov_World;

import java.util.Comparator;

public class Sort_by_deaths implements Comparator<nCov_World> {
    @Override
    public int compare(nCov_World o1, nCov_World o2) {
        int x = o1.getConfirmed() - o2.getConfirmed();
        if(x > 0) return -1;
        else if(x == 0) return 0;
        return 1;
    }
}
