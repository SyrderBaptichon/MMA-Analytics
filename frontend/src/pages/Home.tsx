import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

type EventDto = { id: string; name: string; eventDate?: string; location?: string };

export function Home(): JSX.Element {
  const [events, setEvents] = useState<EventDto[]>([]);

  useEffect(() => {
    fetch('/api/events?page=0&size=10')
      .then(r => r.json())
      .then(d => setEvents(d.content ?? []))
      .catch(() => setEvents([]));
  }, []);

  return (
    <div>
      <h2>Events</h2>
      <ul>
        {events.map(ev => (
          <li key={ev.id}>
            <Link to={`/events/${ev.id}`}>{ev.name}</Link>
            {ev.eventDate ? ` — ${ev.eventDate}` : ''} {ev.location ? ` — ${ev.location}` : ''}
          </li>
        ))}
      </ul>
    </div>
  );
}


