package com.github.lindenhoney.migration;

import com.github.lindenhoney.domain.Song;
import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonArray;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@ChangeLog
public class InitialSetupChangelog {

    @Profile("initial-data")
    @ChangeSet(order = "001", id = "initial-data", author = "Alexander Babai")
    public void loadData(MongoTemplate mongoTemplate, Environment environment){
        final String dataUrl = environment.getRequiredProperty("linden-honey.db.migration.initial-data-url");
        log.debug("Loading data from {}", dataUrl);
        WebClient.create(dataUrl)
                .get()
                .retrieve()
                .bodyToMono(String.class)
                .map(BsonArray::parse)
                .doOnError(e -> log.error("Error occurred during initial data loading", e))
                .doOnSuccess(songs -> log.info("{} document(s) will be inserted ", songs.size()))
                .subscribe(songs -> mongoTemplate
                        .bulkOps(BulkOperations.BulkMode.ORDERED, Song.class)
                        .insert(songs).execute());
    }
}
