package net.nighthawkempires.core.datasection;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public abstract class AbstractMongoRegistry<T extends Model> implements Registry<T> {

    protected final Cache<String, T> m_RegisteredData;
    protected final MongoCollection<Document> m_Collection;

    public AbstractMongoRegistry(MongoCollection<Document> p_Collection, int p_ExpireMins) {
        m_Collection = p_Collection;
        if (p_ExpireMins > 0) {
            m_RegisteredData =
                    CacheBuilder.newBuilder().concurrencyLevel(4).expireAfterAccess(p_ExpireMins, TimeUnit.MINUTES)
                            .build();
        } else {
            m_RegisteredData = CacheBuilder.newBuilder().concurrencyLevel(4).build();
        }
    }

    public Optional<T> fromKey(String p_Key) {
        if (!m_RegisteredData.asMap().containsKey(p_Key))
            loadFromDb(p_Key);
        return Optional.ofNullable(m_RegisteredData.asMap().getOrDefault(p_Key, null));
    }

    public T register(T p_Value) {
        m_RegisteredData.put(p_Value.getKey(), p_Value);
        saveToDb(p_Value.getKey());
        return p_Value;
    }

    public T put(String p_Key, T p_Value) {
        m_RegisteredData.put(p_Key, p_Value);
        saveToDb(p_Key);
        return p_Value;
    }

    public void remove(String p_Key) {
        m_RegisteredData.asMap().remove(p_Key);
        m_Collection.deleteOne(Filters.eq("key", p_Key));
    }

    public void remove(T p_Value) {
        m_RegisteredData.asMap().remove(p_Value.getKey());
        m_Collection.deleteOne(Filters.eq("key", p_Value.getKey()));
    }

    public void saveToDb(String p_Key) {
        Optional<Document> loaded = documentFromDb(p_Key);
        if (m_RegisteredData.asMap().containsKey(p_Key)) {
            Model model = m_RegisteredData.asMap().get(p_Key);
            if (loaded.isPresent())
                m_Collection.deleteMany(Filters.eq("key", p_Key));
            Document document = mapToDocument(model.serialize());
            document.put("key", p_Key);
            m_Collection.insertOne(document);
        }
    }

    public void loadFromDb(String p_Key) {
        Document document = m_Collection.find(Filters.eq("key", p_Key)).first();
        if (document != null)
            m_RegisteredData.put(p_Key, fromDataSection(p_Key, new MJsonSection(document)));
    }

    public Optional<Document> documentFromDb(String p_Key) {
        Document document = m_Collection.find(Filters.eq("key", p_Key)).first();
        if (document != null)
            return Optional.of(document);
        return Optional.empty();
    }

    public Map<String, T> loadAllFromDb() {
        try (MongoCursor<Document> cursor = m_Collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                String key = document.getString("key");
                if (key != null) {
                    try {
                        //UUID.fromString(key);
                        m_RegisteredData.put(key, fromDataSection(key, new MJsonSection(document)));
                    } catch (Exception oops) {
                        oops.printStackTrace();
                    }
                }
            }
        }
        return m_RegisteredData.asMap();
    }

    public void purge() {
        m_RegisteredData.asMap().clear();
        m_Collection.drop();
    }
    
    public static Document mapToDocument(Map<String, Object> p_Map) {
        Document document = new Document();
        for (Map.Entry<String, Object> entry : p_Map.entrySet()) {
            if (entry.getValue() instanceof Map) {
                document.put(entry.getKey(), mapToDocument((Map) entry.getValue()));
            } else {
                document.put(entry.getKey(), entry.getValue());
            }
        }
        return document;
    }

    public static Map<String, Object> documentToMap(Document p_Document) {
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<String, Object> entry : p_Document.entrySet()) {
            if (entry.getValue() instanceof Document) {
                map.put(entry.getKey(), documentToMap((Document) entry.getValue()));
            } else {
                map.put(entry.getKey(), entry.getValue());
            }
        }
        return map;
    }
}