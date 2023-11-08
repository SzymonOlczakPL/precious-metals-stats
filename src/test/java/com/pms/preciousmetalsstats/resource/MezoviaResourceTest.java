package com.pms.preciousmetalsstats.resource;

import com.pms.preciousmetalsstats.dao.MezoviaDao;
import com.pms.preciousmetalsstats.model.PreciousMetalsDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MezoviaResourceTest {

    private final MezoviaDao mezoviaDao = mock(MezoviaDao.class);
    private final MezoviaResource mezoviaResource = new MezoviaResource(mezoviaDao);

    private final List<PreciousMetalsDTO> metalsDTOS = mock(List.class);

    @Test
    void getGoldCoinsTest() {
        //GIVEN
        when(mezoviaDao.getGoldCoinsProducts()).thenReturn(metalsDTOS);

        //WHEN
        List<PreciousMetalsDTO> actualPreciousMetalsDTOS = mezoviaResource.getGoldCoins();

        //THEN
        assertEquals(metalsDTOS, actualPreciousMetalsDTOS);
    }

    @Test
    void getSilverCoinsTest() {
        //GIVEN
        when(mezoviaDao.getSilverCoinsProducts()).thenReturn(metalsDTOS);

        //WHEN
        List<PreciousMetalsDTO> actualPreciousMetalsDTOS = mezoviaResource.getSilverCoins();

        //THEN
        assertEquals(metalsDTOS, actualPreciousMetalsDTOS);
    }
}