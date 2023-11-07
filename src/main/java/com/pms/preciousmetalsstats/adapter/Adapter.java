package com.pms.preciousmetalsstats.adapter;

import com.pms.preciousmetalsstats.model.PreciousMetalsDTO;

public interface Adapter<T> {

    PreciousMetalsDTO adapt(T obj);
}
