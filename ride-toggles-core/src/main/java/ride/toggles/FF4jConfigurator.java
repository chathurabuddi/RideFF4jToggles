package ride.toggles;

import org.apache.commons.dbcp.BasicDataSource;
import org.ff4j.FF4j;
import org.ff4j.audit.repository.EventRepository;
import org.ff4j.audit.repository.JdbcEventRepository;
import org.ff4j.cache.FF4jCacheProxy;
import org.ff4j.cache.FeatureCacheProviderRedis;
import org.ff4j.core.FeatureStore;
import org.ff4j.property.store.JdbcPropertyStore;
import org.ff4j.property.store.PropertyStore;
import org.ff4j.redis.RedisConnection;
import org.ff4j.store.JdbcFeatureStore;

public class FF4jConfigurator{

    private final FF4j ff4j;

    public FF4jConfigurator(FF4j ff4j) {
        BasicDataSource dbcpDataSource = new BasicDataSource();
        dbcpDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dbcpDataSource.setUsername("namal");
        dbcpDataSource.setPassword("123456");
        dbcpDataSource.setUrl("jdbc:mysql://192.168.0.222:3306/ff4j");

        FeatureStore featureStore = new JdbcFeatureStore(dbcpDataSource);
        PropertyStore propertyStore = new JdbcPropertyStore(dbcpDataSource);
        EventRepository eventRepository = new JdbcEventRepository(dbcpDataSource);

        RedisConnection redisConnection = new RedisConnection("localhost", 6379);
        FeatureCacheProviderRedis fcr = new FeatureCacheProviderRedis();
        fcr.setRedisConnection(redisConnection);
        FF4jCacheProxy redisCacheProxy = new FF4jCacheProxy(featureStore, propertyStore, fcr);

        ff4j.setFeatureStore(redisCacheProxy);
        ff4j.setPropertiesStore(redisCacheProxy);
        ff4j.setEventRepository(eventRepository);

        this.ff4j = ff4j;
    }

    public FF4j getFf4j(){
        return ff4j;
    }
}
