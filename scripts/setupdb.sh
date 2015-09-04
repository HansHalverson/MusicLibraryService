#!/bin/bash

if mysql.server start; then
	echo "CREATE DATABASE IF NOT EXISTS music_library;
		USE music_library;
		CREATE TABLE IF NOT EXISTS artist (
			artist_id INT NOT NULL AUTO_INCREMENT,
			name VARCHAR(255),
			PRIMARY KEY (artist_id));
		CREATE TABLE IF NOT EXISTS album (
			album_id INT NOT NULL AUTO_INCREMENT,
			name VARCHAR(255),
			artist_id INT NOT NULL,
			PRIMARY KEY (album_id),
			FOREIGN KEY (artist_id) REFERENCES artist(artist_id));
		CREATE TABLE IF NOT EXISTS song (
			song_id INT NOT NULL AUTO_INCREMENT,
			name VARCHAR(255),
			file VARCHAR(255),
			time INT,
			plays INT,
			track_number INT,
			disc_number INT,
			album_id INT NOT NULL,
			PRIMARY KEY (song_id),
			FOREIGN KEY (album_id) REFERENCES album(album_id));
		CREATE TABLE IF NOT EXISTS genre (
			genre_id INT NOT NULL AUTO_INCREMENT,
			name VARCHAR(255),
			supergenre_id INT,
			PRIMARY KEY (genre_id),
			FOREIGN KEY (supergenre_id) REFERENCES genre(genre_id));
		CREATE TABLE IF NOT EXISTS album_genre (
			album_id INT NOT NULL,
			genre_id INT NOT NULL,
			FOREIGN KEY (album_id) REFERENCES album(album_id),
			FOREIGN KEY (genre_id) REFERENCES genre(genre_id));" | mysql -u root;
else
	echo "Could not start mysql";
fi
