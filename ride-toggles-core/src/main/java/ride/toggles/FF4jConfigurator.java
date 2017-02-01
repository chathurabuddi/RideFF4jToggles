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
import org.ff4j.store.JdbcQueryBuilder;

public class FF4jConfigurator{

    private final FF4j ff4j;

    public FF4jConfigurator(FF4j ff4j) {
        BasicDataSource dbcpDataSource = new BasicDataSource();
        dbcpDataSource.setDriverClassName("org.postgresql.Driver");
        dbcpDataSource.setUsername("postgres");
        dbcpDataSource.setPassword("");
        dbcpDataSource.setUrl("jdbc:postgresql://192.168.1.84:5433/PGRS1");

        JdbcFeatureStore featureStore = new JdbcFeatureStore(dbcpDataSource);
        JdbcPropertyStore propertyStore = new JdbcPropertyStore(dbcpDataSource);
        JdbcEventRepository eventRepository = new JdbcEventRepository(dbcpDataSource);

        featureStore.setQueryBuilder(new JdbcQueryBuilder("ff4j.FF4j_", ""));
        propertyStore.setQueryBuilder(new JdbcQueryBuilder("ff4j.FF4j_", ""));
        eventRepository.setQueryBuilder(new JdbcQueryBuilder("ff4j.FF4j_", ""));

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
