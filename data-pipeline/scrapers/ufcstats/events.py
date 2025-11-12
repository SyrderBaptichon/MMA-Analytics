import os
import time
import hashlib
from typing import Iterator, Dict, Any, List, Optional

import requests
from bs4 import BeautifulSoup  # type: ignore
from tenacity import retry, stop_after_attempt, wait_exponential  # type: ignore

BASE_URL = "http://ufcstats.com"
EVENTS_URL = f"{BASE_URL}/statistics/events/completed?page=all"
USER_AGENT = "FightIQBot/0.1 (+https://example.com)"


def _cache_path(url: str) -> str:
  cache_dir = os.path.join(os.path.expanduser("~"), ".fightiq", "cache")
  os.makedirs(cache_dir, exist_ok=True)
  key = hashlib.sha256(url.encode("utf-8")).hexdigest() + ".html"
  return os.path.join(cache_dir, key)


def _read_cache(path: str, ttl_seconds: int) -> Optional[str]:
  if not os.path.exists(path):
    return None
  mtime = os.path.getmtime(path)
  if (time.time() - mtime) > ttl_seconds:
    return None
  with open(path, "r", encoding="utf-8") as f:
    return f.read()


def _write_cache(path: str, content: str) -> None:
  with open(path, "w", encoding="utf-8") as f:
    f.write(content)


@retry(stop=stop_after_attempt(3), wait=wait_exponential(multiplier=1, min=1, max=8))
def fetch(url: str, ttl_seconds: int = 3600) -> str:
  headers = {"User-Agent": USER_AGENT}
  cache_file = _cache_path(url)
  cached = _read_cache(cache_file, ttl_seconds)
  if cached is not None:
    return cached
  response = requests.get(url, headers=headers, timeout=20)
  response.raise_for_status()
  time.sleep(0.5)
  html = response.text
  _write_cache(cache_file, html)
  return html


def parse_events(html: str) -> List[Dict[str, Any]]:
  soup = BeautifulSoup(html, "lxml")
  table = soup.select_one("table.b-statistics__table-events")
  if table is None:
    return []
  rows = table.select("tr.b-statistics__table-row")
  results: List[Dict[str, Any]] = []
  for row in rows:
    link = row.select_one("a")
    date_el = row.select_one("span.b-statistics__date")
    location_el = row.select_one("td.b-statistics__table-col_style_big-top-padding")
    if not link:
      continue
    results.append({
      "name": link.text.strip(),
      "event_url": link.get("href"),
      "date": date_el.text.strip() if date_el else None,
      "location": (location_el.text.strip() if location_el else None)
    })
  return results


def iter_events() -> Iterator[Dict[str, Any]]:
  html = fetch(EVENTS_URL)
  for ev in parse_events(html):
    yield ev


if __name__ == "__main__":
  for ev in iter_events():
    print(ev)


