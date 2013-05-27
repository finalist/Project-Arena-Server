alter table participantanswer add column id BIGINT NOT NULL DEFAULT nextval('hibernate_sequence');
alter table participantanswer drop constraint participantanswer_pkey;
alter table participantanswer add primary key(id);
