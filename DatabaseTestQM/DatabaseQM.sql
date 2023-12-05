create database quizmasterUsers;  
use quizmasterUsers;  
create table user(username varchar(40), email varchar(40), phoneNumber varchar(40),password varchar(40));  

SELECT * FROM user;

create table categories(serialNumber int(10) PRIMARY KEY, categoryName varchar(40));

INSERT INTO categories (serialNumber, categoryName) VALUES (1, 'Biology');
INSERT INTO categories (serialNumber, categoryName) VALUES (2, 'Chemistry');
INSERT INTO categories (serialNumber, categoryName) VALUES (3, 'English');
INSERT INTO categories (serialNumber, categoryName) VALUES (4, 'Physics');
INSERT INTO categories (serialNumber, categoryName) VALUES (5, 'General Knowledge');

SELECT * FROM categories;

create table question (questionID int(10) PRIMARY KEY, categoryID int(10), questionText varchar(1000), option1 varchar(1000), option2 varchar(1000), option3 varchar(1000), option4 varchar(1000), correctAnswer int(10));


INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (1, 1, 'What is the primary function of DNA in living organisms?', 'Energy production', 'Cellular respiration','Genetic information storage','Protein synthesis', 3);idname
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (2, 1, 'Define photosynthesis and identify the key components involved in this process.', 'Oxygen consumption', 'Sugar breakdown','Sunlight absorption','Conversion of sunlight into chemical energy', 4);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (3, 1, 'Explain the difference between prokaryotic and eukaryotic cells.', 'Both lack a nucleus', 'Prokaryotic cells have a nucleus, while eukaryotic cells dont','Eukaryotic cells have a nucleus, while prokaryotic cells dont','Both contain membrane-bound organelles', 2);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (4, 1, 'What role does the mitochondria play in cellular function?', 'Photosynthesis', 'Cellular respiration and energy production','DNA replication','Protein synthesis', 2);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (5, 1, 'How does natural selection contribute to the process of evolution?', 'Random genetic mutations', 'Environmental changes','Selective advantage leading to increased survival and reproduction ','Artificial selection by humans', 3);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (6, 1, 'What is the basic unit of life?', 'Atom', 'Cell','Molecule','Organism', 2);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (7, 1, 'Which of the following is not a macromolecule?', 'Protein', 'Lipid','Glucose','Nucleic acid', 3);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (8, 1, 'What is the primary pigment used in photosynthesis?', 'Carotene', 'Chlorophyll', 'Xanthophyll','Anthocyanin', 2);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (9, 1, 'Which part of the cell is responsible for genetic storage?', 'Ribosome', 'Cytoplasm','Nucleus','Cell membrane', 3);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (10, 1, 'What blood type is considered the universal donor?', 'AB', 'A', 'B', 'O', 4);

INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (11, 2, 'What is the chemical symbol for Iron?', 'Fe', 'Ir', 'In', 'I', 1);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (12, 2, 'What is the pH of pure water at 25°C?', '5', '7', '9', '11', 2);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (13, 2, 'Which of the following is a noble gas?', 'Nitrogen', 'Oxygen', 'Helium', 'Hydrogen', 3);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (14, 2, 'What type of bond is formed when electrons are shared between atoms?', 'Ionic bond', 'Covalent bond', 'Metallic bond', 'Hydrogen bond', 2);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (15, 2, 'Which element is a liquid at room temperature?', 'Gold', 'Mercury', 'Silver', 'Copper', 2);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (16, 2, 'What is the main component of natural gas?', 'Methane', 'Ethane', 'Propane', 'Butane', 1);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (17, 2, 'What is the formula for table salt (Sodium Chloride)?', 'NaCl', 'KCl', 'Na2CO3', 'MgCl2', 1);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (18, 2, 'Which is not a type of chemical reaction?', 'Synthesis', 'Decomposition', 'Photosynthesis', 'Single displacement', 3);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (19, 2, 'What is the molar mass of water (H2O)?', '18 g/mol', '16 g/mol', '20 g/mol', '22 g/mol', 1);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (20, 2, 'Which of the following is an example of a physical change?', 'Rusting of iron', 'Burning wood', 'Dissolving salt in water', 'Baking a cake', 3);

INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (21, 3, 'What is a synonym for "happy"?', 'Sad', 'Elated', 'Angry', 'Confused', 2);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (22, 3, 'Who wrote "Romeo and Juliet"?', 'Charles Dickens', 'Jane Austen', 'William Shakespeare', 'J.K. Rowling', 3);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (23, 3, 'What is the past tense of "run"?', 'Ran', 'Running', 'Runs', 'Runned', 1);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (24, 3, 'Which of these is a metaphor?', 'She is like a rose.', 'Time is a thief.', 'He runs fast as a cheetah.', 'The rain was cold.', 2);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (25, 3, 'What type of word is "quickly"?', 'Noun', 'Verb', 'Adjective', 'Adverb', 4);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (26, 3, 'What is the main theme of George Orwelles "1984"?', 'Love', 'Adventure', 'Totalitarianism', 'Comedy', 3);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (27, 3, 'Which punctuation mark is used to indicate possession?', 'Comma', 'Apostrophe', 'Period', 'Exclamation mark', 2);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (28, 3, 'What is the plural form of "mouse"?', 'Mouses', 'Mousees', 'Mice', 'Mouseses', 3);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (29, 3, 'What is an antonym for "ancient"?', 'Old', 'Historic', 'Modern', 'Past', 3);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (30, 3, 'Which word is an example of an onomatopoeia?', 'Whisper', 'Buzz', 'Pretty', 'Smooth', 2);

INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (31, 4, 'What is the unit of force in the International System of Units?', 'Newton', 'Joule', 'Pascal', 'Watt', 1);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (32, 4, 'What does \'E\' represent in the equation E=mc²?', 'Energy', 'Electricity', 'Electron', 'Entropy', 1);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (33, 4, 'Which of the following is not a fundamental force in physics?', 'Gravitational Force', 'Electromagnetic Force', 'Strong Nuclear Force', 'Frictional Force', 4);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (34, 4, 'What phenomenon explains why the sky is blue?', 'Reflection', 'Refraction', 'Rayleigh scattering', 'Diffraction', 3);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (35, 4, 'What type of lens is used to correct myopia?', 'Convex Lens', 'Concave Lens', 'Cylindrical Lens', 'Bifocal Lens', 2);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (36, 4, 'Which principle explains why ships float?', 'Pascal\'s Principle', 'Archimedes\' Principle', 'Newton\'s Third Law', 'Bernoulli\'s Principle', 2);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (37, 4, 'What is the speed of light in a vacuum?', '3 × 10^8 m/s', '1 × 10^6 m/s', '5 × 10^7 m/s', '2 × 10^9 m/s', 1);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (38, 4, 'Which particle is known as the \'God Particle\'?', 'Electron', 'Neutron', 'Photon', 'Higgs Boson', 4);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (39, 4, 'What is the process of splitting a nucleus into two or more smaller nuclei called?', 'Fusion', 'Fission', 'Ionization', 'Polarization', 2);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (40, 4, 'What is the principle behind the working of a rocket?', 'Newton\'s First Law', 'Newton\'s Second Law', 'Newton\'s Third Law', 'Einstein\'s Theory of Relativity', 3);

INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (41, 5, 'What is the capital city of France?', 'Berlin', 'London', 'Paris', 'Madrid', 3);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (42, 5, 'Which planet is known as the Red Planet?', 'Venus', 'Jupiter', 'Mars', 'Saturn', 3);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (43, 5, 'Who wrote the play \'Hamlet\'?', 'Charles Dickens', 'Jane Austen', 'William Shakespeare', 'Leo Tolstoy', 3);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (44, 5, 'What is the largest mammal in the world?', 'Elephant', 'Rhino', 'Blue Whale', 'Giraffe', 3);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (45, 5, 'In which year did the Titanic sink?', '1905', '1912', '1920', '1931', 2);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (46, 5, 'Which element has the chemical symbol \'O\'?', 'Gold', 'Oxygen', 'Osmium', 'Iron', 2);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (47, 5, 'Who is known as the \'Father of Computers\'?', 'Nikola Tesla', 'Charles Babbage', 'Albert Einstein', 'Thomas Edison', 2);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (48, 5, 'What is the hardest natural substance on Earth?', 'Iron', 'Diamond', 'Quartz', 'Granite', 2);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (49, 5, 'Which country is the largest in the world by area?', 'China', 'United States', 'Canada', 'Russia', 4);
INSERT INTO question(questionID, categoryID, questionText, option1, option2, option3, option4, correctAnswer) VALUES (50, 5, 'Who painted the Mona Lisa?', 'Vincent van Gogh', 'Pablo Picasso', 'Leonardo da Vinci', 'Michelangelo', 3);

SELECT * FROM question;
