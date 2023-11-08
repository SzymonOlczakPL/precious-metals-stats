package com.pms.preciousmetalsstats.resource;


import com.pms.preciousmetalsstats.dao.MezoviaDao;
import com.pms.preciousmetalsstats.model.PreciousMetalsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mezovia")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MezoviaResource {

    private final MezoviaDao mezoviaDao;

    @GetMapping("/goldCoins")
    public List<PreciousMetalsDTO> getGoldCoins() {
        return mezoviaDao.getGoldCoinsProducts();
    }

    @GetMapping("/silverCoins")
    public List<PreciousMetalsDTO> getSilverCoins() {
        return mezoviaDao.getSilverCoinsProducts();
    }

}
