CREATE TABLE song (
    id SERIAL,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255),
    album VARCHAR(255),
    CONSTRAINT pk_song_id PRIMARY KEY (id)
);

CREATE TABLE verse (
    id SERIAL,
    index INTEGER NOT NULL,
    song_id INTEGER NOT NULL,
    CONSTRAINT pk_verse_id PRIMARY KEY (id),
    CONSTRAINT uk_verse_id_song_id UNIQUE (id, song_id),
    CONSTRAINT uk_verse_index_song_id UNIQUE (index, song_id),
    CONSTRAINT fk_verse_song_id FOREIGN KEY (song_id) REFERENCES song (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE quote (
    id SERIAL,
    phrase TEXT NOT NULL,
    index INTEGER NOT NULL,
    verse_id INTEGER NOT NULL,
    CONSTRAINT pk_quote_id PRIMARY KEY (id),
    CONSTRAINT uk_quote_id_verse_id UNIQUE (id, verse_id),
    CONSTRAINT uk_quote_index_verse_id UNIQUE (index, verse_id),
    CONSTRAINT fk_quote_verse_id FOREIGN KEY (verse_id) REFERENCES verse (id) ON DELETE CASCADE ON UPDATE CASCADE
);
