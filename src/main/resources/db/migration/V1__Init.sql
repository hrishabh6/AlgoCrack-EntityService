-- Create Table: user
CREATE TABLE user (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      created_at TIMESTAMP NOT NULL,
                      updated_at TIMESTAMP NOT NULL,
                      name VARCHAR(255),
                      user_id VARCHAR(255) UNIQUE NOT NULL,
                      img_url VARCHAR(255),
                      `rank` VARCHAR(255),
                      achievement_points INT
);



-- Create Table: question
CREATE TABLE question (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          created_at TIMESTAMP NOT NULL,
                          updated_at TIMESTAMP NOT NULL,
                          question_title VARCHAR(255),
                          question_description TEXT,
                          is_output_order_matters BOOLEAN,
                          difficulty_level VARCHAR(255),
                          company VARCHAR(255),
                          constraints VARCHAR(255),
                          timeout_limit INT
);


-- Create the 'solution' table
CREATE TABLE solution (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          question_id BIGINT NOT NULL,
                          language VARCHAR(50) NOT NULL,
                          code TEXT NOT NULL,

                          CONSTRAINT fk_solution_question FOREIGN KEY (question_id) REFERENCES question(id) ON DELETE CASCADE
);

-- Optional: Prevent duplicate solutions for the same question and language
CREATE UNIQUE INDEX idx_unique_question_language ON solution(question_id, language);


-- Create Table: test_case
CREATE TABLE test_case (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           created_at TIMESTAMP NOT NULL,
                           updated_at TIMESTAMP NOT NULL,
                           question_id BIGINT,
                           input TEXT,
                           expected_output TEXT,
                           order_index INT,
                           is_hidden BOOLEAN,
                           FOREIGN KEY (question_id) REFERENCES question(id)
);

-- Create Table: submission
CREATE TABLE submission (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            created_at TIMESTAMP NOT NULL,
                            updated_at TIMESTAMP NOT NULL,
                            user_id BIGINT,
                            question_id BIGINT,
                            code TEXT,
                            language VARCHAR(255),
                            is_passed BOOLEAN,
                            time_of_submission TIMESTAMP,
                            type_of_error VARCHAR(255),
                            FOREIGN KEY (user_id) REFERENCES user(id),
                            FOREIGN KEY (question_id) REFERENCES question(id)
);

-- Create Table: user_questionsolved (for ManyToMany relationship between User and Question)
CREATE TABLE user_questionsolved (
                                     user_id BIGINT NOT NULL,
                                     question_id BIGINT NOT NULL,
                                     PRIMARY KEY (user_id, question_id),
                                     FOREIGN KEY (user_id) REFERENCES user(id),
                                     FOREIGN KEY (question_id) REFERENCES question(id)
);

-- Create Table: question_tags (for ElementCollection in Question)
CREATE TABLE tag (
                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                     created_at TIMESTAMP NOT NULL,
                     updated_at TIMESTAMP NOT NULL,
                     name VARCHAR(255) UNIQUE NOT NULL
);
CREATE TABLE question_tag (
                              question_id BIGINT NOT NULL,
                              tag_id BIGINT NOT NULL,
                              PRIMARY KEY (question_id, tag_id),
                              FOREIGN KEY (question_id) REFERENCES question(id),
                              FOREIGN KEY (tag_id) REFERENCES tag(id)
);


-- Alter Table: question - Add a self-referencing foreign key for correct_answer
-- This assumes that 'correctAnswer' in Question refers to a list of Submission IDs
-- If `correctAnswer` in `Question` is intended to store the *actual* correct submission(s) from past submissions,
-- it would likely be a many-to-many or a list of submission IDs.
-- Based on the provided Submission entity, `correctAnswer` in `Question`
-- being a `@OneToMany` relationship to `Submission` means that a `Question` can have multiple `Submission` records
-- marked as its "correct answers". This is a bit unusual for a single correct answer, but plausible for multiple valid solutions.
-- The current schema suggests a `Submission` can be a correct answer for a `Question`.
-- This is already covered by the `submission` table having `question_id`.
-- If the intent is to explicitly mark certain submissions *as* the correct answer(s) for a question,
-- then a separate linking table or a flag on the `submission` table might be more appropriate.
-- For now, I'm assuming `correctAnswer` implies a relation where `Submission` records are associated with the `Question`
-- to denote them as "correct answers". No additional foreign key is strictly needed beyond what's in `submission` table,
-- unless you want to add a specific column in `submission` like `is_correct_answer_for_question` or a separate join table.
-- Given the `@OneToMany(mappedBy = "question", cascade = CascadeType.ALL)` on `correctAnswer` in `Question`,
-- and `Submission` having a `question_id`, it implies that submissions linked to a question via this relationship are
-- considered 'correct answers'. No new table or column is explicitly required for this specific mapping beyond
-- what is already present in the `submission` table.