import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

type FighterDto = {
  id: string;
  name: string;
  nickname?: string;
  stance?: string;
  heightCm?: number;
  reachCm?: number;
  dateOfBirth?: string;
};

export function FighterDetail(): JSX.Element {
  const { id } = useParams();
  const [fighter, setFighter] = useState<FighterDto | null>(null);

  useEffect(() => {
    if (!id) return;
    fetch(`/api/fighters/${id}`)
      .then(r => r.json())
      .then(setFighter)
      .catch(() => setFighter(null));
  }, [id]);

  if (!fighter) return <div>Loading...</div>;
  return (
    <div>
      <h2>{fighter.name}</h2>
      {fighter.nickname && <p>Nickname: {fighter.nickname}</p>}
      {fighter.stance && <p>Stance: {fighter.stance}</p>}
      {fighter.heightCm && <p>Height: {fighter.heightCm} cm</p>}
      {fighter.reachCm && <p>Reach: {fighter.reachCm} cm</p>}
      {fighter.dateOfBirth && <p>DOB: {fighter.dateOfBirth}</p>}
    </div>
  );
}


