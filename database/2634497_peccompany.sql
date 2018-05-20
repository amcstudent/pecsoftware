-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: fdb19.awardspace.net
-- Generation Time: Apr 26, 2018 at 01:50 PM
-- Server version: 5.7.20-log
-- PHP Version: 5.5.38

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `2634497_peccompany`
--

-- --------------------------------------------------------

--
-- Table structure for table `cancelledJobs`
--

CREATE TABLE `cancelledJobs` (
  `id` bigint(100) NOT NULL,
  `jobOrderId` bigint(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Table structure for table `candidates`
--

CREATE TABLE `candidates` (
  `id` bigint(100) NOT NULL,
  `name` varchar(50) NOT NULL,
  `address` varchar(100) NOT NULL,
  `birthdate` varchar(15) NOT NULL,
  `email` varchar(50) NOT NULL,
  `residence_number` varchar(20) NOT NULL,
  `business_number` varchar(20) NOT NULL,
  `marital_status` varchar(10) NOT NULL,
  `driver_information` varchar(50) NOT NULL,
  `degree` varchar(50) NOT NULL,
  `position_desired` varchar(50) NOT NULL,
  `salary_desired` decimal(10,2) NOT NULL,
  `geographic_preference` varchar(50) NOT NULL,
  `travel_preference` varchar(3) NOT NULL,
  `current_position_salary` decimal(10,2) NOT NULL,
  `one_previous_position_salary` decimal(10,2) NOT NULL,
  `two_previous_position_salary` decimal(10,2) NOT NULL,
  `three_previous_position_salary` decimal(10,2) NOT NULL,
  `tenure_responsibilities` varchar(8) NOT NULL,
  `leaving_reason` varchar(200) NOT NULL,
  `interview_impressions` varchar(100) NOT NULL,
  `ratings` int(1) NOT NULL,
  `consultant_initials` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


-- --------------------------------------------------------

--
-- Table structure for table `candidatesFound`
--

CREATE TABLE `candidatesFound` (
  `id` bigint(100) NOT NULL,
  `candidateId` bigint(100) NOT NULL,
  `clientId` bigint(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Table structure for table `candidatesFoundHistory`
--

CREATE TABLE `candidatesFoundHistory` (
  `id` bigint(100) NOT NULL,
  `candidateId` bigint(100) NOT NULL,
  `clientId` bigint(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Table structure for table `currentJobs`
--

CREATE TABLE `currentJobs` (
  `id` bigint(100) NOT NULL,
  `jobOrderId` bigint(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `invoices`
--

CREATE TABLE `invoices` (
  `id` bigint(100) NOT NULL,
  `jobOrderId` bigint(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Table structure for table `jobOrders`
--

CREATE TABLE `jobOrders` (
  `id` bigint(100) NOT NULL,
  `position` varchar(50) NOT NULL,
  `date_inserted` varchar(10) NOT NULL,
  `firm` varchar(50) NOT NULL,
  `department` varchar(20) NOT NULL,
  `contact_name` varchar(50) NOT NULL,
  `address` varchar(100) NOT NULL,
  `billing_contact` varchar(50) NOT NULL,
  `business_type` varchar(100) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `educational_requirements` varchar(200) NOT NULL,
  `salary` decimal(10,2) DEFAULT NULL,
  `starting_date` varchar(10) NOT NULL,
  `experience_requirements` varchar(200) NOT NULL,
  `new_position` varchar(3) NOT NULL,
  `duties` varchar(200) NOT NULL,
  `bonuses` varchar(200) NOT NULL,
  `travel_requirements` varchar(200) CHARACTER SET utf8 COLLATE utf8_estonian_ci NOT NULL,
  `car` varchar(300) NOT NULL,
  `career_opportunities` varchar(100) NOT NULL,
  `interview` varchar(200) NOT NULL,
  `order_taker` varchar(100) NOT NULL,
  `placement_fee` decimal(10,2) DEFAULT NULL,
  `counselor_ultimate` varchar(50) NOT NULL,
  `invoiceNo` bigint(100) NOT NULL,
  `placement_date` varchar(10) NOT NULL,
  `candidateId` bigint(100) NOT NULL,
  `actual_starting_date` varchar(10) NOT NULL,
  `status` varchar(10) NOT NULL,
  `clientId` bigint(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `pecClients`
--

CREATE TABLE `pecClients` (
  `id` bigint(100) NOT NULL,
  `company` varchar(50) NOT NULL,
  `position` varchar(50) NOT NULL,
  `user` varchar(50) NOT NULL,
  `pass` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `pecClients`
--

INSERT INTO `pecClients` (`id`, `company`, `position`, `user`, `pass`, `email`) VALUES
(1, 'My Company', 'Λεωφόρος αλεξάνδρας 33', 'test', 'test', 'testemail@gmail.com'),

-- --------------------------------------------------------

--
-- Table structure for table `pecUsers`
--

CREATE TABLE `pecUsers` (
  `id` bigint(100) NOT NULL,
  `user` varchar(50) NOT NULL,
  `pass` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `pecUsers`
--

INSERT INTO `pecUsers` (`id`, `user`, `pass`, `name`, `surname`, `email`) VALUES
(1, 'kmanolatos', 'km123', 'ΚΩΝΣΤΑΝΤΙΝΟΣ', 'ΜΑΝΩΛΑΤΟΣ', 'kmanolatos18a@amcstudent.edu.gr'),
(2, 'orfeasorfan', 'oo123', 'ΟΡΦΕΑΣ', 'ΟΡΦΑΝΟΓΙΑΝΝΗΣ', '');

-- --------------------------------------------------------

--
-- Table structure for table `pendingJobs`
--

CREATE TABLE `pendingJobs` (
  `id` bigint(100) NOT NULL,
  `jobOrderId` bigint(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `resumes`
--

CREATE TABLE `resumes` (
  `id` bigint(100) NOT NULL,
  `common_duties` varchar(100) NOT NULL,
  `career_opportunities` varchar(100) NOT NULL,
  `educational_requirements` varchar(100) NOT NULL,
  `salary_ranges` varchar(50) NOT NULL,
  `experience_requirements` varchar(100) NOT NULL,
  `candidateId` bigint(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `waitingJobs`
--

CREATE TABLE `waitingJobs` (
  `id` bigint(100) NOT NULL,
  `jobOrderId` bigint(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cancelledJobs`
--
ALTER TABLE `cancelledJobs`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `candidates`
--
ALTER TABLE `candidates`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `candidatesFound`
--
ALTER TABLE `candidatesFound`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `candidatesFoundHistory`
--
ALTER TABLE `candidatesFoundHistory`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `currentJobs`
--
ALTER TABLE `currentJobs`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `invoices`
--
ALTER TABLE `invoices`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `jobOrders`
--
ALTER TABLE `jobOrders`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pecClients`
--
ALTER TABLE `pecClients`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pecUsers`
--
ALTER TABLE `pecUsers`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pendingJobs`
--
ALTER TABLE `pendingJobs`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `resumes`
--
ALTER TABLE `resumes`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `waitingJobs`
--
ALTER TABLE `waitingJobs`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cancelledJobs`
--
ALTER TABLE `cancelledJobs`
  MODIFY `id` bigint(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
--
-- AUTO_INCREMENT for table `candidates`
--
ALTER TABLE `candidates`
  MODIFY `id` bigint(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
--
-- AUTO_INCREMENT for table `candidatesFound`
--
ALTER TABLE `candidatesFound`
  MODIFY `id` bigint(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
--
-- AUTO_INCREMENT for table `candidatesFoundHistory`
--
ALTER TABLE `candidatesFoundHistory`
  MODIFY `id` bigint(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
--
-- AUTO_INCREMENT for table `currentJobs`
--
ALTER TABLE `currentJobs`
  MODIFY `id` bigint(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
--
-- AUTO_INCREMENT for table `invoices`
--
ALTER TABLE `invoices`
  MODIFY `id` bigint(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
--
-- AUTO_INCREMENT for table `jobOrders`
--
ALTER TABLE `jobOrders`
  MODIFY `id` bigint(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
--
-- AUTO_INCREMENT for table `pecClients`
--
ALTER TABLE `pecClients`
  MODIFY `id` bigint(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
--
-- AUTO_INCREMENT for table `pecUsers`
--
ALTER TABLE `pecUsers`
  MODIFY `id` bigint(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
--
-- AUTO_INCREMENT for table `pendingJobs`
--
ALTER TABLE `pendingJobs`
  MODIFY `id` bigint(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
--
-- AUTO_INCREMENT for table `resumes`
--
ALTER TABLE `resumes`
  MODIFY `id` bigint(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
--
-- AUTO_INCREMENT for table `waitingJobs`
--
ALTER TABLE `waitingJobs`
  MODIFY `id` bigint(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
