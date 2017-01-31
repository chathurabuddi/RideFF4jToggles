package ride;

import org.ff4j.FF4j;
import ride.toggles.FF4jConfigurator;

public class App {
    public static void main( String[] args ) {
        FF4j ff4j = new FF4jConfigurator(new FF4j()).getFf4j();

        if(ff4j.check("F2")){
            System.out.println("Feature 2 Enabled");
        }else{
            System.out.println("Feature 2 Not Enabled");
        }

        System.out.println(ff4j.getFeature("F2").getGroup());
        System.out.println(ff4j.getFeatures());

    }
}
