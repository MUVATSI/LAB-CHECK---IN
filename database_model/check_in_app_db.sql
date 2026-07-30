-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : jeu. 30 juil. 2026 à 09:08
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `lab_db`
--

-- --------------------------------------------------------

--
-- Structure de la table `labor`
--

CREATE TABLE `labor` (
  `id_labo` int(11) NOT NULL,
  `nom_labo` varchar(50) NOT NULL,
  `capacite_max` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `labor`
--

INSERT INTO `labor` (`id_labo`, `nom_labo`, `capacite_max`) VALUES
(1, 'LABORATOIRE FTSI', 5),
(2, 'COMPUTER LAB', 80);

-- --------------------------------------------------------

--
-- Structure de la table `lab_user`
--

CREATE TABLE `lab_user` (
  `id_user` int(11) NOT NULL,
  `matricule` varchar(10) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `post_nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `departement` varchar(10) NOT NULL,
  `faculte` varchar(20) DEFAULT 'FTSI'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `lab_user`
--

INSERT INTO `lab_user` (`id_user`, `matricule`, `nom`, `post_nom`, `prenom`, `departement`, `faculte`) VALUES
(1, '1725', 'Mbale', 'Sangula', 'Patrice', 'GI', 'FTSI'),
(2, '3525', 'Kasungu', 'Kasungu', 'Elize', 'GI', 'FTSI'),
(3, '3425', 'Masali', 'Masali', 'Jean-Vianney', 'GI ', 'FSTI ');

-- --------------------------------------------------------

--
-- Structure de la table `presence`
--

CREATE TABLE `presence` (
  `id_presence` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `id_labo` int(11) NOT NULL,
  `motif` varchar(50) NOT NULL,
  `date_heure_in` datetime NOT NULL,
  `date_heure_out` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `presence`
--

INSERT INTO `presence` (`id_presence`, `id_user`, `id_labo`, `motif`, `date_heure_in`, `date_heure_out`) VALUES
(1, 1, 1, 'Item 1', '2026-07-29 19:14:51', '2026-07-29 19:15:18'),
(2, 1, 1, 'Item 1', '2026-07-29 19:15:33', '2026-07-29 19:19:11'),
(3, 1, 1, 'Item 1', '2026-07-29 19:19:31', '2026-07-29 19:22:26'),
(4, 1, 1, 'Item 1', '2026-07-29 19:22:44', '2026-07-29 19:31:35'),
(5, 1, 1, 'Item 1', '2026-07-29 19:31:46', '2026-07-29 19:41:43'),
(6, 2, 1, 'Item 1', '2026-07-29 19:33:46', '2026-07-29 19:34:26'),
(7, 1, 1, 'Item 1', '2026-07-29 19:41:51', NULL),
(8, 2, 1, 'Item 1', '2026-07-29 19:42:01', NULL),
(9, 3, 1, 'Travail personnel', '2026-07-30 08:21:13', NULL);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `labor`
--
ALTER TABLE `labor`
  ADD PRIMARY KEY (`id_labo`);

--
-- Index pour la table `lab_user`
--
ALTER TABLE `lab_user`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `matricule` (`matricule`);

--
-- Index pour la table `presence`
--
ALTER TABLE `presence`
  ADD PRIMARY KEY (`id_presence`),
  ADD KEY `id_user` (`id_user`),
  ADD KEY `id_labo` (`id_labo`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `labor`
--
ALTER TABLE `labor`
  MODIFY `id_labo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `lab_user`
--
ALTER TABLE `lab_user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `presence`
--
ALTER TABLE `presence`
  MODIFY `id_presence` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `presence`
--
ALTER TABLE `presence`
  ADD CONSTRAINT `presence_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `lab_user` (`id_user`) ON DELETE CASCADE,
  ADD CONSTRAINT `presence_ibfk_2` FOREIGN KEY (`id_labo`) REFERENCES `labor` (`id_labo`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
