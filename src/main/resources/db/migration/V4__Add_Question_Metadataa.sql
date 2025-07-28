CREATE TABLE question_metadata
(
    id                   BIGINT AUTO_INCREMENT NOT NULL,
    created_at           datetime              NOT NULL,
    updated_at           datetime              NOT NULL,
    function_name        VARCHAR(255)          NULL,
    return_type          VARCHAR(255)          NULL,
    language             VARCHAR(255)          NULL,
    code_template        TEXT                  NULL,
    test_case_format     TEXT                  NULL,
    execution_strategy   VARCHAR(255)          NULL,
    custom_input_enabled BIT(1)                NULL,
    question_id          BIGINT                NULL,
    CONSTRAINT pk_questionmetadata PRIMARY KEY (id)
);

CREATE TABLE question_metadata_param_names
(
    question_metadata_id BIGINT       NOT NULL,
    param_names          VARCHAR(255) NULL
);

CREATE TABLE question_metadata_param_types
(
    question_metadata_id BIGINT       NOT NULL,
    param_types          VARCHAR(255) NULL
);

ALTER TABLE tag
    ADD `description` TEXT NULL;

ALTER TABLE question_metadata
    ADD CONSTRAINT FK_QUESTIONMETADATA_ON_QUESTION FOREIGN KEY (question_id) REFERENCES question (id);

ALTER TABLE question_metadata_param_names
    ADD CONSTRAINT fk_questionmetadata_paramnames_on_question_metadata FOREIGN KEY (question_metadata_id) REFERENCES question_metadata (id);

ALTER TABLE question_metadata_param_types
    ADD CONSTRAINT fk_questionmetadata_paramtypes_on_question_metadata FOREIGN KEY (question_metadata_id) REFERENCES question_metadata (id);




