package com.github.lindenhoney.migration;

import com.github.lindenhoney.domain.Song;
import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonArray;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@ChangeLog
public class InitialSetupChangelog {

    @ChangeSet(order = "001", id = "loadData", author = "Alexander Babai")
    public void loadData(MongoTemplate mongoTemplate, Environment environment){
        final String dataUrl = environment.getRequiredProperty("linden-honey.db.migration.load-data.url");
        WebClient.create(dataUrl)
                .get()
                .retrieve()
                .bodyToMono(String.class)
                .map(BsonArray::parse)
                .doOnError(e -> log.error("Error occurred during init data loading", e))
                .doOnSuccess(songs -> log.info("Initial data successfully inserted - {} document(s)", songs.size()))
                .subscribe(songs -> mongoTemplate.insert(songs, Song.class));
    }
}
