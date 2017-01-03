package org.iotacontrolcenter.ui;

import org.iotacontrolcenter.ui.util.UiUtil;
import org.iotacontrolcenter.dto.IotaNeighborDto;
import org.iotacontrolcenter.dto.NeighborDto;


import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class IccrTest {

    private static int nbrKey = 0;
    private static String uiNbr1Uri = "udp://10.0.0.1:14265";
    private static String iotaNbr1Uri = "/10.0.0.1:14265";
    private static String iotaNbr2Uri = "fred.com/10.0.0.1:14265";
    private static String iotaNbr3Uri = "10.0.0.1:14265";

    @BeforeClass
    public static void setUp() {
        System.out.println("Setup");
    }

    @Test
    public void testIotaNbrUpdate() {
        System.out.println("test IOTA nbr update");
        nbrKey++;
        NeighborDto uiNbr1 = new NeighborDto(String.valueOf(nbrKey),
                uiNbr1Uri,
                String.valueOf(nbrKey),
                String.valueOf(nbrKey),
                true);

        IotaNeighborDto iotaNbr = new IotaNeighborDto();
        iotaNbr.setAddress(iotaNbr1Uri);

        boolean sameNbr = UiUtil.isSameNbr(uiNbr1, iotaNbr);
        Assert.assertTrue("UI nbr1 != IOTA nbr1", sameNbr);

        iotaNbr = new IotaNeighborDto();
        iotaNbr.setAddress(iotaNbr2Uri);

        sameNbr = UiUtil.isSameNbr(uiNbr1, iotaNbr);
        Assert.assertTrue("UI nbr1 != IOTA nbr2", sameNbr);

        iotaNbr = new IotaNeighborDto();
        iotaNbr.setAddress(iotaNbr3Uri);

        sameNbr = UiUtil.isSameNbr(uiNbr1, iotaNbr);
        Assert.assertTrue("UI nbr1 != IOTA nbr3", sameNbr);



    }

}