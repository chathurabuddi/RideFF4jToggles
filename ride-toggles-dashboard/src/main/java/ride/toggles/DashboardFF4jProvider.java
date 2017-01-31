package ride.toggles;

import org.ff4j.FF4j;
import org.ff4j.web.FF4JProvider;

public class DashboardFF4jProvider implements FF4JProvider{
	private final FF4j ff4j;

    public DashboardFF4jProvider() {
        ff4j = new FF4jConfigurator(new FF4j()).getFf4j();
    }
    
    public FF4j getFF4j() { return ff4j; }
}
