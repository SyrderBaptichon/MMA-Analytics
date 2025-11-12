CREATE TABLE IF NOT EXISTS fighters (
  id UUID PRIMARY KEY,
  external_ref TEXT UNIQUE,
  name TEXT NOT NULL,
  nickname TEXT,
  stance TEXT,
  height_cm INTEGER,
  reach_cm INTEGER,
  date_of_birth DATE
);

CREATE TABLE IF NOT EXISTS fighter_aliases (
  fighter_id UUID REFERENCES fighters(id) ON DELETE CASCADE,
  alias TEXT NOT NULL,
  PRIMARY KEY (fighter_id, alias)
);

CREATE TABLE IF NOT EXISTS events (
  id UUID PRIMARY KEY,
  external_ref TEXT UNIQUE,
  name TEXT NOT NULL,
  event_date DATE,
  location TEXT
);

CREATE TABLE IF NOT EXISTS bouts (
  id UUID PRIMARY KEY,
  event_id UUID NOT NULL REFERENCES events(id) ON DELETE CASCADE,
  fighter_red_id UUID NOT NULL REFERENCES fighters(id) ON DELETE RESTRICT,
  fighter_blue_id UUID NOT NULL REFERENCES fighters(id) ON DELETE RESTRICT,
  weight_class TEXT,
  result TEXT,
  method TEXT,
  round INTEGER,
  time VARCHAR(16)
);

CREATE INDEX IF NOT EXISTS idx_bouts_event ON bouts(event_id);
CREATE INDEX IF NOT EXISTS idx_bouts_fighter_red ON bouts(fighter_red_id);
CREATE INDEX IF NOT EXISTS idx_bouts_fighter_blue ON bouts(fighter_blue_id);


