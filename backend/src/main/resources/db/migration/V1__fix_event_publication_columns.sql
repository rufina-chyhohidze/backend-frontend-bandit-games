-- Fix Spring Modulith event_publication table to handle large serialized events
-- This migration ensures the columns can store TEXT instead of VARCHAR(255)

-- Check if table exists before altering (in case Hibernate hasn't created it yet)
DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'event_publication') THEN
        -- Alter serialized_event column to TEXT
ALTER TABLE event_publication
ALTER COLUMN serialized_event TYPE TEXT;

        -- Alter event_type column to TEXT
ALTER TABLE event_publication
ALTER COLUMN event_type TYPE TEXT;

        -- Alter listener_id column to TEXT
ALTER TABLE event_publication
ALTER COLUMN listener_id TYPE TEXT;
END IF;
END $$;

-- Also fix win_probability_projections if it exists
DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'win_probability_projections') THEN
ALTER TABLE win_probability_projections
ALTER COLUMN game_state TYPE TEXT;
END IF;
END $$;