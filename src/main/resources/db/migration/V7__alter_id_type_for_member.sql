ALTER TABLE Member ALTER COLUMN id TYPE BIGINT;
ALTER TABLE Member ALTER COLUMN id SET DEFAULT nextval('member_seq');
