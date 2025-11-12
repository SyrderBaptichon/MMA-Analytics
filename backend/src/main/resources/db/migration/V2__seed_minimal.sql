-- Minimal seed data for dev
INSERT INTO fighters (id, external_ref, name)
VALUES 
  ('00000000-0000-0000-0000-000000000001', 'fighter:sample:red', 'Sample Red'),
  ('00000000-0000-0000-0000-000000000002', 'fighter:sample:blue', 'Sample Blue')
ON CONFLICT (id) DO NOTHING;

INSERT INTO events (id, external_ref, name, event_date, location)
VALUES 
  ('10000000-0000-0000-0000-000000000001', 'event:sample:1', 'UFC Sample 1', CURRENT_DATE, 'Las Vegas, NV')
ON CONFLICT (id) DO NOTHING;

INSERT INTO bouts (id, event_id, fighter_red_id, fighter_blue_id, weight_class, result, method, round, time)
VALUES 
  ('20000000-0000-0000-0000-000000000001',
   '10000000-0000-0000-0000-000000000001',
   '00000000-0000-0000-0000-000000000001',
   '00000000-0000-0000-0000-000000000002',
   'Welterweight',
   'Red',
   'Decision',
   3,
   '5:00')
ON CONFLICT (id) DO NOTHING;


