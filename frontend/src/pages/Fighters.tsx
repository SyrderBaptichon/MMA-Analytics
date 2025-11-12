import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

type FighterDto = { id: string; name: string; nickname?: string };

export function Fighters(): JSX.Element {
  const [fighters, setFighters] = useState<FighterDto[]>([]);
  const [q, setQ] = useState('');

  useEffect(() => {
    fetch(`/api/fighters?q=${encodeURIComponent(q)}&page=0&size=20`)
      .then(r => r.json())
      .then(d => setFighters(d.content ?? []))
      .catch(() => setFighters([]));
  }, [q]);

  return (
    <div>
      <h2>Fighters</h2>
      <input
        placeholder="Search fighters"
        value={q}
        onChange={e => setQ(e.target.value)}
        style={{ padding: 8, marginBottom: 12 }}
      />
      <ul>
        {fighters.map(f => (
          <li key={f.id}>
            <Link to={`/fighters/${f.id}`}>{f.name}</Link>
            {f.nickname ? ` (${f.nickname})` : ''}
          </li>
        ))}
      </ul>
    </div>
  );
}


