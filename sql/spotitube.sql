-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Gegenereerd op: 18 mrt 2021 om 15:27
-- Serverversie: 10.4.10-MariaDB
-- PHP-versie: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `spotitube`
--
CREATE DATABASE IF NOT EXISTS `spotitube` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `spotitube`;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `playlists`
--

CREATE TABLE `playlists` (
  `id` int(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `owner` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tabel leegmaken voor invoegen `playlists`
--

TRUNCATE TABLE `playlists`;
--
-- Gegevens worden geëxporteerd voor tabel `playlists`
--

INSERT INTO `playlists` (`id`, `name`, `owner`) VALUES
(1, 'Pop', 'testsjaak'),
(2, 'Dutch', 'testsjaak'),
(3, 'French', 'testaccount'),
(4, 'Test', 'testsjaak'),
(5, 'Progressive Rock', 'testaccount'),
(14, 'Klein testje', 'testaccount');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `playlist_tracks`
--

CREATE TABLE `playlist_tracks` (
  `id` int(100) NOT NULL,
  `playlistId` int(100) NOT NULL,
  `trackId` int(100) NOT NULL,
  `offlineAvailable` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tabel leegmaken voor invoegen `playlist_tracks`
--

TRUNCATE TABLE `playlist_tracks`;
--
-- Gegevens worden geëxporteerd voor tabel `playlist_tracks`
--

INSERT INTO `playlist_tracks` (`id`, `playlistId`, `trackId`, `offlineAvailable`) VALUES
(1, 1, 3, 0),
(2, 1, 4, 0),
(3, 1, 6, 1),
(5, 2, 1, 1),
(6, 2, 2, 0),
(7, 3, 9, 1),
(8, 3, 10, 0),
(13, 1, 7, 0),
(15, 5, 5, 1);

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `tokens`
--

CREATE TABLE `tokens` (
  `username` varchar(100) NOT NULL,
  `token` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tabel leegmaken voor invoegen `tokens`
--

TRUNCATE TABLE `tokens`;
--
-- Gegevens worden geëxporteerd voor tabel `tokens`
--

INSERT INTO `tokens` (`username`, `token`) VALUES
('testsjaak', '06a7355c-53ee-496d-aa8f-e52680ffdf72'),
('testaccount', 'c4cfb829-e7a3-4b3f-8157-560ab53d44aa');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `tracks`
--

CREATE TABLE `tracks` (
  `id` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `performer` varchar(100) NOT NULL,
  `duration` int(11) NOT NULL,
  `album` varchar(200) DEFAULT NULL,
  `playcount` int(100) DEFAULT NULL,
  `publicationDate` varchar(100) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tabel leegmaken voor invoegen `tracks`
--

TRUNCATE TABLE `tracks`;
--
-- Gegevens worden geëxporteerd voor tabel `tracks`
--

INSERT INTO `tracks` (`id`, `title`, `performer`, `duration`, `album`, `playcount`, `publicationDate`, `description`) VALUES
(1, 'Scars', 'Snelle', 196, 'Beetje bij Beetje', NULL, NULL, NULL),
(2, 'Eredivisie', 'Snelle', 145, 'Beetje bij Beetje', NULL, NULL, NULL),
(3, 'Boyfriend', 'Justin Bieber', 171, 'Believe', NULL, NULL, NULL),
(4, 'All Around The World', 'Justin Bieber', 244, 'Believe', NULL, NULL, NULL),
(5, 'Yummy', 'Justin Bieber', 208, 'Changes', NULL, NULL, NULL),
(6, 'Levitating', 'Dua Lipa', 203, NULL, 51, '13-08-2020', 'Bonus song for the album'),
(7, 'Physical', 'Dua Lipa', 194, NULL, 32, '31-01-2020', 'Very popular song'),
(8, 'Break My Heart', 'Dua Lipa', 222, NULL, 81, '25-03-2020', 'Beautiful song'),
(9, 'Balance ton quoi', 'Angèle', 189, NULL, 12, '15-04-2019', 'This song got many prizes'),
(10, 'Flou', 'Angèle', 197, NULL, 73, '05-10-2018', 'French song'),
(11, 'Lifestyle', 'Jason Derulo', 153, NULL, 11, '21-01-2021', NULL),
(12, 'Dragon Roll', 'Frenna', 156, 'Highest', NULL, NULL, NULL),
(13, 'Anyone', 'Justin Bieber', 190, NULL, 144, '01-01-2021', 'Song about the love of his life.');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `users`
--

CREATE TABLE `users` (
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tabel leegmaken voor invoegen `users`
--

TRUNCATE TABLE `users`;
--
-- Gegevens worden geëxporteerd voor tabel `users`
--

INSERT INTO `users` (`username`, `password`) VALUES
('testaccount', 'ecd71870d1963316a97e3ac3408c9835ad8cf0f3c1bc703527c30265534f75ae'),
('testsjaak', '9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08');

--
-- Indexen voor geëxporteerde tabellen
--

--
-- Indexen voor tabel `playlists`
--
ALTER TABLE `playlists`
  ADD PRIMARY KEY (`id`),
  ADD KEY `owner` (`owner`);

--
-- Indexen voor tabel `playlist_tracks`
--
ALTER TABLE `playlist_tracks`
  ADD PRIMARY KEY (`id`),
  ADD KEY `playlistId` (`playlistId`),
  ADD KEY `trackId` (`trackId`);

--
-- Indexen voor tabel `tokens`
--
ALTER TABLE `tokens`
  ADD PRIMARY KEY (`username`),
  ADD UNIQUE KEY `token` (`token`);

--
-- Indexen voor tabel `tracks`
--
ALTER TABLE `tracks`
  ADD PRIMARY KEY (`id`);

--
-- Indexen voor tabel `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`username`);

--
-- AUTO_INCREMENT voor geëxporteerde tabellen
--

--
-- AUTO_INCREMENT voor een tabel `playlists`
--
ALTER TABLE `playlists`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT voor een tabel `playlist_tracks`
--
ALTER TABLE `playlist_tracks`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT voor een tabel `tracks`
--
ALTER TABLE `tracks`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Beperkingen voor geëxporteerde tabellen
--

--
-- Beperkingen voor tabel `playlists`
--
ALTER TABLE `playlists`
  ADD CONSTRAINT `playlists_ibfk_1` FOREIGN KEY (`owner`) REFERENCES `users` (`username`);

--
-- Beperkingen voor tabel `playlist_tracks`
--
ALTER TABLE `playlist_tracks`
  ADD CONSTRAINT `playlist_tracks_ibfk_1` FOREIGN KEY (`playlistId`) REFERENCES `playlists` (`id`),
  ADD CONSTRAINT `playlist_tracks_ibfk_2` FOREIGN KEY (`playlistId`) REFERENCES `playlists` (`id`),
  ADD CONSTRAINT `playlist_tracks_ibfk_3` FOREIGN KEY (`trackId`) REFERENCES `tracks` (`id`);

--
-- Beperkingen voor tabel `tokens`
--
ALTER TABLE `tokens`
  ADD CONSTRAINT `tokens_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
