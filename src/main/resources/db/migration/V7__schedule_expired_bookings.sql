-- Requires the pg_cron package and shared_preload_libraries configuration.
-- This migration follows the existing V1-V6 migrations in main (55e4975).
CREATE EXTENSION IF NOT EXISTS pg_cron;

SELECT cron.schedule(
    'set_unpaid_bookings_expired',
    '* * * * *',
    $job$
        UPDATE public.bookings AS b
        SET status_id = expired.id
        FROM public.booking_statuses AS pending,
             public.booking_statuses AS expired
        WHERE b.status_id = pending.id
          AND pending.name = 'PENDING_PAYMENT'
          AND expired.name = 'EXPIRED'
          AND b.created_at <= CURRENT_TIMESTAMP - INTERVAL '5 minutes';
    $job$
);
