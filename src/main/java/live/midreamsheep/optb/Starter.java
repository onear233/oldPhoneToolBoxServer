package live.midreamsheep.optb;

import live.midreamsheep.optb.scanner.annotation.scan.Scan;

@Scan("live.midreamsheep.optb.executes.execute")
public class Starter {
    public static void main(String[] args) throws Exception {
        ApplicationStarter.starter(Starter.class);
    }
}
