package me.kansio.client.modules.impl.player.hackerdetect;

import java.util.*;
import me.kansio.client.modules.impl.player.hackerdetect.checks.*;
import me.kansio.client.modules.impl.player.hackerdetect.checks.movement.*;
import me.kansio.client.modules.impl.player.hackerdetect.checks.exploit.*;

public class CheckManager
{
    private /* synthetic */ ArrayList<Check> checks;
    
    public CheckManager() {
        this.checks = new ArrayList<Check>();
        this.checks.add(new SpeedA());
        this.checks.add(new FlightA());
        this.checks.add(new PhaseA());
    }
    
    public ArrayList<Check> getChecks() {
        return this.checks;
    }
}
