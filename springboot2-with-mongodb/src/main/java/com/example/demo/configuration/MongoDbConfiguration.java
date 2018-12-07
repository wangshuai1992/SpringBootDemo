package com.example.demo.configuration;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.util.Arrays;

@Configuration
@Profile("!use-mongodb-embedded")
public class MongoDbConfiguration {

    private final MongoDbProperties mongoDbProperties;

    public MongoDbConfiguration(MongoDbProperties mongoDbProperties) {
        this.mongoDbProperties = mongoDbProperties;
    }

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        MongoCredential credential = MongoCredential.createCredential(mongoDbProperties.getUsername(),
                mongoDbProperties.getDatabase(), mongoDbProperties.getPwd().toCharArray());
//        MongoClient mongoClient = new MongoClient(mongoDbProperties.getHost(), mongoDbProperties.getPort());
        MongoClient mongoClient = new MongoClient(new ServerAddress(mongoDbProperties.getHost(), mongoDbProperties.getPort()),
                Arrays.asList(credential));
        return new SimpleMongoDbFactory(mongoClient, mongoDbProperties.getDatabase());
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
        return mongoTemplate;
    }

}
