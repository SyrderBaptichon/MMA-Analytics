import React from 'react';
import { Link, Route, Routes } from 'react-router-dom';
import { Home } from './pages/Home';
import { Fighters } from './pages/Fighters';
import { FighterDetail } from './pages/FighterDetail';
import { EventDetail } from './pages/EventDetail';

export function App(): JSX.Element {
  return (
    <div style={{ padding: 24 }}>
      <h1>Fight-IQ</h1>
      <nav style={{ display: 'flex', gap: 12, marginBottom: 16 }}>
        <Link to="/">Home</Link>
        <Link to="/fighters">Fighters</Link>
      </nav>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/fighters" element={<Fighters />} />
        <Route path="/fighters/:id" element={<FighterDetail />} />
        <Route path="/events/:id" element={<EventDetail />} />
      </Routes>
    </div>
  );
}


