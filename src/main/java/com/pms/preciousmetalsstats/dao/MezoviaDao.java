package com.pms.preciousmetalsstats.dao;

import com.pms.preciousmetalsstats.model.PreciousMetalsDTO;

import java.util.List;

public interface MezoviaDao {

    List<PreciousMetalsDTO> getGoldCoinsProducts();

    List<PreciousMetalsDTO> getSilverCoinsProducts();
}
