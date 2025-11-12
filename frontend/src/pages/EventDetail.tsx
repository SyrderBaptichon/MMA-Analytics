import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

type EventDto = { id: string; name: string; eventDate?: string; location?: string };
type Bout = {
  id: string;
  weightClass?: string;
  result?: string;
  method?: string;
  round?: number;
  time?: string;
};

export function EventDetail(): JSX.Element {
  const { id } = useParams();
  const [event, setEvent] = useState<EventDto | null>(null);
  const [bouts, setBouts] = useState<Bout[]>([]);

  useEffect(() => {
    if (!id) return;
    fetch(`/api/events/${id}`).then(r => r.json()).then(setEvent).catch(() => setEvent(null));
    fetch(`/api/events/${id}/bouts`).then(r => r.json()).then(setBouts).catch(() => setBouts([]));
  }, [id]);

  if (!event) return <div>Loading...</div>;
  return (
    <div>
      <h2>{event.name}</h2>
      <p>{event.eventDate} — {event.location}</p>
      <h3>Bouts</h3>
      <ul>
        {bouts.map(b => (
          <li key={b.id}>
            {b.weightClass ?? ''} — {b.method ?? ''} {b.round ? `(R${b.round} ${b.time ?? ''})` : ''}
          </li>
        ))}
      </ul>
    </div>
  );
}


