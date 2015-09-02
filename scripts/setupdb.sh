#!/bin/bash

if mysql.server start; then
	echo "CREATE DATABASE IF NOT EXISTS music_library;
		USE music_library;
		CREATE TABLE IF NOT EXISTS artist (
			artist_id INT NOT NULL,
			name VARCHAR(255),
			PRIMARY KEY (artist_id));
		CREATE TABLE IF NOT EXISTS album (
			album_id INT NOT NULL,
			name VARCHAR(255),
			artist_id INT NOT NULL,
			PRIMARY KEY (album_id),
			FOREIGN KEY (artist_id) REFERENCES artist(artist_id));
		CREATE TABLE IF NOT EXISTS song (
			song_id INT NOT NULL,
			name VARCHAR(255),
			time INT,
			file VARCHAR(255),
			track_number INT,
			disc_number INT,
			album_id INT NOT NULL,
			PRIMARY KEY (song_id),
			FOREIGN KEY (album_id) REFERENCES album(album_id));
		CREATE TABLE IF NOT EXISTS genre (
			genre_id INT NOT NULL,
			name VARCHAR(255),
			PRIMARY KEY (genre_id));
		CREATE TABLE IF NOT EXISTS song_genre (
			song_id INT NOT NULL,
			genre_id INT NOT NULL,
			FOREIGN KEY (song_id) REFERENCES song(song_id),
			FOREIGN KEY (genre_id) REFERENCES genre(genre_id));" | mysql -u root;
else
	echo "Could not start mysql";
fi
