CREATE OR REPLACE FUNCTION get_available_flights_RP_2627(
    p_origin      VARCHAR,
    p_destination VARCHAR,
    p_date        DATE
)
RETURNS TABLE (
    flight_code VARCHAR,
    airline     VARCHAR,
    origin      VARCHAR,
    destination VARCHAR,
    departure   TIME,
    arrival     TIME,
    duration    INTEGER,
    free_seats  INTEGER
)
LANGUAGE plpgsql
STABLE
AS $$
BEGIN
    RETURN QUERY
    SELECT f.flight_code,
           al.name,
           src.iata,
           dst.iata,
           f.departure,
           f.arrival,
           f.duration,
           (p.total_seats - COUNT(t.ticket_number))::INTEGER AS free_seats
    FROM flights f
    JOIN airports src ON src.code = f.source
    JOIN airports dst ON dst.code = f.destination
    JOIN airlines al  ON al.airlineid = f.airlineid
    JOIN planes p     ON p.plane_id = f.plane
    LEFT JOIN tickets t
           ON t.flight_code = f.flight_code
          AND t.date_of_travel = p_date
          AND t.date_of_cancellation IS NULL
    WHERE src.iata = UPPER(p_origin)
      AND dst.iata = UPPER(p_destination)
    GROUP BY f.flight_code, al.name, src.iata, dst.iata, f.departure, f.arrival, f.duration, p.total_seats
    HAVING p.total_seats - COUNT(t.ticket_number) > 0
    ORDER BY f.departure;
END;
$$;
