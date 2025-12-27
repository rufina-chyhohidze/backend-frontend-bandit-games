-- Force fix the event_publication columns to TEXT
-- This is a retry because V1 may have failed silently

ALTER TABLE event_publication ALTER COLUMN serialized_event TYPE TEXT;
ALTER TABLE event_publication ALTER COLUMN event_type TYPE TEXT;
ALTER TABLE event_publication ALTER COLUMN listener_id TYPE TEXT;

DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'win_probability_projections') THEN
ALTER TABLE win_probability_projections ALTER COLUMN game_state TYPE TEXT;
END IF;
END $$;
